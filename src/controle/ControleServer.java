package controle;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Beans.Conta;
import modelo.InterfaceModelo;

public class ControleServer extends UnicastRemoteObject implements InterfaceControle {

    public ControleServer() throws RemoteException {
        super();
    }

//    public static void main(String[] args){
//        ControleServer newControle;
//        try {
//            newControle = new ControleServer();
//            newControle.iniciar();
//        } catch (RemoteException ex) {
//            Logger.getLogger(ControleServer.class.getName()).log(Level.SEVERE, null, ex);
//        }  
//    }
    public void iniciar() {
        try {
            // Cria um objeto do tipo da classe CalculadoraServer. 
            ControleServer obj = new ControleServer();

            // Liga (bind) esta instancia de objeto ao nome "Calculadora" no registro RMI no localhost
            //Naming.rebind("Calculadora", obj); // isto equivale ao comando completo abaixo:
            Naming.rebind("rmi://localhost/ServerControle", obj);

            // Liga (bind) esta instancia de objeto ao nome "Calculadora" em um registro RMI contido em outra máquina
            //Naming.rebind("rmi://172.16.2.222:1099/Calculadora", obj); 
            //DEBUG
            System.out.println("Controle >> ligado no registro RMI sob o nome ServerControle");

        } catch (Exception erro) {
            //DEBUG
            System.out.println("ERRO: ControleServer " + erro.getMessage());
            erro.printStackTrace();
        }
    }

    public Conta solicitaCriacao(String nome, String senha) throws RemoteException {

        Conta resultado;
        try {
            InterfaceModelo im = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
            boolean verificaConta = im.isNomeUnico(nome);//receba parametros

            if (verificaConta) {
                //digitar informações e passa-la por parametro, nome e senha - id gera depois
                resultado = im.criarConta(nome, senha);
                return resultado;
            } else {
                resultado = new Conta();
                resultado.setId(-1);
                //System.out.println("Esse nome já existe");
                return resultado;

            }
        } catch (Exception erro) {
            //DEBUG
            System.out.println("ERRO: ControleServer " + erro.getMessage());
            erro.printStackTrace();
        }
        return null;
    }

    @Override
    public double solicitaConsulta(String nome) throws RemoteException {
        try {
            double saldo;
            InterfaceModelo ic = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
            saldo = ic.retornaSaldo(nome);
            
            return saldo;
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(ControleServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public ArrayList<String> solicitaExtrato(boolean flag, int id) throws RemoteException {
        // Filtrar se serão retornadas 5 ou length total das transferencias
        try {

            InterfaceModelo ic = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
            return ic.retornoExtrato(flag, id);

        } catch (NotBoundException ex) {
            Logger.getLogger(ControleServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ControleServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean autenticacao(String nome, String senha) throws RemoteException {
        try {
            boolean confere;
            InterfaceModelo ic = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
            confere = ic.verifica(nome, senha);
            return confere;
        } catch (NotBoundException | MalformedURLException ex) {
            Logger.getLogger(ControleServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean login(String id, String senha) throws RemoteException {
        if (id.equals("1")) {
            return true;
        }
        return false;

    }

    public String consultaNome(String id) throws RemoteException {
        return "Teste";

    }

    public int transfereSaldo(String remetente, String destino, double saldo) throws RemoteException {
        
        GregorianCalendar gc = new GregorianCalendar();
        Date dataOp = gc.getTime();
        int retorno = 0;
        String resultado = "";

        try {
            InterfaceModelo im = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
            
            resultado = im.transfereSaldo(remetente, destino, saldo, dataOp.toString());
                    
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        if (destino.equals("0000")) {
            retorno = 0;
        } else if (destino.equals("1111")) {
            retorno = 1;
        }
        return retorno;
    }
}
