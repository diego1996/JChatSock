/*
        NameClass: ServidorHilo
        Version: v1.0
        Autor: @Diego_Asencio
        GitHub: diego1996
        E-Mail: diego.asencio.cuellar@unillanos.edu.co 
        Descripcion: Clase que da soporte a los clientes permitiendo obtener la informacion que llega al servidor
        Licence: None
*/

import java.awt.Color;
import java.awt.List;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClienteHilo extends Thread {
	private Socket scks;
     	private DataInputStream entrada;
     	private List pantalla;
     
	public ClienteHilo(Socket socket, List pantalla) {
        	this.pantalla = pantalla;
         	try {
            		entrada = new DataInputStream(socket.getInputStream());
         	}catch(Exception e) { System.out.print(e); }
    	}

    	public void desconnectar() {
        	System.out.println("desconecta");
        	try {
        	    scks.close();
        	} catch (IOException ex) {
        	    Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
        	}
    	}

    	public void run() {
        	try {
        		for(;;) {
             			pantalla.setForeground(Color.green);
             			String msn=entrada.readUTF();
             			pantalla.add(msn);      
        		}
      		}catch(Exception e) { 
			System.out.print(e); 
		} 
    	}

}
