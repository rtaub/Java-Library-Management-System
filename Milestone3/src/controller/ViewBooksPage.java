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
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ViewBooksPage implements Initializable {

    @FXML private TableView<Transaction> tblBooks;
    @FXML private TableColumn<Transaction, Integer> colBookID;
    @FXML private TableColumn<Transaction, Integer> colUserID;
    @FXML private TableColumn<Transaction, Date> colIssueDate;
    private ObservableList<Transaction> tList;
    Library library;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void initData(Library library) throws SQLException {
        this.library = library;
        library.getLog();
        tList= FXCollections.observableList(library.getActiveTransactions()); //get from model


        tblBooks.setItems(tList);
        PropertyValueFactory<Transaction, Integer> bookIDCellValueFactory = new PropertyValueFactory<>("bookID");
        colBookID.setCellValueFactory(bookIDCellValueFactory);
        PropertyValueFactory<Transaction, Integer> userIDCellValueFactory = new PropertyValueFactory<>("userID");
        colUserID.setCellValueFactory(userIDCellValueFactory);
        PropertyValueFactory<Transaction, Date> dateCellValueFactory = new PropertyValueFactory<>("issueDate");
        colIssueDate.setCellValueFactory(dateCellValueFactory);

    }
}
