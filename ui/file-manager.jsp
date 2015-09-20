<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
    <title>Remote Control</title>
    <link rel="stylesheet" type="text/css" href="ui/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="ui/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="ui/css/maxel.css">
</head>
<body>
<%@include file='navbar.jsp'%>
<div class="content">
    <%  ArrayList<String> directories = (ArrayList<String>) request.getAttribute("directories");
        for (String content: directories) {
    %>
    <div>
        <a href="/maxel/FileManager?cd=<%=content%>"><%=content%></a>
    </div>
    <%}%>
</div>
</body>
</html>