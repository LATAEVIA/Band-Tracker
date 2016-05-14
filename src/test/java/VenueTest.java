import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;



public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();
  @Test
  public void Venue_instantiatesCorrectly_true() {
    Venue myVenue = new Venue("Thee FourHundred");
    assertEquals(true, myVenue instanceof Venue);
  }

  @Test
  public void getVenueName_venueInstantiatesWithVenueName_String() {
    Venue myVenue = new Venue("Thee FourHundred");
    assertEquals("Thee FourHundred", myVenue.getVenueName());
  }

  @Test
  public void all_emptyAtFirst_0() {
    assertEquals(0, Venue.all().size());
  }

  @Test
  public void equals_returnsTrueIfVenueNamesAretheSame_true() {
    Venue firstVenue = new Venue("Thee FourHundred");
    Venue secondVenue = new Venue("Thee FourHundred");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Venue myVenue = new Venue("Thee FourHundred");
    myVenue.save();
    assertTrue(Venue.all().get(0).equals(myVenue));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Venue myVenue = new Venue("Thee FourHundred");
    myVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(myVenue.getId(), savedVenue.getId());
  }

  @Test
  public void find_findsVenueInDatabase_true() {
    Venue myVenue = new Venue("Thee FourHundred");
    myVenue.save();
    Venue savedVenue = Venue.find(myVenue.getId());
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void addBand_addsBandToVenue() {
    Band myBand = new Band("Thee Epicoders");
    myBand.save();
    Venue myVenue = new Venue("Thee FourHundred");
    myVenue.save();
    myVenue.addBand(myBand);
    Band savedBand = myVenue.getBands().get(0);
    assertTrue(myBand.equals(savedBand));
  }

  @Test
  public void getBandName_returnsAllBands_List() {
    Band myBand = new Band("Thee Epicoders");
    myBand.save();
    Venue myVenue = new Venue("Thee FourHundred");
    myVenue.save();
    myVenue.addBand(myBand);
    List savedBands = myVenue.getBands();
    assertEquals(1, savedBands.size());
  }
}
