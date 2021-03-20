

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
    private CardLayout cl;
    DBproject sq;

    public GuiFrame(DBproject esql) throws SQLException, IOException {

        this.sq = esql;
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
        addCruiseShipBox.setEditable(true);
        addCruiseCaptainBox.setEditable(true);
        String sql = "SELECT id FROM Ship ORDER BY id ASC";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            // add all cnums to comboBox
            for (List<String> ls : rs)
                addCruiseShipBox.addItem(ls.get(0));
        } catch (SQLException throwables) {
            System.out.println("SQL error in initAddCruiseComboBoxes()");
            throwables.printStackTrace();
        }
        sql = "SELECT fullname FROM Captain ORDER BY fullname ASC";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            // add all names to comboBox
            for (List<String> ls : rs)
                addCruiseCaptainBox.addItem(ls.get(0));
        } catch (SQLException throwables) {
            System.out.println("SQL error in initAddCruiseComboBoxes()");
            throwables.printStackTrace();
        }
    }

    /*
     * populates cruise number combo box for
     * the booking menu
     */
    private void initBookingComboBox() {
        bookMenuCombo.removeAllItems();
        bookMenuCombo.setEditable(true);
        String sql = "SELECT cnum FROM Cruise ORDER BY cnum ASC";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            // add all cnums to comboBox
            for (List<String> ls : rs)
                bookMenuCombo.addItem(ls.get(0));
            fillCruiseInfo(0);
        } catch (SQLException throwables) {
            System.out.println("SQL error in initBookingComboBox()");
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
        passengerBox1.setEditable(true);

        String sql = "SELECT cnum FROM Cruise ORDER BY cnum ASC";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            // add all cnums to comboBox
            for (List<String> ls : rs)
                passengerBox1.addItem(ls.get(0));
        } catch (SQLException throwables) {
            System.out.println("SQL error in initPassengerComboBox()");
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
        seatCruiseBox.setEditable(true);

        String sql = "SELECT cnum FROM Cruise ORDER BY cnum ASC";
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            // add all cnums to comboBox
            for (List<String> ls : rs)
                seatCruiseBox.addItem(ls.get(0));
        } catch (SQLException throwables) {
            System.out.println("SQL error in initSeatComboBox()");
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
        }
        else if (item.equals("bookButton"))
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
        }
        else if (item.equals("Available Seats")) {
            initSeatComboBox();
            cl.show(panelHolder, "seatsMenu");
        }
        else if (item.equals("Repairs Info")) {
            cl.show(panelHolder, "repairMenu");
            try {
                showRepairsList();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (item.equals("Passengers Info"))
            cl.show(panelHolder, "passengerMenu");
        else if (item.equals("View Data")){
            try {
                createTable("Captain");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            cl.show(panelHolder, "dataMenu");
        }

        else if (item.equals("Quit"))
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
                } if (cruise7.getText().length() != 5 || cruise8.getText().length() != 5) {
                    JOptionPane.showMessageDialog(null, "Please Make Sure That Departure Port and Arrival Port have 5 characters.");
                    return;
                }if (cruise7.getText().length() != 5 || cruise8.getText().length() != 5) {
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
                    System.out.println(sql);
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
                    System.out.println(sql);
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
        if(date1.compareTo(date2) > 0){
            return false;
        }
        return true;
    }

    /*
     * processes booking cruise information
     * pulls data from application interface, and sends to DB
     * INSERTs reservation for relevant customer
     */
    public void bookCruise() {
        // ensure all fields are entered
        System.out.println(bookMenuCombo.getSelectedItem());
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
        System.out.println(sql);
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
        System.out.println(d);  /**DATE RIGHT HERE*/
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
        System.out.println(sql);

        sql = "SELECT actual_departure_date FROM Cruise WHERE cnum=" + cnum;
        try {
            List<List<String>> rs = sq.executeQueryAndReturnResult(sql);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date departure_date;
            try {
                departure_date = dateFormat.parse(rs.get(0).get(0));
                if (departure_date.compareTo(date) < 0) {
                    JOptionPane.showMessageDialog(null, "This date is after the cruise has departed.");
                    return;
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
        Dt_date.setEditor(new JSpinner.DateEditor(Dt_date, "dd/MM/yy"));
        Dt_time.setEditor(new JSpinner.DateEditor(Dt_time, "HH:mm"));

        at_date = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        at_time = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.HOUR_OF_DAY));
        at_date.setEditor(new JSpinner.DateEditor(at_date, "dd/MM/yy"));
        at_time.setEditor(new JSpinner.DateEditor(at_time, "HH:mm"));

        seatDate = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
        seatDate.setEditor(new JSpinner.DateEditor(seatDate, "dd/MM/yy"));
    }

}




