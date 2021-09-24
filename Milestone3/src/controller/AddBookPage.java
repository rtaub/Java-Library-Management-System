package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Window;
import model.Book;
import model.Library;



import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddBookPage implements Initializable {

    @FXML private TextField txtName;
    @FXML private TextField txtAuthor;
    @FXML private TextField txtISBN;
    @FXML private TextField txtYear;
    @FXML private ListView<String> lstPublisher;
    @FXML private ComboBox<String> cmbGenre;
    @FXML private Button btnRegister;

    Library library;
    ObservableList<String> publishers;
    ObservableList<String> genres;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        publishers = FXCollections.observableArrayList ("Pearson", "Scholastic", "Allen and Unwin", "Wiley","Disney","Penguin").sorted();
        lstPublisher.setItems(publishers);
        genres = FXCollections.observableArrayList("Education", "Adventure", "Thriller","History").sorted();
        cmbGenre.setItems(genres);
    }

    public void initData(Library library){
        this.library=library;
        library.getLog(); //clears the log
    }

    public void registerNewBook(ActionEvent e) throws SQLException {
        Window owner = btnRegister.getScene().getWindow();
        String selectedPublisher=lstPublisher.getSelectionModel().getSelectedItem();
        String selectedGenre=cmbGenre.getSelectionModel().getSelectedItem();
        Book newBook = new Book(txtName.getText(),txtAuthor.getText(),selectedPublisher,selectedGenre,txtISBN.getText(),Long.parseLong(txtYear.getText()));

        library.addBook(newBook); // update model

        ((Node)(e.getSource())).getScene().getWindow().hide();
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Registration Successful", "Registered " + newBook.getName());
    }
}
