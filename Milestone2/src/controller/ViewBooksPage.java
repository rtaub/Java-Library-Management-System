package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Library;
import model.Transaction;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewBooksPage implements Initializable {
    //sample controller skeleton attributes for controls
    @FXML private TableView<Transaction> tblBooks;
    @FXML private TableColumn<Transaction, Integer> colBookID;
    @FXML private TableColumn<Transaction, Integer> colUserID;
    @FXML private TableColumn<Transaction, Date> colIssueDate;

    //library object for this instance that will hold the reference to the universal library object being passed around
    Library library;
    //declare a new observable list of transactions that will be used to populate the issued books table
    private ObservableList<Transaction> transactions;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    //the initData method called from main that is passed the library object
    public void initData(Library library) {
        //sets the instance of library for this class to the reference passed in
        this.library = library;
        //return the current msgLog then clear it
        library.getLog();
        //populate transactions with all current transactions by calling currentTransactions from library
        transactions= FXCollections.observableList(library.currentTransactions());
        //sets the items for the issued books table to be from transactions
        tblBooks.setItems(transactions);
        //sets each of the columns to its respective value (bookID, userID, and issueDate) to populate
        //the table with all the appropriate issued books/transactions
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
    }
}
