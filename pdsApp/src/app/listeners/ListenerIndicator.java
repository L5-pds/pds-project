package app.listeners;

import app.models.other.PaneSearchIndicator;
import org.jfree.data.general.DefaultPieDataset;

public interface ListenerIndicator {
    void setIHM();
    void makeTablePane(PaneSearchIndicator theTableForPane);
}
