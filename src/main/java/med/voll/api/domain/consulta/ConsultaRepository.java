package med.voll.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Con esta interface se podra hacer todito el proceso de gestion con la base de datos a nivel del CRUD
 * Se realiza automaticamente porque se Extiende de JpaRepository
 * JpaRepository necesita de dos parametros
 *  1ro: El tipo de objeto que se va a guardar aqui, el tipo de entidad con el que se va a trabajar en este repositorio
 *  2do: El tipo de objeto del id
 */

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
//    Page<Consulta> findByActivoTrue(Pageable pageable);
}
