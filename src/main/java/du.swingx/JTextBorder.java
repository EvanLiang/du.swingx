package du.swingx;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class JTextBorder extends AbstractBorder {

    private JTextPane parent;
    private Color color;
    private int oneInset = -1;
    private int leftInset = -1;
    private Rectangle[] lineRect;

    public JTextBorder(JTextPane parent) {
        this.parent = parent;
    }

    public Insets getBorderInsets(Component c) {
        return getBorderInsets(c, new Insets(0, 0, 0, 0));
    }

    public Insets getBorderInsets(Component c, Insets insets) {
        setWidth(c.getFontMetrics(c.getFont()));
        insets.left = leftInset + 2;
        return insets;
    }

    public boolean isBorderOpaque() {
        return false;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(c.getBackground());
        g.fillRect(x, y, leftInset, height);

        g.setColor(color);
        Stroke dash = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0.5f, new float[]{1, 1,}, 0f);
        g2d.setStroke(dash);
        g2d.drawLine(leftInset, y, leftInset, height);

        g2d.setFont(parent.getFont());
        FontMetrics fm = g2d.getFontMetrics();
        updateLineRect(fm);
        paintBackground(g);

        int fontHeight = fm.getHeight();
        int ybaseline = y + fm.getAscent();

        String[] borderText = parent.getBorderText();
        Color[] borderTextColor = parent.getBorderTextColor();
        if (borderText != null) {
            for (int i = 0; i < borderText.length; i++) {
                String text = borderText[i];
                int left = leftInset - (oneInset * text.length()) - 2;
                if (borderTextColor != null && borderTextColor.length > i && borderTextColor[i] !=null) {
                    g2d.setColor(borderTextColor[i]);
                } else {
                    g2d.setColor(color);
                }
                g2d.drawString(text, left, ybaseline);
                ybaseline += fontHeight;
            }
        }
    }

    protected void paintBackground(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int height = fm.getHeight();
        Insets insets = parent.getInsets();
        int[] highlight = parent.getHighlight();
        Color[] highlightColor = parent.getHighlightColor();

        Color original = g.getColor();
        if (highlight != null) {
            for (int i = 0; i < highlight.length; i++) {
            	if(highlightColor[i]!=null){
                	g.setColor(highlightColor[i]);
                	g.fillRect(0, insets.top + (highlight[i] * height), leftInset, height);
                }
            }
        }

        if (parent.isFocus()) {
            int lineFocus = parent.getLineFocus();
            g.setColor(parent.getFocusColor());
            g.fillRect(0, insets.top + (lineFocus * height), leftInset, height);
        }

        if (parent.isHover()) {
            int lineHover = parent.getLineHover();
            g.setColor(parent.getHoverColor());
            g.fillRect(0, insets.top + (lineHover * height), leftInset, height);
        }
        g.setColor(original);
    }

    private void updateLineRect(FontMetrics fm) {
        int lineCount = parent.getLineCount();
        if (lineRect == null || lineRect.length != lineCount) {
            int height = fm.getHeight();
            Insets insets = parent.getInsets();
            int lineWidth = leftInset;
            lineRect = new Rectangle[lineCount];
            for (int i = 0; i < lineCount; i++) {
                Rectangle rect = new Rectangle();
                rect.setBounds(insets.left, insets.top + (height * i), lineWidth, height);
                lineRect[i] = rect;
            }
        }
    }

    private void setWidth(FontMetrics fm) {
        String[] borderText = parent.getBorderText();
        if (borderText != null) {
            if (leftInset == -1) {
                String text = "";
                for (String line : borderText) {
                    if (line.length() > text.length()) {
                        text = line;
                    }
                }
                leftInset = fm.stringWidth(String.valueOf(text)) + 2;
                oneInset = fm.stringWidth(String.valueOf("0"));
            }
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}