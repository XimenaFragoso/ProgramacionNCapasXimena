package com.example.ProgromacionNCapasXimena.ML;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Roll {
    
    private int IdRoll;    
    private String Nombre;     
    
    public int getIdRoll(){
        return IdRoll;
    }    
    
    public void setIdRoll(int IdRoll){        
        this.IdRoll = IdRoll; 
    }
    
    public String getNombre(){
        return Nombre;         
    }
    
    public void setNombre(String Nombre) { 
        this.Nombre = Nombre; 
    }
    
    
}
