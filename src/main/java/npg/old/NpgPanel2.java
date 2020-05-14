package npg.old;

import npg.test.GUITester;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NpgPanel2 extends JPanel implements PanelListener {
    private JTable table;

    private NpgDefinition npgDefPanel;

    private JDialog dialog;

    private Comparator<String> comp;

    private WiderDropDownCombo<String> npgCombobox;

    /**
     * Create the panel.
     */
    public NpgPanel2(NpgDefinition npgDefPanel) {
        this.npgDefPanel = npgDefPanel;
        npgDefPanel.addPanelListener(this);
        Map<String, List<Integer>> npgMap = npgDefPanel.getNpgMap();
        Map<Integer, List<String>> npgMessageMap = npgDefPanel.getNpgMessageMap();
        comp = (s1, s2) -> {
            String[] split = s1.substring(1).split("\\.");
            Integer firstPart = Integer.parseInt(split[0]);
            Integer secondPart = Integer.parseInt(split[1]);

            String[] split2 = s2.substring(1).split("\\.");
            Integer firstPart2 = Integer.parseInt(split2[0]);
            Integer secondPart2 = Integer.parseInt(split2[1]);

            int result = firstPart.compareTo(firstPart2);
            if (result == 0) {
                result = secondPart.compareTo(secondPart2);
            }
            return result;
        };

        List<String> sorted = new ArrayList<>(npgMap.keySet());
        sorted.sort(comp);

        GridBagLayout gridBagLayout = new GridBagLayout();

        gridBagLayout.columnWidths = new int[]{400, 0};
        gridBagLayout.rowHeights = new int[]{200, 0, 300, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};

        setLayout(gridBagLayout);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filter Override Fields",
                TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.gridheight = 1;
        gbc_panel_2.anchor = GridBagConstraints.CENTER;
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 0;
        gbc_panel_2.gridy = 0;
        gbc_panel_2.weightx = 1.0;
        panel_2.setPreferredSize(new Dimension(200, 200));
        add(panel_2, gbc_panel_2);
        panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

        JCheckBox chckbxCommandControl = new JCheckBox("Command & Control Indicator");
        panel_2.add(chckbxCommandControl);

        JCheckBox chckbxEmergencyIndicator = new JCheckBox("Emergency Indicator");
        panel_2.add(chckbxEmergencyIndicator);

        JCheckBox chckbxForceTellIndicator = new JCheckBox("Force Tell Indicator");
        panel_2.add(chckbxForceTellIndicator);

        JPanel npgPanel = new JPanel();
        npgPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        npgCombobox = new WiderDropDownCombo<String>();

        npgCombobox.setPreferredSize(new Dimension(180, 24));
        // npgCombobox.setPrototypeDisplayValue("xxxxxxxxxxxxxxxxxxxxxxxxx");

        updateCombobox();

        npgCombobox.setWide(true);

        JButton allowBtn = new JButton("İzin Ver");
        JButton filterBtn = new JButton("Filtrele");

        npgPanel.add(new JLabel("Mesaj Grubu"));
        npgPanel.add(npgCombobox);
        npgPanel.add(allowBtn);
        npgPanel.add(filterBtn);

        GridBagConstraints gbl_npgPanel = new GridBagConstraints();
        gbl_npgPanel.fill = GridBagConstraints.VERTICAL;
        gbl_npgPanel.anchor = GridBagConstraints.WEST;
        gbl_npgPanel.weightx = 1.0;
        gbl_npgPanel.gridx = 0;
        gbl_npgPanel.gridy = 1;

        add(npgPanel, gbl_npgPanel);

        JPanel panel_1 = new JPanel();

        panel_1.setPreferredSize(new Dimension(400, 200));
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 2;
        add(panel_1, gbc_panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{400, 0, 0};
        gbl_panel_1.rowHeights = new int[]{200, 0};
        gbl_panel_1.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{1.0, 0.0};

        panel_1.setLayout(gbl_panel_1);

        JScrollPane scrollPane_3 = new JScrollPane();
        scrollPane_3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
        gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_3.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_3.gridx = 0;
        gbc_scrollPane_3.gridy = 0;
        gbc_scrollPane_3.weightx = 1.0;
        panel_1.add(scrollPane_3, gbc_scrollPane_3);

        table = new JTable();
        table.setRowHeight(24);
        ((JComponent) table.getDefaultRenderer(Boolean.class)).setOpaque(true);
        table.setFillsViewportHeight(true);

        DefaultTableModel tableModel = new DefaultTableModel() {
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) {
                    return Boolean.class;
                } else
                    return super.getColumnClass(columnIndex);
            }
        };

        Object[][] data = new Object[npgMap.size()][3];

        for (int i = 0; i < data.length; i++) {
            String key = sorted.get(i);
            data[i][0] = key;
            ArrayList<Integer> listOfNpg = new ArrayList<>(npgMap.getOrDefault(key, Collections.emptyList()));
            data[i][1] = listOfNpg.stream().map(String::valueOf).collect(Collectors.joining(" "));
            data[i][2] = false;
        }
        tableModel.setDataVector(data, new Object[]{"Mesaj", "Mesaj Grubu", "Filtreli"});

        table.setModel(tableModel);

        scrollPane_3.setViewportView(table);

        JPanel panel_3 = new JPanel();
        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        gbc_panel_3.anchor = GridBagConstraints.EAST;
        gbc_panel_3.fill = GridBagConstraints.VERTICAL;
        gbc_panel_3.gridx = 0;
        gbc_panel_3.gridy = 1;
        panel_1.add(panel_3, gbc_panel_3);

        JButton btnHepsi = new JButton("Hepsi");
        panel_3.add(btnHepsi);

        JButton btnNone = new JButton("Hi\u00E7biri");
        panel_3.add(btnNone);

        JButton btnInvert = new JButton("Tersine \u00C7evir");
        panel_3.add(btnInvert);

        btnHepsi.addActionListener(e -> setAllTo(true));

        btnNone.addActionListener(e -> setAllTo(false));

        btnInvert.addActionListener(e -> {
            int len = tableModel.getRowCount();
            for (int i = 0; i < len; i++) {
                Boolean valueAt = (Boolean) table.getValueAt(i, 2);
                tableModel.setValueAt(!valueAt, i, 2);

            }
        });

        allowBtn.addActionListener(e -> {
            int len = tableModel.getRowCount();
            boolean firstTime = true;
            for (int i = 0; i < len; i++) {
                boolean val = (boolean) tableModel.getValueAt(i, 2);
                if (val) {
                    firstTime = false;
                    break;
                }
            }
            if (firstTime) {
                for (int i = 0; i < len; i++) {
                    tableModel.setValueAt(true, i, 2);
                }
            }

            String s = (String) npgCombobox.getSelectedItem();
            String[] labels = s.split(":")[1].trim().split(" ");
            for (String message : labels) {
                for (int i = 0; i < len; i++) {
                    String rowMessage = (String) tableModel.getValueAt(i, 0);
                    if (rowMessage.equals(message)) {
                        tableModel.setValueAt(false, i, 2);
                    }
                }

            }

        });

        filterBtn.addActionListener(e -> {
            int len = tableModel.getRowCount();
            boolean firstTime = true;
            for (int i = 0; i < len; i++) {
                boolean val = (boolean) tableModel.getValueAt(i, 2);
                if (!val) {
                    firstTime = false;
                    break;
                }
            }
            if (firstTime) {
                for (int i = 0; i < len; i++) {
                    tableModel.setValueAt(false, i, 2);
                }
            }

            String s = (String) npgCombobox.getSelectedItem();
            String[] labels = s.split(":")[1].trim().split(" ");
            for (String message : labels) {
                for (int i = 0; i < len; i++) {
                    String rowMessage = (String) tableModel.getValueAt(i, 0);
                    if (rowMessage.equals(message)) {
                        tableModel.setValueAt(true, i, 2);
                    }
                }

            }

        });

    }

    private void updateCombobox() {
        Map<String, List<Integer>> npgMap = npgDefPanel.getNpgMap();
        Map<Integer, List<String>> npgMessageMap = npgDefPanel.getNpgMessageMap();

        Stream<Integer> sorted2 = npgMap.values().stream().flatMap(List::stream).distinct().sorted();
        String[] collect = sorted2.map(i -> {
            String npgs = npgMessageMap.getOrDefault(i, Collections.emptyList()).stream().sorted(comp)
                    .map(String::valueOf).collect(Collectors.joining(" "));

            return Integer.toString(i) + " : " + npgs;
        }).toArray(String[]::new);
        npgCombobox.setModel(new DefaultComboBoxModel<String>(collect));
    }

    @Override
    public void updateNpgColumn() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        int len = tableModel.getRowCount();
        Map<String, List<Integer>> npgMap = npgDefPanel.getNpgMap();
        for (int i = 0; i < len; i++) {
            String key = (String) tableModel.getValueAt(i, 0);
            List<Integer> intList = npgMap.getOrDefault(key, Collections.emptyList());
            tableModel.setValueAt(intList.stream().map(String::valueOf).collect(Collectors.joining(" ")), i, 1);

        }

        updateCombobox();
    }

    private void setAllTo(boolean flag) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        int len = tableModel.getRowCount();
        for (int i = 0; i < len; i++) {
            tableModel.setValueAt(flag, i, 2);

        }
    }

    private void transfer(List<Integer> items, JList<Integer> from, JList<Integer> to) {
        items.stream().forEach(element -> {
            ((SortedListModel) to.getModel()).addElement(element);
            ((SortedListModel) from.getModel()).removeElement(element);

        });

    }

    private void transferSelected(JList<Integer> from, JList<Integer> to) {
        int[] selectedIndices = from.getSelectedIndices();
        List<Integer> selectedValuesList = from.getSelectedValuesList();
        transfer(selectedValuesList, from, to);
        if (selectedIndices.length > 0) {
            int size = from.getModel().getSize();
            if (selectedIndices[0] < size) {
                from.setSelectedIndex(selectedIndices[0]);
            } else if (size > 0) {
                from.setSelectedIndex(size - 1);
            }

        }
        int toSize = to.getModel().getSize();
        int[] newSelectedIndices = new int[selectedIndices.length];
        int c = 0;
        for (int i = 0; i < toSize; i++) {
            if (selectedValuesList.contains(to.getModel().getElementAt(i))) {
                newSelectedIndices[c++] = i;
            }

        }
        to.setSelectedIndices(newSelectedIndices);

    }

    private void transferAll(JList<Integer> from, JList<Integer> to) {
        transfer(((SortedListModel) from.getModel()).getAll(), from, to);
    }

    public static void main(String[] args) {
        GUITester.test(() -> new NpgPanel2(new NpgDefinition()), args.length > 0 ? args[0] : "Nimbus");

    }

}
