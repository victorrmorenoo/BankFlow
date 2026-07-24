package br.com.bankflow.util;

import java.util.Scanner;

public class ValidadorCampoObrigatorio {
    public static String lerCampoObrigatorio(Scanner sc, String pergunta, String mensagemErro) {
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
}
