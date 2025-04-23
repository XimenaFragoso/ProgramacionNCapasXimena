package com.example.ProgromacionNCapasXimena.DAO;

import com.example.ProgromacionNCapasXimena.ML.Result;
import com.example.ProgromacionNCapasXimena.ML.Usuario;
import com.example.ProgromacionNCapasXimena.ML.UsuarioDireccion;

public interface IUsuarioDAO {
    
    Result GetAll();   //metodo abstracto
    Result Add(UsuarioDireccion usuarioDireccion);
    Result DireccionById(int IdUsuario);
    Result GetById(int IdUsuario);
    Result UpdateUsuario(Usuario usuario); 
    Result UsuarioDelete(int IdUsuario);
    Result GetAllDinamico(Usuario usuario);
}
