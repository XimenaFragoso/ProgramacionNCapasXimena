package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Pais;
import com.example.ProgromacionNCapasXimena.ML.Result;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.Types;
import java.util.ArrayList;

import java.util.List;



@Repository
public class PaisDAOImplementation implements IPaisDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override 
    public Result GetAll(){
        Result result = new Result(); 
        try{
            result.object = jdbcTemplate.execute("{CALL PaisGetAll(?)}",(CallableStatementCallback<List<Pais>>) callableStatement -> {
               callableStatement.registerOutParameter(1,Types.REF_CURSOR); 
               callableStatement.execute(); 
               
               ResultSet resultSet = (ResultSet) callableStatement.getObject(1); 
               List<Pais> paises = new ArrayList(); 
               while(resultSet.next()){
                   Pais pais = new Pais (); 
                   pais.setIdPais(resultSet.getInt("IdPais"));
                   pais.setNombre(resultSet.getString("Nombre"));
                   paises.add(pais);
                   
               }
               
                result.correct = true;
                return paises; 
                       
        });
        } catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
            return result; 
    }

    @Override
    public Result GetAllJPA() {
        Result result = new Result(); 
        
        try {
            
        } catch (Exception ex) {
        }
        return result;
    }
    
}

