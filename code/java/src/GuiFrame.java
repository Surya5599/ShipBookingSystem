

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.JPanel;

public class GuiFrame extends JFrame implements ActionListener {


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
    private JComboBox<String> bookMenuCombo;
    private JTextField bookField1;
    private JTextField bookField2;
    private JButton bookButton;
    private JComboBox<String> seatCruiseBox;
    private JTable shipTable;
    private JComboBox<String> passengerBox1;
    private JComboBox<String> passengerBox2;
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
    private JLabel passengerNumField;
    private JLabel seatNumField;
    private JButton seatButton;
    private JSpinner seatDate;
    private JLabel seatReservedField;
    private JLabel costInfo;
    private JLabel soldInfo;
    private JLabel stopInfo;
    private JLabel dDate;
    private JLabel aDate;
    private JLabel aPort;
    private JLabel dPort;
    private JComboBox addCruiseShipBox;
    private JComboBox addCruiseCaptainBox;
    private JPanel seatPanel;
    private JLabel seatInfo;
    private JLabel seatInfo2;
    private JTextPane textPane1;
    private final CardLayout cl;
    DBproject sq;

    public GuiFrame(DBproject esql) {

        this.sq = esql;
        $$$setupUI$$$();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.cl = (CardLayout) panelHolder.getLayout();
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

        bookMenuCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    int item = Integer.parseInt((String) e.getItem());
                    try {
                        fillCruiseInfo(item);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        ItemListener passengerBoxListener = new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED)
                    createNumPassengers();
            }
        };
        passengerBox1.addItemListener(passengerBoxListener);
        passengerBox2.addItemListener(passengerBoxListener);

        initAddCruiseComboBoxes();
        initBookingComboBox();
        initPassengerComboBox();
        initSeatComboBox();
        createUIComponents();

        addDataButton.addActionListener(this);
        bookACruiseButton.addActionListener(this);
        availableSeatsButton.addActionListener(this);
        repairsInfoButton.addActionListener(this);
        passengersInfoButton.addActionListener(this);
        allDataButton.addActionListener(this);
        quitButton.addActionListener(this);


        seatButton.addActionListener(this);
        seatButton.setActionCommand("seatButton");
        bookButton.addActionListener(this);
        bookButton.setActionCommand("bookButton");


    }

    /*
     * pulls relevant date for the combo boxes
     * in the add cruise menu
     */
    private void initAddCruiseComboBoxes() {
        addCruiseShipBox.removeAllItems();
        addCruiseCaptainBox.removeAllItems();
        String sql = "SELECT id FROM Ship ORDER BY id ASC";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            // add all cnums to comboBox
            for (List<String> ls : rs)
                addCruiseShipBox.addItem(ls.get(0));
        } catch (SQLException throwables) {
//            System.out.println("SQL error in initAddCruiseComboBoxes()");
            throwables.printStackTrace();
        }
        sql = "SELECT fullname FROM Captain ORDER BY fullname ASC";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            // add all names to comboBox
            for (List<String> ls : rs)
                addCruiseCaptainBox.addItem(ls.get(0));
        } catch (SQLException throwables) {
//            System.out.println("SQL error in initAddCruiseComboBoxes()");
            throwables.printStackTrace();
        }
    }

    /*
     * populates cruise number combo box for
     * the booking menu
     */
    private void initBookingComboBox() {
        bookMenuCombo.removeAllItems();
        //bookMenuCombo.setEditable(true);
        String sql = "SELECT cnum FROM Cruise ORDER BY cnum ASC";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            // add all cnums to comboBox
            for (List<String> ls : rs)
                bookMenuCombo.addItem(ls.get(0));
            fillCruiseInfo(0);
        } catch (SQLException throwables) {
//            System.out.println("SQL error in initBookingComboBox()");
            throwables.printStackTrace();
        }
    }

    /*
     * fills relevant cruise data for the booking menu
     */
    private void fillCruiseInfo(int x) throws SQLException {
        String sql = "SELECT * FROM Cruise where cnum = " + x;
        List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
        List<String> first = rs.get(0);
        costInfo.setText(String.valueOf(first.get(1)));
        soldInfo.setText(String.valueOf(first.get(2)));
        stopInfo.setText(String.valueOf(first.get(3)));
        dDate.setText(String.valueOf(first.get(4)));
        aDate.setText(String.valueOf(first.get(5)));
        aPort.setText(String.valueOf(first.get(6)));
        dPort.setText(String.valueOf(first.get(7)));
    }

    /*
     * retrieves cruise numbers for populating
     * gui in num passenger menu
     */
    private void initPassengerComboBox() {
        passengerBox1.removeAllItems();
        //passengerBox1.setEditable(true);

        String sql = "SELECT cnum FROM Cruise ORDER BY cnum ASC";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            // add all cnums to comboBox
            for (List<String> ls : rs)
                passengerBox1.addItem(ls.get(0));
        } catch (SQLException throwables) {
//            System.out.println("SQL error in initPassengerComboBox()");
            throwables.printStackTrace();
        }

        passengerBox2.addItem("Reserved");
        passengerBox2.addItem("Waitlisted");
        passengerBox2.addItem("Completed");
    }

    /*
     * retrieves cruise numbers for population combo box
     * in the available seats menu
     */
    private void initSeatComboBox() {
        seatCruiseBox.removeAllItems();
        //seatCruiseBox.setEditable(true);

        String sql = "SELECT cnum FROM Cruise ORDER BY cnum ASC";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            // add all cnums to comboBox
            for (List<String> ls : rs)
                seatCruiseBox.addItem(ls.get(0));
        } catch (SQLException throwables) {
//            System.out.println("SQL error in initSeatComboBox()");
            throwables.printStackTrace();
        }
    }

    /*
     * creates gui menu
     */
    public JMenuBar createMenuBar() {
        JMenuBar jb = new JMenuBar();
        JMenu menu2;
        JButton menu1;
        JMenuItem i1, i2, i3, i4, i5, i6, i7;
        menu1 = new JButton("Home");
        menu1.setOpaque(true);
        menu1.setContentAreaFilled(false);
        menu1.setBorderPainted(false);
        menu1.setFocusable(false);
        menu2 = new JMenu("File");
        i1 = new JMenuItem("Add Data");
        i2 = new JMenuItem("Book a Cruise");
        i3 = new JMenuItem("Available Seats");
        i4 = new JMenuItem("Repairs Info");
        i5 = new JMenuItem("Passengers Info");
        i6 = new JMenuItem("View Data");
        i7 = new JMenuItem("Quit");

        i1.addActionListener(this);
        i2.addActionListener(this);
        i3.addActionListener(this);
        i4.addActionListener(this);
        i5.addActionListener(this);
        i6.addActionListener(this);
        i7.addActionListener(this);
        menu1.addActionListener(this);
        addButton.addActionListener(this);
        addButton.setActionCommand("addButton");

        menu2.add(i1);
        menu2.add(i2);
        menu2.add(i3);
        menu2.add(i4);
        menu2.add(i5);
        menu2.add(i6);
        menu2.add(i7);
        jb.add(menu1);
        jb.add(menu2);
        //table1.setAutoCreateRowSorter(true);

        return jb;
    }

    /*
     * shows correct add data menu depending on selected item
     */
    public void showTable(String item) {
        CardLayout cl = (CardLayout) Holder.getLayout();
        if (item.equals("Ship")) {
            cl.show(Holder, "addShip");
        }
        if (item.equals("Captain")) {
            cl.show(Holder, "addCaptain");
        }
        if (item.equals("Cruise")) {
            initAddCruiseComboBoxes();
            cl.show(Holder, "addCruise");
        }
    }

    /*
     * creates data table for the view data menu
     * just gets selected table and places them into java tables
     */
    public void createTable(String item) throws SQLException {
        DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
        table1.setEnabled(false);
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        String tableName = "captain";
        if (item.equals("Captains")) {
            tableName = "captain";
        }
        if (item.equals("Cruises")) {
            tableName = "cruise";
        }
        if (item.equals("Cruise Info")) {
            tableName = "cruiseinfo";
        }
        if (item.equals("Customers")) {
            tableName = "customer";
        }
        if (item.equals("Repairs")) {
            tableName = "repairs";
        }
        if (item.equals("Reservations")) {
            tableName = "reservation";
        }
        if (item.equals("Schedules")) {
            tableName = "schedule";
        }
        if (item.equals("Ships")) {
            tableName = "ship";
        }
        if (item.equals("Technicians")) {
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
            for (int i = 1; i <= columnCount; i++) {
                row.add(result.getString(rsmd.getColumnName(i)));
            }
            tableModel.addRow(row);
        }
    }

    /*
     * looks up and then displays number of passengers
     * for given cruise and reservation status
     */
    public void createNumPassengers() {
        if (passengerBox1.getSelectedItem() == null || passengerBox2.getSelectedItem() == null)
            return;
        int cnum = Integer.parseInt(passengerBox1.getSelectedItem().toString());
        char status = passengerBox2.getSelectedItem().toString().charAt(0);
        String sql = "SELECT COUNT(rnum) FROM Reservation WHERE cid=" + cnum + "AND status='" + status + "';";
        try {
            passengerNumField.setText(sq.executeQueryAndReturnResult(sql).get(0).get(0));
        } catch (SQLException throwables) {
            passengerNumField.setText("Not Valid Entries");
            throwables.printStackTrace();
        }
    }


    /*
     * all GUI event handlers
     * calls relevant functions
     */
    public void actionPerformed(ActionEvent e) {
        String item = e.getActionCommand();
        if (item.equals("addButton")) {
            try {
                addData();
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        } else if (item.equals("bookButton"))
            bookCruise();
        else if (item.equals("seatButton"))
            findNumSeats();

        if (item.equals("Home"))
            cl.show(panelHolder, "mainMenu");
        else if (item.equals("Add Data"))
            cl.show(panelHolder, "addMenu");
        else if (item.equals("Book a Cruise")) {
            bookField1.setText(null);
            bookField2.setText(null);
            initBookingComboBox();
            cl.show(panelHolder, "bookMenu");
        } else if (item.equals("Available Seats")) {
            initSeatComboBox();
            cl.show(panelHolder, "seatsMenu");
        } else if (item.equals("Repairs Info")) {
            cl.show(panelHolder, "repairMenu");
            try {
                showRepairsList();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (item.equals("Passengers Info"))
            cl.show(panelHolder, "passengerMenu");
        else if (item.equals("View Data")) {
            try {
                createTable("Captain");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            cl.show(panelHolder, "dataMenu");
        } else if (item.equals("Quit"))
            this.dispose();
    }

    /* creates, populates, and shows repair list
     * ordered with SQL and displayed in java table
     */
    private void showRepairsList() throws SQLException {
        String sql = "SELECT R.ship_id, COUNT(R.ship_id) as total_repairs, S.make, S.model, S.age, S.seats from repairs R, ship S WHERE S.id = R.ship_id GROUP BY R.ship_id, S.make, S.model,S.seats, S.age ORDER BY COUNT(R.ship_id) desc;";
        ResultSet result = sq.executeQueryAndReturnResultWithColumn(sql);
        ResultSetMetaData rsmd = result.getMetaData();
        DefaultTableModel tableModel = (DefaultTableModel) shipTable.getModel();
        shipTable.setEnabled(false);
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
        int columnCount = rsmd.getColumnCount();
        List<String> columnNames = new ArrayList<>();
        for (int i = 1; i <= columnCount; i++) {
            tableModel.addColumn(rsmd.getColumnName(i));
            columnNames.add(rsmd.getColumnName(i));
        }
        while (result.next()) {
            Vector<String> row = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                row.add(result.getString(rsmd.getColumnName(i)));
            }
            tableModel.addRow(row);
        }
    }

    /*
     * holds logic for all add data forms
     * index = 0 for ship
     * index = 1 for captain
     * index = 2 for cruise
     */
    public void addData() throws ParseException {
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
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error. Please try again.");
                        return;
                    }

                    // update addCruise combobox
                    addCruiseShipBox.addItem(Integer.toString(id));

                    // reset fields
                    ship2.setText(null);
                    ship3.setText(null);
                    ship4.setText(null);
                    ship5.setText(null);
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
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error. Please try again.");
                    return;
                }

                // update addCruise combobox
                addCruiseCaptainBox.addItem(captain2.getText());

                // reset fields
                captain2.setText(null);
                captain3.setText(null);
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
                    return;
                }
                if (cruise7.getText().length() != 5 || cruise8.getText().length() != 5) {
                    JOptionPane.showMessageDialog(null, "Please Make Sure That Departure Port and Arrival Port have 5 characters.");
                    return;
                }
                if (cruise7.getText().length() != 5 || cruise8.getText().length() != 5) {
                    JOptionPane.showMessageDialog(null, "Please Make Sure That Departure Port and Arrival Port have 5 characters.");
                    return;
                }
                if (!checkDate(dt, at)) {
                    JOptionPane.showMessageDialog(null, "Please Make Sure That departure is before arrival.");
                    return;

                } else {
                    String sql = "SELECT MAX(cnum) FROM cruise;";
                    int cnum = 0;
                    try {
                        List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
                        int result = Integer.parseInt(rs.get(0).get(0));
                        cnum = result + 1;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        return;
                    }
                    sql = "INSERT INTO cruise (cnum, cost, num_sold, num_stops, actual_departure_date, actual_arrival_date, arrival_port, departure_port) VALUES ("
                            + cnum + ", "
                            + cruise2.getText() + ", "
                            + cruise3.getText() + ", "
                            + cruise4.getText() + ", '"
                            + getString(dt) + "', '"
                            + getString(at) + "', '"
                            + cruise7.getText() + "', '"
                            + cruise8.getText() + "');";
//                    System.out.println(sql);
                    try {
                        sq.executeUpdate(sql);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error adding cruise. Please try again.");
                        return;
                    }

                    // add CruiseInfo
                    int ship_id = Integer.parseInt(addCruiseShipBox.getSelectedItem().toString());
                    String fullname = addCruiseCaptainBox.getSelectedItem().toString();

                    // get captain id
                    int captain_id;
                    sql = "SELECT id FROM Captain WHERE fullname='" + fullname + "';";
                    try {
                        List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
                        try {
                            captain_id = Integer.parseInt(rs.get(0).get(0));
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Captain not found");
                            return;
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        return;
                    }

                    // get new ciid
                    int ciid;
                    sql = "SELECT MAX(ciid) FROM CruiseInfo;";
                    try {
                        List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
                        int result = Integer.parseInt(rs.get(0).get(0));
                        ciid = result + 1;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        return;
                    }

                    // INSERT cruiseinfo
                    sql = "INSERT INTO CruiseInfo (ciid, cruise_id, captain_id, ship_id) VALUES ("
                            + ciid + ", "
                            + cnum + ","
                            + captain_id + ","
                            + ship_id + ");";
//                    System.out.println(sql);
                    try {
                        sq.executeUpdate(sql);
                        JOptionPane.showMessageDialog(null, "Success! Cruise Added.");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error add cruise info. Please try again.");
                        return;
                    }

                    // reset fields
                    cruise2.setText(null);
                    cruise3.setText(null);
                    cruise4.setText(null);
                    cruise7.setText(null);
                    cruise8.setText(null);
                }
            }
        }
    }

    private boolean checkDate(JPanel dt, JPanel at) throws ParseException {
        DateFormat dform = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = dform.parse(getString(dt));
        Date date2 = dform.parse(getString(at));
        return date1.compareTo(date2) <= 0;
    }

    /*
     * processes booking cruise information
     * pulls data from application interface, and sends to DB
     * INSERTs reservation for relevant customer
     */
    public void bookCruise() {
        // ensure all fields are entered
//        System.out.println(bookMenuCombo.getSelectedItem());
        if (bookField1.getText().isEmpty()
                || bookField2.getText().isEmpty()
                || bookMenuCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please Complete All Fields");
            return;
        }
        int cid, ccid, rnum;
        char status;

        // set cid (cruise id)
        cid = Integer.parseInt(bookMenuCombo.getSelectedItem().toString());

        // get ccid (customer id)
        String sql = "SELECT c.id FROM Customer c WHERE c.fname='" + bookField1.getText() + "' AND c.lname='" + bookField2.getText() + "';";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            try {
                ccid = Integer.parseInt(rs.get(0).get(0));
            } catch (Exception e) {
                //JOptionPane.showMessageDialog(null, "Customer does not exist. Please create customer first");
                customerInfo dialog = new customerInfo(bookField1.getText(), bookField2.getText(), sq);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                return;
            }
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, "Could not find customer");
            throwables.printStackTrace();
            return;
        }

        // determine reservation status (RESERVED | WAITLISTED)
        sql = "SELECT num_sold FROM Cruise WHERE cnum=" + cid + " UNION SELECT s.seats FROM Ship s WHERE s.id = (SELECT c.ship_id FROM CruiseInfo c WHERE c.cruise_id=" + cid + ");";
        try {
            int n_reserved, n_ship_seats;
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
        sql = "UPDATE Cruise SET num_sold=num_sold+1 WHERE cnum=" + cid + ";";
        try {
            sq.executeUpdate(sql);
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, "Could update Cruise info (internal error)");
            throwables.printStackTrace();
            return;
        }
