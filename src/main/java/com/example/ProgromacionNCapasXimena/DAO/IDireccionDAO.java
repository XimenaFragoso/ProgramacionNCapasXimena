package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Direccion;
import com.example.ProgromacionNCapasXimena.ML.Result;
import com.example.ProgromacionNCapasXimena.ML.UsuarioDireccion;

public interface IDireccionDAO {

    Result DireccionAdd(UsuarioDireccion usuarioDireccion);

    Result GetByIdDireccion(int IdDireccion);

    Result UpdateDireccion(Direccion direccion);

    Result DireccionDelete(int IdDireccion);

    Result DireccionAddJPA(UsuarioDireccion usuarioDireccion);
    
    Result DireccionDeletJPA(int IdDireccion);

    Result UpdateDireccionJPA(Direccion direccion); 
    
    Result GetByIdDireccionJPA(int IdDireccion);
}
