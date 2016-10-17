/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.hateoas.repositories;

/**
 *
 * @author Anand
 */
import java.util.HashMap;
import java.util.Map.Entry;

import edu.asu.cse564.hateoas.model.Grade;
import edu.asu.cse564.hateoas.model.Identifier;
public class GradeRepository {
 
     private static final GradeRepository theRepository = new GradeRepository();
    private HashMap<String, Grade> backingStore = new HashMap<String, Grade>(); // Default implementation, not suitable for production!

    public static GradeRepository current() {
        return theRepository;
    }
    
    private GradeRepository(){}
    
    public Grade get(Identifier identifier) {
        return backingStore.get(identifier.toString());
     }
    
    public Grade take(Identifier identifier) {
        Grade grade = backingStore.get(identifier.toString());
        remove(identifier);
        return grade;
    }

    public Identifier store(Grade grade) {
        Identifier id = new Identifier();
        backingStore.put(id.toString(), grade);
        return id;
    }
    
    public void store(Identifier orderIdentifier, Grade grade) {
        backingStore.put(orderIdentifier.toString(), grade);
    }

    public boolean has(Identifier identifier) {
        boolean result =  backingStore.containsKey(identifier.toString());
        return result;
    }

    public void remove(Identifier identifier) {
        backingStore.remove(identifier.toString());
    }
    
    public boolean gradePlaced(Identifier identifier) {
        return GradeRepository.current().has(identifier);
    }
    
    public boolean gradeNotPlaced(Identifier identifier) {
        return !gradePlaced(identifier);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entry<String, Grade> entry : backingStore.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\t:\t");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized void clear() {
        backingStore = new HashMap<String, Grade>();
    }

    public int size() {
        return backingStore.size();
    }
    
}