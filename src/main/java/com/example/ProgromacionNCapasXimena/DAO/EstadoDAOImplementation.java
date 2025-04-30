package com.example.ProgromacionNCapasXimena.DAO;
import com.example.ProgromacionNCapasXimena.ML.Pais;
import com.example.ProgromacionNCapasXimena.ML.Estado;
import com.example.ProgromacionNCapasXimena.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.util.List;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


//implementa la logica de la BD
@Repository
public class EstadoDAOImplementation implements IEstadoDAO{
    
    @Autowired 
    private JdbcTemplate jdbcTemplate; 
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result EstadoByIdPais(int IdPais){
        Result result = new Result(); 
        try{
            jdbcTemplate.execute("CALL EstadoByIdPais(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
               
                callableStatement.setInt(1,IdPais);
                callableStatement.registerOutParameter(2,Types.REF_CURSOR);
                
                callableStatement.execute(); 
            
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                
                result.objects = new ArrayList<>(); 
                while(resultSet.next()){
                    Estado estado = new Estado(); 
                    
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    
                    result.objects.add(estado);
                }
                return 1;
            
            });
             
            result.correct = true; 
        }catch(Exception ex){
            result.correct = false; 
            result.errorMessage = ex.getLocalizedMessage();
            result.ex =  ex;           
        }
        return result; 
    }

    @Override
    public Result EstadoByIdPaisJPA(int IdPais) {
        
        Result result = new Result();
        
        try {
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Estado> queryEstado = entityManager.createQuery("FROM Estado WHERE Pais.IdPais = :idpais", com.example.ProgromacionNCapasXimena.JPA.Estado.class);
            queryEstado.setParameter("idpais", IdPais);
            List<com.example.ProgromacionNCapasXimena.JPA.Estado> estadosJPA = queryEstado.getResultList();
            
            result.objects = new ArrayList(); 
            for (com.example.ProgromacionNCapasXimena.JPA.Estado estadoJPA : estadosJPA) {
                Estado estado = new Estado(); 
                estado.setIdEstado(estadoJPA.getIdEstado());
                estado.setNombre(estadoJPA.getNombre());
                
                result.objects.add(estado);
            }
            
            result.correct = true;
            
        } catch (Exception Ex) {
            result.object = false; 
            result.errorMessage = Ex.getLocalizedMessage(); 
            result.ex = Ex; 
            
        }
        return result;
    }
    
    
}
