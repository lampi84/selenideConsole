package de.palamb.testing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);
    private SelenideCommandInterpreter interpreter;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        log.info("Starting SelenideConsole and chrome webdriver");
        
        this.interpreter = SelenideCommandInterpreter.getInstance();

        String fxmlFile = "/fxml/main.fxml";
        log.debug("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = new FXMLLoader();
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));

        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 400, 600);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Selenide Console");
        stage.setScene(scene);
        stage.show();
        
        
    }
    
    @Override
    public void stop(){
        System.out.println("Stage is closing");
        this.interpreter.close();
    }
}
