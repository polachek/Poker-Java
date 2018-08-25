/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mapeadores;

import Persistencia.Mapeador;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Participante;

/**
 * @date 02-jul-2018
 * @time 19:02:52
 * @author Leonardo Pérez
 */
public class MapeadorParticipante implements Mapeador {
    
    private Participante participante;

    public MapeadorParticipante() {
    }

    public MapeadorParticipante(Participante participante) {
        this.participante = participante;
    }
    
    @Override
    public int getOid() {
        return participante.getOid();
    }

    @Override
    public void setOid(int oid) {
        participante.setOid(oid);
    }

    @Override
    public ArrayList<String> getSqlInsertar() {
        ArrayList<String> sqls = new ArrayList();
        sqls.add("INSERT INTO participaciones VALUES (" +participante.getOid()+", " + participante.getJuego().getOid() + ", " +
                participante.getJugador().getOid() + ", " + participante.getSaldoInicial() +", " +
                participante.getSaldoFinal() + ", " + participante.getTotalApostado() + ")");
        return sqls;
    }

    @Override
    public ArrayList<String> getSqlModificar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getSqlBorrar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSqlSelect() {
        return "SELECT * FROM participaciones";
    }

    @Override
    public void crearNuevo() {
        participante = new Participante();
    }

    @Override
    public void leer(ResultSet rs) throws SQLException {
        participante.setOid_Juego(rs.getInt("oid_Juego"));
        participante.setOid_Jugador(rs.getInt("oid_Jugador"));
        participante.setSaldoInicial(rs.getInt("saldoInicial"));
        participante.setSaldoFinal(rs.getInt("saldoFinal"));
        participante.setTotalApostado(rs.getInt("totalApostado"));
    }

    @Override
    public Object getObjeto() {
        return participante;
    }

}
