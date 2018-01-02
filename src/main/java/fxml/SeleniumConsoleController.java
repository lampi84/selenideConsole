/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import de.palamb.testing.SelenideCommandInterpreter;
import de.palamb.testing.SelenideLogger;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;

public class SeleniumConsoleController implements Initializable  {

    @FXML
    private TextField commandline;
    
    @FXML
    private TextArea outputLog;
    
    @FXML
    private ListView<String> list_recordedCommands;
    private ObservableList<String> recordedCommands; 
    
    private SelenideLogger log;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.recordedCommands = FXCollections.observableArrayList(); 
        list_recordedCommands.setItems(recordedCommands);
        this.addListContextMenu();
        this.log = SelenideLogger.getInstance();
        this.outputLog.setText("Selenide console started");
        this.log.setLogArea(outputLog);
        this.commandline.setText("");
    }
    
    // TODO not working yet
    private void setupAutoComplete(){
        String command = this.commandline.getText();
        
        String[] commandParts = command.split("\\(|\\)");
        int commandStage = commandParts.length;
        
        if(commandStage == 0){
            TextFields.bindAutoCompletion(this.commandline, Arrays.asList( "open(\"", "$(\"" ) );
            //this.commandline.set
            
        }
    }
    
    private void addListContextMenu(){
        this.list_recordedCommands.setCellFactory((lv) -> {
            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();
            
            MenuItem copyToClipboard = new MenuItem("Copy to clipboard");
            copyToClipboard.setOnAction(event -> {
                
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(cell.getText());
                clipboard.setContent(content);
            });
            
            MenuItem deleteCommand = new MenuItem("Delete Command");
            deleteCommand.setOnAction(event -> {
                this.list_recordedCommands.getItems().remove(cell.getItem());
            });
            
            contextMenu.getItems().addAll(copyToClipboard, deleteCommand);           
            
            cell.textProperty().bind(cell.itemProperty());
            
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if(isNowEmpty){
                    cell.setContextMenu(null);
                }else{
                    cell.setContextMenu(contextMenu);
                }
            });
            
            return cell;
        });
    }

    @FXML
    public void readCommand(KeyEvent event) {
       // this.setupAutoComplete();
        if (event.getCode() == KeyCode.ENTER) {
            String currentCommand = this.commandline.getText();
            this.commandline.clear();
            
            this.log.info("Executing: " + currentCommand);
            addCommandToLog(currentCommand);
            SelenideCommandInterpreter.getInstance().interpret(currentCommand, null);
        }
    }
    
    private void addCommandToLog(String command){
        this.recordedCommands.add(command);
    }

    
    
    @FXML
    private void closeApp(ActionEvent evt){
        SelenideCommandInterpreter.getInstance().close();
        System.exit(0);
    }
}
