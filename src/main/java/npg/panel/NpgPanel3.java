package npg.panel;

import npg.model.*;
import npg.old.NpgPanel;
import npg.test.GUITester;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static npg.panel.GUIUtil.*;

public class NpgPanel3 extends JPanel implements NpgDefinition.NpgDefinitionListener {
    private final NpgDefinition definitionPanel;
    private JTable npgTable;
    private JTable selectedNpgTable;
    private LabelSublabelTableModel labelSublabelModel;
    private NpgRowWithFilterTableModel selectedNpgTableModel;

    public NpgPanel3() {
        this(new NpgDefinition());
    }

    /**
     * Create the panel.
     */
    public NpgPanel3(NpgDefinition definitionPanel) {
        this.definitionPanel = definitionPanel;
        this.definitionPanel.addDefinitionListener(this);

        int y = 0;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.50, 0.75, Double.MIN_VALUE};
        setLayout(gridBagLayout);


        JPanel viewPanel = new JPanel();
        JRadioButton npgViewButton = new JRadioButton("Mesaj Grubuna Göre");
        JRadioButton labelSublabelViewButton = new JRadioButton("Mesaja Göre");
        ButtonGroup group = new ButtonGroup();
        group.add(npgViewButton);
        group.add(labelSublabelViewButton);

        viewPanel.add(npgViewButton);
        viewPanel.add(labelSublabelViewButton);


        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = y;
        add(viewPanel, gbc);


