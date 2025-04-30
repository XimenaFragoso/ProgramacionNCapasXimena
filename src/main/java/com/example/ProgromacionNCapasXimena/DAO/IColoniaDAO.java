package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Result;


public interface IColoniaDAO {
    
    Result ColoniaByIdMunicipio(int IdMunicipio);
    
    Result ColoniaByIdMunicipioJPA(int IdMunicipio);
    
}
