/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invertedindexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author extra
 */
public class IndexerJob implements Runnable {
      public String docID;
      public int Countofwords;
     
   String Path;
    TreeMap<String, List<Words>> index = new TreeMap<String, List<Words>>();
    List<String> files = new ArrayList<String>();
    TreeMap<String,Long>overallfreq=new TreeMap<String,Long>(); 
    TreeMap<String,Map<Integer,Integer>>WordCountInDoc=new TreeMap <String,Map<Integer,Integer>>();
    TreeMap<String,Integer>TF=new TreeMap <String,Integer>();
    TreeMap<String,Integer>IDF=new TreeMap<String,Integer>();
    List<String> headers=new ArrayList<String>();
    List<String> Titles=new ArrayList<String>();
    List<String> divs=new ArrayList<String>();
    List<String> parag=new ArrayList<String>();
    List<String> bold=new ArrayList<String>();
    List<String> Table=new ArrayList<String>();
    List<String> h1=new ArrayList<String>();
    List<String> h2=new ArrayList<String>();
    List<String> h3=new ArrayList<String>();
    List<String> h4=new ArrayList<String>();
    List<String> h5=new ArrayList<String>();
    List<String> h6=new ArrayList<String>();
    
    TreeMap<String, Set<String>> Stemmed = new TreeMap<String, Set<String>>();
    
   
       ///////////////////////////////list of stop words to be changed to read from file//////////////////////////////////////// 
    List<String> stopwords = Arrays.asList("a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the");
        /*("a", "able", "about",
			"across", "after", "all", "almost", "also", "am", "among", "an",
			"and", "any", "are", "as", "at", "be", "because", "been", "but",
			"by", "can", "cannot", "could", "dear", "did", "do", "does",
			"either", "else", "ever", "every", "for", "from", "get", "got",
			"had", "has", "have", "he", "her", "hers", "him", "his", "how",
			"however", "i", "if", "in", "into", "is", "it", "its", "just",
			"least", "let", "like", "likely", "may", "me", "might", "most",
			"must", "my", "neither", "no", "nor", "not", "of", "off", "often",
			"on", "only", "or", "other", "our", "own", "rather", "said", "say",
			"says", "she", "should", "since", "so", "some", "than", "that",
			"the", "their", "them", "then", "there", "these", "they", "this",
			"tis", "to", "too", "twas", "us", "wants", "was", "we", "were",
			"what", "when", "where", "which", "while", "who", "whom", "why",
			"will", "with", "would", "yet", "you", "your");*/
    
    
    
    /////////parsing html text////////////////////
  
    public String HTMLToText(String html)
    {
        return Jsoup.parse(html).text();
    }
     public  TreeMap<String, List<Words>> getWords()
   {
       return index;
   }
        String body;
        String id;
        String title;
        Elements hTags;
        
        
        ////////extracting headeer ,tilte, and body
     public void getInfo(Document doc)
    {   
        title = doc.title();
        
        body = doc.body().text();
        Countofwords+=body.length();
        hTags = doc.select("h1, h2, h3, h4, h5, h6");
    }
        public void extractDivs(Document doc)
        {
            Elements divss = doc.select("div");
       //divs.get(0).html();
       for(Element elem : divss){
         //System.out.println(elem.html()); //get all elements inside div
         //divs.add(elem.html());
           for (String _word : elem.html().split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              divs.add(newword);
                         }
         }
       
       
     }
        
        
         public void extractParag(Document doc)
        {
           Elements paragraphs = doc.select("p");
            for(Element p : paragraphs)
            {  //System.out.println();
           // parag.add(p.text());
                for (String _word : p.text().split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              parag.add(newword);
                         }
            
            }
        }
         public void getBold(Document doc)
         {
             Elements boldTags = doc.getElementsByTag("b");

                for (Element tag : boldTags) {
                   // System.out.println(tag.text());
                  for (String _word : tag.text().split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              bold.add(newword);
                         }
                }
                Elements strongTags = doc.getElementsByTag("strong");

                for (Element tag : strongTags) {
                   // System.out.println(tag.text());
                    
                    
                    for (String _word : tag.text().split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              bold.add(newword);
                         }
                }
        }
      
