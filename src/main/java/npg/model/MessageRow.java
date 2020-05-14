package npg.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MessageRow implements Comparable<MessageRow> {

    private static final Comparator<MessageRow> MESSAGEROW_COMPARATOR = Comparator.comparing(MessageRow::getRow);

    private JMessage row;

    private List<String> npgList;

    private boolean filtered;


    public MessageRow(String jMessage, String list) {
        row = new JMessage(jMessage);
        if (list != null) {
            npgList = new ArrayList<>(Arrays.asList(list.split("[^a-zA-z0-9_-]")));
            npgList.sort(NpgDefinitionTableModel.COMPARATOR);
        } else {
            npgList = new ArrayList<String>();
        }

    }

    public JMessage getRow() {
        return row;
    }

    public void setRow(JMessage row) {
        this.row = row;
    }

    public List<String> getNpgList() {
        return npgList;
    }

    public void setNpgList(List<String> npgList) {
        this.npgList = npgList;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((row == null) ? 0 : row.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MessageRow other = (MessageRow) obj;
        if (row == null) {
            if (other.row != null)
                return false;
        } else if (!row.equals(other.row))
            return false;
        return true;
    }

    @Override
    public int compareTo(MessageRow o1) {
        return MESSAGEROW_COMPARATOR.compare(this, o1);
    }


}
