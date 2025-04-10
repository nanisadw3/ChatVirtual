package org.example.c_s;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main_servidor extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main_servidor.class.getResource("View_servidor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Servidor");
        stage.setScene(scene);

        // Configurar el comportamiento al cerrar
        stage.setOnCloseRequest(event -> {
            // Aquí puedes agregar lógica adicional antes de cerrar
            System.out.println("Cerrando la ventana...");
            stage.close(); // Esto es equivalente a dispose()
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}