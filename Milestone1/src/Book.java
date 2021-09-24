public class Book {
    //attributes of a book are declared and booksAdded is initialized to 0 since you'll have no books at first
    private int ID;
    private String name;
    private String author;
    private String publisher;
    private String genre;
    private String ISBN;
    private long year;
    private static int booksAdded=0;
    //constructor for a book object, the ID is not taken in and is instead set to booksAdded + 1, this way whenever a new
    //book is created and this constructor is called, booksAdded will be increased by 1 giving each book the correct id
    public Book(String name, String author, String publisher, String genre, String ISBN, long year) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.ISBN = ISBN;
        this.year = year;
        this.ID =booksAdded++;
    }

    //getters and setters for the book attributes
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    //toString method that prints the book id and name
    @Override
    public String toString() {
        return "ID=" + ID +
                ", name='" + name + '\'' +
                ' ';
    }
    //equals method which takes in an object and compares it to the current book
    public boolean equals(Object b){
        //since the equals method uses the datatype object for the parameter the obj passed in is cast as a Book
        Book book = (Book) b;
        //if the passed in object is an instance of Book
        if(b instanceof Book) {
            //if the passed in book id is equal to this/the current book
            if (this.ID == book.ID) {
                //they're the same so return true
                return true;
            }
            //else if the ids arent the same
            else
                //return false since they aren't the same book
                return false;
        }
        //otherwise
        else
            //return false since the object isn't a book
            return false;
    }
}