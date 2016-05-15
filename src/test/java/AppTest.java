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
}

//
// @Test
// public void categoryShowPageDisplaysName() {
//   Category testCategory = new Category("Household chores");
//   testCategory.save();
//   String url = String.format("http://localhost:4567/categories/%d", testCategory.getId());
//   goTo(url);
//   assertThat(pageSource()).contains("Household chores");
// }
//
// @Test
// public void taskShowPageDisplaysDescription() {
//   Task testTask = new Task("Mow the lawn");
//   testTask.save();
//   String url = String.format("http://localhost:4567/tasks/%d", testTask.getId());
//   goTo(url);
//   assertThat(pageSource()).contains("Mow the lawn");
// }
//
// @Test
// public void taskIsAddedToCategory() {
//   Category testCategory = new Category("Household chores");
//   testCategory.save();
//   Task testTask = new Task("Mow the lawn");
//   testTask.save();
//   String url = String.format("http://localhost:4567/categories/%d", testCategory.getId());
//   goTo(url);
//   fillSelect("#task_id").withText("Mow the lawn");
//   submit(".btn");
//   assertThat(pageSource()).contains("<li>");
//   assertThat(pageSource()).contains("Mow the lawn");
// }
//
// @Test
// public void categoryIsAddedToTask() {
//   Category testCategory = new Category("Household chores");
//   testCategory.save();
//   Task testTask = new Task("Mow the lawn");
//   testTask.save();
//   String url = String.format("http://localhost:4567/tasks/%d", testTask.getId());
//   goTo(url);
//   fillSelect("#category_id").withText("Household chores");
//   submit("#category-to-task");
//   assertThat(pageSource()).contains("<li>");
//   assertThat(pageSource()).contains("Household chores");
// }
//
// // @Test
// // public void taskUpdate() {
// //   Category myCategory = new Category("Home");
// //   myCategory.save();
// //   Task myTask = new Task("Clean");
// //   myTask.save();
// //   String taskPath = String.format("http://localhost:4567/categories/%d/tasks/%d", myCategory.getId(), myTask.getId());
// //   goTo(taskPath);
// //   fill("#description").with("Dance");
// //   submit("#update-task");
// //   assertThat(pageSource()).contains("Dance");
// // }
// }

// onsubmit="
//   if (this.venueCheckbox.checked == false) {
//      alert ('You must select a venue first.');
//      return false;
//    } else {
//      return true;
//    }"
//
// onsubmit="
//   if(this.venueCheckbox.checked){
//     return true;
//   } else {
//     alert('You must select a venue first.');
//     return false;
//   }"
