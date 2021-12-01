/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controle;

import java.rmi.*;


/**
 *
 * @author pedro
 */
public interface InterfaceControle extends Remote {
    
    public boolean isNomeUnico() throws RemoteException;
    
    
}
