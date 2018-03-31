/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invertedindexer;

import com.mongodb.AggregationOptions;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author extra
 */
public class QueriesOnDataBase {
    MongoClient mongoClient=null;
    DB db;
    DBCollection documents;
    DBCollection WordDoc;
    DBCollection URLS;
    public void getDocumentINDX()
    {
        documents = db.getCollection("WordsNew");
    }
    public void getURLINDX()
    {
        URLS = db.getCollection("urls");
    }
   public void getWordINDX()
   {
       WordDoc=db.getCollection("WordsStemIDF");
   }
   public void NEwWordINDX()
   {
       documents=db.getCollection("NewWordURl");
   }
   public void getStatus()
   {
       
   }
    public void create()
    {
          mongoClient = null ;
          mongoClient = new MongoClient( "localhost" , 27017 );
          db = mongoClient.getDB( "ProjectTrial" );
  
    }
    public void getDocumentContainWord(String word)
    {  
    
    
     NEwWordINDX();
    BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("WordLIst.WordName", word));
    BasicDBObject unwind = new BasicDBObject("$unwind", "$WordLIst");
    BasicDBObject group=new BasicDBObject("$group", new Document("_id","$WordLIst.WordName").append("URL", new Document("$addToSet",new Document("URL","$_id"))));
    

    // first perform a match to get the object which contains a subscription we want [uses the index]
    // then unwind to get the individual subscriptions
    // then match again to get only the subscriptions we want.
    // NOTE: the first step is not redundant, we need to first narrow down using an index (for performance) before unwinding and ultimately getting only the results we want.
    AggregationOutput aggregate = documents.aggregate( unwind,match, group);

  for (com.mongodb.DBObject dbObject : aggregate.results()) {
       System.out.println(dbObject);
        List<BasicDBObject>  URLs= (List<BasicDBObject>) dbObject.get("URL");
        double cnt=URLs.size();
        System.out.println(cnt);
    }
    }
    
    public void getWordsInDocument(String Doc)
    {     NEwWordINDX();
         BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("_id", Doc));
         BasicDBObject unwind = new BasicDBObject("$unwind", "$WordLIst");
         BasicDBObject group=new BasicDBObject("$group", new Document("_id","$_id").append("WordList", new Document("$addToSet",new Document("Word","$WordLIst.WordName"))));
          AggregationOutput aggregate = documents.aggregate( unwind,match, group);
          for (com.mongodb.DBObject dbObject : aggregate.results()) {
         System.out.println(dbObject);
        List<BasicDBObject>  URLs= (List<BasicDBObject>) dbObject.get("WordList");
        double cnt=URLs.size();
        System.out.println(cnt);
    }
    }
    
    
    
    public void getStemMatch(String stem)
    {
        
        
        NEwWordINDX();
        
     //   BasicDBObject fields = new BasicDBObject("WordLIst.TF", 1);
       // fields.put("WordLIst.WordName", 1);
        //BasicDBObject project = new BasicDBObject("$project", fields);
        ///////////////////get words url  tf whose stem is ...////////////////////////////////////////////
        BasicDBObject match = new BasicDBObject("$match", new BasicDBObject("WordLIst.Stem", stem));
        BasicDBObject unwind = new BasicDBObject("$unwind", "$WordLIst");
       BasicDBObject group=new BasicDBObject("$group", new Document("_id","$WordLIst.WordName").append("URLLIst",new Document("$addToSet",new Document("URL","$_id").append("TF","$WordLIst.TF" ))));
    
/////////////////////////////////////////////////////////////////////////
   
    AggregationOutput aggregate = documents.aggregate( unwind,match,group );
   
    
    
    
   for (com.mongodb.DBObject dbObject : aggregate.results()) {
       System.out.println(dbObject);
       String Word=(String)dbObject.get("_id");
       List<BasicDBObject>  URLs= (List<BasicDBObject>) dbObject.get("URLLIst");
         for (int i = 0; i < URLs.size(); i++) {
            BasicDBObject OBJ = URLs.get(i);
            Double TF=(Double)OBJ.get("TF");
            String URL=(String)OBJ.get("URL");
           System.out.println("TF: "+TF+" URL: "+URL);
       }
    // 1 - can call methods of element
    // 2 - can use 'i' to make index-based calls to methods of list

    // ...
}
       // double cnt=URLs.size();
        //System.out.println(cnt);
      
  
 /*   BasicDBObject query=new BasicDBObject("WordLIst.Stem", stem);
    BasicDBObject fields=new BasicDBObject("WordLIst.$", 1);

DBCursor f = documents.find(query, fields);
    while(f.hasNext()) {
        System.out.println(f.next());
    }*/
    ////////////////get words and idf whose stem is ................///////////////////////////////////////////////////
    
    getWordINDX();
    BasicDBObject whereQuery = new BasicDBObject();
    whereQuery.put("Stem", stem);
    DBCursor cursor = WordDoc.find(whereQuery);
    while(cursor.hasNext()) {
        System.out.println(cursor.next());
    }
    
     
    }
    
}
