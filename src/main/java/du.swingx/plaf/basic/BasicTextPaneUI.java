package du.swingx.plaf.basic;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;

public class BasicTextPaneUI extends javax.swing.plaf.basic.BasicTextPaneUI {
    public static ComponentUI createUI(JComponent c) {
        return new BasicTextPaneUI(c);
    }

    private JTextPaneUIHelper helper;

    public BasicTextPaneUI(JComponent c) {
        helper = new JTextPaneUIHelper((du.swingx.JTextPane) c);
    }

    @Override
    protected void paintBackground(Graphics g) {
        super.paintBackground(g);
        helper.paintBackground(g);
    }
}
