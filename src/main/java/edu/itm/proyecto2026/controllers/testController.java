package edu.itm.proyecto2026.controllers;

import edu.itm.proyecto2026.identities.Autor;
import edu.itm.proyecto2026.services.AutorServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class testController {

    private final AutorServices autoresServices;

    public testController(AutorServices autoresServices){
        this.autoresServices = autoresServices;
    }

    @GetMapping("/autor")
    public List<Autor> getAutor(){
        return autoresServices.getAutor();
    }

}
