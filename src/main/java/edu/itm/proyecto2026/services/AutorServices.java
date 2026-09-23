package edu.itm.proyecto2026.services;

import edu.itm.proyecto2026.identities.Autor;
import edu.itm.proyecto2026.repositories.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorServices {
    private final AutorRepository autorRepository;

    public AutorServices(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public List<Autor> getAutor() {
        return autorRepository.getAutor();
    }

    public Autor insertarAutor(Autor autor){
        return autorRepository.insertarAutor(autor);
    }

    public Autor buscarAutor(Long idAutor) {
        return autorRepository.buscarAutor(idAutor);
    }

    public Autor actualizarAutor(Long idAutor, Autor autor) {
        return autorRepository.actualizarAutor(idAutor, autor);
    }

    public boolean eliminarAutor(Long idAutor) {
        return autorRepository.eliminarAutor(idAutor);
    }
}
