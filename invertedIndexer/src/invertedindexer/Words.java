/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invertedindexer;

import java.util.ArrayList;

/**
 *
 * @author extra
 */
 ////////////////////////class to keep track of number of occurrence in docucent, documrnt id ,tf//////////////////////////
      public class Words {
		public String ID;
                public ArrayList<Integer> pos = new ArrayList<Integer>();
		public int position;
                public int cnt=0;
                public double TF=0;
                public boolean Headerss=false;
                public boolean title=false;
                public String Stem;
                 
		public Words(String fileno, int position) {
			this.ID = fileno;
			 pos.add(position); 
                        cnt++;
                     
		}
                public String getID()
                {
                    return ID;
                }
                public int getposition()
                {
                    return position;
                }
                public int getCount()
                {
                    return cnt;
                }
                
                public void SetId(String id)
                {
                    this.ID=id;
                }
                public void SetPos(int positionn)
                {
                    pos.add(positionn);
                }
                public void SetHeader(boolean x)
                {
                    Headerss=x;
                }
                public void SetTitle(boolean x)
                {
                    title=x;
                }
                public void SetStem(String s)
                {
                    this.Stem=s;
                }
                public String GetStem()
                {
                    return Stem;
                    
                }
	}