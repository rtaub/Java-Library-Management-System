package controller;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Book;
import model.Library;
import model.User;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main extends Application {
    Library library = new Library();
    public static Stage primaryStage;

    public Main() throws SQLException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Library library = new Library();

        Main.primaryStage =primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("MainMenuPage.fxml"));
        Parent root = loader.load();

        MainMenuPage loginController = loader.getController();
        loginController.initData(library);
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 800   , 600));
        primaryStage.show();
    }
    /*
    public void launchLoginScene(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("PersonView.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);

        //access the controller and call a method
        PersonViewController controller = loader.getController();
        controller.initData(tableView.getSelectionModel().getSelectedItem());

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    */

    public static void main(String[] args) {
        launch(args);
    }
}
