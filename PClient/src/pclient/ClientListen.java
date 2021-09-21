/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pclient;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author RIPON
 */
public class ClientListen implements Runnable {
    
    ClientGui cg;
    
    
    
    
    Socket newsock;
    BufferedReader FromServer;
    Thread t = null;
    String NewMessage;
    PrintWriter ToServer = null;

    public ClientListen(Socket sock,ClientGui c) throws IOException {
        cg=c;
        
        
        newsock = sock;
        FromServer = new BufferedReader(new InputStreamReader(newsock.getInputStream()));
        
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
        while (true) {
            
            try {
                NewMessage = FromServer.readLine();
                System.out.println(NewMessage);
                cg.ta.append(NewMessage+"\n");
            } catch (IOException ex) {
                System.out.println("Client logged out");
                break;
                
            }
            
        }
    }

    
}
