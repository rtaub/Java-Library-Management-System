package model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ConsoleTest {

    public static void main(String args[]) throws InterruptedException, SQLException {
        User u1 = new User("John Stones","jstones@gmail.com","211 Ryder St. Tallahassee, FL 32304", LocalDate.of(1989,06,13), true);
        User u2 = new User("Jack Bauer","jack24@gmail.com","7400 Bay Rd. University Center, MI 48710",LocalDate.of(1988,11,15),false);
        User u3 = new User("Harry Kane","hkane@gmail.com","123 James Boyd Rd. Scranton, PA 28410",LocalDate.of(1988,2,1), false);
        User u4 = new User("Tim Arnold","ta123@gmail.com","3412 Dinsmore Ave, MA 01710",LocalDate.of(1999,1,15), true);

        Book b1 = new Book("Programming with Java","Daniel Liang","Pearson","Education","1234568924",2020);
        Book b2 = new Book("Data Structures and Algorithms","Robert Lafore","Pearson","Education","98726213",2001);
        Book b3 = new Book("Harry Potter and The Chamber of Secrets","J.K. Rowling","Scholastic","Adventure","343255323",1998);
        Book b4 = new Book("Lord of the Rings - The Two Towers","Tolkien","Wiley","Thriller","989636362",1945);

        Library library = new Library();

        library.addUser(u1);
        library.addUser(u2);
        library.addUser(u3);
        library.addUser(u4);


        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);
        library.addBook(b4);


        printLog(library);

        System.out.println("------issue book 0 to user 0------");
        // issue book 0 to user 0
        library.issueBook(0,0);
        printLog(library);

        System.out.println("------attempt to issue book 0 to user 1------");
        library.issueBook(1,0);
        printLog(library);

        System.out.println("------issue 2 more books to user 0------");
        library.issueBook(0,2);
        library.issueBook(0,3);
        printLog(library);

        System.out.println("------ attempt to issue 1 more books to user 0------");
        library.issueBook(0,1);
        printLog(library);



        System.out.println("------ Search for a book that contains the word and ------");
        ArrayList<Book> btemp = library.searchBook("and");
        library.printBooks(btemp);
        printLog(library);

        System.out.println("------ Get Id for a book------");
        Book thisBook = library.getBook(btemp.get(0).getID());//should return the data structure book
        System.out.println(thisBook.getName());

        //Wait for a few seconds to generate user fine
        int sleepTime = 20;
        System.out.println("Sleeping for "+sleepTime+" secs, to simulate a late return");
        Thread.sleep(sleepTime*1000);

        System.out.println("------ Return book 0 from user 0------");
        //accept return for book 0
        library.returnBook(0);
        printLog(library);

        //attempt to issue book to user 1 with outstanding balance
        System.out.println("------ attempt to issue book to user 0 with outstanding balance------");
        library.issueBook(0,1);
        printLog(library);

        //collect user fine
        System.out.println("------ Collect user Fine for user 0------");
        library.collectFine(library.getUser(0));
        printLog(library);

        //attempt to reissue book to user 1 with outstanding balance
        System.out.println("------ attempt to reissue book to user 0 with NO outstanding balance------");
        library.issueBook(0,1);
        printLog(library);

    }

    public static void clearLog(Library l){
        l.msgLog.clear();
    }

    public static void printLog(Library l){
        for(String s : l.msgLog)
            System.out.println(s);
        clearLog(l);
    }


}
