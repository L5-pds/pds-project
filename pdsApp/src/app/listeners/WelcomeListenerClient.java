package app.listeners;

import java.util.*;

public interface WelcomeListenerClient {
    void authenticationIhm();
    void updateAnswerLabel(String answer);
    void setMenu();
    void setButtonBackMenu();
}
