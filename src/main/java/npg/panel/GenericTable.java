package npg.panel;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

public class GenericTable extends JTable {

    private Set<Integer> columnIndexes = new HashSet<>();


    public GenericTable() {
        setRowHeight(26);
        setFillsViewportHeight(true);
    }

    public GenericTable(int ... index) {
        this();
        setTooltipModelIndex(index);
    }

    public void setTooltipModelIndex(int... index) {
        for (int i : index) {
            columnIndexes.add(i);
        }
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        int row = rowAtPoint(e.getPoint());
        int column = columnAtPoint(e.getPoint());
        int index = columnModel.getColumnIndexAtX(e.getPoint().x);

        int realIndex =
                columnModel.getColumn(index).getModelIndex();
        Object value = null;
        if(columnIndexes.contains(realIndex) && row >= 0) {
            value = getValueAt(row, column);
        }
        return value == null ? null : value.toString();
    }
}
