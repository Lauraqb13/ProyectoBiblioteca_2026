package edu.itm.proyecto2026.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    Connection con;

    public Connection obtenerConexion() {
        try{
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca_test","root", "biblioteca2026");
        }catch (SQLException ex){
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null,ex);
            System.out.println(ex);
            ex.printStackTrace();
        }

        return con;
    }

    public static void main(String[] args) {
        Conexion connection = new Conexion();
        try{
            ResultSet r = connection.obtenerConexion().prepareStatement("select * from autor").executeQuery();
            if (r.next()){
                while (r.next()){
                    System.out.println("idAutor: "+ r.getLong("idAutor") + " nombreAutor: " + r.getString("nombreAutor") + " apellidoAutor: " + r.getString("apellidoAutor") + " nacionalidadAutor: " + r.getString("nacionalidadAutor") + " fechaNacimiento: " + r.getDate("fechaNacimiento"));
                }
            }else{
                System.out.println("NO HAY DATOS");
            }
        }catch (Exception e){
            System.out.println("#Excepcion: " + e.getMessage());
        }
    }
}