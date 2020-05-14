package npg.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.DefaultTableModel;

public class NpgDefinitionTableModel extends DefaultTableModel {

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
	
	public NpgDefinitionTableModel(List<MessageRow> rowList) {
		Object[][] dataVector = new Object[rowList.size()][2];
		for (int i = 0; i < dataVector.length; i++) {
			MessageRow messageRow = rowList.get(i);
			Object[] objects = dataVector[i];
			objects[0] = messageRow.getRow();
			objects[1] = messageRow.getNpgList();
		}
		
		setDataVector(dataVector, new Object[] {"Mesaj", "Mesaj Grupları"});
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return column == 1;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case 0:
			return JMessage.class;
		case 1:
			return List.class;
		default:
			return Object.class;
		}
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		if (column == 1) {
			String text = (String) aValue;
			List<String> npgs = Arrays.stream(text.trim().split("[^A-Za-z0-9_-]+")).collect(Collectors.toList());

			npgs.sort(COMPARATOR);

			String ngpString = npgs.stream().collect(Collectors.joining(" "));
			super.setValueAt(ngpString, row, column);

		} else {
			super.setValueAt(aValue, row, column);
		}
	}
}
