package du.swingx;

import du.swingx.plaf.JETreeAddon;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.plaf.LookAndFeelAddons;
import org.jdesktop.swingx.treetable.TreeTableModel;

public class JETreeTable extends JXTreeTable {
    static {
        LookAndFeelAddons.contribute(new JETreeAddon());
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
