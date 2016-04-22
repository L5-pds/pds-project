package app;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.TextArea;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ServerInterface extends JFrame {

  private static final long serialVersionUID = 1L;
  private JPanel contentPane;
  private static TextArea lblLogServer = new TextArea();
  private static JButton btnStartServer = new JButton("Start server");
  private Server server;
  private static JLabel userNbLabel;
  private static JLabel availableNbLabel;

  /**
   * Create the frame.
   */
  public ServerInterface() {

    printIHM();

    server = new Server();

    btnStartServer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
          server.launch();
      }
    });

  }

  public static void changeTextLog(String addText)  {
    lblLogServer.setText(lblLogServer.getText() + addText + System.getProperty("line.separator"));
  }

  public static void updateInfoLabel(int connect, int dispo)  {
    userNbLabel.setText(connect + " connexions");
    availableNbLabel.setText(dispo + " disponibles");
  }

  private void printIHM() {
    setTitle("Serveur");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 800, 800);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);
    lblLogServer.setBounds(10, 91, 760, 645);
    lblLogServer.setFont(new Font("Tahoma", Font.CENTER_BASELINE, 15));
    contentPane.add(lblLogServer);

    btnStartServer.setFont(new Font("Tahoma", Font.PLAIN, 25));
    btnStartServer.setBorder(null);
    btnStartServer.setBackground(Color.LIGHT_GRAY);
    btnStartServer.setBounds(10, 11, 549, 69);
    contentPane.add(btnStartServer);

    userNbLabel = new JLabel("0 utilis\u00E9e");
    userNbLabel.setHorizontalAlignment(SwingConstants.CENTER);
    userNbLabel.setBounds(574, 24, 189, 20);
    contentPane.add(userNbLabel);

    availableNbLabel = new JLabel("0 disponible");
    availableNbLabel.setHorizontalAlignment(SwingConstants.CENTER);
    availableNbLabel.setBounds(574, 45, 189, 20);
    contentPane.add(availableNbLabel);
  }
}
