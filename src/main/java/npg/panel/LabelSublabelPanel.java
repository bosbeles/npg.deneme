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

    private JPanel backPanel;
    private JPanel bulkOpPanel;
    private LabelSublabelTableModel tableModel;
    private JPanel labelSublabelScrollPanel;

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
        //wrapper = new GlasspaneWrapper(backPanel);
        add(backPanel, BorderLayout.CENTER);

        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridwidth = 6;
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 1;

        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{400, 0};
        gbl_panel_1.rowHeights = new int[]{200, 0};
        gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{1.0, 0.0};

        backPanel.setLayout(gbl_panel_1);

        JScrollPane labelSublabelScroll = new JScrollPane();


        labelSublabelScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
        gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_3.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane_3.gridx = 0;
        gbc_scrollPane_3.gridy = 0;
        gbc_scrollPane_3.weightx = 1.0;
        labelSublabelScrollPanel = new JPanel(new BorderLayout());
        labelSublabelScrollPanel.setBorder(new TitledBorder("Etiket/Alt Etiket"));
        JPanel scrollpaneWrapperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc_wrapper = new GridBagConstraints();
        gbc_wrapper.weightx = 1.0;
        gbc_wrapper.weighty = 1.0;
        gbc_wrapper.fill = GridBagConstraints.BOTH;
        gbc_wrapper.insets = new Insets(5, 5, 5, 5);
        scrollpaneWrapperPanel.add(labelSublabelScroll, gbc_wrapper);
        labelSublabelScrollPanel.add(scrollpaneWrapperPanel);
        backPanel.add(labelSublabelScrollPanel, gbc_scrollPane_3);

        table = new JTable();
        table.setRowHeight(24);
        table.setDefaultRenderer(List.class, new ListClassRenderer());
        table.setDefaultEditor(List.class, new ListClassEditor());

        ((JComponent) table.getDefaultRenderer(Boolean.class)).setOpaque(true);
        table.setFillsViewportHeight(true);
        List<MessageRow> rowList = new ArrayList<>();
        tableModel = new LabelSublabelTableModel(rowList);
        table.setModel(tableModel);

        labelSublabelScroll.setViewportView(table);


        bulkOpPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        labelSublabelScrollPanel.add(bulkOpPanel, BorderLayout.SOUTH);

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


    public void activate(boolean activate) {
        GUIUtil.enableComponents(bulkOpPanel, activate);
        tableModel.setEditable(activate);
        labelSublabelScrollPanel.setEnabled(activate);

    }


    public void setAllTo(boolean flag) {
        LabelSublabelTableModel tableModel = (LabelSublabelTableModel) table.getModel();
        int len = tableModel.getRowCount();
        for (int i = 0; i < len; i++) {
            tableModel.setValueAt(flag, i, 2);

        }
    }
}
