package model;

import java.sql.*;
import java.util.ArrayList;

public class TransactionDAO {
    //initialize the connection as null
    Connection connection = null;
    //constructor for TransactionDAO that takes in the url connection string
    public TransactionDAO(String url) throws SQLException {
        //get connection to the database using the url
        connection = DriverManager.getConnection(url);
        //print that the connection was established
        System.out.println("Connection established!");
    }
    //this method inserts a new transaction (t) object into the books table in the db
    public void insert(Transaction t) throws SQLException {
        //if a user wants to check out a book for the second time there are issues with the ids so delete the transaction passed in if its already happened/is in the table
        try {
            delete(t);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //prepared statement with sql that specifies to insert into the transactions table in all the columns
        //the values to insert are initially ? placeholders
        PreparedStatement preparedStatement = connection.prepareStatement("insert into libTransactions values(?,?,?,?)");
        //set the first placeholder to the book id of the transaction
        preparedStatement.setInt(1,t.getBookID());
        //set the second placeholder to the user id of the transaction
        preparedStatement.setInt(2,t.getUserID());
        //set the third placeholder to the date of the transaction
        preparedStatement.setDate(3,t.getIDate());
        //set the fourth to the status of the transaction
        preparedStatement.setBoolean(4,t.isStatus());
        //once the prepared statement is filled out with all the values from the transaction t, execute the update which will add the transaction to transactions
        preparedStatement.executeUpdate();
    }
    //this method deletes the passed in transaction object
    public void delete(Transaction t) throws SQLException {
        //prepared statement with sql that specifies to delete from the transactions table where the id's match that of the passed in transaction
        PreparedStatement preparedStatement = connection.prepareStatement("delete from libTransactions where bookID = ? and userID = ?");
        //sets the first placeholder to the book id
        preparedStatement.setInt(1,t.getBookID());
        //sets the second to the user id
        preparedStatement.setInt(2,t.getUserID());
        //executes the deletion
        preparedStatement.executeUpdate();
    }
    //this method updates the passed in transaction in the db
    public void update(Transaction t) throws SQLException {
        //prepared statement with sql that updates transactions where the id matches that of the passed in transaction, all attributes are updated to make things easier
        PreparedStatement preparedStatement = connection.prepareStatement("update libTransactions set bookID=?, userID=?, issueDate=?, status=? where bookID = ? and userID = ?");
        preparedStatement.setInt(1,t.getBookID());
        preparedStatement.setInt(2,t.getUserID());
        preparedStatement.setDate(3,t.getIDate());
        preparedStatement.setBoolean(4,t.isStatus());
        preparedStatement.setInt(5,t.getBookID());
        preparedStatement.setInt(6,t.getUserID());
        //executes the update in the db
        preparedStatement.executeUpdate();
    }
    //this method returns an array list of all transaction objects in the transactions table
    public ArrayList<Transaction> getAll() throws SQLException {
        //prepared statement with sql that gets all the transactions from transactions
        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions");
        //new array list to hold all the transactions
        ArrayList<Transaction> arr = new ArrayList<>();
        //result set to hold the execution of the prepared statement
        ResultSet rs = preparedStatement.executeQuery();
        //this loop goes through the result set and adds each transaction to the transactions array
        while(rs.next()){
            //new temporary transaction object to be added to the arr that uses the current result (transaction) in the result set (rs.get...)
            Transaction temp = new Transaction(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getBoolean(4));
            //adds temp to arr
            arr.add(temp);
        }
        //returns the now populated arr
        return arr;
    }
    //this method returns an arraylist of transactions that are currently active
    public ArrayList<Transaction> getCurrent() throws SQLException {
        //prepared statement with sql to select all transactions from the table that have a true (active) status
        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions where status = ?");
        //set the placeholder to true
        preparedStatement.setBoolean(1,true);
        //new arraylist to hold all the active transactions
        ArrayList<Transaction> arr = new ArrayList<>();
        //result set to hold execution of the prepared statement
        ResultSet rs = preparedStatement.executeQuery();
        //this loop goes through the result set and adds each transaction to the transactions array
        while(rs.next()){
            //new temporary transaction object to be added to the arr that uses the current result (transaction) in the result set (rs.get...)
            Transaction temp = new Transaction(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getBoolean(4));
            //adds temp to arr
            arr.add(temp);
        }
        //returns the now populated arr
        return arr;
    }
    //this method returns all active transactions for a user
    public ArrayList<Transaction> getByUser(int id) throws SQLException {
        //prepared statement with sql to select all transactions from the table that have a true (active) status and that match the user id passed in
        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions where userID = ? and status = ?");
        //sets first placeholder to the user id
        preparedStatement.setInt(1,id);
        //sets the second to true
        preparedStatement.setBoolean(2,true);
        //new arraylist to hold all the active transactions for a user
        ArrayList<Transaction> arr = new ArrayList<>();
        //result set to hold execution of the prepared statement
        ResultSet rs = preparedStatement.executeQuery();
        //this loop goes through the result set and adds each transaction to the transactions array
        while(rs.next()){
            //new temporary transaction object to be added to the arr that uses the current result (transaction) in the result set (rs.get...)
            Transaction temp = new Transaction(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getBoolean(4));
            //adds temp to arr
            arr.add(temp);
        }
        //returns the now populated arr
        return arr;
    }
    //this method returns all active transactions for a book
    public ArrayList<Transaction> getByBook(int id) throws SQLException {
        //prepared statement with sql to select all transactions from the table that have a true (active) status and that match the book id passed in
        PreparedStatement preparedStatement = connection.prepareStatement("select * from libTransactions where bookID = ? and status = ?");
        //sets first placeholder to the book id
        preparedStatement.setInt(1,id);
        //sets the second to true
        preparedStatement.setBoolean(2,true);
        //new arraylist to hold all the active transactions for a book
        ArrayList<Transaction> arr = new ArrayList<>();
        //result set to hold execution of the prepared statement
        ResultSet rs = preparedStatement.executeQuery();
        //this loop goes through the result set and adds each transaction to the transactions array
        while(rs.next()){
            //new temporary transaction object to be added to the arr that uses the current result (transaction) in the result set (rs.get...)
            Transaction temp = new Transaction(rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getBoolean(4));
            //adds temp to arr
            arr.add(temp);
        }
        //returns the now populated arr
        return arr;
    }
}
