/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invertedindexer;

import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.bson.Document;

/**
 *
 * @author extra
 */
public class dddddddddbbbbbbbbbbb {
   MongoClient mongoClient=null;
    DB db;
    DBCollection documents;
    
    public void getDocumentINDX()
    {
        documents = db.getCollection("WordsListt");
    }
    public void getURLINDX()
    {
        documents = db.getCollection("urlsDOC");
    }
    public void create()
    {
          mongoClient = null ;
          mongoClient = new MongoClient( "localhost" , 27017 );
          db = mongoClient.getDB( "ProjectTrial" );
  
    }
    public synchronized void insertINDX(TreeMap<String, List<Words>> words)
    {
      

   Set<Map.Entry<String, List<Words>>> entries = words.entrySet();
   for (Map.Entry<String,  List<Words>> entry : entries) {        
        BasicDBObject document = new BasicDBObject();  
        BasicDBObject Query = new BasicDBObject();  
        String key=entry.getKey();
        Query.put("WordName", key);
        Query.put("IDF", 0);
        
              //  String stem=
         List<BasicDBObject> wordURLList = new ArrayList<BasicDBObject>();
         List<Words> files=entry.getValue();
         Query.put("Stem", files.get(0).Stem);
         for (int i = 0; i < files.size(); i++) {
             String ID=files.get(i).ID;
             
             
               documents.update(new BasicDBObject("WordName", key), new BasicDBObject("$pull", new BasicDBObject("$addToSet.$.URL" ,ID)));
             
             
             

                  List<String>poss=new ArrayList();
                  List<String>Type=new ArrayList();
                  for(int j=0;j<files.get(i).pos.size();j++)
                  {
                      poss.add(new Integer(files.get(i).pos.get(j)).toString());
                  }
                   for(int j=0;j<files.get(i).type.size();j++)
                  {    
                      Type.add((files.get(i).type.get(j)).toString());
                  }
                   String[] TypeArray = Type.toArray(new String[0]); 
                  String[] posititonArray = poss.toArray(new String[0]);

                   BasicDBObject URLL = new BasicDBObject();
                   URLL.put("URL",files.get(i).getID());
                   URLL.put("FreqOfWord",files.get(i).getCount());
                   URLL.put("Position",posititonArray);
                   URLL.put("TF",files.get(i).TF);
                   document.put("TF",files.get(i).TF);
                   document.put("Header", files.get(i).Headerss);
                   document.put("Title", files.get(i).title);
                   document.put("Type", TypeArray);

                    wordURLList.add(URLL);


           }
             document.put("$addToSet", new BasicDBObject( "URLList", wordURLList ));
             documents.update( Query, document );
            // documents.insert(document);


     }


    }
    public void deleteWord( String stem,String word,int docid)
    {
        /////////get words with specific urls////////////////////////////////////////
        
        
        ///////////delete word //////////////////////////////////////////////////////////////////
         documents.update(new BasicDBObject("Stem", stem), new BasicDBObject("$pull", new BasicDBObject("Word", new BasicDBObject("WordName", word))));
         
         ///////////////////////delete URL/////////////////////////////////////////////////////////////////////////////////////////
         //collection.update(new BasicDBObject("name", companyName), new BasicDBObject("$pull", new BasicDBObject("departments." + (departmentId - 1) + ".employees", new BasicDBObject("name", employeeName))));

      
    }
    public void InsertWordToStem(List<Words> files,String Stem ,String word)
    {
     BasicDBObject match = new BasicDBObject();
        match.put( "Stem", Stem ); 

        BasicDBObject Wordspec = new BasicDBObject();

        Wordspec.put( "WordName",    word );

        Wordspec.put( "IDF",   0 );


              BasicDBObject URLL = new BasicDBObject();
                   URLL.put("ID","33");
                   URLL.put("FreqOfWord","55");
                   URLL.put("Position","55");
                   URLL.put("TF",0.3333);

                   


           
        Wordspec.put("URL", URLL);  
        BasicDBObject update = new BasicDBObject();
        update.put( "$addToSet", new BasicDBObject( "Word", Wordspec ) );
        documents.update( match, update );
        
    }
public void UpdateFieldINWord()
    {

BasicDBObject match = new BasicDBObject();

match.put( "Stem", "0" );
match.put( "Word.WordName", "0" );


  BasicDBObject URLL = new BasicDBObject();
  
   //  URLL.put("Word.$.IDF",777777777);                 


BasicDBObject update = new BasicDBObject();
update.put( "$set", URLL );

documents.update( match, update );

    }
 public void insertNewURL()
    {
        BasicDBObject match = new BasicDBObject();

match.put( "Stem", "000printf" );
match.put( "Word.WordName", "0" );

                   BasicDBObject URLL = new BasicDBObject();
                   URLL.put("ID","33222225");
                   URLL.put("FreqOfWord",662222255);
                   URLL.put("Position","777522225");
                   URLL.put("TF",0.11111111133356453);


BasicDBObject update = new BasicDBObject();

update.put( "$addToSet", new BasicDBObject( "URL", URLL ) );

documents.update( match, update );

    }
    public void updateWord(String stem,String word,Words w,int wordPos)
    {
         List<String>poss=new ArrayList();
                  for(int j=0;j<w.pos.size();j++)
                  {
                      poss.add(new Integer(w.pos.get(j)).toString());
                  }
        String[] posititonArray = poss.toArray(new String[0]);           
        documents.update(new BasicDBObject("Stem", stem), new BasicDBObject("$push", new BasicDBObject("Word."+wordPos+".URL", new BasicDBObject("ID", w.getID()).append("FreqOfWord",w.getCount()).append("TF", w.TF).append("Position", posititonArray))));

    
    }
    
