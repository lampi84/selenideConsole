/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxml;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SeleniumConsoleController {

    @FXML
    private TextField commandline;

    @FXML
    public void readCommand(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String currentCommand = this.commandline.getText();

            System.out.println("Current command: " + currentCommand);
            this.commandline.clear();

        }

    }

}
