package modelo.Beans;

import java.util.ArrayList;

/**
 *
 * @author Pedro Souza
 */

public class Conta {
    
    String nome;
    int id;
    String senha;
    int saldo = 0;
    
    public Conta(String nome, String senha) {
        this.nome = nome;
        this.id = -1;
        this.senha = senha;
        this.saldo = 1000;
    }
    
    public Conta() {
     
    }
     
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
         
}