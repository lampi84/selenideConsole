/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.palamb.testing;

import com.codeborne.selenide.WebDriverRunner;
import java.util.List;
import javafx.stage.Stage;
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
    public Stage primaryStage;

    private SelenideCommandInterpreter() {
        
        // System.out.println(this.getClass().getResource("resources/webdriver"));
        
        java.io.File file = new java.io.File("");
        String chromeDriverPath = file.getAbsolutePath() + "\\chromedriver.exe";
        System.out.println(chromeDriverPath);
	System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        this.driver = new ChromeDriver();
        WebDriverRunner.setWebDriver(this.driver);
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
    
    public static String generateCode(List<String> commands){
        StringBuilder generatedCode = new StringBuilder();
        
        for (String command : commands) {
            if(isRealCommand(command)){
               generatedCode.append(translateCode(command)).append(";\n");
            }
        }

        return generatedCode.toString();        
    }
    
    private static boolean isRealCommand(String command){
        String[] unrealCommands = {".mark(",".text("};
        boolean isRealCommand = true;
        
        for (String unrealCommand : unrealCommands) {
            if(command.contains(unrealCommand)) isRealCommand = false;
        }
        return isRealCommand;
    }
    
    private static String translateCode(String command){
        String[] selenideCommands = {".open(",".click(",".setValue(","shouldHave("};
        String translatedCommand = command;
        for (String selenideCommand : selenideCommands) {
            if(command.contains(selenideCommand)) translatedCommand = command;
        }
        return translatedCommand;
    }
    
}
