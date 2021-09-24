package model;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {
    //initialize the connection as null
    Connection connection = null;
    //constructor for BookDAO that takes in the url connection string
    public BookDAO(String url) throws SQLException {
        //get connection to the database using the url
        connection = DriverManager.getConnection(url);
        //print that the connection was established
        System.out.println("Connection established!");
    }
    //this method inserts a new book (b) object into the books table in the db
    public void insert(Book b) throws SQLException {
        //prepared statement with sql that specifies to insert into the books table in all the columns except id which auto increments
        //the values to insert are initially ? placeholders
        PreparedStatement preparedStatement = connection.prepareStatement("insert into books(name, author, publisher, genre, ISBN, year) values(?,?,?,?,?,?)");
        //set the first placeholder to the name of the book
        preparedStatement.setString(1,b.getName());
        //set the second placeholder to the author
        preparedStatement.setString(2,b.getAuthor());
        //set the third to the publisher
        preparedStatement.setString(3,b.getPublisher());
        //set the fourth to the genre
        preparedStatement.setString(4,b.getGenre());
        //set the fifth to the isbn
        preparedStatement.setString(5,b.getISBN());
        //set the sixth to the year
        preparedStatement.setLong(6,b.getYear());
        //once the prepared statement is filled out with all the values from the book b, execute the update which will add the book to books
        preparedStatement.executeUpdate();
    }
    //this method deletes the passed in book object
    public void delete(Book b) throws SQLException {
        //prepared statement with sql that specifies to delete from the books table where the name matches that of the passed in book
        PreparedStatement preparedStatement = connection.prepareStatement("delete from books where name = ?");
        //set the placeholder ? to the name of the passed in book
        preparedStatement.setString(1,b.getName());
        //execute the deletion
        preparedStatement.executeUpdate();
    }
    //this method updates the passed in book in the db
    public void update(Book b) throws SQLException {
        //prepared statement with sql that updates books where the id matches that of the passed in book, all attributes are updated to make things easier
        PreparedStatement preparedStatement = connection.prepareStatement("update books set name=?, author=?, publisher=?, genre=?, ISBN=?, year=? where ID = ?");
        //as with the insertion, set each placeholder ? to its respective attribute from the passed in book
        preparedStatement.setString(1,b.getName());
        preparedStatement.setString(2,b.getAuthor());
        preparedStatement.setString(3,b.getPublisher());
        preparedStatement.setString(4,b.getGenre());
        preparedStatement.setString(5,b.getISBN());
        preparedStatement.setLong(6,b.getYear());
        preparedStatement.setInt(7,b.getID());
        //execute the update in the db
        preparedStatement.executeUpdate();
    }
    //this method returns an array list of all book objects in the books table
    public ArrayList<Book> getAll() throws SQLException {
        //prepared statement with sql that gets all the books from books
        PreparedStatement preparedStatement = connection.prepareStatement("select * from books");
        //new array list to hold all the books
        ArrayList<Book> arr = new ArrayList<>();
        //result set to hold the execution of the prepared statement
        ResultSet rs = preparedStatement.executeQuery();
        //this loop goes through the result set and adds each book to the books array
        while(rs.next()){
            //new temporary book object to be added to the arr that uses the current result (book) in the result set (rs.get...)
            Book temp = new  Book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), rs.getLong(7));
            //adds temp to arr
            arr.add(temp);
        }
        //returns the now populated arr
        return arr;
    }
    //this method returns an array list of books that match the search query passed in
    public ArrayList<Book> getByQuery(String query) throws SQLException {
        //prepared statement with sql to select books from the table that contain the search query
        PreparedStatement preparedStatement = connection.prepareStatement("select * from books where name like ?");
        //sets the placeholder to "%"+query+"%" which fills in the rest of the passed in query search when searching for it in the table
        preparedStatement.setString(1,"%"+query+"%");
        //new array list of books to hold all the books that match the search
        ArrayList<Book> arr = new ArrayList<>();
        //result set to hold the execution of the prepared statement
        ResultSet rs = preparedStatement.executeQuery();
        //this loop goes through the result set and adds each book to the books array
        while(rs.next()){
            //new temporary book object to be added to the arr that uses the current result (book) in the result set (rs.get...)
            Book temp = new  Book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), rs.getLong(7));
            //adds temp to arr
            arr.add(temp);
        }
        //returns the now populated arr
        return arr;
    }
    //this method returns the book from the db that matches the id passed in
    public Book getBook(int id) throws SQLException {
        //new prepared statement with sql to select the book from the table that has the id passed in
        PreparedStatement preparedStatement = connection.prepareStatement("select * from books where ID = ?");
        //sets the placeholder to the passed in id
        preparedStatement.setInt(1,id);
        //result set to hold the execution of the prepared statement
        ResultSet rs = preparedStatement.executeQuery();
        //new book object temp initially set to null
        Book temp = null;
        //this loop goes through the result set, adding the book in it to temp
        while(rs.next()){
            temp = new  Book(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getString(5), rs.getString(6), rs.getLong(7));
        }
        //returns the desired book which is now stored as temp
        return temp;
    }

}
