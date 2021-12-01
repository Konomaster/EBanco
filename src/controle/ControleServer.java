/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.*;

/**
 *
 * @author pedro
 */

public class ControleServer extends UnicastRemoteObject implements InterfaceControle{
    
    public void iniciar(){
        try { 
            // Cria um objeto do tipo da classe CalculadoraServer. 
            ControleServer obj = new ControleServer(); 

            // Liga (bind) esta instancia de objeto ao nome "Calculadora" no registro RMI no localhost
            //Naming.rebind("Calculadora", obj); // isto equivale ao comando completo abaixo:
            Naming.rebind("rmi://localhost/ServerControle", obj); 

            // Liga (bind) esta instancia de objeto ao nome "Calculadora" em um registro RMI contido em outra máquina
            //Naming.rebind("rmi://172.16.2.222:1099/Calculadora", obj); 

            //DEBUG
            System.out.println("Server >> ligado no registro RMI sob o nome ServerControle"); 

        } 
        catch (Exception erro) { 
            //DEBUG
            System.out.println("ERRO: CalculadoraServer " + erro.getMessage()); 
            erro.printStackTrace(); 
        }
    }

    @Override
    public boolean isNomeUnico() throws RemoteException {
        return true;
    }
}
