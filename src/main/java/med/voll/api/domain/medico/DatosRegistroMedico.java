package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.direccion.DatosDireccion;

// este es el DTO(Data transfer Object) que se encarga de recibir de lo que está viniendo de la API
public record DatosRegistroMedico(
        @NotBlank(message = "Nombre es obligatorio") // es igual que NotNull (ni nulo ni blanco)
        String nombre,
        @NotBlank(message = "E-mail es obligatorio")
        @Email
        String email,
        @NotBlank(message = "Teléfono es obligatorio")
        String telefono,
        @NotBlank(message = "Documento es obligatorio")
        @Pattern(regexp = "\\d{4,6}")
        String documento,
        @NotNull(message = "Especialidad es obligatorio")
        Especialidad especialidad,
        @NotNull(message = "Dirección es obligatorio")
        @Valid
        DatosDireccion direccion) {
}
