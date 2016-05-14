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

  // @Test
  // public void venueIsDiplayedOnBandPageTest() {
  //   goTo("http://localhost:4567/");
  //   click("a", withText("Add a band"));
  //   fill("#bandName").with("Thee Epicoders");
  //   submit(".btn");
  //   goTo("http://localhost:4567/bands");
  //   assertThat(pageSource()).contains("Thee Epicoders");
  // }
}
