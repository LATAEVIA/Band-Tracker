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


}
