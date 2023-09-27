package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorDeConsultas{
    @Autowired
    private MedicoRepository medicoRepository;
    @Override
    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        if (datosAgendarConsulta.idMedico() == null) {
            return;
        }
        boolean activoById = medicoRepository.findActivoById(datosAgendarConsulta.idMedico());
        if (!activoById){
            throw new ValidationException("El médico no se encuentra activo");
        }
    }
}
