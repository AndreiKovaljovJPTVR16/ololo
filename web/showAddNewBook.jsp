<%-- 
    Document   : page3
    Created on : Dec 10, 2018, 11:03:45 AM
    Author     : Melnikov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Библиотека</title>
    </head>
    <body>
        <h1>Добавление книги/h1>
            <p id="info">${info}</p>
            <p>Введите данные книги</p><br>
            <form action="addBook" method="POST">
                Имя:<br>
                <input type="text" name="name"><br>
                Автор:<br>
                <input type="text" name="author"><br>
                ISBN:<br>
                <input type="text" name="isbn"><br>
                <br>
                Логин:<br>
                <input type="text" name="login"><br>
                <br>
                Количество экземпляров:<br>
                <input type="text" name="count"><br>
                <br>

                <input id="btnAdd" type="submit" value="Добавить">
            </form>
    </body>
</html>
