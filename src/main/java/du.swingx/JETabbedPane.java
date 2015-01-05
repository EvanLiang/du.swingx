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
				panel.setText(super.getTitleAt(i));
				setTabComponentAt(i, panel);
			}
		}
	}

	private void removeClosePanel() {
		for (int i = 0; i < this.getTabCount(); i++) {
			Component c = getTabComponentAt(i);
			if (c instanceof TabbedPanel) {
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

	public void closeTabAt(int index) {
		if (index <= -1) {
			return;
		}
		if (tabCloseListener != null
				&& !tabCloseListener.actionPerformed(index)) {
			return;
		}
		remove(index);
	}

	private TabCloseListener tabCloseListener;

	public void setTabCloseListener(TabCloseListener tabCloseListener) {
		this.tabCloseListener = tabCloseListener;
	}

	public interface TabCloseListener {
		boolean actionPerformed(int index);
	}

	class TabbedPanel extends JPanel {
		private final JTabbedPane tabbedPanel;
		private JLabel label;

		public TabbedPanel(final JTabbedPane tabbedPanel) {
			super(new FlowLayout(FlowLayout.LEFT, 0, 0));
			this.tabbedPanel = tabbedPanel;
			setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
			setOpaque(false);
			label = new JLabel();
			label.setOpaque(true);
			add(label);
			add(new TabButton());
		}

		public String getText() {
			return label.getText();
		}

		public void setText(String text) {
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
				setOpaque(true);
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
						int index = tabbedPanel
								.indexOfTabComponent(TabbedPanel.this);
						closeTabAt(index);
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
				g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight()
						- delta - 1);
				g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight()
						- delta - 1);
				g2.dispose();
			}
		}
	}
}