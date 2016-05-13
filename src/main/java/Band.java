import java.util.List;
import org.sql2o.*;
import java.util.ArrayList;

public class Band {
  private int id;
  private String band_name;

  public Band(String band_name) {
    this.band_name = band_name;
  }

  public String getBandName() {
    return band_name;
  }

  public int getId() {
    return id;
  }

  public static List<Band> all() {
    String sql = "SELECT id, band_name FROM bands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  @Override
  public boolean equals(Object otherBand) {
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getBandName().equals(newBand.getBandName()) &&
             this.getId() == newBand.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands(band_name) VALUES (:band_name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("band_name", this.band_name)
        .executeUpdate()
        .getKey();
    }
  }

  // public static Band find(int id) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM bands where id=:id";
  //     Band band = con.createQuery(sql)
  //       .addParameter("id", id)
  //       .executeAndFetchFirst(Band.class);
  //     return band;
  //   }
  // }
  //
  // public List<Client> getClients() {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM clients where bandId=:id";
  //     return con.createQuery(sql)
  //       .addParameter("id", this.id)
  //       .executeAndFetch(Client.class);
  //   }
  // }
}
