package npg.old;

import java.util.stream.IntStream;

import javax.swing.DefaultListModel;
import javax.swing.JList;

public final class NpgListGenerator {
	/**
	 * @wbp.factory
	 */
	public static JList<Integer> createJList() {
		SortedListModel dataModel = new SortedListModel();
		IntStream.range(1, 32).forEach(e -> dataModel.addElement(e));
		JList<Integer> list = new JList<>(dataModel);
		
		
		return list;
	}
}