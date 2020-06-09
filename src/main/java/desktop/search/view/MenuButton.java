package desktop.search.view;

import javax.swing.JPanel;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class MenuButton extends JPanel {

	/**
	 * Create the panel.
	 */
	public MenuButton(String text) {
		setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton = new JButton(text, new ImageIcon(getClass().getClassLoader().getResource("search.png")));
		btnNewButton.setVerticalTextPosition(AbstractButton.BOTTOM);
		btnNewButton.setHorizontalTextPosition(AbstractButton.CENTER);
		btnNewButton.setPreferredSize(new Dimension(140, 90));
		add(btnNewButton);

	}

}
