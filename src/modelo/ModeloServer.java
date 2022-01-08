/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import modelo.Beans.Conta;
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
    
    public Conta criarConta(String nome, String senha) throws RemoteException{
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

    public double retornaSaldo(int id) throws RemoteException {
        
        double saldo = 0;
        
        for(int i = 0; i < contadao.lista.size(); i++){
            if(contadao.lista.get(i).getId() == id){
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
    
    public ArrayList<String> retornoExtrato(boolean flag, int id)throws RemoteException{
        
        ArrayList<String> retorno=new ArrayList<String>();
        if(contadao.lista.size()>0 && contadao.ler(id).getId()==id){
            retorno=contadao.ler(id).getMovimentacoes(flag, id);
        }
        return retorno;
    }
    
    public String transfereSaldo(String remetente, String destino, double saldo, String data) throws RemoteException{
        
    // Transferir dinheiro, e guardar String de movimentação em ambas as contas
    
        Conta conta1 = null, conta2 = null;
        String comprovante1 = "";
        String comprovante2 = "";
        
        for(int i = 0; i < 2 ; i++){
            
            for(int j = 0; j < contadao.lista.size(); j++){
                if(contadao.lista.get(j).getId()==Integer.parseInt(destino)){

                    conta1 = contadao.lista.get(j);

                }
                else if(contadao.lista.get(j).getId()==Integer.parseInt(remetente)){
                    conta2 = contadao.lista.get(j);
                }
            }
        }
        
        conta1.setSaldo(conta1.getSaldo() + saldo);
        conta2.setSaldo(conta1.getSaldo() - saldo);
        
        comprovante1 = "****\nRecebido\nDia "+data+":\n De: "+remetente+"\n"+"Para:"+destino+"\nValor: "+saldo;
        comprovante2 = "****\nEnviado\nDia "+data+":\n De: "+remetente+"\n"+"Para:"+destino+"\nValor: "+saldo;
        
        conta1.setMovimentacoes(comprovante1);
        conta2.setMovimentacoes(comprovante2);
        
        
        return "Transferência realizada com sucesso";
    }

    @Override
    public int retornaSaldo(String nome) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}