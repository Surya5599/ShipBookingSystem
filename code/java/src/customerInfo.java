import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class customerInfo extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel customerMenu;
    private JSpinner dob;
    private JLabel cust1;
    private JLabel cust2;
    private JComboBox cust3;
    private JTextField cust4;
    private JTextField cust5;
    private JTextField cust7;
    private JTextField cust8;
    private JPanel cust6;
    DBproject sq;

    public customerInfo(String f, String l, DBproject psql) {
        sq = psql;
        cust1.setText(f);
        cust2.setText(l);
        cust7.setColumns(10);
        cust8.setColumns(5);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    //when the submit button is pressed
    private void onOK() {
        // add your code here
        if (cust1.getText().isEmpty()
                || cust2.getText().isEmpty()
                || (String) cust3.getSelectedItem() == null
                || cust4.getText().isEmpty()
                || cust7.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Complete All Fields");
            return;
        } else {
            int id;
            String sql = "SELECT MAX(id) FROM customer;";
            try {
                List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
                int result = Integer.parseInt(rs.get(0).get(0));
                id = result + 1;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return;
            }
            String fname = cust1.getText();
            String lname = cust2.getText();
            char gender = ((String) cust3.getSelectedItem()).charAt(0);
            String address = cust4.getText();
            String bday = getString(cust6);
            String phone = cust7.getText();
            String zip = cust8.getText();
            if(phone.length() != 10 || !isNumeric(phone)){
                JOptionPane.showMessageDialog(null, "Please Make Sure The Phone Number has 10 numbers and no characters");
                return;
            }
            if(zip.length() != 5 || !isNumeric(zip)){
                JOptionPane.showMessageDialog(null, "Please Make Sure The Zip Code has 5 numbers and no characters");
                return;
            }
            sql = "INSERT INTO customer (id, fname, lname, gtype, dob, address, phone, zipcode) VALUES ("
                    + id + ", '"
                    + fname + "', '"
                    + lname + "','"
                    + gender + "','"
                    + bday + "', '"
                    + address + "', '"
                    + phone + "', '"
                    + zip + "');";
            System.out.println(sql);
            try {
                sq.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Success! Customer Added.");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error adding customer info. Please try again.");
                return;
            }
        }
        dispose();
    }

    //check if string is numeric
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //change date to string
    private String getString(JPanel pan) {
        DateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date) ((JSpinner) pan.getComponent(0)).getValue();
        String d = d1.format(date);
        return d;
    }

    //when they press cancel button
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    //custom UI set up
    private void createUIComponents() {
        dob = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        //seatTime = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
        dob.setEditor(new JSpinner.DateEditor(dob, "dd/MM/yy"));
        dob.setEditor(new JSpinner.DateEditor(dob, "dd/MM/yy"));
    }

}
