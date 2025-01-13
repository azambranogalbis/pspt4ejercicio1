package psp.t4ejercicio1;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.math.BigDecimal;

public class Cliente {
    
    private static final String HOST = "localhost";
    private static final int PORT = 5000;
    
    Cliente() {
        try {
            Socket socket = new Socket(HOST, PORT);
            InputStream input = socket.getInputStream();
            DataInputStream dis = new DataInputStream(input);
            OutputStream output = socket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(output);
            
            String mensajeBienvenida = dis.readUTF();
            System.out.println(mensajeBienvenida);
            
            String inputTeclado;
            String salir = "salir";
            
            do {
                inputTeclado = System.console().readLine("> ");
                if(!inputTeclado.equals(salir)) {
                    dos.writeUTF(inputTeclado);
                    String precio = dis.readUTF();
                    System.out.println(String.format("El precio de %s es %s€", inputTeclado, precio));
                }
            } while (!inputTeclado.equals(salir));
            socket.close();
        } catch(Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Cliente();
    }
}
