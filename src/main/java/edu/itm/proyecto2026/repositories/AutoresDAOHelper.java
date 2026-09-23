package edu.itm.proyecto2026.repositories;

import org.springframework.stereotype.Component;

@Component
public class AutoresDAOHelper {
    public String listarAutores(){
        return "Select id_autor, nombre_autor, apellido_autor, nacionalidad_autor, fecha_nacimiento from autor";
    }

    public String insertarAutor(){
        return  "Insert into autor (id_autor, nombre_autor, apellido_autor, nacionalidad_autor, fecha_nacimiento) values (?,?,?,?,?)";
    }

    public String actualizarAutor(){
        return "update autor set id_autor=?, nombre_autor=?, apellido_autor=?, nacionalidad_autor=?, fecha_nacimiento=? where id_autor=?";

    }
}
