package br.com.bankflow.application;

import br.com.bankflow.domain.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GerenciarUsuarios {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static int proximoId = 1;


    public static void cadastrarUsuario(String nome, String sobrenome, String email, String senha){
        for (Usuario usuario : usuarios){
            if(usuario.getEmail().equals(email)){
                System.out.println("Já existe um cadastro com esse email");
                return;
            }
        }
        Usuario usuario = new Usuario(proximoId, nome, sobrenome, email, senha);
        usuarios.add(usuario);
        proximoId += 1;
    }

    public static Usuario loginUsuario(String email, String senha){
        for (Usuario usuario: usuarios){
            if(usuario.getEmail().equals(email) && usuario.autenticarSenha(senha)){
                return usuario;
            }
        }
        return null;
    }
}
