package com.example.chronosapp;

public class Common {
    private static String dbAddress =
//                "http://algolearn-team.prv.pl/1213146_fsa523/" //remoteServer
                "http://192.168.56.2/chronos/"                 //localMrServer
//                "http://a005c36c1440.ngrok.io/chronos/"         //localMrServer
//            "http://192.168.8.105/Example/"                        //localJkServer
    ;
    public static String getDbAddress() {return dbAddress;}
}
