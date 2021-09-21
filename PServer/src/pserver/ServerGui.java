/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pserver;

import com.sun.corba.se.spi.activation.Server;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author RIPON
 */
public class ServerGui extends JFrame {

    
    public static String toSend;

    ArrayList<Socket> sockets;
    ArrayList<String> names;
    public BufferedReader FromClient;
    public PrintWriter ToClient;
    public JButton StartServer;
    public JButton StopServer;

    
    public JButton send;
    public JTextArea ta;

    public JTextArea rexams;
    public BorderLayout layout;
    public JPanel p1;
    public JPanel p2;
    public JPanel p3;
    public GridLayout l1;
    public static int ServerRunning;
    public ServerSocket socket;
    Socket ClientSocket;
    //ArrayList<NewClient> thread;
    String PortNoStr;
    int PortNoInt;
    
    String namestr;
    

    public ServerGui() {
        super("Server");
        
        ServerRunning = 0;
        names = new ArrayList<String>();
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layout = new BorderLayout(5, 5);
        setLayout(layout);
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p1.setLayout(new GridLayout(5, 1, 5, 50));
        p2.setLayout(new GridLayout(3, 1,5,50));
        p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
        

        StartServer = new JButton("Start Server");
        StopServer = new JButton("Stop Server");
        send = new JButton("SEND");

        
        
        ta = new JTextArea(70, 50);

        rexams = new JTextArea(10, 10);

        p1.add(StartServer);
        p1.add(StopServer);

        
        p2.add(ta);
        p2.add(rexams);
        p2.add(send);

        
        add(p1, BorderLayout.WEST);
        add(p2, BorderLayout.CENTER);
        add(p3, BorderLayout.EAST);
        ButtonHandler handler = new ButtonHandler();
        StartServer.addActionListener(handler);
        StopServer.addActionListener(handler);

        
        sockets = new ArrayList<Socket>();
        
        
        
    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //throw new UnsupportedOperationException("Not supported yet.");
            if ("Start Server".equals(e.getActionCommand())) {
                Thread t;
                t = new Thread() {
                    public void run() {
                        try {
                            socket = new ServerSocket(5000);

                            ServerRunning = 1;
                            ta.append("SERVER STARTED\n");
                        } catch (IOException ex) {
                            Logger.getLogger(PServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        while (true) {
                            if (ServerRunning == 1) {
                                try {
                                    System.out.println("waiting");
                                    ClientSocket = socket.accept();
                                    System.out.println("After accepting");
                                    
                                    sockets.add(ClientSocket);
                                    FromClient = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
                                    ToClient = new PrintWriter(ClientSocket.getOutputStream(), true);
                                    namestr = FromClient.readLine();

                                    System.out.println("Connected to: " + namestr);
                                    ta.append("User Logged In: " + namestr + "\n");
                                    
                                    names.add(namestr);

                                        
                                    ToClient.println("Yes");
                                    //NewClient th = new NewClient(StudentIdStr, ClientSocket, ServerGui.this);
                                    //thread.add(th);

                                } catch (SocketException sc) {
                                    ta.append("SERVER STOPPED\n");
                                    ServerRunning = 0;
                                    
                                    
                                } catch (IOException ex) {
                                    Logger.getLogger(PServer.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                    }
                };
                t.start();

            }
            if ("Stop Server".equals(e.getActionCommand())) {
                try {
                    socket.close();
                    System.out.println("server closed");
                } catch (IOException ex) {
                    Logger.getLogger(PServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            
            
            if ("SEND".equals(e.getActionCommand()))
            {
                toSend = rexams.getText();
                for (int i =0;i<sockets.size();i++)
                {
                    Socket sock = sockets.get(i);
                    try {
                        ToClient = new PrintWriter(sock.getOutputStream(), true);
                        ToClient.print(toSend);
                    } catch (IOException ex) {
                        Logger.getLogger(ServerGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                //ServerWrite.notify = 1;
            }
        }
    }

}
