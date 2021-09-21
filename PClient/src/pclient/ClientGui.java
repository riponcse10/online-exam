/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pclient;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author RIPON
 */
public class ClientGui extends JFrame {

    //static String Recieve_directory;
    public String Recieve_directory;
    public File file1;
    public BufferedReader FromServer;
    public PrintWriter ToServer;
    String PortNoStr;
    
    String namestr;
    
    JLabel StdId;
    
    JTextField name;
    JButton login;
    
    
    
    JTextArea ta;
    JPanel p1;
    JPanel p2;
    JPanel p3;
    BorderLayout layout;
    Socket ClientSocket;

    public ClientGui() {
        super("Client");
        setSize(700, 500);
        layout = new BorderLayout(5, 5);

        setLayout(layout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        StdId = new JLabel("Client name: ");
        
        name = new JTextField(10);
        login = new JButton("Connect");
        
        ta = new JTextArea(100, 50);
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        
        

        
        p1.add(StdId);
        p1.add(name);
        p1.add(login);
        
        p2.setLayout(new GridLayout(9, 1, 5, 25));
        
        
        p3.add(ta);
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.WEST);
        add(p3, BorderLayout.CENTER);
        ButtonHandler handler = new ButtonHandler();
        login.addActionListener(handler);
        
        
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
            if ("Connect".equals(e.getActionCommand())) {
                try {


                    
                    
                    namestr = name.getText();
                    
                    try {
                        ClientSocket = new Socket("127.0.0.1", 5000);

                    } catch (UnknownHostException ex) {
                        
                        ta.append("Server not started\n");

                    } catch (IOException ex) {
                        
                        ta.append("Server not started\n");

                    }
                    FromServer = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
                    ToServer = new PrintWriter(ClientSocket.getOutputStream(), true);
                    ToServer.println(namestr);
                    ClientListen cl = new ClientListen(ClientSocket, ClientGui.this);
                    

                } catch (IOException ex) {
                    //Logger.getLogger(ClientGui.class.getName()).log(Level.SEVERE, null, ex);
                    ta.append("Server not started");
                }



            }
            
            
        }
    }

    
}
