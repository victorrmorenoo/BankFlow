package br.com.bankflow.domain;

import java.time.LocalDate;
import java.util.List;

public class Carteira {
    private Usuario usuario;
    private double saldo = 0;
    private List<Movimentacao> movimentacoes;
    private int proximoId = 1;

    public Carteira(Usuario usuario) {
        this.usuario = usuario;
        
    }

    public void registrarEntrada(double valor, LocalDate data, String descricao, Categoria categoria) {
        Entrada entrada = new Entrada(this.proximoId, valor, data, descricao, categoria);
        proximoId += 1;
        saldo += valor;
    }

    public void registrarSaida(double valor, LocalDate data, String descricao, Categoria categoria, FormaPagamento formaPagamento) {
        if (valor > saldo) {
            System.out.println("Você não pode gastar mais do que tem!");
            return;
        }
        Saida saida = new Saida(this.proximoId, valor, data, descricao, categoria, formaPagamento);
        proximoId += 1;
        saldo -= valor;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Movimentacao> getMovimentacoes() {
        return movimentacoes;
    }
}
