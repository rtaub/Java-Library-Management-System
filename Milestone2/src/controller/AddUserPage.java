package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import model.Library;
import model.User;
import java.net.URL;
import java.util.ResourceBundle;

public class AddUserPage implements Initializable {
    //sample controller skeleton attributes for controls
    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private TextArea txtAddress;
    @FXML private RadioButton radStudent;
    @FXML private RadioButton radFaculty;
    @FXML private DatePicker dtDateOfBirth;
    @FXML private Button btnRegister;

    //library object for this instance that will hold the reference to the universal library object being passed around
    Library library;
    //make a toggle group to put the radio buttons in so only one can be selected
    private ToggleGroup userType;

    //the initData method called from main that is passed the library object
    public void initData(Library library){
        //sets the instance of library for this class to the reference passed in
        this.library=library;
        //return the current msgLog then clear it
        library.getLog();
    }

    //method initialize which is called when the page is loaded
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize the toggle group declared above
        userType = new ToggleGroup();
        //set each of the radio buttons to be in the userType toggle group
        radFaculty.setToggleGroup(userType);
        radStudent.setToggleGroup(userType);
        //set the student radio button to true since it's the default option
        radStudent.setSelected(true);
    }

    //action event method registerUser for the register button which registers a new user with the entered information
    public void registerUser(ActionEvent e){
        //boolean variable which will be set to false if the selected button is faculty and true if it's student (set to true as default)
        boolean isStudent=true;
        //if the student radio button is selected
        if (radStudent.isSelected())
            //the user is a student so set isStudent to true
            isStudent=true;
        //else if the faculty radio button is selected
        else if (radFaculty.isSelected())
            //the new user is a faculty member so set isStudent to false
            isStudent=false;
        //new user object created by getting the text from all the fields
        User user = new User(txtName.getText(),txtEmail.getText(),txtAddress.getText(),dtDateOfBirth.getValue(),isStudent);
        //add the new user to the library object
        library.addUser(user);
        //when the register button is clicked, after it performs its function close the Add new user window
        ((Node)(e.getSource())).getScene().getWindow().hide();
        //create a new alert that will pop up after a user has been registered saying the user has been registered with an ok button to close it
        Alert a = new Alert(Alert.AlertType.NONE, "Registered: "+user.getName(),ButtonType.OK);
        //set the title of the alert
        a.setTitle("Registration Successful");
        //show the alert
        a.show();
    }


}
