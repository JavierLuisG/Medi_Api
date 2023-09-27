package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component
public class PacienteSinConsulta implements ValidadorDeConsultas{
    @Autowired
    private ConsultaRepository consultaRepository;
    @Override
    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        LocalDateTime primerHorario = datosAgendarConsulta.fecha().withHour(7);
        LocalDateTime ultimoHorario = datosAgendarConsulta.fecha().withHour(18);

        boolean pacienteConConsulta = consultaRepository
                .existsByPacienteIdAndFechaBetween(
                        datosAgendarConsulta.idPaciente(),
                        primerHorario,
                        ultimoHorario);
        if (pacienteConConsulta) {
            throw new ValidationException("No se puede realizar consulta, el paciente ya cuenta con una consulta para ese día");
        }
    }
}
