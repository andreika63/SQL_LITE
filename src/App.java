import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by kay on 17.09.2016.
 */
public class App {
    final static String charset = "UTF8"; //CP1251
    final static String filename = "List.csv";
    final static String spliter = "\t";
    final static String driver = "org.sqlite.JDBC";
    final static String conString = "jdbc:sqlite:test.db";
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement ps = null;
        Statement stmt = null;
        FReader fr = new FReader(filename,charset);
        ArrayList<String[]> arlist = fr.readToAL(spliter); //читаем весь файл в память, ну и что ее может и не хватить :)
        if (arlist.size() == 0) System.exit(-1);


        try { //Загружаем драйвер, если драйвер не грузится, дальше выолнять код нет смысла
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        try {
            conn = DriverManager.getConnection(conString);
            stmt = conn.createStatement();
            stmt.execute("delete from LIST"); // Очищаем временную таблицу
            conn.setAutoCommit(false); // Выключаем AutoCommit - с ним работает очень медленно
            ps = conn.prepareStatement("insert or replace into LIST(G1,G2,G3,G4,G5,NAME,COD,ART,FNAME,PRICE) values(?,?,?,?,?,?,?,?,?,?)");
            for (int x = 1; x < arlist.size(); x++) {
                for (int i = 0; i < arlist.get(x).length; i++) {
                    ps.setString(i+1,arlist.get(x)[i]);
                }
                ps.executeUpdate();
            }

            //добавим новые строки в таблицу TOVAR (PK = COD) или обновим строки где поменялись данные
            int r = stmt.executeUpdate("insert or replace into TOVAR select * from LIST  except select * from TOVAR");
            System.out.println(r + " rows inserted or updated");

            conn.commit(); // Подтверждение всех изменений в базе
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    if (stmt != null) stmt.close();
                    if (ps != null) ps.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            };
        }
    }

}
