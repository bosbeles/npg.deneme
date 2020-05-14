package npg.model;

import java.util.ArrayList;
import java.util.List;

public class NpgRowWithFilterTableModel extends NpgRowTableModel {

    private boolean editable = true;

    public NpgRowWithFilterTableModel() {
        super(new ArrayList<>());
    }


    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 2:
                return "İzinli";
            case 3:
                return "Filtreli";

            default:
                return super.getColumnName(columnIndex);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 2:
            case 3:
                return Boolean.class;
            default:
                return super.getColumnClass(columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return editable && columnIndex > 1;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }


    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 2:
                return npgRowList.get(rowIndex).isAllowed();
            case 3:
                return npgRowList.get(rowIndex).isFiltered();
            default:
                return super.getValueAt(rowIndex, columnIndex);
        }

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        NpgRow npgRow = npgRowList.get(rowIndex);

        switch (columnIndex) {
            case 2:
                npgRow.setAllowed((Boolean) aValue);
                break;
            case 3:
                npgRow.setFiltered((Boolean) aValue);
                break;
            default:
                break;
        }
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void shift(List<NpgRow> rowList, boolean up) {


        if (up) {
            int currentNew = -1;
            for (NpgRow npgRow : rowList) {
                int index = npgRowList.indexOf(npgRow);
                if (index > 0 && index > currentNew + 1) {
                    swap(index, index - 1);
                    currentNew = index - 1;
                } else {
                    currentNew = index;
                }

            }
        } else {
            int currentNew = npgRowList.size();
            for (int i = rowList.size() - 1; i >= 0; i--) {
                NpgRow npgRow = rowList.get(i);
                int index = npgRowList.indexOf(npgRow);
                if (index < npgRowList.size() - 1 && index + 1 < currentNew) {
                    swap(index, index + 1);
                    currentNew = index + 1;
                } else {
                    currentNew = index;
                }
            }

        }

        fireTableStructureChanged();
    }

    private void swap(int oldIndex, int newIndex) {
        NpgRow oldRow = npgRowList.get(oldIndex);
        NpgRow newRow = npgRowList.get(newIndex);
        npgRowList.set(newIndex, oldRow);
        npgRowList.set(oldIndex, newRow);
    }


    @Override
    public void addNpgRow(NpgRow row, int i) {
        npgRowList.add(i, row);
        int index = npgRowList.indexOf(row);
        fireTableRowsInserted(index, index);
    }

    @Override
    public void addNpgRows(List<NpgRow> rowList, int i) {
        int oldSize = npgRowList.size();
        if (npgRowList.addAll(i, rowList)) {
            fireTableRowsInserted(oldSize, npgRowList.size() - 1);
        }
    }


}
