/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Drankkaarten;

import java.util.HashMap;
import java.util.HashSet;



/**
 *
 * @author Ruben
 */
public class DataBeheer {
    private HashMap<Integer,HashSet<Vereniging>> verenigingen;
    
      
     public DataBeheer(){
         verenigingen = new HashMap<>();
     }
     public void voegToe(Vereniging v){
         if (verenigingen.containsKey(v.getType())){
         verenigingen.get(v.getType()).add(v);
         }
         else{
             HashSet<Vereniging> set = new HashSet<>();
             set.add(v);
             verenigingen.put(v.getType(),set);
         }
     }

    public HashSet<Vereniging> getVerenigingen(int type) {
        return verenigingen.get(type);
    }

    public HashMap<Integer, HashSet<Vereniging>> getVerenigingen() {
        return verenigingen;
    }
    
     

}
