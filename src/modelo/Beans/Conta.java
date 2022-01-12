package modelo.Beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Pedro Souza
 */
public class Conta implements Serializable {

    String nome;
    int id;
    String senha;
    int saldo = 0;
    ArrayList<String> movimentacoes = new ArrayList<String>();

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
        return this.id;
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

    public ArrayList<String> getMovimentacoes(boolean flag, int id) {

        ArrayList<String> retorno;

        if (flag) {

            int movimentacoes = this.movimentacoes.size();

            if (movimentacoes >= 5) {
                
                retorno = new ArrayList<String>();
                
                for (int i = 0; i < 5; i++) {
                    retorno.add(this.movimentacoes.get(i));
                }
                
            } else {
                retorno = this.movimentacoes;
            }
            
        }else{
            retorno = this.movimentacoes;
        }
        return retorno;
    }

    public void setMovimentacoes(String movimentacoes) {
        this.movimentacoes.add(movimentacoes);
    }
}
