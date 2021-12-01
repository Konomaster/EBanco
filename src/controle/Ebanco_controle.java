/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controle;

import java.util.Scanner;
import org.jgroups.*;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
/**
 *
 * @author Acer
 */
public class Ebanco_controle extends UnicastRemoteObject{
    //extends ReceiverAdapter
    
    private JChannel canal;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        new Ebanco_controle().start();
        ControleServer c = new ControleServer();
        c.iniciar();
        
    }
    
    private void start() throws Exception {
        canal = new JChannel();
        //canal.setReceiver(this);

        canal.connect("visao_controle");
        canal.close();
    }
  
    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": " + msg.getObject());
    }
    
    public void viewAccepted(View new_view) {
        System.out.println("\t\t[DEBUG] ** view: " + new_view);
    }

    

}
