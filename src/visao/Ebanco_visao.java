/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visao;

import java.util.Scanner;
import org.jgroups.*;

/**
 *
 * @author Acer
 */
public class Ebanco_visao extends ReceiverAdapter {

    private JChannel canal;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        new Ebanco_visao().start();
    }

    private void start() throws Exception {
        canal = new JChannel();
        canal.setReceiver(this);

        canal.connect("visao_controle");
        eventLoop();
        canal.close();

    }

    private void eventLoop() throws Exception {

        Scanner teclado = new Scanner(System.in);
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
                
            } else if (line.startsWith("cadastro")) {
                
                canal.send(new Message(null, null, "teste"));
                
            } else {
                System.out.println("Digite uma opcao válida ('login' ou 'cadastro' ou 'sair')");
            }

        }//while
    }

    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": " + msg.getObject());
    }
    
    public void viewAccepted(View new_view) {
        System.out.println("\t\t[DEBUG] ** view: " + new_view);
    }
}