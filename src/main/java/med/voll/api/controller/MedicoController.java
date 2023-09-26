package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    // no es recomendable utilizar Autowired
    @Autowired // anotacion especial para no instanciar el MedicoRepository haciendo "= new ..."
    private MedicoRepository medicoRepository;

    /**
     * ResponseEntity: es un wrapper para encapsular la respuesta que le vamos a dar a nuestro servidor
     */
    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(
            @RequestBody @Valid DatosRegistroMedico datosRegistroMedico, UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(
                medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        // Return 201 Created
        // URL donde encontrar al medico
        // GET -> http://localhost:8080/medicos/id
        URI uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(datosRespuestaMedico);
    }

    /**
     * Page ayuda a realizar el paginado, es decir, separar la lista que lleva a la cantidad de json que quiero que
     * aparezcan
     * Pageable: para trabajar por paginado necesitamos este parametro frond-end. COn pageable no necesitamos el stream()
     * ni el toList(). Por eso está comentado sin pageable dentro de findAll()
     * @PageableDefault es para poder definir los valores que son por defecto y asi tener un orden ya predefinido
     */
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedicos(@PageableDefault Pageable pageable){
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(pageable).map(DatosListadoMedico::new)); //con findAll() aunque no tenga funciones MedicoRepository, por la interface Jpa.. se puede
//        return medicoRepository.findAll(pageable).map(DatosListadoMedico::new); //con findAll() aunque no tenga funciones MedicoRepository, por la interface Jpa.. se puede
//        return medicoRepository.findAll().stream().map(DatosListadoMedico::new).toList();
    }

    /**
     * Con RequestBody y Valid llegan los valores y el id no lo toma como nulo asi que puede hacer la validacion
     * Jpa con getReferenceById lo que hace es mapear el medico que se trae de la base de datos para que luego
     * mediante actualizarDatos se realice la
     * @Transactional abre una transaccion en la db (con el se realiza la modificacion)
     * por asi decirlo, realiza el commit
     */
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(
                medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }

    /**
     * Delete logico, practicamente es desactivar su estado activo
     * @param id
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.inhabilitarMedico();
        return ResponseEntity.noContent().build();
    }
    /**
     * Este metodo es para eliminar el registro por completo - DELETE en base de datos
     * Como el path "@RequestMapping" esta hasta "/medicos" y la forma para eliminar es "/medicos/Id" entonces se
     * genera eliminarMedico por medio del path @DeleteMapping con el num de Id a eliminar,
     * como es de forma generica la forma es {id} y para indicarle al parametro de donde viene la variable id
     * entonces se utiliza el path @PathVariable
     */
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void eliminarMedico(@PathVariable Long id){
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.inhabilitarMedico();
        var datosMedicos = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(), medico.getTelefono(), medico.getDocumento(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedicos);
    }

}
