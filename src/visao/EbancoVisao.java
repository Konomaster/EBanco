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
                login();
            } else if (line.startsWith("cadastro")) {
                cadastro();
            } else {
                System.out.println("Digite uma opcao válida ('login' ou 'cadastro' ou 'sair').");
            }

        }//while
    }

    public void cadastro() throws Exception {

        System.out.println("****Ambiente de Cadastro****");
        System.out.println("Voce pode digitar 'sair' a qualquer momento para "
                + "cancelar o cadastro e voltar ao menu inicial.");
        String operacao = "";
        int idConta = -1;

        while (!operacao.equals("sair") && !operacao.equals("concluido") && !operacao.equals("falha")) {
            System.out.println("Por gentileza, insira seu nome: ");
            String nomeConta = teclado.nextLine().toLowerCase();
            String senhaConta = "";
            String senhaContaConfirmar = " ";

            if (nomeConta.equals("sair")) {
                System.out.println("*****Bem vindo ao E-Banco*****");
                System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");
                operacao = "sair";
                continue;
            }
            if (!nomeValido(nomeConta)) {
                System.out.println("Nome inválido. Digite um nome que contenha somente letras.");
                continue;
            }

            while (!senhaConta.equals(senhaContaConfirmar)) {
                System.out.println("Por gentileza, insira uma senha de quadro dígitos para a conta: ");
                senhaConta = teclado.nextLine().toLowerCase();
                if (senhaConta.equals("sair")) {
                    nomeConta = "sair";
                    break;
                }
                if (!senhaValida(senhaConta)) {
                    System.out.println("A senha deve conter somente numeros.");
                    System.out.println("Repetindo o processo.");
                    continue;
                }
                System.out.println("Por gentileza, repita senha de quadro digitos para a conta: ");
                senhaContaConfirmar = teclado.nextLine().toLowerCase();
                if (senhaContaConfirmar.equals("sair")) {
                    nomeConta = "sair";
                    break;
                }
                if (!senhaValida(senhaContaConfirmar)) {
                    System.out.println("A senha deve conter somente numeros.");
                    System.out.println("Repetindo o processo.");
                    continue;
                }
                if (!senhaConta.equals(senhaContaConfirmar)) {
                    System.out.println("As senhas digitadas nao sao iguais, repetindo o processo.");
                }

                InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
                boolean opResult = ic.solicitaCriacao(nomeConta, senhaConta);
                if (opResult) {

                    operacao = "concluido";
                    idConta = 0000;
                } else {
                    operacao = "falha";
                }
            }

            if (nomeConta.equals("sair")) {
                System.out.println("*****Bem vindo ao E-Banco*****");
                System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");
                operacao = "sair";
                continue;
            }

//            InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerModelo");
//            boolean teste = ic.isNomeUnico();
//            if (teste) {
//                System.out.println("Deu certo");
//            } else {
//                System.out.println("Deu errado");
//            }
        }

        if (operacao.equals("concluido")) {
            System.out.println("Cadastro concluido com sucesso!");
            System.out.println("O id unico da sua conta é: ");
            System.out.println(idConta);
            System.out.println("Faca login utilizando o id da sua conta e a senha que voce definiu neste cadastro.");
            System.out.println("Retornando ao menu inicial.");
            System.out.println("");
            System.out.println("*****Bem vindo ao E-Banco_CONTROLE*****");
            System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");

        } else {
            System.out.println("Cadastro nao pode ser executado com sucesso.");
            System.out.println("Retornando ao menu inicial.");
            System.out.println("");
            System.out.println("*****Bem vindo ao E-Banco_CONTROLE*****");
            System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");

        }

    }

    public void login() throws Exception {

        System.out.println("****Ambiente de Login****");
        System.out.println("Voce pode digitar 'sair' a qualquer momento para "
                + "cancelar o login e voltar ao menu inicial");
        String operacao = "";
        String idConta = "";

        while (!operacao.equals("sair") && !operacao.equals("concluido") && !operacao.equals("falha")) {
            System.out.println("Por gentileza, insira o id da sua conta: ");
            idConta = teclado.nextLine().toLowerCase();

            if (idConta.equals("sair")) {
                System.out.println("*****Bem vindo ao E-Banco*****");
                System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");
                operacao = "sair";
                continue;
            }

            if (!idValido(idConta)) {
                continue;
            }

            System.out.println("Por gentileza, insira a senha de quatro digitos da sua conta: ");
            String senha = teclado.nextLine().toLowerCase();

            if (senha.equals("sair")) {
                System.out.println("*****Bem vindo ao E-Banco*****");
                System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");
                operacao = "sair";
                continue;
            }

            if (!senhaValida(senha)) {
                System.out.println("Senha invalida. Insira a senha de 4 digitos.");
                System.out.println("Repetindo o processo.");
                continue;
            }

            InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
            boolean opResult = ic.login(idConta, senha);

            if (opResult) {
                operacao = "concluido";
            } else {
                operacao = "falha";
            }
        }

        if (operacao.equals("concluido")) {
            ambienteLogado(idConta);
        }
        System.out.println("*****Bem vindo ao E-Banco*****");
        System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");
    }

    private void ambienteLogado(String idConta) throws Exception {
        InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
        String nomeCliente = ic.consultaNome(idConta);
        System.out.println("Login efetuado com sucesso.");
        System.out.println("Bem vindo: " + nomeCliente);
        System.out.println("*");
        System.out.println("*");
        System.out.println("Digite:");
        System.out.println("'saldo' para consultar saldo;");
        System.out.println("'transferencia' para transferir saldo de uma conta para outra;");
        System.out.println("'extrato' para consultar seu historico de operacoes;");
        System.out.println("'sair' para fazer logout.");

        String opcao = "";

        while (!opcao.equals("sair")) {

            System.out.println(">");
            System.out.flush();
            opcao = teclado.nextLine().toLowerCase();

            if (opcao.equals("sair")) {
                System.out.println("Deseja mesmo sair da sua conta e ir para o menu principal? Digite 'sim' em caso afirmativo e qualquer outra coisa em caso negativo.");
                System.out.println(">");
                System.out.flush();
                if (!teclado.nextLine().toLowerCase().equals("sim")) {
                    opcao = "";
                    System.out.println("Logout cancelado.");
                }
                continue;
            } else if (opcao.equals("saldo")) {

            } else if (opcao.equals("transferencia")) {
            } else if (opcao.equals("extrato")) {
            }
        }

    }

    private void saldo(String idConta) throws Exception {
        InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
    }

    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": " + msg.getObject());
    }

    public void viewAccepted(View new_view) {
        System.out.println("\t\t[DEBUG] ** view: " + new_view);
    }

    private boolean senhaValida(String senha) {

        if (senha.length() != 4) {
            return false;
        }

        for (char c : senha.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    private boolean nomeValido(String nome) {
        boolean retorno = true;
        for (char c : nome.toCharArray()) {
            if (Character.isDigit(c)) {
                retorno = false;
                break;
            }
        }
        return retorno;
    }

    private boolean idValido(String id) {
        boolean retorno = true;
        for (char c : id.toCharArray()) {
            if (!Character.isDigit(c)) {
                retorno = false;
                break;
            }
        }
        return retorno;
    }
}
