package stemming;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/*
 *    IteratedLovinsStemmer.java
 *    Copyright (C) 2001 Eibe Frank
 *
 *    This program is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program; if not, write to the Free Software
 *    Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
public class IteratedLovinsStemmer extends LovinsStemmer {

  /**
   * Iterated stemming of the given word.
   * Word is converted to lower case.
   */
  public String stem(String str) {

    if (str.length() <= 2) {
      return str;
    }
    String stemmed = super.stem(str);
    while (!stemmed.equals(str)) {
      str = stemmed;
      stemmed = super.stem(stemmed);
    }
    return stemmed;
  }

  /**
   * Stems text coming into stdin and writes it to stdout.
   */
  public static void main(String[] ops) throws FileNotFoundException, IOException {

    IteratedLovinsStemmer ls = new IteratedLovinsStemmer();

    /*try {
      int num;
     StringBuffer wordBuffer = new StringBuffer();
      while ((num = System.in.read()) != -1) {
	char c = (char)num;
	if (((num >= (int)'A') && (num <= (int)'Z')) ||
	    ((num >= (int)'a') && (num <= (int)'z'))) {
	  wordBuffer.append(c);
	} else {
	  if (wordBuffer.length() > 0) {
	    System.out.print(ls.stem(wordBuffer.toString().
				     toLowerCase()));
	    wordBuffer = new StringBuffer();
	  }
	  System.out.print(c);
	}
      }
    } catch (Exception e) {
      e.printStackTrace();
    }*/
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

        
             for (String _word : everything.split(" ")) {
    System.out.print(ls.stem(_word.toLowerCase())+" ");
}
       

} finally {
    br.close();
}
  }
}
    

