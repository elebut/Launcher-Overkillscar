/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Drankkaarten;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;



/**
 *
 * @author Ruben
 */
public class DataBeheer implements Serializable {
    private HashMap<String,HashSet<Vereniging>> verenigingen;
    private String[] types;
      
     public DataBeheer(){
         verenigingen = new HashMap<>();
         types = new String[]{"Chiro", "KSA", "KLJ", "SCOUTS", "ANDERE"};
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

    public HashSet<Vereniging> getVerenigingen(String type) {
        return verenigingen.get(type);
    }

    public HashMap<String, HashSet<Vereniging>> getVerenigingen() {
        return verenigingen;
    }

    public String[] getTypes() {
        return types;
    }
    
    public String getLijstString(){
        String s = "";
        for(String str:verenigingen.keySet()){
            s+= str +": \n";
            for(Vereniging v:verenigingen.get(str)){
                s+= v.getNaam() + " Aantal Drankkaarten: " + v.getDrankkaarten() + "\n";
            }
        }
        return s;
    }
    
    
     

}
