package br.com.bankflow.application;

import br.com.bankflow.domain.Categoria;
import br.com.bankflow.domain.FormaPagamento;
import br.com.bankflow.domain.TipoCategoria;
import br.com.bankflow.domain.Usuario;
import br.com.bankflow.util.ValidadorCampoObrigatorio;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.Locale;
import java.text.NumberFormat;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Usuario login = null;
        Locale localeBR = new Locale("pt", "BR");
        NumberFormat formatoMoeda = NumberFormat.getCurrencyInstance(localeBR);
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

                switch (opcaoMenu) {
                    case 1 -> {
                        boolean sucesso = cadastrarEntrada(sc, login);
                        if (sucesso) {
                            System.out.println("Entrada registrada com sucesso!");
                        } else {
                            System.out.println("Valor inválido");
                        }
                    }
                    case 2 -> {
                        boolean sucesso = cadastrarSaida(sc, login);
                        if (sucesso) {
                            System.out.println("Saída registrada com sucesso!");
                        } else {
                            System.out.println("Valor inválido");
                        }
                    }
                    case 3 -> {
                        consultarSaldo(sc, login, formatoMoeda);
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

    public static boolean cadastrarEntrada(Scanner sc, Usuario login) {
        double valor = ValidadorCampoObrigatorio.lerCampoObrigatorioDouble(sc, "Digite o valor da entrada: ", "O valor deve ser maior que zero");
        LocalDate data = ValidadorCampoObrigatorio.verificacaoData(sc, "entrada");
        String descricao = ValidadorCampoObrigatorio.lerCampoObrigatorioTexto(sc, "Digite a descrição da entrada: ", "A descrição deve ser preenchida");
        Categoria categoria = GerenciarCategorias.escolherCategoria(sc, TipoCategoria.ENTRADA);

        return login.getCarteira().registrarEntrada(valor, data, descricao, categoria);
    }

    public static boolean cadastrarSaida(Scanner sc, Usuario login) {
        double valor = ValidadorCampoObrigatorio.lerCampoObrigatorioDouble(sc, "Digite o valor da saída: ", "O valor deve ser maior que zero");
        LocalDate data = ValidadorCampoObrigatorio.verificacaoData(sc, "saída");
        String descricao = ValidadorCampoObrigatorio.lerCampoObrigatorioTexto(sc, "Digite a descrição da saída: ", "A descrição deve ser preenchida");
        Categoria categoria = GerenciarCategorias.escolherCategoria(sc, TipoCategoria.SAIDA);
        FormaPagamento formaPagamento = ValidadorCampoObrigatorio.validarFormaPagamento(sc);

        return login.getCarteira().registrarSaida(valor, data, descricao, categoria, formaPagamento);
    }

    public static void consultarSaldo(Scanner sc, Usuario login, NumberFormat formatoMoeda) {
        System.out.println("Deseja filtrar a consulta?");
        System.out.println("1 - Sim\n2 - Não");
        System.out.print("Digite sua resposta: ");
        int opcaoFiltro = sc.nextInt();
        switch (opcaoFiltro) {
            case 1 -> {
                TipoCategoria tipo;
                loopTipo:
                while (true) {
                    System.out.println("Deseja filtrar por entrada ou por saída?");
                    System.out.println("1 - Entrada\n2 - Saída");
                    System.out.print("Digite sua resposta: ");
                    int opcaoTipo = sc.nextInt();
                    switch (opcaoTipo) {
                        case 1 -> {
                            tipo = TipoCategoria.ENTRADA;
                            break loopTipo;
                        }
                        case 2 -> {
                            tipo = TipoCategoria.SAIDA;
                            break loopTipo;
                        }
                        default -> {
                            System.out.println("Opção inválida");
                        }
                    }
                }

                System.out.println("Total movimentado em " + formatoMoeda.format(GerenciarCategorias.filtragemSaldo(sc, login, tipo)));
            }
            case 2 -> {
                System.out.println("Seu saldo atual é: " + formatoMoeda.format(login.getCarteira().getSaldo()));
            }
            default -> {
                System.out.println("Opção inválida");
            }
        }
    }
}
