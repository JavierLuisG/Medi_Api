package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosActualizarPaciente(
        @NotNull(message = "Id es obligatorio")
        Long id,
        String nombre,
        String documentoIdentidad,
        DatosDireccion direccion) {

}
