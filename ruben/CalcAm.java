package app.views.ruben;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.xml.internal.ws.api.databinding.MappingInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.print.PrinterException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import javafx.scene.input.DataFormat;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CalcAm extends javax.swing.JFrame {

    private static JPanel contentPane;
    String name="Ruben";
    String surname="Edery";
    int idpdf=(int) (Math.random() * 100000 + 1);
    int nombre_annee;
    double tauxdassurance;
    double taux;
    double montant;
    double totalmensualite; 
    double totalinteret; 
    double totalassurance;
    static DefaultTableModel model;
    String data[][];
    DecimalFormat df = new DecimalFormat("0.00");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy à HH:mm:ss");
    String dateStr = simpleDateFormat.format(new Date());
    
    public CalcAm(String data[][],int nombre_annee,
            double tauxdassurance,
            double taux,
            double montant,
            double totalmensualite, 
            double totalinteret,
            double totalassurance )
    {
        this.nombre_annee = nombre_annee;
        this.tauxdassurance = tauxdassurance;
        
        this.taux=taux;
        this.montant=montant;
        this.totalmensualite=totalmensualite;
        this.totalinteret=totalinteret;
        this.totalassurance=totalassurance;
        
                
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
        amortissement_calcul(data, montant, taux, tauxdassurance, nombre_annee);
        this.data = data;
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
        lblEmprunt.setText("Emprunt : "+df.format(totalmensualite)+" €");
        // Affiche la date actuelle
        dateSimul.setText("Date de la simulation : "+dateStr );
    }

    public void amortissement_calcul(String data[][], double montant, double taux, double tauxdassurance, int nombre_annee) {
        // Source : http://java.worldbestlearningcenter.com/2013/04/amortization-program.html
        // Cette source nous premet de calculer toutes nos données sauf le taux d'assurance
        
        model = (DefaultTableModel)tableAmort.getModel();
        // On affiche tous les mois sauf le dernier
        for (int i = 1; i < 48; i++) {
            model.addRow(data[i]);          
        }

        lblTotalMensualite.setText("Total des mensualités hors-assurance: "+df.format(totalmensualite+totalinteret));
        lblTotalInteret.setText("Total des interets : "+df.format(totalinteret));
        lblTotalAssurance.setText("Total de l'assurance : "+df.format(totalassurance));
    }
    

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
        jButton1 = new javax.swing.JButton();

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

        printButton.setBackground(new java.awt.Color(255, 51, 51));
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
        pdfButton.setText("Enregistrer PDF");
        pdfButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pdfButtonActionPerformed(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(printButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pdfButton, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pdfButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addGap(13, 13, 13)
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
       tabprint();       
    }//GEN-LAST:event_printButtonActionPerformed

    private void pdfButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pdfButtonActionPerformed
       
        try{
            pdfgener();
            JOptionPane.showMessageDialog(this,"Le fichier est bien télécharger","Telechargement",1);
        }
        catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }//GEN-LAST:event_pdfButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_jButton1ActionPerformed

    public void tabprint(){
        try {
          tableAmort.print();
        } catch (PrinterException pe) {
          System.err.println("Error printing: " + pe.getMessage());
        } 
    }
    
    private void downloadtab() { // Classe à améliorer Generer fichier excel.
    Document document = new Document(PageSize.A4.rotate());
    try {
      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(surname+"_"+name+"_"+dateStr+"-"+idpdf+".pdf"));

      document.open();
      PdfContentByte cb = writer.getDirectContent();

      cb.saveState();
      Graphics2D g2 = cb.createGraphicsShapes(500, 1000);

      Shape oldClip = g2.getClip();
      g2.clipRect(0,0, 700, 700);

      tableAmort.print(g2);
      g2.setClip(oldClip);
      g2.dispose();
      cb.restoreState();

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
    document.close(); 

    
    }
     
    
    private void pdfgener(){
        Document document = new Document();
        String title = "<html> <h1><u>Rapport de la simulation de prêt de " +name +" "+ surname+"<u></h1><br/></html>";
        String linkMail = "<html> Email : <a href= mailto:\\\"contact@l5.fr\\\">contact@l5.fr</a><html>";
        String phoneTel = "<html> Téléphone : <a href= \\\"01.43.53.07.31\\\"> 01.43.53.07.31</a><html>";
        //String phoneTel2 = "<a href=\"tel:+33102030405\"> Appeler la société xxx </a>"; 
        String tab [] = {
                "Année", "Montant", "Paye/mois", "Interets", "Reste", "Assurance", "NewMontant"
                          };
        //P.repaint();
        //pp.repaint();

        document.setPageSize(new Rectangle(1500,1000));

            try { 

                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(surname+"_"+name+"_"+dateStr+"-"+idpdf+".pdf" ));
                document.open();
                PdfContentByte contentByte = writer.getDirectContent();
                
                PdfTemplate template = contentByte.createTemplate(1500,1000);//500, 1184
                Graphics2D g2 = template.createGraphics(1500,1000);
                document.newPage();
                HTMLWorker htmlWorker = new HTMLWorker(document);
                htmlWorker.parse(new StringReader(title));

                //document.addTitle("BONJOUR");
                //document.addTitle("Rapport de supervison");
                document.addCreationDate();
                //Paragraph p = new Paragraph(title);
                //p.setAlignment(1);
                //document.add(p);
                document.add(new Paragraph("Ci-dessous, vous pouvez trouver la simulation de prêt que vous avez effectuer le "+dateStr));
                document.add(new Paragraph("Pour plus d'informations, vous pouvez nous contacter par email ou par téléphone : "));
                //HTMLWorker htmlWorker = new HTMLWorker(document);
                htmlWorker.parse(new StringReader(linkMail));
                htmlWorker.parse(new StringReader(phoneTel));

                document.newPage();
                
                tableAmort.print(g2);// T est le JTable 
                
                g2.dispose();

                contentByte.addTemplate(template,30, 0);

            } catch (Exception e) {
                e.printStackTrace();
            }
            finally{
                
                if(document.isOpen()){
                document.close();
                }
            }
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public java.awt.Label dateSimul;
    public javax.swing.JButton jButton1;
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
