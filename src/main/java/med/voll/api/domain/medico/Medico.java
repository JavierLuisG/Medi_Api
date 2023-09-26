package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.domain.direccion.Direccion;

/**
la entidad Medico que esta se usa para generar la persistencia de datos con el modelo de la base de datos
 */
@Entity(name = "Medico")
@Table(name = "medicos")
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
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono; // nuevo 12/09
    private String documento;
    private Boolean activo;
    @Enumerated(EnumType.STRING) // fijar el Enum
    private Especialidad especialidad;
    @Embedded // Embeddable es para la clase que contiene las variables, Embedded es para la instancia
    private Direccion direccion;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.nombre = datosRegistroMedico.nombre();
        this.email = datosRegistroMedico.email();
        this.telefono = datosRegistroMedico.telefono();
        this.documento = datosRegistroMedico.documento();
        this.activo = true;
        this.especialidad = datosRegistroMedico.especialidad();
        this.direccion = new Direccion(datosRegistroMedico.direccion());
    }

    /**
     * En dado caso de que se quiera actualizar solo uno de los tres valores es necesario realizar el if para saber
     * que valores estan llegando null y no se actualicen con null
     */
    public void actualizarDatos(DatosActualizarMedico datosActualizarMedico) {
        if (datosActualizarMedico.nombre() != null){
            this.nombre = datosActualizarMedico.nombre();
        }
        if (datosActualizarMedico.documento() != null) {
            this.email = datosActualizarMedico.documento();
        }
        if (datosActualizarMedico.direccion() != null) {
            this.direccion = direccion.actualizarDatosDireccion(datosActualizarMedico.direccion());
        }
    }

    public void inhabilitarMedico() {
        this.activo = false;
    }
}
