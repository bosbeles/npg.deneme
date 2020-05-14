package npg.model;

import java.awt.Component;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ListClassRenderer extends DefaultTableCellRenderer {


	public ListClassRenderer() {
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if(value instanceof List) {
			List<Object> list = (List) value;
		
			String representation = list.stream().map(e -> e.toString()).sorted(NpgDefinitionTableModel.COMPARATOR).collect(Collectors.joining(" "));
			return super.getTableCellRendererComponent(table, representation, isSelected, hasFocus, row, column);
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
	
}