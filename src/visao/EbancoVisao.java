/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import controle.InterfaceControle;
import java.rmi.Naming;
import java.util.Scanner;
import org.jgroups.*;

public class EbancoVisao {

    Scanner teclado;

    public static void main(String[] args) throws Exception {
        new EbancoVisao().start();
    }

    private void start() throws Exception {
        eventLoop();
    }

    private void eventLoop() throws Exception {

        teclado = new Scanner(System.in);
        String line = "";

        System.out.println("*****Seja bem vindo ao E-Banco*****");
        System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");

        boolean continua = true;
        while (continua) {

            System.out.print("> ");
            System.out.flush();

            line = teclado.nextLine().toLowerCase();

            if (line.startsWith("sair")) {
                continua = false;
            } else if (line.startsWith("cadastro")) {
                cadastro();
            } else if (line.startsWith("login")) {
                login();
            } else if (line.startsWith("admin")) {
                admin();
            } else {
                System.out.println("Digite uma opcao válida ('login' ou 'cadastro' ou 'sair').");
            }
        }
    }

    public void cadastro() throws Exception{
        System.out.println("****Ambiente de Cadastro****");
        System.out.println("Voce pode digitar 'sair' a qualquer momento para cancelar o cadastro e voltar ao menu inicial.");
        String operacao = "";
        boolean flag;

        while (!operacao.equals("sair") && !operacao.equals("concluido")) {
            
            System.out.println("Por gentileza, insira o nome da conta: ");
            String nomeConta = teclado.nextLine().toLowerCase();
            System.out.println("Agora digite a senha: ");
            String senhaConta = teclado.nextLine().toLowerCase();

            if (nomeConta.equals("sair"))
                eventLoop();
                       
            InterfaceControle ictrl = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
            flag = ictrl.solicitaCriacao(nomeConta, senhaConta);
            
            if(flag == true){
                System.out.println("Conta criada com sucesso");
                operacao = "concluido";
            }
            else{
                System.out.println("Este nome já pertence a outra conta, por favor, escolha outro nome");
            }
        }

        if (operacao.equals("concluido")) {
            System.out.println("Cadastro concluido com sucesso!");
            System.out.println("Faca login utilizando o nome da sua conta e a senha que voce definiu neste cadastro.");
            eventLoop();
        }
    }

    public void login() throws Exception {
        teclado = new Scanner(System.in);
        String line = "";
        System.out.println("****Ambiente de Login****");
        System.out.println("Voce pode digitar 'sair' a qualquer momento para cancelar o login e voltar ao menu inicial");
        String operacao = "";

        while (!operacao.equals("sair") && !operacao.equals("concluido")) {
            
            InterfaceControle iCtrl = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
            System.out.println("Por gentileza, insira seu nome: ");
            String nomeConta = teclado.nextLine().toLowerCase();
            
            if (nomeConta.equals("sair"))
                eventLoop();
          
            System.out.println("Por gentileza, insira sua senha: ");
            String senhaConta = teclado.nextLine().toLowerCase();
            System.out.println("Autenticando....");
            
            //Verificar se a conta existe, se existir, peça senha
            if(iCtrl.autenticacao(nomeConta,senhaConta)){//trocar nome por id

                System.out.println("Logado com sucesso!");
                System.out.println("Digite 'consulta' para consultar seu extrato e 'transferencia' para transferir dinheiro");
                System.out.print("> ");
                System.out.flush();

                line = teclado.nextLine().toLowerCase();

                if (line.startsWith("sair")) {
                    eventLoop();
                } else if (line.startsWith("consulta")) {
                    int saldo = 0;
                    saldo = iCtrl.solicitaConsulta(nomeConta);
                    System.out.println("O saldo dessa conta é "+ saldo);
                    login();
                } else if (line.startsWith("transferencia")) {
                    iCtrl.solicitaTransferencia();
                } else {
                    System.out.println("Digite uma opcao válida ('consulta', 'transferencia' ou 'sair').");
                    login();
                }
            }
            else{
                System.out.println("Nome de usuário ou senha inválidos");
                eventLoop();
            }
        }
    }
    
    public void admin() throws Exception {
        
        String operacao = "";
        System.out.println("****Ambiente do Administrador****");
        System.out.println("Digite 'montante' para auditar o valor total do banco ou 'listar' para ver os registros");
        
        System.out.println("Por gentileza, insira seu nome: ");
        String nome = teclado.nextLine().toLowerCase();
        System.out.println("Por gentileza, insira sua senha: ");
        String senha = teclado.nextLine().toLowerCase();
        
        if(nome.equals("pedro") && senha.equals("1")){
            
            while (!operacao.equals("sair")) {    
                InterfaceControle ictrl = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
                System.out.println("Digite 'consulta' para consultar seu extrato e 'transferencia' para transferir dinheiro");
                teclado = new Scanner(System.in);
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