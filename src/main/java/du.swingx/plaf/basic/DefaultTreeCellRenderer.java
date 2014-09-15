package du.swingx.plaf.basic;


import javax.swing.*;
import java.awt.*;

public class DefaultTreeCellRenderer extends javax.swing.tree.DefaultTreeCellRenderer{
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        return this;
    }
}