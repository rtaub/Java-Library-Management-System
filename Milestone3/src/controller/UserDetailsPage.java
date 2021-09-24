package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Book;
import model.Library;
import model.Transaction;
import model.User;
import javafx.scene.paint.Color;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserDetailsPage implements Initializable {

    @FXML private ComboBox<User> cmbSelectUser;
    @FXML private Label lblName;
    @FXML private Label lblEmail;
    @FXML private Label lblAddress;
    @FXML private Label lblBirthday;
    @FXML private Label lblUserType;
    @FXML private Label lblBalance;
    @FXML private ListView<Book> lstIssuedBooks;
    @FXML private Button btnCollectFine;

    Library library;
    private ObservableList<User> userList;
    private ObservableList<Book> bookList;
    private User selectedUser;

    public void initData(Library library) throws SQLException {
        this.library=library;
        library.getLog(); //clears the log
        //get list of users to populate combobox

        userList= FXCollections.observableArrayList();
        userList.addAll(library.getUsers()); //get from model
        cmbSelectUser.setItems(userList);


        selectedUser=null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnCollectFine.setVisible(false); // will show if user has balance
        lblBalance.setTextFill(Color.WHITE); // initially set to neutral color
    }

    public void populateSelectedUser(ActionEvent e) throws SQLException {
        btnCollectFine.setVisible(false); // will show if user has balance
        lblBalance.setTextFill(Color.WHITE); // initially set to neutral

        selectedUser =  cmbSelectUser.getValue();

        lblName.setText(selectedUser.getName());
        lblAddress.setText(selectedUser.getAddress());
        lblEmail.setText(selectedUser.getEmail());
        lblBirthday.setText(selectedUser.getDateOfBirth().toString());
        lblUserType.setText(selectedUser.getStudent() ? "Student" : "Faculty");
        lblBalance.setText("$"+selectedUser.getBalance());

        //populate listview
        bookList= FXCollections.observableList(library.getCheckedOutBooks(selectedUser)); //get from model
        lstIssuedBooks.setItems(bookList);

        if (selectedUser.getBalance() > 0) {
            btnCollectFine.setVisible(true);
            lblBalance.setTextFill(Color.DARKRED);
        }

    }

    public void launchCollectFine(ActionEvent e) throws SQLException {
        library.collectFine(selectedUser);
        lblBalance.setText("$"+selectedUser.getBalance());
        lblBalance.setTextFill(Color.LIGHTGREEN);
    }


}
