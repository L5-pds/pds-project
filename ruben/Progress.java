package app.views.ruben;

import javax.swing.JProgressBar;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Progress extends JFrame{
  private Thread t;
  private JProgressBar bar;
  private JButton launch ;
   
  public Progress(Calcul ca){      
    this.setSize(300, 80);
    this.setTitle("*** Chargement ***");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);      
      
    t = new Thread(new Traitement());
    bar  = new JProgressBar();
    bar.setMaximum(500);
    bar.setMinimum(0);
    bar.setStringPainted(true);
      
    this.getContentPane().add(bar, BorderLayout.CENTER);
      
    launch = new JButton("Lancer");
    launch.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event){
        Chart g = new Chart(ca);
        
      }
    });      
    this.getContentPane().add(launch, BorderLayout.SOUTH);      
    t.start();      
    this.setVisible(true);      
  }

  class Traitement implements Runnable{   
    public void run(){
      launch.setEnabled(false);
         
      for(int val = 0; val <= 500; val++){
        bar.setValue(val);
        try {
          t.sleep(1);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
        e.printStackTrace();
        }
      }
      launch.setEnabled(true);
    }   
  }

}
