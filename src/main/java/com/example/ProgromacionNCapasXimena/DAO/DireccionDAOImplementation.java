package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Colonia;
import com.example.ProgromacionNCapasXimena.ML.Direccion;
import com.example.ProgromacionNCapasXimena.ML.Estado;
import com.example.ProgromacionNCapasXimena.ML.Municipio;
import com.example.ProgromacionNCapasXimena.ML.Pais;
import com.example.ProgromacionNCapasXimena.ML.Result;
import com.example.ProgromacionNCapasXimena.ML.UsuarioDireccion;
import java.sql.ResultSet;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class DireccionDAOImplementation implements IDireccionDAO {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public Result DireccionAdd(UsuarioDireccion usuarioDireccion){
        Result result = new Result(); 
        
        try{
            jdbcTemplate.execute("CALL DireccionAdd(?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, usuarioDireccion.Usuario.getIdUsuario());
                callableStatement.setString(2, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(3, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(4, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(5, usuarioDireccion.Direccion.Colonia.getIdColonia());

                int rowAffected = callableStatement.executeUpdate();

                result.correct = rowAffected > 0 ? true : false;

                return 1;

        });
            
        }catch(Exception Ex){
            result.correct = false; 
            result.errorMessage = Ex.getLocalizedMessage(); 
            result.ex = Ex;
            
        }
        return result;
        
    }
    

    //Sirve para que se llenen los campos por el IdDireccion al momento de editar
    @Override
    public Result GetByIdDireccion(int IdDireccion){
        Result result = new Result(); 
        
        
        
        try{
            jdbcTemplate.execute("CALL DireccionGetByIdDireccion(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
               callableStatement.setInt(1,IdDireccion); 
               callableStatement.registerOutParameter(2,Types.REF_CURSOR);
               
               callableStatement.execute(); 
               
               ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
               
               if(resultSet.next()){
                   Direccion direccion = new Direccion(); 
                   direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                   direccion.setCalle(resultSet.getString("Calle"));
                   direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                   direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                   direccion.Colonia = new Colonia(); 
                   direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                   direccion.Colonia.Municipio = new Municipio(); 
                   direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                   direccion.Colonia.Municipio.Estado = new Estado(); 
                   direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                   direccion.Colonia.Municipio.Estado.Pais = new Pais(); 
                   direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                   
                   result.object = direccion; 
                   
               }
               
               return 1;
               
            });
            
            result.correct = true;
            
        }catch(Exception Ex){
            result.correct = false; 
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex; 
            
        }
        
        return result;
        
    }
    
    @Override
    //Actualizar direccion
    public Result UpdateDireccion(Direccion direccion){
        Result result = new Result(); 
        
        try{
            jdbcTemplate.execute("CALL DireccionUpdate(?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, direccion.getIdDireccion());
                callableStatement.setString(2, direccion.getCalle());
                callableStatement.setString(3, direccion.getNumeroInterior());
                callableStatement.setString(4,direccion.getNumeroExterior()); 
                callableStatement.setInt(5,direccion.Colonia.getIdColonia()); 
                
                int rowsAffected = callableStatement.executeUpdate(); 
                
                if(rowsAffected > 0){
                    result.correct = true; 
                } else { 
                    result.correct = false; 
                    result.errorMessage = "No se pudo actualizar"; 
                }
                
                return 1; 
    });
            
        }catch(Exception Ex){
        result.correct = false; 
        result.errorMessage = Ex.getLocalizedMessage(); 
        result.ex = Ex; 
        }
        
        return result; 
    }
    
    
    //Borrar direcion por idDireccion en DetailDireccion
    public Result DireccionDelete(int IdDireccion){
        Result result = new Result(); 
        try{
            jdbcTemplate.execute("CALL DireccionDelete(?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1,IdDireccion); 
                
                callableStatement.execute(); 
                
                return 1;
            });
            
        }catch(Exception Ex){
                    result.correct = false; 
                    result.errorMessage = Ex.getLocalizedMessage(); 
                    result.ex = Ex; 
                    
                    }
        return result; 
        }
    }
    

