package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosRegistroPaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest // le indicamos que vamos a trabajar con persistencia de base de datos
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // para realizar las operaciones con una base de datos externa e indicarle que no vamos a utilizar la db que estamos utilizando
@ActiveProfiles("test") // indicarle cual va a ser el perfil que vamos a utilizar
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private TestEntityManager em;
    @Test
    /**
     * Pueden haber dos posibles casos, que sea null o que devuelva al médico
     */
    @DisplayName("deberia retornar nulo cuando el médico se encuentre en consulta " +
            "con otro paciente en ese horario") // la operacion que va a realizar este metodo de prueba
    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        Medico medico = registrarMedico("Jose", "jose@gmail.com", "123456", Especialidad.CARDIOLOGIA);

        Paciente paciente = registrarPaciente("Arturo", "arturo@gmail.com", "234567");

        registrarConsulta(medico, paciente, proximoLunes10H);

        Medico medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA,
                proximoLunes10H);

        assertThat(medicoLibre).isNull(); // se va a comparar si el medico es null
    }
    @Test
    /**
     * segunda posibilidad
     */
    @DisplayName("debería retornar un médico cuando realice la consulta en la base de datos para ese horario") // la operacion que va a realizar este metodo de prueba
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        Medico medico = registrarMedico("Jose", "jose@gmail.com", "123456", Especialidad.CARDIOLOGIA);

        Medico medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA,
                proximoLunes10H);

        assertThat(medicoLibre).isEqualTo(medico); // se va a comparar si el medico es null
    }
    @Test
    void findActivoById() {
    }
    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        em.persist(new Consulta(null, medico, paciente, fecha, null));
    }
    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad){
        Medico medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }
    private Paciente registrarPaciente(String nombre, String email, String documento){
        Paciente paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }
    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad){
        return new DatosRegistroMedico(
                nombre,
                email,
                "619999",
                documento,
                especialidad,
                datosDireccion()
        );
    }
    private DatosRegistroPaciente datosPaciente(String nombre, String email, String documento){
        return new DatosRegistroPaciente(
                nombre,
                email,
                "619999",
                documento,
                datosDireccion()
        );
    }
    private DatosDireccion datosDireccion(){
        return new DatosDireccion(
                " loca",
                "azul",
                "acapulco",
                "321",
                "12"
        );
    }
}