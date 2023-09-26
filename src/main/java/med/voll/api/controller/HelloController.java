package med.voll.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // antes era con @Controller
@RequestMapping("/hello") // que ruta de HTTP esta siguiendo este metodo
public class HelloController {
    @GetMapping // se mapea el metodo Get en la ruta de /hello ejm http://localhost:8080/hello
    public String helloWorld(){
        return "Hello world from Colombia parce";
    }

}
