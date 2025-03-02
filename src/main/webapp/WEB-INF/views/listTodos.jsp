<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container text-center">

    <h1 class="mb-5">Your Todos</h1>

    <table class="table">
        <thead>
            <tr>
                <th></th>
                <th>Description</th>
                <th>Target Date</th>
                <th>Is Done?</th>
                <th></th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${todos}" var="todo">
                <tr>
                    <form action="mark-done" method="post">
                                <input type="hidden" name="id" value="${todo.id}" />
                                <td><button type="submit" class="btn btn-primary">DONE</button></td>
                    </form>
                    <td>${todo.description}</td>
                    <td>${todo.targetDate}</td>
                    <td>${todo.done}</td>
                    <td><a href="update-todo?id=${todo.id}" class="btn btn-success">UPDATE</a></td>
                    <td><a href="delete-todo?id=${todo.id}" class="btn btn-danger">DELETE</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="add-todo" class="btn btn-success">Add Todo</a>
</div>

<%@ include file="common/footer.jspf"%>
