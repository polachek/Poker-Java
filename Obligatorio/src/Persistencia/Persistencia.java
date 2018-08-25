/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import modelo.Usuario;

/**
 *
 * @author docenteFI
 */
public class Persistencia {
    
    private static Persistencia instancia = new Persistencia();
    private BaseDatos base;

    public static Persistencia getInstancia() {
        return instancia;
    }
   
    private Persistencia() {
        base = BaseDatos.getInstancia();
        base.conectar("obligatoriodda", "root", "root");
    }
    private int proximoOid(){
        int oid = -1;
        ResultSet rs = base.consultar("SELECT valor from OID");
        try {
            if(rs.next()){ //hay un registro
                oid = rs.getInt("valor");
                base.modificar("UPDATE oid set valor = " + (oid+1));
            }
            else{
                System.out.println("DEBE INICIALIZAR EL OID");
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener oid:"+ ex.getMessage());
        }
        return oid;
    }
    public void guardar(Mapeador p){
        if(p.getOid()==0){
            insertar(p);
        }else modificar(p);
    }

    private void insertar(Mapeador p) {
        int oid = proximoOid();
        p.setOid(oid);
        ArrayList<String> sqls = p.getSqlInsertar();
        if(!base.transaccion(sqls)){
            System.out.println("NO SE PUDO INSERTAR EL OBJETO");
            p.setOid(0);
        }
    }

    private void modificar(Mapeador p) {
        
        ArrayList<String> sqls = p.getSqlModificar();
        if(!base.transaccion(sqls)){
            System.out.println("ERROR AL ACTUALIZAR EL OBJETO");
        }

    }
    public void borrar(Mapeador p){
        if(p.getOid()==0) return;
        ArrayList<String> sqls = p.getSqlBorrar();
        if(!base.transaccion(sqls)){
            System.out.println("ERROR AL BORRAR EL OBJETO");
        }else{
            p.setOid(0);
        }
    }
    public ArrayList<Usuario> todos(Mapeador map){
        return buscar(map,null);
    }
    public ArrayList buscar(Mapeador map,String filtro){
        ArrayList lista = new ArrayList();
        String sql = map.getSqlSelect();//
        if(filtro!=null) sql+= " WHERE " + filtro;
        ResultSet rs = base.consultar(sql);
        try {
//            Statement st = rs.getStatement();
//            if(st!= null){
//                System.out.println(st.getQueryTimeout());
//                System.out.println(st.isClosed());
//            }
            while(rs.next()){
               map.crearNuevo();
               map.setOid(rs.getInt("oid")); //EL CAMPO DEBE LLAMARSE ASI
               map.leer(rs);
               lista.add(map.getObjeto());
               //System.out.println(st.isClosed());
            }
        } catch (SQLException ex) {
            System.out.println("Error al buscar:" + ex);
            return null;
        }
        return lista;
    }
    
    
}