        GridBagLayout messageGroupPanelLayout = new GridBagLayout();
        messageGroupPanelLayout.columnWidths = new int[]{0, 0, 0, 20, 0};
        messageGroupPanelLayout.rowHeights = new int[]{0, 0};
        messageGroupPanelLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        messageGroupPanelLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};

        JPanel messageGroupPanel = new JPanel(messageGroupPanelLayout);
        messageGroupPanel.setPreferredSize(new Dimension(700, 300));
        messageGroupPanel.setBorder(new TitledBorder("Mesaj Grubuna Göre Filtreleme"));



        GridBagConstraints gbc_messageGroupPanel = new GridBagConstraints();
        gbc_messageGroupPanel.insets = new Insets(0, 0, 5, 0);
        gbc_messageGroupPanel.fill = GridBagConstraints.BOTH;
        gbc_messageGroupPanel.gridx = 0;
        gbc_messageGroupPanel.gridy = ++y;
        gbc_messageGroupPanel.gridwidth = 2;
        add(messageGroupPanel, gbc_messageGroupPanel);


        GridBagConstraints gbc_npgScroll = new GridBagConstraints();
        gbc_npgScroll.insets = new Insets(5, 5, 5, 5);
        gbc_npgScroll.fill = GridBagConstraints.BOTH;
        gbc_npgScroll.gridx = 0;
        gbc_npgScroll.gridy = 0;


        JScrollPane npgScroll = new JScrollPane();


        messageGroupPanel.add(npgScroll, gbc_npgScroll);

        npgTable = new JTable();

        npgTable.setRowHeight(26);
        npgTable.setFillsViewportHeight(true);
        npgTable.setModel(new NpgRowTableModel(definitionPanel.getNpgRowList()));
        npgScroll.setViewportView(npgTable);

        JPanel buttonPanel = new JPanel();
        GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
        gbc_buttonPanel.insets = new Insets(0, 0, 5, 5);
        gbc_buttonPanel.gridx = 1;
        gbc_buttonPanel.gridy = 0;
        messageGroupPanel.add(buttonPanel, gbc_buttonPanel);
        buttonPanel.setLayout(new GridLayout(0, 1, 0, 0));

        JButton rightButton = new JButton(getIcon("right.png"));
        buttonPanel.add(rightButton);

        JButton leftButton = new JButton(getIcon("left.png"));
        buttonPanel.add(leftButton);

        JButton rightDoubleButton = new JButton(getIcon("right-double.png"));
        buttonPanel.add(rightDoubleButton);

        JButton leftDoubleButton = new JButton(getIcon("left-double.png"));
        buttonPanel.add(leftDoubleButton);

        JScrollPane selectedNpgScroll = new JScrollPane();
        GridBagConstraints gbc_selectedNpgScroll = new GridBagConstraints();
        gbc_selectedNpgScroll.insets = new Insets(0, 0, 5, 5);
        gbc_selectedNpgScroll.fill = GridBagConstraints.BOTH;
        gbc_selectedNpgScroll.gridx = 2;
        gbc_selectedNpgScroll.gridy = 0;
        messageGroupPanel.add(selectedNpgScroll, gbc_selectedNpgScroll);

        selectedNpgTable = new JTable();
        ((JComponent) selectedNpgTable.getDefaultRenderer(Boolean.class)).setOpaque(true);
        selectedNpgTable.setRowHeight(26);
        selectedNpgTableModel = new NpgRowWithFilterTableModel();
        selectedNpgTable.setModel(selectedNpgTableModel);
        selectedNpgTable.setFillsViewportHeight(true);
        selectedNpgScroll.setViewportView(selectedNpgTable);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.gridx = 3;
        gbc_panel.gridy = 0;
        messageGroupPanel.add(panel, gbc_panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton upButton = new JButton(getIcon("up.png"));
        upButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        panel.add(upButton);

        JButton downButton = new JButton(getIcon("down.png"));
        downButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(downButton);


        LabelSublabelPanel labelSublabelPanel = new LabelSublabelPanel();


        labelSublabelModel = (LabelSublabelTableModel) labelSublabelPanel.getTable().getModel();

        List<MessageRow> messageRowList = definitionPanel.getMessageRowList();
        labelSublabelModel.setData(messageRowList);

        GridBagConstraints gbc_labelSublabel = new GridBagConstraints();
        gbc_labelSublabel.gridx = 0;
        gbc_labelSublabel.gridy = ++y;
        gbc_labelSublabel.gridwidth = 1;
        gbc_labelSublabel.weightx = 1.0;
        gbc_labelSublabel.fill = GridBagConstraints.BOTH;


        add(labelSublabelPanel, gbc_labelSublabel);

        FilterOverridenPanel filterOverridenPanel = new FilterOverridenPanel();
        GridBagConstraints gbc_filterOverridenPanel = new GridBagConstraints();
        gbc_filterOverridenPanel.gridx = 1;
        gbc_filterOverridenPanel.gridy = y;
        gbc_filterOverridenPanel.gridwidth = 1;
        gbc_filterOverridenPanel.weightx = 1.0;
        gbc_filterOverridenPanel.fill = GridBagConstraints.BOTH;

        add(filterOverridenPanel, gbc_filterOverridenPanel);


        TableModelListener selectedNpgTableModelListener = e -> {
            int rowCount = selectedNpgTableModel.getRowCount();
            if (rowCount > 0) {
                NpgRow firstRow = (NpgRow) selectedNpgTable.getValueAt(0, -1);
                labelSublabelPanel.setAllTo(firstRow.isAllowed());
            }
            for (int i = 0; i < rowCount; i++) {
                NpgRow row = (NpgRow) selectedNpgTable.getValueAt(i, -1);
                Collection<JMessage> messageList = row.getMessageList();
                int labelRowCount = labelSublabelModel.getRowCount();
                for (int j = 0; j < labelRowCount; j++) {
                    Object label = labelSublabelModel.getValueAt(j, 0);
                    if (messageList.contains(label)) {
                        labelSublabelModel.setValueAt(row.isFiltered(), j, 2);
                    }
                }
            }
        };
        selectedNpgTableModel.addTableModelListener(selectedNpgTableModelListener);

        rightButton.addActionListener(e -> transfer(npgTable, selectedNpgTable));
        leftButton.addActionListener(e -> transfer(selectedNpgTable, npgTable));
        upButton.addActionListener(e -> order(true));
        downButton.addActionListener(e -> order(false));
        rightDoubleButton.addActionListener(e -> {
            if (npgTable.getRowCount() > 0) {
                npgTable.setRowSelectionInterval(0, npgTable.getRowCount() - 1);
                transfer(npgTable, selectedNpgTable);
            }
        });
        leftDoubleButton.addActionListener(e -> {
            if (selectedNpgTable.getRowCount() > 0) {
                selectedNpgTable.setRowSelectionInterval(0, selectedNpgTable.getRowCount() - 1);
                transfer(selectedNpgTable, npgTable);
            }
        });

        ItemListener itemListener = e -> {
            boolean flag = npgViewButton.isSelected();
            labelSublabelPanel.activate(!flag);

            enableComponents(panel, flag);
            enableComponents(buttonPanel, flag);
            selectedNpgTableModel.setEditable(flag);
            selectedNpgTable.setEnabled(flag);
            npgTable.setEnabled(flag);

            messageGroupPanel.setEnabled(flag);
            if(flag) {
                selectedNpgTableModelListener.tableChanged(null);
            }


        };
        npgViewButton.setSelected(true);
        itemListener.itemStateChanged(null);
        npgViewButton.addItemListener(itemListener);
    }

    public static void main(String[] args) {
        GUITester.test(() -> new NpgPanel3());
    }

    private void order(boolean up) {
        int[] selectedRows = selectedNpgTable.getSelectedRows();
        List<NpgRow> selecteds = new ArrayList<>();
        for (int i = 0; i < selectedRows.length; i++) {
            int index = selectedRows[i];
            NpgRow row = (NpgRow) selectedNpgTable.getValueAt(index, -1);
            selecteds.add(row);
        }

        if (selectedRows.length > 0) {
            NpgRowWithFilterTableModel model = (NpgRowWithFilterTableModel) selectedNpgTable.getModel();
            model.shift(selecteds, up);
            selectTheSelecteds(selectedNpgTable, selecteds);
        }
    }

    private void transfer(JTable from, JTable to) {
        int[] selectedRows = from.getSelectedRows();
        List<NpgRow> selecteds = new ArrayList<>();
        for (int i = 0; i < selectedRows.length; i++) {
            int index = selectedRows[i];
            NpgRow row = (NpgRow) from.getValueAt(index, -1);
            selecteds.add(row);
        }
        NpgRowTableModel model = (NpgRowTableModel) from.getModel();
        model.removeNpgRows(selecteds);

        boolean nonSelected = true;
        for (int i = 0; i < selectedRows.length; i++) {
            int index = selectedRows[i];
            if (index >= 0 && index < from.getRowCount()) {
                from.addRowSelectionInterval(index, index);
                nonSelected = false;
            }
        }
        if (nonSelected && selectedRows.length > 0 && from.getRowCount() > 0) {
            from.addRowSelectionInterval(from.getRowCount() - 1, from.getRowCount() - 1);
        }

        NpgRowTableModel selectedModel = (NpgRowTableModel) to.getModel();
        selectedModel.addNpgRows(selecteds);

        selectTheSelecteds(to, selecteds);
    }

    private void selectTheSelecteds(JTable to, List<NpgRow> selecteds) {

        int rowCount = to.getRowCount();

        if (!selecteds.isEmpty()) {
            to.clearSelection();
        }

        for (int i = 0; i < rowCount; i++) {
            Object valueAt = to.getValueAt(i, -1);
            if (selecteds.contains(valueAt)) {
                to.addRowSelectionInterval(i, i);
            }
        }
    }


    @Override
    public void definitionChanged() {
        List<MessageRow> messageRowList = definitionPanel.getMessageRowList();
        labelSublabelModel.setData(messageRowList);
        npgTable.setModel(new NpgRowTableModel(definitionPanel.getNpgRowList()));
        selectedNpgTableModel.setData(new ArrayList<>());
    }
}
