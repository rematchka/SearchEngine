/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invertedindexer;
import com.mongodb.AggregationOptions;
import com.mongodb.AggregationOutput;
import java.net.UnknownHostException;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkUpdateRequestBuilder;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteRequestBuilder;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Aggregates;
import static com.mongodb.client.model.Aggregates.sample;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.bson.Document;


/**
 *
 * @author extra
 */
public class DataBaseConnection {
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
    public void insertINDX(TreeMap<String, List<Words>> words,TreeMap<String, Set<String>> Stemmed)
    {
        BulkWriteOperation  bulkWriteOperation= documents.initializeUnorderedBulkOperation();
         
        
        Set<Map.Entry<String, Set<String>>> Stemmedentries = Stemmed.entrySet();
     
           for (Map.Entry<String,  Set<String>> entryins : Stemmedentries) {            
               
               
                
                 String keyS=entryins.getKey();                 
                 BasicDBObject query = new BasicDBObject("Stem", keyS); 
                 
                 Set<String>keyWords=entryins.getValue(); 
                            
                  
                     for (String key : keyWords) {
                         
                 
                         List<Words> files=words.get(key); 
                      

                            for (int i = 0; i < files.size(); i++) {
                                // List<BasicDBObject> URLWORDLIST = new ArrayList<BasicDBObject>();                             
                                  BasicDBObject WL = new BasicDBObject();
                                  WL.put("WordName", key);


                             List<String>poss=new ArrayList();
                             for(int j=0;j<files.get(i).pos.size();j++)
                             {
                                 poss.add(new Integer(files.get(i).pos.get(j)).toString());
                             }
                             String[] posititonArray = poss.toArray(new String[0]);

                             // BasicDBObject URLL = new BasicDBObject();
                              WL.put("ID",files.get(i).getID());
                              WL.put("FreqOfWord",files.get(i).getCount());
                              WL.put("Position",posititonArray);
                              WL.put("TF",files.get(i).TF);

                             //  URLWORDLIST.add(WL);
                            
                             BasicDBObject update = new BasicDBObject();

                            update.put( "$addToSet", new BasicDBObject( "WordURL", WL ) );
 
                            documents.update( query, update,true,true );
                        
                             


                           }          
                            
             
              
             //(new BasicDBObject("Stem", keyS).append("WordURL", URLWORDLIST));            
                    
                    

                  }
                 
                 
                 
                 
                
                
           }
           
//           BulkWriteResult result=bulkWriteOperation.execute();
       }


    public void deleteWord( String stem,String word,int docid)
    {
       
        
        
        
       documents.update(new BasicDBObject("Stem", stem), new BasicDBObject("$pull", new BasicDBObject("WordURL", new BasicDBObject("WordName", word).append("ID", docid))));
      
    }
    public void deleteWordBulk()
    {
        BulkWriteOperation  bulkWriteOperation= documents.initializeUnorderedBulkOperation();
          BasicDBObject query = new BasicDBObject("Stem", "howtobehappynnnnnnnnnnnnnnnnnnnnnnn").append("WordURL", new BasicDBObject("WordName", "hellofromanotherdimenssion").append("ID", 60));  
        //  query.put("WordURL.WordName", "hellofromanotherdimenssion");
          //query.put("WordURL.ID",60);
          
         bulkWriteOperation.find(query).removeOne();
          
          
           List<BasicDBObject> wordURLList = new ArrayList<BasicDBObject>();

                            List<BasicDBObject> URLWORDLIST = new ArrayList<BasicDBObject>(); 
                                BasicDBObject WL = new BasicDBObject();
                                WL.put("WordName", "hellofromanotherdimenssion");


                             List<String>poss=new ArrayList();
                            

                             // BasicDBObject URLL = new BasicDBObject();
                                  WL.put("ID",60);                            

                               URLWORDLIST.add(WL);
          
          
           //   updateReq.update(new BasicDBObject("$pull",new BasicDBObject("WordURL", URLWORDLIST))); 
              BulkWriteResult result=bulkWriteOperation.execute();
    
    }
    public void InsertWordToStem()
    {
          BulkWriteOperation  bulkWriteOperation= documents.initializeUnorderedBulkOperation();
          BasicDBObject query = new BasicDBObject("Stem", "howtobehappynnnnnnnnnnnnnnnnnnnnnnn");  
          BulkWriteRequestBuilder bulkWriteRequestBuilder=bulkWriteOperation.find(query);
          
          
           List<BasicDBObject> wordURLList = new ArrayList<BasicDBObject>();

                            List<BasicDBObject> URLWORDLIST = new ArrayList<BasicDBObject>(); 
                                BasicDBObject WL = new BasicDBObject();
                                WL.put("WordName", "hellofromanotherdimenssion");


                             List<String>poss=new ArrayList();
                            

                             // BasicDBObject URLL = new BasicDBObject();
                              WL.put("ID",80);
                              WL.put("FreqOfWord",21);
                              WL.put("Position",12);
                              WL.put("TF",0.44343);

                               URLWORDLIST.add(WL);
          
            BulkUpdateRequestBuilder updateReq= bulkWriteRequestBuilder.upsert();
              updateReq.update(new BasicDBObject("$addToSet",new BasicDBObject("WordURL", URLWORDLIST))); 
              BulkWriteResult result=bulkWriteOperation.execute();
        
                             

    }
    public void updateWord(String stem,String word,Words w,int wordPos)
    {
        
    }
    
