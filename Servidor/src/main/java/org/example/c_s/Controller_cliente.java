package org.example.c_s;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class Controller_cliente implements Runnable {
    @FXML
    private TextField txt_mensaje;
    @FXML
    private TextField txt_name;
    @FXML
    private TextField txt_ip;
    @FXML
    private Button btn_enviar;
    @FXML
    private TextArea txt_chat;
    @FXML
    private Label label_info;

    private static String ip_server;
    private static String nombre;
    private static String ip_destino;
    //Metodo "inicializador"
    @FXML
    private void initialize() {
        txt_chat.setEditable(false);
        txt_name.setEditable(false);
        txt_ip.setEditable(false);

        ip_server = pedirIP();
        nombre = pedirNombre();
        ip_destino = pedirIPdestino();

        txt_name.setText(nombre);
        txt_ip.setText(ip_destino);

        Thread hilo = new Thread(this);
        hilo.start();
    }
    private String pedirIPdestino() {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Info");
        dialogo.setHeaderText("Dame la IP del destino de la comunicacion");
        dialogo.setContentText("IP:");

        Optional<String> resultado = dialogo.showAndWait();

        return resultado.orElse(""); // Devuelve una cadena vacía si el usuario cancela
    }
    private String pedirNombre() {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Info");
        dialogo.setHeaderText("Dame tu host name");
        dialogo.setContentText("Nombre:");

        Optional<String> resultado = dialogo.showAndWait();

        return resultado.orElse(""); // Devuelve una cadena vacía si el usuario cancela
    }
    private String pedirIP() {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Info");
        dialogo.setHeaderText("Dame la IP del servidor al que te vas a conectar");
        dialogo.setContentText("IP:");

        Optional<String> resultado = dialogo.showAndWait();

        return resultado.orElse(""); // Devuelve una cadena vacía si el usuario cancela
    }


    @FXML
    private void accionEnviar(ActionEvent event) {
        generarSocket();
    }

    private void generarSocket() {
        try {
            Socket socket = new Socket(ip_server, 1234);
            Envios en = new Envios();
            en.setNombre(txt_name.getText());
            en.setIp(txt_ip.getText());
            en.setMensaje(txt_mensaje.getText());

            ObjectOutputStream paquete_Datos = new ObjectOutputStream(socket.getOutputStream());
            paquete_Datos.writeObject(en);
            socket.close();
            txt_chat.appendText("\n" + txt_name.getText() + ": " + txt_mensaje.getText());
            txt_mensaje.clear();

        } catch(java.net.SocketTimeoutException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Timeout de conexión");
            alerta.setContentText("El servidor no respondió en 5 segundos");
            alerta.showAndWait();
        } catch(java.net.ConnectException e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error de conexión");
            alerta.setContentText("No se pudo conectar al servidor. Verifique:\n1. Que el servidor esté corriendo\n2. Que la IP sea correcta\n3. Que el firewall no esté bloqueando la conexión");
            alerta.showAndWait();
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error en la conexión");
            alerta.setContentText(e.getMessage());
            alerta.showAndWait();
        }
    }


    @Override
    public void run() {
        try {
            ServerSocket socket_cliente = new ServerSocket(1231);
            Socket cliente;
            Envios paquete_Recibido;

            while (true) {
                cliente = socket_cliente.accept();
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                paquete_Recibido = (Envios) entrada.readObject();
                txt_chat.appendText("\n" + paquete_Recibido.getNombre() + ": " + paquete_Recibido.getMensaje());
                cliente.close();
            }
        } catch (Exception e) {
            System.out.println("Error en hilo receptor: " + e.getMessage());
        }
    }
}