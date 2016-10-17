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

import edu.asu.cse564.hateoas.model.Appeal;
import edu.asu.cse564.hateoas.model.Identifier;
public class AppealRepository {
 private static final AppealRepository theRepository = new AppealRepository();
    private HashMap<String, Appeal> backingStore = new HashMap<String, Appeal>(); // Default implementation, not suitable for production!

    public static AppealRepository current() {
        return theRepository;
    }
    
    private AppealRepository(){}
    
    public Appeal get(Identifier identifier) {
        return backingStore.get(identifier.toString());
     }
    
    public Appeal take(Identifier identifier) {
        Appeal appeal = backingStore.get(identifier.toString());
        remove(identifier);
        return appeal;
    }

    public Identifier store(Appeal appeal) {
        Identifier id = new Identifier();
        backingStore.put(id.toString(), appeal);
        return id;
    }
    
    public void store(Identifier orderIdentifier, Appeal appeal) {
        backingStore.put(orderIdentifier.toString(), appeal);
    }

    public boolean has(Identifier identifier) {
        boolean result =  backingStore.containsKey(identifier.toString());
        return result;
    }

    public void remove(Identifier identifier) {
        backingStore.remove(identifier.toString());
    }
    
    public boolean appealPlaced(Identifier identifier) {
        return AppealRepository.current().has(identifier);
    }
    
    public boolean appealNotPlaced(Identifier identifier) {
        return !appealPlaced(identifier);
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Entry<String, Appeal> entry : backingStore.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\t:\t");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

    public synchronized void clear() {
        backingStore = new HashMap<String, Appeal>();
    }

    public int size() {
        return backingStore.size();
    }
    
}
