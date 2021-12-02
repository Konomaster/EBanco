/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import java.rmi.Naming;
import java.util.Scanner;
import modelo.InterfaceModelo;
import org.jgroups.*;

/**
 *
 * @author Acer
 */
public class EbancoVisao {

    Scanner teclado;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        new EbancoVisao().start();
    }

    private void start() throws Exception {

        eventLoop();

    }

    private void eventLoop() throws Exception {

        teclado = new Scanner(System.in);
        String line = "";

        System.out.println("*****Bem vindo ao E-Banco_CONTROLE*****");
        System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");

        boolean continua = true;
        while (continua) {

            System.out.print("> ");
            System.out.flush();

            line = teclado.nextLine().toLowerCase();

            if (line.startsWith("sair")) {
                continua = false;
            } else if (line.startsWith("login")) {
            } else if (line.startsWith("cadastro")) {
                cadastro();
            } else {
                System.out.println("Digite uma opcao válida ('login' ou 'cadastro' ou 'sair').");
            }

        }//while
    }

    public void cadastro() throws Exception{

        System.out.println("****Ambiente de Cadastro****");
        System.out.println("Voce pode digitar 'sair' a qualquer momento para "
                + "cancelar o cadastro e voltar ao menu inicial.");
        String operacao = "";

        while (!operacao.equals("sair") && !operacao.equals("concluido")) {
            System.out.println("Por gentileza, insira seu nome: ");
            String nomeConta = teclado.nextLine().toLowerCase();

            if (nomeConta.equals("sair")) {
                System.out.println("*****Bem vindo ao E-Banco_CONTROLE*****");
                System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");
                operacao = "sair";
            }

            InterfaceModelo ic = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerControle");
            boolean teste = ic.isNomeUnico();
            if (teste) {
                System.out.println("Deu certo");
            } else {
                System.out.println("Deu errado");
            }
        }

        if (operacao.equals("concluido")) {
            System.out.println("Cadastro concluido com sucesso!");
            System.out.println("O id unico da sua conta é: ");
            System.out.println("Faca login utilizando o id da sua conta e a senha que voce definiu neste cadastro.");
        }

    }

    public void login() throws Exception {

        System.out.println("****Ambiente de Login****");
        System.out.println("Voce pode digitar 'sair' a qualquer momento para "
                + "cancelar o login e voltar ao menu inicial");
        String operacao = "";
        String idConta = "";

        while (!operacao.equals("sair") && !operacao.equals("concluido")) {
            System.out.println("Por gentileza, insira seu nome: ");
            String nomeConta = teclado.nextLine().toLowerCase();

            if (nomeConta.equals("sair")) {
                System.out.println("*****Bem vindo ao E-Banco_CONTROLE*****");
                System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");
                operacao = "sair";
            }
        }

    }

    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": " + msg.getObject());
    }

    public void viewAccepted(View new_view) {
        System.out.println("\t\t[DEBUG] ** view: " + new_view);
    }
}
