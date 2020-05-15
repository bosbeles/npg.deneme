package npg.old;

import npg.model.JMessage;
import npg.model.LabelSublabelTableModel;
import npg.model.MessageRow;
import npg.model.NpgRow;
import npg.panel.FilterOverridenPanel;
import npg.panel.LabelSublabelPanel;
import npg.panel.NpgDefinition;
import npg.test.GUITester;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Comparator;
import java.util.List;

public class NpgPanel2 extends JPanel implements npg.panel.NpgDefinition.NpgDefinitionListener {
    private final LabelSublabelPanel panel_1;
    private JTable table;
    private NpgDefinition npgDefPanel;
    private JDialog dialog;
    private Comparator<String> comp;
    private WiderDropDownCombo<NpgRow> npgCombobox;

    /**
     * Create the panel.
     */
    public NpgPanel2(NpgDefinition npgDefPanel) {
        this.npgDefPanel = npgDefPanel;
        npgDefPanel.addDefinitionListener(this);


        GridBagLayout gridBagLayout = new GridBagLayout();

        gridBagLayout.columnWidths = new int[]{400, 0};
        gridBagLayout.rowHeights = new int[]{100, 0, 300, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};

        setLayout(gridBagLayout);

        JPanel panel_2 = new FilterOverridenPanel();
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.gridheight = 1;
        gbc_panel_2.anchor = GridBagConstraints.CENTER;
        gbc_panel_2.fill = GridBagConstraints.BOTH;
        gbc_panel_2.gridx = 0;
        gbc_panel_2.gridy = 0;
        gbc_panel_2.weightx = 1.0;
        add(panel_2, gbc_panel_2);


        JPanel npgPanel = new JPanel();
        npgPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        npgCombobox = new WiderDropDownCombo<NpgRow>();

        npgCombobox.setPreferredSize(new Dimension(180, 24));

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

        panel_1 = new LabelSublabelPanel();
        LabelSublabelTableModel tableModel = (LabelSublabelTableModel) panel_1.getTable().getModel();

        List<MessageRow> messageRowList = npgDefPanel.getMessageRowList();
        tableModel.setData(messageRowList);

        panel_1.setPreferredSize(new Dimension(400, 200));
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 2;
        add(panel_1, gbc_panel_1);


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

            NpgRow row = (NpgRow) npgCombobox.getSelectedItem();
            for (JMessage message : row.getMessageList()) {
                for (int i = 0; i < len; i++) {
                    JMessage rowMessage = (JMessage) tableModel.getValueAt(i, 0);
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

            NpgRow row = (NpgRow) npgCombobox.getSelectedItem();
            for (JMessage message : row.getMessageList()) {
                for (int i = 0; i < len; i++) {
                    JMessage rowMessage = (JMessage) tableModel.getValueAt(i, 0);
                    if (rowMessage.equals(message)) {
                        tableModel.setValueAt(true, i, 2);
                    }
                }

            }

        });

    }

    public static void main(String[] args) {
        GUITester.test(() -> new NpgPanel2(new NpgDefinition()), args.length > 0 ? args[0] : "Nimbus");

    }

    private void updateCombobox() {

        List<NpgRow> npgRowList = npgDefPanel.getNpgRowList();
        /*
        Map<String, List<Integer>> npgMap = npgDefPanel.getNpgMap();
        Map<Integer, List<String>> npgMessageMap = npgDefPanel.getNpgMessageMap();



        Stream<Integer> sorted2 = npgMap.values().stream().flatMap(List::stream).distinct().sorted();
        String[] collect = sorted2.map(i -> {
            String npgs = npgMessageMap.getOrDefault(i, Collections.emptyList()).stream().sorted(comp)
                    .map(String::valueOf).collect(Collectors.joining(" "));

            return Integer.toString(i) + " : " + npgs;
        }).toArray(String[]::new);
        */
        npgCombobox.setModel(new DefaultComboBoxModel<NpgRow>(npgRowList.toArray(new NpgRow[]{})));
    }

    @Override
    public void definitionChanged() {
        LabelSublabelTableModel model = (LabelSublabelTableModel) panel_1.getTable().getModel();
        model.setData(npgDefPanel.getMessageRowList());

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

}
