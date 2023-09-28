package med.voll.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DatosAutenticacionUsuario;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DatosJwtToken;
import med.voll.api.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @PostMapping
    @Tag(
            name = "Autenticación",
            description = "Obtiene el token para el usuario asignado que da acceso al resto de endpoint"
    ) // en vez de utilizar @Operation se utiliza @Tag
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());
        var authenticate = authenticationManager.authenticate(authenticationToken);
        var JwtToken = tokenService.generarToken((Usuario) authenticate.getPrincipal());
        return ResponseEntity.ok(new DatosJwtToken(JwtToken));
    }
}
