import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

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

  // @Test
  // public void save_assignsIdToObject() {
  //   Band myBand = new Band("Thee Epicoders");
  //   myBand.save();
  //   Band savedBand = Band.all().get(0);
  //   assertEquals(myBand.getId(), savedBand.getId());
  // }
  //
  // @Test
  // public void find_findBandInDatabase_true() {
  //   Band myBand = new Band("Thee Epicoders");
  //   myBand.save();
  //   Band savedBand = Band.find(myBand.getId());
  //   assertTrue(myBand.equals(savedBand));
  // }
  //
  // @Test
  // public void getClients_retrievesALlClientsFromDatabase_clientsList() {
  //   Band myBand = new Band("Thee Epicoders");
  //   myBand.save();
  //   Client firstClient = new Client("Mow the lawn", myBand.getId());
  //   firstClient.save();
  //   Client secondClient = new Client("Do the dishes", myBand.getId());
  //   secondClient.save();
  //   Client[] clients = new Client[] { firstClient, secondClient };
  //   assertTrue(myBand.getClients().containsAll(Arrays.asList(clients)));
  // }
}
