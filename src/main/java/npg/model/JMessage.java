package npg.model;

import java.util.Comparator;

public class JMessage implements Comparable<JMessage> {

    private static final Comparator<JMessage> JMESSAGE_COMPARATOR = Comparator.comparing(JMessage::getLabel)
            .thenComparing(JMessage::getSublabel);
    private int label;
    private int sublabel;

    public JMessage() {
        // TODO Auto-generated constructor stub
    }

    public JMessage(int label, int sublabel) {
        this.label = label;
        this.sublabel = sublabel;
    }


    public JMessage(String message) {
        String[] split = message.substring(1).split("\\.");
        label = Integer.parseInt(split[0]);
        sublabel = Integer.parseInt(split[1]);
    }


    @Override
    public String toString() {
        return "J" + label + "." + sublabel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + label;
        result = prime * result + sublabel;
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
        JMessage other = (JMessage) obj;
        if (label != other.label)
            return false;
        if (sublabel != other.sublabel)
            return false;
        return true;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getSublabel() {
        return sublabel;
    }

    public void setSublabel(int sublabel) {
        this.sublabel = sublabel;
    }

    @Override
    public int compareTo(JMessage o) {
        return JMESSAGE_COMPARATOR.compare(this, o);
    }
}
