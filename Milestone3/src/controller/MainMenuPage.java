package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Library;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainMenuPage implements Initializable{

    Library library;

    // FXML variables not requried to be defined, keeping them for future extensions
    @FXML private Button btnAddNewUser;
    @FXML private Button btnAddNewBook;
    @FXML private Button btnIssueBook;
    @FXML private Button btnReturnBook;
    @FXML private Button btnViewIssuedBooks;
    @FXML private Button btnSearchBook;
    @FXML private Button btnViewUser;
    @FXML private Button btnCollectFine;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(Library library){
        this.library=library;
        library.getLog(); //clears the log
    }

    //general launcher, cast return type to controller
    public Object loadScreen(String title,String url) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/controller/"+url));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage= new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(Main.primaryStage);
        stage.setScene(scene);
        stage.show();
        return loader.getController();
    }


    public void launchAddNewUser(ActionEvent event) throws IOException {
        AddUserPage controller = (AddUserPage) loadScreen("Add New User","AddUserPage.fxml");
        controller.initData(library);
    }

    public void launchAddNewBook(ActionEvent event) throws IOException {
        AddBookPage controller = (AddBookPage) loadScreen("Add New Book","AddBookPage.fxml");
        controller.initData(library);
    }


    public void launchIssueBook(ActionEvent event) throws IOException, SQLException {
        IssueBookPage controller = (IssueBookPage) loadScreen("Issue Book Page","IssueBookPage.fxml");
        controller.initData(library);
    }

    public void launchReturnBook(ActionEvent event) throws IOException, SQLException {
        ReturnBookPage controller = (ReturnBookPage) loadScreen("Return Book Page","ReturnBookPage.fxml");
        controller.initData(library);
    }


    public void launchViewUser(ActionEvent event) throws IOException, SQLException {
        UserDetailsPage controller = (UserDetailsPage) loadScreen("View User Details Page","UserDetailsPage.fxml");
        controller.initData(library);
    }

    public void launchViewIssuedBooks(ActionEvent event) throws IOException, SQLException {
        ViewBooksPage controller = (ViewBooksPage) loadScreen("View Issued Books Page","ViewBooksPage.fxml");
        controller.initData(library);
    }


}
