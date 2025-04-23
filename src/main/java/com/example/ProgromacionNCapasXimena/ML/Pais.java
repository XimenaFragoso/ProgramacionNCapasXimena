package com.example.ProgromacionNCapasXimena.ML;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Pais {
    
    private int IdPais; 
    private String Nombre; 

    public int getIdPais() {
        return IdPais;
    }

    public void setIdPais(int IdPais) {
        this.IdPais = IdPais;
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    
    
}
