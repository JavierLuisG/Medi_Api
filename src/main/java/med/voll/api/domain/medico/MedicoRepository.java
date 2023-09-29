package med.voll.api.domain.medico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * Con esta interface se podra hacer todito el proceso de gestion con la base de datos a nivel del CRUD
 * Se realiza automaticamente porque se Extiende de JpaRepository
 * JpaRepository necesita de dos parametros
 *  1ro: El tipo de objeto que se va a guardar aqui, el tipo de entidad con el que se va a trabajar en este repositorio
 *  2do: El tipo de objeto del id
 */
@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable pageable);
    /**
     * @param especialidad -> dentro de la query =:especialidad hace referencia a este parametro
     */
    @Query("""
            select m from Medico m
            where m.activo= true
            and
            m.especialidad=:especialidad
            and
            m.id not in(
                select c.medico.id from Consulta c
                where
                c.fecha=:fecha
            )
            order by rand()
            limit 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime fecha);

    @Query("""
            select m.activo
            from Medico m
            where m.id=:idMedico
            """)
    boolean findActivoById(Long idMedico);
}
