package com.seoayeon.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

//@Controller
@SessionAttributes("name")
public class TodoController {

    public TodoController(TodoService todoService) {
        super();
        this.todoService = todoService;
    }

    private TodoService todoService;


    @RequestMapping("list-todos")
    public String listAllTodos(ModelMap model) {
        String username = getLoggedInUsername(model);
        List<Todo> todos = todoService.findByUsername(username);
        model.addAttribute("todos", todos);

        return "listTodos";
    }

    //GET, POST
    @RequestMapping(value="add-todo", method = RequestMethod.GET)
    public String showNewTodoPage(ModelMap model) {
        String username = getLoggedInUsername(model);
        Todo todo = new Todo(0, username, "", LocalDate.now().plusYears(1), false);
        model.put("todo", todo);
        return "todo";
    }

    @RequestMapping(value="add-todo", method = RequestMethod.POST)
    public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

        if(result.hasErrors()) {
            return "todo";
        }

        String username = getLoggedInUsername(model);
        todoService.addTodo(username, todo.getDescription(),
                LocalDate.now().plusYears(1), false);
        return "redirect:list-todos";
    }

    @RequestMapping("delete-todo")
    public String deleteTodo(@RequestParam int id) {
        //Delete todo

        todoService.deleteById(id);
        return "redirect:list-todos";

    }

    @RequestMapping(value="update-todo", method = RequestMethod.GET)
    public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
        Todo todo = todoService.findById(id);
        model.addAttribute("todo", todo);
        return "todo";
    }
    @RequestMapping(value="update-todo", method = RequestMethod.POST)
    public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

        if(result.hasErrors()) {
            return "todo";
        }
        String username = getLoggedInUsername(model);
        todo.setUsername(username);
        todoService.updateTodo(todo);
        return "redirect:list-todos";
    }
    private String getLoggedInUsername(ModelMap model) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
    @RequestMapping(value = "mark-done", method = RequestMethod.POST)
    public String markTodoAsDone(@RequestParam int id) {
        // 해당 id로 Todo를 찾음
        Todo todo = todoService.findById(id); // todoService를 사용해 Todo 찾기
        if (todo != null) {
            // done 값을 true로 설정
            todo.setDone(true);
            // 변경된 todo를 저장
            todoService.updateTodo(todo);
        }
        // 완료된 후 목록 페이지로 리다이렉트
        return "redirect:list-todos";
    }
}