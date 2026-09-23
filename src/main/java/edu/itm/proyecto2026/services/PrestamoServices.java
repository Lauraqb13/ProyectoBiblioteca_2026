package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Prestamo;
import edu.itm.proyecto2026.repositories.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoServices {

    private final PrestamoRepository prestamoRepository;

    public PrestamoServices(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public List<Prestamo> getPrestamos() {
        return prestamoRepository.getPrestamos();
    }

    public Prestamo getPrestamo(Integer id) {
        return prestamoRepository.getPrestamo(id);
    }

    public Prestamo insertarPrestamo(Prestamo prestamo) {
        return prestamoRepository.insertarPrestamo(prestamo);
    }

    public Prestamo devolverPrestamo(Integer id) {
        return prestamoRepository.devolverPrestamo(id);
    }

    public Prestamo actualizarPrestamo(Prestamo prestamo) {
        return prestamoRepository.actualizarPrestamo(prestamo);
    }

    public boolean eliminarPrestamo(Integer id) {
        return prestamoRepository.eliminarPrestamo(id);
    }
}
