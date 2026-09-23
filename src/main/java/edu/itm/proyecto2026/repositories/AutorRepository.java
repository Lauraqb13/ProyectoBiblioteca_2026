package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Autor;
import edu.itm.proyecto2026.repositories.AutoresDAOHelper;
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

        List<Autor> autores = new ArrayList<>();

        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarAutores());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                autores.add(mapearAutor(rs));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return autores;
    }

    public Autor getAutor(Integer id) {

        Autor autor = null;

        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.obtenerAutorPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                autor = mapearAutor(rs);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return autor;
    }

    public Autor insertarAutor(Autor autor) {
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

        } catch (Exception exception) {
            exception.printStackTrace();
            autor = null;
        } finally {
            cerrarConexion(con);
        }
        return autor;
    }

    public Autor actualizarAutor(Autor autor) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.actualizarAutor());
            ps.setString(1, autor.getNombreAutor());
            ps.setString(2, autor.getApellidoAutor());
            ps.setString(3, autor.getNacionalidadAutor());
            ps.setDate(4, Date.valueOf(autor.getFechaNacimiento()));
            ps.setLong(5, autor.getIdAutor());
            ps.executeUpdate();

        } catch (Exception exception) {
            exception.printStackTrace();
            autor = null;
        } finally {
            cerrarConexion(con);
        }
        return autor;
    }

    public boolean eliminarAutor(Integer id) {
        boolean eliminado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.eliminarAutor());
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            eliminado = filas > 0;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return eliminado;
    }

    private Autor mapearAutor(ResultSet rs) throws SQLException {
        return Autor.builder()
                .idAutor(rs.getLong("idAutor"))
                .nombreAutor(rs.getString("nombreAutor"))
                .apellidoAutor(rs.getString("apellidoAutor"))
                .nacionalidadAutor(rs.getString("nacionalidadAutor"))
                .fechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate())
                .build();
    }

    private void cerrarConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
}