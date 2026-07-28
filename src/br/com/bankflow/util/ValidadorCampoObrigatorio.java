package br.com.bankflow.util;

import br.com.bankflow.domain.FormaPagamento;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

public class ValidadorCampoObrigatorio {
    public static String lerCampoObrigatorioTexto(Scanner sc, String pergunta, String mensagemErro) {
        while (true) {
            System.out.print(pergunta);
            String valor = sc.nextLine();
            if (valor.isBlank()) {
                System.out.println(mensagemErro);
                continue;
            }
            return valor;
        }
    }

    public static double lerCampoObrigatorioDouble(Scanner sc, String pergunta, String mensagemErro) {
        while (true) {
            System.out.print(pergunta);
            double valor = sc.nextDouble();
            sc.nextLine();
            if (valor <= 0) {
                System.out.println(mensagemErro);
                continue;
            }
            return valor;
        }
    }

    public static LocalDate verificacaoData(Scanner sc, String tipo) {
        while (true) {
            System.out.print("Digite o ano da " + tipo + ": ");
            int ano = sc.nextInt();
            System.out.print("Digite o mês da " + tipo + ": ");
            int mes = sc.nextInt();
            System.out.print("Digite o dia da " + tipo + ": ");
            int dia = sc.nextInt();
            sc.nextLine();

            try {
                return LocalDate.of(ano, mes, dia);
            } catch (DateTimeException e) {
                System.out.println("Data inválida, tente novamente.\n");
            }
        }
    }

    public static String lerSenha(Scanner sc, String pergunta, String mensagemErro) {
        while (true) {
            System.out.print(pergunta);
            String valor = sc.nextLine();
            if (valor.length() < 8 || valor.isBlank()) {
                System.out.println(mensagemErro);
                continue;
            }
            return valor;
        }
    }

    public static FormaPagamento validarFormaPagamento(Scanner sc) {
        while (true) {
            System.out.println("------ Forma de pagamento ------");
            System.out.println("1 - Dinheiro\n2 - Cartão de crédito\n3 - Cartão de débito\n4 - Pix\n5 - Transferência\n");
            System.out.print("Escolha uma opção: ");
            int opcaoPagamento = sc.nextInt();
            sc.nextLine();
            switch (opcaoPagamento) {
                case 1 -> {
                    return FormaPagamento.DINHEIRO;
                }
                case 2 -> {
                    return FormaPagamento.CARTAO_CREDITO;
                }
                case 3 -> {
                    return FormaPagamento.CARTAO_DEBITO;
                }
                case 4 -> {
                    return FormaPagamento.PIX;
                }
                case 5 -> {
                    return FormaPagamento.TRANSFERENCIA;
                }
                default -> {
                    System.out.println("Opção inválida");
                }
            }
        }
    }
}
