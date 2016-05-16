import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import org.sql2o.*;
import org.junit.*;


public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Band Tracker");
  }

  @Test
  public void bandIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Add a band"));
    fill("#bandName").with("Thee Epicoders");
    submit(".btn");
    goTo("http://localhost:4567/bands");
    assertThat(pageSource()).contains("Thee Epicoders");
  }

  @Test
  public void bandIsDisplayedOnItsOwnPageTest() {
    Band testBand = new Band("Thee Epicoders");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    assertThat(pageSource()).contains("Thee Epicoders");
  }

  @Test
  public void venueIsCreatedTest() {
    Band testBand = new Band("Thee Epicoders");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    click("a", withText("Add a venue"));
    fill("#venueName").with("Thee FourHundred");
    submit("#addaVenueToChecklitSubmit");
    assertThat(pageSource()).contains("Thee FourHundred");
  }

  @Test
  public void venueIsDiplayedOnVenuePageTest() {
    Band testBand = new Band("Thee Epicoders");
    testBand.save();
    Venue testVenue = new Venue("Thee FourHundred");
    testVenue.save();
    String url = String.format("http://localhost:4567/bands/%d/venues/new", testBand.getId());
    goTo(url);
    assertThat(pageSource()).contains("Thee FourHundred");
  }

  @Test
  public void venueIsSavedToBandAndDiplayedOnBandPageTest() {
    Band testBand = new Band("Thee Epicoders");
    testBand.save();
    Venue testVenue = new Venue("Thee FourHundred");
    testVenue.save();
    testBand.addVenue(testVenue);
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    assertThat(pageSource()).contains("Thee FourHundred");
  }

  @Test
  public void BandNameIsUpdated() {
    Band testBand = new Band("Thee Epicoders");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    click("a", withText("Edit band"));
    fill("#bandRename").with("Ye old Epigrads");
    submit(".btn");
    goTo(url);
    assertThat(pageSource()).contains("Ye old Epigrads");
  }

  @Test
  public void bandIsDeleted() {
    Band testBand = new Band("Thee Epicoders");
    testBand.save();
    String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
    goTo(url);
    submit("#delete");
    goTo(url);
    assertThat(pageSource()).contains("$band.getBandName()");
  }
// ----------------------------
  // @Test
  // public void venueIsCreatedTest() {
  //   Band testBand = new Band("Thee Epicoders");
  //   testBand.save();
  //   String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
  //   goTo(url);
  //   click("a", withText("Add a venue"));
  //   fill("#venueName").with("Thee FourHundred");
  //   submit("#addaVenueToChecklitSubmit");
  //   assertThat(pageSource()).contains("Thee FourHundred");
  // }
  //
  // @Test
  // public void BandNameIsUpdated() {
  //   Band testBand = new Band("Thee Epicoders");
  //   testBand.save();
  //   String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
  //   goTo(url);
  //   click("a", withText("Edit band"));
  //   fill("#bandRename").with("Ye old Epigrads");
  //   submit(".btn");
  //   goTo(url);
  //   assertThat(pageSource()).contains("Ye old Epigrads");
  // }
  //
  // @Test
  // public void bandIsDeleted() {
  //   Band testBand = new Band("Thee Epicoders");
  //   testBand.save();
  //   String url = String.format("http://localhost:4567/bands/%d", testBand.getId());
  //   goTo(url);
  //   submit("#delete");
  //   goTo(url);
  //   assertThat(pageSource()).contains("$band.getBandName()");
  // }
//   @Test
//   public void bandIsAddedToVenue() {
//     Band testBand = new Band("Thee Epicoders");
//     testBand.save();
//     Venue testVenue = new Venue("Thee FourHundred");
//     testVenue.save();
//     String url = String.format("http://localhost:4567/venues/%d", testVenue.getId());
//     goTo(url);
// `    fillSelect("#band_id").withText("Thee Epicoders");
// `    submit("#band-to-venue");
//     assertThat(pageSource()).contains("<li>");
//     assertThat(pageSource()).contains("Thee Epicoders");
//   }
//
//   @Test
//   public void venueUpdate() {
//     Band myBand = new Band("Home");
//     myBand.save();
//     Venue myVenue = new Venue("Clean");
//     myVenue.save();
//     String venuePath = String.format("http://localhost:4567/bands/%d/venues/%d", myBand.getId(), myVenue.getId());
//     goTo(venuePath);
//     fill("#venueName").with("Dance");
//     submit("#update-venue");
//     assertThat(pageSource()).contains("Dance");
//   }

}
