/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invertedindexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author extra
 */
public class InvertedIndexerJobOld {
     public String docID;
      public int Countofwords;
     
   
    TreeMap<String, List<Words>> index = new TreeMap<String, List<Words>>();
    List<String> files = new ArrayList<String>();
    TreeMap<String,Long>overallfreq=new TreeMap<String,Long>(); 
    TreeMap<String,Map<Integer,Integer>>WordCountInDoc=new TreeMap <String,Map<Integer,Integer>>();
    TreeMap<String,Integer>TF=new TreeMap <String,Integer>();
    TreeMap<String,Integer>IDF=new TreeMap<String,Integer>();
    List<String> headers=new ArrayList<String>();
    List<String> Titles=new ArrayList<String>();
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
    System.out.println(element.ownText());
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
         getInfo(doc);  
        extractTitles();
        extractHeaders();
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
                             
                                   for (int c = 0; c < newword.length(); c++)
                                       s.add(newword.charAt(c));
                                   
                                   s.stem();
                                   String stemmed;
                                   stemmed = s.toString();
                                   
                                   
                                   
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
                                        
				}
                                else {
                                // idx.
                                  Iterator<Words> iterator = idx.iterator();

                                    if (iterator.hasNext()) {
                                      Words foo = iterator.next();
                                      foo.pos.add(pos) ;
                                      foo.cnt++;
                                      
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
                  files.get(i).TF= ((1+Math.log10(cnt))/(Math.log10(Countofwords)));
		
	}
//     TF.put(key,  TF.get(key)/Countofwords);
   
           }
        
    }
  
 public void readFiles(String pathhhhhh,int id) throws FileNotFoundException, IOException
    {
        // this.docID=id;
         BufferedReader br;
         br = new BufferedReader(new FileReader(pathhhhhh));
         StringBuilder sb = new StringBuilder();
      
         try {         
           String line = br.readLine();
   

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
    
}
