/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package downunder.ws;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 *
 * @author Eduardo Mendiccelli
 */
public class Server {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");			
        } catch (RemoteException e) {
            System.out.println("RMI registry already running.");			
        }
        try {
            Naming.rebind ("DownUnder", new DownUnder());
            System.out.println ("Server is ready.\n");
        } catch (Exception e) {
            System.out.println ("Server failed: ");
            e.printStackTrace();
        }
    }
}
