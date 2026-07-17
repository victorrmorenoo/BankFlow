package br.com.bankflow.domain;

import java.time.LocalDate;

public class Entrada  extends Movimentacao{

    public Entrada(int id, double valor, LocalDate data, String descricao, Categoria categoria) {
        super(id, valor, data, descricao, categoria);
    }
}
