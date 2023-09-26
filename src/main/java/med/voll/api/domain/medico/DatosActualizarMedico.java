package med.voll.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosActualizarMedico(
        @NotNull(message = "Id es obligatorio")
        Long id,
        String nombre,
        String documento,
        DatosDireccion direccion) {
}
