package com.example.ProgromacionNCapasXimena.RestController;

import com.example.ProgromacionNCapasXimena.ML.Usuario; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demoapi")
public class DemoRestController {
    
    @GetMapping("saludo")
    public String Saludo(@RequestParam String nombre){
        return "Hola mundo" + " " + nombre;
    }
    
    //manda como respuesta un json
    @GetMapping("saludo2")
    public ResponseEntity Saludo2(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Ximena");
        usuario.setApellidoPaterno("Fragoso");
        usuario.setApellidoMaterno("Cortes");
        
        return ResponseEntity.accepted().body(usuario);
        
    }
    
    //suma
    @GetMapping("operacion/{numeroUno}/{numeroDos}")
    public ResponseEntity Operacion(@PathVariable int numeroUno, @PathVariable int numeroDos){
        int suma = numeroUno + numeroDos; 
        return ResponseEntity.ok().body(numeroUno + " + " + numeroDos + " = " + suma);
        
    }
    
    //resta
    @GetMapping("operacion1/{numeroUno}/{numeroDos}")
    public ResponseEntity Operacion1(@PathVariable int numeroUno, @PathVariable int numeroDos){
        int resta = numeroUno - numeroDos; 
        return ResponseEntity.ok().body(numeroUno + " - " + numeroDos + " = " + resta);        
    }
    
    //multiplicacion
    @GetMapping("operacion2/{numeroUno}/{numeroDos}")
    public ResponseEntity Operacion2(@PathVariable int numeroUno, @PathVariable int numeroDos){
        int multiplicacion = numeroUno * numeroDos; 
        return ResponseEntity.ok().body(numeroUno + " * " + numeroDos + " = " + multiplicacion);
    }
    
    //división
    @GetMapping("operacion3/{numeroUno}/{numeroDos}")
    public ResponseEntity Operacion3(@PathVariable int numeroUno, @PathVariable int numeroDos){

        if(numeroDos == 0 | numeroUno == 0){
            return ResponseEntity.badRequest().body("Error - No se puede dividir entre 0");
        }else{
            double division = numeroUno / numeroDos; 
            return ResponseEntity.ok().body(numeroUno + " / " + numeroDos + " = " + division);
        }
        
    }
    
}
