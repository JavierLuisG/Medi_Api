package med.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;

public record DatosDireccion(
        @NotBlank(message = "Calle es obligatorio")
        String calle,
        @NotBlank(message = "Distrito es obligatorio")
        String distrito,
        @NotBlank(message = "Ciudad es obligatorio")
        String ciudad,
        @NotBlank(message = "Número es obligatorio")
        String numero,
        @NotBlank(message = "Complemento es obligatorio")
        String complemento){
}
