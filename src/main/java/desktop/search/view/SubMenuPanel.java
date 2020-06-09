package desktop.search.view;

import desktop.search.model.SubMenu;
import desktop.search.view.util.Gbc;
import desktop.search.view.util.WrapLayout;

import javax.swing.*;
import java.awt.*;

public class SubMenuPanel extends JPanel {

    private SubMenu subMenu;

    public SubMenuPanel(SubMenu subMenu) {
        this.subMenu = subMenu;
        initPanel();
    }

    private void initPanel() {
        setLayout(new GridBagLayout());

        JLabel label = new JLabel(subMenu.getName());
        label.setForeground(Color.WHITE);
        label.setFont(label.getFont().deriveFont(label.getFont().getStyle() | Font.BOLD, 14f));
        add(label, Gbc.constraints(0,0).insets(10,22,0,10).build());

        JPanel panel = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 10));
        panel.setOpaque(false);

        for (String action : subMenu.getActionList()) {
            MenuButton menuButton = new MenuButton(action);
            panel.add(menuButton);
        }

        add(panel, Gbc.constraints(1, 0).insets(0,0,0,0).fillBoth().build());

    }
}