     public void extractTitles()
     {
          for (String _word : title.split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              Titles.add(newword);
          }
         
     }
     public void extractHeaders()
     {
         //Elements elements = document.body().select("*");

    for (Element element : hTags) {
    
    String x=element.ownText();
    //System.out.println(element.ownText());
     for (String _word : x.split(" ")) {
               _word = _word.replace("^\"|\"$", "");
                                _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              headers.add(newword);
          }
}
     }
     
     public void getHeader1(Document doc)
     {
         Elements heads = doc.select("h1");
// iterate and get inner html of that elements by
          String html = heads.html();
          for (String _word : html.split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              h1.add(newword);
                         }
     }
       public void getHeader2(Document doc)
     {
         Elements heads = doc.select("h2");
// iterate and get inner html of that elements by
          String html = heads.html();
          for (String _word : html.split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              h2.add(newword);
                         }
     }
         public void getHeader3(Document doc)
     {
         Elements heads = doc.select("h3");
// iterate and get inner html of that elements by
          String html = heads.html();
          for (String _word : html.split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              h3.add(newword);
                         }
     }
           public void getHeader4(Document doc)
     {
         Elements heads = doc.select("h4");
// iterate and get inner html of that elements by
          String html = heads.html();
          for (String _word : html.split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              h4.add(newword);
                         }
     }
             public void getHeader5(Document doc)
     {
         Elements heads = doc.select("h5");
// iterate and get inner html of that elements by
          String html = heads.html();
          for (String _word : html.split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              h5.add(newword);
                         }
     }
               public void getHeader6(Document doc)
     {
         Elements heads = doc.select("h6");
// iterate and get inner html of that elements by
          String html = heads.html();
          for (String _word : html.split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              h6.add(newword);
                         }
     }
               public void extractTable(Document doc)
               {
                    Element table = doc.select("table").first();
                Iterator<Element> iterator = table.select("td").iterator();
                while(iterator.hasNext()){
                     for (String _word : iterator.next().text().split(" ")) {
                                 _word = _word.replace("^\"|\"$", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                 _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || ( _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
              
              Table.add(newword);
                         }
                    //System.out.println("text : "+iterator.next().text()); //kolom -1
                   // System.out.println("text : "+iterator.next().text()); //kolom -2
                }
               }
     ////////////////////////////parse file ///////////////////////
     /*
     1- parse file
     2- split words on space
     4-remove sop words
     5-Stemming
     3- store it udate freq ,tf,count,pos
     */
    public void parseFile(String text)
    {
         Document doc = Jsoup.parse(text);
         parseFile1(doc);
         getInfo(doc);  
         extractDivs(doc);
         extractParag(doc);
         getBold(doc);
        extractTitles();
        extractHeaders();
        getHeader6(doc);
        getHeader5(doc);
        getHeader4(doc);
        getHeader3(doc);
        getHeader2(doc);
        getHeader1(doc);
      //  extractTable(doc);
        //getHeader6();
          int pos=0;
          int strLen;
         for (String _word : body.split(" ")) {
                                 pos++;
                                Stemmer s = new Stemmer();
                                _word = _word.replace("^\"|\"$", "");
                                _word=_word.replaceAll("[^a-zA-Z0-9]", "");
                                _word=_word.replaceAll("[^a-zA-Z0-9\\s+]", "");
                                if (_word == null || (strLen = _word.length()) == 0||_word.contains(" "))
                                continue;
				String word = _word.toLowerCase();
                               /////to call steming.///////////////////                            
                            
                               String newword=word;
				
                                
				if (stopwords.contains(newword))
					continue;
                             
                                //   for (int c = 0; c < newword.length(); c++)
                                  //     s.add(newword.charAt(c));
                                  Stemmer2 ss=new Stemmer2();
                                  
                                   String stemmed=ss.stem2(newword);
                                  
                                 
                                  // s.stem();
                                  
                                  // stemmed = s.toString();
                                   
                                   
                                   
                                   //////////////store stemmed and word///////////////////
                                   Set<String>xxx=Stemmed.get(stemmed);
                                   if(xxx==null)
                                   {
                                       xxx =  new HashSet<String>() {};
					Stemmed.put(stemmed, xxx);
                                        
                                        xxx.add(newword);                                       
                                   }
                                   else {
                                       xxx.add(newword);
                                   }
                          

				List<Words> idx = index.get(newword);
                               // Long num=overallfreq.get(word);
				if (idx == null) {
					idx = new LinkedList<Words>();
					index.put(newword, idx);
                                        overallfreq.put(newword,new Long(0));
                                        idx.add(new Words(docID, pos));
                                         Iterator<Words> iterator = idx.iterator();

                                    if (iterator.hasNext()) {
                                      Words foo = iterator.next();
                                      if( divs.contains(newword))
                                      {
                                          foo.SetType("Divs");
                                      }
                                    /*  if( Table.contains(newword))
                                      {
                                          foo.SetType("Table");
                                      }*/
                                      if( bold.contains(newword))
                                      {
                                          foo.SetType("Bold");
                                      }
                                      if( parag.contains(newword))
                                      {
                                          foo.SetType("Parag");
                                      }
                                      if( h1.contains(newword))
                                      {
                                          foo.SetType("H1");
                                      }
                                      if( h2.contains(newword))
                                      {
                                          foo.SetType("H2");
                                      }
                                      if( h3.contains(newword))
                                      {
                                          foo.SetType("H3");
                                      }
                                      if( h4.contains(newword))
                                      {
                                          foo.SetType("H4");
                                      }
                                      if( h5.contains(newword))
                                      {
                                          foo.SetType("H5");
                                      }
                                      if( h6.contains(newword))
                                      {
                                          foo.SetType("H6");
                                      }
                                      
                                      foo.Headerss=headers.contains(newword);
                                      foo.title=title.contains(newword);
                                      foo.Stem=stemmed;
                                      
                                    }
                                        
				}
                                else {
                                // idx.
                                  Iterator<Words> iterator = idx.iterator();

                                    if (iterator.hasNext()) {
                                      Words foo = iterator.next();
                                      foo.pos.add(pos) ;
                                      foo.cnt++;
                                      foo.Headerss=headers.contains(newword);
                                      foo.title=title.contains(newword);
                                      foo.Stem=stemmed;
                                      if( divs.contains(newword))
                                      {
                                          foo.SetType("Divs");
                                      }
                                   /*   if( Table.contains(newword))
                                      {
                                          foo.SetType("Table");
                                      }*/
                                      if( bold.contains(newword))
                                      {
                                          foo.SetType("Bold");
                                      }
                                      if( parag.contains(newword))
                                      {
                                          foo.SetType("Parag");
                                      }
                                      if( h1.contains(newword))
                                      {
                                          foo.SetType("H1");
                                      }
                                      if( h2.contains(newword))
                                      {
                                          foo.SetType("H2");
                                      }
                                      if( h3.contains(newword))
                                      {
                                          foo.SetType("H3");
                                      }
                                      if( h4.contains(newword))
                                      {
                                          foo.SetType("H4");
                                      }
                                      if( h5.contains(newword))
                                      {
                                          foo.SetType("H5");
                                      }
                                      if( h6.contains(newword))
                                      {
                                          foo.SetType("H6");
                                      }
                                      
                                    }
                                
                                }
                              
				//idx.add(new Words(docID, pos));
                               overallfreq.put(newword, overallfreq.get(newword) + 1);
                               
			}
        
        calculateTF();
     //   Print();
         
    }
  public void calculateTF()
    {
        
Set<Map.Entry<String, List<Words>>> entries = index.entrySet();
       
for (Map.Entry<String,  List<Words>> entry : entries) {
   
    String key=entry.getKey();
    Map<Integer, Integer> map2 = WordCountInDoc.get(key);
    List<Words> files=entry.getValue();
    
    for (int i = 0; i < files.size(); i++) {
                  /*  if(files.get(i).getID()==docID)
                    {
                        if(map2!=null)
                        {
                            if(map2.containsKey(docID))
                            {
                               map2.put(docID, map2.get(docID) + 1);
                               WordCountInDoc.put(key, map2);
                            }
                            else 
                            {
                                map2.put(docID, 1);
                                WordCountInDoc.put(key, map2);
                            }
                             TF.put(key,  TF.get(key)+1);
                            
                        }
                        else 
                        {   map2=new TreeMap<Integer,Integer>();
                                map2.put(docID, 1);
                                WordCountInDoc.put(key, map2);
                                TF.put(key, 1);
                        }
                         
                    }*/
                  int cnt=files.get(i).getCount();
                  files.get(i).TF= ((1+Math.log(cnt))/(Math.log10(Countofwords)));
		
	}
//     TF.put(key,  TF.get(key)/Countofwords);
   
           }
        
    }
  
 public void readFiles() throws FileNotFoundException, IOException
    {
         
         BufferedReader br;
         br = new BufferedReader(new FileReader(this.Path));
         StringBuilder sb = new StringBuilder();
      
         try {         
           String line = br.readLine();
           this.docID=line;
           line = br.readLine();

           while (line != null) {
           sb.append(line);
           sb.append(System.lineSeparator());
              
           line = br.readLine();
        
               }
           String everything = sb.toString();   
         
           parseFile(everything);                
       

             } 
         finally {
              br.close();
                 }

        
    }
 
   public void Print()
   {
      
        Set<Map.Entry<String, List<Words>>> entries = index.entrySet();
        for (Map.Entry<String,  List<Words>> entry : entries) {
        //System.out.println(entry.getKey());
        String key=entry.getKey();
        List<Words> files=entry.getValue();

        for (int i = 0; i < files.size(); i++) {
                  //System.out.println(lList.get(i));
                  System.out.println("String "+ key+ " ID  "+files.get(i).getID()+" Position "+files.get(i).getposition());

          }
  
        }  
     
      
    }
   
   TreeMap<String, Set<String>> getStemmed()
   {
       return Stemmed;
   }
   IndexerJob(String Path)
   {
       this.Path=Path;
   }
  
   @Override
    public void run()
    {
          try {
              File f= new File(this.Path);
                if(f.exists() && !f.isDirectory())
                {readFiles();
              File file = new File(this.Path);
              file.delete();
               
                //  System.out.println("Path "+this.Path+ "Thread Name "+ Thread.currentThread().getName());
             
             if(!index.isEmpty()&&!Stemmed.isEmpty())
             {    DataBaseConnection dbc=new DataBaseConnection();
                  dbc.create();
                  dbc.NEwWordINDX();
                   dbc.insertUrLWordArray(index,"");
             }
             
              }
             
          } catch (IOException ex) {
              Logger.getLogger(IndexerJob.class.getName()).log(Level.SEVERE, null, ex);
          }
        
    }
    
    
    
      public void parseFile1(Document doc)
    {
       
         String fileName=docID;

           String line="";
            //document=Jsoup.connect(url).timeout(100).get();


           
           String fileTitle=doc.title().toString();
             line+=fileTitle;


          String  fileBody=doc.body().text();
            line+=fileBody;

            Elements elem=doc.select("h1,h2,h3,h4,h5,h6");
         String fileHeaders="";
            for(Element e:elem)
            {
                fileHeaders+=e.text()+'\n';

            }
            line+=fileHeaders;

            //Get description from document object.

              Elements des= doc.select("meta[name=description]");
              String description="";
              if(des.size()>0&&des!=null&&des.get(0)!=null)   description=des .get(0).attr("content");

           //   System.out.println(description);


            //Get keywords from document object.
            String keywords="";
            Elements keys =
                    doc.select("meta[name=keywords]");
                  if(keys.size()>0&&keys!=null&&keys.first()!=null)         keywords=keys.first().attr("content");


            String docString=doc.toString();
            if(line.equals(""))
            {
                System.out.println("doc is empty");


            }
            else {
                   DataBaseConnection dbc=new DataBaseConnection();
                  dbc.create();
                  dbc.NEwURL();
                   dbc.insertUrL(fileName,docString,keywords,description,fileHeaders,line,fileBody);

            }

        

       
    }

}
   
   
