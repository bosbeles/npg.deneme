package npg.panel;

import npg.model.LabelSublabelTableModel;
import npg.model.ListClassEditor;
import npg.model.ListClassRenderer;
import npg.model.MessageRow;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LabelSublabelPanel extends JPanel {


    private JTable table;
    private GlasspaneWrapper wrapper;
    private JPanel backPanel;

    public LabelSublabelPanel() {
        initialize();
    }

    public JTable getTable() {
        return table;
    }

    private void initialize() {
        setLayout(new BorderLayout());

        backPanel = new JPanel();


        backPanel.setPreferredSize(new Dimension(400, 300));
        wrapper = new GlasspaneWrapper(backPanel);
        add(backPanel, BorderLayout.CENTER);

        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridwidth = 6;
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 1;

        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[] { 400, 200, 0 };
        gbl_panel_1.rowHeights = new int[] { 200, 0 };
        gbl_panel_1.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
        gbl_panel_1.rowWeights = new double[] { 1.0, 0.0 };

        backPanel.setLayout(gbl_panel_1);

        JScrollPane labelSublabelScroll = new JScrollPane();
        labelSublabelScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
        gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_3.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_3.gridx = 0;
        gbc_scrollPane_3.gridy = 0;
        gbc_scrollPane_3.weightx = 1.0;
        backPanel.add(labelSublabelScroll, gbc_scrollPane_3);

        table = new JTable();
        table.setRowHeight(24);
        table.setDefaultRenderer(List.class, new ListClassRenderer());
        table.setDefaultEditor(List.class, new ListClassEditor());

        ((JComponent) table.getDefaultRenderer(Boolean.class)).setOpaque(true);
        table.setFillsViewportHeight(true);
        List<MessageRow> rowList = new ArrayList<>();
        LabelSublabelTableModel tableModel = new LabelSublabelTableModel(rowList);
        table.setModel(tableModel);

        labelSublabelScroll.setViewportView(table);


        JPanel filterOverridenPanel = new JPanel();
        filterOverridenPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filter Override Fields", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.gridheight = 2;
        gbc_panel_2.anchor = GridBagConstraints.WEST;
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 1;
        gbc_panel_2.gridy = 0;
        gbc_panel_2.weightx = 1.0;
        filterOverridenPanel.setPreferredSize(new Dimension(200, 200));
        backPanel.add(filterOverridenPanel, gbc_panel_2);
        filterOverridenPanel.setLayout(new BoxLayout(filterOverridenPanel, BoxLayout.Y_AXIS));

        JCheckBox chckbxCommandControl = new JCheckBox("Command & Control Indicator");
        filterOverridenPanel.add(chckbxCommandControl);

        JCheckBox chckbxEmergencyIndicator = new JCheckBox("Emergency Indicator");
        filterOverridenPanel.add(chckbxEmergencyIndicator);

        JCheckBox chckbxForceTellIndicator = new JCheckBox("Force Tell Indicator");
        filterOverridenPanel.add(chckbxForceTellIndicator);

        JPanel bulkOpPanel = new JPanel();
        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        gbc_panel_3.anchor = GridBagConstraints.EAST;
        gbc_panel_3.fill = GridBagConstraints.VERTICAL;
        gbc_panel_3.gridx = 0;
        gbc_panel_3.gridy = 1;
        backPanel.add(bulkOpPanel, gbc_panel_3);

        JButton btnHepsi = new JButton("Hepsi");
        bulkOpPanel.add(btnHepsi);

        JButton btnNone = new JButton("Hi\u00E7biri");
        bulkOpPanel.add(btnNone);

        JButton btnInvert = new JButton("Tersine \u00C7evir");
        bulkOpPanel.add(btnInvert);

        btnHepsi.addActionListener(e -> setAllTo(true));

        btnNone.addActionListener(e -> setAllTo(false));

        btnInvert.addActionListener(e -> {
            int len = tableModel.getRowCount();
            for (int i = 0; i < len; i++) {
                Boolean valueAt = (Boolean) table.getValueAt(i, 2);
                tableModel.setValueAt(!valueAt, i, 2);

            }
        });

    }



    public void activate(boolean activate){

        if(activate) {
            wrapper.activateGlassPane(false);
        }
        else {
            wrapper.activateGlassPane(true);
        }
    }

    public void setAllTo(boolean flag) {
        LabelSublabelTableModel tableModel = (LabelSublabelTableModel) table.getModel();
        int len = tableModel.getRowCount();
        for (int i = 0; i < len; i++) {
            tableModel.setValueAt(flag, i, 2);

        }
    }
}
