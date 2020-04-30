package table;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel{
	String [] columnName;
	ArrayList<ArrayList<String>> data;
	public TableModel() {
		
	}
	public TableModel(String[] columnName, ArrayList<ArrayList<String>> data) {
		super();
		this.columnName = columnName;
		this.data = data;
	}
	@Override
	public int getColumnCount() {
		return columnName.length;
	}

	public void setColumnName(String[] columnName) {
		this.columnName = columnName;
	}
	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}
	public String getColumnName(int c) {
		return columnName[c];
	}
	public ArrayList<ArrayList<String>> getData() {
		return data;
	}
	public void setData(ArrayList<ArrayList<String>> data) {
		this.data = data;
	}
	public boolean isCellEditable(int row, int col) { //수정가능
		if ( col!=0) return true;
		else return false;
	}
	public void setValueAt(Object value,int row, int col) {
		ArrayList<String> temp = (ArrayList<String>)data.get(row);
		temp.set(col, (String) value);
		fireTableCellUpdated(row, col);
	}
}
