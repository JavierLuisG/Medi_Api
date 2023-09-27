package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class HorarioDeAnticipacion implements ValidadorDeConsultas{
    @Override
    public void validar(DatosAgendarConsulta datosAgendarConsulta) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime horaConsulta = datosAgendarConsulta.fecha();

        boolean diferencia30Min = Duration.between(now, horaConsulta).toMinutes() < 30;

        if (diferencia30Min) {
            throw new ValidationException("Las consultas deben ser programadas con al menos 30 minutos de anticipación");
        }

    }
}
