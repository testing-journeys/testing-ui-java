## Approaches for automating E2E tests (examples for GUI)

### Capture and replay

In capture/playback approaches, tools are used to capture interactions with the SUT 
while performing the sequence of actions as defined by a test procedure/scenario.

[Check out this example](src/test/java/org/testing/journeys/ui/a_captureplay/ExploreCaliforniaTest.side)

### Linear scripting

Linear scripting approach uses a scripting (programming) language and an interface 
controller (test tool) to describe the actions and interaction elements to navigate
the user through a sequence of steps. This approach  (usually) does not follow any 
abstraction concepts or reuse patterns - that is why this approach is not recommended
when a large suite of scripts is to be developed (high maintenance cost).

[Check out this example](src/test/java/org/testing/journeys/ui/b_linear/ExploreCaliforniaTest.java)
```java
    @Test
    public void shouldNavigateToContactPage(ChromeDriver driver) {

        driver.get("https://www.explorecalifornia.org/index.htm");

        driver.findElement(By.xpath(".//*[@id='siteNav']/ul/li[5]")).click();

        Assertions.assertEquals("A little about us...", driver.getTitle(),
                "Navigation: Incorrect page");
    }
```

### Structure scripting

Compared to linear scripting in structured scripting technique we see some levels of 
reuse and abstraction. These elements get usually transposed into a script library. 
The library contains reusable scripts that perform sequences of instructions that 
are commonly required across a number of tests (interaction and data processing related).

[Check out this example](src/test/java/org/testing/journeys/ui/c_structured/ExploreCaliforniaTest.java)

```java
    @Test
    public void shouldFillInContactPageForm(ChromeDriver driver) {

        goToPage(driver, Pages.HOME_PAGE_URL);
        clickOn(driver, Pages.ContactPage.MENU_ITEM);
        Assertions.assertEquals(Pages.ContactPage.TITLE, driver.getTitle(), "Navigation: Incorrect page");

        type(driver, Pages.ContactPage.NAME_TEXTFIELD, "John Donovan");
        type(driver, Pages.ContactPage.EMAIL_TEXTFIELD, "John.Donovan@outlook.com");
        type(driver, Pages.ContactPage.PHONE_TEXTFIELD, "403 233 2332");
        type(driver, Pages.ContactPage.ADDRESS_TEXTFIELD, "34th Street Ave NE, Seattle");
        selectByValue(driver, Pages.ContactPage.STATE_DROPDOWN, "WA");
        type(driver, Pages.ContactPage.ZIPCODE_TEXTFIELD, "98034");
    }
```

### Data-driven testing

The data-driven scripting technique builds on the structured scripting technique. The 
most significant difference is how the test inputs are handled. The inputs are extracted 
from the scripts and put into one or more separate files (typically called _data files_). 

This means the main test script (control script) can be reused to implement a number of 
tests (rather than just a single test). The control script contains the sequence of 
instructions necessary to perform the tests but the input data from a data file.

[Check out this example](src/test/java/org/testing/journeys/ui/d_datadriven/ExploreCaliforniaTest.java)

```java
    @ParameterizedTest(name = "{index}: Sending feedback from {0}")
    @CsvSource({
            "John Donovan,  John.Donovan@outlook.com,   403 233 2332,   '34th Street Ave NE, Seattle',  WA, 98034",
            "Helen Donovan, Helen.Donovan@outlook.com,  403 231 2334,   '32th Street Ave NE, Seattle',  WA, 98033"
    })
    public void shouldFillInContactPageForm(String name, String email, String phone,
                                            String address, String state, String zip,
                                            ChromeDriver driver) {

        goToPage(driver, Pages.HOME_PAGE_URL);
        clickOn(driver, Pages.ContactPage.MENU_ITEM);
        Assertions.assertEquals(Pages.ContactPage.TITLE, driver.getTitle(), "Navigation: Incorrect page");

        type(driver, Pages.ContactPage.NAME_TEXTFIELD, name);
        type(driver, Pages.ContactPage.EMAIL_TEXTFIELD, email);
        type(driver, Pages.ContactPage.PHONE_TEXTFIELD, phone);
        type(driver, Pages.ContactPage.ADDRESS_TEXTFIELD, address);
        selectByValue(driver, Pages.ContactPage.STATE_DROPDOWN, state);
        type(driver, Pages.ContactPage.ZIPCODE_TEXTFIELD, zip);
    }
```

### Keyword-driven testing

Keyword-driven testing is a technique to automate test cases that builds on the following 
principles:
- the interactions with the system under test are similar in nature but with different data sets
- test controller implements logic for all possible interactions with the system under test
which could be technical (e.g. click, type) and business in nature (e.g. place order)
- test analyst defined test scripts using _action words_ and _test data_ and serve them to 
test controller for execution.

[Check out this example - control script](src/test/java/org/testing/journeys/ui/e_keyworddriven/ExploreCaliforniaTest.java)

```java
    @Test
    @JsonScenario("ExploreCalifornia.yml")
    @ExtendWith(JsonScenarioParameterResolver.class)
    void shouldFillInContactPageForm(Scenario scenario, ChromeDriver driver) {
        scenario.getSteps().forEach(step -> ActionMapper.execute(driver, step));
    }
```
[Check out this example - test script](src/test/resources/ExploreCalifornia.yml)

```yml
name: Explore California
steps:
  - action: goto
    params:
      target: https://www.explorecalifornia.org/index.htm

  - action: click
    params:
      criteria: byXpath
      target: ".//*[@id='siteNav']/ul/li[5]"

  - action: type
    params:
      criteria: byId
      target: name
      value: John Donovan

  - action: selectByValue
    params:
      criteria: byId
      target: state
      value: WA
```


### Process-driven scripting

The process-driven approach builds on the keyword-driven scripting technique with the difference 
that scenarios – representing uses cases of the SUT and variants thereof – constitute the scripts 
which are parameterized with test data or combined into higher-level test definitions

An example of such scenario is given bellow:

```gherkin
    Feature: Explore California
    
      As a web site visitor
      I want to leave my contact details
      So that I could be notified when new trips are planned
    
      Scenario: User leaves contact details
        Given 'Mark Donovan' is a new customer
        When he navigates to 'Contact' page
        Then should be able to leave his contact details
```

This scenario follows a Gherkin definition style and the steps implementation is done using Cucumber tool