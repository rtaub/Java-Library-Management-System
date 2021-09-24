package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Book;
import model.Library;
import javafx.fxml.FXML;
import model.User;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class IssueBookPage implements Initializable {
    //sample controller skeleton attributes for controls
    @FXML private TextField txtUserSearch;
    @FXML private TextField txtBookSearch;
    @FXML private TextField txtUserID;
    @FXML private TextField txtBookID;
    @FXML private ListView<User> lstUsers;
    @FXML private ListView<Book> lstBooks;
    @FXML private Button btnIssueBook;
    @FXML private Label lblNotif;

    //library object for this instance that will hold the reference to the universal library object being passed around
    Library library;
    //declare a new observable list of users and books that will be used to populate the user and book search result lists
    private ObservableList<User> users;
    private ObservableList<Book> books;

    //method initialize which is called when the page is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize users as an observable arraylist
        users= FXCollections.observableArrayList();
        //initialize books as an observable arraylist
        books= FXCollections.observableArrayList();
        //set the notification label to blank initially
        lblNotif.setText("");
    }
    //the initData method called from main that is passed the library object
    public void initData(Library library){
        //sets the instance of library for this class to the reference passed in
        this.library=library;
        //return the current msgLog then clear it
        library.getLog();
        // initialize the two listviews to show all books and users by calling the getUsers and getBooks methods
        //with a blank string passed in
        getUsers("");
        getBooks("");

        //add listener to the user search text box so it can pick up/detect when something is typed (contents of the search box stored as new_val)
        txtUserSearch.textProperty().addListener((observableValue, old_val, new_val)->{
            //when something is typed in the search box, first clear the list
            lstUsers.getSelectionModel().clearSelection();
            //then call getUsers with the search string (new_val) passed in which will populate the list with users that match the search
            getUsers(new_val);
        });
        //add listener to the book search text box so it can pick up/detect when something is typed (contents of the search box stored as new_val)
        txtBookSearch.textProperty().addListener((observableValue, old_val, new_val)->{
            //when something is typed in the search box, first clear the list
            lstBooks.getSelectionModel().clearSelection();
            //then call getBooks with the search string (new_val) passed in which will populate the list with books that match the search
            getBooks(new_val);
        });

        //add listener to the users list which will take the selected item and use it to populate the user id text box
        //the user object that's selected is stored as new_val
        lstUsers.getSelectionModel().selectedItemProperty().addListener((observableValue, old_val, new_val) -> {
            //if new_val isn't null, meaning that a user has been selected from the list
            if (new_val!=null)
                //set the text of the user id text box to the id of the selected user
                txtUserID.setText(String.valueOf(new_val.getID()));
        });
        //add listener to the books list which will take the selected item and use it to populate the book id text box
        //the book object that's selected is stored as new_val
        lstBooks.getSelectionModel().selectedItemProperty().addListener((observableValue, old_val, new_val) -> {
            //if new_val isn't null, meaning that a book has been selected from the list
            if (new_val!=null)
                //set the text of the book id text box to the id of the selected book
                txtBookID.setText(String.valueOf(new_val.getID()));
        });
    }

    //method getUsers which takes in a search string from the user search text box
    public void getUsers(String search){
        //first clear users of any previous search results
        users.clear();
        //new user arraylist searchRslt which calls the searchUser method from library with the search
        //string passed in. This will populate searchRslt with all users that match the search
        ArrayList<User> searchRslt = library.searchUser(search);
        //add all the users from search result to users
        users.addAll(searchRslt);
        //add all the users that match the search (now stored in users) to the users list
        lstUsers.setItems(users);
    }

    //method getBooks which takes in a search string from the book search text box
    public void getBooks(String search){
        //first clear books of any previous search results
        books.clear();
        //new book arraylist searchRslt which calls the searchBook method from library with the search
        //string passed in. This will populate searchRslt with all books that match the search
        ArrayList<Book> searchRslt = library.searchBook(search);
        //add all the books from search result to books
        books.addAll(searchRslt);
        //add all the books that match the search (now stored in books) to the books list
        lstBooks.setItems(books);
    }

    //action event method issueBook for the issue book button which issues/checks out a book to the desired user
    @FXML public void issueBook(ActionEvent e){
        //string userId which gets the id from the userid text box
        String userId = txtUserID.getText();
        //string bookId which gets the id from teh bookid text box
        String bookId = txtBookID.getText();
        //if either id is blank that means that one or both of the fields are blank
        if(userId.equals("") || bookId.equals("")){
            //set the text of the notification label to red
            lblNotif.setTextFill(Color.RED);
            //set the text of the notification label to alert the user that they are missing an id
            lblNotif.setText("ID fields cannot be blank");
        }
        //otherwise if there is an id entered in both fields
        else {
            //call issueBook from library and pass in the user and book id which will check out the book to the user and add it to transactions
            library.issueBook(Integer.parseInt(userId), Integer.parseInt(bookId));
            //set the text of the notification label to green
            lblNotif.setTextFill(Color.LAWNGREEN);
            //set the text of the notification label to getLog which returns msgLog which will have a string confirming the
            //transaction and giving a due date
            lblNotif.setText(library.getLog());
        }
    }

}
