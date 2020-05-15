package npg.old;

import npg.panel.NpgPanel3;
import npg.test.GUITester;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static npg.panel.GUIUtil.getIcon;

public class NpgPanel extends JPanel implements PanelListener {
    private JTable table;

    private NpgDefinition npgDefPanel;

    private JDialog dialog;


    /**
     * Create the panel.
     */
    public NpgPanel() {
        npgDefPanel = new NpgDefinition();
        npgDefPanel.addPanelListener(this);



        JPanel npgPanel1 = new JPanel();


        GridBagLayout gridBagLayout = new GridBagLayout();

        gridBagLayout.columnWidths = new int[]{400, 0};
        gridBagLayout.rowHeights = new int[]{300, 300, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.25, 0.75, Double.MIN_VALUE};

        npgPanel1.setLayout(gridBagLayout);

        JPanel panel = new JPanel();
        panel.setBorder(
                new TitledBorder(UIManager.getBorder("TitledBorder.border"), "NPG Kullanarak Filtre Olu\u015Ftur", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, null, null));
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.gridwidth = 6;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        gbc_panel.weighty = 1.0;
        npgPanel1.add(panel, gbc_panel);

        GridBagLayout gbl_panel = new GridBagLayout();

        gbl_panel.columnWidths = new int[]{200, 0, 200, 0, 200, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};

        panel.setLayout(gbl_panel);

        JLabel lblNewLabel = new JLabel("\u0130zin Verilen");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        panel.add(lblNewLabel, gbc_lblNewLabel);

        JButton btnNpg = new JButton("Mesaj Grubu");
        GridBagConstraints gbc_btnNpg = new GridBagConstraints();
        gbc_btnNpg.insets = new Insets(0, 0, 5, 5);
        gbc_btnNpg.gridx = 2;
        gbc_btnNpg.gridy = 0;
        panel.add(btnNpg, gbc_btnNpg);

        JLabel lblIzinVerilmeyen = new JLabel("\u0130zin Verilmeyen");
        GridBagConstraints gbc_lblIzinVerilmeyen = new GridBagConstraints();
        gbc_lblIzinVerilmeyen.insets = new Insets(0, 0, 5, 5);
        gbc_lblIzinVerilmeyen.gridx = 4;
        gbc_lblIzinVerilmeyen.gridy = 0;
        panel.add(lblIzinVerilmeyen, gbc_lblIzinVerilmeyen);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 1;
        panel.add(scrollPane, gbc_scrollPane);

        JScrollPane scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridx = 2;
        gbc_scrollPane_1.gridy = 1;
        panel.add(scrollPane_1, gbc_scrollPane_1);

        JPanel leftPanel = new JPanel();
        GridBagConstraints gbc_leftPanel = new GridBagConstraints();
        gbc_leftPanel.insets = new Insets(0, 0, 5, 5);
        gbc_leftPanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_leftPanel.gridx = 1;
        gbc_leftPanel.gridy = 1;
        panel.add(leftPanel, gbc_leftPanel);
        leftPanel.setLayout(new GridLayout(0, 1, 0, 0));

        JButton npgToLeft = new JButton(getIcon("left.png"));

        npgToLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(npgToLeft);

        JButton leftToNpg = new JButton(getIcon("right.png"));
        leftToNpg.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(leftToNpg);

        JButton npgToLeftAll = new JButton(getIcon("left-double.png"));
        npgToLeftAll.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(npgToLeftAll);

        JButton leftToNpgAll = new JButton(getIcon("right-double.png"));
        leftToNpgAll.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(leftToNpgAll);

        JScrollPane scrollPane_2 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
        gbc_scrollPane_2.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_2.gridx = 4;
        gbc_scrollPane_2.gridy = 1;
        panel.add(scrollPane_2, gbc_scrollPane_2);

        JPanel rightPanel = new JPanel();
        GridBagConstraints gbc_rightPanel = new GridBagConstraints();
        gbc_rightPanel.insets = new Insets(0, 0, 5, 5);
        gbc_rightPanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_rightPanel.gridx = 3;
        gbc_rightPanel.gridy = 1;
        panel.add(rightPanel, gbc_rightPanel);
        rightPanel.setLayout(new GridLayout(0, 1, 0, 0));

        JButton npgToRight = new JButton(getIcon("right.png"));
        npgToRight.setAlignmentX(0.5f);
        rightPanel.add(npgToRight);

        JButton rightToNpg = new JButton(getIcon("left.png"));
        rightToNpg.setAlignmentX(0.5f);
        rightPanel.add(rightToNpg);

        JButton npgToRightAll = new JButton(getIcon("right-double.png"));
        npgToRightAll.setAlignmentX(0.5f);
        rightPanel.add(npgToRightAll);

        JButton rightToNpgAll = new JButton(getIcon("left-double.png"));
        rightToNpgAll.setAlignmentX(0.5f);
        rightPanel.add(rightToNpgAll);

        JList<Integer> allowedList = new JList<>(new SortedListModel());
        scrollPane.setViewportView(allowedList);

        JList<Integer> npgList = NpgListGenerator.createJList();
        scrollPane_1.setViewportView(npgList);

        JList<Integer> notAllowedList = new JList<>(new SortedListModel());
        scrollPane_2.setViewportView(notAllowedList);

        JButton btnAllow = new JButton("\u0130zin Ver");
        GridBagConstraints gbc_btnAllow = new GridBagConstraints();
        gbc_btnAllow.insets = new Insets(0, 0, 5, 5);
        gbc_btnAllow.gridx = 0;
        gbc_btnAllow.gridy = 2;
        panel.add(btnAllow, gbc_btnAllow);

        JButton btnFilter = new JButton("Filtrele");
        GridBagConstraints gbc_btnFilter = new GridBagConstraints();
        gbc_btnFilter.insets = new Insets(0, 0, 5, 5);
        gbc_btnFilter.gridx = 4;
        gbc_btnFilter.gridy = 2;
        panel.add(btnFilter, gbc_btnFilter);

        JPanel panel_1 = new JPanel();

        panel_1.setPreferredSize(new Dimension(400, 200));
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridwidth = 6;
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 1;
        npgPanel1.add(panel_1, gbc_panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{400, 200, 0};
        gbl_panel_1.rowHeights = new int[]{200, 0};
        gbl_panel_1.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
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

        Map<String, List<Integer>> npgMap = npgDefPanel.getNpgMap();
        DefaultTableModel tableModel = new DefaultTableModel() {
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 2) {
                    return Boolean.class;
                } else
                    return super.getColumnClass(columnIndex);
            }
        };

