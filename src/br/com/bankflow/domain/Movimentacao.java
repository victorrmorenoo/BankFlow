package br.com.bankflow.domain;

import java.time.LocalDate;

public class Movimentacao {
    private int id;
    private double valor;
    private LocalDate data;
    private String descricao;
    private Categoria categoria;

    public Movimentacao(int id, double valor, LocalDate data, String descricao, Categoria categoria) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }
}
