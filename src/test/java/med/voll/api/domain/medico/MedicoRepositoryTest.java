package med.voll.api.domain.medico;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest // le indicamos que vamos a trabajar con persistencia de base de datos
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // para realizar las operaciones con una base de datos externa e indicarle que no vamos a utilizar la db que estamos utilizando
@ActiveProfiles("test") // indicarle cual va a ser el perfil que vamos a utilizar
class MedicoRepositoryTest {

    @Test
    void seleccionarMedicoConEspecialidadEnFecha() {
    }
}