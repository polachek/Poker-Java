/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaWeb;

import controlador.ControladorJuego;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import modelo.AplicationException;
import modelo.Carta;
import modelo.Figura;
import modelo.Participante;
import vista.IVistaJuego;

/**
 *
 * @author Polachek
 */
public class vJuego implements IVistaJuego{
    private ControladorJuego controlador;
    
    private PrintWriter out;
    private HttpServletRequest request;
    
    public void inicializar() {
        Participante miP = (Participante)request.getSession(false).getAttribute("participante");
        controlador = new ControladorJuego(miP, this);
    }

    @Override
    public void mostrarSaldo(int saldoActual) {
        enviar("mostrarSaldo",saldoActual+"");
    }

    @Override
    public void actualizarCartas(Carta[] cartas) {
        enviar("nuevaMano","");
        for (int i = 0; i < cartas.length; i++) {
            String strCarta = "" + ((cartas[i].getNumero()) + 2) + cartas[i].getPaloString().substring(0, 1);
            strCarta = "assets/images/cartas/" + strCarta.toLowerCase() + ".gif";
            enviar("carta",strCarta);
        }
        enviar("aviso","Comienza una nueva mano");
    }

    @Override
    public void mostrarPozo(int pozoManoActual) {
        enviar("mostrarPozo",pozoManoActual+"");
    }

    @Override
    public void avisarQueHuboApuesta(int valor, String nombreCompletoDelJugador) {
        String mensaje = nombreCompletoDelJugador + " ha apostado $" + valor;
        enviar("aviso",mensaje);
        enviar("huboApuesta","");       
    }

    @Override
    public void mostrarGanador(Participante p) {
        String ganador = p.getNombreCompletoDelJugador();
        String pozo = String.valueOf(p.getUltimaMano().getPozo().getValor());
        String juegoGanador = p.getMisCartas().getClass().getName()+" de " +p.getMisCartas().getNombre();
        String mensajeWeb = "<h1>"+ganador+" ha ganado la mano<h1><h2>Juego ganador: "+juegoGanador+"</h2><p>Pozo acumulado: $" + pozo + "<br /></p>";

        enviar("aviso","Mano terminada");
        enviar("aviso",ganador+" ha ganado la mano con "+juegoGanador+" Se ha llevado un pozo de $" + pozo);
        enviar("confirmarProximaMano",mensajeWeb);
    }

    @Override
    public void limpiar() {
        enviar("limpiar","limpiar");
    }

    @Override
    public void avisarQuePase() {
        enviar("aviso","Has pasado la apuesta");
    }

    @Override
    public void mostrarLosQuePasaron(ArrayList<Participante> losQuepasan) {
        String pasaron = "Pasa/n la apuesta";
        mostrarAvisoDeParticipantes(losQuepasan, pasaron);
    }
    
    private void mostrarAvisoDeParticipantes(ArrayList<Participante> losQuepasan, String pasaron) {
        for (Participante p : losQuepasan) {
            pasaron += " " + p.getNombreCompletoDelJugador();
        }
        
        enviar("aviso",pasaron);
    }

    @Override
    public void mostrarLosQueDejanLamano(ArrayList<Participante> losQueSeFueron) {
        String pasaron = "Deja/n la mano ";
        mostrarAvisoDeParticipantes(losQueSeFueron, pasaron);
    }

    @Override
    public void seTerminoMiPartida(String mensaje, String titulo) {
        enviar("seTerminoMiPartida",titulo+" "+mensaje);
    }

    @Override
    public void felicitarGanadorPartida(Participante p) {
        String ganador = p.getNombreCompletoDelJugador();
        String pozo = String.valueOf(p.getJuego().getPozo().getValor());
        String mensaje = ganador + " has ganado la Partida!!! <br/> Pozo acumulado: $" + pozo;
        enviar("felicitarGanadorPartida",mensaje);
    }

    @Override
    public void mostrarLomodeLasCartasYDesactivarBotones() {
        String strCarta = "assets/images/cartas/Invertida.gif";
        enviar("darVueltaCartas",strCarta);
    }

    @Override
    public void mostrarSaludo(String nombreCompletoDelJugador) {
        enviar("mostrarSaludo",nombreCompletoDelJugador.toUpperCase());
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        enviar("errormensaje",mensaje);
    }
    
    public void enviar(String evento, String dato) {
        out.write("event: " + evento + "\n");
        dato = dato.replace("\n", "");
        out.write("data: " + dato + "\n\n");
        if (out.checkError()) {//checkError llama a flush, si da false evio bien
            System.out.println("Falló Envío");            
        } else {
            //TODO OK!
             //System.out.println("Enviado");
        }
    }
    
    public void conectarSSE(HttpServletRequest request) throws IOException {
        
        request.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        AsyncContext contexto = request.startAsync();
        this.request = (HttpServletRequest)contexto.getRequest();
        contexto.getResponse().setContentType("text/event-stream");
        contexto.getResponse().setCharacterEncoding("UTF-8");
        contexto.setTimeout(0);//SIN TIMEOUT
        out = contexto.getResponse().getWriter();
        
    }
    
    public void procesar(HttpServletRequest request,String accion) throws AplicationException{
        switch(accion){
            case "aposte" : apostar(request);break;
            case "paso" : pasar();break;
            case "retiroMano" : retirar();break;
            case "pagarApuesta" : pagarApuesta();break;
            case "jugarMano" : confirmar();break;
            case "retirarJuego" : dejarPartida();break;  
            //case "salir" : controlador.salir();break;
        }
    }
    
    private void pasar() {
        this.controlador.pasar();
    }
    
    private void retirar() {
        this.controlador.retirar();
    }
    
    private void dejarPartida() {
        this.controlador.dejarPartida();
    }
    
    private void confirmar() throws AplicationException {
        this.controlador.confirmar();
    }
    
    private void apostar(HttpServletRequest request) {
        try {
            int valorApuesta = Integer.parseInt(request.getParameter("apue"));
            this.controlador.apostar(valorApuesta);
        } catch (Exception e) {
            /*UtilidadesVista u = new UtilidadesVista();
            u.mostrarError(this, e.getMessage());*/
        }
    }
    
    private void pagarApuesta() {
        try {
            this.controlador.pagarApuesta();
        } catch (Exception e) {
           // mostrarMensajeError(e.getMessage());
        }
    }

    @Override
    public void actualizarContador(String mensaje) {
        enviar("actualizarContador",mensaje);
    }

    @Override
    public void mostrarNombreFigura(Figura misCartas) {
        String aviso = formatearNombreFigura(misCartas);
        enviar("mostrarNomFigura",aviso);
    }
    
    private String formatearNombreFigura(Figura misCartas) {
        String nombreFig =misCartas.getClass().getName();
        String[] nomDiv =nombreFig.split("[.]");
        String nombre =nomDiv[1];
        String aviso = nombre + " de " + misCartas.getNombre();
        return aviso;
    }

    
}
