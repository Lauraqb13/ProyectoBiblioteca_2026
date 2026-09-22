package edu.itm.proyecto2026.identities;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder

public class Autor {
    private Long idAutor;
    private String nombreAutor;
    private String apellidoAutor;
    private String nacionalidadAutor;
    private LocalDate fechaNacimiento;
}
