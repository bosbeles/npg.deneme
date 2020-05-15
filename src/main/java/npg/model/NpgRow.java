package npg.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class NpgRow implements Comparable<NpgRow> {

    private static final Comparator<NpgRow> NPGROW_COMPARATOR = Comparator.comparing(NpgRow::getNpg, NpgDefinitionTableModel.COMPARATOR);
    protected String npg;
    protected SortedSet<JMessage> messageList;


    protected boolean filtered;

    public NpgRow(String npg) {
        this.npg = npg;
        messageList = new TreeSet<>();
    }

    public NpgRow(NpgRow row) {
        this.messageList = row.messageList;
        this.npg = row.npg;
        this.filtered = row.filtered;
    }


    public boolean isAllowed() {
        return !filtered;
    }

    public void setAllowed(boolean allowed) {
        this.filtered = !allowed;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    public String getNpg() {
        return npg;
    }

    public void setNpg(String npg) {
        this.npg = npg;
    }

    public Collection<JMessage> getMessageList() {
        return messageList;
    }

    public void setMessageList(Collection<JMessage> messageList) {
        this.messageList = new TreeSet<>(messageList);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((npg == null) ? 0 : npg.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof NpgRow))
            return false;
        NpgRow other = (NpgRow) obj;
        if (npg == null) {
            if (other.npg != null)
                return false;
        } else if (!npg.equals(other.npg))
            return false;
        return true;
    }

    @Override
    public int compareTo(NpgRow o) {
        return NPGROW_COMPARATOR.compare(this, o);

    }

    @Override
    public String toString() {
        return npg + ": " + messageList;
    }
}
