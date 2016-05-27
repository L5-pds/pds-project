/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import app.helpers.Serialization;
import app.listeners.*;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Thibault
 */
public class ControllerLinda {
    private ListenerLinda listener;

    private static Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Serialization s;

    public ControllerLinda(Socket socket) {
        this.socket = socket;
        this.s = new Serialization();
        try {
          out = new PrintWriter(socket.getOutputStream());
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + e.getMessage());
        }
    }

    public void addListener(ListenerLinda l) {
        this.listener = l;
        listener.setIHM();
    }
    
    public void testBDD(int idCustomer)	{
    	try {
            out.println("SPECIF_6/toto/" + idCustomer);
            out.flush();
            javax.swing.JOptionPane.showMessageDialog(null,"RÈponse: " + in.readLine());
        } catch (HeadlessException | IOException e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne r√©pond plus");
        }
    }
    
}