        Object[][] data = new Object[npgMap.size()][3];
        Comparator<String> comp = (s1, s2) -> {
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


//		TableModelListener l = new TableModelListener() {
//
//		    @Override
//		    public void tableChanged(TableModelEvent e) {
//		        if (e.getType() ==  TableModelEvent.UPDATE) {
//		            SwingUtilities.invokeLater(new Runnable() {
//		                public void run() {
//		                    int viewRow = table.convertRowIndexToView(e.getFirstRow());
//		                    table.scrollRectToVisible(table.getCellRect(viewRow, 0, true));    
//		                }
//		            });
//		        }
//		    }
//		};
//		table.getModel().addTableModelListener(l);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Filter Override Fields", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, null));
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.gridheight = 2;
        gbc_panel_2.anchor = GridBagConstraints.WEST;
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 1;
        gbc_panel_2.gridy = 0;
        gbc_panel_2.weightx = 1.0;
        panel_2.setPreferredSize(new Dimension(200, 200));
        panel_1.add(panel_2, gbc_panel_2);
        panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));

        JCheckBox chckbxCommandControl = new JCheckBox("Command & Control Indicator");
        panel_2.add(chckbxCommandControl);

        JCheckBox chckbxEmergencyIndicator = new JCheckBox("Emergency Indicator");
        panel_2.add(chckbxEmergencyIndicator);

        JCheckBox chckbxForceTellIndicator = new JCheckBox("Force Tell Indicator");
        panel_2.add(chckbxForceTellIndicator);

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

        npgToLeft.addActionListener(e -> transferSelected(npgList, allowedList));
        leftToNpg.addActionListener(e -> transferSelected(allowedList, npgList));
        npgToLeftAll.addActionListener(e -> transferAll(npgList, allowedList));
        leftToNpgAll.addActionListener(e -> transferAll(allowedList, npgList));

        npgToRight.addActionListener(e -> transferSelected(npgList, notAllowedList));
        rightToNpg.addActionListener(e -> transferSelected(notAllowedList, npgList));
        npgToRightAll.addActionListener(e -> transferAll(npgList, notAllowedList));
        rightToNpgAll.addActionListener(e -> transferAll(notAllowedList, npgList));

        btnNpg.addActionListener(e -> {
            if (dialog == null) {
                dialog = new JDialog(SwingUtilities.getWindowAncestor(NpgPanel.this));

                dialog.getContentPane().add(npgDefPanel);
                dialog.pack();
                dialog.setLocationRelativeTo(NpgPanel.this);

            }

            dialog.setVisible(true);

        });

        btnAllow.addActionListener(e -> {


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


            Map<String, List<Integer>> map = npgDefPanel.getNpgMap();
            List<Integer> list = ((SortedListModel) allowedList.getModel()).getAll();
            Set<Entry<String, List<Integer>>> entrySet = map.entrySet();
            for (Entry<String, List<Integer>> entry : entrySet) {
                boolean found = false;
                for (Integer npgCandidate : list) {
                    if (entry.getValue().contains(npgCandidate)) {
                        found = true;
                    }
                }

                if (found) {
                    String message = entry.getKey();


                    for (int i = 0; i < len; i++) {
                        String rowMessage = (String) tableModel.getValueAt(i, 0);
                        if (rowMessage.equals(message)) {
                            tableModel.setValueAt(false, i, 2);
                        }
                    }
                }
            }

        });

        btnFilter.addActionListener(e -> {
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


            Map<String, List<Integer>> map = npgDefPanel.getNpgMap();
            List<Integer> list = ((SortedListModel) notAllowedList.getModel()).getAll();
            Set<Entry<String, List<Integer>>> entrySet = map.entrySet();
            for (Entry<String, List<Integer>> entry : entrySet) {
                boolean found = false;
                for (Integer npgCandidate : list) {
                    if (entry.getValue().contains(npgCandidate)) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    String message = entry.getKey();

                    for (int i = 0; i < len; i++) {
                        String rowMessage = (String) tableModel.getValueAt(i, 0);
                        if (rowMessage.equals(message)) {
                            tableModel.setValueAt(true, i, 2);

                        }
                    }
                }
            }

        });

        btnHepsi.addActionListener(e -> setAllTo(true));

        btnNone.addActionListener(e -> setAllTo(false));

        btnInvert.addActionListener(e -> {
            int len = tableModel.getRowCount();
            for (int i = 0; i < len; i++) {
                Boolean valueAt = (Boolean) table.getValueAt(i, 2);
                tableModel.setValueAt(!valueAt, i, 2);

            }
        });

        allowedList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() > 1) {
                    // Double-click detected
                    transferSelected(allowedList, npgList);
                }
            }
        });

        notAllowedList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList) evt.getSource();
                if (evt.getClickCount() > 1) {
                    // Double-click detected
                    transferSelected(notAllowedList, npgList);
                }
            }
        });

        npg.panel.NpgDefinition npgDefinition = new npg.panel.NpgDefinition();
        NpgPanel2 npgPanel2 = new NpgPanel2(npgDefinition);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("0", npgDefinition);
        tabbedPane.addTab("1", npgPanel1);
        tabbedPane.addTab("2", npgPanel2);
        tabbedPane.addTab("3", new NpgPanel3(npgDefinition));

        setLayout(new BorderLayout());
        add(tabbedPane);

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
        GUITester.test(() -> new NpgPanel(), args.length > 0 ? args[0] : "Nimbus");

    }

}
