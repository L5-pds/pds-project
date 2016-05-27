/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.controllers;

import app.helpers.*;
import app.listeners.*;
import app.models.SpecifRuben;
import java.awt.HeadlessException;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Thibault
 */
public class ControllerRuben {
    private ListenerRuben listener;

    private static Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private Serialization s;

    public ControllerRuben(Socket socket) {
        this.socket = socket;
        this.s = new Serialization();
        try {
          out = new PrintWriter(socket.getOutputStream());
          in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le message est : " + e.getMessage());
        }
    }

    public void addListener(ListenerRuben l) {
        this.listener = l;
        listener.setIHM();
    }
    
    public void getInfo(int idClient){
        try {
            out.println("SPECIF_3/getAllLoanByCustomer/" + idClient);
            out.flush();
            SpecifRuben tmp = s.unserializeSpecifRuben(in.readLine());
            javax.swing.JOptionPane.showMessageDialog(null,tmp.getFirst_name());
        } catch (HeadlessException e) {
          javax.swing.JOptionPane.showMessageDialog(null,"Le serveur ne répond plus");
        } catch (IOException ex) {
            Logger.getLogger(ControllerRuben.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
