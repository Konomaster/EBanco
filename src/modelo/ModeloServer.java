/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*
 * @author Pedro Souza
 */
public class ModeloServer extends UnicastRemoteObject implements InterfaceModelo{

    private EbancoModelo ebancomodelo;
    
    public ModeloServer() throws RemoteException{
        super();
    }
    
    public boolean isNomeUnico() throws RemoteException {
        
        return true;
    }
    
    public void iniciar(EbancoModelo eb){
        
        ebancomodelo = eb;
        
        try { 
            // Cria um objeto do tipo da classe CalculadoraServer. 
            ModeloServer obj = new ModeloServer(); 

            // Liga (bind) esta instancia de objeto ao nome "Calculadora" no registro RMI no localhost
            //Naming.rebind("Calculadora", obj); // isto equivale ao comando completo abaixo:
            Naming.rebind("rmi://localhost/ModeloControle", obj); 

            // Liga (bind) esta instancia de objeto ao nome "Calculadora" em um registro RMI contido em outra máquina
            //Naming.rebind("rmi://172.16.2.222:1099/Calculadora", obj); 

            //DEBUG
            System.out.println("Modelo >> ligado no registro RMI sob o nome ServerControle"); 

        } 
        catch (Exception erro) { 
            //DEBUG
            System.out.println("ERRO: ModeloServer " + erro.getMessage()); 
            erro.printStackTrace(); 
        }
    }
    
}
