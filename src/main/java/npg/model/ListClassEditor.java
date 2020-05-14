package npg.model;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ListClassEditor extends DefaultCellEditor {
    public ListClassEditor() {
        super(new JTextField());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof java.util.List) {
            java.util.List<Object> list = (List) value;

            String representation = list.stream().map(e -> e.toString()).collect(Collectors.joining(" "));
            return super.getTableCellEditorComponent(table, representation, isSelected, row, column);
        }
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    }
}
