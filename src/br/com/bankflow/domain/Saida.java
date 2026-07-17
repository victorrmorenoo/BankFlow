package br.com.bankflow.domain;

import java.time.LocalDate;

public class Saida extends Movimentacao {
    private FormaPagamento formaPagamento;

    public Saida(int id, double valor, LocalDate data, String descricao, Categoria categoria, FormaPagamento formaPagamento){
        super(id, valor, data, descricao, categoria);
        this.formaPagamento = formaPagamento;
    }
}
