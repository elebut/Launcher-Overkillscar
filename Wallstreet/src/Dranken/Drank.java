/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dranken;

import java.util.Objects;

/**
 *
 * @author Ruben
 */
public class Drank implements Comparable<Drank>,Cloneable{
    private String naam;
    private double prijs;
    private String prijsStr,borgStr;
    private double borg;

    public Drank(String naam, double prijs, double borg) {
        this.naam = naam;
        this.prijs = prijs;
        prijsStr = String.valueOf(prijs);
        this.borg = borg;
        borgStr = String.valueOf(borg);
    }
    
    public Drank(String naam, double prijs) {
        this.naam = naam;
        this.prijs = prijs;
        prijsStr = String.valueOf(prijs);
        borg = 0;
        borgStr = String.valueOf(borg);
    }
    
    public boolean hasBorg(){
        return borg > 0.0;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
        prijsStr = String.valueOf(prijs);
        System.out.println("De nieuwe Prijs is:" + prijs);
    }

    public double getBorg() {
        return borg;
    }

    public void setBorg(double borg) {
        this.borg = borg;
        borgStr = String.valueOf(borg);
        System.out.println("De nieuwe Borg is:" + borg);
    }

    public String getPrijsStr() {
        return prijsStr;
    }

    public String getBorgStr() {
        return borgStr;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.naam);
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.prijs) ^ (Double.doubleToLongBits(this.prijs) >>> 32));
        hash = 17 * hash + (int) (Double.doubleToLongBits(this.borg) ^ (Double.doubleToLongBits(this.borg) >>> 32));
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
        final Drank other = (Drank) obj;
        if (Double.doubleToLongBits(this.prijs) != Double.doubleToLongBits(other.prijs)) {
            return false;
        }
        if (Double.doubleToLongBits(this.borg) != Double.doubleToLongBits(other.borg)) {
            return false;
        }
        if (!Objects.equals(this.naam, other.naam)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Drank o) {
        return naam.compareTo(o.getNaam());
    }

    @Override
    public String toString() {
        return "Drank{" + "naam=" + naam + ", prijs=" + prijs + ", borg=" + borg + '}';
    }
   
    @Override
    public Drank clone(){
       try{ 
           return (Drank) super.clone();
       }
       catch(Exception e){
           return null;           
       }
    }
    
}
