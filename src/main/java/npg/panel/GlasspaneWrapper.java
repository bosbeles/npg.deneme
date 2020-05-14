package npg.panel;

import javax.swing.*;
import java.awt.event.MouseAdapter;

public class GlasspaneWrapper extends JLayeredPane {

    private JPanel glassPanel = new JPanel();

    public GlasspaneWrapper(JComponent myPanel) {
        glassPanel.setOpaque(false);
        glassPanel.setVisible(false);
        glassPanel.addMouseListener(new MouseAdapter() {
        });
        glassPanel.setFocusable(true);

        myPanel.setSize(myPanel.getPreferredSize());
        add(myPanel, JLayeredPane.DEFAULT_LAYER);
        add(glassPanel, JLayeredPane.PALETTE_LAYER);

        glassPanel.setPreferredSize(myPanel.getPreferredSize());
        glassPanel.setSize(myPanel.getPreferredSize());
        setPreferredSize(myPanel.getPreferredSize());
    }

    public void activateGlassPane(boolean activate) {
        glassPanel.setVisible(activate);
        if (activate) {
            glassPanel.requestFocusInWindow();
            glassPanel.setFocusTraversalKeysEnabled(false);
        }
    }

}
