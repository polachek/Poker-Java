/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author docenteFI
 */
public class BaseDatos {
    
    private static BaseDatos instancia = new BaseDatos();
    private Connection conexion;
    private Statement stmt;

    public static BaseDatos getInstancia() {
        return instancia;
    }
    private BaseDatos() {
    }
    public void conectar(String base,String usuario,String pass){
        String driver = "com.mysql.jdbc.Driver";
        String connection = "jdbc:mysql://localhost:3306/" + base;
        try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(connection, usuario, pass);
            stmt = conexion.createStatement();
            
            System.out.println("Conectado!");
        } catch (Exception ex) {
            System.out.println("Error:" + ex.getMessage());
        }
    }
    public int modificar(String sql){
           try {
            return stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("Error al modificar:" + ex.getMessage());
            System.out.println("SQL:" + sql);
           return -1;
        }
    }
    
    public ResultSet consultar(String sql){
        try {
         
            return stmt.executeQuery(sql);
            
        } catch (SQLException ex) {
            System.out.println("Error al consultar:" + ex.getMessage());
            System.out.println("SQL:" + sql);
           return null;
        }
    }

//    public void desconectar() {
//        try {
//            conexion.close();
//        } catch (SQLException ex) {
//                
//        }
//    }

    public boolean transaccion(ArrayList<String> sqls) {
        try {
            conexion.setAutoCommit(false); //BEGIN T
            for(String sql:sqls){
                if(modificar(sql)<0){
                    throw new SQLException("Fallo");
                }
            }
            conexion.commit();
        } catch (SQLException ex) {
            System.out.println("ERROR AL EJECUTAR LA TRANSACCION:" + ex.getMessage());
            try {
                conexion.rollback();
            } catch (SQLException ex1) { System.out.println("NO ROLLBACK JAJA!!!");}
            return false;
        }finally{
            try {
                conexion.setAutoCommit(true); //END T
            } catch (SQLException ex) { }
        }
        return true;
    }
    
    
}
