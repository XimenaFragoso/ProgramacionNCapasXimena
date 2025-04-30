package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Result;

public interface IMunicipioDAO {
    
    Result MunicipioByIdEstado(int IdEstado);
    
    Result MunicipioByIdEstadoJPA(int IdEstado); 
    
}
