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
        GerenciarCategorias.inicializarCategorias();

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
                    System.out.println("\n");
                }
                case 2 -> {
                    login = loginUsuario(sc);
                    System.out.println("\n");
                }
                case 3 -> {
                    System.out.println("Saindo...");
                    break loopAutenticar;
                }
                default -> {
                    System.out.println("Opção inválida");
                    continue;
                }
            }

            loopMenuPrincipal:
            while (login != null) {
                System.out.println("------ Menu Principal ------");
                System.out.println("1 - Cadastrar Entrada\n2 - Cadastrar Saída\n3 - Consultar Saldo\n4 - Consultar Movimentações\n5 - Gerar relatório\n6 - Sair da conta");
                System.out.print("Digite a opção escolhida: ");
                int opcaoMenu = sc.nextInt();

                switch(opcaoMenu){
                    case 1 -> {
                        cadastrarEntrada(sc, login);
                    }
                    case 6 -> {
                        System.out.println("Saindo da conta...");
                        login = null;
                    }
                    default -> {
                        System.out.println("Opção inválida");
                    }
                }
            }
        }
    }

    public static void cadastrarUsuario(Scanner sc) {
        String nome = ValidadorCampoObrigatorio.lerCampoObrigatorioTexto(sc, "Digite seu nome: ", "O nome deve ser preenchido!");
        String sobrenome = ValidadorCampoObrigatorio.lerCampoObrigatorioTexto(sc, "Digite seu sobrenome: ", "O sobrenome deve ser preenchido!");
        String email = ValidadorCampoObrigatorio.lerCampoObrigatorioTexto(sc, "Digite seu email: ", "O email deve ser preenchido");
        String senha = ValidadorCampoObrigatorio.lerSenha(sc, "Digite sua senha: ", "A senha deve ter no mínimo 8 caracteres");

        GerenciarUsuarios.cadastrarUsuario(nome, sobrenome, email, senha);
    }

    public static Usuario loginUsuario(Scanner sc) {
        String email = ValidadorCampoObrigatorio.lerCampoObrigatorioTexto(sc, "Digite seu email: ", "O email deve ser preenchido");
        String senha = ValidadorCampoObrigatorio.lerCampoObrigatorioTexto(sc, "Digite sua senha: ", "A senha deve ser preenchida");

        return GerenciarUsuarios.loginUsuario(email, senha);
    }

    public static void cadastrarEntrada(Scanner sc, Usuario login){
        double valor = ValidadorCampoObrigatorio.lerCampoObrigatorioDouble(sc, "Digite o valor da entrada: ", "O valor deve ser maior que zero");
        LocalDate data = ValidadorCampoObrigatorio.verificacaoData(sc, "entrada");
        sc.nextLine();
        String descricao = ValidadorCampoObrigatorio.lerCampoObrigatorioTexto(sc, "Digite a descrição da entrada: ", "A descrição deve ser preenchida");
        Categoria categoria = GerenciarCategorias.escolherCategoria(sc, TipoCategoria.ENTRADA);

        login.getCarteira().registrarEntrada(valor, data, descricao, categoria);
    }
}
