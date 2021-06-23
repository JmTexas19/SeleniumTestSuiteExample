# SeleniumTestSuiteExample
Test Suite created to practice proficiency with Selenium. Created using industry standard POM. Built using Maven Project Structure

Libraries:
GSON
Selenium
JUnit5
httpclient

Process:
* Logs into system with paramaterized username and password.
* Navigates to desired page with required data.
* Uses query's from URL from previous page to call API endpoint for retrieving Sales Data.
* Navigates to page for inputting Sales data and enters paramaterized value.
* Checks REST API Endpoint once again to confirm data integrity of inputted data.
* Logs each page to assure process can be followed by tester.
* Logs any JS Errors encountered during test and logs as ERROR.
