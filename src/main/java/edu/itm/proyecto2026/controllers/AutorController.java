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
                return new ResponseEntity<> (service.insertarAutor(autor), HttpStatus.OK);
            }else{
                return new ResponseEntity<> (autor, HttpStatus.NOT_ACCEPTABLE);
            }
        }catch(Exception excepcion){
            excepcion.printStackTrace();
            return new ResponseEntity<>(autor, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
