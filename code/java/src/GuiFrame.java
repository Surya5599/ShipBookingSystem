

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.JPanel;

public class GuiFrame extends JFrame implements ActionListener{


    private JPanel mainPanel;
    private JPanel panelHolder;
    private JPanel mainMenu;
    private JButton addDataButton;
    private JButton bookACruiseButton;
    private JButton availableSeatsButton;
    private JPanel Holder;
    private JPanel addShip;
    private JTextField ship2;
    private JTextField ship3;
    private JTextField ship4;
    private JTextField ship5;
    private JPanel addCaptain;
    private JTextField captain2;
    private JTextField captain3;
    private JPanel addCruise;
    private JComboBox addMenuCombo;
    private JPanel addMenu;
    private JTextField cruise2;
    private JTextField cruise3;
    private JTextField cruise4;
    private JComboBox dataMenuCombo;
    private JPanel ContentHolder;
    private JTable table1;
    private JPanel dataMenu;
    private JButton repairsInfoButton;
    private JButton passengersInfoButton;
    private JButton allDataButton;
    private JButton quitButton;
    private JPanel bookMenu;
    private JPanel seatsMenu;
    private JPanel repairMenu;
    private JPanel passengerMenu;
    private JComboBox bookMenuCombo;
    private JTextField bookField1;
    private JTextField bookField2;
    private JButton bookButton;
    private JComboBox cruiseNumBox;
    private JComboBox dateBox;
    private JTable shipTable;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton addButton;
    private JTextField cruise7;
    private JTextField cruise8;
    private JPanel topBar;
    private JPanel dt;
    private JSpinner Dt_date;
    private JSpinner Dt_time;
    private JLabel dep_time;
    private JPanel at;
    private JLabel arr_time;
    private JSpinner at_date;
    private JSpinner at_time;
    private JPanel mainMenuPanel;
    private CardLayout cl;
    DBproject sq;

