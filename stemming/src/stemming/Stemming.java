/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stemming;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author extra
 */
public class Stemming {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        
        
        
     
    {
         
         BufferedReader br;
          br = new BufferedReader(new FileReader("energy.txt"));
          StringBuilder sb = new StringBuilder();
      
       try {         
        String line = br.readLine();
   

         while (line != null) {
         sb.append(line);
        sb.append(System.lineSeparator());
              
        line = br.readLine();
        
         }
           String everything = sb.toString(); 
         everything=  everything.replaceAll("[^\\u0009\\u000a\\u000d\\u0020-\\uD7FF\\uE000-\\uFFFD]", "");
          everything=  everything.replaceAll("[\\uD83D\\uFFFD\\uFE0F\\u203C\\u3010\\u3011\\u300A\\u166D\\u200C\\u202A\\u202C\\u2049\\u20E3\\u300B\\u300C\\u3030\\u065F\\u0099\\u0F3A\\u0F3B\\uF610\\uFFFC]", "");

         //  StanfordLemmatizer sl=new StanfordLemmatizer();
           //List<String>out=sl.lemmatize(everything);
            //System.out.println(out);
             ArrayList <String> words= new ArrayList <String>();
             ArrayList <String> out= new ArrayList <String>();
             
             //Porter p=new Porter();
             for (String _word : everything.split(" ")) {
              words.add(_word.toUpperCase());
             }
            /* StemmerSnow sss=new StemmerSnow();
             System.out.println( sss.stem(words));*/
             
              

       

} finally {
    br.close();
}

        
    }
        // TODO code application logic here
    }
    
}
