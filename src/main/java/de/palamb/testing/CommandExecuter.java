/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.palamb.testing;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author patric
 */
public class CommandExecuter {
    
    private static final SelenideLogger log = SelenideLogger.getInstance();
    
    private static WebDriver driver = null;
    
    private static void setDriver(){
        if(driver == null){
            CommandExecuter.driver = WebDriverRunner.getWebDriver();
        }
    }
    
    public static void open(String url){
        Selenide.open(url);
        log.info("Open " + url);
    }
    


    public static WebElement $(String selector) {
        setDriver();
        try {
            CommandExecuter.driver.findElement(By.cssSelector(selector));
            WebElement element = Selenide.$(selector);
            return element;
        } catch(NoSuchElementException e){
            log.error("Not able to select element " + selector);
            return null;
        }        
    }
    
    public static void shouldHave(WebElement element, String text){
        SelenideElement selenideElement = (SelenideElement) element;
        String actualText = selenideElement.getText();
        
        if(!actualText.equals(text)){
            log.error("Text match failed for " + element.getTagName());
            log.error("Expected text: " + text);
            log.error("Actual text: " + element.getText());
        }        
    }

    static void click(WebElement element) {
        element.click();
    }
    
    
    
}
