package app.listeners;

import java.util.*;

public interface VariableRateSimulationListener {
  public double labelString(String g);
  public String getAnswerAmount();
  public String getAnswerInitialRate();
  public String getAnswerTime();
}
