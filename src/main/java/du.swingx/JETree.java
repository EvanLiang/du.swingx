package du.swingx;

import du.swingx.plaf.JETreeAddon;
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.plaf.LookAndFeelAddons;
import org.jdesktop.swingx.tree.DefaultXTreeCellEditor;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.util.Hashtable;
import java.util.Vector;

public class JETree extends JXTree {

    static {
        LookAndFeelAddons.contribute(new JETreeAddon());
    }

    public JETree() {
        super();
    }

    public JETree(Object[] value) {
        super(value);
    }

    public JETree(Vector<?> value) {
        super(value);
    }

    public JETree(Hashtable<?, ?> value) {
        super(value);
    }

    public JETree(TreeNode root) {
        super(root, false);
    }

    public JETree(TreeNode root, boolean asksAllowsChildren) {
        super(new DefaultTreeModel(root, asksAllowsChildren));
    }

    public JETree(TreeModel newModel) {
        super(newModel);
    }

    public void setCellRenderer(TreeCellRenderer renderer) {
        DelegatingRenderer delegatingRenderer = new DelegatingRenderer();
        delegatingRenderer.setDelegateRenderer(renderer);
        super.setCellRenderer(delegatingRenderer);
        if ((renderer instanceof javax.swing.tree.DefaultTreeCellRenderer) &&
                (getCellEditor() instanceof DefaultXTreeCellEditor)) {
            ((DefaultXTreeCellEditor) getCellEditor()).setRenderer((javax.swing.tree.DefaultTreeCellRenderer) renderer);
        }
        firePropertyChange("cellRenderer", null, delegatingRenderer);
    }

    public class DelegatingRenderer extends JXTree.DelegatingRenderer {

        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean selected, boolean expanded, boolean leaf, int row,
                                                      boolean hasFocus) {
            Component result = getDelegateRenderer().getTreeCellRendererComponent(tree,
                    value, selected, expanded, leaf, row, false);

            if ((compoundHighlighter != null) && (row < getRowCount())
                    && (row >= 0)) {
                result = compoundHighlighter.highlight(result,
                        getComponentAdapter(row));
            }

            return result;
        }
    }
}
