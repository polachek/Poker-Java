/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaWeb;

import controlador.ControladorInicioJugador;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.AplicationException;
import modelo.Participante;
import vista.IVistaInicioJugador;

/**
 *
 * @author Polachek
 */
public class vInicioJugador implements IVistaInicioJugador{
    
    private ControladorInicioJugador controlador;
    private PrintWriter out;
    private HttpServletRequest request;
    private HttpServletResponse response;
    
    public void inicializar() throws AplicationException {
        Participante miP = (Participante)request.getSession(false).getAttribute("participante");
        controlador = new ControladorInicioJugador(this, miP);
        controlador.revisaSiInicia();
    }

    @Override
    public void actualizarCuantosFaltan(int cuantosJugadoresFaltan) {
        enviar("actualizarCuantosFaltan",cuantosJugadoresFaltan+"");
    }

    @Override
    public void iniciarJuego(Participante participante) {
        System.out.println("VIJug iniciar Juego");
        System.out.println("Me llega Participante: "+participante.getNombreCompletoDelJugador());
        enviar("iniciarJuego","url");
    }

    @Override
    public void abandonarEspera() {
        controlador.abandonarEspera();
    }

    @Override
    public void mostrarSaludo(String nombreCompletoDelJugador) {
        enviar("mostrarSaludo",nombreCompletoDelJugador.toUpperCase());
    }

    @Override
    public void mostrarMensajeError(String mensaje) {
        enviar("MError",mensaje);
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
            case "abandono" : abandonarEspera();break;
        }
    }

    
    
}
