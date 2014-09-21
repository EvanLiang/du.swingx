package du.swingx;

import du.swingx.plaf.JETreeAddon;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.plaf.LookAndFeelAddons;
import org.jdesktop.swingx.treetable.TreeTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class JETreeTable extends JXTreeTable {
    static {
        LookAndFeelAddons.contribute(new JETreeAddon());
        UIManager.put("Table.focusCellHighlightBorder", new EmptyBorder(0, 0, 0, 0));
    }

    public JETreeTable() {
        super();
    }

    public JETreeTable(TreeTableModel treeModel) {
        super(treeModel);
    }

    @Override
    protected TreeTableHacker createTreeTableHacker() {
        //Allow row selected for tree
        return new TreeTableHacker();
    }
}
