package my_sql_java;

import java.sql.*;

public class App {

  private static final String URL = "jdbs.mysgl://localhost::3306/podnik";
  private static final String USER = "root";
  private static final String PASSWORD = "";
  private static final String SELECT_ALL_EMPLOYERS =
    "SELECT id, meno, priezv FROM zam";
  private static final String SELECT_SALLERY_AND_EMPLOYERS =
    "SELECT zam.id, zam.meno, zam.priezv, mzda.mzda FROM zam JOIN mzda ON zam.id = mzda.id";
  private static final String SELECT_EMPLOYER_SALLERY_DEPARTMENT =
    "SELECT meno, priezv, mzda, odd,  FROM zam INNER JOIN mzda ON zam.id = mzda.id INNER JOIN odd ON zam.odd = odd.odd WHERE zam.id = '?'";
  private static final String INSERT_NEW_EMPOLYER =
    "INSERT INTO zam(meno, priezv, dnar, rc, odd) VALUES ('?', '?', '?', '?', '?')";

  public static void main(String[] args) {
    App app = new App();
    Connection connection = null;
    try {
      app.PrintAllEmployers(connection);
      app.PrintEmployersSallery(connection);
      app.printEmployerDetails(connection, 1);
      app.printInsertNewEmployer(
        connection,
        "Janko",
        "Hrasko",
        "1999-01-01",
        "9999999999",
        1
      );
      connection.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public void PrintAllEmployers(Connection connection) throws SQLException {
    connection = DriverManager.getConnection(URL, USER, PASSWORD);
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(SELECT_ALL_EMPLOYERS);
    while (resultSet.next()) {
      int id = resultSet.getInt("id");
      String meno = resultSet.getString("meno");
      String priezv = resultSet.getString("priezv");

      System.out.println(id + " " + meno + " " + priezv);
    }
  }

  public void PrintEmployersSallery(Connection connection) throws SQLException {
    connection = DriverManager.getConnection(URL, USER, PASSWORD);
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery(SELECT_SALLERY_AND_EMPLOYERS);
    while (resultSet.next()) {
      int id = resultSet.getInt("id");
      String meno = resultSet.getString("meno");
      String priezv = resultSet.getString("priezv");
      int mzda = resultSet.getInt("mzda");

      System.out.println(id + " " + meno + " " + priezv + " " + mzda);
    }
  }

  private void printEmployerDetails(Connection connection, int id)
    throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(
      SELECT_EMPLOYER_SALLERY_DEPARTMENT
    );
    preparedStatement.setInt(1, id);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
      String meno = resultSet.getString("meno");
      String priezv = resultSet.getString("priezv");
      int mzda = resultSet.getInt("mzda");
      int odd = resultSet.getInt("odd");

      System.out.println(meno + " " + priezv + " " + mzda + " " + odd);
    } else {
      System.out.println("Zamestnanec s id " + id + " neexistuje");
    }
  }

  public void printInsertNewEmployer(
    Connection connection,
    String meno,
    String priezv,
    String dnar,
    String rc,
    int odd
  ) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(
      INSERT_NEW_EMPOLYER
    );
    preparedStatement.setString(1, meno);
    preparedStatement.setString(2, priezv);
    preparedStatement.setString(3, dnar);
    preparedStatement.setString(4, rc);
    preparedStatement.setInt(5, odd);
    int result = preparedStatement.executeUpdate();
    System.out.println("result: " + result);
  }
}
