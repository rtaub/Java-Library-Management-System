package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Book;
import model.Library;
import model.User;
import java.time.LocalDate;

//**** I referenced the main.java from the milestone 2 walkthrough video for help with setting up main
public class Main extends Application {
    //initialize the primary stage
    public static Stage primaryStage;
    //start(stage) method to initialize the library object as well as set up the main menu by loading MainMenuPage
    @Override
    public void start(Stage primaryStage) throws Exception{
        //initial users and books to populate library, names and details copied from milestone 2 walkthrough video
        User u1 = new User("John Stones","jstones@gmail.com","445 Conradi St. Tallahassee, FL 32304", LocalDate.of(1989,06,13), true);
        User u2 = new User("Jack Bauer","jack24@gmail.com","7400 Bay Rd. University Center, MI 48710",LocalDate.of(1988,11,15),false);
        User u3 = new User("Harry Kane","hkane@gmail.com","123 James Boyd Rd. Scranton, PA 28410",LocalDate.of(1988,2,1), false);
        User u4 = new User("Tim Arnold","ta123@gmail.com","3412 Dinsmore Ave, MA 01710",LocalDate.of(1999,1,15), true);
        Book b1 = new Book("Programming with Java","Daniel Liang","Pearson","Education","1234568924",2020);
        Book b2 = new Book("Data Structures and Algorithms","Robert Lafore","Pearson","Education","98726213",2001);
        Book b3 = new Book("Harry Potter and The Chamber of Secrets","J.K. Rowling","Scholastic","Adventure","343255323",1998);
        Book b4 = new Book("Lord of the Rings - The Two Towers","Tolkien","Wiley","Thriller","989636362",1945);

        //create a library object that will serve as our database for now which will be passed through the pages/controllers
        Library library = new Library();
        //the above test books and users are added to the library object that will be passed through the controllers
        library.addUser(u1);
        library.addUser(u2);
        library.addUser(u3);
        library.addUser(u4);
        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);
        library.addBook(b4);

        //set Main's primarystage to primaryStage
        Main.primaryStage =primaryStage;
        //initiates a new FXMLloader loader
        FXMLLoader loader = new FXMLLoader();
        //Points the loader to the the MainMenu fxml page
        loader.setLocation(getClass().getResource("MainMenuPage.fxml"));
        //loads the fxml file loader has been set to and calls initialize in it
        Parent root = loader.load();
        //gets a handle on the MainMenuPage controller class instance
        MainMenuPage controller = loader.getController();
        //calls the initData method in the MainMenuPage controller class and passes it the library object
        controller.initData(library);
        //sets up the stage and its title and displays it
        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(new Scene(root, 800 , 600));
        primaryStage.show();
    }

}
