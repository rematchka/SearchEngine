/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invertedindexer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 *
 * @author extra
 */
public class DataBaseCrawlerStatus {
    DBCollection Satus;
    MongoClient mongoClient=null;
    DB db;
    public void getStatus()
   {
       Satus=db.getCollection("State");
   }
    public void closeConnection()
    {
        mongoClient.close();
    }
    public void create()
    {
          mongoClient = null ;
          mongoClient = new MongoClient( "localhost" , 27017 );
          db = mongoClient.getDB( "SearchEngineDB" );
  
    }
   
            
    public String getState()
    {
        DBCursor outout= Satus.find();
        BasicDBObject   document = ( BasicDBObject ) outout.next();
        return (String)document.get("crawlerState");
    }
    
}
