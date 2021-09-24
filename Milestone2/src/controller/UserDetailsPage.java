package controller;

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
import model.User;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;

public class UserDetailsPage implements Initializable {
    //sample controller skeleton attributes for controls
    @FXML private ComboBox<User> cmbSelectUser;
    @FXML private Label lblName;
    @FXML private Label lblEmail;
    @FXML private Label lblAddress;
    @FXML private Label lblBirthday;
    @FXML private Label lblUserType;
    @FXML private Label lblBalance;
    @FXML private ListView<Book> lstIssued;
    @FXML private Button btnCollectFine;

    //library object for this instance that will hold the reference to the universal library object being passed around
    Library library;
    //declare a new observable list of users that will be user to populate the user selection combo box
    private ObservableList<User> users;
    //declare a new observable list of books that will hold all the checked out books and populate the issued list view
    private ObservableList<Book> books;
    //declare a new user that will hold the user that is selected
    private User user;

    //the initData method called from main that is passed the library object
    public void initData(Library library){
        //sets the instance of library for this class to the reference passed in
        this.library=library;
        //return the current msgLog then clear it
        library.getLog();
        //initialize users as an observable arraylist
        users= FXCollections.observableArrayList();
        //add all the current users to users using a call to library's getAllUsers()
        users.addAll(library.getAllUsers());
        //then populate the select user combo box with said users
        cmbSelectUser.setItems(users);
    }

    //method initialize which is called when the page is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initially set the collect fine button to invisible
        btnCollectFine.setVisible(false);
        //initially set the balance label to white
        lblBalance.setTextFill(Color.WHITE);
    }

    //action event method displaySelectedUser for the select user combo box which displays the details including checked
    //out books and the current balance of the user that was selected
    public void displaySelectedUser(ActionEvent e){
        //set user to the user that was selected from the select user combo box
        user = cmbSelectUser.getValue();
        //set the name label to the selected user's name (user.getName())
        lblName.setText(user.getName());
        //set the address label to the selected user's address (user.getAddress())
        lblAddress.setText(user.getAddress());
        //set the email label to the selected user's email (user.getEmail())
        lblEmail.setText(user.getEmail());
        //set the birthday label to the selected user's bday (user.getDateofBirth().toString())
        lblBirthday.setText(user.getDateOfBirth().toString());
        //if user.getStudent() returns true this means the user is a student
        if(user.getStudent()==true)
            //set the user type label to display Student
            lblUserType.setText("Student");
        //otherwise the user is a faculty member
        else
            //set the user type label to display Faculty
            lblUserType.setText("Faculty");
        //set the balance label to display the selected user's balance (user.getBalance())
        lblBalance.setText("$"+user.getBalance());

        //set books to hold all the currently checkout books by calling library's checkedOutBooks(user)
        books= FXCollections.observableList(library.checkedOutBooks(user));
        //populate the issued books list with books (checked out books)
        lstIssued.setItems(books);
        //if the user has an outstanding balance
        if (user.getBalance() > 0) {
            //set the collect fine button to visible
            btnCollectFine.setVisible(true);
            //set their balance label to red to indicate a balance that needs to be payed
            lblBalance.setTextFill(Color.RED);
        }
    }
    //action event method collectFine for the collect fine button which collects the selected users fine/balance (has them "pay" their fine)
    public void collectFine(ActionEvent e){
        //"collect" the fine by setting the user's balance to 0
        user.setBalance(0);
        //set the balance label to user.getBalance() which is now 0
        lblBalance.setText("$"+user.getBalance());
        //set the color of the balance label back to white
        lblBalance.setTextFill(Color.WHITE);
    }
}
