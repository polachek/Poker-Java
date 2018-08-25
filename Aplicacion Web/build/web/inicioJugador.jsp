<%-- 
    Document   : inicioJugador
    Created on : 01-jul-2018, 23:12:22
    Author     : Polachek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles/styles.css">
        <title>Poker ORT Nigh - Inicio Jugador</title>    
    </head>
    
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript">
            var vistaWeb = new EventSource("inicioJugador?accion=new");
            
            vistaWeb.onerror = function(evento) {
               alert("Sin conexion con el servidor o UnsupportedOperationException");
               // vistaWeb.close();
                //document.location="/";
            };
            
            //Mostrar Saludo
            vistaWeb.addEventListener("mostrarSaludo", function (evento){
                document.getElementById("usuario").innerHTML=evento.data;
                
            },false);
            
            //Actualizar Jugadores que Faltan
            vistaWeb.addEventListener("actualizarCuantosFaltan", function (evento){
                document.getElementById("cantJug").innerHTML=evento.data;
                
            },false);
            
            //Iniciar Juego
            vistaWeb.addEventListener("iniciarJuego", function (evento){
                window.location.replace("juego.jsp");
            },false);
            
            function abandonarEspera(){
                $.get("inicioJugador?accion=abandono", function (data) { 
                    window.location.replace("index.jsp");
                });
            }
          
    </script>
    
    <body class="iniJugador">
        <h1>¡¡ Bienvenido <span id="usuario"></span> !!</h1>
        <h2 class="txtEspJug">Estamos a la espera de <span id="cantJug"></span> jugadores para iniciar la partida</h2>
        <a href="#" onclick="abandonarEspera();return false;">Abandonar espera</a>
    </body>
</html>
