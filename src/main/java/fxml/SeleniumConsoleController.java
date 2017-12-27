/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import de.palamb.testing.SelenideCommandInterpreter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SeleniumConsoleController implements Initializable  {

    @FXML
    private TextField commandline;
    
    @FXML
    private ListView<String> list_recordedCommands;
    private ObservableList<String> recordedCommands = FXCollections.observableArrayList();  
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list_recordedCommands.setItems(recordedCommands);
    }

    @FXML
    public void readCommand(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String currentCommand = this.commandline.getText();
            this.commandline.clear();
            
            System.out.println("Current command: " + currentCommand);
            addCommandToLog(currentCommand);
            SelenideCommandInterpreter.getInstance().interpret(currentCommand, null);
        }
    }
    
    private void addCommandToLog(String command){
        this.recordedCommands.add(command);
    }
    
}
