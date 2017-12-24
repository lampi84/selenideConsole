/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.palamb.testing;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.openqa.selenium.WebDriver;
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
    
    public void interpretCommand(String command){
        
        String[] commandParts = command.split("\\(|\\)");
        
        String func = commandParts[0];
        String url = commandParts[1].replace("\"", "");
        
        if(func.equals("open")){
            Selenide.open(url);
        }
    }
    
    public void close(){
        this.driver.close();
    }
    
    
}
