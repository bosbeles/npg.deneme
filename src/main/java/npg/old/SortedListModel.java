package npg.old;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortedListModel extends AbstractListModel<Integer> {

    List<Integer> model;

    public SortedListModel() {
        model = new ArrayList<Integer>();
    }

    public int getSize() {
        return model.size();
    }

    public Integer getElementAt(int index) {
        return model.get(index);
    }

    public List<Integer> getAll() {
        return new ArrayList<Integer>(model);
    }

    public void addElement(Integer element) {
        if (model.add(element)) {
            Collections.sort(model);
            fireContentsChanged(this, 0, getSize());
        }
    }

    public void addAll(List<Integer> all) {
        model.addAll(all);
        Collections.sort(model);
        fireContentsChanged(this, 0, getSize());
    }

    public void clear() {
        model.clear();
        fireContentsChanged(this, 0, getSize());
    }

    public boolean contains(Integer element) {
        return model.contains(element);
    }

    public boolean removeElement(Integer element) {

        int index = model.indexOf(element);
        if (index >= 0) {
            model.remove(index);
            fireIntervalRemoved(this, index, index);
            return true;
        }
        return false;
    }

    public boolean removeAll(List<Integer> element) {
        int size = getSize();
        boolean removed = model.removeAll(element);
        if (removed) {
            fireIntervalRemoved(this, 0, size - 1);
        }
        return removed;
    }
}