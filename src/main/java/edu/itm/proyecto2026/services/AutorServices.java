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
}
