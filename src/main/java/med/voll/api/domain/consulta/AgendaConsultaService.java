package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorCancelamientoDeConsulta;
import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaConsultaService {
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    List<ValidadorDeConsultas> validadorDeConsultasList;
    List<ValidadorCancelamientoDeConsulta> validadoresCancelamiento;
    public DatosDetalleConsulta agendar(DatosAgendarConsulta datosAgendarConsulta){
        if (!pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este id para el paciente no fue encontrado");
        }
        if (datosAgendarConsulta.idMedico() != null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionDeIntegridad("Este id para el médico no fue encontrado");
        }
        //validaciones
        validadorDeConsultasList.forEach(vali -> vali.validar(datosAgendarConsulta));

        Paciente paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();
        Medico medico = seleccionarMedico(datosAgendarConsulta);
        if (medico == null){
            throw new ValidacionDeIntegridad("No existe médicos disponibles para este horario y especialidad");
        }

//        Consulta consulta = new Consulta(null, medico, paciente, datosAgendarConsulta.fecha());
        Consulta consulta = new Consulta(medico, paciente, datosAgendarConsulta.fecha());
        consultaRepository.save(consulta);
        return new DatosDetalleConsulta(consulta);
    }
    public void cancelar(DatosCancelamientoConsulta cancelamientoConsulta){
        if (!consultaRepository.existsById(cancelamientoConsulta.idConsulta())){
            throw new ValidacionDeIntegridad("Id de la consulta a cancelar no existe");
        }
        validadoresCancelamiento.forEach(vali -> vali.validar(cancelamientoConsulta));
        Consulta consulta = consultaRepository.getReferenceById(cancelamientoConsulta.idConsulta());
        consulta.cancelar(cancelamientoConsulta.motivo());
    }
    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if (datosAgendarConsulta.idMedico() != null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if (datosAgendarConsulta.especialidad() == null){
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el médico");
        }
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(
                datosAgendarConsulta.especialidad(), datosAgendarConsulta.fecha());
    }
}
