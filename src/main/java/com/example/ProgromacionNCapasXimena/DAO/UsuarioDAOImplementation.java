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
    private JdbcTemplate jdbcTemplate;

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
        public Result Add
        (UsuarioDireccion usuarioDireccion
        
            ) {
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
        public Result DireccionById
        (int IdUsuario
        
            ) {
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
        public Result GetById
        (int IdUsuario
        
            ) {
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
                        usuarioDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuarioDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                        usuarioDireccion.Usuario.setCelular(resultSet.getString("Celular"));
                        usuarioDireccion.Usuario.setEmail(resultSet.getString("Email"));
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
        public Result UpdateUsuario
        (Usuario usuario
        
            ) {
        Result result = new Result();

            try {
                jdbcTemplate.execute("CALL UsuarioUpdate(?,?,?,?,?,?,?,?,?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
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
                    callableStatement.setString(12, usuario.getCURP());
                    callableStatement.setInt(13, usuario.Roll.getIdRoll());

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

}
