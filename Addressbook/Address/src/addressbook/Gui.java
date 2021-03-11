package addressbook;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.TextField;
import java.awt.Panel;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Gui extends JFrame {
	
	private JFrame frame;
	private final Choice choice = new Choice(); 
	
	private JTable table; 
	
	JTextField nameField;
	JTextField phoneField; 
	JTextField addressField; 
	JTextField emailField;; 
	static ObjectInputStream in=null;
	ObjectOutputStream out=null;
	
	

	
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				
				try {		
					Connection conn = null; //데이터베이스를 연결하기 위해	
					Class.forName("org.mariadb.jdbc.Driver"); //JDBC 드라이버 로드
					String url = "jdbc:mariadb://localhost:3306/mardb"; 
					

					AddressBook ad1 = new AddressBook(url,conn); //Addressbook url, conn 넣어 호출

					Gui window = new Gui(ad1);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui(AddressBook a) { //Adressbook 
		
		initialize(a);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(AddressBook ad)   {
	
		
		frame = new JFrame(); //������ ����
		frame.setBounds(100, 100, 477, 410);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel panel = new JPanel(); // ���̺� ������ �г� ����
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		
		JButton viewButton = new JButton("전체조회"); //��ü��ȸ ��ư (�˻���, ���̺� �˻��� �ּҷϸ� �� ��� Ŭ���� �ٽ� ��ü ��ȸ ����)
		viewButton.setActionCommand("view");
		panel.add(viewButton);
		
		choice.add("이름");
		choice.add("번호");
		panel.add(choice); //�˻��� �׸� ����
		
		TextField textField = new TextField();//�˻� �Է� â
		textField.setColumns(20);
		panel.add(textField);
		
		JButton searchButton = new JButton("검색"); //�˻� ��ư
		searchButton.setActionCommand("search");
		panel.add(searchButton);
		
		JButton deleteButton = new JButton("삭제"); //���� ��ư
		deleteButton.setActionCommand("delete");
		panel.add(deleteButton);
		
		
		
		
		// ���̺� �� �г� ����
		Panel panel_1 = new Panel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout(); //gridbaglayout���� ����
		gbl_panel_1.columnWidths = new int[]{42, 125, 0, 130, 38, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 30, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		
		JLabel nameLabel = new JLabel("이름"); //�̸� ��
		nameLabel.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_nameLabel = new GridBagConstraints();
		gbc_nameLabel.anchor = GridBagConstraints.WEST;
		gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_nameLabel.gridx = 1;
		gbc_nameLabel.gridy = 0;
		panel_1.add(nameLabel, gbc_nameLabel);
		
		
		nameField = new JTextField(); //�̸� �Է�â
		GridBagConstraints gbc_nameField = new GridBagConstraints();
		gbc_nameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_nameField.insets = new Insets(0, 0, 5, 5);
		gbc_nameField.gridx = 3;
		gbc_nameField.gridy = 0;
		panel_1.add(nameField, gbc_nameField);
		nameField.setColumns(10);
		
		
		JLabel phoneLabel = new JLabel("번호"); //��ȣ ��
		GridBagConstraints gbc_phoneLabel = new GridBagConstraints();
		gbc_phoneLabel.anchor = GridBagConstraints.WEST;
		gbc_phoneLabel.insets = new Insets(0, 0, 5, 5);
		gbc_phoneLabel.gridx = 1;
		gbc_phoneLabel.gridy = 1;
		panel_1.add(phoneLabel, gbc_phoneLabel);
		
		
		phoneField = new JTextField(); //��ȣ �Է�â
		GridBagConstraints gbc_phoneField = new GridBagConstraints();
		gbc_phoneField.insets = new Insets(0, 0, 5, 5);
		gbc_phoneField.fill = GridBagConstraints.HORIZONTAL;
		gbc_phoneField.gridx = 3;
		gbc_phoneField.gridy = 1;
		panel_1.add(phoneField, gbc_phoneField);
		phoneField.setColumns(10);
		
		
		JLabel addressLabel = new JLabel("주소"); //�ּҶ�
		GridBagConstraints gbc_addressLabel = new GridBagConstraints();
		gbc_addressLabel.anchor = GridBagConstraints.WEST;
		gbc_addressLabel.insets = new Insets(0, 0, 5, 5);
		gbc_addressLabel.gridx = 1;
		gbc_addressLabel.gridy = 2;
		panel_1.add(addressLabel, gbc_addressLabel);
		
		
		addressField = new JTextField(); //�ּ� �Է�â
		GridBagConstraints gbc_addressField = new GridBagConstraints();
		gbc_addressField.insets = new Insets(0, 0, 5, 5);
		gbc_addressField.fill = GridBagConstraints.HORIZONTAL;
		gbc_addressField.gridx = 3;
		gbc_addressField.gridy = 2;
		panel_1.add(addressField, gbc_addressField);
		addressField.setColumns(10);
		
		
		JLabel emailLabel = new JLabel("이메일"); //�̸��� ��
		GridBagConstraints gbc_emailLabel = new GridBagConstraints();
		gbc_emailLabel.anchor = GridBagConstraints.WEST;
		gbc_emailLabel.insets = new Insets(0, 0, 5, 5);
		gbc_emailLabel.gridx = 1;
		gbc_emailLabel.gridy = 3;
		panel_1.add(emailLabel, gbc_emailLabel);
		
		
		emailField = new JTextField(); //�̸��� �Է�â
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.insets = new Insets(0, 0, 5, 5);
		gbc_emailField.fill = GridBagConstraints.HORIZONTAL;
		gbc_emailField.gridx = 3;
		gbc_emailField.gridy = 3;
		panel_1.add(emailField, gbc_emailField);
		emailField.setColumns(10);
		
		
		
		// �߰�,����,����,���� ��ư
		JButton addButton  = new JButton("추가"); //�߰� ��ư ������ ���̺� �ּҷ� ������
		addButton.setActionCommand("add");
		GridBagConstraints gbc_addButton = new GridBagConstraints();
		gbc_addButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_addButton.insets = new Insets(0, 0, 5, 5);
		gbc_addButton.gridx = 1;
		gbc_addButton.gridy = 4;
		panel_1.add(addButton, gbc_addButton);
		
		
		JButton editButton = new JButton("수정"); //���� ��ư ������ ���̺� ���� �ּҷ��� �������ʵ� ������ ����Ǿ� �ּҷ� ������
		editButton.setActionCommand("edit");
		GridBagConstraints gbc_editButton = new GridBagConstraints();
		gbc_editButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_editButton.insets = new Insets(0, 0, 5, 5);
		gbc_editButton.gridx = 3;
		gbc_editButton.gridy = 4;
		panel_1.add(editButton, gbc_editButton);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 0, 5);
		gbc_panel_2.gridx = 2;
		gbc_panel_2.gridy = 5;
		panel_1.add(panel_2, gbc_panel_2);
		
		JButton exitButton = new JButton("종료"); //���� ��ư ������ â�� ����
		panel_2.add(exitButton);
		exitButton.setActionCommand("end");
		
		
		
		String[] colNames = new String[] {"이름","번호","주소","이메일"}; //���̺� ���
		DefaultTableModel model = new DefaultTableModel(colNames,0); //�� ���̺� ����� ���� ������ ���� ��ü ����
		table = new JTable(model); // ���̺� 
		table.setBackground(Color.LIGHT_GRAY); //�� ���� �� �絵�� ���̺� ���� �ٲ� �� 
		frame.getContentPane().add(table, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(table); //��ũ�� �г� (����� ���� �ʿ�����)
		frame.getContentPane().add(scrollPane, BorderLayout.EAST);
		
		
		
		//추가
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				// TODO Auto-generated method stub
				
				String[] rows = new String[4];
				rows[0] = nameField.getText();
				rows[1] = phoneField.getText();
				rows[2] = addressField.getText();
				rows[3] = emailField.getText();
				model.addRow(rows);
				
				nameField.setText("");
				phoneField.setText("");
				addressField.setText("");
				emailField.setText(""); 
				
				String name = rows[0];
				String phone = rows[1];
				String address = rows[2];
				String email= rows[3]; 
				
				
				try {
					ad.addAddress(new Person(name,phone,address,email));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
					JOptionPane.showMessageDialog(null, "추가하는데 오류가 생겼습니다." , "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					
				}
			}
		});
		
		//수정
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e2) {
				
				int ind = table.getSelectedRow(); 
				
				if(ind == -1) {
					JOptionPane.showMessageDialog(null,"수정할 항목을 선택해주세요.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				}
				String edit = (String) table.getValueAt(ind, 0); 
				
				int ind1 = -1;
				try { 
					ind1 = ad.searchName(edit);
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "파일을 검색하는데 문제가 발생했습니다.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				}
				
				String[] rows = new String[4];
				rows[0] = nameField.getText();
				rows[1] = phoneField.getText();
				rows[2] = addressField.getText();
				rows[3] = emailField.getText();
				
				nameField.setText("");
				phoneField.setText("");
				addressField.setText("");
				emailField.setText("");
				
				String name = rows[0];
				String phone = rows[1];
				String address = rows[2];
				String email= rows[3]; 
				
				
				
				Person p = new Person(name,phone,address,email);
				try {
					ad.modify(ind1, p);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
					// GUI에서 SQLException 처리하기
					JOptionPane.showMessageDialog(null, "수정할 수 없습니다.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					
				} 
				
				int remove = table.getRowCount();
				if(remove >0) {
					model.setRowCount(0); 
				}
				
				model.addRow(rows); 
				
			}
			
		});
		
		//검색
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e4) {
				if(choice.getSelectedIndex() == 0) { 
					String searchN1 = textField.getText(); 
					textField.setText(""); 
					int index1 = -1; 
					int remove = table.getRowCount();
					if(remove >0) {
						model.setRowCount(0); 
					}
					
					try {
						index1 = ad.searchName(searchN1);
						
						String[] rows = new String[4];
						rows[0] = ad.getPerson(index1).getName();
						rows[1] = ad.getPerson(index1).getPhoneNum();
						rows[2] = ad.getPerson(index1).getAddress();
						rows[3] = ad.getPerson(index1).getEmail();
						model.addRow(rows);
						
					} catch (Exception e) { 
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "찾으시는 이름이 없습니다.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
						
					}
					
					
					
				}else if(choice.getSelectedIndex() == 1) { 
					String searchP1 = textField.getText(); 
					textField.setText("");
					int index2 = -1; 
					
					int remove = table.getRowCount();
					if(remove >0) {
						model.setRowCount(0);

					}
					try {
						index2 = ad.searchPhoneNum(searchP1);
						
						String[] rows = new String[4];
						rows[0] = ad.getPerson(index2).getName();
						rows[1] = ad.getPerson(index2).getPhoneNum();
						rows[2] = ad.getPerson(index2).getAddress();
						rows[3] = ad.getPerson(index2).getEmail();
						model.addRow(rows);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null,"찾으시는 번호가 없습니다." , "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
						
					}
				}
			}
			
		});
		
		//삭제
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e5) {
				int rowIndex = table.getSelectedRow();  
				
				if(rowIndex == -1)
					JOptionPane.showMessageDialog(null,  "삭제할 항목을 선택하세요.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				
				String del = (String) table.getValueAt(rowIndex, 0); 
				
				int ind2 = -1;
				try { 
					ind2 = ad.searchName(del);
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "파일을 검색하는데 문제가 발생했습니다.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
				}
				
				model.removeRow(rowIndex); 
				try {
					ad.delete(ind2);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
					// GUI에서 SQLException 처리하기
					JOptionPane.showMessageDialog(null, "삭제를 할 수 없습니다.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					
				} 
			}
			
		});
		
		//전체 조회
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e6) {
				int c = 0;
				try {
					c = ad.getId();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
					// GUI에서 SQLException 처리하기
					JOptionPane.showMessageDialog(null, "주소록 수를 불러 오는 데 문제가 발생했습니다.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					
				}
				int remove = table.getRowCount();
				if(remove >0) {
					model.setRowCount(0);
					//table.removeAll(); 
				}else if(c == 0){
					JOptionPane.showMessageDialog(null, "표시할 주소록이 없습니다.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					
				}
				for(int i = 0; i < c; i++) { 
					try {
						if(ad.getIdByCheck(i)==false) {// i값에 해당하는 id값 있으면 테이블에 추가
							String[] rows = new String[4];
							rows[0] = ad.getPerson(i).getName();
							rows[1] = ad.getPerson(i).getPhoneNum();
							rows[2] = ad.getPerson(i).getAddress();
							rows[3] = ad.getPerson(i).getEmail();
							model.addRow(rows);
						}else { //없으면 i++
							continue;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						
						// GUI에서 SQLException 처리하기
						JOptionPane.showMessageDialog(null, "주소록을 불러오는데 오류가 발생했습니다.", "ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
		});
		
		//종료
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e7) {
				System.exit(0); //종료하기
			}
			
		});

	}

	

	
	
}


