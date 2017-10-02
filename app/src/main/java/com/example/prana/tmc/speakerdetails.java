package com.example.prana.tmc;

/**
 * Created by prana on 8/13/2017.
 */

public class speakerdetails {
    String nm;
    String tp;
    String tim;//seconds
    int votecount;
    speakerdetails(){}
    speakerdetails(String n,String t,String ti){
        nm=n;
        tp=t;
        tim=ti;
        votecount=0;
    }
    void updatevote()
    {
        votecount=votecount+1;
    }
    int getvotes()
    {
        return votecount;
    }
}
