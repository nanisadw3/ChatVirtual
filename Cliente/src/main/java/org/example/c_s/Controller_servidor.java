package org.example.c_s;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Controller_servidor implements Runnable {
    @FXML
    TextArea text_servidor;

    @FXML
    public void initialize() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            text_servidor.appendText("Servidor iniciado en puerto 1234\n");

            while (true) {
                Socket socket = serverSocket.accept();
                ObjectInputStream paquete = new ObjectInputStream(socket.getInputStream());
                Envios recibido = (Envios) paquete.readObject();

                String mensaje = recibido.getMensaje();
                String nombre = recibido.getNombre();
                String ip = recibido.getIp();

                text_servidor.appendText("IP: " + ip + " | Nombre: " + nombre + " | Mensaje: " + mensaje + "\n");


                Socket envia_destino = new Socket(ip, 1231);//ip del destino con el puerto que permanecera habierto
                ObjectOutputStream paquete_destino = new ObjectOutputStream(envia_destino.getOutputStream());
                paquete_destino.writeObject(recibido);

                paquete_destino.close();
                envia_destino.close();
                socket.close();
            }
        } catch(java.net.ConnectException e) {
            text_servidor.appendText("Error al reenviar mensaje al cliente: " + e.getMessage() + "\n");
        } catch (Exception e) {
            text_servidor.appendText("Error en servidor: " + e.getMessage() + "\n");
        }
    }
}