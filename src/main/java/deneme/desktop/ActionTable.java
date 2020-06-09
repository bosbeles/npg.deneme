package deneme.desktop;

import npg.test.GUITester;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.List;

public class ActionTable extends JTable {

    private final ActionTableModel actionTableModel;

    public ActionTable(List<SearchableAction> actionList) {

        actionTableModel = new ActionTableModel(actionList);
        this.setModel(actionTableModel);
        this.setRowHeight(32);
        this.setFont(this.getFont().deriveFont(16f));


        TableRowSorter<ActionTableModel> sorter
                = new TableRowSorter<ActionTableModel>(actionTableModel);
        setRowSorter(sorter);

        getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


    }

    private void newFilter(String filterText) {
        RowFilter<ActionTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = new RowFilter<ActionTableModel, Object>() {
                @Override
                public boolean include(Entry<? extends ActionTableModel, ?> entry) {

                    return false;
                }
            };
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        TableRowSorter<ActionTableModel> sorter = (TableRowSorter<ActionTableModel>) this.getRowSorter();
        sorter.setRowFilter(rf);
    }


    private static class ActionTableModel extends AbstractTableModel {

        private List<SearchableAction> actionList;

        public ActionTableModel(List<SearchableAction> actionList) {
            this.actionList = actionList;
        }

        @Override
        public String getColumnName(int column) {
            return "Action";
        }

        @Override
        public int getRowCount() {
            return actionList.size();
        }

        @Override
        public int getColumnCount() {
            return 1;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            SearchableAction searchableAction = actionList.get(rowIndex);
            return searchableAction;
        }
    }


    public static void main(String[] args) {
        GUITester.test(ActionTable::test);
    }

    private static JComponent test() {
        JPanel panel = new JPanel(new BorderLayout());

        List<SearchableAction> searchableActions = SearchableAction.getSearchableActions();
        ActionTable table = new ActionTable(searchableActions);

        JScrollPane pane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        panel.add(pane);

        JTextField textField = new JTextField();
        //Whenever filterText changes, invoke newFilter.
        textField.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        table.newFilter(textField.getText());
                    }

                    public void insertUpdate(DocumentEvent e) {
                        table.newFilter(textField.getText());
                    }

                    public void removeUpdate(DocumentEvent e) {
                        table.newFilter(textField.getText());
                    }
                });
        panel.add(textField, BorderLayout.NORTH);
        return panel;
    }

}
