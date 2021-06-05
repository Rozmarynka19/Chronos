package com.example.chronosapp;

public class Common {
    private static String dbAddress =
//                "http://algolearn-team.prv.pl/1213146_fsa523/" //remoteServer
                "http://192.168.56.2/chronosg/"                 //localMrServer
//                "http://a005c36c1440.ngrok.io/chronos/"         //localMrServer
//            "http://192.168.8.105/Example/"                        //localJkServer
//           "http://192.168.56.1/1213146_fsa523/"                  //localServerKK
//                "http://192.168.1.99/"                             //localOmServer
    ;
    public static String getDbAddress() {return dbAddress;}
}