   public void updateURL(String stem,String word,Words w,int wordPos)
    {
       

    
    }
   public void findWordINDOC(int DocID)
   {
       
   }
   public void FindDoc(String word)
   {
       
   }
   
   public void InsertUsingBulk(TreeMap<String, List<Words>> words,TreeMap<String, Set<String>> Stemmed)
   {
        BulkWriteOperation  bulkWriteOperation= documents.initializeUnorderedBulkOperation();
        
        
        
        Set<Map.Entry<String, Set<String>>> Stemmedentries = Stemmed.entrySet();
     
           for (Map.Entry<String,  Set<String>> entryins : Stemmedentries) {               
                            
                String keyS=entryins.getKey();                 
                 BasicDBObject query = new BasicDBObject("Stem", keyS);   
                 Set<String>keyWords=entryins.getValue(); 
                 
                 BulkWriteRequestBuilder bulkWriteRequestBuilder=bulkWriteOperation.find(query);
                             
                     //  BasicDBObject document = new BasicDBObject(); 
                     //  document.put("Stem", keyS);
                       List<BasicDBObject> URLWORDLIST = new ArrayList<BasicDBObject>();
                       BasicDBObject documentW = new BasicDBObject(); 
                       for (String key : keyWords) {
                         
                         
                         List<Words> files=words.get(key); 


                            List<BasicDBObject> wordURLList = new ArrayList<BasicDBObject>();

                            for (int i = 0; i < files.size(); i++) {
                                BasicDBObject WL = new BasicDBObject();
                                WL.put("WordName", key);


                             List<String>poss=new ArrayList();
                             for(int j=0;j<files.get(i).pos.size();j++)
                             {
                                 poss.add(new Integer(files.get(i).pos.get(j)).toString());
                             }
                             String[] posititonArray = poss.toArray(new String[0]);

                             // BasicDBObject URLL = new BasicDBObject();
                              WL.put("ID",files.get(i).getID());
                              WL.put("FreqOfWord",files.get(i).getCount());
                              WL.put("Position",posititonArray);
                              WL.put("TF",files.get(i).TF);

                               URLWORDLIST.add(WL);


                           }                                                                        
              BulkUpdateRequestBuilder updateReq= bulkWriteRequestBuilder.upsert();
              updateReq.update(new BasicDBObject("$addToSet",new BasicDBObject("WordURL", URLWORDLIST)));            
                    
                      //  document.put("WordURL", URLWORDLIST);
                       // documents.insert(document);

                  }
                 
                 
                 
                 
                
                
           }
           
           BulkWriteResult result=bulkWriteOperation.execute();
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
        
        
        
         List<DBObject> aggregationQuery = Arrays.<DBObject>asList(
            new BasicDBObject("$unwind", "$WordLIst"),
             new BasicDBObject("$group", new Document("_id", "$WordLIst.WordName").append("URL", new Document("$addToSet",new Document("URL","$_id").append("Stem","$WordLIst.Stem" ))))
    );

    System.out.println(aggregationQuery);

    Cursor aggregateOutput = documents.aggregate(
            aggregationQuery,
            AggregationOptions.builder()
                    .allowDiskUse(true)
                    .build()
    );


// Print for demo

 
getWordINDX();
 List<DBObject> documentss = new ArrayList<>();
//documents.add(document1);



BasicDBObject document = new BasicDBObject();

// Delete All documents from collection Using blank BasicDBObject
WordDoc.remove(document);


while (aggregateOutput.hasNext())
{
    DBObject dbObject = aggregateOutput.next();
  // System.out.println(dbObject);
   
    String word=(String)dbObject.get("_id");
  BasicDBObject words=new BasicDBObject("Word",word);
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
     
}
    WordDoc.insert(documentss); 
   // mongoClient.close();
               
   }
   public void calculateIDFNew()
   {
   }
   
   
   
