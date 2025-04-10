package org.example.c_s;

import java.io.Serializable;

public class Envios implements Serializable {
    private String nombre, ip, mensaje;

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getIp() {
        return ip;
    }

    public String getMensaje() {
        return mensaje;
    }
}
