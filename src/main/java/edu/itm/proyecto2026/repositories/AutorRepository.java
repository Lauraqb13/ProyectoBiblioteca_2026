package edu.itm.proyecto2026.repositories;

import edu.itm.proyecto2026.identities.Autor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

//nuevos imports
import org.springframework.jdbc.core.JdbcTemplate;
import java.sql.ResultSet;
import java.sql.SQLException;
@Repository
public class AutorRepository {

    private final JdbcTemplate  jdbcTemplate;
    public AutorRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Autor> getAutor() {
        // List<Autor> autor = new ArrayList<>();
                String sql = "SELECT id_autor, nombre_autor, apellido_autor, nacionalidad_autor, fecha_nacimiento FROM autor";
                return jdbcTemplate.query(sql, this::mapRowToAutor);
    }

    private Autor mapRowToAutor(ResultSet rs, int rowNum) throws SQLException {
    return Autor.builder()
            .idAutor(rs.getLong("id_autor"))
            .nombreAutor(rs.getString("nombre_autor"))
            .apellidoAutor(rs.getString("apellido_autor"))
            .nacionalidadAutor(rs.getString("nacionalidad_autor"))
            .fechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate())
            .build();
}
}
