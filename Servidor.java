/*
        NameClass: Servidor
        Version: v1.0
        Autor: @Diego_Asencio
        GitHub: diego1996
        E-Mail: diego.asencio.cuellar@unillanos.edu.co 
        Descripcion: Esta es la clase que construye un servidor capaz de escuchar en un puerto y aceptar infinitos clientes
		     que se conecten a el. Esta clase tambien permite enviar cadenas de texto a todos los clientes que 
		     este servidor tenga conectados.
        Licence: None
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;

public class Servidor extends JFrame implements ActionListener {

        private JPanel arriba, centro, abajo;
        private JLabel texto_user, texto_nick;
        private JTextArea envio;
        private JButton enviar, limpiar, cambiar_nick;
        private List conectados,pantalla;
        private ServerSocket ssocket;
        private ArrayList<Socket> clientes; 
        private DataOutputStream salida;
        private final int puerto = 7777;
        private String nick;
        private int num_cli;

    	public static void main(String[] args) {
        	Servidor in = new Servidor();
    	}
    
    	public Servidor() {
        	super("JChatSock Server");
        	nick = "Servidor";
        	num_cli=0;
        	clientes = new ArrayList();
        	setLayout(new BorderLayout());
        	arriba = new JPanel();
        	arriba.setBackground(Color.black);
        	centro = new JPanel();
        	centro.setLayout(new GridLayout());
        	centro.setBackground(Color.black);
        	pantalla = new List(15);
        	centro.add(pantalla);
        	abajo = new JPanel();
        	abajo.setBackground(Color.black);
        	abajo.setLayout(new FlowLayout());
        	texto_user = new JLabel("            Conectado Como:             ");
        	texto_nick = new JLabel(nick);
        	envio = new JTextArea();
        	envio.setSize(300,100);
        	envio.setRows(10);
        	envio.setColumns(30);
        	enviar = new JButton("Enviar");
        	limpiar = new JButton("Limpiar");
        	cambiar_nick = new JButton("Cambiar Nick");
        	abajo.add(texto_user, BorderLayout.EAST);
        	abajo.add(texto_nick, BorderLayout.WEST);
        	abajo.add(enviar);
        	abajo.add(limpiar);
        	abajo.add(cambiar_nick);
        	abajo.add(envio);
        	enviar.addActionListener(this);
        	limpiar.addActionListener(this);
        	cambiar_nick.addActionListener(this);
        	texto_user.setForeground(Color.blue);
        	texto_nick.setForeground(Color.blue);
        	add(arriba, BorderLayout.NORTH);
        	add(centro, BorderLayout.CENTER);
        	add(abajo, BorderLayout.SOUTH);
        
        	setVisible(true);
        	setSize(500,400);
        	setResizable(false);
        	setLocationRelativeTo(null);
        	setDefaultCloseOperation(EXIT_ON_CLOSE);
        	
		initServer();

	} 

	public void initServer() {
        	try {
                	ssocket = new ServerSocket(puerto);
                        	for(;;) {
                                	clientes.add(ssocket.accept());
                               		((ServidorHilo) new ServidorHilo(clientes.get(num_cli), pantalla,clientes)).start();
                               		num_cli++;
                       		}
            	}catch(Exception e) { 
			System.out.println("Error " +e); 
		}
     	}

   	 public void actionPerformed(ActionEvent e) {
         	if(e.getSource() == enviar) {
                        String msn = envio.getText();
                        pantalla.add(nick + " >> " + msn);
                        envio.setText("");
                        for(int a=0;a<clientes.size();a++) {
                	        try {
                        	        salida = new DataOutputStream(clientes.get(a).getOutputStream());
                                        salida.writeUTF(nick + " >> " + msn);
                                }catch(IOException ex) { 
					System.out.println("Error " +ex); 
				}
                        }
                }
         	if(e.getSource() == limpiar) {
                	envio.setText("");
                }
                if(e.getSource() == cambiar_nick) {
                        nick = JOptionPane.showInputDialog("Ingrese un Nick de Usuario");
                        texto_nick.setText(nick);
                }
	}
}
