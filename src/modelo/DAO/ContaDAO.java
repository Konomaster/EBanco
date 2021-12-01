/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.DAO;

import java.util.ArrayList;
import modelo.Beans.Conta;

/**
 *
 * @author Pedro Souza
 */
public class ContaDAO {
    
    ArrayList<Conta> lista = new ArrayList();
    
    public int salvar(String nome, int id, String senha){//CREATE
        Conta c = new Conta(nome,id,senha);
        try{
            lista.add(c);
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    
    public ArrayList<Conta> ler(){//READ
        return lista;
    }
    
    public Conta ler(int id){//READ
        Conta c = new Conta();
               
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getId() == id){
                return lista.get(i);
            }
        }
        c.setId(-1);
        return c;
    }
    
    public int atualizar(String nome, int id, String senha){//UPDATE
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getId() == id){
                lista.get(i).setNome(nome);
                lista.get(i).setSenha(senha);
                return 1;
            }
        }
        return 0;
    }
    
    public int deletar(int id){//DELETE
        for (int i = 0; i < lista.size(); i++) {
            if(lista.get(i).getId() == id){
                lista.remove(i);
                return 1;
            }
        }
        return 0;
    }
}