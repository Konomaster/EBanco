/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controle.ControleServer;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.DAO.ContaDAO;

/*
 * @author Pedro Souza
 */
public class ModeloServer extends UnicastRemoteObject implements InterfaceModelo{

    private EbancoModelo ebancomodelo;
    private ContaDAO contadao;
    
    public ModeloServer() throws RemoteException{
        super();
        contadao = new ContaDAO();
    }
    
//    public static void main(String[] args){
//        ModeloServer newModelo;
//        try {
//            newModelo = new ModeloServer();
//            newModelo.iniciar();
//        } catch (RemoteException ex) {
//            Logger.getLogger(ControleServer.class.getName()).log(Level.SEVERE, null, ex);
//        }  
//    }
      
    public void iniciar(){
        
        try { 
            // Cria um objeto do tipo da classe CalculadoraServer. 
            ModeloServer obj = new ModeloServer(); 

            // Liga (bind) esta instancia de objeto ao nome "Calculadora" no registro RMI no localhost
            //Naming.rebind("Calculadora", obj); // isto equivale ao comando completo abaixo:
            Naming.rebind("rmi://localhost/ServerModelo", obj); 

            // Liga (bind) esta instancia de objeto ao nome "Calculadora" em um registro RMI contido em outra máquina
            //Naming.rebind("rmi://172.16.2.222:1099/Calculadora", obj); 

            //DEBUG
            System.out.println("Modelo >> ligado no registro RMI sob o nome ServerModelo"); 

        } 
        catch (Exception erro) { 
            //DEBUG
            System.out.println("ERRO: ModeloServer " + erro.getMessage()); 
            erro.printStackTrace(); 
        }
    }
    
    public boolean criarConta(String nome, String senha) throws RemoteException{
        return this.contadao.salvar(nome,senha);
    }
    
    public boolean isNomeUnico(String nome) throws RemoteException {
        
        if(contadao.pesquisar(nome) == -1){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int retornaSaldo(String nome) throws RemoteException {
        int saldo = 0;
        for(int i = 0; i < contadao.lista.size(); i++){
            if(contadao.lista.get(i).getNome().equals(nome)){
                saldo = contadao.lista.get(i).getSaldo();
            }
        }
        return saldo;
    }

    @Override
    public boolean verifica(String nome, String senha) throws RemoteException {
        for(int i = 0; i < contadao.lista.size(); i++){
            if(contadao.lista.get(i).getNome().equals(nome) && contadao.lista.get(i).getSenha().equals(senha)){
               return true;
            }
        }
        return false;
    }
    
    
}