   public void getDocuments()
   {
       
                 BasicDBObject query = new BasicDBObject("updated", 1);
                 DBCursor cursor = documents.find(query);
                 int count = documents.find(query).count();
                 List<String>urladdeed=new ArrayList<String>();;
                  if(count>=1)
                  {
                      while(cursor.hasNext())
                      {
                          BasicDBObject document = new BasicDBObject();
                          urladdeed.add((String)document.get("url"));
                      }
                      
                  
                  }
       
   }
   
   public void insertUrLWordArray( TreeMap<String, List<Words>> Words,String URLID)
   {
//       BulkWriteOperation  bulkWriteOperation= documents.initializeUnorderedBulkOperation();
        List<BasicDBObject> wordURLList = new ArrayList<BasicDBObject>();
       Set<Map.Entry<String, List<Words>>> entries = Words.entrySet();
        
        String ID="";
         for (Map.Entry<String,  List<Words>> entry : entries) {        
        BasicDBObject document = new BasicDBObject();  
        String key=entry.getKey();
        document.put("WordName", key);
         List<Words> files=entry.getValue();
         for (int i = 0; i < files.size(); i++) {


                  List<String>poss=new ArrayList();
                  for(int j=0;j<files.get(i).pos.size();j++)
                  {    ID=files.get(i).ID;
                      poss.add(new Integer(files.get(i).pos.get(j)).toString());
                  }
                  String[] posititonArray = poss.toArray(new String[0]);

                
                   document.put("FreqOfWord",files.get(i).getCount());
                   document.put("Position",posititonArray);
                   document.put("TF",files.get(i).TF);
                   document.put("Header", files.get(i).Headerss);
                   document.put("Title", files.get(i).title);
                   document.put("Stem", files.get(i).Stem);
                   

                   // wordURLList.add(URLL);


           }
          wordURLList.add(document);
         }
        deleteURL(ID);
         BasicDBObject query = new BasicDBObject("_id", ID);
         //BasicDBObject xx=new BasicDBObject("WordLIst", wordURLList);
          query.put("WordLIst",wordURLList);
       //  documents.update(query, xx,true,false);
         // documents.update(query, xx, true, false);
      documents.insert(query);
     // mongoClient.close();

       
       
   }
   
   public void deleteURL(String URL)
   {BasicDBObject query = new BasicDBObject("_id", URL);
  //  query.put("_id", URL);
    

    documents.remove(query);
    
   // BasicDBObject b1 = new BasicDBObject("URL", new BasicDBObject("$gt", URL));
 
        // invoke findOne so that the first document is fetched
     //   DBObject doc = documents.findOne(); // get first document
 
        // remove the document fetched using findOne method
       // WriteResult r1 = documents.remove(doc);
       //documents.update( new BasicDBObject("$pull", new BasicDBObject("URL",URL)));
       
   }
   
     
}
