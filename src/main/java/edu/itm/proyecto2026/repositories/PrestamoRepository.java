package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Prestamo;
import edu.itm.proyecto2026.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PrestamoRepository {

    @Autowired
    private PrestamoDAOHelper helper;

    public List<Prestamo> getPrestamos() {
        List<Prestamo> prestamos = new ArrayList<>();

        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.listarPrestamos());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                prestamos.add(mapearPrestamo(rs));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return prestamos;
    }

    public Prestamo getPrestamo(Integer id) {
        Prestamo prestamo = null;

        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.obtenerPrestamoPorId());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                prestamo = mapearPrestamo(rs);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            cerrarConexion(con);
        }
        return prestamo;
    }

    /** Crear un préstamo solo si el ejemplar está disponible,
     * y  marca el ejemplar como no disponible, se hacenen una sola
     * transacción para que la base no quede inconsistente
     */
    public Prestamo insertarPrestamo(Prestamo prestamo) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            con.setAutoCommit(false);

            if (!ejemplarDisponible(con, prestamo.getIdEjemplar())) {
                con.rollback();
                return null;
            }

            PreparedStatement ps = con.prepareStatement(helper.insertarPrestamo(), Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, prestamo.getIdEjemplar());
            ps.setLong(2, prestamo.getIdUsuario());
            ps.setLong(3, prestamo.getIdBibliotecario());
            ps.setString(4, "activo");
            ps.setDate(5, Date.valueOf(prestamo.getFechaPrestamo()));
            ps.setDate(6, Date.valueOf(prestamo.getFechaDevolucionPrevista()));
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                prestamo.setIdPrestamo(keys.getLong(1));
            }
            prestamo.setEstadoPrestamo("activo");

            actualizarDisponibilidad(con, prestamo.getIdEjemplar(), "no");

            con.commit();
        } catch (Exception exception) {
            exception.printStackTrace();
            rollback(con);
            prestamo = null;
        } finally {
            restaurarAutoCommit(con);
            cerrarConexion(con);
        }
        return prestamo;
    }

    /**
     * Devuelve un préstamo, pues el préstamo como devuelto
     * y libera el ejemplar (disponibilidadEjemplar='si') en la misma transacción.
     */
    public Prestamo devolverPrestamo(Integer idPrestamo) {
        Prestamo prestamo = getPrestamo(idPrestamo);
        if (prestamo == null) {
            return null;
        }

        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement(helper.actualizarPrestamo());
            ps.setString(1, "devuelto");
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setLong(3, prestamo.getIdPrestamo());
            ps.executeUpdate();

            actualizarDisponibilidad(con, prestamo.getIdEjemplar(), "si");

            con.commit();

            prestamo.setEstadoPrestamo("devuelto");
            prestamo.setFechaDevolucionReal(LocalDate.now());
        } catch (Exception exception) {
            exception.printStackTrace();
            rollback(con);
            prestamo = null;
        } finally {
            restaurarAutoCommit(con);
            cerrarConexion(con);
        }
        return prestamo;
    }

    /**
     * Update  del CRUD (para corregir el estado o la fecha
     * de devolución de un préstamo sin pasar por el flujo de negocio de devolución, etc).
     */
    public Prestamo actualizarPrestamo(Prestamo prestamo) {
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.actualizarPrestamo());
            ps.setString(1, prestamo.getEstadoPrestamo());
            if (prestamo.getFechaDevolucionReal() != null) {
                ps.setDate(2, Date.valueOf(prestamo.getFechaDevolucionReal()));
            } else {
                ps.setNull(2, Types.DATE);
            }
            ps.setLong(3, prestamo.getIdPrestamo());
            ps.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
            prestamo = null;
        } finally {
            cerrarConexion(con);
        }
        return prestamo;
    }

    public boolean eliminarPrestamo(Integer id) {
        boolean eliminado = false;
        Conexion conexion = new Conexion();
        Connection con = conexion.obtenerConexion();

        try {
            PreparedStatement ps = con.prepareStatement(helper.eliminarPrestamo());
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

    private boolean ejemplarDisponible(Connection con, Long idEjemplar) throws SQLException {
        PreparedStatement ps = con.prepareStatement(helper.consultarDisponibilidadEjemplar());
        ps.setLong(1, idEjemplar);
        ResultSet rs = ps.executeQuery();
        return rs.next() && "si".equalsIgnoreCase(rs.getString("disponibilidadEjemplar"));
    }

    private void actualizarDisponibilidad(Connection con, Long idEjemplar, String disponibilidad) throws SQLException {
        PreparedStatement ps = con.prepareStatement(helper.actualizarDisponibilidadEjemplar());
        ps.setString(1, disponibilidad);
        ps.setLong(2, idEjemplar);
        ps.executeUpdate();
    }

    private Prestamo mapearPrestamo(ResultSet rs) throws SQLException {
        Date fechaDevolucionReal = rs.getDate("fechaDevolucionReal");
        return Prestamo.builder()
                .idPrestamo(rs.getLong("idPrestamo"))
                .idEjemplar(rs.getLong("idEjemplar"))
                .idUsuario(rs.getLong("idUsuario"))
                .idBibliotecario(rs.getLong("idBibliotecario"))
                .estadoPrestamo(rs.getString("estadoPrestamo"))
                .fechaPrestamo(rs.getDate("fechaPrestamo").toLocalDate())
                .fechaDevolucionPrevista(rs.getDate("fechaDevolucionPrevista").toLocalDate())
                .fechaDevolucionReal(fechaDevolucionReal != null ? fechaDevolucionReal.toLocalDate() : null)
                .build();
    }

    private void rollback(Connection con) {
        if (con != null) {
            try {
                con.rollback();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    private void restaurarAutoCommit(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
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