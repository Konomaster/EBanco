package modelo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import modelo.Beans.Conta;

public interface InterfaceModelo extends Remote {
    
    public boolean isNomeUnico(String nome) throws RemoteException;//Objeto string como parâmetro
    public Conta criarConta(String nome, String senha) throws RemoteException;
    public double retornaSaldo(int id) throws RemoteException;
    public boolean verifica(int id, String senha)throws RemoteException;
    public ArrayList<String> retornoExtrato(boolean flag, int id)throws RemoteException;
    public int transfereSaldo(String remetente, String destino, double saldo, String data) throws RemoteException;
    public String retornaNome(int id) throws RemoteException;
    public ArrayList<String> buscaContaPorNome(String nome)throws RemoteException;
    public String montante() throws RemoteException;
    public int logout(int id) throws RemoteException;
    
}
