package model;

import java.time.LocalDate;

public class User {
    //attributes of a user are declared and booksAdded is initialized to 0 since you'll have no books at first
    private int ID;
    private String name;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
    private boolean isStudent;
    private double balance=0.0;
    private static int usersAdded=0;
    //constructor for a user object, the ID is not taken in and is instead set to usersAdded + 1, this way whenever a new
    //user is created and this constructor is called, usersAdded will be increased by 1 giving each user the correct id
    public User(String name, String email, String address, LocalDate dateOfBirth, boolean isStudent) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.isStudent = isStudent;
        this.ID = usersAdded++;
    }

    //getters and setters for the user attributes
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean getStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    //toString method that prints the user id and name
    @Override
    public String toString() {
        return "ID=" + ID +
                ", name='" + name + '\'' +
                ' ';
    }
    //equals method which takes in an object and compares it to the current user
    public boolean equals(Object u){
        //since the equals method uses the datatype object for the parameter the obj passed in is cast as a User
        User user = (User) u;
        //if the passed in object is an instance of User
        if(u instanceof User) {
            //if the passed in user id is equal to this/the current user
            if (this.ID == user.ID) {
                //they're the same so return true
                return true;
            }
            //else if the ids arent the same
            else
                //return false since they aren't the same user
                return false;
        }
        //otherwise
        else
            //return false since the object isnt a user
            return false;
    }

}