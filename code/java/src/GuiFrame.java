

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
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
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JPanel addCaptain;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JPanel addCruise;
    private JComboBox addMenuCombo;
    private JPanel addMenu;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JTextField textField13;
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
    private JComboBox bookMenuBox;
    private JTextField textField14;
    private JButton button1;
    private JTextField textField15;
    private JComboBox cruiseNumBox;
    private JComboBox dateBox;
    private JTable shipTable;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private CardLayout cl;
    DBproject sq;

    public GuiFrame(DBproject esql) throws SQLException, IOException {

        this.sq = esql;

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

        addDataButton.addActionListener(this);
        bookACruiseButton.addActionListener(this);
        availableSeatsButton.addActionListener(this);
        repairsInfoButton.addActionListener(this);
        passengersInfoButton.addActionListener(this);
        allDataButton.addActionListener(this);
        quitButton.addActionListener(this);

    }

    private void initBookingComboBox() {
        Vector comboBoxItems=new Vector();

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



    public JPanel getPanelHolder(){
        return panelHolder;
    }

    public CardLayout getCardLayout(){
        return cl;
    }

    public void actionPerformed(ActionEvent e) {
        String item = e.getActionCommand();
        if(item.equals("Home")){
            cl.show(panelHolder, "mainMenu");
        }
        if(item.equals("Add Data")){
            cl.show(panelHolder, "addMenu");
        }
        if(item.equals("Book a Cruise")){
            cl.show(panelHolder, "bookMenu");
        }
        if(item.equals("Available Seats")){
            cl.show(panelHolder, "seatsMenu");
        }if(item.equals("Repairs Info")){
            cl.show(panelHolder, "repairMenu");
        }
        if(item.equals("Passengers Info")){
            cl.show(panelHolder, "passengerMenu");
        }
        if(item.equals("View Data")){
            cl.show(panelHolder, "dataMenu");
        }
        if(item.equals("Quit")){
            this.dispose();
        }
    }
}

