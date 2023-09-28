package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;

import java.time.Duration;
import java.time.LocalDateTime;

public class ValidadorHorarioAntecedencia implements ValidadorCancelamientoDeConsulta{
    private ConsultaRepository consultaRepository;
    @Override
    public void validar(DatosCancelamientoConsulta cancelamientoConsulta) {
        Consulta consulta = consultaRepository.getReferenceById(cancelamientoConsulta.idConsulta());
        LocalDateTime now = LocalDateTime.now();
        long diferenciaEnHoras = Duration.between(now, consulta.getFecha()).toHours();

        if (diferenciaEnHoras < 24){
            throw new ValidationException("Se puede cancelar a mas de 24 horas de antelación");
        }
    }
}
