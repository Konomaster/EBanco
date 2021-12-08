/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Pedro Souza
 */
public interface InterfaceModelo extends Remote {
    
    public boolean isNomeUnico(String nome) throws RemoteException;//Objeto string como parâmetro
    public boolean criarConta(String nome, String senha) throws RemoteException;
    public int retornaSaldo(String nome) throws RemoteException;
    public boolean verifica(String nome, String senha)throws RemoteException;
    
}
