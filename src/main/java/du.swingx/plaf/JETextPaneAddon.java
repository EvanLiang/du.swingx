package du.swingx.plaf;

import du.swingx.JETextPane;
import org.jdesktop.swingx.plaf.AbstractComponentAddon;
import org.jdesktop.swingx.plaf.DefaultsList;
import org.jdesktop.swingx.plaf.LookAndFeelAddons;

import javax.swing.*;

public class JETextPaneAddon extends AbstractComponentAddon {

    public JETextPaneAddon() {
        super("JETextPane");
        UIManager.getLookAndFeelDefaults().put(JETextPane.uiClassID, null);
    }

    protected void addBasicDefaults(LookAndFeelAddons addon, DefaultsList defaults) {
        defaults.add(JETextPane.uiClassID, "du.swingx.plaf.basic.JEBasicTextPaneUI");
    }
}
