package com.example.ProgromacionNCapasXimena.ML;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Municipio {
    
    private int IdMunicipio; 
    private String Nombre; 
    public Estado Estado; 

    public Estado getEstado() {
        return Estado;
    }

    public void setEstado(Estado Estado) {
        this.Estado = Estado;
    }
    
    public int getIdMunicipio () {
        return IdMunicipio;
    }
    
    public void setIdMunicipio (int IdMunicipio) { 
        this.IdMunicipio = IdMunicipio; 
    }
    
    public String getNombre () { 
        return Nombre; 
    }
    
    public void setNombre (String Nombre) {
        this.Nombre = Nombre; 
    }
    
}
