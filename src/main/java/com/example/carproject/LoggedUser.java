package com.example.carproject;

import com.example.carproject.DataAccess.BookDb;
import com.example.carproject.models.Book;
import com.example.carproject.models.BookRental;
import com.example.carproject.models.User;

import java.util.ArrayList;


//TODO: make into singleton
public class LoggedUser
{
    public static User current;

    //its best to update every time used.
    public static ArrayList<BookRental> rentals;

    public static boolean isLogged(){return current != null;}

    public static void updateRentals(){
        if (isLogged())
            rentals = BookDb.fetchRentalsFor(current);
    }

    public static boolean isRentedByMe(Book b){
        return LoggedUser.rentals.stream().anyMatch(rental -> b.getId() == rental.getBookId());
    }
}
