import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;


public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_instantiatesCorrectly_true() {
    Band myBand = new Band("Thee Epicoders");
    assertEquals(true, myBand instanceof Band);
  }

  @Test
  public void getBandName_bandInstantiatesWithName_String() {
    Band myBand = new Band("Thee Epicoders");
    assertEquals("Thee Epicoders", myBand.getBandName());
  }

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Band.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Band firstBand = new Band("Thee Epicoders");
    Band secondBand = new Band("Thee Epicoders");
    assertTrue(firstBand.equals(secondBand));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Band myBand = new Band("Thee Epicoders");
    myBand.save();
    assertTrue(Band.all().get(0).equals(myBand));
  }

  @Test
  public void save_assignsIdToObject() {
    Band myBand = new Band("Thee Epicoders");
    myBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(myBand.getId(), savedBand.getId());
  }

  @Test
  public void find_findBandInDatabase_true() {
    Band myBand = new Band("Thee Epicoders");
    myBand.save();
    Band savedBand = Band.find(myBand.getId());
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void getVenues_retrievesAllVenuesFromDatabase_venuesList() {
    Band myBand = new Band("Thee Epicoders");
    myBand.save();
    Venue firstVenue = new Venue("Thee FourHundred");
    firstVenue.save();
    myBand.addVenue(firstVenue);
    Venue secondVenue = new Venue("Ye ole Eighth Floor");
    secondVenue.save();
    myBand.addVenue(secondVenue);
    List savedVenues = myBand.getVenues();
    assertEquals(2, savedVenues.size());
  }

  @Test
  public void update_updatesBandName_true() {
    Band testBand = new Band("Thee Epicoders");
    testBand.save();
    testBand.update("Ye old Epigrads");
    assertEquals("Ye old Epigrads", Band.find(testBand.getId()).getBandName());
  }

  @Test
  public void delete_deletesBand_true() {
    Band myBand = new Band("Thee Epicoders");
    myBand.save();
    int myBandId = myBand.getId();
    myBand.delete();
    assertEquals(null, Band.find(myBandId));
  }
}
