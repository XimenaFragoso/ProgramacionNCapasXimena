package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Colonia;
import com.example.ProgromacionNCapasXimena.ML.Direccion;
import com.example.ProgromacionNCapasXimena.ML.Estado;
import com.example.ProgromacionNCapasXimena.ML.Municipio;
import com.example.ProgromacionNCapasXimena.ML.Pais;
import com.example.ProgromacionNCapasXimena.ML.Result;
import com.example.ProgromacionNCapasXimena.ML.Roll;
import com.example.ProgromacionNCapasXimena.ML.Usuario;
import com.example.ProgromacionNCapasXimena.ML.UsuarioDireccion;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository //Logica de Base de Datos
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired //Inyeccion de depencias  
    private JdbcTemplate jdbcTemplate; //conexión directa a la BD 

    @Autowired //conexion de JPA
    private EntityManager entityManager;

    @Override
    public Result GetAll() {

        //Para que se vean los datos en la tabla
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL GetAllSP(?)}",
                    (CallableStatementCallback<Integer>) callableStatement -> {
                        callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                        callableStatement.execute();

                        ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                        result.objects = new ArrayList<>();

                        while (resultSet.next()) {
                            //Se usa para hacer la comparación de direcciones y no duplicar el usuario
                            int IdUsuario = resultSet.getInt("IdUsuario");

                            if (!result.objects.isEmpty() && IdUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {
                                //Sirve para agregar solo direccion 
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                                ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                            } else {

                                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                                usuarioDireccion.Usuario = new Usuario();
                                usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                                usuarioDireccion.Usuario.setNombre(resultSet.getString("nombreUsuario"));
                                usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                                usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                                usuarioDireccion.Usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                                usuarioDireccion.Usuario.setUserName(resultSet.getString("UserName"));
                                usuarioDireccion.Usuario.setEmail(resultSet.getString("Email"));
                                usuarioDireccion.Usuario.setPassword(resultSet.getString("Password"));
                                usuarioDireccion.Usuario.setSexo(resultSet.getString("Sexo"));
                                usuarioDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                                usuarioDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                                usuarioDireccion.Usuario.setCURP(resultSet.getString("CURP"));
                                usuarioDireccion.Usuario.setStatus(resultSet.getInt("Status"));
                                usuarioDireccion.Usuario.setImagen(resultSet.getString("Imagen"));

                                usuarioDireccion.Usuario.Roll = new Roll();
                                usuarioDireccion.Usuario.Roll.setIdRoll(resultSet.getInt("IdRoll"));
                                usuarioDireccion.Usuario.Roll.setNombre(resultSet.getString("NombreRoll"));

                                usuarioDireccion.Direcciones = new ArrayList<>();
                                Direccion direccion = new Direccion();
                                direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                                direccion.setCalle(resultSet.getString("Calle"));
                                direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                                direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                                direccion.Colonia = new Colonia();
                                direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                                direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                                direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                                direccion.Colonia.Municipio = new Municipio();
                                direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                                direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));

                                direccion.Colonia.Municipio.Estado = new Estado();
                                direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                                direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));

                                direccion.Colonia.Municipio.Estado.Pais = new Pais();
                                direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                                direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                                usuarioDireccion.Direcciones.add(direccion);
                                result.objects.add(usuarioDireccion);
                            }
                        }
                        result.correct = true;
                        return 1;

                    });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.object = null;

        }
        return result;
    }

    @Override
    public Result GetAllDinamico(Usuario usuario) {
        //Procedimiento dinamico para buscar Usuarios por letra
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL UsuarioGetAllDinamico(?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setInt(4, usuario.Roll.getIdRoll());
                callableStatement.registerOutParameter(5, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);

                result.objects = new ArrayList<>();

                while (resultSet.next()) {

                    int IdUsuario = resultSet.getInt("IdUsuario");
                    if (!result.objects.isEmpty() && IdUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {
                        //Sirve para agregar solo direccion 
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                        ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                    } else {
                        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

                        usuarioDireccion.Usuario = new Usuario();
                        usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuarioDireccion.Usuario.setNombre(resultSet.getString("nombreUsuario"));
                        usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuarioDireccion.Usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuarioDireccion.Usuario.setUserName(resultSet.getString("UserName"));
                        usuarioDireccion.Usuario.setEmail(resultSet.getString("Email"));
                        usuarioDireccion.Usuario.setPassword(resultSet.getString("Password"));
                        usuarioDireccion.Usuario.setSexo(resultSet.getString("Sexo"));
                        usuarioDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                        usuarioDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                        usuarioDireccion.Usuario.setCURP(resultSet.getString("CURP"));
                        usuarioDireccion.Usuario.setStatus(resultSet.getInt("Status"));
                        usuarioDireccion.Usuario.setImagen(resultSet.getString("Imagen"));

                        usuarioDireccion.Usuario.Roll = new Roll();
                        usuarioDireccion.Usuario.Roll.setIdRoll(resultSet.getInt("IdRoll"));
                        usuarioDireccion.Usuario.Roll.setNombre(resultSet.getString("NombreRoll"));

                        usuarioDireccion.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));

                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));

                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                        usuarioDireccion.Direcciones.add(direccion);
                        result.objects.add(usuarioDireccion);

                    }
                }
                return 1;

            }
            );
            result.correct = true;

        } catch (Exception Ex) {
            result.correct = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;
            result.objects = null;
        }
        return result;
    }

    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        //insertar usuario en el formulario
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setString(1, usuarioDireccion.Usuario.getNombre());
                callableStatement.setString(2, usuarioDireccion.Usuario.getApellidoPaterno());
                callableStatement.setString(3, usuarioDireccion.Usuario.getApellidoMaterno());
                callableStatement.setDate(4, new java.sql.Date(usuarioDireccion.Usuario.getFechaNacimiento().getTime()));
                callableStatement.setInt(5, usuarioDireccion.Usuario.Roll.getIdRoll());
                callableStatement.setString(6, usuarioDireccion.Usuario.getUserName());
                callableStatement.setString(7, usuarioDireccion.Usuario.getEmail());
                callableStatement.setString(8, usuarioDireccion.Usuario.getPassword());
                callableStatement.setString(9, usuarioDireccion.Usuario.getSexo());
                callableStatement.setString(10, usuarioDireccion.Usuario.getTelefono());
                callableStatement.setString(11, usuarioDireccion.Usuario.getCelular());
                callableStatement.setString(12, usuarioDireccion.Usuario.getCURP());
                callableStatement.setInt(13, usuarioDireccion.Usuario.getStatus());
                callableStatement.setString(14, usuarioDireccion.Usuario.getImagen());

                callableStatement.setString(15, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(16, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(17, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(18, usuarioDireccion.Direccion.Colonia.getIdColonia());
//                callableStatement.setInt(17, usuarioDireccion.Usuario.getIdUsuario());

                int rowAffected = callableStatement.executeUpdate();

                result.correct = rowAffected > 0 ? true : false;

                return 1;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.object = null;
        }

        return result;
    }

    //Sirve para ver la direccion por usuario en la tabla de Direcciones 
    @Override
    public Result DireccionById(int IdUsuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL DireccionByIdUsuario(?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.setInt(3, IdUsuario);

                callableStatement.execute();

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(1);

                if (resultSetUsuario.next()) {
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setIdUsuario(resultSetUsuario.getInt("IdUsuario"));
                    usuarioDireccion.Usuario.setNombre(resultSetUsuario.getString("NombreUsuario"));
                    usuarioDireccion.Usuario.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    usuarioDireccion.Usuario.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    usuarioDireccion.Usuario.setTelefono(resultSetUsuario.getString("Telefono"));
                    usuarioDireccion.Usuario.setCelular(resultSetUsuario.getString("Celular"));
                    usuarioDireccion.Usuario.setEmail(resultSetUsuario.getString("Email"));
                    usuarioDireccion.Usuario.setImagen(resultSetUsuario.getString("Imagen"));
                }

                ResultSet resultSetDireccion = (ResultSet) callableStatement.getObject(2);
                usuarioDireccion.Direcciones = new ArrayList<>();
                while (resultSetDireccion.next()) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSetDireccion.getInt("IdDireccion"));
                    direccion.setCalle(resultSetDireccion.getString("Calle"));
                    direccion.setNumeroInterior(resultSetDireccion.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSetDireccion.getString("NumeroExterior"));
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSetDireccion.getInt("IdColonia"));
                    direccion.Colonia.setNombre(resultSetDireccion.getString("NombreColonia"));
                    direccion.Colonia.setCodigoPostal(resultSetDireccion.getString("CodigoPostal"));

                    usuarioDireccion.Direcciones.add(direccion);
                }

                result.object = usuarioDireccion;
                result.correct = true;
                return 1;

            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.object = null;
        }
        return result;
    }

    //Sirve para ver el usuario por ID en la tabla de Direcciones
    @Override
    public Result GetById(int IdUsuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL UsuarioById(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                if (resultSet.next()) {
                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuarioDireccion.Usuario.setNombre(resultSet.getString("nombreUsuario"));
                    usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuarioDireccion.Usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    usuarioDireccion.Usuario.setUserName(resultSet.getString("UserName"));
                    usuarioDireccion.Usuario.setEmail(resultSet.getString("Email"));
                    usuarioDireccion.Usuario.setPassword(resultSet.getString("Password"));
                    usuarioDireccion.Usuario.setSexo(resultSet.getString("Sexo"));
                    usuarioDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                    usuarioDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                    usuarioDireccion.Usuario.setCURP(resultSet.getString("CURP"));
                    usuarioDireccion.Usuario.setImagen(resultSet.getString("Imagen"));

                    usuarioDireccion.Usuario.Roll = new Roll();
                    usuarioDireccion.Usuario.Roll.setIdRoll(resultSet.getInt("IdRoll"));
                    usuarioDireccion.Usuario.Roll.setNombre(resultSet.getString("nombreRoll"));

                    result.object = usuarioDireccion;

                }
                result.correct = false;
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
    //Actualizar Usuario 
    public Result UpdateUsuario(Usuario usuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("{CALL UsuarioUpdate(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                callableStatement.setDate(5, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(6, usuario.getUserName());
                callableStatement.setString(7, usuario.getEmail());
                callableStatement.setString(8, usuario.getPassword());
                callableStatement.setString(9, usuario.getSexo());
                callableStatement.setString(10, usuario.getTelefono());
                callableStatement.setString(11, usuario.getCelular());
                callableStatement.setInt(12, usuario.getStatus());
                callableStatement.setString(13, usuario.getImagen());
                callableStatement.setString(14, usuario.getCURP());
                callableStatement.setInt(15, usuario.Roll.getIdRoll());

                int rowsAffected = callableStatement.executeUpdate();

                if (rowsAffected > 0) {
                    result.correct = true;

                } else {
                    result.correct = false;
                    result.errorMessage = "Error en la actualizacion";
                }

                return 1;
            });

        } catch (Exception Ex) {
            result.correct = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;

        }
        return result;
    }
    //Eliminar usuario por id para la tabla de usuarios

    public Result UsuarioDelete(int IdUsuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL UsuarioDelete(?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);

                callableStatement.execute();

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
    public Result GetAllJPA() {

        Result result = new Result();

        try {
            //Lenguaje JPQL
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Usuario> queryUsuarios = entityManager.createQuery("FROM Usuario", com.example.ProgromacionNCapasXimena.JPA.Usuario.class);
            List<com.example.ProgromacionNCapasXimena.JPA.Usuario> usuarios = queryUsuarios.getResultList();

            result.objects = new ArrayList<>();

            for (com.example.ProgromacionNCapasXimena.JPA.Usuario usuario : usuarios) {

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

                usuarioDireccion.Usuario = new Usuario();

                usuarioDireccion.Usuario.setIdUsuario(usuario.getIdUsuario());
                usuarioDireccion.Usuario.setNombre(usuario.getNombre());
                usuarioDireccion.Usuario.setApellidoPaterno(usuario.getApellidoPaterno());
                usuarioDireccion.Usuario.setApellidoMaterno(usuario.getApellidoMaterno());
                usuarioDireccion.Usuario.setFechaNacimiento(usuario.getFechaNacimiento());
                usuarioDireccion.Usuario.setUserName(usuario.getUserName());
                usuarioDireccion.Usuario.setEmail(usuario.getEmail());
                usuarioDireccion.Usuario.setPassword(usuario.getPassword());
                usuarioDireccion.Usuario.setSexo(usuario.getSexo());
                usuarioDireccion.Usuario.setTelefono(usuario.getTelefono());
                usuarioDireccion.Usuario.setCelular(usuario.getCelular());
                usuarioDireccion.Usuario.setCURP(usuario.getCURP());
                usuarioDireccion.Usuario.setStatus(usuario.getStatus());
                usuarioDireccion.Usuario.setImagen(usuario.getImagen());

                usuarioDireccion.Usuario.Roll = new Roll();

                usuarioDireccion.Usuario.Roll.setIdRoll(usuario.Roll.getIdRoll());
                usuarioDireccion.Usuario.Roll.setNombre(usuario.Roll.getNombre());

                //obtener direcciones por idusuario
                TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Direccion> queryDireccion = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", com.example.ProgromacionNCapasXimena.JPA.Direccion.class);
                queryDireccion.setParameter("idusuario", usuario.getIdUsuario());

                List<com.example.ProgromacionNCapasXimena.JPA.Direccion> direccionesJPA = queryDireccion.getResultList();
                usuarioDireccion.Direcciones = new ArrayList();

                for (com.example.ProgromacionNCapasXimena.JPA.Direccion direccionJPA : direccionesJPA) {

                    Direccion direccion = new Direccion();

                    direccion.setCalle(direccionJPA.getCalle());
                    direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                    direccion.setNumeroExterior(direccionJPA.getNumeroExterior());

                    direccion.Colonia = new Colonia();

                    direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());
                    direccion.Colonia.setNombre(direccionJPA.Colonia.getNombre());
                    direccion.Colonia.setCodigoPostal(direccionJPA.Colonia.getCodigoPostal());

                    direccion.Colonia.Municipio = new Municipio();

                    direccion.Colonia.Municipio.setIdMunicipio(direccionJPA.Colonia.Municipio.getIdMunicipio());
                    direccion.Colonia.Municipio.setNombre(direccionJPA.Colonia.Municipio.getNombre());

                    direccion.Colonia.Municipio.Estado = new Estado();

                    direccion.Colonia.Municipio.Estado.setIdEstado(direccionJPA.getIdDireccion());
                    direccion.Colonia.Municipio.Estado.setNombre(direccionJPA.Colonia.Municipio.Estado.getNombre());

                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(direccionJPA.Colonia.Municipio.Estado.Pais.getIdPais());
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(direccionJPA.Colonia.Municipio.Estado.Pais.getNombre());

                    usuarioDireccion.Direcciones.add(direccion);
                }
                result.objects.add(usuarioDireccion);
            }
            result.correct = true;
        } catch (Exception Ex) {
            result.correct = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;
        }
        return result;
    }

    //entityManager.persist(alumnoJPA); para agregar
    //entityManager.merge(); para actualizar
    //merge actualiza con id != 0
    //merge agrega con id == 0
    //entityManager.remove(alumno);
    @Transactional
    @Override
    public Result AddJPA(UsuarioDireccion usuarioDireccion) {

        Result result = new Result();

        try {
            //Se llena usuarioJPA con usuarioML
            com.example.ProgromacionNCapasXimena.JPA.Usuario usuarioJPA = new com.example.ProgromacionNCapasXimena.JPA.Usuario();

            usuarioJPA.setNombre(usuarioDireccion.Usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuarioDireccion.Usuario.getNombre());
            usuarioJPA.setApellidoMaterno(usuarioDireccion.Usuario.getApellidoMaterno());
            usuarioJPA.setFechaNacimiento(usuarioDireccion.Usuario.getFechaNacimiento());
            usuarioJPA.setUserName(usuarioDireccion.Usuario.getUserName());
            usuarioJPA.setEmail(usuarioDireccion.Usuario.getEmail());
            usuarioJPA.setPassword(usuarioDireccion.Usuario.getPassword());
            usuarioJPA.setSexo(usuarioDireccion.Usuario.getSexo());
            usuarioJPA.setTelefono(usuarioDireccion.Usuario.getTelefono());
            usuarioJPA.setCelular(usuarioDireccion.Usuario.getCelular());
            usuarioJPA.setCURP(usuarioDireccion.Usuario.getCURP());
            usuarioJPA.setStatus(usuarioDireccion.Usuario.getStatus());
            usuarioJPA.setImagen(usuarioDireccion.Usuario.getImagen());

            usuarioJPA.Roll = new com.example.ProgromacionNCapasXimena.JPA.Roll();
            usuarioJPA.Roll.setIdRoll(usuarioDireccion.Usuario.Roll.getIdRoll());

            //No realiza uuna inserción directa sino que registra el objeto dentro de la persitencia
            //y la inserción se realiza cuando la transacción se confirme
            entityManager.persist(usuarioJPA);

            com.example.ProgromacionNCapasXimena.JPA.Direccion direccionJPA = new com.example.ProgromacionNCapasXimena.JPA.Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroInterior());

            direccionJPA.Colonia = new com.example.ProgromacionNCapasXimena.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());

            direccionJPA.Colonia.Municipio = new com.example.ProgromacionNCapasXimena.JPA.Municipio();
            direccionJPA.Colonia.Municipio.setIdMunicipio(usuarioDireccion.Direccion.Colonia.Municipio.getIdMunicipio());

            direccionJPA.Colonia.Municipio.Estado = new com.example.ProgromacionNCapasXimena.JPA.Estado();
            direccionJPA.Colonia.Municipio.Estado.setIdEstado(usuarioDireccion.Direccion.Colonia.Municipio.Estado.getIdEstado());

            direccionJPA.Colonia.Municipio.Estado.Pais = new com.example.ProgromacionNCapasXimena.JPA.Pais();
            direccionJPA.Colonia.Municipio.Estado.Pais.setIdPais(usuarioDireccion.Direccion.Colonia.Municipio.Estado.Pais.getIdPais());

            //se guarda la direccion insertada en el usuario que se insertó anteriormente
            direccionJPA.Usuario = usuarioJPA;

            entityManager.persist(direccionJPA);

            result.correct = true;

        } catch (Exception Ex) {
            result.object = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;

        }
        return result;

    }

    @Transactional
    @Override
    public Result UsuarioDeleteJPA(int IdUsuario) {

        Result result = new Result();

        try {
            //Se recuperan las direcciones delos usuarios para asi poder eliminarlas y consecutivamente eliminar el usuario
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Direccion> queryDireccionesUsuario = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :idusuario", com.example.ProgromacionNCapasXimena.JPA.Direccion.class);
            queryDireccionesUsuario.setParameter("idusuario", IdUsuario);
            List<com.example.ProgromacionNCapasXimena.JPA.Direccion> direccionesUsuario = queryDireccionesUsuario.getResultList();
            //Obtener direcciones por IdUsuario

            for (com.example.ProgromacionNCapasXimena.JPA.Direccion direccionJPA : direccionesUsuario) {

                //Ya no se obtienen los valores de direccion ya que como tal en direccionJPA se encuentra todo
                entityManager.remove(direccionJPA);
            }

            //se recupera el ID del Usuario
            com.example.ProgromacionNCapasXimena.JPA.Usuario usuarioJPA
                    = entityManager.find(com.example.ProgromacionNCapasXimena.JPA.Usuario.class, IdUsuario);

            entityManager.remove(usuarioJPA);

            return result;

        } catch (Exception Ex) {
            result.object = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;

        }
        return result;

    }

    @Transactional
    @Override
    public Result UpdateUsuarioJPA(Usuario usuario) {

        Result result = new Result();

        try {
            com.example.ProgromacionNCapasXimena.JPA.Usuario usuarioJPA = new com.example.ProgromacionNCapasXimena.JPA.Usuario();
            usuarioJPA = entityManager.find(com.example.ProgromacionNCapasXimena.JPA.Usuario.class, usuario.getIdUsuario());

            //se vacea el usuario ML al usuario JPA
            usuarioJPA.setNombre(usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());
            usuarioJPA.setFechaNacimiento(usuario.getFechaNacimiento());
            usuarioJPA.setUserName(usuario.getUserName());
            usuarioJPA.setEmail(usuario.getEmail());
            usuarioJPA.setPassword(usuario.getPassword());
            usuarioJPA.setSexo(usuario.getSexo());
            usuarioJPA.setTelefono(usuario.getTelefono());
            usuarioJPA.setCelular(usuario.getCelular());
            usuarioJPA.setCURP(usuario.getCURP());
            usuarioJPA.setStatus(usuario.getStatus());
            usuarioJPA.setImagen(usuario.getImagen());

            usuarioJPA.Roll = new com.example.ProgromacionNCapasXimena.JPA.Roll();
            usuarioJPA.Roll.setIdRoll(usuario.Roll.getIdRoll());

            //
            entityManager.merge(usuarioJPA);

        } catch (Exception Ex) {
            result.object = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;
        }
        return result;
    }

    //busqueda dinamica
    @Override
    public Result GetAllDinamicoJPA(Usuario usuario) {
        Result result = new Result();
        try {
            //nombre, apellido paterno, apellido materno, roll, status

            String queryDinamico = "FROM Usuario";

            queryDinamico = queryDinamico + "WHERE Nombre = :nombre ";
            queryDinamico = queryDinamico + "AND ApellidoPaterno = :apellidopaterno";
            queryDinamico = queryDinamico + "AND ApellidoMaterno = :apellidomaterno";

            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Usuario> queryUsuario = entityManager.createQuery(queryDinamico, com.example.ProgromacionNCapasXimena.JPA.Usuario.class);
            queryUsuario.setParameter(":nombre", usuario.getNombre());
            queryUsuario.setParameter(":apellidopaterno", usuario.getApellidoPaterno());
            queryUsuario.setParameter(":apellidomaterno", usuario.getApellidoMaterno());

            List<com.example.ProgromacionNCapasXimena.JPA.Usuario> usuarios = queryUsuario.getResultList();

            result.correct = true;

        } catch (Exception Ex) {
            result.object = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;

        }

        return result;
    }

    @Override
    public Result GetByIdJPA(int IdUsuario) {

        Result result = new Result();

        try {
            com.example.ProgromacionNCapasXimena.JPA.Usuario usuarioById = new com.example.ProgromacionNCapasXimena.JPA.Usuario();
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Usuario> queryUsuarioById = entityManager.createQuery("FROM Usuario WHERE Usuario.IdUsuario = :idusuario", com.example.ProgromacionNCapasXimena.JPA.Usuario.class);

            List<com.example.ProgromacionNCapasXimena.JPA.Usuario> usuariosJPA = queryUsuarioById.getResultList();
            usuarioById = entityManager.find(com.example.ProgromacionNCapasXimena.JPA.Usuario.class, IdUsuario);

        } catch (Exception Ex) {
            result.object = false;
            result.errorMessage = Ex.getLocalizedMessage();
            result.ex = Ex;
        }
        return result;
    }

    @Override
    public Result DireccionByIdJPA(int IdUsuario) {
        Result result = new Result();

        try {
            com.example.ProgromacionNCapasXimena.JPA.Usuario DireccionByIdUsuario = new com.example.ProgromacionNCapasXimena.JPA.Usuario();
            TypedQuery<com.example.ProgromacionNCapasXimena.JPA.Usuario> querydireccionByIdUsuario = entityManager.createQuery("FROM Direccion WHERE Direccion.IdUsuario = :idusuario", com.example.ProgromacionNCapasXimena.JPA.Usuario.class);

        } catch (Exception Ex) {

        }
        return result;

    }

}
