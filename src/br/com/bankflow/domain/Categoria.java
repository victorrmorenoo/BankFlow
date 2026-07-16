package br.com.bankflow.domain;

public class Categoria {
    private int id;
    private String nome;
    private TipoCategoria tipo;

    public Categoria(int id, String nome, TipoCategoria tipo){
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoCategoria getTipo() {
        return tipo;
    }
}
