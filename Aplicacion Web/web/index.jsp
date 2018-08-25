<%-- 
    Document   : index
    Created on : 01-jul-2018, 16:43:55
    Author     : Polachek
--%>
<%
    String mensaje = request.getParameter("mensaje");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles/styles.css">
        <title>Poker ORT Night</title>
    </head>
    <body class="login">
        
        <video autoplay muted loop id="myVideo">
            <source src="assets/media/poker-video.mp4" type="video/mp4">
        </video>

	<h1 class="title">POKER ORT NIGHT</h1>
	
	<section id="login">
		<h2>LOGIN</h2>
		<form method="get" action="login">
			<input class="input-login" type="text" name="usuario" placeholder="Usuario"></input>
			<br>
			<input class="input-login" type="password" name="password" placeholder="Contrasena"></input>
			<br>
			<input type="submit" id="submit" value="Ingresar"> </input>
                        <p class="login-error">
                        <%
                            if(mensaje!=null){
                                out.println("Error: " + mensaje);
                            }
                        %>
                        </p>
		</form>
	</section>
    </body>
</html>
