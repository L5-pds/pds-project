package app.views.ruben;

import app.views.alexandre.FixedRateSimulation;
import java.io.FileOutputStream;
import app.views.welcome.WelcomeViewClient;
import com.itextpdf.text.pdf.PdfContentByte;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.DecimalFormat;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class CalcAm {
// Variables declaration - do not modify

    JPanel pane = new JPanel();
    JPanel paneLeft = new JPanel();
    JPanel paneRight = new JPanel();
    JPanel paneCenter = new JPanel();
    JPanel paneBottom = new JPanel();

    public JScrollPane jScrollPane1;
    public Label lblYears;
    public Label lblinsurance;
    public Label lblloan;
    public Label lblRate;
    public Label lblTitle;
    public Label lblTotalinsurance;
    public Label lbltotalInterest;
    public Label lbltotalMonthlyPayement;
    public JButton pdfButton;
    public JButton printButton;
    public JButton graphButton = new JButton();
    public JTable tableAmort;

    public String name = "Ruben";
    public String surname = "Edery";
    int idpdf = (int) (Math.random() * 100000 + 1);
    int number_month;
    double insuranceRate;
    double rate;
    double amount;
    double totalMonthlyPayement;
    double totalInterest;
    double totalinsurance;
    static DefaultTableModel model;

    String data[][];
    DecimalFormat df = new DecimalFormat("0.00");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy à HH:mm:ss");
    String dateStr = simpleDateFormat.format(new Date());

// End of variables declaration

    public CalcAm(String data[][], int nombre_annee,
            double insuranceRate,
            double rate,
            double amount,
            double totalMonthlyPayement,
            double totalInterest,
            double totalinsurance) {
        this.number_month = nombre_annee;
        this.insuranceRate = insuranceRate;

        this.rate = rate;
        this.amount = amount;
        this.totalMonthlyPayement = totalMonthlyPayement;
        this.totalInterest = totalInterest;
        this.totalinsurance = totalinsurance;

        initComponents(); //
        addPane();
        this.data = data;
        amortissement_calcul(data, amount, rate, insuranceRate, nombre_annee);
        pane.revalidate();
        pane.repaint();
    }

    public void amortissement_calcul(String data[][], double amount, double rate, double insuranceRate, int nombre_annee) {
        // Source : http://java.worldbestlearningcenter.com/2013/04/amortization-program.html
        // Cette source nous premet de calculer toutes nos données sauf le rate d'insurance

        model = (DefaultTableModel) tableAmort.getModel();
        // On affiche tous les mois sauf le dernier
        for (int i = 1; i < number_month+1; i++) {
            model.addRow(data[i]);
        }
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableAmort = new javax.swing.JTable();
        lblloan = new java.awt.Label();
        lblRate = new java.awt.Label();
        lblinsurance = new java.awt.Label();
        lblYears = new java.awt.Label();
        lblTitle = new java.awt.Label();
        lbltotalMonthlyPayement = new Label();
        lbltotalInterest = new Label();
        lblTotalinsurance = new Label();
        pdfButton = new JButton();

        JButton graphbutton = new JButton("Graphique");

        graphbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                    Calcul ca = new Calcul();
                    Chart am = new Chart(ca);
                    LineChart lc = new LineChart(ca, "Restant à payer selon les trimestres", "Restant à payer selon les trimestres", ca.data);
                    pane.removeAll();
                    paneLeft.add(am);
                    paneRight.add(lc);
                    pane.revalidate();
                    pane.repaint();
            }
        });
        paneLeft.add(graphbutton);

        tableAmort.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Mois N°", "Montant", "Mensualité Totale", "Intérets", "Mensualités", "Assurance", "Capital Restant"
                }
        ));
        tableAmort.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableAmort.setSize(new java.awt.Dimension(900, 400));
        jScrollPane1.setViewportView(tableAmort);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(700, 400));
        paneCenter.add(jScrollPane1);

        lblTitle.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        lblTitle.setSize(new java.awt.Dimension(42, 30));

        // Affichage de l'icone d'impression
        addPrintIcon();

        // Affichage de l'icone PDF
        addPdfIcon();

        //Affichage des labels
        displayLabel();
        // Affichage des label totaux
        displayTotalLabel();

    }

    public void addPane() {
        pane.setBorder(new EmptyBorder(5, 5, 5, 5));
        pane.setBackground(new Color(215, 203, 233, 255));
        paneLeft.setBackground(new Color(215, 203, 233, 255));
        paneRight.setBackground(new Color(215, 203, 233, 255));
        paneCenter.setBackground(new Color(215, 203, 233, 255));
        paneBottom.setBackground(new Color(215, 203, 233, 255));
        pane.setLayout(new BorderLayout());
        pane.setPreferredSize(new Dimension(1000, 450));
        paneLeft.setLayout(new BoxLayout(paneLeft, BoxLayout.Y_AXIS));
        paneRight.setLayout(new BoxLayout(paneRight, BoxLayout.Y_AXIS));
        paneBottom.setLayout(new FlowLayout());
        pane.setVisible(true);
        pane.add(paneLeft, BorderLayout.WEST);
        pane.add(paneRight, BorderLayout.EAST);
        pane.add(paneCenter, BorderLayout.CENTER);
        pane.add(paneBottom, BorderLayout.SOUTH);

    }

    public void addPrintIcon() {
        ImageIcon imgPrinter = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinter.png"));
        Image im = imgPrinter.getImage();
        im = im.getScaledInstance(60, 60, 1);
        JLabel buttonPrint = new JLabel(new ImageIcon(im));
        buttonPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPrint.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabprint();
            }
            public void mouseEntered(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinterHover.png")).getImage().getScaledInstance(50, 50, 1);
                buttonPrint.setIcon(new ImageIcon(im));
            }

            public void mouseExited(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPrinter.png")).getImage().getScaledInstance(50, 50, 1);
                buttonPrint.setIcon(new ImageIcon(im));
            }

        });
        paneRight.add(buttonPrint);
    }

    public void addPdfIcon() {
        ImageIcon imgPdf = new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPDF.png"));
        Image pict = imgPdf.getImage();
        pict = pict.getScaledInstance(60, 60, 1);
        JLabel pdfButton = new JLabel(new ImageIcon(pict));
        pdfButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pdfButton.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    pdfgener();
                    JOptionPane.showMessageDialog(pane, "Le fichier est bien télécharger", "Telechargement", 1);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                    JOptionPane.showMessageDialog(pane, "Erreur de téléchargement. Veuillez changer le code du programme.", "Erreur téléchargement", 1);
                }
            }

            public void mouseEntered(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPDFHover.png")).getImage().getScaledInstance(50, 50, 1);
                pdfButton.setIcon(new ImageIcon(im));
            }

            public void mouseExited(MouseEvent e) {
                Image im= new ImageIcon(WelcomeViewClient.class.getResource("/pictures/iconPDF.png")).getImage().getScaledInstance(50, 50, 1);
                pdfButton.setIcon(new ImageIcon(im));
            }
        });
        paneRight.add(new JLabel(" "));
        paneRight.add(pdfButton);
    }

    public void displayLabel() {
        JLabel amountLoan = new JLabel("Montant emprunté : " + df.format(totalMonthlyPayement) + " €");
        amountLoan.setBounds(6, 61, 161, 16);
        paneLeft.add(amountLoan);

        JLabel lblDurationLoan = new JLabel("Durée du pret : " + number_month + " mois");
        lblDurationLoan.setBounds(6, 12, 161, 16);
        paneLeft.add(lblDurationLoan);

        JLabel lblRateLoan = new JLabel("Taux d'emprunt : " + rate + " %");
        lblRateLoan.setBounds(6, 40, 161, 16);
        paneLeft.add(lblRateLoan);

        JLabel lblRateinsurance = new JLabel("Taux d'assurance : " + insuranceRate + " %");
        lblRateinsurance.setBounds(6, 79, 123, 16);
        paneLeft.add(lblRateinsurance);
    }

    public void displayTotalLabel() {
        lbltotalMonthlyPayement = new Label("                      Total des mensualités : " + df.format(totalMonthlyPayement) + " €");
        lbltotalInterest = new Label("Total des interets : " + df.format(totalInterest) + " €");
        lblTotalinsurance = new Label("Total de l'assurance : " + df.format(totalinsurance) + " €");

        lbltotalMonthlyPayement.setBounds(10, 50, 100, 20);
        lbltotalInterest.setBounds(10, 50, 100, 20);
        lblTotalinsurance.setBounds(10, 50, 100, 20);

        paneBottom.add(lbltotalMonthlyPayement);
        paneBottom.add(lbltotalInterest);
        paneBottom.add(lblTotalinsurance);

    }

    public void tabprint() {
        try {
            tableAmort.print();
        } catch (PrinterException pe) {
            System.err.println("Erreur d'impression: " + pe.getMessage());
        }
    }

    private void pdfgener() {

        Document document = new Document();

        try {
            
            SimpleDateFormat simpleDateSpecifFormat = new SimpleDateFormat("dd-MM-yyyy_HHmmss");
            String dataSpecifStr = simpleDateSpecifFormat.format(new Date());
            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(surname + "_" + name + "_" + dataSpecifStr + "-" + idpdf + ".pdf"));
            document.open();
            // Set color and police

            com.itextpdf.text.Font fontText = FontFactory.getFont(FontFactory.COURIER_BOLD, 18, com.itextpdf.text.Font.UNDERLINE);
            fontText.setColor(215,203,233);

            String information = "Voici un récapitulatif de la simulation faite par " + name + " " + surname + " à la date du " + dateStr + ". Le montant de l'emprunt est de " + df.format(totalMonthlyPayement) + " €  avec un taux d'interet prévu à " + rate + "% et un taux d'assurance prévu à " + insuranceRate + "%. Cette simulation est faite pour un prêt avec une durée de " + number_month + " mois.";
            Paragraph recapParagraph = new Paragraph(information);
            recapParagraph.setAlignment(PdfContentByte.ALIGN_LEFT);

            Chunk title = new Chunk("Récapitulatif du prêt", fontText);
            Paragraph titlePdf = new Paragraph(title);
            titlePdf.setAlignment(PdfContentByte.ALIGN_CENTER);

            Chunk amortTabTitle = new Chunk("Tableau d'amortissement", fontText);
            Paragraph amortTitlePar = new Paragraph(amortTabTitle);
            amortTitlePar.setAlignment(PdfContentByte.ALIGN_CENTER);

            Paragraph lineSpace = new Paragraph("                   ");
            lineSpace.setAlignment(PdfContentByte.ALIGN_CENTER);

            String footer = "Pour plus d'information, vous pouvez nous contacter par téléphone au 0651525354 ou bien par email : L5presta@L5.fr .";
            Paragraph footerParagraph = new Paragraph(footer);
            footerParagraph.setAlignment(PdfContentByte.ALIGN_LEFT);



            //com.itextpdf.text.Image logoL5 = com.itextpdf.text.Image.getInstance("/pictures/LogoL5.png");
            //document.add(logoL5);

            PdfPTable table = new PdfPTable(7);
            table.addCell("Mois N°");
            table.addCell("Montant");
            table.addCell("Mensualité Totale");
            table.addCell("Intérêts");
            table.addCell("Mensualités");
            table.addCell("Assurance");
            table.addCell("Capital Restant");

            for (int i = 0; i < number_month; i++) {
                table.addCell(data[i][0]);
                table.addCell(data[i][1]);
                table.addCell(data[i][2]);
                table.addCell(data[i][3]);
                table.addCell(data[i][4]);
                table.addCell(data[i][5]);
                table.addCell(data[i][6]);
            }

            document.add(titlePdf);
            document.add(lineSpace);
            document.add(recapParagraph);
            document.add(lineSpace);
            document.add(lineSpace);
            document.add(amortTitlePar);
            document.add(lineSpace);
            document.add(lineSpace);
            document.add(table);
            document.add(lineSpace);
            document.add(lineSpace);
            document.add(footerParagraph);

        } catch (Exception e) {
            e.printStackTrace();
        }

        document.close();

    }

    public int getNumberMonths(){
        return number_month;
    }
}
