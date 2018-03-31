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
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author extra
 */
public class DataBaseOld {
   MongoClient mongoClient=null;
    DB db;
    DBCollection documents;
    
    public void getDocumentINDX()
    {
        documents = db.getCollection("Words");
    }
    public void getURLINDX()
    {
        documents = db.getCollection("urls");
    }
    public void create()
    {
          mongoClient = null ;
          mongoClient = new MongoClient( "localhost" , 27017 );
          db = mongoClient.getDB( "ProjectTrial" );
  
    }
    public void insertINDX(TreeMap<String, List<Words>> words,TreeMap<String, Set<String>> Stemmed)
    {
       Set<Map.Entry<String, Set<String>>> Stemmedentries = Stemmed.entrySet();
     
           for (Map.Entry<String,  Set<String>> entryins : Stemmedentries) {                      
               
               
                
                 String keyS=entryins.getKey();
                 
                 BasicDBObject query = new BasicDBObject("Stem", keyS);   
                 Set<String>keyWords=entryins.getValue();
                 
                 DBCursor cursor = documents.find(query);
                 int count = documents.find(query).count();
                  if(count>=1)
                  {
                      List<String>Words=new ArrayList<String>();
                      BasicDBObject document = new BasicDBObject();
                      while( cursor.hasNext() )                          
                      {
                          document = ( BasicDBObject ) cursor.next();
                          Words.add((String)document.get("WordName"));
                          
                      }
                      ///////////check if word exist in database /////////////////
                       List<BasicDBObject> wordList = new ArrayList<BasicDBObject>();
                      for (String key : keyWords) {
                          if (Words.contains(key))
                          {
                              int index=Words.indexOf(key);
                                // BasicDBObject WL = new BasicDBObject();
                                // WL.put("WordName", key);
                                //WL.put("IDF", 0);
                                List<Words> files=words.get(key); 
                                 for (int i = 0; i < files.size(); i++) {
                                     
                                     updateURL(keyS,key,files.get(i),index);
                                 }
                                
                              ///////////////insert URL in database//////////////////////
                          }
                          else{
                              /////////////insert word+URL in database////////////////////
                            
                              List<Words> files=words.get(key); 
                              List<BasicDBObject> wordURLList = new ArrayList<BasicDBObject>();
                              BasicDBObject WL = new BasicDBObject();
                               WL.put("WordName", key);
                               WL.put("IDF", 0);

                            for (int i = 0; i < files.size(); i++) {


                             List<String>poss=new ArrayList();
                             for(int j=0;j<files.get(i).pos.size();j++)
                             {
                                 poss.add(new Integer(files.get(i).pos.get(j)).toString());
                             }
                             String[] posititonArray = poss.toArray(new String[0]);

                              BasicDBObject URLL = new BasicDBObject();
                              URLL.put("ID",files.get(i).getID());
                              URLL.put("FreqOfWord",files.get(i).getCount());
                              URLL.put("Position",posititonArray);
                              URLL.put("TF",files.get(i).TF);

                               wordURLList.add(URLL);


                                                                  }
                            
                                WL.put("URL", wordURLList);  
                                 wordList.add(WL);
                              
                              
                          }
                      }
                       BasicDBObject update = new BasicDBObject();  
                       BasicDBObject match = new BasicDBObject();
                        match.put( "Stem", keyS ); 
                       update.put( "$addToSet", new BasicDBObject( "Word", wordList ) );
                       documents.update( match, update );
                      
                      
                  }
                  else{
                      BasicDBObject document = new BasicDBObject(); 
                       document.put("Stem", keyS);
                       List<BasicDBObject> wordList = new ArrayList<BasicDBObject>();
                       BasicDBObject documentW = new BasicDBObject(); 
                       for (String key : keyWords) {
                         BasicDBObject WL = new BasicDBObject();
                         WL.put("WordName", key);
                         WL.put("IDF", 0);
                         List<Words> files=words.get(key); 


                            List<BasicDBObject> wordURLList = new ArrayList<BasicDBObject>();

                            for (int i = 0; i < files.size(); i++) {


                             List<String>poss=new ArrayList();
                             for(int j=0;j<files.get(i).pos.size();j++)
                             {
                                 poss.add(new Integer(files.get(i).pos.get(j)).toString());
                             }
                             String[] posititonArray = poss.toArray(new String[0]);

                              BasicDBObject URLL = new BasicDBObject();
                              URLL.put("ID",files.get(i).getID());
                              URLL.put("FreqOfWord",files.get(i).getCount());
                              URLL.put("Position",posititonArray);
                              URLL.put("TF",files.get(i).TF);

                               wordURLList.add(URLL);


                                                                  }
                            
                                WL.put("URL", wordURLList);  
                                 wordList.add(WL);




                    

                    
                     }
                        document.put("Word", wordList);
                        documents.insert(document);

                  }
                 
                 
                 
                 
                
                
           }
       }

/*    Set<Map.Entry<String, List<Words>>> entries = words.entrySet();
   for (Map.Entry<String,  List<Words>> entry : entries) {        
        BasicDBObject document = new BasicDBObject();  
        String key=entry.getKey();
        document.put("WordName", key);
        document.put("IDF", 0);
         List<BasicDBObject> wordURLList = new ArrayList<BasicDBObject>();
         List<Words> files=entry.getValue();
         for (int i = 0; i < files.size(); i++) {


                  List<String>poss=new ArrayList();
                  for(int j=0;j<files.get(i).pos.size();j++)
                  {
                      poss.add(new Integer(files.get(i).pos.get(j)).toString());
                  }
                  String[] posititonArray = poss.toArray(new String[0]);

                   BasicDBObject URLL = new BasicDBObject();
                   URLL.put("ID",files.get(i).getID());
                   URLL.put("FreqOfWord",files.get(i).getCount());
                   URLL.put("Position",posititonArray);
                   URLL.put("TF",files.get(i).TF);

                    wordURLList.add(URLL);


           }
             document.put("URL", wordURLList);
             documents.insert(document);


     }


*/
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
     
    
}
