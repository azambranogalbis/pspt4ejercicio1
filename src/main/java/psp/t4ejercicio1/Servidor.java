package psp.t4ejercicio1;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
    
    private static final int PORT = 5000;
    
    public static Map<String, Double> productos = new HashMap<String, Double>();
    
    Servidor() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Servidor escuchando en el puerto " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                threadPool.execute(new ClientHandler(clientSocket));
            }
        } catch (Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Servidor();
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            DataInputStream dis = new DataInputStream(input);
            OutputStream output = clientSocket.getOutputStream();
            DataOutputStream dos = new DataOutputStream(output);
            
            dos.writeUTF("¡Bienvenid@! Escribe 'salir' para desconectarte.");
            String mensaje;
            String salir = "salir";
            do {
                mensaje = dis.readUTF().trim().toLowerCase();
                if (!mensaje.equals(salir)) {
                    if(!Servidor.productos.containsKey(mensaje)) {
                        Servidor.productos.put(mensaje, precioAleatorio());
                    }
                    double precio = Servidor.productos.get(mensaje);
                    System.out.println(String.format("%s solicita precio de %s (%1.2f€)", clientSocket.getInetAddress(), mensaje, precio));
                    dos.writeUTF(Double.toString(precio));
                }
            } while (!mensaje.equals(salir));
            clientSocket.close();
        } catch(EOFException e) {
            System.out.println("Conexión con cliente finalizada: " + clientSocket.getInetAddress());
        } catch(Exception e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
    
    private static double precioAleatorio() {
        double rnd = Math.random();
        long centimos = Math.round(rnd * 1000);
        double precio = centimos / 100.0;
        return precio;
    }
}
