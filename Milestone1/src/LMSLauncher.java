import java.time.LocalDate;
import java.util.ArrayList;

public class LMSLauncher {

    public static void main(String args[]) throws InterruptedException {
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

        System.out.println("----------User List-------------");
        for (User u : library.users)
            System.out.println(u);

        System.out.println("----------Book List-------------");
        for (Book b : library.books)
            System.out.println(b);




        System.out.println("------issue book 0 to user 0 ------");
        // issue book 0 to user 0
        boolean res = library.issueBook(0,0);
        System.out.println(res);

        System.out.println("------attempt to issue book 0 to user 1------");
        res = library.issueBook(1,0);
        System.out.println(res);

        System.out.println("------issue bookid 2 and 3 more books to user 0------");
        res=library.issueBook(0,2);
        System.out.println(res);
        res=library.issueBook(0,3);
        System.out.println(res);


        System.out.println("------ attempt to issue 1 more books to user 0------");
        res=library.issueBook(0,1);
        System.out.println(res);

        System.out.println("------ view all transactions------");
        for (Transaction t : library.transactions)
            System.out.println(t);

        System.out.println("------ Search for a book that contains the word \'and\' ------");
        ArrayList<Book> btemp = library.searchBook("and");

        if (btemp.size()==0)
            System.out.println("No books matched the search criteria");
        else
            System.out.println(btemp);



        System.out.println("------ Get Id for data structure book------");
        Book thisBook = library.getBook(btemp.get(0).getID());//should return the data structure book
        System.out.println(thisBook);



        System.out.println("------ Return book 0 from user 0------");
        //accept return for book 0
        res = library.returnBook(0);
        System.out.println(res);

        System.out.println("------ view all transactions------");
        for (Transaction t : library.transactions)
            System.out.println(t);


        System.out.println("------ Hacking the balance field for user id 0 to $50------");
        User temp = library.getUser(0);
        temp.setBalance(50);
        System.out.println(temp);


        //attempt to issue book to user 1 with outstanding balance
        System.out.println("------ attempt to issue book to user 0 with outstanding balance------");
        res = library.issueBook(0,1);
        System.out.println(res);

        //collect user fine
        System.out.println("------ Collected user Fine for user 0------");
        library.collectFine(library.getUser(0));


        //attempt to reissue book to user 1 with outstanding balance
        System.out.println("------ attempt to reissue book to user 0 with NO outstanding balance------");
        res = library.issueBook(0,1);
        System.out.println(res);

    }

}
