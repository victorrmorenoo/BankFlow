package br.com.bankflow.application;

import br.com.bankflow.domain.Categoria;
import br.com.bankflow.domain.FormaPagamento;
import br.com.bankflow.domain.TipoCategoria;
import br.com.bankflow.domain.Usuario;
import br.com.bankflow.util.ValidadorCampoObrigatorio;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Usuario login = null;

        System.out.println("Bem vindo ao BankFlow!");
        loopAutenticar:
        while (true) {
            System.out.println("------ Cadastro/Login ------");
            System.out.println("1 - Cadastrar-se\n2 - Logar-se\n3 - Sair");
            System.out.print("Digite a opção escolhida: ");
            int opcaoAutenticacao = sc.nextInt();
            sc.nextLine();
            switch (opcaoAutenticacao) {
                case 1 -> {
                    cadastrarUsuario(sc);
                }
                default -> {
                    System.out.println("Opção inválida");
                    continue;
                }
            }
        }
    }

    public static void cadastrarUsuario(Scanner sc) {
        String nome = ValidadorCampoObrigatorio.lerCampoObrigatorio(sc, "Digite seu nome: ", "O nome deve ser preenchido!");
        String sobrenome = ValidadorCampoObrigatorio.lerCampoObrigatorio(sc, "Digite seu sobrenome: ", "O sobrenome deve ser preenchido!");
        String email = ValidadorCampoObrigatorio.lerCampoObrigatorio(sc, "Digite seu email: ", "O email deve ser preenchido");
        String senha = ValidadorCampoObrigatorio.lerSenha(sc, "Digite sua senha", "A senha deve ter no mínimo 8 caracteres");

        GerenciarUsuarios.cadastrarUsuario(nome, sobrenome, email, senha);
    }
}
