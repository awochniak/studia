package pl.kti.itsec.sqlinjection.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mysql.jdbc.PreparedStatement;

import pl.kti.itsec.sqlinjection.data.CachingResultSetTableModel;
import pl.kti.itsec.sqlinjection.data.DBManager;

public class DBDemoFrame extends JFrame {
	
	private static final long serialVersionUID = -6846315193410091203L;

	public static void main(String[] args) {
		JFrame app_frame = new DBDemoFrame();
		app_frame.setVisible(true);
	}
	
	private JTextField _user;
	private JTextField _pass;
	private JButton _sendButton;
	private JTable _table;
	private CachingResultSetTableModel _tableModel;
	
	public DBDemoFrame() {
		super("SQL injection DEMO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 650);
		setLocationRelativeTo(null);
		
		initGUI();
	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		
		_user = new JTextField("");
		
		_pass = new JTextField("");
	
		GridLayout g = new GridLayout(3,2);
		JPanel p = new JPanel();
		p.setLayout(g);
		p.add(new JLabel("login"));
		p.add(new JLabel("password"));
		p.add(_user);
		p.add(_pass);
		
		// button
		_sendButton = new JButton("Login");
		p.add(_sendButton);
		
		add(p, BorderLayout.NORTH);
		
		// data table
		_tableModel = new CachingResultSetTableModel(null);
		_table = new JTable(_tableModel);
		JScrollPane tableScroll = new JScrollPane(_table);
        add(tableScroll, BorderLayout.SOUTH);

		
		
		
		// send query handler
		_sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String query = "select * from users where username = ? and password = ?";
				System.out.println(query);
				sendQuery(query);
			}
		});
	}

	protected void sendQuery(String query) {
		try {
						
			if (query.length() > 6 && 
				( 
					query.substring(0, 6).equalsIgnoreCase("insert") || 
					query.substring(0, 6).equalsIgnoreCase("update") ||
					query.substring(0, 6).equalsIgnoreCase("delete") 
				)
			) {
				Statement stmt = DBManager.getConnection().createStatement();
				stmt.executeUpdate(query);
			    _tableModel.setResultSet(null);
			    stmt.close();
			} else if ((query.length() > 6) && (query.substring(0,6).equalsIgnoreCase("select"))){
				// create SQL statement and execute query read from the _editor
				
				java.sql.PreparedStatement prepStmt = DBManager.getConnection().prepareStatement(query);
				prepStmt.setString(1, _user.getText());
				prepStmt.setString(2, getHash(_pass.getText()));
				ResultSet queryResult = prepStmt.executeQuery();
				System.out.println(prepStmt);

				// pass the resultSet to the table model (use setResultSet method from the model)
				_tableModel.setResultSet(queryResult);
				// close the resultSet and statement
				queryResult.close();
				prepStmt.close();
			}
			else
			{
				Statement stmt = DBManager.getConnection().createStatement();
				stmt.execute(query);
			    stmt.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public String getHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
		byte[] input = digest.digest(password.getBytes("UTF-8"));
		return input.toString();
	}
}
