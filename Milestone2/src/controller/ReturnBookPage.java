package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Book;
import model.Library;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReturnBookPage implements Initializable {
    //sample controller skeleton attributes for controls
    @FXML private TextField txtBookID;
    @FXML private ListView<Book> lstBooks;
    @FXML private TextField txtBookSearch;
    @FXML private Button btnReturnBook;
    @FXML private Label lblNotif;

    //library object for this instance that will hold the reference to the universal library object being passed around
    Library library;
    //declare a new observable list of books that will be used to populate book search result list
    private ObservableList<Book> books;

    //method initialize which is called when the page is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        // initialize the listview to show all books by calling the getBooks method with a blank string passed in
        getBooks("");

        //add listener to the book search text box so it can pick up/detect when something is typed (contents of the search box stored as new_val)
        txtBookSearch.textProperty().addListener((observableValue, old_val, new_val)->{
            //when something is typed in the search box, first clear the list
            lstBooks.getSelectionModel().clearSelection();
            //then call getBooks with the search string (new_val) passed in which will populate the list with books that match the search
            getBooks(new_val);
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

    //action event method returnBook for the return book button which returns a book that is currently checked out
    @FXML public void returnBook(ActionEvent e){
        //string bookId which gets the id from teh bookid text box
        String bookId = txtBookID.getText();
        //if the book id is blank that means that nothings been entered in teh book id field
        if(bookId.equals("")){
            //set the text of the notification label to red
            lblNotif.setTextFill(Color.RED);
            //set the text of the notification label to alert the user that they are missing book id
            lblNotif.setText("Book ID field cannot be empty ");
        }
        //otherwise if there is a book id entered
        else {
            //call returnBook from library and pass in the book id which will return the book, making it available and closing the transaction
            library.returnBook(Integer.parseInt(bookId));
            //set the text of the notification label to green
            lblNotif.setTextFill(Color.LAWNGREEN);
            //set the text of the notification label to getLog which returns msgLog which will have a string confirming the return
            lblNotif.setText(library.getLog());
        }
    }

}
