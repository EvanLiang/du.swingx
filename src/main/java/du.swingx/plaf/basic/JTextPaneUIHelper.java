package du.swingx.plaf.basic;

import du.swingx.JTextPane;

import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JTextPaneUIHelper {

    private JTextPane editor;
    private Rectangle[] lineRect;

    public JTextPaneUIHelper(JTextPane editor) {
        this.editor = editor;
        registerFocus();
        registerHover();
    }

    private void registerFocus() {
        editor.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                if (editor.isFocus()) {
                    int lineFocus = editor.getLineOfOffset(editor.getCaretPosition());
                    editor.setLineFocus(lineFocus);
                    editor.repaint();
                }
            }
        });
    }

    private void registerHover() {
        editor.addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (editor.isHover()) {
                    registerUpdateLineRect();
                    for (int i = 0; i < lineRect.length; i++) {
                        if (lineRect[i].contains(e.getPoint())) {
                            editor.setLineHover(i);
                            editor.repaint();
                            break;
                        }
                    }
                }
            }
        });
    }

    private void registerUpdateLineRect() {
        if (lineRect == null) {
            updateLineRect();
            editor.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) {
                    updateLineRect();
                }

                public void removeUpdate(DocumentEvent e) {
                    updateLineRect();
                }

                public void changedUpdate(DocumentEvent e) {
                    updateLineRect();
                }
            });
        }
    }

    private void updateLineRect() {
        FontMetrics fm = editor.getFontMetrics(editor.getFont());
        int height = fm.getHeight();
        int lineCount = editor.getLineCount();
        Insets insets = editor.getInsets();
        int lineWidth = editor.getWidth() - insets.left - insets.right;

        lineRect = new Rectangle[lineCount];
        for (int i = 0; i < lineCount; i++) {
            Rectangle rect = new Rectangle();
            rect.setBounds(insets.left, insets.top + (height * i), lineWidth, height);
            lineRect[i] = rect;
        }
    }

    protected void paintBackground(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int height = fm.getHeight();
        Insets insets = editor.getInsets();
        int lineWidth = editor.getWidth() - insets.left - insets.right;
        int[] highlight = editor.getHighlight();
        Color[] highlightColor = editor.getHighlightColor();

        Color original = g.getColor();
        if (highlight != null) {
            for (int i = 0; i < highlight.length; i++) {
                if (highlightColor != null && highlightColor.length > i) {
                    if(highlightColor[i]!=null){
                    	g.setColor(highlightColor[i]);
                    	g.fillRect(insets.left, insets.top + (highlight[i] * height), lineWidth, height);
                    }
                }
            }
        }

        if (editor.isFocus()) {
            int lineFocus = editor.getLineFocus();
            g.setColor(editor.getFocusColor());
            g.fillRect(insets.left, insets.top + (lineFocus * height), lineWidth, height);
        }

        if (editor.isHover()) {
            int lineHover = editor.getLineHover();
            g.setColor(editor.getHoverColor());
            g.fillRect(insets.left, insets.top + (lineHover * height), lineWidth, height);
        }
        g.setColor(original);
    }
}