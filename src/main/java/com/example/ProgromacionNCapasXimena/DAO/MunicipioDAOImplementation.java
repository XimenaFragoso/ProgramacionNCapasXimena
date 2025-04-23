/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Municipio;
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
public class MunicipioDAOImplementation implements IMunicipioDAO {

    @Autowired 
    private JdbcTemplate jdbcTemplate; 
    
    @Override
    public Result MunicipioByIdEstado(int IdEstado) {

        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL MunicipioByIdEstado(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, IdEstado);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                result.objects = new ArrayList<>();
                while (resultSet.next()) {
                    Municipio municipio = new Municipio();

                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombre(resultSet.getString("Nombre"));

                    result.objects.add(municipio);
                }
                result.correct = true; 

                return 1;
            });
            
        } catch (Exception Ex) {
            result.correct = false; 
            result.errorMessage = Ex.getLocalizedMessage(); 
            result.ex = Ex;
            
        }

        return result; 
    }
}