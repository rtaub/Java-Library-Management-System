package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.Transaction;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReturnBookPage implements Initializable {

    @FXML private TextField txtBookID;
    @FXML private ListView<Book> lstBookList;
    @FXML private TextField txtBookSearch;
    @FXML private Button btnReturnBook;
    @FXML private Label msgLabel;

    Library library;
    private ObservableList<Book> bookList;
    public void initData(Library library) throws SQLException {
        this.library=library;
        library.getLog(); //clears the log
        // initializing listviews
        lstBookList.setItems(bookList);
        getBookList("");
        // adding listeners to search boxes
        txtBookSearch.textProperty().addListener((Observable,oldValue,newValue)->{
            lstBookList.getSelectionModel().clearSelection();
            try {
                getBookList(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        // populating textboxes with list selection
        lstBookList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue!=null)
                txtBookID.setText(newValue.getID()+"");
        });


    }

    public void getBookList(String query) throws SQLException { //get from model
        bookList.clear();
        ArrayList<Book> temp = library.searchBook(query);
        bookList.addAll(temp);
    }

    @FXML public void launchReturnBook(ActionEvent e) throws SQLException {

        String bid = txtBookID.getText();
        if(bid.equals("")){
            msgLabel.setText("Book ID  cannot be empty ");
            msgLabel.setTextFill(Color.DARKRED);
            return;
        }

        boolean result = library.returnBook(Integer.parseInt(bid));  // update model
        msgLabel.setText(library.getLog()); //success or failure

        if(!result)
            msgLabel.setTextFill(Color.DARKRED);
        else
            msgLabel.setTextFill(Color.LIGHTGREEN);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookList= FXCollections.observableArrayList();
        msgLabel.setText("");
    }
}
