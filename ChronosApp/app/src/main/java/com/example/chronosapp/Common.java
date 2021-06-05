package com.example.chronosapp;

public class Common {
    private static String dbAddress =
//  "http://algolearn-team.prv.pl/1213146_fsa523/" //remoteServer
//  "http://192.168.56.2/chronos/"                 //localMrServer
//  "http://de42e33b4c60.ngrok.io/chronos/"        //localMrServer
//  "http://192.168.8.105/Example/"                //localJkServer
    "http://192.168.1.21/Chronos/";                //localKbServer
    public static String getDbAddress() {return dbAddress;}
}
