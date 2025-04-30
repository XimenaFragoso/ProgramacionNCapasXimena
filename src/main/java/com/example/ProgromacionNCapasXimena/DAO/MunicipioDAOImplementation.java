package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Municipio;
import com.example.ProgromacionNCapasXimena.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class MunicipioDAOImplementation implements IMunicipioDAO {

    @Autowired 
    private JdbcTemplate jdbcTemplate; 
    
    @Autowired
    private EntityManager entityManager;
    
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

    @Override
    public Result MunicipioByIdEstadoJPA(int IdEstado) {
        Result result = new Result(); 
        
        try {
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Municipio> queryMunicipio = entityManager.createQuery("FROM Municipio WHERE Estado.IdEstado = :idestado", com.example.ProgromacionNCapasXimena.JPA.Municipio.class);
            queryMunicipio.setParameter("idestado", IdEstado);
            List<com.example.ProgromacionNCapasXimena.JPA.Municipio> municipiosJPA = queryMunicipio.getResultList();

            result.objects = new ArrayList<>();
            for (com.example.ProgromacionNCapasXimena.JPA.Municipio municipioJPA : municipiosJPA) {
                Municipio municipio = new Municipio(); 
                municipio.setIdMunicipio(municipioJPA.getIdMunicipio());
                municipio.setNombre(municipioJPA.getNombre());
                
                result.objects.add(municipio);
            }
            
        }catch(Exception Ex){
            result.object = false; 
            result.errorMessage = Ex.getLocalizedMessage(); 
            result.ex = Ex;
        }
        return result; 
    }
}