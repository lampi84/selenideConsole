/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.palamb.testing;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author patric
 */
public class SelenideCommandInterpreter {
    
    private static SelenideCommandInterpreter instance;
    private WebDriver driver;

    private SelenideCommandInterpreter() {
        
       try {
            System.setProperty("webdriver.chrome.driver", "\\webdriver\\chromedriver.exe");
            this.driver = new ChromeDriver();
            WebDriverRunner.setWebDriver(this.driver);
        } catch (Exception e) {
            System.out.println(e.getCause());
        }
        

    }

    public static SelenideCommandInterpreter getInstance() {
        if(SelenideCommandInterpreter.instance == null){
            SelenideCommandInterpreter.instance = new SelenideCommandInterpreter();
        }
        return SelenideCommandInterpreter.instance;
    }
    
    
    public void interpret(String command, WebElement prevElement){
        int indexOfDelimiter = command.indexOf(").");
                
        // command is final only execute
        if(indexOfDelimiter == -1){
            WebElement element = executeCommand(command, prevElement);
        // command is followed by other commands
        }else{
            String headCommand = command.substring(0, indexOfDelimiter+1);
            WebElement element = executeCommand(headCommand, prevElement);
            
            String tailCommand = command.substring(indexOfDelimiter+2);
            interpret(tailCommand, element);
            
        }
    }
    
    private WebElement executeCommand(String command, WebElement prevElement){
        WebElement element = null;
        String[] commandParts = command.split("\\(|\\)");
        
        String func = commandParts[0];
        String parameter = "";
        if(commandParts.length > 1) parameter = commandParts[1].replace("\"", "");
        
        if(func.equals("open")) CommandExecuter.open(parameter);
        if(func.equals("$")) element = CommandExecuter.$(parameter);
        if(func.equals("click")) CommandExecuter.click(prevElement);
        if(func.equals("shouldHave")) CommandExecuter.shouldHave(prevElement, parameter);
        if(func.equals("text")) CommandExecuter.text(prevElement);
        if(func.equals("mark")) CommandExecuter.mark(prevElement);
        if(func.equals("setValue")) CommandExecuter.setValue(prevElement, parameter);
        
        return element;        
    }
    
    public void close(){
        this.driver.close();
    }
    
    
}
