/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.hateoas.model;

import java.util.Random;

/**
 *
 * @author Anand
 */
public class Grade {
    
   // public int studentId;
    public char letterGrade;
    
    public Grade(char g)
    {
    //final String alphabet = "ABCDE";
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