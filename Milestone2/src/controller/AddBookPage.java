package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import model.Book;
import model.Library;
import java.net.URL;
import java.util.ResourceBundle;

public class AddBookPage implements Initializable {
    //sample controller skeleton attributes for controls
    @FXML private TextField txtName;
    @FXML private TextField txtAuthor;
    @FXML private TextField txtISBN;
    @FXML private TextField txtYear;
    @FXML private ListView<String> lstPublisher;
    @FXML private ComboBox<String> cmbGenre;
    @FXML private Button btnRegister;
    //library object for this instance that will hold the reference to the universal library object being passed around
    Library library;
    //declare two new observable lists which will hold the publisher and genre options
    ObservableList<String> publishers;
    ObservableList<String> genres;
    //method initialize which is called when the page is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       //initialize publishers as an observableArrayList and populate with 5 publishers
        publishers = FXCollections.observableArrayList ( "Disney", "Pearson", "Penguin", "Macmillan", "Scholastic");
        //set the publisher list to contain all items in publishers
        lstPublisher.setItems(publishers);
        //initialize genres as an observableArrayList and populate with the genres a user can choose from
        genres = FXCollections.observableArrayList("Education", "Adventure", "Thriller","History");
        //set the genre combo box to contain all items in genres
        cmbGenre.setItems(genres);
    }
    //the initData method called from main that is passed the library object
    public void initData(Library library){
        //sets the instance of library for this class to the reference passed in
        this.library=library;
        //return the current msgLog then clear it
        library.getLog();
    }
    //action event method addNewBook for the register button which registers a new book record with the entered information
    public void addNewBook(ActionEvent e){
        //string publisher which gets the selected publisher from the list
        String publisher=lstPublisher.getSelectionModel().getSelectedItem();
        //string genre which gets the selected genre from the list
        String genre=cmbGenre.getSelectionModel().getSelectedItem();
        //new book object created using the text and selections entered in the fields
        Book book = new Book(txtName.getText(),txtAuthor.getText(),publisher,genre,txtISBN.getText(),Long.parseLong(txtYear.getText()));
        //add the new book to the library object
        library.addBook(book);
        //when the register button is clicked, after it performs its function close the Add new book window
        ((Node)(e.getSource())).getScene().getWindow().hide();
        //create a new alert that will pop up after a book has been registered saying the book has been registered with an ok button to close it
        Alert a = new Alert(Alert.AlertType.NONE, "Added: "+book.getName(),ButtonType.OK);
        //set the title of the alert
        a.setTitle("Book Record Added Successfully");
        //show the alert
        a.show();
    }
}
