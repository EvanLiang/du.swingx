package du.swingx.plaf.basic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

public class BasicGraphicsUtils {

    public static boolean isLeftToRight(JTree tree) {
        return tree.getComponentOrientation().isLeftToRight();
    }

    public static boolean isMenuShortcutKeyDown(InputEvent event) {
        return (event.getModifiers() &
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0;
    }
}