//        System.out.println(sql);
    }

    private void findNumSeats() {
        // ensure all fields are entered
        if (seatCruiseBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Please Complete All Fields");
            return;
        }

        int cnum = Integer.parseInt(seatCruiseBox.getSelectedItem().toString());
        int n_reserved = 0, n_ship_seats = 0;
        DateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date) ((JSpinner) seatPanel.getComponent(0)).getValue();
        String d = d1.format(date);
//        System.out.println(d);  /**DATE RIGHT HERE*/
        // find num_sold, # of seats
        String sql = "SELECT num_sold FROM Cruise WHERE cnum=" + cnum
                + " UNION "
                + "SELECT s.seats FROM Ship s WHERE s.id = (SELECT c.ship_id FROM CruiseInfo c WHERE c.cruise_id=" + cnum
                + ");";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            n_reserved = Integer.parseInt(rs.get(0).get(0));
            n_ship_seats = Integer.parseInt(rs.get(1).get(0));
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, "Could not retrieve Cruise info (internal error)");
            throwables.printStackTrace();
            return;
        }
//        System.out.println(sql);
        seatInfo.setText("");
        seatInfo2.setText("");
        sql = "SELECT actual_departure_date FROM Cruise WHERE cnum=" + cnum;
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date departure_date;
            try {
                departure_date = dateFormat.parse(rs.get(0).get(0));
                System.out.println(departure_date + " " + date);
                if (departure_date.compareTo(date) < 0) {
                    JOptionPane.showMessageDialog(null, "There is no cruise on this date.");
                    seatNumField.setText(String.valueOf(0));
                    seatReservedField.setText("0");
                    return;
                }
                if (departure_date.compareTo(date) > 0) {
                    // t = dateFormat.parse(departure_date);
                    seatInfo.setText("This cruise is not available on this date.");
                    seatInfo2.setText("It is next available on " + rs.get(0).get(0) + ".");
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(null, "Could not retrieve Cruise info (internal error)");
            throwables.printStackTrace();
            return;
        }

        seatNumField.setText(String.valueOf(n_ship_seats - n_reserved));
        seatReservedField.setText(n_reserved + " / " + n_ship_seats);
    }

    private String getString(JPanel pan) {
        DateFormat d1 = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat t1 = new SimpleDateFormat("HH:mm");
        Date date = (Date) ((JSpinner) pan.getComponent(0)).getValue();
        String d = d1.format(date);
        date = (Date) ((JSpinner) pan.getComponent(1)).getValue();
        String t = t1.format(date);
        return d + " " + t;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void createUIComponents() {
        Dt_date = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        Dt_time = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
        Dt_date.setEditor(new JSpinner.DateEditor(Dt_date, "dd/MM/yyyy"));
        Dt_time.setEditor(new JSpinner.DateEditor(Dt_time, "HH:mm"));

        at_date = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        at_time = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
        at_date.setEditor(new JSpinner.DateEditor(at_date, "dd/MM/yyyy"));
        at_time.setEditor(new JSpinner.DateEditor(at_time, "HH:mm"));

        seatDate = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        seatDate.setEditor(new JSpinner.DateEditor(seatDate, "dd/MM/yyyy"));
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        panelHolder = new JPanel();
        panelHolder.setLayout(new CardLayout(0, 0));
        mainPanel.add(panelHolder, BorderLayout.CENTER);
        mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayoutManager(2, 1, new Insets(40, 0, 0, 0), -1, -1));
        mainMenu.setFocusable(false);
        panelHolder.add(mainMenu, "mainMenu");
        mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        mainMenuPanel.setOpaque(true);
        mainMenu.add(mainMenuPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        availableSeatsButton = new JButton();
        availableSeatsButton.setText("Available Seats");
        mainMenuPanel.add(availableSeatsButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mainMenuPanel.add(spacer1, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mainMenuPanel.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        repairsInfoButton = new JButton();
        repairsInfoButton.setText("Repairs Info");
        mainMenuPanel.add(repairsInfoButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passengersInfoButton = new JButton();
        passengersInfoButton.setText("Passengers Info");
        mainMenuPanel.add(passengersInfoButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        allDataButton = new JButton();
        allDataButton.setText("View Data");
        mainMenuPanel.add(allDataButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bookACruiseButton = new JButton();
        bookACruiseButton.setText("Book a Cruise");
        mainMenuPanel.add(bookACruiseButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addDataButton = new JButton();
        addDataButton.setText("Add Data");
        mainMenuPanel.add(addDataButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        quitButton = new JButton();
        quitButton.setText("Quit");
        mainMenuPanel.add(quitButton, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        mainMenuPanel.add(spacer3, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, -1, 28, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setText("Ship Management System");
        mainMenu.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addMenu = new JPanel();
        addMenu.setLayout(new GridLayoutManager(1, 1, new Insets(15, 15, 15, 15), -1, -1));
        panelHolder.add(addMenu, "addMenu");
        addMenu.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        topBar = new JPanel();
        topBar.setLayout(new GridLayoutManager(2, 5, new Insets(50, 0, 0, 0), -1, -1));
        addMenu.add(topBar, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Add a:");
        topBar.add(label2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addMenuCombo = new JComboBox();
        addMenuCombo.setMaximumRowCount(10);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Ship");
        defaultComboBoxModel1.addElement("Captain");
        defaultComboBoxModel1.addElement("Cruise");
        addMenuCombo.setModel(defaultComboBoxModel1);
        addMenuCombo.setPopupVisible(false);
        addMenuCombo.setVerifyInputWhenFocusTarget(false);
        topBar.add(addMenuCombo, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setLabel("Submit");
        addButton.setText("Submit");
        topBar.add(addButton, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        topBar.add(spacer4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        topBar.add(spacer5, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        Holder = new JPanel();
        Holder.setLayout(new CardLayout(0, 0));
        topBar.add(Holder, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addShip = new JPanel();
        addShip.setLayout(new GridLayoutManager(5, 5, new Insets(0, 100, 100, 100), -1, -1));
        Holder.add(addShip, "addShip");
        ship2 = new JTextField();
        addShip.add(ship2, new GridConstraints(0, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ship3 = new JTextField();
        addShip.add(ship3, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Make");
        addShip.add(label3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Model");
        addShip.add(label4, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        ship4 = new JTextField();
        addShip.add(ship4, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        ship5 = new JTextField();
        addShip.add(ship5, new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Age");
        addShip.add(label5, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("Seats");
        addShip.add(label6, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        addShip.add(spacer6, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        addShip.add(spacer7, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        addShip.add(spacer8, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        addCaptain = new JPanel();
        addCaptain.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));
        Holder.add(addCaptain, "addCaptain");
        captain2 = new JTextField();
        addCaptain.add(captain2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        captain3 = new JTextField();
        addCaptain.add(captain3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("Full Name");
        addCaptain.add(label7, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("Nationality");
        addCaptain.add(label8, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        addCaptain.add(spacer9, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer10 = new Spacer();
        addCaptain.add(spacer10, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer11 = new Spacer();
        addCaptain.add(spacer11, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        addCruise = new JPanel();
        addCruise.setLayout(new GridLayoutManager(10, 4, new Insets(0, 0, 0, 0), -1, -1));
        Holder.add(addCruise, "addCruise");
        cruise2 = new JTextField();
        cruise2.setText("");
        addCruise.add(cruise2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cruise3 = new JTextField();
        addCruise.add(cruise3, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cruise4 = new JTextField();
        addCruise.add(cruise4, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cruise7 = new JTextField();
        addCruise.add(cruise7, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        cruise8 = new JTextField();
        addCruise.add(cruise8, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label9 = new JLabel();
        label9.setText("Cost");
        addCruise.add(label9, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label10 = new JLabel();
        label10.setText("Tickets Sold");
        addCruise.add(label10, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label11 = new JLabel();
        label11.setText("Stops");
        addCruise.add(label11, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label12 = new JLabel();
        label12.setText("Departure Port");
        addCruise.add(label12, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label13 = new JLabel();
        label13.setText("Arrival Port");
        addCruise.add(label13, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer12 = new Spacer();
        addCruise.add(spacer12, new GridConstraints(9, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        dt = new JPanel();
        dt.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        addCruise.add(dt, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        dt.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        dt.add(Dt_date, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dt.add(Dt_time, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer13 = new Spacer();
        addCruise.add(spacer13, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer14 = new Spacer();
        addCruise.add(spacer14, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        dep_time = new JLabel();
        dep_time.setText("Departure Time");
        addCruise.add(dep_time, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        at = new JPanel();
        at.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        addCruise.add(at, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        at.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        at.add(at_date, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        at.add(at_time, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        arr_time = new JLabel();
        arr_time.setText("Arrival Time");
        addCruise.add(arr_time, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addCruiseShipBox = new JComboBox();
        addCruiseShipBox.setEditable(false);
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        addCruiseShipBox.setModel(defaultComboBoxModel2);
        addCruise.add(addCruiseShipBox, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label14 = new JLabel();
        label14.setText("Ship");
        addCruise.add(label14, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addCruiseCaptainBox = new JComboBox();
        addCruise.add(addCruiseCaptainBox, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label15 = new JLabel();
        label15.setText("Captain");
        addCruise.add(label15, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dataMenu = new JPanel();
        dataMenu.setLayout(new GridLayoutManager(2, 3, new Insets(15, 15, 15, 15), -1, -1));
        panelHolder.add(dataMenu, "dataMenu");
        dataMenu.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        ContentHolder = new JPanel();
        ContentHolder.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        dataMenu.add(ContentHolder, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        ContentHolder.add(scrollPane1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        table1 = new JTable();
        table1.setAutoResizeMode(4);
        table1.setPreferredScrollableViewportSize(new Dimension(600, 400));
        scrollPane1.setViewportView(table1);
        final JLabel label16 = new JLabel();
        label16.setText("Select Table:");
        dataMenu.add(label16, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dataMenuCombo = new JComboBox();
        dataMenuCombo.setInheritsPopupMenu(false);
        dataMenuCombo.setMaximumRowCount(10);
        final DefaultComboBoxModel defaultComboBoxModel3 = new DefaultComboBoxModel();
        defaultComboBoxModel3.addElement("Captains");
        defaultComboBoxModel3.addElement("Cruise Info");
        defaultComboBoxModel3.addElement("Cruises");
        defaultComboBoxModel3.addElement("Customers");
        defaultComboBoxModel3.addElement("Repairs");
        defaultComboBoxModel3.addElement("Reservations");
        defaultComboBoxModel3.addElement("Schedules");
        defaultComboBoxModel3.addElement("Ships");
        defaultComboBoxModel3.addElement("Technicians");
        dataMenuCombo.setModel(defaultComboBoxModel3);
        dataMenuCombo.setSelectedIndex(0);
        dataMenu.add(dataMenuCombo, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bookMenu = new JPanel();
        bookMenu.setLayout(new GridLayoutManager(7, 3, new Insets(100, 100, 100, 100), -1, -1));
        panelHolder.add(bookMenu, "bookMenu");
        bookMenuCombo = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel4 = new DefaultComboBoxModel();
        bookMenuCombo.setModel(defaultComboBoxModel4);
        bookMenu.add(bookMenuCombo, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer15 = new Spacer();
        bookMenu.add(spacer15, new GridConstraints(5, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        bookField1 = new JTextField();
        bookMenu.add(bookField1, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label17 = new JLabel();
        label17.setText("Cruise");
        bookMenu.add(label17, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label18 = new JLabel();
        label18.setText("Customer First Name");
        bookMenu.add(label18, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bookButton = new JButton();
        bookButton.setText("Submit");
        bookMenu.add(bookButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer16 = new Spacer();
        bookMenu.add(spacer16, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label19 = new JLabel();
        label19.setText("Create a booking");
        bookMenu.add(label19, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        bookField2 = new JTextField();
        bookMenu.add(bookField2, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label20 = new JLabel();
        label20.setText("Customer Last Name");
        bookMenu.add(label20, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        bookMenu.add(panel1, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder(null, "Cruise Info", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JLabel label21 = new JLabel();
        label21.setText("Cost");
        panel1.add(label21, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label22 = new JLabel();
        label22.setText("Sold");
        panel1.add(label22, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label23 = new JLabel();
        label23.setText("Stops");
        panel1.add(label23, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label24 = new JLabel();
        label24.setText("Departure Date");
        panel1.add(label24, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label25 = new JLabel();
        label25.setText("Arrival Date");
        panel1.add(label25, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label26 = new JLabel();
        label26.setText("Arrival Port");
        panel1.add(label26, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label27 = new JLabel();
        label27.setText("Departure Port");
        panel1.add(label27, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        costInfo = new JLabel();
        costInfo.setText("Label");
        panel1.add(costInfo, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        soldInfo = new JLabel();
        soldInfo.setText("Label");
        panel1.add(soldInfo, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stopInfo = new JLabel();
        stopInfo.setText("Label");
        panel1.add(stopInfo, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dDate = new JLabel();
        dDate.setText("Label");
        panel1.add(dDate, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aDate = new JLabel();
        aDate.setText("Label");
        panel1.add(aDate, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aPort = new JLabel();
        aPort.setText("Label");
        panel1.add(aPort, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        dPort = new JLabel();
        dPort.setText("Label");
        panel1.add(dPort, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        seatsMenu = new JPanel();
        seatsMenu.setLayout(new GridLayoutManager(8, 3, new Insets(100, 100, 100, 100), -1, -1));
        panelHolder.add(seatsMenu, "seatsMenu");
        seatCruiseBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel5 = new DefaultComboBoxModel();
        seatCruiseBox.setModel(defaultComboBoxModel5);
        seatsMenu.add(seatCruiseBox, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label28 = new JLabel();
        label28.setText("Cruise Number");
        seatsMenu.add(label28, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label29 = new JLabel();
        label29.setText("Date");
        seatsMenu.add(label29, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label30 = new JLabel();
        label30.setText("Available Seats:");
        seatsMenu.add(label30, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        seatNumField = new JLabel();
        seatNumField.setText("");
        seatsMenu.add(seatNumField, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        seatButton = new JButton();
        seatButton.setText("Find Availability");
        seatsMenu.add(seatButton, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label31 = new JLabel();
        label31.setText("Reserved Seats:");
        seatsMenu.add(label31, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer17 = new Spacer();
        seatsMenu.add(spacer17, new GridConstraints(7, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        seatReservedField = new JLabel();
        seatReservedField.setText("");
        seatsMenu.add(seatReservedField, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        seatsMenu.add(seatPanel, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        seatPanel.add(seatDate, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        seatInfo = new JLabel();
        seatInfo.setText("Label");
        seatsMenu.add(seatInfo, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        seatInfo2 = new JLabel();
        seatInfo2.setText("Label");
        seatsMenu.add(seatInfo2, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        repairMenu = new JPanel();
        repairMenu.setLayout(new GridLayoutManager(2, 1, new Insets(50, 50, 50, 50), -1, -1));
        panelHolder.add(repairMenu, "repairMenu");
        repairMenu.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JScrollPane scrollPane2 = new JScrollPane();
        repairMenu.add(scrollPane2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPane2.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        shipTable = new JTable();
        scrollPane2.setViewportView(shipTable);
        final JLabel label32 = new JLabel();
        Font label32Font = this.$$$getFont$$$(null, Font.BOLD, 24, label32.getFont());
        if (label32Font != null) label32.setFont(label32Font);
        label32.setText("Repairs Info");
        repairMenu.add(label32, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passengerMenu = new JPanel();
        passengerMenu.setLayout(new GridLayoutManager(5, 2, new Insets(100, 100, 100, 100), -1, -1));
        panelHolder.add(passengerMenu, "passengerMenu");
        passengerBox1 = new JComboBox();
        passengerMenu.add(passengerBox1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        passengerBox2 = new JComboBox();
        passengerMenu.add(passengerBox2, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label33 = new JLabel();
        label33.setText("Cruise");
        passengerMenu.add(label33, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label34 = new JLabel();
        label34.setText("Reservation Status");
        passengerMenu.add(label34, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer18 = new Spacer();
        passengerMenu.add(spacer18, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        passengerNumField = new JLabel();
        passengerNumField.setText("");
        passengerMenu.add(passengerNumField, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label35 = new JLabel();
        label35.setText("Number of Passengers:");
        passengerMenu.add(label35, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label36 = new JLabel();
        label36.setText("Please select Cruise ID # and reservation status");
        passengerMenu.add(label36, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}




