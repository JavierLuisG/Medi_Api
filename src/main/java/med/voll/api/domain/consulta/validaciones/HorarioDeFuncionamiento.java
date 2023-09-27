package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class HorarioDeFuncionamiento implements ValidadorDeConsultas{
    @Override
    public void validar(DatosAgendarConsulta datosAgendarConsulta) {
        boolean domingo = DayOfWeek.SUNDAY.equals(datosAgendarConsulta.fecha().getDayOfWeek());
        boolean antesDeApertura = datosAgendarConsulta.fecha().getHour() < 7;
        boolean despuesDeCierre = datosAgendarConsulta.fecha().getHour() > 19;
        if (domingo || antesDeApertura || despuesDeCierre){
            throw new ValidationException("El horario de atención de la clínica es de lunes a sábado de 07:00 a 19:00 horas");
        }
    }
}
