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

        Conta resultado = new Conta();
        resultado.setId(-1);
        try {
            InterfaceModelo im = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
            boolean verificaConta = im.isNomeUnico(nome);//receba parametros

            if (verificaConta) {
                //digitar informações e passa-la por parametro, nome e senha - id gera depois
                resultado = im.criarConta(nome, senha);
            }
        } catch (Exception erro) {
        }
        return resultado;
    }

    @Override
    public double solicitaSaldo(int id) throws RemoteException {
        double saldo = -1;
        try {

            InterfaceModelo ic = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
            saldo = ic.retornaSaldo(id);

        } catch (Exception ex) {

        }
        return saldo;
    }

    @Override
    public ArrayList<String> solicitaExtrato(boolean flag, int id) throws RemoteException {
        // Filtrar se serão retornadas 5 ou length total das transferencias
        ArrayList<String> retorno = new ArrayList<String>();
        try {

            InterfaceModelo ic = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
            retorno = ic.retornoExtrato(flag, id);

        } catch (Exception ex) {

        }
        return retorno;
    }

    @Override
    public boolean autenticacao(int id, String senha) throws RemoteException {
        boolean confere = false;
        try {

            InterfaceModelo ic = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
            confere = ic.verifica(id, senha);
        } catch (Exception ex) {

        }
        return confere;
    }

    public boolean login(String id, String senha) throws RemoteException {

        try {
            InterfaceModelo ic = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
            return ic.verifica(Integer.parseInt(id), senha);
            //Salvar como: Arraylist de String de logged users
        } catch (Exception e) {

        }
        return false;

    }

    public String consultaNome(String id) throws RemoteException {
        String nome = "";
        try {
            InterfaceModelo im = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
            int resultado = Integer.parseInt(id);
            nome = im.retornaNome(resultado);
            
            if(nome.equals("****")){
                return "Usuário não encontrado";
            }
        } catch (Exception ex) {
        
        }
        return "Essa conta pertence ao usuario"+ nome;

    }

    public int transfereSaldo(String remetente, String destino, int saldo) throws RemoteException {

        GregorianCalendar gc = new GregorianCalendar();
        Date dataOp = gc.getTime();
        int retorno = 1;
        int resultado = 1;

        if (!remetente.equals(destino)) {

            try {
                InterfaceModelo im = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");

                resultado = im.transfereSaldo(remetente, destino, saldo, dataOp.toString());
                if (resultado == 0) {
                    retorno = 0;
                }
            } catch (Exception e) {

            }
        }
        return retorno;
    }

    //quando tiver jgroups tem que retornar o montante visto por todos os membros
    //do cluster de modelo
    public String montante() throws RemoteException {
        
        //Numero de contas e valor total da soma delas
        String retorno = "";
        try {
            InterfaceModelo im = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");

            retorno = im.montante();
        } catch (Exception e) {

        }
        return retorno;
    }
}
