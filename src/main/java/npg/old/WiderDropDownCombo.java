package npg.old;

import javax.swing.*;
import java.awt.*;

public class WiderDropDownCombo<T> extends JComboBox<T> {

    private String type;
    private boolean layingOut = false;
    private int widestLengh = 0;
    private boolean wide = false;

    public WiderDropDownCombo() {
        super();
    }

    public WiderDropDownCombo(T[] objs) {
        super(objs);
    }

    public static void main(String[] args) {
        String title = "Combo Test";
        JFrame frame = new JFrame(title);

        String[] items = {
                "I need lot of width to be visible , oh am I visible now",
                "I need lot of width to be visible , oh am I visible now"};
        WiderDropDownCombo simpleCombo = new WiderDropDownCombo(items);
        simpleCombo.setPreferredSize(new Dimension(180, 20));
        simpleCombo.setWide(true);
        JLabel label = new JLabel("Wider Drop Down Demo");

        frame.getContentPane().add(simpleCombo, BorderLayout.NORTH);
        frame.getContentPane().add(label, BorderLayout.SOUTH);
        int width = 200;
        int height = 150;
        frame.setSize(width, height);
        frame.setVisible(true);

    }

    public boolean isWide() {
        return wide;
    }

    // Setting the JComboBox wide
    public void setWide(boolean wide) {
        this.wide = wide;
        widestLengh = getWidestItemWidth();

    }

    public Dimension getSize() {
        Dimension dim = super.getSize();
        if (!layingOut && isWide())
            dim.width = Math.max(widestLengh, dim.width);
        return dim;
    }

    public int getWidestItemWidth() {

        int numOfItems = this.getItemCount();
        Font font = this.getFont();
        FontMetrics metrics = this.getFontMetrics(font);
        int widest = 0;
        for (int i = 0; i < numOfItems; i++) {
            Object item = this.getItemAt(i);
            int lineWidth = metrics.stringWidth(item.toString());
            widest = Math.max(widest, lineWidth);
        }

        return widest + 10;
    }

    public void doLayout() {
        try {
            layingOut = true;
            super.doLayout();
        } finally {
            layingOut = false;
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String t) {
        type = t;
    }
}