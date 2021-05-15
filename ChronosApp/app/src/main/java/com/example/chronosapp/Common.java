package com.example.chronosapp;

public class Common {
    private static String dbAddress =
//                "http://algolearn-team.prv.pl/1213146_fsa523/" //remoteServer
                "http://192.168.56.2/chronos/"                 //localMrServer
    ;
    public static String getDbAddress() {return dbAddress;}
}
