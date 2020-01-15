package pl.kti.itsec.sqlinjection.data;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.AbstractTableModel;

abstract class ResultSetTableModel extends AbstractTableModel {
	private ResultSet _rs;
	private ResultSetMetaData _rsmd;

	public String getColumnName(int c) {
		try {
			return _rsmd.getColumnName(c + 1);
		} catch (SQLException e) {
			System.err.println("Error " + e);
			return "";
		}
	}

	public int getColumnCount() {
		if (_rsmd == null) { return 0; }
		try {
			return _rsmd.getColumnCount();
		} catch (SQLException e) {
			System.err.println("Error " + e);
			return 0;
		}
	}

	protected ResultSet getResultSet() {
		return _rs;
	}

	public void setResultSet(ResultSet rs) throws SQLException {
		_rs = rs;
		if (_rs != null) {
			_rsmd = rs.getMetaData();
		} else {
			_rsmd = null;
		}
		
		fireTableStructureChanged();
	}

}
