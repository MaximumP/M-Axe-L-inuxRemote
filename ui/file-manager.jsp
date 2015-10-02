<%@ page import="java.util.List" %>
<%@ page import="net.schmizz.sshj.sftp.RemoteResourceInfo;" %>

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
    <a href="/maxel/FileManager?cd='..'">..</a>
    <%  List<RemoteResourceInfo> directories = (List<RemoteResourceInfo>) request.getAttribute("directories");
        for (RemoteResourceInfo content: directories) {
            if (content.isDirectory()) {
    %>
            <div>
                <a href="/maxel/FileManager?cd=<%=content.getName()%>">
                    <img src="ui/img/folder.svg" alt="folder icon" height="42" width="42" />
                    <br>
                    <%=content.getName()%>
                </a>
            </div>
            <%} else {%>
                <a href="/maxel/FileManager?cd=<%=content.getName()%>">
                    <img src="ui/img/file.svg" alt="file icon" height="42" width="42" />
                    <%=content.getName()%>
                </a>
            <%}%>
    <%}%>
</div>
</body>
</html>