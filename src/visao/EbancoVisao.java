/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import controle.InterfaceControle;
import java.rmi.Naming;
import java.util.ArrayList;
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

        System.out.println("*****Bem vindo ao E-Banco*****");
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
            } else if (line.startsWith("montante")) {
                String retorno = "Erro ao imprimir montante";
                try {
                    InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
                    retorno = ic.montante();
                } catch (Exception e) {

                }
                System.out.println(retorno);
                System.out.println("");
                System.out.println("*****Bem vindo ao E-Banco*****");
                System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");
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
        String idConta = "-1";

        while (!operacao.equals("sair") && !operacao.equals("concluido") && !operacao.equals("falha")) {
            System.out.println("Por gentileza, insira seu nome: ");
            String nomeConta = teclado.nextLine();
            String senhaConta = "";
            String senhaContaConfirmar = " ";

            if (nomeConta.toLowerCase().equals("sair")) {
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

                String codResult = "-1";
                try {
                    InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
                    codResult = ic.solicitaCriacao(nomeConta, senhaConta);
                } catch (Exception e) {

                }

                if (!codResult.equals("-1")) {

                    operacao = "concluido";
                    idConta = codResult;
                } else {
                    operacao = "falha";
                }
            }

            if (nomeConta.toLowerCase().equals("sair")) {
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
            System.out.println("*****Bem vindo ao E-Banco*****");
            System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");

        } else {
            System.out.println("Cadastro nao pode ser executado com sucesso.");
            System.out.println("Retornando ao menu inicial.");
            System.out.println("");
            System.out.println("*****Bem vindo ao E-Banco*****");
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

            boolean opResult = false;
            try {
                InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
                opResult = ic.login(idConta, senha);
            } catch (Exception e) {
            }

            if (opResult) {
                operacao = "concluido";
            } else {
                operacao = "falha";
            }
        }

        if (operacao.equals("concluido")) {
            ambienteLogado(idConta);
        } else {
            System.out.println("Falha ao fazer login, tente novamente.");
            System.out.println("");
            System.out.println("");
        }
        System.out.println("*****Bem vindo ao E-Banco*****");
        System.out.println("Escreva 'login' para fazer login, 'cadastro' para se cadastrar, ou 'sair' para sair.");
    }

    private void ambienteLogado(String idConta) throws Exception {
        String nomeCliente = "Erro";
        try {
            InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
            nomeCliente = ic.consultaNome(idConta);
        } catch (Exception e) {
        }
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
                    System.out.println("");
                    System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
                } else {
                //

                    boolean retorno = false;
                    try {
                        InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
                        retorno = ic.logout(idConta);
                    } catch (Exception e) {

                    }

                    if (!retorno) {
                        opcao = "";
                        System.out.println("Nao foi possivel fazer logout, tente novamente!");
                        System.out.println("");
                        System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
                    }
                }
            } else if (opcao.equals("saldo")) {
                saldo(idConta);
            } else if (opcao.equals("transferencia")) {
                transferencia(idConta);
            } else if (opcao.equals("extrato")) {
                extrato(idConta);
            } else {
                System.out.println("Digite uma opcao valida, 'saldo', 'transferencia', 'extrato' ou 'sair'");
            }
        }

    }

    private void saldo(String idConta) throws Exception {
        double saldo = -1;
        String titular = "Erro";
        try {
            InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
            saldo = ic.solicitaSaldo(Integer.parseInt(idConta));
            titular = ic.consultaNome(idConta);
        } catch (Exception e) {

        }
        System.out.println("Identificador da conta: " + idConta);
        System.out.println("Titular: " + titular);
        System.out.println("Saldo: " + saldo);
        System.out.println("");
        System.out.println("Voltando ao menu de cliente logado.");
        System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
    }

    private void extrato(String idConta) throws Exception {
        String opcao = "";

        System.out.println("Voce deseja obter o historico parcial ou o historico total?");
        System.out.println("Digite 'total' ou 'parcial'.");
        opcao = teclado.nextLine().toLowerCase();

        if (opcao.equals("sair")) {
            System.out.println("Cancelando operacao de extrato.");
            System.out.println("Voltando ao menu de cliente logado.");
            System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
            return;
        } else if (opcao.equals("total")) {
            ArrayList<String> resposta = new ArrayList<String>();
            String titular = "Erro";
            try {
                InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
                resposta = ic.solicitaExtrato(false, Integer.parseInt(idConta));
                titular = ic.consultaNome(idConta);
            } catch (Exception e) {
            }

            System.out.println("Exibindo historico total de operacoes para a conta: " + idConta);
            System.out.println("Titular: " + titular);

            if (resposta.size() == 0) {
                System.out.println("Ainda nao existem movimentacoes na sua conta");
            } else {
                for (String s : resposta) {
                    System.out.println(s);
                }
            }

        } else if (opcao.equals("parcial")) {
            ArrayList<String> resposta = new ArrayList<String>();
            String titular = "Erro";
            try {
                InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
                resposta = ic.solicitaExtrato(false, Integer.parseInt(idConta));
                titular = ic.consultaNome(idConta);
            } catch (Exception e) {
            }

            System.out.println("Exibindo historico parcial de operacoes para a conta: " + idConta);
            System.out.println("Titular: " + titular);

            if (resposta.size() == 0) {
                System.out.println("Ainda nao existem movimentacoes na sua conta");
            } else {
                for (String s : resposta) {
                    System.out.println(s);
                }
            }

        }

        System.out.println("Voltando ao menu de cliente logado.");
        System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
    }

    private void transferencia(String idConta) throws Exception {
        String contaRemetente = idConta;
        String contaDestino = "";

        System.out.println("Para realizar transferencias voce precisara informar o numero identificador unico da conta destino.");
        System.out.println("Voce sabe o identificador unico da conta destino ou gostaria de descobri-lo fazendo uma pesquisa pelo nome do titular? ");
        System.out.println("Responda 'sim', 'nao' ou qualquer outra coisa para cancelar a operacao de transferencia");
        String op = teclado.nextLine().toLowerCase();

        if (op.equals("sim")) {
        } else if (op.equals("nao")) {
            if (buscaId() == 1) {
                return;
            }
        } else {
            System.out.println("Cancelando operacao de transferencia.");
            System.out.println("Voltando ao menu de cliente logado.");
            System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
            return;
        }

        System.out.println("Digite o identificador unico da conta destino.");
        contaDestino = teclado.nextLine();

        if (contaDestino.equals("sair")) {
            System.out.println("Voltando ao menu de cliente logado.");
            System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
            return;
        }

        if (!idValido(contaDestino)) {
            System.out.println("Identificador unico informado eh invalido.");
            System.out.println("Cancelando opcao de transferencia e voltando ao menu.");
            System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
            return;
        }

        System.out.println("Agora digite a quantidade de dinheiro a ser transferida: ");
        String quantidade = teclado.nextLine();

        if (quantidade.equals("sair")) {
            System.out.println("Voltando ao menu de cliente logado.");
            System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
            return;
        } else if (!doubleValido(quantidade)) {
            System.out.println("Entrada informada nao corresponde a valor valido.");
            System.out.println("Cancelando opcao de transferencia e voltando ao menu.");
            System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
            return;
        }

        int dblQuantidade = Integer.parseInt(quantidade);

        System.out.println("Efetuando transferencia...");
        int retornoOp = 1;
        try {
            InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
            retornoOp = ic.transfereSaldo(contaRemetente, contaDestino, dblQuantidade);
        } catch (Exception e) {
        }
        if (retornoOp == 0) {
            System.out.println("Transferencia de saldo concluida com sucesso.");
            System.out.println("Voltando ao menu de cliente logado.");
            System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
        } else if (retornoOp == 1) {
            System.out.println("FALHA: Transferencia nao pode ser executada.");
            System.out.println("Tente novamente em alguns instantes.");
            System.out.println("Voltando ao menu de cliente logado.");
            System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
        }

    }

    private int buscaId() throws Exception {

        System.out.println("********");
        System.out.println("Iniciando procedimento de descoberta de identificador de conta atraves do nome do titular.");
        System.out.println("Digite o nome do titular da conta parcialmente ou todo: ");
        String stringdebusca = teclado.nextLine().toLowerCase();

        if (stringdebusca.equals("sair")) {
            System.out.println("Cancelando operacao de descoberta de identificador de conta.");
            System.out.println("Cancelando operacao de transferencia.");
            System.out.println("Voltando ao menu de cliente logado.");
            System.out.println("Digite uma opcao: 'saldo', 'transferencia', 'extrato' ou 'sair'.");
            return 1;
        }

        System.out.println("Resultado da busca: ");
        ArrayList<String> resultado = new ArrayList<String>();
        try {
            InterfaceControle ic = (InterfaceControle) Naming.lookup("rmi://localhost/ServerControle");
            resultado = new ArrayList<String>();
        } catch (Exception e) {
        }

        if (resultado.size() == 0) {
            System.out.println("***Nao existem contas com esse titular.***");
        }

        System.out.println("Continuando com processo de transferencia...");
        return 0;
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

    public boolean doubleValido(String quantidade) {
        boolean retorno = false;
        try {
            double dbl = Double.parseDouble(quantidade);
            retorno = true;
        } catch (Exception e) {

        }
        return retorno;
    }
}
