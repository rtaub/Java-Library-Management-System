package model;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.TimeUnit;

//*****To avoid any possible errors I chose to use the Milestone 2 solution as a starting point instead of using my own

public class Library {
    private int MAX_BOOK_LIMIT=3;
    private int MAX_LOAN_DAYS=14;
    private boolean DEBUG_DATE=true;
    //Use the DAO classes this time to add persistence with database
    private TransactionDAO transactions;
    private UserDAO users;
    private BookDAO books;
    public ArrayList<String> msgLog;
    //url string which will hold the db connection url
    String url;

    //Throughout library I had to add .getAll() to return the arrays as well as .insert() and .update() in some places to make sure it was persistent with the db and DAO classes

    public ArrayList<Transaction> getTransactions() throws SQLException {
        return transactions.getAll();
    }

    public ArrayList<User> getUsers() throws SQLException {
        return users.getAll();
    }

    public ArrayList<Book> getBooks() throws SQLException {
        return books.getAll();
    }

    public Library() throws SQLException {
        //url string to my database
        url = "jdbc:sqlite:lib.db";
        //initialize the three objects as DAO with the url passed in
        transactions = new TransactionDAO(url);
        users = new UserDAO(url);
        books = new BookDAO(url);
        msgLog =new ArrayList<String>();

    }

    public void addBook(Book b) throws SQLException {
        books.insert(b);
        msgLog.add("Book ID "+b.getID()+" added to Library");
    }
    public void addUser(User u) throws SQLException {
        users.insert(u);
        msgLog.add("User ID "+u.getID()+" added to Library");
    }
    public void addTransaction(Transaction t) throws SQLException {
        transactions.insert(t);
    }
    public boolean isAvailable(int bookID) throws SQLException {
        //use .getByBook to return active transactions with the bookID passed in
        for(Transaction t:transactions.getByBook(bookID)){
            if (t.getBookID() == bookID)
                return false;
        }
        return true;

    }
    public int getBorrowCount(int userID) throws SQLException {
        int count=0;
        //use getByUser to return active transactions with the userID passed in
        for(Transaction t: transactions.getByUser(userID)){
                count++;
        }
        return count;
    }


    public LocalDate getDueDate(LocalDate issueDate) {
        return issueDate.plusDays(MAX_LOAN_DAYS);
    }


    public boolean issueBook(int userID, int bookID) throws SQLException {
        User u = getUser(userID);
        Book b = getBook(bookID);

        //check if book is unavailable
        if (!isAvailable(bookID)){
            msgLog.add("This book is currently unavailable!");
            return false;
        }

        //check if max books limit has been reached or outstanding fines
        if (getBorrowCount(userID) >= MAX_BOOK_LIMIT) {
            msgLog.add("User has reached the maximum limit of borrowed books!");
            return false;
        }
        //check if user has outstanding balance
        if (u.getBalance() > 0){
            msgLog.add("User has an outstanding balance of $"+u.getBalance()+"!");
            return false;
        }

        // ready to issue book.
        Transaction trans = new Transaction(bookID,userID);
        addTransaction(trans);
        msgLog.add(b.getName()+" has been issued to "+u.getName()+"." );
        msgLog.add("The due date is "+getDueDate(trans.getIssueDate()));
        return true;
    }

    public boolean returnBook(int bookID) throws SQLException {
        Transaction trans=null;
        //use getCurrent to return array of only active transactions
        for (Transaction t: transactions.getCurrent())
            if (t.getBookID()==bookID){
                trans = t;
                break;
            }
        if (trans==null){
            msgLog.add("Book currently not borrowed");
            return false;
        }

        //compute Fines
        int userID = trans.getUserID();
        User u = getUser(userID);
        Book b = getBook(bookID);
        double fine = computeFine(trans);
        //System.out.println(fi)
        u.setBalance(u.getBalance()+fine);
        trans.setStatus(false);
        //make sure to update transactions with trans now that book has been returned
        transactions.update(trans);
        if(fine==0)
            msgLog.add("Thanks for returning "+b.getName()+"!");
        else {
                msgLog.add("You returned " + b.getName() + " " + fine + " days late!");
                msgLog.add("Your outstanding balance is $" + u.getBalance());
        }
        return true;

    }

