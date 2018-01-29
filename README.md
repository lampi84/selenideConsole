# selenideConsole
A console for trying out selenide commands

## Getting started ###
- Download the latest release jar from this SelenideCode project.
- Download the chromedriver.exe and place them bot in the same folder.
- Start the jar file with double click
- Have fun 

### Possible command which could be entered are:

    open("http://www.google.com")
Open the given url


    $(" a css selector ")
Select a element for one of the following functions


#### Actions on an element (appended with dot)

    .click() 
Performs a click on the selected element

    .shouldHave(" your text to test")
Check if the text matches the inner text of the element (Result shown in log)

    .text()
Will print out the text of the selected element in the console

    .mark()
Marks the selected element in purple


    .setValue(" your text value ")
Enters the given text value into the selected element (should be a textarea or input field)