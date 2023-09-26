package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.direccion.Direccion;

@Entity(name = "Paciente")
@Table(name = "pacientes")
/*
Para no generar los getter and setters y constructores, aquí es donde entra el concepto "Lombok" de para no generar el
codigo a mano
 */
@Getter
@Setter
@NoArgsConstructor // constructor sin argumentos
@AllArgsConstructor // constructor con argumentos
// HashCode utiliza el parametro id para la comparacion entre medicos(si los id son iguales va a inferir que es igual)
@EqualsAndHashCode(of = "id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private Boolean activo;
    private String documentoIdentidad;
    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente datosRegistroPaciente) {
        this.nombre = datosRegistroPaciente.nombre();
        this.email = datosRegistroPaciente.email();
        this.telefono = datosRegistroPaciente.telefono();
        this.activo = true;
        this.documentoIdentidad = datosRegistroPaciente.documentoIdentidad();
        this.direccion = new Direccion(datosRegistroPaciente.direccion());
    }

    public void actualizarDatos(DatosActualizarPaciente datosActualizarPaciente) {
        if (datosActualizarPaciente.nombre() != null){
            this.nombre = datosActualizarPaciente.nombre();
        }
        if (datosActualizarPaciente.documentoIdentidad() != null){
            this.documentoIdentidad = datosActualizarPaciente.documentoIdentidad();
        }
        if (datosActualizarPaciente.direccion() != null){
            this.direccion = direccion.actualizarDatosDireccion(datosActualizarPaciente.direccion());
        }
    }

    public void inhabilitarPaciente() {
        this.activo = false;
    }
}
