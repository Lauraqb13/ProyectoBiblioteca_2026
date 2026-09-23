package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Autor;
import edu.itm.proyecto2026.services.AutorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorServices service;

    @GetMapping("/listar")
    public ResponseEntity<List<Autor>> getAutores(){
        try{
            return new ResponseEntity<> (service.getAutor(), HttpStatus.OK);
        }catch(Exception excepcion){
            excepcion.printStackTrace();
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Autor> insertarAutor(@RequestBody Autor autor){
        if (ObjectUtils.isEmpty(autor) || ObjectUtils.isEmpty(autor.getNombreAutor())){
            return new ResponseEntity<>(autor,HttpStatus.BAD_REQUEST);
        }
        try{
            Autor a = service.insertarAutor(autor);
            if (a!=null){
                return new ResponseEntity<> (a, HttpStatus.OK);
            }else{
                return new ResponseEntity<> (autor, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch(Exception excepcion){
            excepcion.printStackTrace();
            return new ResponseEntity<>(autor, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/actualizar")
    public ResponseEntity<Autor> actualizarAutor(@RequestBody Autor autor){
        if (ObjectUtils.isEmpty(autor) || ObjectUtils.isEmpty(autor.getNombreAutor())
                || ObjectUtils.isEmpty(autor.getIdAutor())){
            return new ResponseEntity<>(autor,HttpStatus.BAD_REQUEST);
        }
        try{
            Autor a = service.actualizarAutor(autor);
            if (a!=null){
                return new ResponseEntity<> (a, HttpStatus.ACCEPTED);
            }else{
                return new ResponseEntity<> (autor, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch(Exception excepcion){
            excepcion.printStackTrace();
            return new ResponseEntity<>(autor, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/consultar/{id}")
    public ResponseEntity<Autor> getAutor(@PathVariable Integer id) {
        try{
            Autor autor = service.getAutor(id);
            if (autor != null){
                return new ResponseEntity<>(autor, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception excepcion){
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarAutor(@PathVariable Integer id) {
        try{
            boolean eliminado = service.eliminarAutor(id);
            if (eliminado){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch(Exception excepcion){
            excepcion.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
