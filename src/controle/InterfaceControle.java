package controle;

import java.rmi.*;
import java.util.ArrayList;

public interface InterfaceControle extends Remote {
    
    public String solicitaCriacao(String nome, String senha) throws RemoteException;
    public double solicitaSaldo(int id) throws RemoteException;
    public ArrayList<String> solicitaExtrato(boolean flag, int id) throws RemoteException;
    public boolean autenticacao(int id, String senha) throws RemoteException;
    public boolean login(String id, String senha) throws RemoteException;
    public boolean logout(String id) throws RemoteException;
    public String consultaNome(String id) throws RemoteException;
    //aqui que pega a hora e faz o log da transferencia para ela poder ser consultada por extrato
    //tem que fazer com que o log na conta remetente e destino seja atualizado para a transferencia
    //antes de qualquer outra operacao ser feita
    public int transfereSaldo(String remetente ,String destino, double quantidade) throws RemoteException;
    public String montante() throws RemoteException;
    public ArrayList<String> buscaContaPorNome(String nome) throws RemoteException;
}
