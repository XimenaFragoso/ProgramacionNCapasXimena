
package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Direccion;
import com.example.ProgromacionNCapasXimena.ML.Result;


public interface IDireccionDAO {
    
    Result GetByIdDireccion(int IdDireccion);
    Result UpdateDireccion(Direccion direccion);
    Result DireccionDelete(int IdDireccion);
}
