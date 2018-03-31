/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invertedindexer;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author extra
 */
public class InvertedIndexer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
      Set<String> Files = new HashSet<String>();
        
        DataBaseCrawlerStatus DBCS=new DataBaseCrawlerStatus();
        DBCS.create();
        DBCS.getStatus();
        String state=DBCS.getState();
       // DBCS.closeConnection();
        ExecutorService executor;
        executor= Executors.newFixedThreadPool(3);
        int cnt=0;
        
       while(state.equals("started")||state.equals("ReadingSeedSet")||state.equals("crawling"))
        { 
            
           File file = new File("C:/Users/extra/Documents/NetBeansProjects/WebCrawler/.idea/WebPages");
           File[] files = file.listFiles();
           for(File f: files){
               if(f.exists() && !f.isDirectory())
               {Files.add(f.getName());
               //System.out.println(f.getName());
               }
              
             }
        //   System.out.println("Visit Number "+cnt);
          Iterator<String> iterator = Files.iterator();
                while (iterator.hasNext()) {
                    String Path = iterator.next();
                    File f= new File("C:/Users/extra/Documents/NetBeansProjects/WebCrawler/.idea/WebPages/"+Path);
                    if(f.exists() && !f.isDirectory())
                    {                    
                            iterator.remove();
                           IndexerJob idx=new IndexerJob("C:/Users/extra/Documents/NetBeansProjects/WebCrawler/.idea/WebPages/"+Path);
                            executor.execute(idx);
                    }
                   cnt++;
          //        System.out.println(Files.size()+" Size of set ");
                    
                }
            
           
           
        DBCS.create();
        DBCS.getStatus();
        state=DBCS.getState();
       // DBCS.closeConnection();
        }
         executor.shutdown();
        while (!executor.isTerminated()) {   }  
       
          executor= Executors.newFixedThreadPool(3);
           File file = new File("C:/Users/extra/Documents/NetBeansProjects/WebCrawler/.idea/WebPages");
           File[] files = file.listFiles();
           //System.out.println(Files.size()+" Size of set ");
            for(File f: files){
                if(f.exists() && !f.isDirectory())
               {Files.add(f.getName());
           //    System.out.println(f.getName());
               }
              
             }
            //System.out.println(Files.size()+" Size of set ");
           Iterator<String> iterator = Files.iterator(); 
           while (iterator.hasNext()) {
               
                    String Path = iterator.next();
                    File f= new File("C:/Users/extra/Documents/NetBeansProjects/WebCrawler/.idea/WebPages/"+Path);
                    if(f.exists() && !f.isDirectory())
                    {     // System.out.println(Files.size()+" Hello ");              
                           iterator.remove();
                           IndexerJob idx=new IndexerJob("C:/Users/extra/Documents/NetBeansProjects/WebCrawler/.idea/WebPages/"+Path);
                            executor.execute(idx);
                    }
                    
                    
                }
       
        executor.shutdown();
        while (!executor.isTerminated()) {   }  
          
         DataBaseConnection dbc=new DataBaseConnection();
         dbc.create();
         dbc.NEwWordINDX();
         dbc.calculateIDF();
    
    
    
    
  
    
    
    
    
    
    
    ////////////////Queries get Documents Containg Given Word///////////////////////////
  // QueriesOnDataBase QDB=new QueriesOnDataBase();
   //QDB.create();
    
    /////////////////////////////////////
  //QDB.getDocumentContainWord("02282018");
    /////////////////////Queries get Words IN Document///////////
  //  QDB.getWordsInDocument("https://www.tripadvisor.com/HotelsNear-g297694-qDPS-Denpasar_Bali.html");
    //////////////////////////Queries get stem words related//////////////////////////////////////////////////
  //QDB.getStemMatch("0254");

    }
     
    
}
