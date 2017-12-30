/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.palamb.testing;

import javafx.scene.control.TextArea;

/**
 *
 * @author patric
 */
public class SelenideLogger {
    
    private TextArea logOutput;
    private static SelenideLogger instance;

    private SelenideLogger() {
    }
    
    public static SelenideLogger getInstance(){
        if(SelenideLogger.instance == null){
            SelenideLogger.instance = new SelenideLogger();
        }
        return SelenideLogger.instance;
    }
    
    
    public void setLogArea(TextArea logOutput){
        this.instance.logOutput = logOutput;
    }
    
    public void info(String logMessage){
        this.instance.logOutput.appendText("\n[INFO] " + logMessage);
    }
    
    public void error(String logMessage){
        this.instance.logOutput.appendText("\n[ERROR] " + logMessage);
    }
    
}
