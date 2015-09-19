<!DOCTYPE html>
<html>
    <head>
        <title>Remote Control</title>
        <link rel="stylesheet" type="text/css" href="ui/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="ui/css/bootstrap-theme.min.css">
    </head>
    <body>
        <div id="content">
            <form action="Login" method="post" name="login-form" class="form-horizontal">
                <div class="form-group">
                    <label for="host" class="col-sm-2 control-label">Host:</label>
                    <div class="col-sm-6">
                        <input name="host" type="text" class="form-control" placeholder="Host">
                    </div>
                </div>
                <div class="form-group">
                    <label for="user" class="col-sm-2 control-label">User:</label>
                    <div class="col-sm-6">
                        <input name="user" type="text" class="form-control" placeholder="User">
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">Password:</label>
                    <div class="col-sm-6">
                        <input name="password" type="password" class="form-control" placeholder="Password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Sign in</button>
                    </div>
                </div>
            </form>
        </div>
    </body>
</html>