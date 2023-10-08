package views;

import javax.swing.table.DefaultTableModel;

class JTableBloqueoCeldas extends DefaultTableModel {

	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}

}
