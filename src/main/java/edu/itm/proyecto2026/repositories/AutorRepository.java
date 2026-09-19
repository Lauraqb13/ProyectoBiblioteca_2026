package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Autor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


@Repository
public class AutorRepository {

    public List<Autor> getAutor() {
        List<Autor> autor = new ArrayList<>();

        autor.add(Autor.builder()
                .idAutor("1")
                .nombreAutor("Howard")
                .apellidoAutor("Lovecraft")
                .nacionalidadAutor("Estadounidense")
                .fechaNacimiento(LocalDate.of(1890,8,20))
                .build()
        );

        autor.add(Autor.builder()
                .idAutor("2")
                .nombreAutor("Stephen")
                .apellidoAutor("King")
                .nacionalidadAutor("Estadounidense")
                .fechaNacimiento(LocalDate.of(1947,9,21))
                .build()
        );

        autor.add(Autor.builder()
                .idAutor("3")
                .nombreAutor("Isabel")
                .apellidoAutor("Allende")
                .nacionalidadAutor("Chilena")
                .fechaNacimiento(LocalDate.of(1942,8,2))
                .build()
        );

        return autor;
    }
}
