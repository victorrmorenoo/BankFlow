package br.com.bankflow.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carteira {
    private Usuario usuario;
    private double saldo = 0;
    private List<Movimentacao> movimentacoes;
    private int proximoId = 1;

    public Carteira(Usuario usuario) {
        this.usuario = usuario;
        this.movimentacoes = new ArrayList<>();
    }

    public boolean registrarEntrada(double valor, LocalDate data, String descricao, Categoria categoria) {
        if (valor <= 0) {
            return false;
        }
        Entrada entrada = new Entrada(this.proximoId, valor, data, descricao, categoria);
        this.movimentacoes.add(entrada);
        proximoId += 1;
        saldo += valor;
        return true;
    }

    public boolean registrarSaida(double valor, LocalDate data, String descricao, Categoria categoria, FormaPagamento formaPagamento) {
        if (valor > saldo || valor <= 0) {
            return false;
        }
        Saida saida = new Saida(this.proximoId, valor, data, descricao, categoria, formaPagamento);
        this.movimentacoes.add(saida);
        proximoId += 1;
        saldo -= valor;
        return true;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Movimentacao> getMovimentacoes() {
        return Collections.unmodifiableList(movimentacoes);
    }
}
