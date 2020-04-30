package table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableModelNotEdit extends TableModel{
	
	public boolean isCellEditable(int row, int col) { //수정가능
		return false;
	}
}
