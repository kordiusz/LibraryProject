package com.example.carproject.DataAccess;

public class LibraryRules
{

    public static final int maxPoints = 100;
    public static final int maxBorrowedAtTheSameTime = 3;

    public static int pointsForReturn(boolean inTime){
        return inTime? 10 : -20;
    }




}
