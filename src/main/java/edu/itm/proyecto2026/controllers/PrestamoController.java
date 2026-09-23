package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Prestamo;
import edu.itm.proyecto2026.services.PrestamoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoServices service;

    @GetMapping("/listar")
    public ResponseEntity<List<Prestamo>> getPrestamos() {
        try {
            return new ResponseEntity<>(service.getPrestamos(), HttpStatus.OK);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Prestamo> getPrestamo(@PathVariable Integer id) {
        try {
            Prestamo prestamo = service.getPrestamo(id);
            if (prestamo != null) {
                return new ResponseEntity<>(prestamo, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // crea el préstamo y deja el ejemplar como no disponible
    @PostMapping("/nuevo")
    public ResponseEntity<Prestamo> insertarPrestamo(@RequestBody Prestamo prestamo) {
        if (ObjectUtils.isEmpty(prestamo) || ObjectUtils.isEmpty(prestamo.getIdEjemplar())
                || ObjectUtils.isEmpty(prestamo.getIdUsuario()) || ObjectUtils.isEmpty(prestamo.getIdBibliotecario())) {
            return new ResponseEntity<>(prestamo, HttpStatus.BAD_REQUEST);
        }
        try {
            Prestamo p = service.insertarPrestamo(prestamo);
            if (p != null) {
                return new ResponseEntity<>(p, HttpStatus.OK);
            }
            // p == null también indica que el ejemplar no estaba disponible para prestar
            return new ResponseEntity<>(prestamo, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(prestamo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // es lo que registra la devolución y libera el ejemplar.
    @PutMapping("/devolver/{id}")
    public ResponseEntity<Prestamo> devolverPrestamo(@PathVariable Integer id) {
        try {
            Prestamo p = service.devolverPrestamo(id);
            if (p != null) {
                return new ResponseEntity<>(p, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Prestamo> actualizarPrestamo(@RequestBody Prestamo prestamo) {
        if (ObjectUtils.isEmpty(prestamo) || ObjectUtils.isEmpty(prestamo.getIdPrestamo())) {
            return new ResponseEntity<>(prestamo, HttpStatus.BAD_REQUEST);
        }
        try {
            Prestamo p = service.actualizarPrestamo(prestamo);
            if (p != null) {
                return new ResponseEntity<>(p, HttpStatus.ACCEPTED);
            }
            return new ResponseEntity<>(prestamo, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(prestamo, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable Integer id) {
        try {
            boolean eliminado = service.eliminarPrestamo(id);
            if (eliminado) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}