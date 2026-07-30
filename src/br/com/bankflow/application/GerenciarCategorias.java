package br.com.bankflow.application;

import br.com.bankflow.domain.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerenciarCategorias {
    private static final List<Categoria> categoriasEntrada = new ArrayList<>();
    private static final List<Categoria> categoriasSaida = new ArrayList<>();

    public static void inicializarCategorias() {
        //Inicializando categorias de entrada
        categoriasEntrada.add(new Categoria(1, "Salário", TipoCategoria.ENTRADA));
        categoriasEntrada.add(new Categoria(2, "Investimentos", TipoCategoria.ENTRADA));
        categoriasEntrada.add(new Categoria(3, "Freelance", TipoCategoria.ENTRADA));
        categoriasEntrada.add(new Categoria(4, "Presente/Doação", TipoCategoria.ENTRADA));

        //Inicializando categorias de saída
        categoriasSaida.add(new Categoria(5, "Alimentação", TipoCategoria.SAIDA));
        categoriasSaida.add(new Categoria(6, "Transporte", TipoCategoria.SAIDA));
        categoriasSaida.add(new Categoria(7, "Moradia", TipoCategoria.SAIDA));
        categoriasSaida.add(new Categoria(8, "Saúde", TipoCategoria.SAIDA));
        categoriasSaida.add(new Categoria(9, "Lazer", TipoCategoria.SAIDA));
        categoriasSaida.add(new Categoria(10, "Parcelas", TipoCategoria.SAIDA));
    }

    public static Categoria escolherCategoria(Scanner sc, TipoCategoria tipo) {
        while (true) {
            if (tipo == TipoCategoria.ENTRADA) {
                for (Categoria categoria : categoriasEntrada) {
                    System.out.println("----------------");
                    System.out.println(categoria.getId() + " - " + categoria.getNome());
                }
                System.out.println("----------------");
                System.out.print("Escolha uma categoria (por numero): ");
                int opcaoCategoria = sc.nextInt();

                if (opcaoCategoria < 1 || opcaoCategoria > categoriasEntrada.size()) {
                    System.out.println("Opção inválida");
                } else {
                    return categoriasEntrada.get(opcaoCategoria - 1);
                }
            } else if (tipo == TipoCategoria.SAIDA) {
                for (Categoria categoria : categoriasSaida) {
                    System.out.println("----------------");
                    System.out.println(categoria.getId() + " - " + categoria.getNome());
                }
                System.out.println("----------------");
                System.out.print("Escolha uma categoria (por numero): ");
                int opcaoCategoria = sc.nextInt();

                if (opcaoCategoria < 1 || opcaoCategoria > categoriasSaida.size()) {
                    System.out.println("Opção inválida");
                } else {
                    return categoriasSaida.get(opcaoCategoria - 1);
                }
            }
        }
    }

    public static double filtragemSaldo(Scanner sc, Usuario login, TipoCategoria tipo) {
        List<Categoria> lista;
        if (tipo == TipoCategoria.ENTRADA) {
            lista = categoriasEntrada;
        } else {
            lista = categoriasSaida;
        }
        double saldoFiltrado = 0;
        System.out.println("------ Escolha uma categoria ------");
        for (Categoria categoria : lista) {
            System.out.println("----------------");
            System.out.println(categoria.getId() + " - " + categoria.getNome());
        }
        System.out.println("----------------");
        System.out.print("Escolha uma categoria (por numero): ");
        int opcaoCategoria = sc.nextInt();

        if (opcaoCategoria > 0 && opcaoCategoria <= lista.size()) {
            System.out.println("Categoria selecionada: " + lista.get(opcaoCategoria - 1).getNome());
            Categoria categoriaSelecionada = lista.get(opcaoCategoria - 1);
            for (Movimentacao movimentacao : login.getCarteira().getMovimentacoes()) {
                if (movimentacao.getCategoria().getId() == categoriaSelecionada.getId()) {
                    saldoFiltrado += movimentacao.getValor();
                }
            }
            return saldoFiltrado;
        } else {
            System.out.println("Categoria não encontrada");
            return 0;
        }
    }

    public static void filtragemMovimentacoes(Usuario login, NumberFormat formatoMoeda) {
        List<Movimentacao> movimentacoes = login.getCarteira().getMovimentacoes();
        if (!movimentacoes.isEmpty()) {
            for (Movimentacao movimentacao : movimentacoes) {
                exibirMovimentacao(movimentacao, formatoMoeda);
            }
        } else {
            System.out.println("Nenhuma movimentação registrada");
        }
    }

    public static void filtragemMovimentacoes(Scanner sc, Usuario login, NumberFormat formatoMoeda, TipoCategoria tipo) {
        List<Categoria> lista;
        List<Movimentacao> movimentacoes = login.getCarteira().getMovimentacoes();
        if (tipo == TipoCategoria.ENTRADA) {
            lista = categoriasEntrada;
        } else {
            lista = categoriasSaida;
        }
        System.out.println("------ Escolha uma categoria ------");
        for (Categoria categoria : lista) {
            System.out.println("----------------");
            System.out.println(categoria.getId() + " - " + categoria.getNome());
        }
        System.out.println("----------------");
        System.out.print("Escolha uma categoria (por numero): ");
        int opcaoCategoria = sc.nextInt();

        if (opcaoCategoria > 0 && opcaoCategoria <= lista.size()) {
            System.out.println("Categoria selecionada: " + lista.get(opcaoCategoria - 1).getNome());
            Categoria categoriaSelecionada = lista.get(opcaoCategoria - 1);
            for (Movimentacao movimentacao : movimentacoes) {
                if (movimentacao.getCategoria().getId() == categoriaSelecionada.getId()) {
                    exibirMovimentacao(movimentacao, formatoMoeda);
                }
            }
        } else {
            System.out.println("Categoria não encontrada");
        }
    }

    public static void exibirMovimentacao(Movimentacao movimentacao, NumberFormat formatoMoeda) {
        System.out.println("------Movimentação número " + movimentacao.getId() + "-------");
        System.out.println("Valor Movimentado: " + formatoMoeda.format(movimentacao.getValor()));
        System.out.println("Data: " + movimentacao.getData());
        System.out.println("Descrição: " + movimentacao.getDescricao());
        System.out.println("Categoria: " + movimentacao.getCategoria().getNome());
        if (movimentacao instanceof Saida) {
            Saida saida = (Saida) movimentacao;
            System.out.println("Forma Pagamento: " + saida.getFormaPagamento());
        }
    }

    public static void gerarRelatorio(Scanner sc, Usuario login, NumberFormat formatomoeda) {
        List<Movimentacao> movimentacoes = login.getCarteira().getMovimentacoes();
        int anoEscolhido;
        int mesEscolhido;

        double totalEntrada = 0;
        double totalSaida = 0;
        double saldoPeriodo = 0;

        //Entrada
        double totalSalario = 0;
        double totalInvestimento = 0;
        double totalFreelance = 0;
        double totalPresenteDoacao = 0;

        //Saída
        double totalAlimentacao = 0;
        double totalTransporte = 0;
        double totalMoradia = 0;
        double totalSaude = 0;
        double totalLazer = 0;
        double totalParcelas = 0;


        while (true) {
            System.out.print("Escolha o ano: ");
            anoEscolhido = sc.nextInt();
            if (anoEscolhido < 2025) {
                System.out.println("Ano inválido, tente novamente");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Escolha o mês: ");
            mesEscolhido = sc.nextInt();
            if (mesEscolhido < 1 || mesEscolhido > 12) {
                System.out.println("Mês inválido, tente novamente");
                continue;
            }
            break;
        }

        for (Movimentacao movimentacao : movimentacoes) {
            if (movimentacao.getData().getYear() == anoEscolhido && movimentacao.getData().getMonthValue() == mesEscolhido) {
                if (movimentacao.getCategoria().getTipo() == TipoCategoria.ENTRADA) {
                    totalEntrada += movimentacao.getValor();
                    switch (movimentacao.getCategoria().getId()) {
                        case 1 -> {
                            totalSalario += movimentacao.getValor();
                        }
                        case 2 -> {
                            totalInvestimento += movimentacao.getValor();
                        }
                        case 3 -> {
                            totalFreelance += movimentacao.getValor();
                        }
                        case 4 -> {
                            totalPresenteDoacao += movimentacao.getValor();
                        }
                    }
                } else {
                    totalSaida += movimentacao.getValor();
                    switch (movimentacao.getCategoria().getId()) {
                        case 5 -> {
                            totalAlimentacao += movimentacao.getValor();
                        }
                        case 6 -> {
                            totalTransporte += movimentacao.getValor();
                        }
                        case 7 -> {
                            totalMoradia += movimentacao.getValor();
                        }
                        case 8 -> {
                            totalSaude += movimentacao.getValor();
                        }
                        case 9 -> {
                            totalLazer += movimentacao.getValor();
                        }
                        case 10 -> {
                            totalParcelas += movimentacao.getValor();
                        }
                    }
                }

            }
        }
        saldoPeriodo = totalEntrada - totalSaida;

        System.out.println("\n------- Relatório -------");
        System.out.println("Total de entradas: " + formatomoeda.format(totalEntrada));
        System.out.println("Total de saídas: " + formatomoeda.format(totalSaida));
        System.out.println("Saldo do período: " + formatomoeda.format(saldoPeriodo));
        if (totalEntrada > 0 || totalSaida > 0) {
            System.out.println("\n------- Por categoria -------");
        } else {
            System.out.println("\nNenhuma movimentação registrada");
        }
        if (totalSalario > 0) {
            System.out.println("Salário: " + formatomoeda.format(totalSalario));
        }
        if (totalInvestimento > 0) {
            System.out.println("Investimento: " + formatomoeda.format(totalInvestimento));
        }
        if (totalFreelance > 0) {
            System.out.println("Freelance: " + formatomoeda.format(totalFreelance));
        }
        if (totalPresenteDoacao > 0) {
            System.out.println("Presente/Doação: " + formatomoeda.format(totalPresenteDoacao));
        }
        if (totalAlimentacao > 0) {
            System.out.println("Alimentação: " + formatomoeda.format(totalAlimentacao));
        }
        if (totalTransporte > 0) {
            System.out.println("Transporte: " + formatomoeda.format(totalTransporte));
        }
        if (totalMoradia > 0) {
            System.out.println("Moradia: " + formatomoeda.format(totalMoradia));
        }
        if (totalSaude > 0) {
            System.out.println("Saúde: " + formatomoeda.format(totalSaude));
        }
        if (totalLazer > 0) {
            System.out.println("Lazer: " + formatomoeda.format(totalLazer));
        }
        if (totalParcelas > 0) {
            System.out.println("Parcelas: " + formatomoeda.format(totalParcelas));
        }
        System.out.println("\n");
    }
}
