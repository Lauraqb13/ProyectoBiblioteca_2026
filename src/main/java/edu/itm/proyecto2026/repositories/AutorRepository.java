package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Autor;
import edu.itm.proyecto2026.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public Autor buscarAutor(Long idAutor) {
        Conexion conexion = new Conexion();
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.buscarAutor())) {
            ps.setLong(1, idAutor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearAutor(rs);
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public Autor actualizarAutor(Long idAutor, Autor autor) {
        Conexion conexion = new Conexion();
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.actualizarAutor())) {
            ps.setString(1, autor.getNombreAutor());
            ps.setString(2, autor.getApellidoAutor());
            ps.setString(3, autor.getNacionalidadAutor());
            ps.setDate(4, Date.valueOf(autor.getFechaNacimiento()));
            ps.setLong(5, idAutor);

            if (ps.executeUpdate() == 1) {
                autor.setIdAutor(idAutor);
                return autor;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public boolean eliminarAutor(Long idAutor) {
        Conexion conexion = new Conexion();
        try (Connection con = conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(helper.eliminarAutor())) {
            ps.setLong(1, idAutor);
            return ps.executeUpdate() == 1;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private Autor mapearAutor(ResultSet rs) throws SQLException {
        return Autor.builder()
                .idAutor(rs.getLong("id_autor"))
                .nombreAutor(rs.getString("nombre_autor"))
                .apellidoAutor(rs.getString("apellido_autor"))
                .nacionalidadAutor(rs.getString("nacionalidad_autor"))
                .fechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate())
                .build();
    }

             /*   String sql = "SELECT id_autor, nombre_autor, apellido_autor, nacionalidad_autor, fecha_nacimiento FROM autor";*/
}
