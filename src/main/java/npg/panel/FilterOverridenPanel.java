package npg.panel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FilterOverridenPanel extends JPanel {

    public FilterOverridenPanel() {

        setBorder(new TitledBorder("Filter Override Fields"));
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.gridheight = 2;
        gbc_panel_2.anchor = GridBagConstraints.WEST;
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 1;
        gbc_panel_2.gridy = 0;
        gbc_panel_2.weightx = 1.0;
        setPreferredSize(new Dimension(200, 100));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JCheckBox chckbxCommandControl = new JCheckBox("Command & Control Indicator");
        add(chckbxCommandControl);

        JCheckBox chckbxEmergencyIndicator = new JCheckBox("Emergency Indicator");
        add(chckbxEmergencyIndicator);

        JCheckBox chckbxForceTellIndicator = new JCheckBox("Force Tell Indicator");
        add(chckbxForceTellIndicator);
    }
}
