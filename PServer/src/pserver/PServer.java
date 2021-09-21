/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pserver;

import java.net.ServerSocket;

/**
 *
 * @author RIPON
 */
public class PServer {
    
    public PServer()
    {
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerGui sg = new ServerGui();
        sg.show();
        // TODO code application logic here
    }
}
