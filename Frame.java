import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.*;

import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Frame{
	private JFrame frame;
	private JPanel special;
	private String email2;
	private ArrayList<String> orders;
	private float total;
	
	/*
	 * FUNCTION: StartPage
	 * PURPOSE: Starting page
	 */
	public void startPage() {
		orders = new ArrayList<>();
		
		frame = new JFrame("Login Page");
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		special = new JPanel();
		special.setLayout(null);
		special.setVisible(true);
		frame.getContentPane().add(special);
		
		JTextField username = new JTextField();
		JTextField password = new JTextField();
		username.setSize(200, 50);
		password.setSize(200, 50);
		username.setLocation(200, 200);
		password.setLocation(200, 300);
		special.add(password);
		special.add(username);
		
		JLabel user = new JLabel("Email:");
		JLabel pass = new JLabel("Password:");
		user.setSize(100, 50);
		pass.setSize(100, 50);
		user.setLocation(50,200);
		pass.setLocation(50,300);
		special.add(pass);
		special.add(user);
		
		JLabel loginLabel = new JLabel("Login");
		loginLabel.setSize(100, 50);
		loginLabel.setLocation(250, 100);
		special.add(loginLabel);
		
		JButton login = new JButton("Login");
		login.setSize(100, 50);
		login.setLocation(220, 400);
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String email = username.getText();
				String passWord = password.getText();
				special.removeAll();
				try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sunflower", "postgres", "password"); 
						Statement statement = connection.createStatement();) {
					String temp = "select passwords " + "from users " + "where email=?";
					PreparedStatement newStatement = connection.prepareStatement(temp);
					newStatement.setString(1, email);
					ResultSet resultSet = newStatement.executeQuery();
					if (resultSet.next()) {
						do {
							if (resultSet.getString("passwords").equalsIgnoreCase(passWord)) {
								temp = "select * " + "from customer";
								newStatement = connection.prepareStatement(temp);
								ResultSet resultSet2 = newStatement.executeQuery();
								while (resultSet2.next()) {
									System.out.println(resultSet2.getString(1)+" "+email);
									if (resultSet2.getString(1).equalsIgnoreCase(email)) {
										email2 = email;
										searchPage(null);
									}
								}
								employeePage();
							}
							else {
								JOptionPane.showMessageDialog(frame, "Password Incorrect.");
							}
						} while (resultSet.next());
					}
					else {
						JOptionPane.showMessageDialog(frame, "Email Doesn't Exist.");
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		special.add(login);
				
		special.revalidate();
		special.repaint();
	}
	
	public void searchPage(ArrayList<String> orders) {
		frame.setTitle("Search Page");
		
		JLabel companyName = new JLabel("Look Inna Books");
		companyName.setSize(150, 150);
		companyName.setLocation(225, 200);
		special.add(companyName);
		
		JTextField search = new JTextField("Search");
		search.setSize(200, 30);
		search.setLocation(150, 300);
		special.add(search);
		
		String[] easy = {"Book Name", "Author Name", "ISBN", "Genre"};
		JList<String> op = new JList<String>(easy);
		JComboBox<String> options = new JComboBox<String>(easy);
		options.setSize(100, 20);
		options.setLocation(200, 400);
		special.add(options);
		
		JButton searchButton = new JButton("?");
		searchButton.setSize(50,30);
		searchButton.setLocation(350, 300);
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String searchBar = search.getText();
				String opSearch = (String) options.getSelectedItem();
				try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sunflower", "postgres", "password"); 
				Statement statement = connection.createStatement();) {
					if (opSearch.equalsIgnoreCase("ISBN")) {
						//how to parse a large integer from a string
						String temp = "select * " + "from book " + "where ISBN=?";
						PreparedStatement newStatement = connection.prepareStatement(temp);
						newStatement.setObject(1, Long.parseLong(searchBar), java.sql.Types.NUMERIC);
						ResultSet resultSet = newStatement.executeQuery();
						booksResultPage(resultSet, orders);
					}
					else if (opSearch.equalsIgnoreCase("Author Name")) {
						String temp = "select *" + "from book " + "where first_name=?";
						PreparedStatement newStatement = connection.prepareStatement(temp);
						try {
							newStatement.setString(1, searchBar);
							ResultSet resultSet = newStatement.executeQuery();
							booksResultPage(resultSet, orders);
						}
						catch (SQLException e1) {
							System.out.println("Bad entry");
						}
					}
					else if (opSearch.equalsIgnoreCase("Book Name")) {
						String temp = "select * " + "from book " + "where title=?";
						PreparedStatement newStatement = connection.prepareStatement(temp);
						try {
							newStatement.setString(1, searchBar);
							ResultSet resultSet = newStatement.executeQuery();
							booksResultPage(resultSet, orders);
						}
						catch (SQLException e1) {
							System.out.println("Bad entry");
						}
					}
					else if (opSearch.equalsIgnoreCase("Genre")) {
						String temp = "select *" + "from book " + "where genre=?";
						PreparedStatement newStatement = connection.prepareStatement(temp);
						try {
							newStatement.setString(1, searchBar);
							ResultSet resultSet = newStatement.executeQuery();
							booksResultPage(resultSet, orders);
						}
						catch (SQLException e1) {
							
						}
					}
			
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		special.add(searchButton);
		
		special.revalidate();
		special.repaint();
	}
	
	public void employeePage() {
		frame.setTitle("Employee Page");
		frame.remove(special);
		
		String[] names = {"Reports", "Add/Remove Employees", "Add Books", "Add Publisher"};
		JList<Object> options = new JList<Object>(names);
		options.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane listScrollPane = new JScrollPane(options);
		listScrollPane.setVisible(true);
		listScrollPane.setSize(300, 600);
		Dimension minimumSize = new Dimension(100, 50);
        listScrollPane.setMinimumSize(minimumSize);
		
		JPanel displayPane = new JPanel();
		displayPane.setLayout(null);
		displayPane.setMinimumSize(minimumSize);
		displayPane.setSize(300, 600);
		
		options.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (e.getValueIsAdjusting()==true) {
					String value = (String) options.getSelectedValue();
					if (value.equalsIgnoreCase(names[0])) {
						displayPane.removeAll();
						displayPane.revalidate();
						displayPane.repaint();
						JLabel label = new JLabel("Hello");
						displayPane.add(label);
					}
					else if (value.equalsIgnoreCase(names[2])) {
						Checkbox remove = new Checkbox("Remove");
						remove.setSize(15, 15);
						remove.setLocation(100, 420);
						displayPane.add(remove);
						
						JButton remove2 = new JButton("Remove");
						remove2.setSize(90,25);
						remove2.setLocation(0, 420);
						displayPane.add(remove2);
						
						displayPane.removeAll();
						displayPane.revalidate();
						displayPane.repaint();
						JTextField field1 = new JTextField("ISBN");
						JTextField field2 = new JTextField("Author Name");
						JTextField field3 = new JTextField("Book Title");
						JTextField field4 = new JTextField("Genre");
						JTextField field5 = new JTextField("Price");
						JTextField field6 = new JTextField("Percent");
						JTextField field7 = new JTextField("Publisher");
						JButton filled = new JButton("Filled");
						field1.setSize(300, 50);
						field1.setLocation(50,0);
						field2.setSize(300, 50);
						field2.setLocation(50,50);
						field3.setSize(300, 50);
						field3.setLocation(50,100);
						field4.setSize(300, 50);
						field4.setLocation(50,150);
						field5.setSize(300, 50);
						field5.setLocation(50,200);
						field6.setSize(300, 50);
						field6.setLocation(50,250);
						field7.setSize(300, 50);
						field7.setLocation(50,300);
						
						filled.setSize(300,50);
						filled.setLocation(150,500);
						displayPane.add(field1);
						displayPane.add(field2);
						displayPane.add(field3);
						displayPane.add(field4);
						displayPane.add(field5);
						displayPane.add(field6);
						displayPane.add(field7);
						displayPane.add(filled);
						displayPane.revalidate();
						displayPane.repaint();
						
						filled.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								if (field1.getText().isEmpty() || field2.getText().isEmpty() || 
										field3.getText().isEmpty() || field4.getText().isEmpty() || field5.getText().isEmpty() || 
										field6.getText().isEmpty()) {
									
								}
								else {
									try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sunflower", "postgres", "password"); 
											Statement statement = connection.createStatement();) {
										String addEmp = "insert " + "into book " + "values (?,?,?,?,?,?)";
										PreparedStatement newStatement = connection.prepareStatement(addEmp);
										newStatement.setObject(1, Long.parseLong(field1.getText()), java.sql.Types.NUMERIC);
										newStatement.setString(2, field3.getText());
										if (field2.getText().contains(" ")) {
											String[] temp = field2.getText().split(" ");
											newStatement.setString(3, temp[0]);
											newStatement.setString(4, temp[1]);
										}
										else {
											newStatement.setString(3, field2.getText());
											newStatement.setString(4, field2.getText());
										}
										newStatement.setString(5, field4.getText());
										newStatement.setObject(6, Float.parseFloat(field5.getText()), java.sql.Types.NUMERIC);
										newStatement.setObject(7, Float.parseFloat(field6.getText()), java.sql.Types.NUMERIC);
										newStatement.executeUpdate();
										if (!field7.getText().isEmpty()) {
											addEmp = "insert " + "into pub_book " + "values (?,?)";
											newStatement = connection.prepareStatement(addEmp);
											newStatement.setObject(2, Long.parseLong(field1.getText()), java.sql.Types.NUMERIC);
											newStatement.setString(1, field7.getText());
											newStatement.executeUpdate();
										}
									}
									catch (SQLException e) {
										
									}
								}
							}
						});
					}
					else if (value.equalsIgnoreCase(names[1])) {
						Checkbox remove = new Checkbox("Remove");
						remove.setSize(15, 15);
						remove.setLocation(100, 520);
						displayPane.add(remove);
						
						JButton remove2 = new JButton("Remove");
						remove2.setSize(90,25);
						remove2.setLocation(0, 520);
						displayPane.add(remove2);
						
						displayPane.removeAll();
						displayPane.revalidate();
						displayPane.repaint();
						JTextField field1 = new JTextField("Email");
						JTextField field2 = new JTextField("Password");
						JTextField field3 = new JTextField("First Name");
						JTextField field4 = new JTextField("Last Name");
						JButton filled = new JButton("Filled");
						field1.setSize(200, 100);
						field1.setLocation(50,0);
						field2.setSize(200, 100);
						field2.setLocation(50,100);
						field3.setSize(200, 100);
						field3.setLocation(50,200);
						field4.setSize(200, 100);
						field4.setLocation(50,300);
						filled.setSize(100,50);
						filled.setLocation(150,500);
						displayPane.add(field1);
						displayPane.add(field2);
						displayPane.add(field3);
						displayPane.add(field4);
						displayPane.add(filled);
						displayPane.revalidate();
						displayPane.repaint();
						
						filled.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								if (field1.getText().isEmpty() || field2.getText().isEmpty() || 
										field3.getText().isEmpty() || field4.getText().isEmpty()) {
									
								}
								else {
									try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sunflower", "postgres", "password"); 
											Statement statement = connection.createStatement();) {
										String addEmp = "insert " + "into users " + "values (?,?,?,?)";
										PreparedStatement newStatement = connection.prepareStatement(addEmp);
										newStatement.setString(1, field1.getText());
										newStatement.setString(2, field2.getText());
										newStatement.setString(3, field3.getText());
										newStatement.setString(4, field4.getText());
										newStatement.executeUpdate();
										addEmp = "insert " + "into employee " + "values (?)";
										newStatement = connection.prepareStatement(addEmp);
										newStatement.setString(1, field1.getText());
										newStatement.executeUpdate();
									}
									catch (SQLException e) {
										
									}
								}
							}
						});
					}
					else {
						Checkbox remove = new Checkbox("Remove");
						remove.setSize(15, 15);
						remove.setLocation(100, 520);
						displayPane.add(remove);
						
						JButton remove2 = new JButton("Remove");
						remove2.setSize(90,25);
						remove2.setLocation(0, 520);
						displayPane.add(remove2);
						
						JTextField field1 = new JTextField("Publisher Name");
						JTextField field2 = new JTextField("Email");
						JTextField field3 = new JTextField("Bank Account");
						JTextField field4 = new JTextField("Addr. No");
						JTextField field5 = new JTextField("Street");
						JTextField field6 = new JTextField("Apt. No");
						JTextField field7 = new JTextField("City");
						JTextField field8 = new JTextField("State");
						JButton filled = new JButton("Filled");
						
						Scanner sc;
						ArrayList<String> countries = new ArrayList<>();
						int i = 0;
						try {
							sc = new Scanner(new File("C:\\Users\\adenm_x26zerm\\Downloads\\country-keyword-list.csv"));
							sc.useDelimiter("/");
							while (sc.hasNext()) {
								countries.add(sc.next());
							}
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JComboBox<Object> newish = new JComboBox<Object>(countries.toArray());
						
						field1.setSize(300, 50);
						field1.setLocation(50,0);
						field2.setSize(300, 50);
						field2.setLocation(50,50);
						field3.setSize(300, 50);
						field3.setLocation(50,100);
						field4.setSize(300, 50);
						field4.setLocation(50,150);
						field5.setSize(300, 50);
						field5.setLocation(50,200);
						field6.setSize(300, 50);
						field6.setLocation(50,250);
						field7.setSize(300, 50);
						field7.setLocation(50,300);
						field8.setSize(300, 50);
						field8.setLocation(50,350);
						newish.setSize(300, 50);
						newish.setLocation(50, 450);
						
						filled.setSize(300,50);
						filled.setLocation(150,500);
						displayPane.add(field1);
						displayPane.add(field2);
						displayPane.add(field3);
						displayPane.add(field4);
						displayPane.add(field5);
						displayPane.add(field6);
						displayPane.add(field7);
						displayPane.add(field8);
						displayPane.add(filled);
						displayPane.add(newish);
						displayPane.revalidate();
						displayPane.repaint();
					}
				}
			}
		});
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, displayPane);
		split.setDividerLocation(150);
		frame.getContentPane().add(split);
	}
	
	@SuppressWarnings("unused")
	public void booksResultPage(ResultSet resultSet, ArrayList<String> listNew) {
		try {
			if (resultSet.next()) {
				ArrayList<String[]> resultset = new ArrayList<String[]>();
				int i=0;
	
				do {
					String[] temp = {"a","b","c","d","e","f"};
					temp[0] = String.valueOf(resultSet.getObject(1));
					temp[1] = (String) resultSet.getObject(2);
					temp[2] = (String) resultSet.getObject(3);
					temp[3] = (String) resultSet.getObject(4);
					temp[4] = (String) resultSet.getObject(5);
					temp[5] = String.valueOf((resultSet.getObject(6)));
					resultset.add(i, temp);
					i++;
				} while(resultSet.next());
				
				JButton[] list = new JButton[i+1];
				JScrollPane listScrollPane = new JScrollPane();
				
				if (listNew == null || listNew.isEmpty()) {
					listNew = new ArrayList<String>();
				}
				
				JButton basket;
				if (orders == null) {
					basket = new JButton("Basket: 0");
				}
				else {
					basket = new JButton("Basket: "+listNew.size());
				}
				
				basket.setSize(100, 50);
				basket.setLocation(225, 500);

				for (int h = 0; h < i; h++) {
					final int k = h;
					list[h] = new JButton();
					list[h].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							String temp = basket.getText();
							int temp2 = Integer.parseInt(temp.split(":")[1].split(" ")[1]);
							temp2++;
							basket.setText("Basket: "+temp2);
							orders.add(resultset.get(k)[0]);
						}
					});
					list[h].setSize(600, 100);
					String newLine = resultset.get(h)[1] + "\n AUTHOR: " + resultset.get(h)[2]
							+ " " + resultset.get(h)[3] + "\n PRICE: " + resultset.get(h)[5];
					list[h].setText("<html>" + newLine.replaceAll("\\n", "<br>") + "</html>");
					listScrollPane.add(list[h]);
				}
				listScrollPane.setSize(600,400);
				listScrollPane.setLocation(0,0);
				special.removeAll();
				special.add(listScrollPane);
				special.add(basket);
				
				orders.addAll(listNew);
				
				basket.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						final ArrayList<String> newOrders = new ArrayList<>(orders);
						HashMap<String, Integer> map = new HashMap<>();
						if (newOrders != null && !newOrders.isEmpty()) {
							for (String a: newOrders) {
								if (map.containsKey(a)) {
									map.replace(a, map.get(a), map.get(a)+1);
								}
								else {
									map.put(a, 1);
								}
							}
							Basket(map);
						}
					}
				});
				
				JButton back = new JButton("Back");
				back.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						special.removeAll();
						searchPage(orders);
					}
				});
				back.setSize(100, 50);
				back.setLocation(500, 500);
				special.add(back);
				
				special.revalidate();
				special.repaint();
			}
			else {
				return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void Basket(HashMap<String, Integer> map) {
		frame.setTitle("Checkout");
		// TODO Auto-generated method stub
		special.removeAll();
		
		total = 0;
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sunflower", "postgres", "password"); 
				Statement statement = connection.createStatement();) {
			for (String searchBar : map.keySet()) {
				String temp = "select * " + "from book " + "where ISBN=?";
				PreparedStatement newStatement = connection.prepareStatement(temp);
				newStatement.setObject(1, Long.parseLong(searchBar), java.sql.Types.NUMERIC);
				ResultSet resultSet = newStatement.executeQuery();
				
				ArrayList<String[]> resultset = new ArrayList<String[]>();
				String[] temp2 = {"a", "b"};
				resultset.add(0,temp2);
				int i=0;
	
				while(resultSet.next()) {
					String[] temp1 = {"a","b","c","d","e","f"};
					temp1[0] = String.valueOf(resultSet.getObject(1));
					temp1[1] = (String) resultSet.getObject(2);
					temp1[2] = (String) resultSet.getObject(3);
					temp1[3] = (String) resultSet.getObject(4);
					temp1[4] = (String) resultSet.getObject(5);
					temp1[5] = String.valueOf((resultSet.getObject(6)));
					total += (Float) resultSet.getFloat(6) * map.get(searchBar);
					resultset.add(i, temp1);
					i++;
				}
				
				//create unclickable buttons for the order system...
				JButton[] list = new JButton[i+1];
				JScrollPane listScrollPane = new JScrollPane();
				
				JButton total1 = new JButton("Total= "+total);
				total1.setSize(200, 50);
				total1.setLocation(200, 500);
				special.add(total1);
				
				for (int h = 0; h < i; h++) {
					final int k = h;
					list[h] = new JButton();
					list[h].setSize(600, 100);
					String newLine = resultset.get(h)[1] + "\n AUTHOR: " + resultset.get(h)[2]
							+ " " + resultset.get(h)[3] + "\n PRICE: " + resultset.get(h)[5] +
							"\n NUMBER: " + map.get(resultset.get(h)[0]);
					list[h].setText("<html>" + newLine.replaceAll("\\n", "<br>") + "</html>");
					listScrollPane.add(list[h]);
					list[h].addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							// TODO Auto-generated method stub
							total -= Float.valueOf(resultset.get(k)[5]);
							total1.setText("Total= "+total);
							if (map.get(resultset.get(k)[0])!=null && map.get(resultset.get(k)[0])>1) {
								map.put(resultset.get(k)[0], map.get(resultset.get(k)[0])-1);
								String newLine = resultset.get(k)[1] + "\n AUTHOR: " + resultset.get(k)[2]
										+ " " + resultset.get(k)[3] + "\n PRICE: " + resultset.get(k)[5] +
										"\n NUMBER: " + map.get(resultset.get(k)[0]);//null for some reason
								list[k].setText("<html>" + newLine.replaceAll("\\n", "<br>") + "</html>");
							}
							else if (map.get(resultset.get(k)[0])==null) {
								return;
							}
							else {
								listScrollPane.remove(list[k]);
								listScrollPane.revalidate();
								listScrollPane.repaint();
							}
							int j=-1;
							for (int i=0; i < resultset.size(); i++) {
								if (resultset.get(i)[0].equalsIgnoreCase(resultset.get(k)[0].toString())) {
									j=i;
									break;
								}
							}
							if (j==-1) {
								return;
							}
							else {
								resultset.remove(j);
							}
						}
					});
				}
				
				JButton back = new JButton("Back");
				back.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						special.removeAll();
						searchPage(orders);
					}
				});
				back.setSize(100, 50);
				back.setLocation(500, 500);
				special.add(back);
				
				listScrollPane.setSize(600,400);
				listScrollPane.setLocation(0,0);
				
				special.add(listScrollPane);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		special.revalidate();
		special.repaint();
	}
}
