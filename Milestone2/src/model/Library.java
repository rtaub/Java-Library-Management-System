package model;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

//****** msgLog additions were added throughout Library taken from the milestone 1 solution that was provided in the Milestone 2 description/instructions page. Message labels in controllers are set to the msgLog which will now have the proper message

public class Library {
    //a variable is made to hold the number of books a user can have
    private int MAX_BOOK_LIMIT=3;
    //the same is done with the max amount of days a book can be loaned for
    private int MAX_LOAN_DAYS=14;
    //an arraylist of each type of object is made to keep track of all users, books, and transactions
    public ArrayList<Transaction> transactions;
    public ArrayList<User> users;
    public ArrayList<Book> books;
    public ArrayList<String> msgLog;

    //getter that returns the entire users array list
    public ArrayList<User> getAllUsers() {
        return users;
    }
    //getter that returns the entire books array list
    public ArrayList<Book> getAllBooks() {
        return books;
    }

    //no args constructor to initialize the arraylists that serve as a simple database
    public Library()
    {
        //the arraylists are initialized
        transactions = new ArrayList<Transaction>();
        users = new ArrayList<User>();
        books = new ArrayList<Book>();
        msgLog =new ArrayList<String>();

    }
    //this method takes in a book object and adds it to the books list
    public void addBook(Book book){
        //the passed in book is added to books
        books.add(book);
        msgLog.add("Book ID "+book.getID()+" added to Library");
    }
    //this method takes in a user object and adds it to the users list
    public void addUser(User user){
        //the passed in user is added to books
        users.add(user);
        msgLog.add("User ID "+user.getID()+" added to Library");
    }
    //this method takes in a transaction object and adds it to the transactions list
    public void addTransaction(Transaction transaction){
        //the passed in transaction is added to transactions
        transactions.add(transaction);
    }

    //a book id is passed in to this method and it returns true if the book is available to be borrowed, and false if not
    public boolean isAvailable(int id){
        //boolean value to be returned
        boolean available = true;
        //to check if a book has been issued, loop through the transactions list
        for(int i =0; i<transactions.size();i++){
            //if the current transaction's BookID is the same as the passed in id and the status of the transaction is true
            //(meaning the book is issued) then that book is currently issued to another user
            if(transactions.get(i).getBookID() == id && transactions.get(i).isStatus()==true){
                //set available to false since it's not available
                available =false;
            }
        }
        //return available which will have turned to false if the book is currently issued to another user
        return available;
    }
    //this method takes in a user id and returns the number of books they have borrowed
    public int getBorrowCount(int id){
        //int borrowed will hold the number of borrowed books, initialized to 0
        int borrowed=0;
        //loop through transactions to see what books have been borrowed and to who
        for(int i =0; i<transactions.size();i++){
            //if the current transaction's userID is the same as the id passed in and the status of the transaction is true
            if(transactions.get(i).getUserID() == id && transactions.get(i).isStatus()==true){
                //that means the user has that book borrowed still, so increase borrowed by 1
                borrowed++;
            }
        }
        //return the number of borrowed books
        return borrowed;
    }

    //the issued date is passed into this method and it returns what the due date will be based on that date
    public LocalDate getDueDate(LocalDate issued){
        //a new LocalDate dueDate is declared which will hold the dueDate
        LocalDate dueDate;
        //the due date will be the date issued, plus the max amount of loan days so you can set
        //dueDate equal to issued.plusDays(MAX_LOAN_DAYS) which adds 14 days to the issued date
        dueDate= issued.plusDays(MAX_LOAN_DAYS);
        //return the dueDate
        return dueDate;
    }

