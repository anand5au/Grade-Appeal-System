/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package edu.asu.cse564.hateoas.model;

import edu.asu.cse564.hateoas.representations.Representations;
import java.util.Random;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anand
 */
@XmlRootElement(name="grade", namespace = Representations.RESTBUCKS_NAMESPACE)
public class Grade {
    
    //public int studentId;
    @XmlElement(name = "gradeLetter", namespace = Representations.RESTBUCKS_NAMESPACE)
    public char letterGrade;
    
    public Grade(){
        
    }
    
    public Grade(char g)
    {
   // final String alphabet = "ABCDE";
    //final int N = alphabet.length();
    //Random r = new Random();
    //letterGrade = alphabet.charAt(r.nextInt(N));
     this.letterGrade = g;
}
   
    public void setValue(char gradeLetter)
    {
        this.letterGrade=gradeLetter;
    }
    
   public char getValue()
    {
        return letterGrade;
    }
}