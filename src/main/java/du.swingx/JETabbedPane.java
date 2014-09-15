package du.swingx;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JETabbedPane extends JTabbedPane {

    private boolean closable = false;

    public JETabbedPane() {
        super();
    }

    public JETabbedPane(int tabPlacement) {
        super(tabPlacement);
    }

    public JETabbedPane(int tabPlacement, int tabLayoutPolicy) {
        super(tabPlacement, tabLayoutPolicy);
    }

    @Override
    public void addTab(String title, Icon icon, Component component) {
        super.addTab(title, icon, component);
        addClosePanel();
    }

    @Override
    public void addTab(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
        addClosePanel();
    }

    @Override
    public void addTab(String title, Component component) {
        super.addTab(title, component);
        addClosePanel();
    }

    private void addClosePanel() {
        for (int i = 0; i < this.getTabCount(); i++) {
            if (!(getTabComponentAt(i) instanceof TabbedPanel)) {
                TabbedPanel panel = new TabbedPanel(this);
                panel.setText(getTitleAt(i));
                setTitleAt(i, "");
                setTabComponentAt(i, panel);
            }
        }
    }

    private void removeClosePanel() {
        for (int i = 0; i < this.getTabCount(); i++) {
            Component c = getTabComponentAt(i);
            if (c instanceof TabbedPanel) {
                setTitleAt(i, ((TabbedPanel) c).getText());
                setTabComponentAt(i, null);
            }
        }
    }

    public boolean isClosable() {
        return closable;
    }

    public void setClosable(boolean closable) {
        this.closable = closable;
        if (closable) {
            addClosePanel();
        } else {
            removeClosePanel();
        }
    }

    class TabbedPanel extends JPanel {
        private final JTabbedPane tabbedPanel;
        private JLabel label;

        public TabbedPanel(final JTabbedPane tabbedPanel) {
            super(new FlowLayout(FlowLayout.LEFT, 0, 0));
            this.tabbedPanel = tabbedPanel;

            setOpaque(false);
            label = new JLabel();
            add(label);
            add(new TabButton());
            setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));
        }

        public String getText() {
            return label.getText();
        }

        public void setText(String text){
            label.setText(text);
        }

        private class TabButton extends JButton {
            public TabButton() {
                setPreferredSize(new Dimension(15, 15));
                setToolTipText("Close");
                setUI(new BasicButtonUI());
                setContentAreaFilled(false);
                setFocusable(false);
                setBorderPainted(false);
                setRolloverEnabled(true);
                setBorder(null);
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        Component component = e.getComponent();
                        if (component instanceof AbstractButton) {
                            AbstractButton button = (AbstractButton) component;
                            button.setBorderPainted(true);
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        Component component = e.getComponent();
                        if (component instanceof AbstractButton) {
                            AbstractButton button = (AbstractButton) component;
                            button.setBorderPainted(false);
                        }
                    }
                });
                addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        int i = tabbedPanel.indexOfTabComponent(TabbedPanel.this);
                        if (i != -1) tabbedPanel.remove(i);
                    }
                });
            }

            @Override
            public void updateUI() {
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                if (getModel().isPressed()) {
                    g2.translate(1, 1);
                }
                g2.setStroke(new BasicStroke(1));
                g2.setColor(Color.GRAY);
                if (getModel().isRollover()) {
                    g2.setColor(Color.RED);
                }
                int delta = 10;
                g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
                g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
                g2.dispose();
            }
        }
    }
}