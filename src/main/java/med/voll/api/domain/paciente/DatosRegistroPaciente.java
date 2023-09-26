package med.voll.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

// este es el DTO(Data transfer Object) que se encarga de recibir de lo que está viniendo de la API
public record DatosRegistroPaciente(
        @NotBlank(message = "Nombre es obligatorio")
        String nombre,
        @NotBlank(message = "E-mail es obligatorio")
        @Email
        String email,
        @NotBlank(message = "Teléfono es obligatorio")
        String telefono,
        @NotBlank(message = "Documento de identidad es obligatorio")
        @Pattern(regexp = "\\d{4,6}")
        String documentoIdentidad,
        @NotNull(message = "Dirección es obligatorio")
        @Valid
        DatosDireccion direccion) {
}
