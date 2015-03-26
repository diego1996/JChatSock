/*
	NameClass: ServidorHilo
	Version: v1.0
	Autor: @Diego_Asencio
	GitHub: diego1996
	E-Mail: diego.asencio.cuellar@unillanos.edu.co
	Descripcion: Clase que permite al Servidor recibir la informacion enviada desde cualquier cliente conectado. Tambien
		     es la encargada de hacer llegar la informacion no solo al Servidor sino tambien a los demas clientes del mismo.
	Licence: None
*/

import java.awt.Color;
import java.awt.List;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.*;
import javax.swing.JTextArea;

public class ServidorHilo extends Thread {

	private Socket scks;
	private ArrayList<Socket> clientes;
	private DataInputStream entrada;
	private DataOutputStream salida;
	private List pantalla;
   	private String nick;

    	public ServidorHilo(Socket socket, List pantalla, ArrayList<Socket> clientes) {
    		this.clientes = clientes;
    		this.pantalla = pantalla;
     		try {
      			entrada = new DataInputStream(socket.getInputStream());
      			salida = new DataOutputStream(socket.getOutputStream());
     		}catch(Exception e) { 
			System.out.print(e); 
		}
    	}

	public void run() {
		try {
          		for(;;) {
                    		pantalla.setForeground(Color.blue);
                    		String llegada = entrada.readUTF();
                    		pantalla.add(llegada);
                    		for(int a=0;a<clientes.size();a++) {
                            		try {
                                     		salida = new DataOutputStream(clientes.get(a).getOutputStream());
                                     		salida.writeUTF(llegada);
                            		}catch(IOException ex) { System.out.println("Error " +ex); }
                   		}
          		}
       		}catch(Exception r) { 
			System.out.print(r); 
		}
	}

}
