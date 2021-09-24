package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Library;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuPage implements Initializable{
    //sample controller skeleton attributes for controls
    @FXML private Button btnAddNewUser;
    @FXML private Button btnAddNewBook;
    @FXML private Button btnIssueBook;
    @FXML private Button btnReturnBook;
    @FXML private Button btnViewIssuedBooks;
    @FXML private Button btnSearchBook;
    @FXML private Button btnViewUser;
    @FXML private Button btnCollectFine;

    //library object for this instance that will hold the reference to the universal library object being passed around
    Library library;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }

    //the initData method called from main that is passed the library object
    public void initData(Library library){
        //sets the instance of library for this class to the reference passed in
        this.library=library;
        //return the current msgLog then clear it
        library.getLog();
    }

    //Instead of having the code to load each screen repeated in each button action I opted to follow the approach
    //detailed in the Milestone 2 walkthrough video where I have a general object launcher method that can be called and
    //have the return type casted as each specific controller
    public Object loadScreen(String title,String url) throws IOException {
        //initiates a new FXMLloader loader
        FXMLLoader loader = new FXMLLoader();
        //Points the loader to the the given fxml page (will be one of the 6 pages)
        loader.setLocation(getClass().getResource("/controller/"+url));
        //loads the fxml file loader has been set to and calls initialize in it
        Parent root = loader.load();
        //sets the scene (passing in the root that loads the file)
        Scene scene = new Scene(root);
        //initialize a new stage
        Stage stage= new Stage();
        //set its title to the passed in title
        stage.setTitle(title);
        //set the stage's scene
        stage.setScene(scene);
        //lastly show the stage
        stage.show();
        //returns the handle on the passed in controller class instance
        return loader.getController();
    }

    //action event method for the add new user button
    public void launchAddNewUser(ActionEvent event) throws IOException {
        //gets a handle on the AddUserPage controller class instance by calling the loadScreen method with the
        //AddUserPage.fxml file url passed in with its returned controller handle cast as AddUserPage
        AddUserPage controller = (AddUserPage) loadScreen("Add New User","AddUserPage.fxml");
        //calls the initData method in the AddUserPage controller class and passes it the library object
        controller.initData(library);
    }
    //action event method for the add new book button
    public void launchAddNewBook(ActionEvent event) throws IOException {
        //gets a handle on the AddBookPage controller class instance by calling the loadScreen method with the
        //AddBookPage.fxml file url passed in with its returned controller handle cast as AddBookPage
        AddBookPage controller = (AddBookPage) loadScreen("Add New Book","AddBookPage.fxml");
        //calls the initData method in the AddBookPage controller class and passes it the library object
        controller.initData(library);
    }
    //action event method for the issue book button
    public void launchIssueBook(ActionEvent event) throws IOException {
        //gets a handle on the IssueBookPage controller class instance by calling the loadScreen method with the
        //IssueBookPage.fxml file url passed in with its returned controller handle cast as IssueBookPage
        IssueBookPage controller = (IssueBookPage) loadScreen("Issue Book Page","IssueBookPage.fxml");
        //calls the initData method in the IssueBookPage controller class and passes it the library object
        controller.initData(library);
    }
    //action event method for the return book button
    public void launchReturnBook(ActionEvent event) throws IOException {
        //gets a handle on the ReturnBookPage controller class instance by calling the loadScreen method with the
        //ReturnBookPage.fxml file url passed in with its returned controller handle cast as ReturnBookPage
        ReturnBookPage controller = (ReturnBookPage) loadScreen("Return Book Page","ReturnBookPage.fxml");
        //calls the initData method in the IssueBookPage controller class and passes it the library object
        controller.initData(library);
    }
    //action event method for the view user details button
    public void launchViewUser(ActionEvent event) throws IOException {
        //gets a handle on the UserDetailsPage controller class instance by calling the loadScreen method with the
        //UserDetailsBookPage.fxml file url passed in with its returned controller handle cast as UserDetailsPage
        UserDetailsPage controller = (UserDetailsPage) loadScreen("View User Details Page","UserDetailsPage.fxml");
        //calls the initData method in the UserDetailsPage controller class and passes it the library object
        controller.initData(library);
    }
    //action event method for the view issued books button
    public void launchViewIssuedBooks(ActionEvent event) throws IOException {
        //gets a handle on the ViewBooksPage controller class instance by calling the loadScreen method with the
        //ViewBooksPage.fxml file url passed in with its returned controller handle cast as ViewBooksPage
        ViewBooksPage controller = (ViewBooksPage) loadScreen("View Issued Books Page","ViewBooksPage.fxml");
        //calls the initData method in the UserDetailsPage controller class and passes it the library object
        controller.initData(library);
    }
}
