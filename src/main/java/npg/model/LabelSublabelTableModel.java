package npg.model;

import javax.swing.table.DefaultTableModel;
import java.util.Comparator;
import java.util.List;

public class LabelSublabelTableModel extends DefaultTableModel {

    public static final Comparator<String> COMPARATOR = new Comparator<String>() {

        @Override
        public int compare(String o1, String o2) {
            try {
                int i1 = Integer.parseInt(o1);
                int i2 = Integer.parseInt(o2);

                return Integer.compare(i1, i2);
            } catch (Exception e) {

            }

            return o1.compareTo(o2);
        }

    };
    private boolean editable = true;

    public LabelSublabelTableModel(List<MessageRow> rowList) {
        setDataOnly(rowList);
    }

    public void setEditable(boolean flag) {
        this.editable = flag;
    }

    public void setData(List<MessageRow> rowList) {
        setDataOnly(rowList);
        fireTableDataChanged();
    }

    private void setDataOnly(List<MessageRow> rowList) {
        Object[][] dataVector = new Object[rowList.size()][3];
        for (int i = 0; i < dataVector.length; i++) {
            MessageRow messageRow = rowList.get(i);
            Object[] objects = dataVector[i];
            objects[0] = messageRow.getRow();
            objects[1] = messageRow.getNpgList();
            objects[2] = messageRow.isFiltered();
        }

        setDataVector(dataVector, new Object[]{"Mesaj", "Mesaj Grupları", "Filtreli"});
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return editable &&  column == 2;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return JMessage.class;
            case 1:
                return List.class;
            case 2:
                return Boolean.class;
            default:
                return Object.class;
        }
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        super.setValueAt(aValue, row, column);
    }
}
