package cherkas.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.hibernate.Session;

import cherkas.model.Account;
import cherkas.util.HibernateUtil;

public class SignUI extends JFrame {
	HibernateUtil hUtil = new HibernateUtil();
	private JTextField regEmailTF;
	private JTextField regPassTF;
	private JTextField logEmailTF;
	private JTextField logPassTF;

	public SignUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Mail Service");
		setLocation(550, 200);
		setSize(300, 250);
		setResizable(false);
		getContentPane().setLayout(null);
		setVisible(true);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 274, 200);
		getContentPane().add(tabbedPane);

		JPanel loginPanel = new JPanel();
		loginPanel.setLayout(null);
		tabbedPane.addTab("Sign in", null, loginPanel, null);

		logEmailTF = new JTextField();
		logEmailTF.setToolTipText("");
		logEmailTF.setColumns(10);
		logEmailTF.setBounds(63, 12, 204, 32);
		loginPanel.add(logEmailTF);

		logPassTF = new JTextField();
		logPassTF.setColumns(10);
		logPassTF.setBounds(63, 68, 204, 32);
		loginPanel.add(logPassTF);

		JButton btnSign = new JButton("Sign in");
		btnSign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signIn();
			}
		});
		btnSign.setBounds(10, 117, 249, 39);
		loginPanel.add(btnSign);

		JLabel label_2 = new JLabel("pass");
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_2.setBounds(10, 64, 43, 39);
		loginPanel.add(label_2);

		JLabel label_3 = new JLabel("email");
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_3.setBounds(10, 11, 43, 32);
		loginPanel.add(label_3);

		JPanel registerPanel = new JPanel();
		registerPanel.setLayout(null);
		tabbedPane.addTab("Register", null, registerPanel, null);

		JLabel label = new JLabel("email");
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setBounds(10, 11, 43, 32);
		registerPanel.add(label);

		JLabel label_1 = new JLabel("pass");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_1.setBounds(10, 64, 43, 39);
		registerPanel.add(label_1);

		regEmailTF = new JTextField();
		regEmailTF.setColumns(10);
		regEmailTF.setBounds(63, 12, 204, 32);
		registerPanel.add(regEmailTF);

		regPassTF = new JTextField();
		regPassTF.setColumns(10);
		regPassTF.setBounds(63, 68, 204, 32);
		registerPanel.add(regPassTF);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
		btnRegister.setBounds(10, 117, 249, 39);
		registerPanel.add(btnRegister);
	}

	private void register() {
		String inputEmail = regEmailTF.getText();
		String inputPass = regPassTF.getText();

		String sql = "SELECT * FROM accounts WHERE email=:mail";
		Session session1 = hUtil.getSessionFactory().openSession();
		session1.getTransaction().begin();

		Query query = session1.createNativeQuery(sql, Account.class);
		query.setParameter("mail", inputEmail);
		Account accFromDB = null;
		try {
			accFromDB = (Account) query.getSingleResult();
			JOptionPane.showMessageDialog(null, "This email is already used!\nChoose another and try again!");
		} catch (Exception e) {
			session1.save(new Account(inputEmail, inputPass));
			JOptionPane.showMessageDialog(null, "Registration successful!");
		}
		
		session1.getTransaction().commit();
		session1.close();

		regEmailTF.setText("");
		regPassTF.setText("");

	}

	private void signIn() {
		String inputMail = logEmailTF.getText();
		String inputPass = logPassTF.getText();

		String sql = "SELECT * FROM accounts WHERE email=:mail AND password=:pass";
		Session session = hUtil.getSessionFactory().openSession();
		session.getTransaction().begin();

		Query query = session.createNativeQuery(sql, Account.class);
		query.setParameter("mail", inputMail);
		query.setParameter("pass", inputPass);
		Account accFromDB = null;
		try {
			accFromDB = (Account) query.getSingleResult();
		} catch (Exception e) {

		}
		session.getTransaction().commit();
		session.close();

		logEmailTF.setText("");
		logPassTF.setText("");

		if (accFromDB != null) {
			JOptionPane.showMessageDialog(null, "Login successful!");
			dispose();
			LettersUI lettersUI = new LettersUI(inputMail);
		} else {
			JOptionPane.showMessageDialog(null, "Account was not found!");
		}
	}
}
