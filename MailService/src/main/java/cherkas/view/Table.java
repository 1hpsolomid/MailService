package cherkas.view;
import java.awt.Dimension;
 
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
 
public class Table extends JFrame {
	public Table() {
	}
 
     public static void createTable(String[][] data) {
          JFrame frame = new JFrame("Test frame");
          frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
          String[] columnNames = {
                    "Sender",
                    "Theme",
                    "Text",
          };
           
          JTable table = new JTable(data, columnNames);
           
          JScrollPane scrollPane = new JScrollPane(table);
           
          frame.getContentPane().add(scrollPane);
          frame.setPreferredSize(new Dimension(450, 200));
          frame.pack();
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
     }
}
