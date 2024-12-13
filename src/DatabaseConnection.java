import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/purrfect_strike";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws Exception {
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

    public static ArrayList<Asset> getMice() {
        ArrayList<Asset> mice = new ArrayList<>();
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String query = "SELECT nama, path_image FROM tikus_image";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                mice.add(new Asset(rs.getString("nama"), rs.getString("path_image")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mice;
    }

    public static ArrayList<Score> getTopScores() throws Exception {
    ArrayList<Score> scores = new ArrayList<>();

    try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
        String query = "SELECT nama, score FROM players ORDER BY score DESC LIMIT 10";
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            String playerName = rs.getString("nama");
            int score = rs.getInt("score");
            scores.add(new Score(playerName, score));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return scores;
}

    
}
