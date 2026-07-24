package br.com.bankflow.domain;

public class Usuario {
    private int id;
    private String email;
    private String senha;
    private Carteira carteira;

    public Usuario(int id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.carteira = new Carteira(this);
    }

    public boolean autenticarSenha(String senha){
        return this.senha.equals(senha);
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Carteira getCarteira() {
        return carteira;
    }
}
