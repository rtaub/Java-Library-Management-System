package model;

import java.time.LocalDate;

public class Transaction {
    //attribute variables for a transaction, the user and book id as well as the issueDate(localDate) and status
    private int bookID;
    private int userID;
    private LocalDate issueDate;
    private boolean status;
    //constructor for Transaction that just takes the userID and bookID, issueDate for the transaction is the current time
    //(localdate.now() and the status is set to true which will be set to false when the book is returned
    public Transaction(int userID, int bookID) {
        this.bookID = bookID;
        this.userID = userID;
        this.issueDate = LocalDate.now();
        this.status=true;
    }
    //toString method which returns a string of all the attributes of a transaction
    @Override
    public String toString() {
        return "Transaction{" +
                "bookID=" + bookID +
                ", userID=" + userID +
                ", issueDate=" + issueDate +
                ", status=" + status +
                '}';
    }

    //getters and setters for the bookID, userID, and issueDate

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

    public LocalDate getIssueDate() {

        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {

        this.issueDate = issueDate;
    }

    //setter for the status
    public void setStatus(boolean status) {

        this.status = status;
    }
    //this isStatus() represents the status of a transaction, and returns the status which will be true when
    //the book is issued and false when it was returned
    public boolean isStatus(){
        return status;
    }
}
