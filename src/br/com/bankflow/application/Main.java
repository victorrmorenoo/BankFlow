package br.com.bankflow.application;

import br.com.bankflow.domain.Categoria;
import br.com.bankflow.domain.FormaPagamento;
import br.com.bankflow.domain.TipoCategoria;
import br.com.bankflow.domain.Usuario;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        GerenciarUsuarios.cadastrarUsuario("victoormorenoo20@gmail.com", "123");
    }
}
