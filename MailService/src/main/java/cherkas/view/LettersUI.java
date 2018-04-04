package cherkas.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;

import org.hibernate.Session;

import cherkas.model.Letter;
import cherkas.util.HibernateUtil;

public class LettersUI extends JFrame {

	CardLayout cl = new CardLayout(0, 0);
	JPanel panelCont = new JPanel();
	JTextPane textTP = new JTextPane();
	private JTextField senderTF;
	private JTextField receiverTF;
	private JTextField themeTF;
	HibernateUtil hUtil = new HibernateUtil();
	private JTable table;

	public LettersUI(final String email) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Post");
		setLocation(285, 120);
		setSize(800, 530);
		setResizable(false);
		getContentPane().setLayout(null);
		setVisible(true);

		JPanel welcomePanel = new JPanel();
		welcomePanel.setBackground(Color.ORANGE);
		welcomePanel.setBounds(0, 0, 794, 48);
		getContentPane().add(welcomePanel);

		JLabel welcomeLbl = new JLabel("Welcome");
		welcomeLbl.setForeground(Color.BLACK);
		welcomeLbl.setFont(new Font("Tahoma", Font.PLAIN, 25));
		welcomePanel.add(welcomeLbl);

		JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setBounds(0, 48, 119, 454);
		getContentPane().add(panel);

		JLabel lblNewLabel = new JLabel("Send Mail");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(panelCont, "1");
			}
		});
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("         ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblNewLabel_1);

		JLabel lblCheckMails = new JLabel("Check Mails");
		lblCheckMails.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(panelCont, "2");
			}
		});
		lblCheckMails.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblCheckMails);
		
		JLabel label = new JLabel("         ");
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(label);
		
		JLabel lblLogOut = new JLabel("Log out");
		lblLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				SignUI signUI = new SignUI();
			}
		});
		lblLogOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		panel.add(lblLogOut);

		panelCont.setBounds(119, 48, 675, 454);
		getContentPane().add(panelCont);
		panelCont.setLayout(cl);

		JPanel sendPanel = new JPanel();
		sendPanel.setBackground(Color.WHITE);
		panelCont.add(sendPanel, "1");
		sendPanel.setLayout(null);

		JLabel senderLbl = new JLabel("Sender:");
		senderLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		senderLbl.setBounds(10, 11, 76, 24);
		sendPanel.add(senderLbl);

		JLabel receiverLbl = new JLabel("Receiver:");
		receiverLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		receiverLbl.setBounds(10, 46, 76, 24);
		sendPanel.add(receiverLbl);

		senderTF = new JTextField();
		senderTF.setFont(new Font("Tahoma", Font.PLAIN, 15));
		senderTF.setText(email);
		senderTF.setBounds(96, 11, 556, 24);
		sendPanel.add(senderTF);

		JLabel themeLbl = new JLabel("Theme:");
		themeLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		themeLbl.setBounds(10, 81, 76, 24);
		sendPanel.add(themeLbl);

		JButton sendBtn = new JButton("Send mail");
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMail();
			}
		});
		sendBtn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		sendBtn.setBounds(10, 408, 642, 35);
		sendPanel.add(sendBtn);

		JLabel textLbl = new JLabel("Text:");
		textLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		textLbl.setBounds(308, 116, 46, 14);
		sendPanel.add(textLbl);

		textTP.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textTP.setBounds(10, 141, 655, 256);
		textTP.setBorder(UIManager.getBorder("TextField.border"));
		sendPanel.add(textTP);

		receiverTF = new JTextField();
		receiverTF.setFont(new Font("Tahoma", Font.PLAIN, 15));
		receiverTF.setBounds(96, 46, 556, 24);
		sendPanel.add(receiverTF);

		themeTF = new JTextField();
		themeTF.setFont(new Font("Tahoma", Font.PLAIN, 15));
		themeTF.setBounds(96, 81, 556, 24);
		sendPanel.add(themeTF);

		JPanel lettersPanel = new JPanel();
		lettersPanel.setBackground(Color.WHITE);
		panelCont.add(lettersPanel, "2");
		lettersPanel.setLayout(null);

		JButton btnCheckMails = new JButton("View mails");
		btnCheckMails.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Table.createTable(checkMails(email));
			}
		});
		btnCheckMails.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCheckMails.setBounds(208, 197, 271, 43);
		lettersPanel.add(btnCheckMails);
	}

	private void sendMail() {
		String senderEmail = senderTF.getText();
		String receiverEmail = receiverTF.getText();
		String mailTheme = themeTF.getText();
		String mailText = textTP.getText();

		Session session1 = hUtil.getSessionFactory().openSession();
		session1.getTransaction().begin();
		session1.save(new Letter(senderEmail, receiverEmail, mailTheme, mailText));
		session1.getTransaction().commit();
		session1.close();

		receiverTF.setText("");
		themeTF.setText("");
		textTP.setText("");
		JOptionPane.showMessageDialog(null, "Mail sended!");
	}
	
	private String[][] checkMails(String email){
		
		String sql = "SELECT * FROM letters WHERE receiver=:mail";
		Session session = hUtil.getSessionFactory().openSession();
		session.getTransaction().begin();
		Query query = session.createNativeQuery(sql, Letter.class);
		query.setParameter("mail", email);
		List<Letter> letters = query.getResultList();
		session.getTransaction().commit();
		session.close();
		String[][] data = new String[letters.size()][3];
		
		for (int i = 0; i < letters.size(); i++) {
			
		    for (int j = 0; j < 3; j++) {
		    	
		    	if(j == 0) {
		    		data[i][j] = letters.get(i).getSender();
		    	}
		    	if(j == 1) {
		    		data[i][j] = letters.get(i).getTheme();
		    	}
		    	if(j == 2) {
		    		data[i][j] = letters.get(i).getText();
		    	}
		    }
		}
		return data;
	}
}
