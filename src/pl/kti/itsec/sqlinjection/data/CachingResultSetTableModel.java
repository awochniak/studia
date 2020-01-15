package pl.kti.itsec.sqlinjection.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CachingResultSetTableModel extends ResultSetTableModel {

	private static final long serialVersionUID = -7792994866489972864L;

	private ArrayList<Object[]> _cache;

	public CachingResultSetTableModel(ResultSet rs) {
		_cache = new ArrayList<Object[]>();
		try {
			if (rs != null) {
				setResultSet(rs);
			}
		} catch (SQLException e) {
			System.out.println("Error " + e);
		}
	}

	public Object getValueAt(int r, int c) {
		if (r < _cache.size())
			return ((Object[]) _cache.get(r))[c];
		else
			return null;
	}

	public int getRowCount() {
		return _cache.size();
	}

	public void setResultSet(ResultSet rs) throws SQLException {
		super.setResultSet(rs);

		_cache = new ArrayList<Object[]>();
		int cols = getColumnCount();

		/*
		 * place all data in an array list of Object[] arrays We don't use an
		 * Object[][] because we don't know how many rows are in the result set
		 */
		if (rs != null) {
			while (rs.next()) {
				Object[] row = new Object[cols];
				for (int j = 0; j < row.length; j++)
					row[j] = rs.getObject(j + 1);
				_cache.add(row);
			}
		}
	}
}
