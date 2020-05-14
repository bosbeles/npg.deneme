package npg.panel;

import npg.model.*;
import npg.test.GUITester;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class NpgPanel3 extends JPanel {
    private JTable npgTable;
    private JTable selectedNpgTable;
    private final NpgDefinition definitionPanel;

    /**
     * Create the panel.
     */
    public NpgPanel3() {
        definitionPanel = new NpgDefinition();

        int y = 0;

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 20, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 300, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
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
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = y;
        add(viewPanel, gbc);

        JScrollPane npgScroll = new JScrollPane();
        GridBagConstraints gbc_npgScroll = new GridBagConstraints();
        gbc_npgScroll.insets = new Insets(0, 0, 5, 5);
        gbc_npgScroll.fill = GridBagConstraints.BOTH;
        gbc_npgScroll.gridx = 0;
        gbc_npgScroll.gridy = ++y;
        add(npgScroll, gbc_npgScroll);

        npgTable = new JTable();

        npgTable.setRowHeight(26);
        npgTable.setFillsViewportHeight(true);
        npgTable.setModel(new NpgRowTableModel(definitionPanel.getNpgRowList()));
        npgScroll.setViewportView(npgTable);

        JPanel buttonPanel = new JPanel();
        GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
        gbc_buttonPanel.insets = new Insets(0, 0, 5, 5);
        gbc_buttonPanel.gridx = 1;
        gbc_buttonPanel.gridy = y;
        add(buttonPanel, gbc_buttonPanel);
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
        gbc_selectedNpgScroll.gridy = y;
        add(selectedNpgScroll, gbc_selectedNpgScroll);

        selectedNpgTable = new JTable();
        ((JComponent) selectedNpgTable.getDefaultRenderer(Boolean.class)).setOpaque(true);
        selectedNpgTable.setRowHeight(26);
        NpgRowWithFilterTableModel selectedNpgTableModel = new NpgRowWithFilterTableModel();
        selectedNpgTable.setModel(selectedNpgTableModel);
        selectedNpgTable.setFillsViewportHeight(true);
        selectedNpgScroll.setViewportView(selectedNpgTable);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.gridx = 3;
        gbc_panel.gridy = y;
        add(panel, gbc_panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton upButton = new JButton(getIcon("up.png"));
        upButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        panel.add(upButton);

        JButton downButton = new JButton(getIcon("down.png"));
        downButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(downButton);


        LabelSublabelPanel labelSublabelPanel = new LabelSublabelPanel();


        LabelSublabelTableModel labelSublabelModel = (LabelSublabelTableModel) labelSublabelPanel.getTable().getModel();

        List<MessageRow> messageRowList = definitionPanel.getMessageRowList();
        labelSublabelModel.setData(messageRowList);

        GridBagConstraints gbc_labelSublabel = new GridBagConstraints();
        gbc_labelSublabel.gridx = 0;
        gbc_labelSublabel.gridy = ++y;
        gbc_labelSublabel.gridwidth = 3;
        gbc_labelSublabel.weightx = 1.0;
        gbc_labelSublabel.fill = GridBagConstraints.BOTH;


        add(labelSublabelPanel, gbc_labelSublabel);

        selectedNpgTableModel.addTableModelListener(e -> {
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
                    if(messageList.contains(label)) {
                        labelSublabelModel.setValueAt(row.isFiltered(), j, 2);
                    }
                }
            }
        });

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

        npgViewButton.addItemListener( e-> {
            boolean flag = npgViewButton.isSelected();
            labelSublabelPanel.activate(!flag);
        });
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

    private ImageIcon getIcon(String path) {
        return new ImageIcon(getClass().getClassLoader().getResource(path));
    }

    public static void main(String[] args) {
        GUITester.test(() -> new NpgPanel3());
    }


    /**
     * Shifts the selected items of an array one unit to front or back. <br>
     * array=[1,2,3,4,5,6] <br>
     * (array, {1,2,4}, true) -> returns: true, array=[2,3,1,5,4,6] <br><br>
     * array=[1,2,3,4,5,6] <br>
     * (array, {0,1,3}, true) -> returns: true, array=[1,2,4,3,5,6] <br><br>
     * array=[1,2,3,4,5,6] <br>
     * (array, {0,1,2}, true) -> returns: false, array=[1,2,3,4,5,6] <br><br>
     *
     * @param array   the array
     * @param indices selected indices
     * @param front   move to front or back.
     * @param <T>     the item type
     * @return true if array has changed, false otherwise.
     */
    public static <T> boolean shiftTo(T[] array, int[] indices, boolean front) {

        return false;
    }

    /**
     * Shifts the selected items of an array <code>n</code> unit to front or back. <br>
     * array=[1,2,3,4,5,6] <br>
     * (array, {1,2,4}, true, 3) -> returns: 2, array=[2,3,5,1,4,6] <br><br>
     *
     * @param array   the array
     * @param indices selected indices
     * @param front   move to front or back.
     * @param n       number of shifts
     * @param <T>     the item type
     * @return number of successful shifts.
     */
    public static <T> int shiftTo(T[] array, int[] indices, boolean front, int n) {

        return 0;
    }

    private static <T> int shiftTo(T[] array, int index, int n) {
        int ret = Math.abs(n);
        if (n < 0) {
            int newIndex = index + n;
            if (newIndex < 0) {
                ret += newIndex;
                newIndex = 0;
            }
            T temp = array[newIndex];
            array[newIndex] = array[index];
            array[index] = temp;

        } else if (n > 0) {
            int newIndex = index + n;
            if (newIndex > array.length - 1) {
                ret += array.length - 1 - newIndex;
                newIndex = array.length - 1;
            }
            T temp = array[newIndex];
            array[newIndex] = array[index];
            array[index] = temp;

        }
        return ret;
    }


}
