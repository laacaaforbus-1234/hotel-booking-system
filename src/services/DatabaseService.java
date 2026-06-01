package services;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import models.*;

public class DatabaseService {
    
    private static final String DB_URL = "jdbc:sqlite:database/hotel.db";
    private static DatabaseService instance;
    
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver error: " + e.getMessage());
        }
    }
    
    private DatabaseService() {
        createTables();
        insertSampleRooms();
    }
    
    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    
    private void createTables() {
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password TEXT NOT NULL,
                name TEXT NOT NULL
            )
        """;
        
        String createRoomsTable = """
            CREATE TABLE IF NOT EXISTS rooms (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                room_number INTEGER UNIQUE NOT NULL,
                room_type TEXT NOT NULL,
                base_rate REAL NOT NULL,
                is_available INTEGER NOT NULL DEFAULT 1
            )
        """;
        
        String createBookingsTable = """
            CREATE TABLE IF NOT EXISTS bookings (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                booking_id TEXT UNIQUE NOT NULL,
                user_id INTEGER NOT NULL,
                room_id INTEGER NOT NULL,
                start_date TEXT NOT NULL,
                end_date TEXT NOT NULL,
                total_price REAL NOT NULL,
                status TEXT NOT NULL,
                FOREIGN KEY (user_id) REFERENCES users(id),
                FOREIGN KEY (room_id) REFERENCES rooms(id)
            )
        """;
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.execute(createUsersTable);
            stmt.execute(createRoomsTable);
            stmt.execute(createBookingsTable);
            
        } catch (SQLException e) {
            System.out.println("❌ Table error: " + e.getMessage());
        }
    }
    
    private void insertSampleRooms() {
        // First, clear existing rooms to ensure fresh insertion
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM rooms");
            System.out.println("✅ Cleared existing rooms");
        } catch (SQLException e) {
            System.out.println("❌ Error clearing rooms: " + e.getMessage());
        }
        
        String insertQuery = "INSERT INTO rooms (room_number, room_type, base_rate, is_available) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
            
            // ========== 12 STANDARD ROOMS ==========
            // Rooms 101-112
            pstmt.setInt(1, 101); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 150); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 102); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 160); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 103); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 155); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 104); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 170); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 105); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 120); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 106); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 185); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 107); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 190); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 108); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 140); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 109); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 175); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 110); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 165); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 111); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 130); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 112); pstmt.setString(2, "Standard"); pstmt.setDouble(3, 180); pstmt.setInt(4, 1); pstmt.addBatch();
            
            // ========== 15 SUITE ROOMS ==========
            // Rooms 201-215
            pstmt.setInt(1, 201); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 300); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 202); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 320); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 203); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 310); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 204); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 600); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 205); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 350); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 206); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 410); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 207); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 335); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 208); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 370); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 209); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 530); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 210); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 305); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 211); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 450); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 212); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 380); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 213); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 420); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 214); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 500); pstmt.setInt(4, 1); pstmt.addBatch();
            pstmt.setInt(1, 215); pstmt.setString(2, "Suite"); pstmt.setDouble(3, 290); pstmt.setInt(4, 1); pstmt.addBatch();
            
            int[] results = pstmt.executeBatch();
            System.out.println("✅ Inserted " + results.length + " rooms (12 Standard + 15 Suite)");
            
        } catch (SQLException e) {
            System.out.println("❌ Insert error: " + e.getMessage());
        }
    }
    
    // ========== USER OPERATIONS ==========
    
    public boolean saveUser(User user) {
        String query = "INSERT INTO users (username, password, name) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public User findUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("name")
                );
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Login error: " + e.getMessage());
        }
        
        return null;
    }
    
    public boolean usernameExists(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
            
        } catch (SQLException e) {
            return true;
        }
    }
    
    // ========== ROOM OPERATIONS ==========
    
    public List<Room> getAvailableRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM rooms WHERE is_available = 1 ORDER BY room_number";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                int roomNumber = rs.getInt("room_number");
                String roomType = rs.getString("room_type");
                double baseRate = rs.getDouble("base_rate");
                
                Room room;
                if (roomType.equals("Standard")) {
                    room = new StandardRoom(roomNumber);
                } else {
                    room = new SuiteRoom(roomNumber);
                }
                room.setBaseRate(baseRate);
                room.setAvailable(true);
                rooms.add(room);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error getting rooms: " + e.getMessage());
        }
        
        return rooms;
    }
    
    public Room getRoomByNumber(int roomNumber) {
        String query = "SELECT * FROM rooms WHERE room_number = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, roomNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String roomType = rs.getString("room_type");
                double baseRate = rs.getDouble("base_rate");
                boolean isAvailable = rs.getInt("is_available") == 1;
                
                Room room;
                if (roomType.equals("Standard")) {
                    room = new StandardRoom(roomNumber);
                } else {
                    room = new SuiteRoom(roomNumber);
                }
                room.setBaseRate(baseRate);
                room.setAvailable(isAvailable);
                return room;
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        
        return null;
    }
    
    public boolean updateRoomAvailability(int roomNumber, boolean isAvailable) {
        String query = "UPDATE rooms SET is_available = ? WHERE room_number = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, isAvailable ? 1 : 0);
            pstmt.setInt(2, roomNumber);
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    // ========== BOOKING OPERATIONS ==========
    
    public boolean saveBooking(Booking booking) {
        String query = "INSERT INTO bookings (booking_id, user_id, room_id, start_date, end_date, total_price, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection()) {
            
            int userId = getUserIdByUsername(conn, booking.getCustomer().getUsername());
            if (userId == -1) return false;
            
            int roomId = getRoomIdByNumber(conn, booking.getRoom().getRoomNumber());
            if (roomId == -1) return false;
            
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, booking.getBookingId());
                pstmt.setInt(2, userId);
                pstmt.setInt(3, roomId);
                pstmt.setString(4, booking.getStartDate().toString());
                pstmt.setString(5, booking.getEndDate().toString());
                pstmt.setDouble(6, booking.getTotalPrice());
                pstmt.setString(7, booking.getStatus());
                pstmt.executeUpdate();
                return true;
            }
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    public List<Booking> getBookingsByUser(String username) {
        List<Booking> bookings = new ArrayList<>();
        String query = """
            SELECT b.*, u.name as user_name, r.room_number, r.room_type, r.base_rate 
            FROM bookings b
            JOIN users u ON b.user_id = u.id
            JOIN rooms r ON b.room_id = r.id
            WHERE u.username = ? AND b.status = 'ACTIVE'
            ORDER BY b.start_date DESC
        """;
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                String bookingId = rs.getString("booking_id");
                int roomNumber = rs.getInt("room_number");
                String roomType = rs.getString("room_type");
                double baseRate = rs.getDouble("base_rate");
                LocalDate startDate = LocalDate.parse(rs.getString("start_date"));
                LocalDate endDate = LocalDate.parse(rs.getString("end_date"));
                double totalPrice = rs.getDouble("total_price");
                String status = rs.getString("status");
                String userName = rs.getString("user_name");
                
                User user = new User(username, "", userName);
                
                Room room;
                if (roomType.equals("Standard")) {
                    room = new StandardRoom(roomNumber);
                } else {
                    room = new SuiteRoom(roomNumber);
                }
                room.setBaseRate(baseRate);
                
                Booking booking = new Booking(user, room, startDate, endDate);
                booking.setBookingId(bookingId);
                booking.setTotalPrice(totalPrice);
                booking.setStatus(status);
                
                bookings.add(booking);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
        
        return bookings;
    }
    
    public boolean cancelBooking(String bookingId) {
        String query = "UPDATE bookings SET status = 'CANCELLED' WHERE booking_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, bookingId);
            
            // Also make the room available again
            String getRoomQuery = "SELECT room_id FROM bookings WHERE booking_id = ?";
            try (PreparedStatement pstmt2 = conn.prepareStatement(getRoomQuery)) {
                pstmt2.setString(1, bookingId);
                ResultSet rs = pstmt2.executeQuery();
                if (rs.next()) {
                    int roomId = rs.getInt("room_id");
                    String updateRoom = "UPDATE rooms SET is_available = 1 WHERE id = ?";
                    try (PreparedStatement pstmt3 = conn.prepareStatement(updateRoom)) {
                        pstmt3.setInt(1, roomId);
                        pstmt3.executeUpdate();
                    }
                }
            }
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            return false;
        }
    }
    
    private int getUserIdByUsername(Connection conn, String username) throws SQLException {
        String query = "SELECT id FROM users WHERE username = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }
    
    private int getRoomIdByNumber(Connection conn, int roomNumber) throws SQLException {
        String query = "SELECT id FROM rooms WHERE room_number = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, roomNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }
}