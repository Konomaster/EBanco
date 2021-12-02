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
import org.jgroups.blocks.MessageDispatcher;
import org.jgroups.blocks.RequestHandler;
/**
 *
 * @author Acer
 */
public class EbancoControle extends ReceiverAdapter implements RequestHandler{
    //
    
    private JChannel canal;
    /**
     * @param args the command line arguments
     */
    
    MessageDispatcher  despachante;
    
    public static void main(String[] args) throws Exception{
        // TODO code application logic here
        new EbancoControle().start();
        
    }
    
    private void start() throws Exception {
        canal = new JChannel();
        //canal.setReceiver(this);
        despachante = new MessageDispatcher(canal, null, null, this);
        
        canal.setReceiver(this); // quem irá lidar com as mensagens recebidas
        canal.connect("controle");
        eventLoop();
        canal.close();
    }
    
    private void eventLoop() throws RemoteException{
        Address meuEndereco = canal.getAddress();
        if(meuEndereco.equals(canal.getView().getMembers().get(0))){
            ControleServer c = new ControleServer();
            c.iniciar();
        }
    }
  
    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": " + msg.getObject());
    }
    
    public void viewAccepted(View new_view) {
        System.out.println("\t\t[DEBUG] ** view: " + new_view);
    }

    @Override
    public Object handle(Message msg) throws Exception {
        //InterfaceModelo ic = (InterfaceModelo) Naming.lookup("rmi://localhost/ServerModelo");
        
        return null;
        
    }

    

}
