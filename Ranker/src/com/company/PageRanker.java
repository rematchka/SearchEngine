package com.company;

import com.mongodb.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import org.bson.Document;
import org.bson.conversions.Bson;

public class PageRanker {

    double[][] ranks=new double[2][10000005];
    ArrayList<ArrayList<Integer>>adjList= new  ArrayList<ArrayList<Integer>> (10000005);
    int [] noOutlinks=new int [10000005];
    String [] urls=new String[10000005];
    Map id = new HashMap();
    int number=0;

    boolean isconverged(double error)
    {
        for (int i=0;i<number;i++)
            if(Math.abs(ranks[0][i]-ranks[1][i])>error)
                return false;
        return  true;
    }
    public void operate() {
        // write your code here
        boolean even=true;
        double d=0.85;

        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
        DB db = mongoClient.getDB( "SearchEngineDB" );
        DBCollection coll = db.getCollection("urls" );

        BasicDBObject query = new BasicDBObject("downloaded", 1);
        DBCursor cursor = coll.find(query);

        int idd=0;
        try
        {
            while (cursor.hasNext())
            {
                BasicDBObject object = (BasicDBObject) cursor.next();
                System.out.println(object);

                Integer out = object.getInt("outLinks");
                noOutlinks[idd] = out;
                ranks[0][idd] = ranks[1][idd] = 1;
                adjList.add(new ArrayList<Integer>());
                id.put(object.getString("url"), idd);
                urls[idd]=object.getString("url");

                idd++;
                System.out.println(idd);
            }
        } finally { cursor.close(); }

        number=idd;

        cursor = coll.find(query);
        try
        {
            idd=0;
            while (cursor.hasNext())
            {
                double rank=0;
                BasicDBObject object = (BasicDBObject) cursor.next();

                List<String> urls = (List<String>) object.get("inLinks");
                for (String url : urls) {
                    if (id.containsKey(url)) {
                        int x = Integer.parseInt(id.get(url).toString());
                        adjList.get(idd).add(x);
                    }
                }
                idd = idd + 1;
            }
        } finally { cursor.close(); }


        int iterations=1;
        do {
            System.out.println("it"+iterations);
            for(int i=0;i<number;i++)
            {
                double rank=0;
                int v=even?1:0;
                for (int j=0;j<adjList.get(i).size();j++)
                    rank+=(ranks[1-v][adjList.get(i).get(j)]/noOutlinks[adjList.get(i).get(j)]);
                rank=rank*d+(1-d);
                ranks[v][i]=rank;
            }
            iterations++;
            even=!even;
        }while (!isconverged(0.005));

        int v=!even?1:0;
        System.out.println(iterations);
        System.out.println(number);
        for(int i=0;i<number;i++) {
            System.out.println(ranks[v][i]);
            query = new BasicDBObject("url", urls[i]);
            BasicDBObject query2 = new BasicDBObject("rank", ranks[v][i]);
            BasicDBObject query3 = new BasicDBObject("$set",query2);
            coll.update(query,query3);

        }
    }
}
