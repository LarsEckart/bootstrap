package kata;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {

  private String answer;
  private PhoneBook phoneBook;

  @Given("^a phone book$")
  public void aPhoneBook() throws Throwable {
    phoneBook = new PhoneBook();
  }

  @When("^there are no entries$")
  public void thereAreNoEntries() throws Throwable {
    answer = phoneBook.isConsistent();
  }

  @When("^there is a entry \"([^\"]*)\"$")
  public void thereIsAEntry(String entry) throws Throwable {
    phoneBook.add(entry);
    answer = phoneBook.isConsistent();
  }

  @When("^there is a entry \"([^\"]*)\", \"([^\"]*)\"$")
  public void thereIsAEntry(String entry1, String entry2) throws Throwable {
    phoneBook.add(entry1);
    phoneBook.add(entry2);
    answer = phoneBook.isConsistent();
  }

  @Then("^the phone book is consistent$")
  public void thePhoneBookIsConsistent() throws Throwable {
    assertEquals("consistent", answer);
  }

  @Then("^the phone book is in \"([^\"]*)\"$")
  public void thePhoneBookIsIn(String arg1) throws Throwable {
    assertEquals("prefix error", answer);
  }
}

