package ui;

import javax.swing.*;
import java.awt.*;
import services.DatabaseService;

public class LoginUI extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberCheckbox;
    private DatabaseService db;
    
    // Color constants
    private final Color GOLD_COLOR = new Color(255, 215, 0);
    private final Color BLUE_BG = new Color(33, 150, 243);
    private final Color BLACK_BORDER = new Color(0, 0, 0);
    private final Color BLUE_TEXT = new Color(33, 150, 243);
    private final Color WHITE_TEXT = new Color(255, 255, 255);
    
    public LoginUI() {
        db = DatabaseService.getInstance();
        initUI();
    }
    
    private void initUI() {
        setTitle("Hotel Booking System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Main panel with background image
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            private Image backgroundImage;
            {
                try {
                    ImageIcon icon = new ImageIcon("images/hotel-bg (2).png");
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
                    g.setColor(new Color(33, 150, 243));
                    g.fillRect(0, 0, getWidth(), getHeight());
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
        
        // Login Form Panel - Blue-black color with rounded corners and golden border
        JPanel loginPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw golden border
                g2d.setColor(GOLD_COLOR);
                g2d.setStroke(new BasicStroke(3));
                g2d.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, 25, 25);
                
                // Fill background
                g2d.setColor(new Color(20, 30, 55, 180));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
            }
        };
        loginPanel.setOpaque(false);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints rGbc = new GridBagConstraints();
        rGbc.insets = new Insets(10, 15, 10, 15);
        
        // Welcome text - GOLDEN
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(GOLD_COLOR);
        rGbc.gridx = 0; rGbc.gridy = 0;
        rGbc.gridwidth = 2;
        loginPanel.add(welcomeLabel, rGbc);
        
        // Sign in text - WHITE
        JLabel signLabel = new JLabel("Sign in to continue");
        signLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        signLabel.setForeground(WHITE_TEXT);
        rGbc.gridy = 1;
        loginPanel.add(signLabel, rGbc);
        
        // Username - BLUE
        rGbc.gridy = 2;
        rGbc.gridwidth = 1;
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userLabel.setForeground(BLUE_TEXT);
        loginPanel.add(userLabel, rGbc);
        
        rGbc.gridx = 1;
        usernameField = new JTextField(15);
        usernameField.setBackground(BLUE_BG);
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BLACK_BORDER, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        loginPanel.add(usernameField, rGbc);
        
        // Password - BLUE
        rGbc.gridx = 0; rGbc.gridy = 3;
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Arial", Font.BOLD, 12));
        passLabel.setForeground(BLUE_TEXT);
        loginPanel.add(passLabel, rGbc);
        
        rGbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.setBackground(BLUE_BG);
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BLACK_BORDER, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        loginPanel.add(passwordField, rGbc);
        
        // Remember me - BLUE
        rGbc.gridx = 0; rGbc.gridy = 4;
        rememberCheckbox = new JCheckBox("Remember me");
        rememberCheckbox.setOpaque(false);
        rememberCheckbox.setForeground(BLUE_TEXT);
        loginPanel.add(rememberCheckbox, rGbc);
        
        // Login Button - Golden background with blue text
        rGbc.gridx = 0; rGbc.gridy = 5;
        rGbc.gridwidth = 2;
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBackground(GOLD_COLOR);
        loginBtn.setForeground(BLUE_TEXT);
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setPreferredSize(new Dimension(200, 45));
        loginBtn.setBorder(BorderFactory.createLineBorder(BLACK_BORDER, 2));
        loginBtn.addActionListener(e -> login());
        loginPanel.add(loginBtn, rGbc);
        
        // OR Label - BLUE
        rGbc.gridy = 6;
        JLabel orLabel = new JLabel("OR");
        orLabel.setForeground(BLUE_TEXT);
        loginPanel.add(orLabel, rGbc);
        
        // Sign Up Button - Golden background with blue text
        rGbc.gridy = 7;
        JButton signupBtn = new JButton("CREATE NEW ACCOUNT");
        signupBtn.setBackground(GOLD_COLOR);
        signupBtn.setForeground(BLUE_TEXT);
        signupBtn.setFont(new Font("Arial", Font.BOLD, 14));
        signupBtn.setFocusPainted(false);
        signupBtn.setPreferredSize(new Dimension(200, 45));
        signupBtn.setBorder(BorderFactory.createLineBorder(BLACK_BORDER, 2));
        signupBtn.addActionListener(e -> openSignup());
        loginPanel.add(signupBtn, rGbc);
        
        mainPanel.add(loginPanel, gbc);
        
        add(mainPanel);
    }
    
    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password");
            return;
        }
        
        var user = db.findUser(username, password);
        
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Welcome " + user.getName() + "!");
            new CustomerDashboard(user).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        }
    }
    
    private void openSignup() {
        new SignupUI().setVisible(true);
        dispose();
    }
}