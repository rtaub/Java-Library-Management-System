package model;

import java.sql.Date;
import java.time.LocalDate;

public class Transaction {

    private int bookID;
    private int userID;
    private LocalDate issueDate;
    private boolean status; // true : active false : complete


    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public LocalDate getIssueDate() { return issueDate; }

    public Date getIDate() {
        //convert local date to sql and return sql date to work with the prepared statement setDate
        java.sql.Date sqlDate = java.sql.Date.valueOf(issueDate);
        return sqlDate;
    }

    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Transaction(int bookID, int userID) {
        this.bookID = bookID;
        this.userID = userID;
        this.issueDate = LocalDate.now();
        this.status=true;
    }
    //new user constructor which takes in all attributes, used in the dao class when returning user/array list of users from db
    public Transaction(int bookID, int userID, Date issueDate, boolean status) {
        this.bookID = bookID;
        this.userID = userID;
        //make an sqlDate set to the date passed in
        java.sql.Date sqlDate = issueDate;
        //convert the sql date to local date
        this.issueDate = sqlDate.toLocalDate();
        this.status=status;
    }

}
