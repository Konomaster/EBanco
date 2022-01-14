package modelo;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import modelo.Beans.Conta;
import modelo.DAO.ContaDAO;

public class ModeloServer extends UnicastRemoteObject implements InterfaceModelo {

    private EbancoModelo ebancomodelo;
    private ContaDAO contadao;
    private ArrayList<String> logados = new ArrayList<String>();

    public ModeloServer() throws RemoteException {
        super();
        contadao = new ContaDAO();
    }

    public void iniciar() {

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

        } catch (Exception erro) {
            //DEBUG
            System.out.println("ERRO: ModeloServer " + erro.getMessage());
            erro.printStackTrace();
        }
    }

    public Conta criarConta(String nome, String senha) throws RemoteException {

        Conta conta = new Conta();
        conta.setId(-1);
        try {
            return this.contadao.salvar(nome, senha);
        } catch (Exception e) {

        }
        return conta;
    }

    public boolean isNomeUnico(String nome) throws RemoteException {

        if (contadao.pesquisar(nome) == -1) {
            return true;
        } else {
            return false;
        }
    }

    public double retornaSaldo(int id) throws RemoteException {

        double saldo = -1;

        for (int i = 0; i < contadao.lista.size(); i++) {
            if (contadao.lista.get(i).getId() == id) {
                saldo = contadao.lista.get(i).getSaldo();
            }
        }
        return saldo;
    }

    @Override
    public boolean verifica(int id, String senha) throws RemoteException {
        for (int i = 0; i < contadao.lista.size(); i++) {
            if (contadao.lista.get(i).getId() == id && contadao.lista.get(i).getSenha().equals(senha)) {
                salvaLogado(contadao.lista.get(i).getId());
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> retornoExtrato(boolean flag, int id) throws RemoteException {

        ArrayList<String> retorno = new ArrayList<String>();
        if (contadao.lista.size() > 0 && contadao.ler(id).getId() == id) {
            retorno = contadao.ler(id).getMovimentacoes(flag, id);
        }
        return retorno;
    }

    public int transfereSaldo(String remetente, String destino, double saldo, String data) throws RemoteException {

        // Transferir dinheiro, e guardar String de movimentação em ambas as contas
        try {
            Conta conta1 = null, conta2 = null;
            String comprovante1 = "";
            String comprovante2 = "";

            for (int i = 0; i < 2; i++) {

                for (int j = 0; j < contadao.lista.size(); j++) {

                    if (contadao.lista.get(j).getId() == Integer.parseInt(destino)) {
                        conta1 = contadao.lista.get(j);
                    } else if (contadao.lista.get(j).getId() == Integer.parseInt(remetente)) {
                        conta2 = contadao.lista.get(j);
                    }
                }
            }

            if (conta2.getSaldo() >= saldo) {
                conta1.setSaldo(conta1.getSaldo() + saldo);
                conta2.setSaldo(conta2.getSaldo() - saldo);

                comprovante1 = "****\nID: " + conta1.getId() + "\nRecebido\nDia " + data + ":\n De: " + remetente + "\n" + "Para:" + destino + "\nValor recebido: " + saldo + "\nSaldo atual: " + conta1.getSaldo();
                comprovante2 = "****\nID: " + conta2.getId() + "\nEnviado\nDia " + data + ":\n De: " + remetente + "\n" + "Para:" + destino + "\nValor transferido: " + saldo + "\nSaldo atual: " + conta2.getSaldo();

                conta1.setMovimentacoes(comprovante1);
                conta2.setMovimentacoes(comprovante2);
            } else {
                return 1;
            }

        } catch (Exception erro) {
            return 1;
        }

        return 0;
    }

    public String retornaNome(int id) throws RemoteException {

        String resultado = "****";

        for (int j = 0; j < contadao.lista.size(); j++) {

            if (contadao.lista.get(j).getId() == id) {
                resultado = contadao.lista.get(j).getNome();
            }
        }
        return resultado;
    }

    public ArrayList<String> buscaContaPorNome(String nome) throws RemoteException {

        ArrayList<String> resultado = new ArrayList<String>();
        try {
            for (int j = 0; j < contadao.lista.size(); j++) {

                if (contadao.lista.get(j).getNome().toLowerCase().contains(nome)) {
                    resultado.add("Identificador: " + contadao.lista.get(j).getId() + " Titular: " + contadao.lista.get(j).getNome());
                }
            }
        } catch (Exception e) {

        }

        return resultado;
    }

    public void salvaLogado(int id) {

        for (int i = 0; i < logados.size(); i++) {
            if (logados.get(i).equals(String.valueOf(id))) {
                return;
            }
        }
        logados.add(String.valueOf(id));
    }

    public String montante() {
        int numContas = 0;
        double somatorio = 0;
        try {

            for (int i = 0; i < contadao.lista.size(); i++) {
                numContas += 1;
                somatorio += contadao.lista.get(i).getSaldo();
            }
        } catch (Exception e) {
            return "Erro";
        }

        return "São " + numContas + " com o valor total de " + somatorio;
    }

    public int logout(int id) throws RemoteException {

        int retorno = 1;

        try {
            for (int i = 0; i < logados.size(); i++) {
                if (logados.get(i).equals(String.valueOf(id))) {
                    logados.remove(i);
                }
            }
            retorno = 0;
        } catch (Exception e) {

        }

        return retorno;
    }
}