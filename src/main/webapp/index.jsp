<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<br/>
<form action="/upload.file" method="post" enctype="multipart/form-data">
    <input type="text" name="message"><br>
    <input type="file" name="file"><br>
    <input type="submit">
</form>
</body>
</html>