package javeriana.ms.rest.controller;

import java.util.*;
import javeriana.ms.rest.models.Trip;
import java.sql.*;

public class Db {
  private String url;
  private String user;
  private String pass;

  public Db(String url, String user, String pass) {
    this.url = url;
    this.user = user;
    this.pass = pass;
  }

  private Connection conn() {
    try {
      return DriverManager.getConnection(this.url, this.user, this.pass);
    } catch (SQLException ex) {
      System.out.println(ex.getMessage());
      return null;
    }
  }

  public List<Trip> getAvailableTrips() throws SQLException {
    try {
      Connection conn = this.conn();
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("select * from paseos;");
      List<Trip> trips = new ArrayList<Trip>();
      while (rs.next()) {
        trips.add(new Trip(rs.getString("id"), rs.getString("origen"), rs.getString("destino")));
      }
      return trips;
    } finally {

    }
  }

  public int update(String id, String destino, String origen) throws SQLException {
    try (Connection conn = this.conn()) {
      PreparedStatement stmt = conn.prepareStatement("update paseos set origen=?,destino=? where id= ?");
      stmt.setString(1, origen);
      stmt.setString(2, destino);
      stmt.setString(3, id);
      int rowsDeleted = stmt.executeUpdate();
      if (rowsDeleted > 0) {
        return 200;
      } else {
        return 404;
      }
    }
  }

  public int deleteTrip(String id) throws SQLException {
    try (Connection conn = this.conn()) {
      PreparedStatement stmt = conn.prepareStatement("delete from paseos where id= ?");
      stmt.setInt(1, Integer.valueOf(id));
      int rowsDeleted = stmt.executeUpdate();
      if (rowsDeleted > 0) {
        return 200;
      } else {
        return 404;
      }
    }
  }

  public int createTrip(Trip trip) throws SQLException {
    try (Connection conn = this.conn()) {
      PreparedStatement statement = conn.prepareStatement("insert into paseos(origen,destino) values(?,?)");
      statement.setString(1, trip.origen);
      statement.setString(2, trip.destino);
      int rows = statement.executeUpdate();
      if (rows > 0) {
        return 201;
      }
      return -1;
    }
  }

}
