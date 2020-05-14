package npg.old;

import npg.test.GUITester;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.util.stream.Collectors;


public class NpgDefinition extends JPanel {
    private JTable table;

    private List<PanelListener> panelListeners = new ArrayList<>();

    /**
     * Create the panel.
     */
    public NpgDefinition() {

        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane);

        table = new JTable();
        table.setRowHeight(24);
        table.setFillsViewportHeight(true);
        table.setModel(new DefaultTableModel(
                new Object[][]{{"J2.0", "7"}, {"J2.2", "6"}, {"J2.3", "6"}, {"J2.4", "6"}, {"J2.5", "6"},
                        {"J2.6", "6"}, {"J3.0", "7"}, {"J3.1", "7"}, {"J3.2", "7"}, {"J3.2", "7"},
                        {"J3.3", "7"}, {"J3.4", "7"}, {"J3.5", "7"}, {"J3.6", "7"}, {"J3.7", "7"},
                        {"J5.4", "7"}, {"J6.0", "7"}, {"J7.0", "7"}, {"J7.1", "7"}, {"J7.2", "7"},
                        {"J7.3", "7"}, {"J7.4", "7"}, {"J7.5", "7"}, {"J7.7", "7"}, {"J9.0", "8"},
                        {"J9.1", "21"}, {"J10.2", "8"}, {"J10.3", "8"}, {"J10.5", "8"}, {"J10.6", "8"},
                        {"J12.0", "9"}, {"J12.1", "9"}, {"J12.2", "9"}, {"J12.3", "9"}, {"J12.4", "9"},
                        {"J12.5", "9"}, {"J12.6", "9"}, {"J12.7", "9"}, {"J13.0", "6"}, {"J13.2", "6"},
                        {"J13.3", "6"}, {"J13.5", "6"}, {"J14.0", "10"}, {"J14.2", "10"}, {"J15.0", "7"},
                        {"J17.0", "9"}, {"J28.2", null}, {"J31.0", "4"}, {"J31.1", "4"}, {"J31.7", null},},
                new String[]{"Mesaj", "Mesaj Grubu"}) {
            @Override
            public void setValueAt(Object aValue, int row, int column) {
                if (column == 1) {
                    String text = (String) aValue;
                    List<Integer> ints = Arrays.stream(text.trim().split("[^A-Za-z0-9_-]+")).map(Integer::parseInt)
                            .collect(Collectors.toList());
                    Collections.sort(ints);
                    String numberString = ints.stream().map(String::valueOf).collect(Collectors.joining(" "));
                    super.setValueAt(numberString, row, column);
                    updateListener();

                } else {
                    super.setValueAt(aValue, row, column);
                }

            }
        });
        scrollPane.setViewportView(table);

    }

    public static void main(String[] args) {
        GUITester.test(() -> new NpgDefinition());

    }

    public void addPanelListener(PanelListener panel) {
        panelListeners.add(panel);
    }

    protected void updateListener() {
        // TODO Auto-generated method stub
        if (panelListeners != null) {
            for (PanelListener panelListener : panelListeners) {
                panelListener.updateNpgColumn();
            }
        }

    }

    public Map<String, List<Integer>> getNpgMap() {
        Map<String, List<Integer>> map = new HashMap<>();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int len = model.getRowCount();
        for (int i = 0; i < len; i++) {
            String text = (String) model.getValueAt(i, 1);
            List<Integer> ints;
            if (text == null) {
                ints = new ArrayList<>();
            } else {
                ints = Arrays.stream(text.trim().split("[^0-9]+")).map(Integer::parseInt)
                        .collect(Collectors.toList());

            }
            map.put((String) model.getValueAt(i, 0), ints);
        }

        return map;
    }

    public Map<Integer, List<String>> getNpgMessageMap() {
        Map<Integer, List<String>> map = new HashMap<>();

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int len = model.getRowCount();
        for (int i = 0; i < len; i++) {
            String text = (String) model.getValueAt(i, 1);
            List<Integer> ints;
            if (text == null) {
                ints = new ArrayList<>();
            } else {
                ints = Arrays.stream(text.trim().split("[^0-9]+")).map(Integer::parseInt)
                        .collect(Collectors.toList());

            }
            String message = (String) model.getValueAt(i, 0);
            ints.forEach(key -> {
                map.computeIfAbsent(key, k -> new ArrayList()).add(message);
            });

        }

        Map<Integer, List<String>> map2 = new HashMap<>();

        map.forEach((k, v) -> map2.put(k, v.stream().distinct().collect(Collectors.toList())));

        return map2;
    }


}
