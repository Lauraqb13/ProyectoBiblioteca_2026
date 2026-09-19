package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Autor;
import edu.itm.proyecto2026.services.AutorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
