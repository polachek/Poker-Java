<%-- 
    Document   : juego
    Created on : 02-jul-2018, 16:55:57
    Author     : Polachek
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles/styles.css">
        <title>JSP Page</title>
    </head>
    
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script type="text/javascript">
            var vistaWeb = new EventSource("juego?accion=new");
            
            $(function(){
                var x = 0;
                setInterval(function(){
                    $('#header').css('background-position','0'+--x + 'px');
                }, 40);
            });
            
            vistaWeb.onerror = function(evento) {
               alert("Sin conexion con el servidor o UnsupportedOperationException");
                vistaWeb.close();
                document.location="index.jsp";
            };
            
            //Mostrar Saludo
            vistaWeb.addEventListener("mostrarSaludo", function (evento){
                document.getElementById("usuario").innerHTML=evento.data;
                
            },false);
            
            //Mostrar Saldo
            vistaWeb.addEventListener("mostrarSaldo", function (evento){
                document.getElementById("saldoAct").innerHTML=evento.data;
                
            },false);
            
            //Mostrar Pozo
            vistaWeb.addEventListener("mostrarPozo", function (evento){
                document.getElementById("pozo").innerHTML=evento.data;
                
            },false);
            
            //Mostrar cartas
            var x = 1;

            vistaWeb.addEventListener("carta", function (evento){

                setTimeout(function() {
                   $("#carta"+x).attr("src", evento.data);     
                    $("#carta"+x).parent().parent().toggleClass( "is-flipped" );               
                    if(x===5)x=0;
                    x++;     
                }, 1000);
                          
            },false);          
            
            //Apostar
            function apostar(){
                var apue = $("#valorApuesta").val();
                
                if(isNaN(apue)||apue.length === 0){
                    limpiarMensajesEmergentes();
                    $("#msjError #errorData").show();
                    $("#msjError #msjErrorData").html("La apuesta debe ser un Número");
                    $("#msjError").show();
                 }else{
                    var apueN = parseInt(apue);
                    if(apueN===0){
                        $("#msjError #errorData").show();
                        $("#msjError #msjErrorData").html("La apuesta debe ser un Número mayor a 0");
                        $("#msjError").show();
                    }else{
                        $.get("juego?accion=aposte&apue=" + apue, function (data) {                 
                        });
                    }
                }        
                
            }
            
            //Pasar
            function pasar(){
                $.get("juego?accion=paso", function (data) {                 
                });
            }
            
            //Retirarse de mano
            function retirarMano(){
                $.get("juego?accion=retiroMano", function (data) {                 
                });
            }
            
            //Apostar
            function pagarApuesta(){
                $.get("juego?accion=pagarApuesta", function (data) {                 
                });
            }
            
            //Mensajes
            vistaWeb.addEventListener("aviso", function (evento){
                $( "#mensajes .mensajes" ).append( "<p>"+evento.data+"</p>" );
                $("#mensajes .mensajes").scrollTop($("#mensajes .mensajes").prop("scrollHeight"));
            },false);
            
            //Hubo Apuesta
            vistaWeb.addEventListener("huboApuesta", function (evento){
                $("#acciones #apostar").hide();
                $("#acciones #pasar").hide();
                $("#acciones #pagarapuesta").show();
                $("#acciones #retirarMano").show();
            },false);
            
            //Limpiar campos
            vistaWeb.addEventListener("limpiar", function (evento){
                $(".cartas .carta-wrapper").toggleClass( "is-flipped" );
                $("#acciones #apostar").show();
                $("#acciones #pasar").show();
                $("#acciones #pagarapuesta").hide();
                $("#acciones #retirarMano").hide();
                $(".pozo #contador").html("");
               
                $('#valorApuesta').val('');
            },false);
            
            //Dar vuelta cartas
            vistaWeb.addEventListener("darVueltaCartas", function (evento){

            },false);
            
            //Error
            vistaWeb.addEventListener("errormensaje", function (evento){
                limpiarMensajesEmergentes();
                $("#msjError #errorData").show();
                $("#msjError #msjErrorData").html(evento.data);
                $("#msjError").show();
            },false);
            
            //Cerrar Error
            function cerrarError(){
                $("#msjError").hide();
                $("#msjError #msjErrorData").html();
            }
            
            function limpiarMensajesEmergentes(){
                $("#msjError #errorData").hide();
                $("#msjError #ganadorPartida").hide();
                $("#msjError #seRetiraDPartida").hide();               
            }
            
            
            //Termino Mi Partida
            vistaWeb.addEventListener("seTerminoMiPartida", function (evento){
                limpiarMensajesEmergentes();
                $("#msjError #seRetiraDPartida").show();
                $("#msjError #seRetiraDPartida #msj").html(evento.data);
                $("#msjError").show();
            },false);
            
            function reLoguin(){
                window.location.replace("index.jsp");
            }
            
            //Felicitar Ganador de Partida
            vistaWeb.addEventListener("felicitarGanadorPartida", function (evento){
                limpiarMensajesEmergentes();
                $("#msjError #ganadorPartida").show();
                $("#msjError #ganadorPartida #msj").html(evento.data);
                $("#msjError").show();
            },false);
            
            function reLoguin(){
                window.location.replace("index.jsp");
            }
            
            function reIniJugador(){
                window.location.replace("inicioJugador.jsp");
            }
            
            //Confirmar para Proxima Mano
            vistaWeb.addEventListener("confirmarProximaMano", function (evento){
                 $("#conProxMano .mensaje").html(evento.data);
                 $("#confirmado").hide();
                 $("#conProxMano .confirmacion").show();
                $("#conProxMano").show();
            },false);
            
            //Jugar Proxima Mano
            function confirmarProxMano(){
                $("#conProxMano .confirmacion").hide();
                $("#conProxMano .confirmado").show();
                $.get("juego?accion=jugarMano", function (data) {                 
                });
            }
            
            //Limpia confirmacion en nueva mano
            vistaWeb.addEventListener("nuevaMano", function (evento){
                $("#conProxMano").hide();
         
            },false);
            
            //Retirarse Juego
            function retirar(){
                $.get("juego?accion=retirarJuego", function (data) {
                    $("#msjError #errorData").hide();
                    $("#msjError #seRetiraDPartida").show();
                    $("#msjError #seRetiraDPartida #msj").html("Te retiras de la Partida");
                    $("#msjError").show();
                });
            }
            
            //Actualizar Contador
            vistaWeb.addEventListener("actualizarContador", function (evento){
                $(".pozo #contador").html(evento.data);
         
            },false);
            
            //MostrarNomFigura
            vistaWeb.addEventListener("mostrarNomFigura", function (evento){
                $("#area-juego #juegoCartas #figura").html(evento.data);
         
            },false);

            
            
    </script>    
    
    <body class="juego">
        <div id="header">
            <h1>POKER ORT NIGHT</h1>            
        </div>
        
        <div class="pozo">
                <h2>Pozo actual: $<span id="pozo"></span></h2>
                <h3 id="contador"></h3>
        </div>
        <section id="area-juego"> 
            <div id="conProxMano" style="display:none">
                <div class="mensaje"></div>
                <div class="confirmacion">
                    <p>¿Desea jugar una nueva mano?</p>
                    <a id="btnConfProxMano" class="btnConf" href="#" onclick="confirmarProxMano();return false;">Jugar</a>
                    <a id="btnAbandonar" class="btnConf" href="#" onclick="retirar();return false;">Retirarse</a>
                </div>
                <div class="confirmado" style="display:none">
                    <p>Confirmado para proxima mano, esperando otros jugadores...</p>
                </div>
            </div>
            <h1 id="juegoCartas">Juego: <span id="figura"></span></h1>
            <div class="cartas">
                <div class="carta-wrapper">
                    <div class=""><img src="assets/images/cartas/Invertida.gif" /></div>
                    <div class="card-back"><img id="carta1" src="" /></div>                    
                </div>
                <div class="carta-wrapper">
                    <div class=""><img src="assets/images/cartas/Invertida.gif" /></div>
                    <div class="card-back"><img id="carta2" src="" /></div>                    
                </div>
                <div class="carta-wrapper">
                    <div class=""><img src="assets/images/cartas/Invertida.gif" /></div>
                    <div class="card-back"><img id="carta3" src="" /></div>                    
                </div>
                <div class="carta-wrapper">
                    <div class=""><img src="assets/images/cartas/Invertida.gif" /></div>
                    <div class="card-back"><img id="carta4" src="" /></div>                    
                </div>
                <div class="carta-wrapper">
                    <div class=""><img src="assets/images/cartas/Invertida.gif" /></div>
                    <div class="card-back"><img id="carta5" src="" /></div>                    
                </div>
            </div>
        </section>
        
        
        <section id="panelJugador">
            <div id="infoJug">
                <h3 id="usuario"></h3>
                <p>Saldo actual: $<span id="saldoAct"></span></p>
            </div>
            
            <div id="acciones">
                <a class="btnaccion btnapuestas" id="apostar" href="" onclick="apostar();return false;">Apostar</a>
                <a style="display:none" class="btnaccion btnapuestas" id="pagarapuesta" href="#" onclick="pagarApuesta();return false;">Pagar apuesta</a>
                <input id="valorApuesta" type="text" placeholder="0"/><br />
                <a class="btnaccion btnPasarRetirar" id="pasar" href="" onclick="pasar();return false;">Pasar</a>  
                <a style="display:none" class="btnaccion btnPasarRetirar" id="retirarMano" href="" onclick="retirarMano();return false;">Retirarse de la mano</a> 
                <a class="btnaccion" id="salir" href="" onclick="retirar();return false;">Salir</a>  
            </div>
        </section>
        
        <div id="mensajes"><h1>Mensajes</h1><div class="mensajes"></div></div>
        
        <div id="msjError" class="mensajeEmergente" style="display:none;">
            <div id="errorData" style="display:none" class="msjEmeData"><div id="msjErrorData"></div><a href="#" onclick="cerrarError();return false;">ACEPTAR</a></div>
            <div id="ganadorPartida" style="display:none" class="msjEmeData"><div id="msj"></div><a href="#" onclick="reLoguin();return false;">Jugar otra Partida</a><a href="#" onclick="reLoguin();return false;">SALIR</a></div>
            <div id="seRetiraDPartida" style="display:none" class="msjEmeData"><div id="msj"></div><a href="#" onclick="reLoguin();return false;">ACEPTAR</a></div>
        </div>
    </body>
</html>