    public void collectFine(User u) throws SQLException {
        if (u.getBalance()<=0){
            msgLog.add("User has no outstanding balances..");
        }
        else{
            msgLog.add("User dues collected....");
            u.setBalance(0);
        }
        //make sure to update users with u to update the balance in db
        users.update(u);
    }
    //**Used my own computeFine
    //this method takes in a transaction object and computes and returns the fine if the book in the
    //transaction is still checked out and/or overdue
    private double computeFine(Transaction transaction){
        //initialize the totalFine as 0
        double totalFine = 0;
        //if the transactions status is true meaning it is still issued
        if (transaction.isStatus()==true) {
            //make a new LocalDate current and set it to now() (the current date)
            LocalDate current = LocalDate.now();
            //using the period class and its methods for manipulating LocalDates, make two new period objects
            //the first uses the .between method which returns the period between the transaction issueDate and the current time
            Period p1 = Period.between(transaction.getIssueDate(), current);
            //the second returns the period between the due date (use getDueDate method with the transaction issue date passed in) and current time
            Period p2 = Period.between(getDueDate(transaction.getIssueDate()), current);
            //use .getDays method to get the number of days from the first period and if that is greater than
            //the max amount of loan days then the book is overdue and a fine needs to be calculated since its been more
            //than 14 days since the issue date
            if (p1.getDays() > MAX_LOAN_DAYS) {
                //set the totalFine equal to $1 times each day past the due date, which we get from p2.getDays()
                //since p2 is the period between the due date and now (it will be positive since we know its past the due date because of the if condition)
                totalFine = 1 * p2.getDays();
            }
        }
        //return totalFine, if the status was false meaning it was returned it should still be 0
        return totalFine;
    }

    public ArrayList<Book> searchBook(String name) throws SQLException {
        if(name.equals(""))
            //use getAll to return array of all books in db
            return books.getAll();
        //use getByQuery(name) which will run an sql query and return an arraylist of books from the db that contain the passed in name
        return books.getByQuery(name);
    }

    public ArrayList<User> searchUser(String name) throws SQLException {
        if(name.equals(""))
            //use getAll to return array of all users in db
            return users.getAll();
        //use getByQuery(name) which will run an sql query and return an arraylist of users from the db that contain the passed in name
        return users.getByQuery(name);
    }


    public void printBooks(ArrayList<Book> results)
    {
        if (results.size()==0)
            msgLog.add("No books matched the search criteria");
        for (Book b: results) {
            msgLog.add("Book Id : "+b.getID()+" Book Name : "+b.getName());
        }
    }

    public void printUsers(ArrayList<User> results)
    {
        if (results.size()==0)
            msgLog.add("No users matched the search criteria");
        for (User b: results) {
            msgLog.add("User Id : "+b.getID()+" User Name : "+b.getName());

        }
    }

    public Book getBook(int bookID) throws SQLException {
        //use getBook to return the book with the matching id
        if (books.getBook(bookID)!=null){
            return books.getBook(bookID);}
        else
            return null;
    }

    public User getUser(int userID) throws SQLException {
        //use getUser to return the user with the matching id
        if (users.getUser(userID)!=null){
            return users.getUser(userID);}
        else
            return null;
    }

    public String getLog(){
        StringBuilder sb= new StringBuilder();

        for(String msg :msgLog){
            sb.append(msg+"\n");
        }
        msgLog.clear();
        return sb.toString();
    }

    public ArrayList<Book> getCheckedOutBooks(User u) throws SQLException {
        ArrayList<Book> res= new ArrayList<>();
        //use getByUser to return array list of users with active transactions
        for(Transaction t: transactions.getByUser(u.getID())){
            res.add(getBook(t.getBookID()));
        }

        return res;
    }

    public ArrayList<Transaction> getActiveTransactions() throws SQLException {
        //to get active transactions, just return transactions.getCurrent() which will return all active transactions in the db
        return transactions.getCurrent();
    }
}
