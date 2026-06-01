package ui;

import javax.swing.*;
import java.awt.*;
import services.DatabaseService;
import models.User;

public class SignupUI extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JPasswordField confirmPasswordField;
    private DatabaseService db;
    
    // Color constants
    private final Color GOLD_COLOR = new Color(255, 215, 0);
    private final Color DARK_BLUE = new Color(25, 118, 210);
    private final Color LIGHT_BLUE = new Color(33, 150, 243);
    
    public SignupUI() {
        db = DatabaseService.getInstance();
        initUI();
    }
    
    private void initUI() {
        setTitle("Hotel Booking System - Create Account");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Main panel with background image
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            private Image backgroundImage;
            {
                try {
                    ImageIcon icon = new ImageIcon("images/hotel-bg.jpg");
                    backgroundImage = icon.getImage();
                } catch (Exception e) {
                    backgroundImage = null;
                }
            }
            
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    Graphics2D g2d = (Graphics2D) g;
                    GradientPaint gp = new GradientPaint(0, 0, new Color(20, 30, 55), 0, getHeight(), new Color(33, 150, 243));
                    g2d.setPaint(gp);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.95;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 30);
        
        // Signup Form Panel - More transparent
        JPanel signupPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw golden border
                g2d.setColor(GOLD_COLOR);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 25, 25);
                
                // Fill background - More transparent (alpha 150 instead of 230)
                GradientPaint gp = new GradientPaint(0, 0, new Color(20, 30, 55, 150), 0, getHeight(), new Color(33, 150, 243, 130));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };
        signupPanel.setOpaque(false);
        signupPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        signupPanel.setLayout(new GridBagLayout());
        GridBagConstraints rGbc = new GridBagConstraints();
        rGbc.insets = new Insets(8, 15, 8, 15);
        
        // Title - Dark Blue (removed icon)
        rGbc.gridx = 0; rGbc.gridy = 0;
        rGbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Create New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 26));
        titleLabel.setForeground(DARK_BLUE);
        signupPanel.add(titleLabel, rGbc);
        
        rGbc.gridy = 1;
        JLabel subtitleLabel = new JLabel("Join us and start booking your dream stay");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitleLabel.setForeground(new Color(220, 220, 230));
        signupPanel.add(subtitleLabel, rGbc);
        
        // Full Name
        rGbc.gridy = 2;
        rGbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
        nameLabel.setForeground(GOLD_COLOR);
        signupPanel.add(nameLabel, rGbc);
        
        rGbc.gridx = 1;
        nameField = new JTextField(15);
        nameField.setBackground(new Color(255, 255, 255, 220));
        nameField.setForeground(Color.BLACK);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        signupPanel.add(nameField, rGbc);
        
        // Username
        rGbc.gridx = 0; rGbc.gridy = 3;
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userLabel.setForeground(GOLD_COLOR);
        signupPanel.add(userLabel, rGbc);
        
        rGbc.gridx = 1;
        usernameField = new JTextField(15);
        usernameField.setBackground(new Color(255, 255, 255, 220));
        usernameField.setForeground(Color.BLACK);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        signupPanel.add(usernameField, rGbc);
        
        // Password
        rGbc.gridx = 0; rGbc.gridy = 4;
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Arial", Font.BOLD, 12));
        passLabel.setForeground(GOLD_COLOR);
        signupPanel.add(passLabel, rGbc);
        
        rGbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.setBackground(new Color(255, 255, 255, 220));
        passwordField.setForeground(Color.BLACK);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        signupPanel.add(passwordField, rGbc);
        
        // Confirm Password
        rGbc.gridx = 0; rGbc.gridy = 5;
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 12));
        confirmLabel.setForeground(GOLD_COLOR);
        signupPanel.add(confirmLabel, rGbc);
        
        rGbc.gridx = 1;
        confirmPasswordField = new JPasswordField(15);
        confirmPasswordField.setBackground(new Color(255, 255, 255, 220));
        confirmPasswordField.setForeground(Color.BLACK);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD_COLOR, 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        signupPanel.add(confirmPasswordField, rGbc);
        
        // Sign Up Button
        rGbc.gridx = 0; rGbc.gridy = 6;
        rGbc.gridwidth = 2;
        JButton signupBtn = new JButton("CREATE ACCOUNT");
        signupBtn.setBackground(GOLD_COLOR);
        signupBtn.setForeground(Color.BLACK);
        signupBtn.setFont(new Font("Arial", Font.BOLD, 14));
        signupBtn.setFocusPainted(false);
        signupBtn.setPreferredSize(new Dimension(220, 48));
        signupBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupBtn.addActionListener(e -> signup());
        signupPanel.add(signupBtn, rGbc);
        
        // OR Label
        rGbc.gridy = 7;
        JLabel orLabel = new JLabel("OR");
        orLabel.setForeground(GOLD_COLOR);
        orLabel.setFont(new Font("Arial", Font.BOLD, 12));
        signupPanel.add(orLabel, rGbc);
        
        // Back to Login Button - Blue background
        rGbc.gridy = 8;
        JButton backBtn = new JButton("Back to Login");
        backBtn.setBackground(LIGHT_BLUE);
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Arial", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        backBtn.setPreferredSize(new Dimension(220, 45));
        backBtn.setBorder(BorderFactory.createLineBorder(new Color(25, 118, 210), 1));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> goBack());
        signupPanel.add(backBtn, rGbc);
        
        mainPanel.add(signupPanel, gbc);
        
        add(mainPanel);
    }
    
    private void signup() {
        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        
        // Validation
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill all fields", 
                "Missing Information", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, 
                "Passwords do not match!", 
                "Password Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, 
                "Password must be at least 4 characters long", 
                "Weak Password", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (db.usernameExists(username)) {
            JOptionPane.showMessageDialog(this, 
                "Username '" + username + "' already exists!\nPlease choose a different username.", 
                "Username Taken", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User newUser = new User(username, password, name);
        
        if (db.saveUser(newUser)) {
            JOptionPane.showMessageDialog(this, 
                "✓ ACCOUNT CREATED SUCCESSFULLY!\n\n" +
                "Welcome " + name + "!\n" +
                "You can now log in with your credentials.", 
                "Welcome Aboard", 
                JOptionPane.INFORMATION_MESSAGE);
            goBack();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error creating account. Please try again.", 
                "Registration Failed", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void goBack() {
        new LoginUI().setVisible(true);
        dispose();
    }
}