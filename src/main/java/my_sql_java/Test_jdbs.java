package my_sql_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test_jdbs {

  private static final String URL = "jdbs.mysgl://localhost::3306/podnik";
  private static final String USER = "root";
  private static final String PASSWORD = "";

  public static void main(String[] args) {
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println("Connection succesfull!");
    } catch (SQLException e) {
      System.out.println(e);
    }
  }
}
