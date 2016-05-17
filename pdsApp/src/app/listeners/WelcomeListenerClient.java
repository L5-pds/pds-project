package app.listeners;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.MatteBorder;

public interface WelcomeListenerClient {
    void authenticationIhm();
    void updateAnswerLabel(String answer);
    void setMenu();
    void setButtonBackMenu();
    JPanel getBody();
    Container getContainer();
}
