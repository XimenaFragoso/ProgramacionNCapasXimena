package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Result;
import com.example.ProgromacionNCapasXimena.ML.Roll;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.repository.query.Parameters.TYPES;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RollDAOImplementation implements IRollDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public Result GetAll(){
        Result result = new Result(); 
        try{ 
            result.object = jdbcTemplate.execute("{CALL RollGetAll(?)}",(CallableStatementCallback<List<Roll>>) callableStatement -> {
                callableStatement.registerOutParameter(1,Types.REF_CURSOR); 
                callableStatement.execute(); 
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1); 
                
                List<Roll> roles = new ArrayList<>();
                while(resultSet.next()){
                    Roll roll = new Roll(); 
                    roll.setIdRoll(resultSet.getInt("IdRoll")); 
                    roll.setNombre(resultSet.getString("Nombre")); 
                    roles.add(roll); 
                    
                }
                
                result.correct = true;
                return roles; 
            });
            
            } catch (Exception ex){
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex; 
        }
            return result; 
    }
    
    
}
