/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Drankkaarten;

import java.util.Objects;

/**
 *
 * @author Ruben
 */
public class Vereniging {
    private String naam;
    private String type;
    private int drankkaarten;
    
    
    public Vereniging(String naam, String type, int drankkaarten){
        this.naam= naam;
        this.type = type;
        this.drankkaarten = drankkaarten;
    }
    public Vereniging(String naam, String type){
        this.naam= naam;
        this.type = type;
        this.drankkaarten = 0;
    }

    
    public String getNaam(){
        return naam;
    }

    public String getType() {
        return type;
    }

    public int getDrankkaarten() {
        return drankkaarten;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.naam);
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + this.drankkaarten;
        return hash;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vereniging other = (Vereniging) obj;
        if (this.type != other.type) {
            return false;
        }
        if (this.drankkaarten != other.drankkaarten) {
            return false;
        }
        if (!Objects.equals(this.naam, other.naam)) {
            return false;
        }
        return true;
    }
  
    
}
