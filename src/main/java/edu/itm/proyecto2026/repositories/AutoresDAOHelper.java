package edu.itm.proyecto2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class AutoresDAOHelper {
    public String listarAutores(){
        return "Select idAutor, nombreAutor, apellidoAutor, nacionalidadAutor, fechaNacimiento from autor";
    }

    public String insertarAutor(){
        return  "Insert into autor (idAutor, nombreAutor, apellidoAutor, nacionalidadAutor, fechaNacimiento) values (?,?,?,?,?)";
    }

    public String actualizarAutor(){
        return "update autor set nombreAutor=?, apellidoAutor=?, nacionalidadAutor=?, fechaNacimiento=? where idAutor=?";
    }

    public String obtenerAutorPorId(){
        return "Select idAutor, nombreAutor, apellidoAutor, nacionalidadAutor, fechaNacimiento from autor where idAutor=?";
    }

    public String eliminarAutor(){
        return "delete from autor where idAutor=?";
    }
}