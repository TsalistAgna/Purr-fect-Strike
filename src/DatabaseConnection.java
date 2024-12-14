import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/purrfect_strike";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static ArrayList<Asset> getCats() {
        ArrayList<Asset> cats = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String query = "SELECT nama, path_image FROM kucing_image";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                cats.add(new Asset(rs.getString("nama"), rs.getString("path_image")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cats;
    }

    public static ArrayList<String> getCatsImagePaths() {
        ArrayList<String> catPaths = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String query = "SELECT path_image FROM kucing_image"; // Ambil hanya path gambar
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                catPaths.add(rs.getString("path_image"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catPaths;
    }
    

    public static ArrayList<String> getMiceImagePaths() {
        ArrayList<String> micePaths = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String query = "SELECT path_image FROM tikus_image";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                micePaths.add(rs.getString("path_image"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return micePaths;
    }

    public static ArrayList<Score> getTopScores() throws SQLException {
        ArrayList<Score> scores = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String query = "SELECT nama, score FROM players ORDER BY score DESC LIMIT 10";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String playerName = rs.getString("nama");
                int score = rs.getInt("score");
                scores.add(new Score(playerName, score));
            }
        }
        return scores;
    }

    public static void savePlayerScore(String playerName, int score) throws SQLException {
        String query = "INSERT INTO players (nama, score) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, playerName);
            stmt.setInt(2, score);
            stmt.executeUpdate();
        }
    }
}
