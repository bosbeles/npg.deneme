package npg.panel;

import npg.model.*;
import npg.test.GUITester;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NpgDefinition extends JPanel {

    private List<MessageRow> messageRowList;
    private List<NpgDefinitionListener> definitionListeners = new ArrayList<>();
    private JTable table;

    public NpgDefinition() {

        setLayout(new BorderLayout());
        initiliazeMesssageRows();


        JScrollPane scrollPane = new JScrollPane();
        add(scrollPane);

        table = new JTable();
        table.setRowHeight(24);
        table.setFillsViewportHeight(true);

        table.setDefaultRenderer(List.class, new ListClassRenderer());
        table.setDefaultEditor(List.class, new ListClassEditor());

        table.setModel(new NpgDefinitionTableModel(messageRowList));
        table.getModel().addTableModelListener(e -> onDefinitionChanged());


        scrollPane.setViewportView(table);

    }

    public static void main(String[] args) {
        GUITester.test(() -> new NpgDefinition());
    }

    private void initiliazeMesssageRows() {
        messageRowList = new ArrayList<>();
        String[][] arr = {{"J2.0", "7 6"}, {"J2.2", "6"}, {"J2.3", "6"}, {"J2.4", "6"}, {"J2.5", "6"},
                {"J2.6", "6"}, {"J3.0", "7"}, {"J3.1", "7"}, {"J3.2", "7"}, {"J3.2", "7"},
                {"J3.3", "7"}, {"J3.4", "7"}, {"J3.5", "7"}, {"J3.6", "7"}, {"J3.7", "7"},
                {"J5.4", "7"}, {"J6.0", "7"}, {"J7.0", "7"}, {"J7.1", "7"}, {"J7.2", "7"},
                {"J7.3", "7"}, {"J7.4", "7"}, {"J7.5", "7"}, {"J7.7", "7"}, {"J9.0", "8"},
                {"J9.1", "21"}, {"J10.2", "8"}, {"J10.3", "8"}, {"J10.5", "8"}, {"J10.6", "8"},
                {"J12.0", "9"}, {"J12.1", "9"}, {"J12.2", "9"}, {"J12.3", "9"}, {"J12.4", "9"},
                {"J12.5", "9"}, {"J12.6", "9"}, {"J12.7", "9"}, {"J13.0", "6"}, {"J13.2", "6"},
                {"J13.3", "6"}, {"J13.5", "6"}, {"J14.0", "10"}, {"J14.2", "10"}, {"J15.0", "7"},
                {"J17.0", "9"}, {"J28.2", null}, {"J31.0", "4"}, {"J31.1", "4"}, {"J31.7", null}};
        for (String[] strings : arr) {
            MessageRow row = new MessageRow(strings[0], strings[1]);
            messageRowList.add(row);
        }

    }

    protected void onDefinitionChanged() {
        for (NpgDefinitionListener npgDefinitionListener : definitionListeners) {
            npgDefinitionListener.definitionChanged();
        }
    }

    public void addDefinitionListener(NpgDefinitionListener listener) {
        definitionListeners.add(listener);
    }

    public List<NpgRow> getNpgRowList() {
        Map<String, NpgRow> npgRowMap = new HashMap<>();
        messageRowList.forEach(messageRow -> {
            if (messageRow.getNpgList().isEmpty()) {
                npgRowMap.computeIfAbsent("", k -> new NpgRow(k)).getMessageList().add(messageRow.getRow());
            }
            messageRow.getNpgList().forEach(npgString -> {
                npgRowMap.computeIfAbsent(npgString, k -> new NpgRow(k)).getMessageList().add(messageRow.getRow());
            });
        });
        List<NpgRow> npgRows = new ArrayList<>(npgRowMap.values());
        npgRows.sort(null);
        return npgRows;
    }

    public List<MessageRow> getMessageRowList() {
        return new ArrayList<>(this.messageRowList);
    }

    public static interface NpgDefinitionListener {
        void definitionChanged();
    }


}
