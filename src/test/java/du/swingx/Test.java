package du.swingx;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.treetable.SimpleFileSystemModel;

import javax.swing.*;
import java.io.File;

public class Test {
    public static void main(String[] args) {
        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(new com.jgoodies.looks.plastic.Plastic3DLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Demo");
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);

        JETabbedPane jtp = new JETabbedPane();
        jtp.setClosable(true);
        jtp.add("Tree", new JScrollPane(new JETree()));

        JETreeTable jtt = new JETreeTable(new SimpleFileSystemModel(new File(System.getProperty("user.dir"))));
        jtp.add("TreeTable", new JScrollPane(jtt));

        RSyntaxTextArea textArea = new RSyntaxTextArea();
        textArea.setText("<Xml>Test</Xml>");
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
        textArea.setCodeFoldingEnabled(true);
        jtp.add("RSyntaxTextArea", new RTextScrollPane(textArea));

        JXTitledPanel jxTitledPanel = new JXTitledPanel("JXTitledPanel");
        jxTitledPanel.add(jtp);

        frame.getContentPane().add(jxTitledPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}