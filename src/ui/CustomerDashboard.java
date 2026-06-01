package ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;
import services.DatabaseService;
import models.*;

public class CustomerDashboard extends JFrame {
    
    private User currentUser;
    private DatabaseService db;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JTable roomTable;
    private JTable bookingTable;
    private JComboBox<String> typeCombo;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JTextField checkInField;
    private JTextField checkOutField;
    private DefaultTableModel roomTableModel;
    private JLabel bookingCountLabel;
    
    public CustomerDashboard(User user) {
        this.currentUser = user;
        this.db = DatabaseService.getInstance();
        initUI();
    }
    
    private void initUI() {
        setTitle("Hotel Booking System - Dashboard");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        setLayout(new BorderLayout());
        
        // Top Panel - Updated with centered golden welcome message
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(20, 30, 48));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getName() + "!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        welcomeLabel.setForeground(new Color(255, 215, 0)); // Golden color
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(244, 67, 54));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("Arial", Font.BOLD, 12));
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> {
            new LoginUI().setVisible(true);
            dispose();
        });
        
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        topPanel.add(logoutBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);
        
        // Left Panel
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(28, 40, 60));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        leftPanel.setPreferredSize(new Dimension(180, 0));
        
        String[] buttons = {"Available Rooms", "My Bookings", "My Profile"};
        
        for (String btnText : buttons) {
            JButton btn = new JButton(btnText);
            btn.setMaximumSize(new Dimension(160, 40));
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setBackground(new Color(52, 152, 219));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusPainted(false);
            
            if (btnText.equals("Available Rooms")) {
                btn.addActionListener(e -> showAvailableRooms());
            } else if (btnText.equals("My Bookings")) {
                btn.addActionListener(e -> showMyBookings());
            } else if (btnText.equals("My Profile")) {
                btn.addActionListener(e -> showProfile());
            }
            
            leftPanel.add(btn);
            leftPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        add(leftPanel, BorderLayout.WEST);
        
        // Center Panel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        mainPanel.add(createAvailableRoomsPanel(), "available");
        mainPanel.add(createMyBookingsPanel(), "bookings");
        mainPanel.add(createProfilePanel(), "profile");
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(20, 30, 48));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(new JLabel(LocalDate.now().toString()));
        add(bottomPanel, BorderLayout.SOUTH);
        
        showAvailableRooms();
    }
    
    private JPanel createAvailableRoomsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Filter Panel
        JPanel filterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        filterPanel.add(new JLabel("Room Type:"), gbc);
        gbc.gridx = 1;
        typeCombo = new JComboBox<>(new String[]{"All", "Standard", "Suite"});
        typeCombo.setPreferredSize(new Dimension(120, 25));
        filterPanel.add(typeCombo, gbc);
        
        gbc.gridx = 2;
        filterPanel.add(new JLabel("Min Price ($):"), gbc);
        gbc.gridx = 3;
        minPriceField = new JTextField(8);
        filterPanel.add(minPriceField, gbc);
        
        gbc.gridx = 4;
        filterPanel.add(new JLabel("Max Price ($):"), gbc);
        gbc.gridx = 5;
        maxPriceField = new JTextField(8);
        filterPanel.add(maxPriceField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        filterPanel.add(new JLabel("Check-in Date:"), gbc);
        gbc.gridx = 1;
        checkInField = new JTextField(LocalDate.now().toString(), 10);
        filterPanel.add(checkInField, gbc);
        
        gbc.gridx = 2;
        filterPanel.add(new JLabel("Check-out Date:"), gbc);
        gbc.gridx = 3;
        checkOutField = new JTextField(LocalDate.now().plusDays(3).toString(), 10);
        filterPanel.add(checkOutField, gbc);
        
        gbc.gridx = 4;
        JButton searchBtn = new JButton("Search Rooms");
        searchBtn.setBackground(new Color(76, 175, 80));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        filterPanel.add(searchBtn, gbc);
        
        gbc.gridx = 5;
        JButton clearBtn = new JButton("Clear Filters");
        clearBtn.setBackground(new Color(158, 158, 158));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFocusPainted(false);
        filterPanel.add(clearBtn, gbc);
        
        panel.add(filterPanel, BorderLayout.NORTH);
        
        // Seasonal Info
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(255, 243, 224));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JLabel infoLabel = new JLabel("Seasonal Pricing: Jan-Jun (Regular) | Jul-Aug (15% off) | Sep-Dec 19 (25% off) | Dec 20-31 (50% up)");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        infoPanel.add(infoLabel);
        panel.add(infoPanel, BorderLayout.CENTER);
        
        // Create table with button column
        String[] columns = {"Room #", "Type", "Price/Night", "Capacity", "Bed Type", "Amenities", "Action"};
        roomTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };
        
        roomTable = new JTable(roomTableModel);
        roomTable.setRowHeight(45);
        roomTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        roomTable.getTableHeader().setBackground(new Color(33, 150, 243));
        roomTable.getTableHeader().setForeground(Color.WHITE);
        
        // Set custom renderer and editor for button column
        roomTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        roomTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));
        
        JScrollPane scrollPane = new JScrollPane(roomTable);
        panel.add(scrollPane, BorderLayout.SOUTH);
        
        roomTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        roomTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        roomTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        roomTable.getColumnModel().getColumn(3).setPreferredWidth(60);
        roomTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        roomTable.getColumnModel().getColumn(5).setPreferredWidth(150);
        roomTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        
        searchBtn.addActionListener(e -> refreshAvailableRooms());
        clearBtn.addActionListener(e -> {
            typeCombo.setSelectedIndex(0);
            minPriceField.setText("");
            maxPriceField.setText("");
            checkInField.setText(LocalDate.now().toString());
            checkOutField.setText(LocalDate.now().plusDays(3).toString());
            refreshAvailableRooms();
        });
        
        refreshAvailableRooms();
        
        return panel;
    }
    
    private void refreshAvailableRooms() {
        List<Room> allRooms = db.getAvailableRooms();
        List<Room> filteredRooms = new ArrayList<>();
        
        String selectedType = (String) typeCombo.getSelectedItem();
        String minPriceText = minPriceField.getText().trim();
        String maxPriceText = maxPriceField.getText().trim();
        
        double minPrice = minPriceText.isEmpty() ? 0 : Double.parseDouble(minPriceText);
        double maxPrice = maxPriceText.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceText);
        
        for (Room room : allRooms) {
            if (!selectedType.equals("All") && !room.getRoomType().equals(selectedType)) continue;
            if (room.getBaseRate() < minPrice) continue;
            if (room.getBaseRate() > maxPrice) continue;
            filteredRooms.add(room);
        }
        
        roomTableModel.setRowCount(0);
        
        for (Room room : filteredRooms) {
            Object[] row = {
                room.getRoomNumber(),
                room.getRoomType(),
                "$" + room.getBaseRate(),
                room.getRoomType().equals("Suite") ? 4 : 2,
                room.getRoomType().equals("Suite") ? "King" : "Queen",
                room.getRoomType().equals("Suite") ? "Shower, MiniBar, WiFi" : "WiFi, TV, AC",
                "Book Now"
            };
            roomTableModel.addRow(row);
        }
    }
    
    // Button Renderer
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(76, 175, 80));
            setForeground(Color.WHITE);
            setFont(new Font("Arial", Font.BOLD, 12));
            setFocusPainted(false);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "Book Now");
            return this;
        }
    }
    
    // Button Editor - Handles the click
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;
        private int clickedRow;
        private JTable table;
        
        public ButtonEditor(JCheckBox checkBox) {
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(new Color(76, 175, 80));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 12));
            button.setFocusPainted(false);
            
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (clickedRow >= 0) {
                        int roomNumber = (int) table.getValueAt(clickedRow, 0);
                        showBookingDialog(roomNumber);
                    }
                    fireEditingStopped();
                }
            });
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            this.table = table;
            this.clickedRow = row;
            button.setText(value != null ? value.toString() : "Book Now");
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            return "Book Now";
        }
    }
    
    private void showBookingDialog(int roomNumber) {
        JDialog dialog = new JDialog(this, "Complete Your Booking", true);
        dialog.setSize(550, 700);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Customer Information
        JPanel customerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer Information"));
        customerPanel.add(new JLabel("Customer Name:"));
        customerPanel.add(new JLabel(currentUser.getName()));
        customerPanel.add(new JLabel("Customer ID:"));
        customerPanel.add(new JLabel(currentUser.getUsername()));
        customerPanel.add(new JLabel("Booking Date:"));
        customerPanel.add(new JLabel(LocalDate.now().toString()));
        mainPanel.add(customerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Room Information
        Room room = db.getRoomByNumber(roomNumber);
        if (room == null) {
            JOptionPane.showMessageDialog(dialog, "Room not found!");
            dialog.dispose();
            return;
        }
        
        JPanel roomPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        roomPanel.setBorder(BorderFactory.createTitledBorder("Room Information"));
        roomPanel.add(new JLabel("Room Number:"));
        roomPanel.add(new JLabel(String.valueOf(room.getRoomNumber())));
        roomPanel.add(new JLabel("Room Type:"));
        roomPanel.add(new JLabel(room.getRoomType()));
        roomPanel.add(new JLabel("Base Rate:"));
        roomPanel.add(new JLabel("$" + room.getBaseRate() + " / night"));
        roomPanel.add(new JLabel("Capacity:"));
        roomPanel.add(new JLabel(room.getRoomType().equals("Suite") ? "4 Adults" : "2 Adults"));
        mainPanel.add(roomPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Date Selection
        JPanel datePanel = new JPanel(new GridLayout(2, 2, 10, 10));
        datePanel.setBorder(BorderFactory.createTitledBorder("Select Dates"));
        
        JTextField checkInFieldDialog = new JTextField(checkInField.getText(), 15);
        JTextField checkOutFieldDialog = new JTextField(checkOutField.getText(), 15);
        
        datePanel.add(new JLabel("Check-in Date (YYYY-MM-DD):"));
        datePanel.add(checkInFieldDialog);
        datePanel.add(new JLabel("Check-out Date (YYYY-MM-DD):"));
        datePanel.add(checkOutFieldDialog);
        mainPanel.add(datePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Calculate Button
        JButton calculateBtn = new JButton("Calculate Total Price");
        calculateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculateBtn.setBackground(new Color(33, 150, 243));
        calculateBtn.setForeground(Color.WHITE);
        calculateBtn.setFocusPainted(false);
        mainPanel.add(calculateBtn);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Daily Breakdown
        JTextArea breakdownArea = new JTextArea(10, 40);
        breakdownArea.setEditable(false);
        breakdownArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        JScrollPane breakdownScroll = new JScrollPane(breakdownArea);
        breakdownScroll.setBorder(BorderFactory.createTitledBorder("Daily Price Breakdown"));
        mainPanel.add(breakdownScroll);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Total Price
        JLabel totalLabel = new JLabel("Total Price: --", SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
        totalLabel.setForeground(new Color(76, 175, 80));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(totalLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        // Confirm Button
        JButton confirmBtn = new JButton("Confirm Booking");
        confirmBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmBtn.setBackground(new Color(76, 175, 80));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setFont(new Font("Arial", Font.BOLD, 14));
        confirmBtn.setFocusPainted(false);
        mainPanel.add(confirmBtn);
        
        JScrollPane scrollMain = new JScrollPane(mainPanel);
        scrollMain.setBorder(null);
        dialog.add(scrollMain, BorderLayout.CENTER);
        
        calculateBtn.addActionListener(e -> {
            try {
                LocalDate start = LocalDate.parse(checkInFieldDialog.getText());
                LocalDate end = LocalDate.parse(checkOutFieldDialog.getText());
                
                if (start.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(dialog, "Check-in date cannot be in the past!");
                    return;
                }
                if (end.isBefore(start) || end.equals(start)) {
                    JOptionPane.showMessageDialog(dialog, "Check-out date must be after check-in date!");
                    return;
                }
                
                double total = room.calculatePrice(start, end);
                totalLabel.setText(String.format("Total Price: $%.2f", total));
                
                StringBuilder breakdown = new StringBuilder();
                LocalDate current = start;
                int dayNum = 1;
                while (current.isBefore(end)) {
                    double multiplier = room.getPricingCalculator().getMultiplier(current);
                    double dailyRate = room.getBaseRate() * multiplier;
                    String season = getSeasonName(current);
                    breakdown.append(String.format("Day %d: %s - %s: $%.2f\n", 
                        dayNum, current, season, dailyRate));
                    current = current.plusDays(1);
                    dayNum++;
                }
                breakdownArea.setText(breakdown.toString());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid date format. Use YYYY-MM-DD");
            }
        });
        
        confirmBtn.addActionListener(e -> {
            try {
                LocalDate start = LocalDate.parse(checkInFieldDialog.getText());
                LocalDate end = LocalDate.parse(checkOutFieldDialog.getText());
                
                if (start.isBefore(LocalDate.now())) {
                    JOptionPane.showMessageDialog(dialog, "Check-in date cannot be in the past!");
                    return;
                }
                if (end.isBefore(start) || end.equals(start)) {
                    JOptionPane.showMessageDialog(dialog, "Check-out date must be after check-in date!");
                    return;
                }
                
                if (room.isAvailable()) {
                    double total = room.calculatePrice(start, end);
                    Booking booking = new Booking(currentUser, room, start, end);
                    booking.setTotalPrice(total);
                    
                    if (db.saveBooking(booking)) {
                        db.updateRoomAvailability(roomNumber, false);
                        
                        JOptionPane.showMessageDialog(dialog, 
                            "✓ BOOKING CONFIRMED!\n\n" +
                            "Booking ID: " + booking.getBookingId() + "\n" +
                            "Customer: " + currentUser.getName() + "\n" +
                            "Room: " + room.getRoomType() + " #" + roomNumber + "\n" +
                            "Check-in: " + start + "\n" +
                            "Check-out: " + end + "\n" +
                            "Total Nights: " + ChronoUnit.DAYS.between(start, end) + "\n" +
                            "Total Price: $" + String.format("%.2f", total) + "\n\n" +
                            "Thank you for choosing our hotel!");
                        
                        refreshAvailableRooms();
                        refreshMyBookings();
                        updateProfileBookingCount();
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Booking failed. Please try again.");
                    }
                } else {
                    JOptionPane.showMessageDialog(dialog, "Room is no longer available.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });
        
        dialog.setVisible(true);
    }
    
    private String getSeasonName(LocalDate date) {
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        if (month == 12 && day >= 20) {
            return "Holiday Season (50% up)";
        } else if (month >= 7 && month <= 8) {
            return "Discount Season (15% off)";
        } else if (month > 8 && month <= 12) {
            return "Discount Season (25% off)";
        } else {
            return "Regular Season";
        }
    }
    
    private JPanel createMyBookingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel title = new JLabel("My Bookings", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(title, BorderLayout.NORTH);
        
        String[] columns = {"Booking ID", "Room #", "Room Type", "Check-in", "Check-out", "Total Price", "Status"};
        bookingTable = new JTable();
        bookingTable.setRowHeight(30);
        bookingTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        bookingTable.getTableHeader().setBackground(new Color(33, 150, 243));
        bookingTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(bookingTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel cancelPanel = new JPanel(new FlowLayout());
        cancelPanel.add(new JLabel("Enter Booking ID:"));
        JTextField cancelField = new JTextField(15);
        cancelPanel.add(cancelField);
        JButton cancelBtn = new JButton("Cancel Booking");
        cancelBtn.setBackground(new Color(244, 67, 54));
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setFocusPainted(false);
        cancelPanel.add(cancelBtn);
        panel.add(cancelPanel, BorderLayout.SOUTH);
        
        cancelBtn.addActionListener(e -> {
            String bookingId = cancelField.getText().trim().toUpperCase();
            if (bookingId.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Enter Booking ID");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(panel, "Cancel booking " + bookingId + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (db.cancelBooking(bookingId)) {
                    JOptionPane.showMessageDialog(panel, "Booking cancelled!");
                    refreshMyBookings();
                    updateProfileBookingCount();
                    cancelField.setText("");
                    refreshAvailableRooms();
                } else {
                    JOptionPane.showMessageDialog(panel, "Booking ID not found");
                }
            }
        });
        
        refreshMyBookings();
        return panel;
    }
    
    private void refreshMyBookings() {
        List<Booking> bookings = db.getBookingsByUser(currentUser.getUsername());
        
        String[] columns = {"Booking ID", "Room #", "Room Type", "Check-in", "Check-out", "Total Price", "Status"};
        Object[][] data = new Object[bookings.size()][7];
        
        for (int i = 0; i < bookings.size(); i++) {
            Booking b = bookings.get(i);
            data[i][0] = b.getBookingId();
            data[i][1] = b.getRoom().getRoomNumber();
            data[i][2] = b.getRoom().getRoomType();
            data[i][3] = b.getStartDate().toString();
            data[i][4] = b.getEndDate().toString();
            data[i][5] = "$" + String.format("%.2f", b.getTotalPrice());
            data[i][6] = b.getStatus();
        }
        
        bookingTable.setModel(new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }
    
    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(currentUser.getName()), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(currentUser.getUsername()), gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Member Since:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(LocalDate.now().toString()), gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Total Bookings:"), gbc);
        gbc.gridx = 1;
        bookingCountLabel = new JLabel();
        panel.add(bookingCountLabel, gbc);
        
        updateProfileBookingCount();
        
        return panel;
    }
    
    private void updateProfileBookingCount() {
        if (bookingCountLabel != null) {
            int bookingCount = db.getBookingsByUser(currentUser.getUsername()).size();
            bookingCountLabel.setText(String.valueOf(bookingCount));
        }
    }
    
    private void showAvailableRooms() {
        refreshAvailableRooms();
        cardLayout.show(mainPanel, "available");
    }
    
    private void showMyBookings() {
        refreshMyBookings();
        cardLayout.show(mainPanel, "bookings");
    }
    
    private void showProfile() {
        updateProfileBookingCount();
        cardLayout.show(mainPanel, "profile");
    }
}