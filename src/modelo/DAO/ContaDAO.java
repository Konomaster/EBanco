package modelo.DAO;

import java.util.ArrayList;
import modelo.Beans.Conta;
import org.jgroups.JChannel;
import org.jgroups.blocks.atomic.Counter;
import org.jgroups.blocks.atomic.CounterService;

public class ContaDAO {
    
    public ArrayList<Conta> lista = new ArrayList();
    Counter counter;
    
    public Conta salvar(String nome, String senha){//CREATE
        Conta c = null;
        
        try{
            JChannel ch = new JChannel("config.xml");
            CounterService counter_service = new CounterService(ch);
            ch.connect("counter-cluster");
            counter = counter_service.getOrCreateCounter("mycounter", 1);
            c = new Conta(nome,senha);
        
            lista.add(c);
            int id = (int)counter.get();
            c.setId(id);
            counter.set(counter.get()+1);
            
        }catch(Exception e){
            e.printStackTrace();
            return c;
        }
        return c;
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
    
    public int pesquisar(String nome){
        
        for(int i = 0; i < lista.size(); i++){
            if(nome.equals(lista.get(i).getNome())){
                return lista.get(i).getId();
            }
        }
        return -1;
    }
}