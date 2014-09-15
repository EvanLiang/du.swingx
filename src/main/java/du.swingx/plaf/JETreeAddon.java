package du.swingx.plaf;

import org.jdesktop.swingx.plaf.AbstractComponentAddon;
import org.jdesktop.swingx.plaf.DefaultsList;
import org.jdesktop.swingx.plaf.LookAndFeelAddons;

import javax.swing.*;

public class JETreeAddon extends AbstractComponentAddon {

    private static final String uiClassID = "TreeUI";

    public JETreeAddon() {
        super("JETree");
        UIManager.getLookAndFeelDefaults().put(uiClassID, null);
    }

    @Override
    protected void addBasicDefaults(LookAndFeelAddons addon, DefaultsList defaults) {
        defaults.add(uiClassID, "du.swingx.plaf.basic.BasicTreeUI");
    }
}
