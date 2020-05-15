package npg.model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class NpgRowTableModel extends AbstractTableModel {

    protected List<NpgRow> npgRowList;

    public NpgRowTableModel(List<NpgRow> npgRowList) {
        this.npgRowList = npgRowList;
    }

    public void setData(List<NpgRow> npgRowList) {
        this.npgRowList = npgRowList;
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Mesaj Grubu";
            case 1:
                return "Mesajlar";

            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return String.class;
            case 1:
                return List.class;

            default:
                return Object.class;
        }
    }

    @Override
    public int getRowCount() {
        return npgRowList.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NpgRow npgRow = npgRowList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return npgRow.getNpg();
            case 1:
                return npgRow.messageList;

            default:
                return npgRow;
        }

    }

    public void addNpgRow(NpgRow row) {
        addNpgRow(row, npgRowList.size());
    }

    public void addNpgRows(List<NpgRow> rowList) {
        addNpgRows(rowList, npgRowList.size());
    }


    public void addNpgRow(NpgRow row, int i) {
        if (i < 0) {
            i = 0;
        } else if (i > npgRowList.size()) {
            i = npgRowList.size();
        }
        npgRowList.add(i, row);
        npgRowList.sort(null);
        int index = npgRowList.indexOf(row);
        fireTableRowsInserted(index, index);
    }

    public void addNpgRows(List<NpgRow> rowList, int i) {
        if (i < 0) {
            i = 0;
        } else if (i > npgRowList.size()) {
            i = npgRowList.size();
        }
        if (npgRowList.addAll(i, rowList)) {
            npgRowList.sort(null);
            fireTableStructureChanged();
        }
    }

    public void removeNpgRow(NpgRow row) {
        int index = npgRowList.indexOf(row);
        if (index >= 0) {
            npgRowList.remove(index);
            fireTableRowsDeleted(index, index);
        }

    }

    public void removeNpgRows(List<NpgRow> rowList) {
        if (npgRowList.removeAll(rowList)) {
            fireTableStructureChanged();
        }
    }
}
