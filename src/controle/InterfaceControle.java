/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.rmi.*;


/**
 *
 * @author pedro
 */
public interface InterfaceControle extends Remote {
    
    public boolean solicitaCriacao(String nome, String senha) throws RemoteException;
    public int solicitaConsulta(String nome) throws RemoteException;
    public void solicitaTransferencia() throws RemoteException;
    public boolean autenticacao(String nome, String senha) throws RemoteException;
    public boolean login(String id, String senha) throws RemoteException;
    public String consultaNome(String id) throws RemoteException;
}
