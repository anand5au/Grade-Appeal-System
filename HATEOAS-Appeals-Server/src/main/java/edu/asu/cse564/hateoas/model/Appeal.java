/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.asu.cse564.hateoas.model;

/**
 *
 * @author Anand
 */
import java.lang.StringBuilder;

public class Appeal {
    
     StringBuilder AppealComments = new StringBuilder(500);
     AppealStatus appStatus ;
     
     public StringBuilder getComments()
     {
         return AppealComments;
     }
     public String getCom()
     {
         return AppealComments.toString();
     }
     
      public void setComments(StringBuilder com)
     {
         AppealComments.append(com);
     }
      public Appeal(StringBuilder app)
      {
           AppealComments.append(app);
           this.appStatus = AppealStatus.PENDING;
      }
       public Appeal(StringBuilder app,AppealStatus status)
      {
           AppealComments.append(app);
           this.appStatus = status;
      }
      public void setStatus(AppealStatus s)
      {
          appStatus = s;
      }
      public AppealStatus getStatus()
      {
          return appStatus;
      }
}
