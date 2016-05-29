/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part_pds;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.print.PrinterException;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import static part_pds.CalcAmortizment.model;
import static part_pds.CalcAmortizment.table;
/**
 *
 * @author RubenEdery
 */
public class CalcAm extends javax.swing.JFrame {

    private static JPanel contentPane;
    String name="Ruben";
    String surname="Edery";
    int idpdf=(int) (Math.random() * 100000 + 1);

    static DefaultTableModel model;// 
    DecimalFormat df = new DecimalFormat("0.00");// Convertir un double avec 2 chiffres apres la virgule
    Object data[] = new Object[7];
    double montant = 10000; // Le montant a emprunte est de 10.000
    double taux = 2.0; // Le taux est a 2%
    double tauxdassurance = 1.0; // Le taux d'assurance est de 1%
    int nombre_annee = 4; // Le nombre d'annees d'emprunt est de 2 ans
    double totalassurance;
    double totalinteret;
    double totalmensualite;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy à HH:mm:ss");
    String dateStr = simpleDateFormat.format(new Date());
    /**
     * Creates new form CalcAm
     */
    public CalcAm() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        this.setVisible(true);
        this.setDefaultLookAndFeelDecorated(true);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        contentPane.setVisible(true);
        initComponents();
        changdata();
        calculAmortizment();
    }

    public String getName() {
    return name;
    }

    public String getSurname() {
        return surname;
    }

    public double getMontant() {
        return montant;
    }
    
    public double getTaux() {
        return taux;
    }

    public double getTauxdassurance() {
        return tauxdassurance;
    }

    public int getNombre_annee() {
        return nombre_annee;
    }

    public double getTotalinteret() {
        return totalinteret;
    }

    public double getTotalmensualite() {
        return totalmensualite;
    }
    
    public double getTotalassurance() {
        return totalassurance;
    }

    public String getDateStr() {
        return dateStr;
    }
    
    
    public void changdata(){ 
        lblTitle.setText("<html><u>Tableau d'amortissement</u></html>");
        //lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD|Font.ITALIC));
        //Affiche le nombre d'années de l'emprunt
        lblAnnees.setText("Années : "+nombre_annee+" ans");        
        //Affiche le taux d'assurance
        lblAssurance.setText("Taux d'assurance : "+tauxdassurance+" %");        
        //Affiche le taux d'emprunt
        lblTaux.setText("Taux d'emprunt : "+taux+" %");
        //Affiche le montant de l'emprunt
        lblEmprunt.setText("Emprunt : "+montant+" €");
        // Affiche la date actuelle
        dateSimul.setText("Date de la simulation : "+dateStr );
    }

    public void calculAmortizment() {
       // if(nombre_annee<=2){
        amortissement_calcul(montant, taux, tauxdassurance, nombre_annee);
        //System.out.println(df.format(totalassurance)+"-"+df.format(totalinteret)+"-"+df.format(totalmensualite));
      /*  }
        else 
            System.out.println(">2");
      */
    }

    public void amortissement_calcul(double montant, double taux, double tauxdassurance, int nombre_annee) {
        // Source : http://java.worldbestlearningcenter.com/2013/04/amortization-program.html
        // Cette source nous premet de calculer toutes nos données sauf le taux d'assurance
        
        this.montant = montant;
        this.taux = taux;
        this.tauxdassurance = tauxdassurance;
        this.nombre_annee = nombre_annee;

        double newmontant;
        double tauxperyear = (taux / 12) / 100;
        int nombre_mois = nombre_annee * 12;
        double txassur = (tauxdassurance/100);
        double monthspay, interestpay, principal_pain;
        int i;

        model = (DefaultTableModel)tableAmort.getModel();
        monthspay = montant * tauxperyear * Math.pow(1 + tauxperyear, (double) nombre_mois) / (Math.pow(1 + tauxperyear, (double) nombre_mois) - 1);
        txassur=this.montant*txassur;
        // On affiche tous les mois sauf le dernier
        for (i = 1; i < nombre_mois; i++) {

            interestpay = montant * tauxperyear;//interest paid
            principal_pain = monthspay - interestpay; //princial paid
            newmontant = montant - principal_pain; //new balance 
            
            totalassurance+=txassur;
            totalinteret+=interestpay;
            totalmensualite+=principal_pain;
            // printSch(i,montant,monthspay,interestpay,principal_pain,newmontant);

            data[0] = i;
            data[1] = df.format(montant);
            data[2] = df.format(monthspay);
            data[3] = df.format(interestpay);
            data[4] = df.format(principal_pain);
            data[5]=  df.format(txassur);
            data[6] = df.format(newmontant);
            
            model.addRow(data);          
            montant = newmontant;  //update old balance

           // model.addRow(i,montant,monthspay,interestpay,principal_pain,newmontant);

        }

        //last month
        principal_pain = montant;
        interestpay = montant * tauxperyear;
        monthspay = principal_pain + interestpay;
        newmontant = 0.0;
        // printSch(i,montant,monthspay,interestpay,principal_pain,newmontant);
        
        data[0]=nombre_annee*12;
        data[1]=df.format(principal_pain);
        data[2]=df.format(monthspay);
        data[3]=df.format(interestpay);
        data[4]=df.format(newmontant);
        data[5]=df.format(txassur);
        data[6]=df.format(newmontant); 
        
        model.addRow(data);
                        
        
        totalassurance+=txassur;
        totalinteret+=interestpay;
        totalmensualite+=principal_pain;
        
        lblTotalMensualite.setText("Total des mensualités hors-assurance: "+df.format(totalmensualite+totalinteret));
        lblTotalInteret.setText("Total des interets : "+df.format(totalinteret));
        lblTotalAssurance.setText("Total de l'assurance : "+totalassurance);

    }
    
 
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableAmort = new javax.swing.JTable();
        lblEmprunt = new java.awt.Label();
        lblTaux = new java.awt.Label();
        lblAssurance = new java.awt.Label();
        lblAnnees = new java.awt.Label();
        printButton = new javax.swing.JButton();
        lblTitle = new java.awt.Label();
        lblTotalMensualite = new java.awt.Label();
        lblTotalInteret = new java.awt.Label();
        lblTotalAssurance = new java.awt.Label();
        dateSimul = new java.awt.Label();
        pdfButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableAmort.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Année", "Montant", "Paye/mois", "Interets", "Reste", "Assurance", "NewMontant"
            }
        ));
        tableAmort.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableAmort.setSize(new java.awt.Dimension(400, 200));
        jScrollPane1.setViewportView(tableAmort);

        lblEmprunt.setText("label1");

        lblTaux.setText("label3");

        lblAssurance.setText("label5");

        lblAnnees.setText("label1");

        printButton.setBackground(new java.awt.Color(102, 102, 102));
        printButton.setForeground(new java.awt.Color(255, 255, 255));
        printButton.setText("Imprimer");
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Lucida Bright", 1, 18)); // NOI18N
        lblTitle.setSize(new java.awt.Dimension(42, 30));
        lblTitle.setText("label1");

        lblTotalMensualite.setText("label1");

        lblTotalInteret.setText("label1");

        lblTotalAssurance.setText("label1");

        dateSimul.setText("label1");

        pdfButton.setBackground(new java.awt.Color(102, 102, 102));
        pdfButton.setForeground(new java.awt.Color(255, 255, 255));
        pdfButton.setText("Enregistrer PDF");
        pdfButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdfButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEmprunt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblAnnees, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                .addComponent(lblTaux, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblAssurance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTotalMensualite, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                .addComponent(lblTotalInteret, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTotalAssurance, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pdfButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dateSimul, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                        .addComponent(lblEmprunt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(printButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTaux, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAssurance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblAnnees, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pdfButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalMensualite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalInteret, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalAssurance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(dateSimul, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        lblEmprunt.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        // TODO add your handling code here:
       tabprint();
       
    }//GEN-LAST:event_printButtonActionPerformed

    private void pdfButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdfButtonActionPerformed
        // TODO add your handling code here:
        downloadtab();
    }//GEN-LAST:event_pdfButtonActionPerformed

    public void tabprint(){
        try {
          tableAmort.print();
        } catch (PrinterException pe) {
          System.err.println("Error printing: " + pe.getMessage());
        } 

    }
    
    private void downloadtab() { // Classe à améliorer 
    Document document = new Document(PageSize.A4.rotate());
    try {
      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(surname+"_"+name+"_"+dateStr+"-"+idpdf+".pdf"));

      document.open();
      PdfContentByte cb = writer.getDirectContent();

      cb.saveState();
      Graphics2D g2 = cb.createGraphicsShapes(500, 500);

      Shape oldClip = g2.getClip();
      g2.clipRect(0, 0, 500, 500);

      tableAmort.print(g2);
      g2.setClip(oldClip);

      g2.dispose();
      cb.restoreState();
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    document.close(); 
    }
     
     
     
    public static void main(String args[]) {     
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CalcAm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CalcAm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CalcAm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CalcAm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CalcAm c = new CalcAm();
              
               //c.calculAmortizment();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public java.awt.Label dateSimul;
    public javax.swing.JScrollPane jScrollPane1;
    public java.awt.Label lblAnnees;
    public java.awt.Label lblAssurance;
    public java.awt.Label lblEmprunt;
    public java.awt.Label lblTaux;
    public java.awt.Label lblTitle;
    public java.awt.Label lblTotalAssurance;
    public java.awt.Label lblTotalInteret;
    public java.awt.Label lblTotalMensualite;
    public javax.swing.JButton pdfButton;
    public javax.swing.JButton printButton;
    public javax.swing.JTable tableAmort;
    // End of variables declaration//GEN-END:variables
}
