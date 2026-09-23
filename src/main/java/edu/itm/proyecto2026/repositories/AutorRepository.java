package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Autor;
import edu.itm.proyecto2026.services.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

//nuevos imports
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class AutorRepository {

    @Autowired
    private AutoresDAOHelper helper;

    public List<Autor> getAutor() {

        List<Autor> autor = new ArrayList<>();

        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();


        try {
            PreparedStatement ps = con.prepareStatement(helper.listarAutores());
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Autor autor1 = Autor.builder()
                        .idAutor(rs.getLong("id_autor"))
                        .nombreAutor(rs.getString("nombre_autor"))
                        .apellidoAutor(rs.getString("apellido_autor"))
                        .nacionalidadAutor(rs.getString("nacionalidad_autor"))
                        .fechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate())
                        .build();
                autor.add(autor1);
            }
        }catch (SQLException sqlException){
            sqlException.printStackTrace();

        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            try {
                con.close();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
        return autor;
    }


    public Autor insertarAutor(Autor autor){
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.insertarAutor());
            ps.setLong(1, autor.getIdAutor());
            ps.setString(2, autor.getNombreAutor());
            ps.setString(3, autor.getApellidoAutor());
            ps.setString(4, autor.getNacionalidadAutor());
            ps.setDate(5, Date.valueOf(autor.getFechaNacimiento()));
            ps.executeUpdate();

        }catch (Exception exception){
            exception.printStackTrace();
            autor = null;
        }finally {
            try {
                con.close();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
        return  autor;
    }

public Autor actualizarAutor(Autor autor){
    Conexion conexion = new Conexion();
    Connection con = conexion.obtenerConexion();

    try {
        PreparedStatement ps = con.prepareStatement(helper.actualizarAutor());
        ps.setLong(1, autor.getIdAutor());
        ps.setString(2, autor.getNombreAutor());
        ps.setString(3, autor.getApellidoAutor());
        ps.setString(4, autor.getNacionalidadAutor());
        ps.setDate(5, Date.valueOf(autor.getFechaNacimiento());
        ps.executeUpdate();

    }catch (Exception exception){
        exception.printStackTrace();
        autor = null;
    }finally {
        try {
            con.close();
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
        return  autor;
    }

    public Autor_getAutor()
             /*   String sql = "SELECT id_autor, nombre_autor, apellido_autor, nacionalidad_autor, fecha_nacimiento FROM autor";*/
}
