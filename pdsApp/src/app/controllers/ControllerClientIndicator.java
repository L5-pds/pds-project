package app.controllers;

import app.helpers.Serialization;
import app.listeners.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ControllerClientIndicator {
    private ListenerClientIndicator listener;

    private static Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Serialization s;
    
    public ControllerClientIndicator(Socket socket) {
        this.socket = socket;
        this.s = new Serialization();
    }
    
    public void addListener(ListenerClientIndicator l) {
        this.listener = l;
        listener.setIHM();
    }
    
}