    public GuiFrame(DBproject esql) throws SQLException, IOException {

        this.sq = esql;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.cl = (CardLayout)panelHolder.getLayout();
        this.add(mainPanel);
        JMenuBar mb = createMenuBar();
        this.setJMenuBar(mb);
        setSize(500, 300);
        cl.show(panelHolder, "mainMenu");
        this.pack();

        addMenuCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String item = (String) e.getItem();
                    showTable(item);
                }
            }
        });

        dataMenuCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String item = (String) e.getItem();
                    try {
                        createTable(item);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        initBookingComboBox();
        createUIComponents();

        addDataButton.addActionListener(this);
        bookACruiseButton.addActionListener(this);
        availableSeatsButton.addActionListener(this);
        repairsInfoButton.addActionListener(this);
        passengersInfoButton.addActionListener(this);
        allDataButton.addActionListener(this);
        quitButton.addActionListener(this);

    }

    private void initBookingComboBox() {
        bookMenuCombo.setEditable(true);
        String sql = "SELECT cnum FROM Cruise";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            // add all cnums to comboBox
            for (List<String> ls: rs)
                bookMenuCombo.addItem(ls.get(0));
        } catch (SQLException throwables) {
            System.out.println("SQL error in initBookingComboBox()");
            throwables.printStackTrace();
        }
    }

    public JMenuBar createMenuBar(){
        JMenuBar jb = new JMenuBar();
        JMenu menu2;
        JButton menu1;
        JMenuItem i1, i2, i3, i4, i5, i6, i7;
        menu1 =new JButton("Home");
        menu1.setOpaque(true);
        menu1.setContentAreaFilled(false);
        menu1.setBorderPainted(false);
        menu1.setFocusable(false);
        menu2=new JMenu("File");
        i1=new JMenuItem("Add Data");
        i2=new JMenuItem("Book a Cruise");
        i3=new JMenuItem("Available Seats");
        i4=new JMenuItem("Repairs Info");
        i5=new JMenuItem("Passengers Info");
        i6=new JMenuItem("View Data");
        i7=new JMenuItem("Quit");

        i1.addActionListener(this);
        i2.addActionListener(this);
        i3.addActionListener(this);
        i4.addActionListener(this);
        i5.addActionListener(this);
        i6.addActionListener(this);
        i7.addActionListener(this);
        menu1.addActionListener(this);
        addButton.addActionListener(this);
        bookButton.addActionListener(this);

        addButton.setActionCommand("addButton");
        bookButton.setActionCommand("bookButton");

        menu2.add(i1);
        menu2.add(i2);
        menu2.add(i3);
        menu2.add(i4);
        menu2.add(i5);
        menu2.add(i6);
        menu2.add(i7);
        jb.add(menu1);
        jb.add(menu2);

        return jb;
    }

    private void showTable(String item) {
        CardLayout cl = (CardLayout)Holder.getLayout();
        if(item.equals("Ship")) {
            cl.show(Holder, "addShip");
        }
        if(item.equals("Captain")){
            cl.show(Holder, "addCaptain");
        }
        if(item.equals("Cruise")){
            cl.show(Holder, "addCruise");
        }
    }

    public void createTable(String item) throws SQLException {
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        table1.setEnabled(false);
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        String tableName = "captain";
        if(item.equals("Captains")) {
            tableName = "captain";
        }
        if(item.equals("Cruises")) {
            tableName = "cruise";
        }
        if(item.equals("Cruise Info")) {
            tableName = "cruiseinfo";
        }
        if(item.equals("Customers")){
            tableName = "customer";
        }
        if(item.equals("Repairs")){
            tableName = "repairs";
        }
        if(item.equals("Reservations")){
            tableName = "reservation";
        }
        if(item.equals("Schedules")){
            tableName = "schedule";
        }
        if(item.equals("Ships")){
            tableName = "ship";
        }
        if(item.equals("Technicians")){
            tableName = "technician";
        }

        String sql = "Select * from " + tableName;
        ResultSet result = sq.executeQueryAndReturnResultWithColumn(sql);
        ResultSetMetaData rsmd = result.getMetaData();
        int columnCount = rsmd.getColumnCount();
        List<String> columnNames = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            tableModel.addColumn(rsmd.getColumnName(i));
            columnNames.add(rsmd.getColumnName(i));
        }
        while (result.next()) {
            Vector<String> row = new Vector<>();
            for(int i = 1; i <= columnCount; i++){
                row.add(result.getString(rsmd.getColumnName(i)));
            }
            tableModel.addRow(row);
        }
    }


    public void actionPerformed(ActionEvent e) {
        String item = e.getActionCommand();
        if(item.equals("addButton")) {
            int index = addMenuCombo.getSelectedIndex();
            if (index == 0) {
                if (ship2.getText().isEmpty()
                        || ship3.getText().isEmpty()
                        || ship4.getText().isEmpty()
                        || ship5.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Complete All Fields.");
                } else {
                    if (!isNumeric(ship4.getText()) || !isNumeric(ship5.getText())) {
                        JOptionPane.showMessageDialog(null, "Please Make Sure That Age and Seats are Integer.");
                    } else {
                        String sql = "SELECT MAX(id) FROM ship;";
                        int id = 0;
                        try {
                            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
                            int result = Integer.parseInt(rs.get(0).get(0));
                            id = result + 1;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        sql = "INSERT INTO ship (id, make, model, age, seats) VALUES (" + id + ", '" + ship2.getText() + "', '" + ship3.getText() + "', " + ship4.getText() + ", " + ship5.getText() + ");";
                        try {
                            sq.executeUpdate(sql);
                            JOptionPane.showMessageDialog(null, "Success! Ship Added.");
                            ship2.setText(null);
                            ship3.setText(null);
                            ship4.setText(null);
                            ship5.setText(null);

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        }
                        System.out.println(sql);
                    }
                }
            }
            if (index == 1) {
                if (captain2.getText().isEmpty()
                        || captain3.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Complete All Fields");
                } else {
                    String sql = "SELECT MAX(id) FROM captain;";
                    int id = 0;
                    try {
                        List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
                        int result = Integer.parseInt(rs.get(0).get(0));
                        id = result + 1;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    sql = "INSERT INTO captain (id, fullname, nationality) VALUES (" + id + ", '" + captain2.getText() + "', '" + captain3.getText() + "');";
                    try {
                        sq.executeUpdate(sql);
                        JOptionPane.showMessageDialog(null, "Success! Captain Added.");
                        captain2.setText(null);
                        captain3.setText(null);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                    }
                    System.out.println(sql);
                }
            }
            if (index == 2) {
                if (cruise2.getText().isEmpty()
                        || cruise3.getText().isEmpty()
                        || cruise4.getText().isEmpty()
                        || cruise7.getText().isEmpty()
                        || cruise8.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please Complete All Fields");
                } else {
                    if (!isNumeric(cruise2.getText()) || !isNumeric(cruise3.getText()) || !isNumeric(cruise4.getText())) {
                        JOptionPane.showMessageDialog(null, "Please Make Sure That Cost, Tickets Sold and Stops are Integer.");
                    } else {
                        String sql = "SELECT MAX(cnum) FROM cruise;";
                        int id = 0;
                        try {
                            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
                            int result = Integer.parseInt(rs.get(0).get(0));
                            id = result + 1;
                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                        sql = "INSERT INTO cruise (cnum, cost, num_sold, num_stops, actual_departure_date, actual_arrival_date, arrival_port, departure_port) VALUES ("
                                + id + ", "
                                + cruise2.getText() + ", "
                                + cruise3.getText() + ", "
                                + cruise4.getText() + ", '"
                                + getString(dt) + "', '"
                                + getString(at) + "', '"
                                + cruise7.getText() + "', '"
                                + cruise8.getText() + "');";
                        System.out.println(sql);
                        try {
                            sq.executeUpdate(sql);
                            JOptionPane.showMessageDialog(null, "Success! Cruise Added.");
                            cruise2.setText(null);
                            cruise3.setText(null);
                            cruise4.setText(null);
                            cruise7.setText(null);
                            cruise8.setText(null);

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        }
                    }
                }
            }
        } // end addButton
        else if (item.equals("bookButton")) {
            // ensure all fields are entered
            System.out.println(bookMenuCombo.getSelectedItem());
            if (bookField1.getText().isEmpty()
                    || bookField2.getText().isEmpty()
                    || bookMenuCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Please Complete All Fields");
                return;
            }
            int n_reserved, n_ship_seats, cid, ccid, rnum;
            char status;

            // set cid (cruise id)
            cid = Integer.parseInt(bookMenuCombo.getSelectedItem().toString());;

            // get ccid (customer id)
            String sql = "SELECT c.id FROM Customer c WHERE c.fname='" + bookField1.getText() + "' AND c.lname='" + bookField2.getText() + "';";
            try {
                // TODO - send message dialog when customer not found in DB
                List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
                ccid = Integer.parseInt(rs.get(0).get(0));
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(null, "Could not find customer");
                throwables.printStackTrace();
                return;
            }

            // determine reservation status (RESERVED | WAITLISTED)
            sql = "SELECT num_sold FROM Cruise WHERE cnum="+cid+" UNION SELECT s.seats FROM Ship s WHERE s.id = (SELECT c.ship_id FROM CruiseInfo c WHERE c.cruise_id="+cid+");";
            try {
                // TODO - send message dialog when customer not found in DB
                List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
                n_reserved = Integer.parseInt(rs.get(0).get(0));
                n_ship_seats = Integer.parseInt(rs.get(1).get(0));
                if (n_reserved >= n_ship_seats)
                    status = 'W';
                else
                    status = 'R';
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(null, "Could not retrieve Cruise info (internal error)");
                throwables.printStackTrace();
                return;
            }

            // get rnum
            sql = "SELECT MAX(rnum) FROM Reservation;";
            try {
                List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
                rnum = Integer.parseInt(rs.get(0).get(0)) + 1;
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(null, "Could not retrieve reservation info (internal error)");
                throwables.printStackTrace();
                return;
            }

            // INSERT reservation
            sql = "INSERT INTO Reservation (rnum, cid, ccid, status) VALUES ("
                    + rnum + ","
                    + cid + ","
                    + ccid + ",'"
                    + status + "');";
            try {
                sq.executeUpdate(sql);
                if (status == 'R')
                    JOptionPane.showMessageDialog(null, "Reservation successfully booked");
                else
                    JOptionPane.showMessageDialog(null, "Your were placed on the waitlist");
                bookField1.setText(null);
                bookField2.setText(null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error. Please try again.");
                return;
            }

            // update num_sold
            sql = "UPDATE Cruise SET num_sold=num_sold+1 WHERE cnum="+cid+";";
            try {
                sq.executeUpdate(sql);
            } catch (SQLException throwables) {
                JOptionPane.showMessageDialog(null, "Could update Cruise info (internal error)");
                throwables.printStackTrace();
                return;
            }
            System.out.println(sql);
        } // end bookButton

        if(item.equals("Home")){
            cl.show(panelHolder, "mainMenu");
        }if(item.equals("Add Data")){
            cl.show(panelHolder, "addMenu");
        }if(item.equals("Book a Cruise")){
            cl.show(panelHolder, "bookMenu");
        }if(item.equals("Available Seats")){
            cl.show(panelHolder, "seatsMenu");
        }if(item.equals("Repairs Info")){
            cl.show(panelHolder, "repairMenu");
        }if(item.equals("Passengers Info")){
            cl.show(panelHolder, "passengerMenu");
        }if(item.equals("View Data")){
            cl.show(panelHolder, "dataMenu");
        }if(item.equals("Quit")){
            this.dispose();
        }
    }

    private String getString(JPanel pan) {
        DateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat t1 = new SimpleDateFormat("HH:mm");
        Date date = (Date)((JSpinner)pan.getComponent(0)).getValue();
        String d = d1.format(date);
        date = (Date)((JSpinner)pan.getComponent(1)).getValue();
        String t = t1.format(date);
        return d + " " + t;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private void createUIComponents() {
        Dt_date = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        Dt_time = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
        Dt_date.setEditor(new JSpinner.DateEditor(Dt_date, "dd/MM/yy"));
        Dt_time.setEditor(new JSpinner.DateEditor(Dt_time, "HH:mm"));

        at_date = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        at_time = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
        at_date.setEditor(new JSpinner.DateEditor(at_date, "dd/MM/yy"));
        at_time.setEditor(new JSpinner.DateEditor(at_date, "HH:mm"));

    }
}





