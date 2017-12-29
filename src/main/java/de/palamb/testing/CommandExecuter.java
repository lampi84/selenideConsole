/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.palamb.testing;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.WebElement;

/**
 *
 * @author patric
 */
public class CommandExecuter {
    
    private static final SelenideLogger log = SelenideLogger.getInstance();
    
    public static void open(String url){
        Selenide.open(url);
        log.info("Open " + url);
    }

    public static WebElement $(String selector) {
        return Selenide.$(selector);
    }

    static void click(WebElement element) {
        element.click();
    }
    
    
    
}
