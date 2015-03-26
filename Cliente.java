/*
        NameClass: Cliente
        Version: v1.0
        Autor: @Diego_Asencio
        GitHub: diego1996
        E-Mail: diego.asencio.cuellar@unillanos.edu.co 
        Descripcion: Esta clase constuye una conexion a un servidor escuchante y es capaz de enviar informacion al mismo
        Licence: None
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.net.*;

public class Cliente extends JFrame implements ActionListener {

        private JPanel arriba, centro, abajo;
        private JLabel texto_user, texto_nick;
        private JTextArea envio;
        private JButton enviar, limpiar, cambiar_nick;
        private List conectados,pantalla;
        private Socket socket;
        private DataOutputStream salida;
        private String nick;

	public static void main(String[] args) {
       		Cliente in = new Cliente();
    	}
    
    	public Cliente() {
      		super("JChatSock Client");
                nick = JOptionPane.showInputDialog("Ingrese un Nick de Usuario");
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

	        initClient();
	} 
 
	private void initClient() {
        	try {
                	socket = new Socket("127.0.0.1", 7777);
                        salida = new DataOutputStream(socket.getOutputStream());
                        ((ClienteHilo) new ClienteHilo(socket, pantalla)).start();
                }catch(Exception e) { 
			System.out.println("Error " +e); 
		}
    	}

    	public void actionPerformed(ActionEvent e) {
        	if (e.getSource() == enviar) {
          		try {
                		String msn;
                  		msn = envio.getText();
		                salida.writeUTF(nick+ " >> " + msn);
                  		envio.setText("");
            		}catch(Exception r) { 
				System.out.print(r); 
			}    
        	}
       		if (e.getSource() == limpiar) {
            		envio.setText("");
  	        }
        	if (e.getSource() == cambiar_nick) {
            		nick = JOptionPane.showInputDialog("Ingrese Su Nick de Usuario");
            		texto_nick.setText(nick);
        	}
	}

}
