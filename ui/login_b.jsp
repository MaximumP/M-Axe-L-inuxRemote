<!DOCTYPE html>
<html>
    <head>
        <title>Remote Control</title>
        <link rel="stylesheet" type="text/css" href="ui/css/maxel.css">
        <link rel="stylesheet" type="text/css" href="ui/css/login.css">
    </head>
    <body>

    <body class="align">
      <div class="site__container">

        <div class="grid__container">

        <table>
        	<tr>
        	<td>
        	<div id="login-logo">
        		<img src="./ui/img/magpie.png" style="padding-right: 32px;"/>
        </td><td>
          <form action="Login" method="post" name="login-form" class="form form--login">

            <div class="form__field">
              <label class="fontawesome-desktop" for="login__username"><span class="hidden">Username</span></label>
              <input id="host" name="host" type="text" value="37.221.196.247" class="form__input" placeholder="Username" required>
            </div>

            <div class="form__field">
              <label class="fontawesome-user" for="login__username"><span class="hidden">Username</span></label>
              <input id="user" name="user" type="text" class="form__input" placeholder="Username" required>
            </div>

            <div class="form__field">
              <label class="fontawesome-lock" for="login__password"><span class="hidden">Password</span></label>
              <input id="password" name="password" type="password" class="form__input" placeholder="Password" required>
            </div>

            <div class="form__field">
              <input type="submit" value="Connect">
            </div>

          </form>
          </td>
          	  </tr>
          </table>

        </div>

      </div>

    </body>
      </body>
</html>