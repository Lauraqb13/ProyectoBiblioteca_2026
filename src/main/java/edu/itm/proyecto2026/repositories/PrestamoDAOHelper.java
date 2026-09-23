package edu.itm.proyecto2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class PrestamoDAOHelper {

    public String listarPrestamos() {
        return "Select idPrestamo, idEjemplar, idUsuario, idBibliotecario, estadoPrestamo, " +
                "fechaPrestamo, fechaDevolucionPrevista, fechaDevolucionReal from Prestamo";
    }

    public String obtenerPrestamoPorId() {
        return "Select idPrestamo, idEjemplar, idUsuario, idBibliotecario, estadoPrestamo, " +
                "fechaPrestamo, fechaDevolucionPrevista, fechaDevolucionReal from Prestamo where idPrestamo=?";
    }

    public String insertarPrestamo() {
        return "Insert into Prestamo (idEjemplar, idUsuario, idBibliotecario, estadoPrestamo, " +
                "fechaPrestamo, fechaDevolucionPrevista) values (?,?,?,?,?,?)";
    }

    public String actualizarPrestamo() {
        return "update Prestamo set estadoPrestamo=?, fechaDevolucionReal=? where idPrestamo=?";
    }

    public String eliminarPrestamo() {
        return "delete from Prestamo where idPrestamo=?";
    }

    // Al prestar/devolver un ejemplar, su disponibilidad cambia.
    public String actualizarDisponibilidadEjemplar() {
        return "update Ejemplar set disponibilidadEjemplar=? where idEjemplar=?";
    }

    public String consultarDisponibilidadEjemplar() {
        return "select disponibilidadEjemplar from Ejemplar where idEjemplar=?";
    }
}

