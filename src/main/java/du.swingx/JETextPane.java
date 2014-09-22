package du.swingx;

import du.swingx.plaf.JETextPaneAddon;
import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.plaf.LookAndFeelAddons;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;

public class JETextPane extends javax.swing.JTextPane {

    public static final String uiClassID = "ETextPaneUI";
    private static final String EOL = "\n";

    static {
        LookAndFeelAddons.contribute(new JETextPaneAddon());
    }

    public String getUIClassID() {
        return uiClassID;
    }

    private int lineFocus = 0;
    private int lineHover = 0;
    private int[] highlight;
    private Color[] highlightColor;
    private boolean focus;
    private Color focusColor;
    private boolean hover;
    private Color hoverColor;
    private String[] borderText;
    private Color[] borderTextColor;

    public JETextPane() {
        initialize();
    }

    private void initialize() {
        setCursor(new Cursor(Cursor.TEXT_CURSOR));
    }

    private String getAllText() {
        Document doc = getDocument();
        if (doc != null) {
            try {
                return doc.getText(0, doc.getLength());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public int getLineCount() {
        String allText = getAllText();
        int lineCount = StringUtils.countMatches(allText, EOL);
        return lineCount + 1;
    }

    public int getLineOfOffset(int offset) {
        if (offset <= 0) {
            return 0;
        }
        String allText = getAllText();
        if (offset > allText.length()) {
            return allText.length();
        }
        int line = 0;
        int offs = allText.indexOf(EOL);
        while (offset > offs) {
            line++;
            offs = allText.indexOf(EOL, offs + 1);
            if (offs == -1) {
                break;
            }
        }
        return line;
    }

    public int lineOffsetStart(int line) {
        if (line <= 0) {
            return 0;
        }
        int ordinal = line;
        int lineCount = getLineCount();
        if (line >= lineCount) {
            ordinal = lineCount - 1;
        }
        String allText = getAllText();
        return StringUtils.ordinalIndexOf(allText, EOL, ordinal) + 1;
    }

    public int lineOffsetEnd(int line) {
        return lineOffsetStart(line + 1);
    }

    public void append(String str) {
        append(str, null);
    }

    public void append(String str, AttributeSet a) {
        Document doc = getDocument();
        if (doc != null) {
            try {
                doc.insertString(doc.getLength(), str.replace("\r", ""), a);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert(int before, String str) {
        insert(before, str, null);
    }

    public void insert(int before, String str, AttributeSet a) {
        Document doc = getDocument();
        if (doc != null) {
            try {
                int offset = lineOffsetStart(before);
                doc.insertString(offset, str.replace("\r", ""), null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public void remove(int offs, int len) {
        Document doc = getDocument();
        if (doc != null) {
            try {
                doc.remove(offs, len);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    public void remove(int line) {
        int start = lineOffsetStart(line);
        int end = lineOffsetEnd(line);
        System.out.println(start + "," + (end - start));
        remove(start, end - start);
    }

    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public void setSize(Dimension d) {
        if (d.width < getParent().getSize().width) {
            d.width = getParent().getSize().width;
        }
        d.width += 100;
        super.setSize(d);
    }

    public int getLineFocus() {
        return lineFocus;
    }

    public void setLineFocus(int lineFocus) {
        this.lineFocus = lineFocus;
    }

    public int getLineHover() {
        return lineHover;
    }

    public void setLineHover(int lineHover) {
        this.lineHover = lineHover;
    }

    public int[] getHighlight() {
        return highlight;
    }

    public void setHighlight(int[] highlight) {
        this.highlight = highlight;
    }

    public Color[] getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(Color[] highlightColor) {
        this.highlightColor = highlightColor;
    }

    public boolean isFocus() {
        return focus;
    }

    public void setFocus(boolean focus) {
        this.focus = focus;
    }

    public Color getFocusColor() {
        return focusColor;
    }

    public void setFocusColor(Color focusColor) {
        this.focusColor = focusColor;
    }

    public boolean isHover() {
        return hover;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public String[] getBorderText() {
        return borderText;
    }

    public void setBorderText(String[] borderText) {
        this.borderText = borderText;
    }

    public Color[] getBorderTextColor() {
        return borderTextColor;
    }

    public void setBorderTextColor(Color[] borderTextColor) {
        this.borderTextColor = borderTextColor;
    }
}