   public void updateURL(String stem,String word,Words w,int wordPos)
    {
         List<String>poss=new ArrayList();
                  for(int j=0;j<w.pos.size();j++)
                  {
                      poss.add(new Integer(w.pos.get(j)).toString());
                  }
        String[] posititonArray = poss.toArray(new String[0]);           
        documents.update(new BasicDBObject("Stem", stem), new BasicDBObject("$set", new BasicDBObject("Word."+wordPos+".URL", new BasicDBObject("ID", w.getID()).append("FreqOfWord",w.getCount()).append("TF", w.TF).append("Position", posititonArray))));

    
    }
   public void findWordINDOC(int DocID)
   {
       
   }
   public void FindDoc(String word)
   {
       
   }
     
    public void calculateIDF()
   {
      //List<DBObject> all = documents.find().toArray();
     //  AggregateIterable<Document> cursor = documents.aggregate(new Document("$group", new Document("_id"));
       //documents.ag
       
       
       
     /*  BasicDBObject groupFields = new BasicDBObject("_id", null);

		groupFields.append("Word", new BasicDBObject("WordURL.$.WordName", "WordURL.$.ID"));

		DBObject group = new BasicDBObject("$group", groupFields);

		// run aggregation
		List<DBObject> pipeline = Arrays.asList(group);
		AggregationOutput output = documents.aggregate(pipeline);*/
       
    /*    AggregationOutput agout = documents.aggregate(new BasicDBObject("$group",
                   new BasicDBObject("_id",new Document("Word", "$WordName").append("URL", "$ID"))));*/
   /*     AggregationOutput agout = documents.aggregate(new BasicDBObject("$group",
                   new BasicDBObject("_id",new Document("Word", "$WordName").append("URL", "$ID"))));

           Iterator<DBObject> results = agout.results().iterator();


   while(results.hasNext()) {
     DBObject obj = results.next();

     System.out.println(obj.get("_id")+" "+obj.get("URL"));

     
  }*/
  
        
  /* Iterable<DBObject> output = documents.aggregate(Arrays.asList(
        (DBObject) new BasicDBObject("$unwind", "$WordLIst"),   
          (DBObject)  new BasicDBObject("$group", new Document("_id", "$WordLIst.WordName").append("URL", new Document("$addToSet",new Document("URL","$URL"))))
                   
        
        )).results();*/
     /*   Iterable<DBObject> output = documents.aggregate(Arrays.asList(
        (DBObject) new BasicDBObject("$unwind", "$WordLIst"),   
           (DBObject)  new BasicDBObject("$group", new Document("_id", "$WordLIst.WordName").append("URL", new Document("$addToSet",new Document("URL","$URL")))),
           AggregationOptions.builder()
                    .allowDiskUse(true)
                    .build()
                              
        
        )).results();*/
        
        getDocumentINDX();
        
         List<com.mongodb.DBObject> aggregationQuery = Arrays.<com.mongodb.DBObject>asList(
            new BasicDBObject("$unwind", "URLList"),
             new BasicDBObject("$group", new Document("_id", "$WordName").append("URL", new Document("$addToSet",new Document("URL","URLList.URL"))))
    );

    System.out.println(aggregationQuery);

    Cursor aggregateOutput = documents.aggregate(
            aggregationQuery,
            AggregationOptions.builder()
                    .allowDiskUse(true)
                    .build()
    );


// Print for demo

 

 List<com.mongodb.DBObject> documentss = new ArrayList<>();
//documents.add(document1);






while (aggregateOutput.hasNext())
{
    com.mongodb.DBObject dbObject = aggregateOutput.next();
  // System.out.println(dbObject);
   
    String word=(String)dbObject.get("_id");
  BasicDBObject words=new BasicDBObject("WordName",word);
  //BasicDBObject fields = new BasicDBObject().append("URL", 1); // SELECT name
  //BasicDBObject query = new BasicDBObject().append("WordLIst.$.WordName", word); // WHERE name = "Jon"
  //DBCursor results = documents.find(query, fields); // FROM yourCollection
     List<BasicDBObject>  URLs= (List<BasicDBObject>) dbObject.get("URL");
     BasicDBObject dbo=URLs.get(0);
     String stem=(String)dbo.get("Stem");
     double cnt=URLs.size();
     words.put("Count", cnt);
     words.put("Stem", stem);
     double idf=Math.log(1+((double)5000/cnt));
     words.put("idf", idf);
    documentss.add(words);
     //WordDoc.insert(words);
     
  BasicDBObject push = new BasicDBObject();
  push.put("$push",new BasicDBObject("IDF",idf));

documents.update(words, push);
     
}
   // WordDoc.insert(documentss); 
   // mongoClient.close();
               
   }
    
    public void inser_items()
    {
    }
}
