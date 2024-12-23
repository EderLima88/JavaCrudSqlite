package Main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.sql.*;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JavaCrudSqLite {

	private JFrame frame;
	private JTable table;
	private JTextField txtName;
	private JTextField txtAddress;
	private JTextField txtPhone;
	private JTextField txtID;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaCrudSqLite window = new JavaCrudSqLite();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public JavaCrudSqLite() {
		initialize();
		Connect();
		table();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	ResultSetMetaData rd;
	DefaultTableModel model;
	
	public void Connect() {
		
		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:sqlite:studant.db");
			JOptionPane.showMessageDialog(null, "Conexão bem sucedida");
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("errou sql");
		}
	}
	
	public void table() {
		
		int a;
		
		try {
			pst = con.prepareStatement("SELECT * FROM student");
			rs = pst.executeQuery();
			
			rd = rs.getMetaData();
			a = rd.getColumnCount();
			model = (DefaultTableModel)table.getModel();
			model.setRowCount(0);
			
			while(rs.next()) {
				Vector v = new Vector();
				
				for(int i=1; i<=a; i++) {
					v.add(rs.getString("id"));
					v.add(rs.getString("stname"));
					v.add(rs.getString("address"));
					v.add(rs.getString("phone"));
				}
				
				model.addRow(v);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(1000, 500, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("CRUD em Java com persistencia SqlLite");
		//frame.getContentPane().setBackground(Color.yellow);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Student", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.ITALIC, 18), new Color(0, 0, 0)));
		panel.setBounds(65, 110, 462, 449);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblName.setBounds(50, 89, 76, 37);
		lblName.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblName);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtName.setBounds(136, 97, 263, 30);
		panel.add(txtName);
		txtName.setColumns(10);
		
		txtAddress = new JTextField();
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtAddress.setColumns(10);
		txtAddress.setBounds(136, 219, 263, 30);
		panel.add(txtAddress);
		
		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtPhone.setColumns(10);
		txtPhone.setBounds(136, 337, 263, 30);
		panel.add(txtPhone);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setHorizontalAlignment(SwingConstants.LEFT);
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAddress.setBounds(50, 211, 76, 37);
		panel.add(lblAddress);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setHorizontalAlignment(SwingConstants.LEFT);
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPhone.setBounds(50, 337, 76, 37);
		panel.add(lblPhone);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, new Font("Arial", Font.ITALIC, 18), null));
		panel_1.setBounds(65, 609, 462, 110);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(36, 46, 35, 25);
		lblId.setHorizontalAlignment(SwingConstants.LEFT);
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel_1.add(lblId);
		
		txtID = new JTextField();
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtID.setColumns(10);
		txtID.setBounds(66, 46, 200, 30);
		panel_1.add(txtID);
		
		JButton btnFind = new JButton("Find");
		btnFind.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		btnFind.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				try {
					
					String id = txtID.getText();
									
					pst = con.prepareStatement("SELECT stname, address, phone FROM student WHERE id = ?");
					pst.setString(1, id);
					ResultSet rs = pst.executeQuery();
								
					if(rs.next()==true) {
						String name = rs.getString(1);
						String address = rs.getString(2);
						String phone = rs.getString(3);
				
						txtName.setText(name);
						txtAddress.setText(address);
						txtPhone.setText(phone);
						
					}else {
						txtName.setText("");
						txtAddress.setText("");
						txtPhone.setText("");
					}
					
				} catch (Exception e2) {
					
				}
						
			}
		});
		
		btnFind.setBounds(276, 47, 137, 30);
		panel_1.add(btnFind);
	
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name, address;
				int phone;
				
				name = txtName.getText();
				address = txtAddress.getText();
				phone = Integer.parseInt(txtPhone.getText());
				
				try {
					pst = con.prepareStatement("INSERT INTO STUDENT(stname,address,phone)VALUES(?,?,?)");
					pst.setString(1, name);
					pst.setString(2, address);
					pst.setInt(3, phone);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Adição gravada");
					table();
				
					txtName.setText("");
					txtAddress.setText("");
					txtPhone.setText("");
					txtID.setText("");
					txtName.requestFocus();
					
					} catch (SQLException e1) {
						e1.printStackTrace();
					}				
				}		
		});
		btnAdd.setBounds(614, 635, 140, 50);
		frame.getContentPane().add(btnAdd);
	
		JButton btnUpdate = new JButton("Updade");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				String name, address;
				int phone, id;
				
				id = Integer.parseInt(txtID.getText());
				name = txtName.getText();
				address = txtAddress.getText();
				phone = Integer.parseInt(txtPhone.getText());
				
				try {
					pst = con.prepareStatement("UPDATE student SET stname = ?, address = ?, phone = ?  WHERE id = ?");
					pst.setString(1, name);
					pst.setString(2, address);
					pst.setInt(3, phone);
					pst.setInt(4, id);					
					pst.executeUpdate();
					
					JOptionPane.showMessageDialog(null,"Alteração gravada");
					table();
					
					txtName.setText("");
					txtAddress.setText("");
					txtPhone.setText("");
					txtID.setText("");
					txtName.requestFocus();
					
				} catch (SQLException e1) {
					
					e1.printStackTrace();
					}			
				}
		});
		btnUpdate.setBounds(780, 635, 140, 50);
		frame.getContentPane().add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		
				int id;
				id = Integer.parseInt(txtID.getText());
						
				try {
					
					pst = con.prepareStatement("DELETE FROM student WHERE id = ?");
					
					pst.setInt(1, id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Delete complete");
					table();
					
					txtName.setText("");
					txtAddress.setText("");
					txtPhone.setText("");
					txtID.setText("");
					txtName.requestFocus();
								
					
				} catch (Exception e2) {
					e2.printStackTrace();
					}
			}
		});
		
		
		btnDelete.setBounds(942, 635, 140, 50);
		frame.getContentPane().add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(596, 114, 497, 445);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 20));
		table.setRowHeight(table.getRowHeight() + 22);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Addres", "Phone"
			}
		));
		
		JLabel lblNewLabel = new JLabel("CRUD Java SqLite Swing - DB dentro Aplicação\r\n");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(65, 42, 731, 39);
		frame.getContentPane().add(lblNewLabel);
	}
}
