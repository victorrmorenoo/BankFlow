package br.com.bankflow.application;

import br.com.bankflow.domain.Categoria;
import br.com.bankflow.domain.Movimentacao;
import br.com.bankflow.domain.TipoCategoria;
import br.com.bankflow.domain.Usuario;

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

    public static double filtragemSaldo(Scanner sc, Usuario login) {
        double saldoFiltrado = 0;
        System.out.println("------ Escolha uma categoria ------");
        for (Categoria categoria : categoriasEntrada) {
            System.out.println("----------------");
            System.out.println(categoria.getId() + " - " + categoria.getNome());
        }
        System.out.println("----------------");
        System.out.print("Escolha uma categoria (por numero): ");
        int opcaoCategoria = sc.nextInt();

        if (opcaoCategoria > 0 && opcaoCategoria < categoriasEntrada.size()) {
            System.out.println("Categoria selecionada: "+categoriasEntrada.get(opcaoCategoria - 1).getNome());
            for (Movimentacao movimentacao : login.getCarteira().getMovimentacoes()) {
                if (movimentacao.getCategoria().getId() == opcaoCategoria) {
                    saldoFiltrado += movimentacao.getValor();
                }
            }
            return saldoFiltrado;
        } else {
            System.out.println("Categoria não encontrada");
            return 0;
        }
    }

}
