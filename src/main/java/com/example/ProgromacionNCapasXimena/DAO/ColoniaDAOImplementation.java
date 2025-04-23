
package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Colonia;
import com.example.ProgromacionNCapasXimena.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alien 3 P9
 */
@Repository
public class ColoniaDAOImplementation implements IColoniaDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate; 
    
    public Result ColoniaByIdMunicipio(int IdMunicipio) {
        
        Result result = new Result();
        
        try{
            jdbcTemplate.execute("CALL ColoniaByIdMunicipio(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                
                callableStatement.setInt(1,IdMunicipio); 
                callableStatement.registerOutParameter(2,Types.REF_CURSOR);
                
                callableStatement.execute(); 
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2); 
                
                result.objects = new ArrayList<>(); 
                while(resultSet.next()){
                    Colonia colonia = new Colonia(); 
                    
                    colonia.setIdColonia(resultSet.getInt("IdColonia")); 
                    colonia.setNombre(resultSet.getString("Nombre"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    result.objects.add(colonia); 
                    
                }
                    result.correct = true; 
                    return 1;
            });
                    
            
        } catch(Exception Ex){
            
            result.correct = false; 
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex; 
            
        }
        return result; 
    }
    
}
    

