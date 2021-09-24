package model;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    //initialize the connection as null
    Connection connection = null;
    //constructor for UserDAO that takes in the url connection string
    public UserDAO(String url) throws SQLException {
        //get connection to the database using the url
        connection = DriverManager.getConnection(url);
        //print that the connection was established
        System.out.println("Connection established!");
    }
    //this method inserts a new user (u) object into the users table in the db
    public void insert(User u) throws SQLException {
        //prepared statement with sql that specifies to insert into the users table in all the columns except id which auto increments
        //the values to insert are initially ? placeholders
        PreparedStatement preparedStatement = connection.prepareStatement("insert into users(name, email, address, dateOfBirth, isStudent, balance) values(?,?,?,?,?,?)");
        //set the first placeholder to the name of the user
        preparedStatement.setString(1,u.getName());
        //set the second parameter to the email
        preparedStatement.setString(2,u.getEmail());
        //set the third parameter to the address
        preparedStatement.setString(3,u.getAddress());
        //set the fourth to the birth date
        preparedStatement.setDate(4,u.getDateOfBirth());
        //set the fifth to whether or not it's a student
        preparedStatement.setBoolean(5,u.getStudent());
        //set the sixth to the balance
        preparedStatement.setDouble(6, u.getBalance());
        //once the prepared statement is filled out with all the values from the user u, execute the update which will add the user to users
        preparedStatement.executeUpdate();
    }
    //this method deletes the passed in user object
    public void delete(User u) throws SQLException {
        //prepared statement with sql that specifies to delete from the users table where the name matches that of the passed in user
        PreparedStatement preparedStatement = connection.prepareStatement("delete from users where name = ?");
        //set the placeholder ? to the name of the passed in user
        preparedStatement.setString(1,u.getName());
        //execute the deletion
        preparedStatement.executeUpdate();
    }
    //this method updates the passed in user in the db
    public void update(User u) throws SQLException {
        //prepared statement with sql that updates users where the id matches that of the passed in user, all attributes are updated to make things easier
        PreparedStatement preparedStatement = connection.prepareStatement("update users set name=?, email=?, address=?, dateOfBirth=?, isStudent=?, balance=? where ID = ?");
        //as with the insertion, set each placeholder ? to its respective attribute from the passed in user
        preparedStatement.setString(1,u.getName());
        preparedStatement.setString(2,u.getEmail());
        preparedStatement.setString(3,u.getAddress());
        preparedStatement.setDate(4,u.getDateOfBirth());
        preparedStatement.setBoolean(5,u.getStudent());
        preparedStatement.setDouble(6, u.getBalance());
        preparedStatement.setInt(7, u.getID());
        //execute the update in the db
        preparedStatement.executeUpdate();

    }
    //this method returns an array list of all user objects in the users table
    public ArrayList<User> getAll() throws SQLException {
        //prepared statement with sql that gets all the users from users
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
        //new array list to hold all the users
        ArrayList<User> arr = new ArrayList<>();
        //result set to hold the execution of the prepared statement
        ResultSet rs = preparedStatement.executeQuery();
        //this loop goes through the result set and adds each user to the users array
        while(rs.next()){
            //new temporary user object to be added to the arr that uses the current result (user) in the result set (rs.get...)
            User temp = new  User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getDate(5), rs.getBoolean(6), rs.getDouble(7));
            //adds temp to arr
            arr.add(temp);
        }
        //returns the now populated arr
        return arr;

    }
    //this method returns an array list of users that match the search query passed in
    public ArrayList<User> getByQuery(String query) throws SQLException {
        //prepared statement with sql to select users from the table that contain the search query
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where name like ?");
        //sets the placeholder to "%"+query+"%" which fills in the rest of the passed in query search when searching for it in the table
        preparedStatement.setString(1,"%"+query+"%");
        //new array list of users to hold all the users that match the search
        ArrayList<User> arr = new ArrayList<>();
        //result set to hold the execution of the prepared statement
        ResultSet rs = preparedStatement.executeQuery();
        //this loop goes through the result set and adds each user to the users array
        while(rs.next()){
            //new temporary user object to be added to the arr that uses the current result (user) in the result set (rs.get...)
            User temp = new  User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getDate(5), rs.getBoolean(6), rs.getDouble(7));
            //adds temp to arr
            arr.add(temp);
        }
        //returns the now populated arr
        return arr;
    }
    //this method returns the user from the db that matches the id passed in
    public User getUser(int id) throws SQLException {
        //new prepared statement with sql to select the user from the table that has the id passed in
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where ID = ?");
        //sets the placeholder to the passed in id
        preparedStatement.setInt(1,id);
        //result set to hold the execution of the prepared statement
        ResultSet rs = preparedStatement.executeQuery();
        //new user object temp initially set to null
        User temp = null;
        //this loop goes through the result set, adding the user in it to temp
        while(rs.next()){
            temp = new  User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4), rs.getDate(5), rs.getBoolean(6), rs.getDouble(7));
        }
        //returns the desired user which is now stored as temp
        return temp;
    }

}