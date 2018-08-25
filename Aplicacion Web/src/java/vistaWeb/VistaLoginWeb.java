/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vistaWeb;

import controlador.ControladorLoginJugador;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Participante;
import vista.ILoginJugador;

/**
 *
 * @author Polachek
 */
public class VistaLoginWeb implements ILoginJugador {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private ControladorLoginJugador controlador;
    private RequestDispatcher dispatcher;
    public VistaLoginWeb() {
        controlador = new ControladorLoginJugador(this);
    }

    @Override
    public void mostrarError(String mensaje) {
        try {
            response.sendRedirect("index.jsp?mensaje=" + mensaje);
        } catch (IOException ex) {
        }
    }

    @Override
    public void ingresar(Participante p) {
        try {
            request.getSession(true).setAttribute("participante", p);
            response.sendRedirect("inicioJugador.jsp");
           
        } catch (IOException ex) {
        } 
    }

    public void procesar(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        controlador.login(request.getParameter("usuario"),
                request.getParameter("password"));
    }

}
