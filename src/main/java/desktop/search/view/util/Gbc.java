package desktop.search.view.util;

import java.awt.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.awt.GridBagConstraints.*;

public final class Gbc {

    private final GridBagConstraints c = new GridBagConstraints();

    private Gbc() {
        super();
    }

    public static Gbc constraints(int row, int column) {
        return new Gbc().row(row).column(column)
                .anchor(LINE_START).insets(4, 4, 4, 4);
    }

    public Gbc apply(Function<Gbc, Gbc> function) {
        return function.apply(this);
    }

    public GridBagConstraints applyAndBuild(Function<Gbc, Gbc> function) {
        return function.apply(this).build();
    }

    public Gbc row(int row) {
        c.gridy = row;
        return this;
    }

    public Gbc column(int column) {
        c.gridx = column;
        return this;
    }

    public Gbc insets(int top, int left, int bottom,
                      int right) {
        c.insets = new Insets(top, left, bottom, right);
        return this;
    }

    public Gbc anchor(int anchor) {
        c.anchor = anchor;
        return this;
    }

    public Gbc fillBoth() {
        return fillHorizontal().fillVertical().fill(BOTH);
    }

    public Gbc fillHorizontal() {
        return weightX(1).fill(HORIZONTAL);
    }

    public Gbc fillVertical() {
        return weightY(1).fill(VERTICAL);
    }

    public Gbc weightX(int weight) {
        c.weightx = weight;
        return this;
    }

    public Gbc weightY(int weight) {
        c.weighty = weight;
        return this;
    }

    private Gbc fill(int type) {
        c.fill = type;
        return this;
    }

    public Gbc gridwidth(int width) {
        c.gridwidth = width;
        return this;
    }

    public Gbc gridheight(int height) {
        c.gridheight = height;
        return this;
    }

    public GridBagConstraints build() {
        return c;
    }

}