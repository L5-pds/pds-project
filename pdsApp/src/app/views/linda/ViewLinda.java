/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.views.linda;

import app.controllers.*;
import app.listeners.*;
import interest_rate.CalculateGrade;
import interest_rate.ClassifyingProfils;
import interest_rate.GainLoss;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ViewLinda extends JPanel implements ListenerLinda {

	
	private ControllerLinda cci;
	private JPanel body;
	private Container cont;

	public ViewLinda(ControllerLinda cci, JPanel body, Container cont) {
		this.cci = cci;
		this.body = body;
		this.cont = cont;
	}

	public void setIHM() {
		body.removeAll();

		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container,BoxLayout.X_AXIS));

		JButton actionCalculateGrade = new JButton("Calculer la note d'un client");
		actionCalculateGrade.setBounds(100,50,100,20);
		container.add(actionCalculateGrade);

		actionCalculateGrade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				new CalculateGrade().setVisible(true);
				
			}
		});


		// will create a JTable with the different profils
		JButton actionInterestRate = new JButton("Afficher les taux d'intérêts");
		actionInterestRate.setBounds(100,50,100,20);
		container.add(actionInterestRate) ;

		actionInterestRate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				// Creating a new board
				ClassifyingProfils tab = new ClassifyingProfils ();
				tab.setVisible(true);

			}
		});

		JButton actionGainLoss = new JButton("Afficher les gains et les pertes");
		container.add(actionGainLoss) ;
		actionGainLoss.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e1){
				GainLoss tab = new GainLoss() ;
				tab.setVisible(true);

			}
		});

		container.revalidate();
		container.repaint();

		body.setLayout(new FlowLayout());
		body.setBackground(new Color(215,203,233,200));
		body.add(container);
		body.revalidate();
		body.repaint();

		cont.revalidate();
		cont.repaint();
		
		//body.add(Components()) ;
	}

	
	
}