    //this method takes in a user and book id and issues the book to the user if the books available, the user
    //is under their borrow limit, and the user doesn't have an outstanding balance/fine
    public boolean issueBook(int uID, int bID){
        //I declare a new user object using the getUser method which return the corresponding
        //user based on the id passed in which in this case is the id passed into the method
        User user = getUser(uID);
        Book book = getBook(bID);
        //if isAvailable returns false when passed the book id
        if(!isAvailable(bID)){
            msgLog.add("This book is currently unavailable!");
            //this means said book is not available to be issued so return false
            return false;
        }
        //if the BorrowCount (use getBorrowCount method) for the passed in user is greater than the max limit
        else if(getBorrowCount(uID)>=MAX_BOOK_LIMIT){
            msgLog.add("User has reached the maximum limit of borrowed books!");
            //return false since the user can't borrow anymore books
            return false;
        }
        //if the user has a balance greater than 0
        else if(user.getBalance()>0){
            msgLog.add("User has an outstanding balance of $"+user.getBalance()+"!");
            //this means they have outstanding fines to pay and can't borrow a book so return false
            return false;
        }
        //if the user and book make it through the above checks, the transaction can go through
        else{
            //make a new transaction object with the uID and bID
            Transaction transaction = new Transaction(uID, bID);
            //add the transaction to transactions
            transactions.add(transaction);
            msgLog.add(book.getName()+" has been issued to "+user.getName()+"." );
            msgLog.add("The due date is "+getDueDate(transaction.getIssueDate()));
            //return true, signalling that the transaction went through and the book is checked out
            return true;
        }

    }

    //this method takes in a book id and then marks it as returned and computes the late fine if there is one
    public boolean returnBook(int id){
        //declare a new transaction object initialized as null which will be used later
        Transaction transaction = null;
        //loop through transactions to find a transaction with this book id
        for(int i=0; i<transactions.size(); i++){
            //if the current transaction's book id is equal to the passed in id and that transaction is still issued
            if(transactions.get(i).getBookID()==id && transactions.get(i).isStatus()){
                //set the transaction object we just made to the current transaction in the list since that is the active
                //transaction associated with the passed in id
                transaction = transactions.get(i);
                //break out of the loop once the transaction is set
                break;
            }
        }
        //if transaction is no longer null meaning that there was a matching active transaction found
        if(transaction!=null){
            //you can return the book and compute the fine
            //start by making a new user using getUser with the transaction's userID passed in
            User user = getUser(transaction.getUserID());
            Book book = getBook(id);
            //then set the balance of the user associated this transaction to their current balance/fine + computeFine with the transaction
            //passed in (which will return the fine if there is one, if there isn't compute fine returns 0)
            user.setBalance(user.getBalance()+computeFine(transaction));
            //return the book to available by setting the status of the transaction to false
            transaction.setStatus(false);
            if(computeFine(transaction)==0)
                msgLog.add("Thanks for returning "+book.getName()+"!");
            else {
                msgLog.add("You returned " + book.getName() + " " + computeFine(transaction) + " days late!");
                msgLog.add("Your outstanding balance is $" + user.getBalance());
            }
            //the book was returned to return true
            return true;
        }
        //otherwise the book is not currently being borrowed/wasn't found in transactions
        else
            msgLog.add("Book currently not borrowed");
            //there wasn't a book to return so return false
            return false;
    }

    //this method collects the fine of the passed in user
    public void collectFine(User user){
        if (user.getBalance()<=0){
            msgLog.add("User has no outstanding balances..");
        }
        else{
            msgLog.add("User dues collected....");
            //to "collect the fine" just set the balance of the user back to 0
            user.setBalance(0);
        }
    }

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
            if (p1.getDays() >= MAX_LOAN_DAYS) {
                //set the totalFine equal to $1 times each day past the due date, which we get from p2.getDays()
                //since p2 is the period between the due date and now (it will be positive since we know its past the due date because of the if condition)
                totalFine = 1 * p2.getDays();
            }
        }
        //return totalFine, if the status was false meaning it was returned it should still be 0
        return totalFine;
    }

    //this method is passed a string and returns a list of book objects that match the search field (name)
    public ArrayList<Book> searchBook(String search){
        //new arraylist of book objects to hold all the books that match the search results
        ArrayList<Book> searchRslt = new ArrayList<Book>();
        //loop through the books arraylist to see if any books match the search
        for (int i = 0; i<books.size();i++){
            //if the current books name contains what's being searched
            if (books.get(i).getName().contains(search))
                //add it to the search results list
                searchRslt.add(books.get(i));
        }
        //return the search result list
        return searchRslt;
    }

    //this method is passed a string and returns a list of book objects that match the search field (name)
    public ArrayList<User> searchUser(String search){
        //new arraylist of book objects to hold all the books that match the search results
        ArrayList<User> searchRslt = new ArrayList<User>();
        //loop through the books arraylist to see if any books match the search
        for (int i = 0; i<users.size();i++){
            //if the current books name contains what's being searched
            if (users.get(i).getName().contains(search))
                //add it to the search results list
                searchRslt.add(users.get(i));
        }
        //return the search result list
        return searchRslt;
    }
    //method to print search results
    public void printResults(ArrayList<Book> searchResults){
        //if the passed in search results ray has a size of zero
        if (searchResults.size()==0)
            //that means no books were found for the search, so add the appropriate message to msgLog
            msgLog.add("No books matched the search criteria");
        //advanced for look to go through searchResults with each book as b
        for (Book b: searchResults) {
            //add each book that came up as a search result to msgLog
            msgLog.add("Book Id : "+b.getID()+" Book Name : "+b.getName());

        }
    }

    //this method takes in a book id and returns the book object with corresponding to that id
    public Book getBook(int id){
        //loop through books to find the book with the matching id
        for (int i = 0; i<books.size();i++){
            //if the current book's id is the same as the passed in id
            if(books.get(i).getID()==id)
                //return that book
                return books.get(i);
        }
        //if no book is found with that id return null
        return null;
    }
    //this method takes in a user id and returns the user object with corresponding to that id
    public User getUser(int id){
        //loop through users to find the user with the matching id
        for (int i = 0; i<users.size();i++){
            //if the current user's id is the same as the passed in id
            if(users.get(i).getID()==id)
                //return that user
                return users.get(i);
        }
        //if no user is found with that id return null
        return null;
    }
    //method which returns and clears the current msgLog
    public String getLog() {
        //initialize an empty string which will hold the current msgLog
        String msg = "";
        //loop through msgLog adding each entry to msg followed by a line space
        for(int i = 0; i<msgLog.size();i++){
            msg=msg+(msgLog.get(i)+"\n");
        }
        //clear/empty msgLog
        msgLog.clear();
        //return the message log (now as the string msg)
        return msg;
    }
    //method which takes in a user and returns all the books they have checked out
    public ArrayList<Book> checkedOutBooks(User user) {
        //make a new book array list to hold all the check out books
        ArrayList<Book> checkedOut = new ArrayList<>();
        //loop through transactions to find any open transactions the passed in user currently has
        for(int i =0; i<transactions.size();i++){
            //if the current transaction's user ID matches that of the user passed in and the status of the transaction is true (meaning the book is still checked out)
            if(transactions.get(i).getUserID() == user.getID() && transactions.get(i).isStatus()){
                //add the book to checkedOut using getBook with the book id from the transaction passed in
                checkedOut.add(getBook(transactions.get(i).getBookID()));
            }
        }
        //at the end return the checked out books array
        return checkedOut;
    }
    //method which returns all the current open transactions
    public ArrayList<Transaction> currentTransactions(){
        //make a new transaction array list to hold all the transactions that currently still have books checked out
        ArrayList<Transaction> current = new ArrayList<>();
        //loop through transactions to find all the active ones
        for(int i =0; i<transactions.size();i++){
            //if the status of the current transaction is true (meaning the book is checked out)
            if(transactions.get(i).isStatus())
                //add the transaction to the list
                current.add(transactions.get(i));
        }
        //return the list of current open transactions
        return current;
    }
}