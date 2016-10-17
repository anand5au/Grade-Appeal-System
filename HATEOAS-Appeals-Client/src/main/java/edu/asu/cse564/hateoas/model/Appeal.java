
 //* To change this license header, choose License Headers in Project Properties.
 //* To change this template file, choose Tools | Templates
 //* and open the template in the editor.
  

package edu.asu.cse564.hateoas.model;

import edu.asu.cse564.hateoas.representations.Representations;
import java.lang.StringBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
/**
 *
 * @author Anand
 */
@XmlRootElement(name="appeal", namespace = Representations.RESTBUCKS_NAMESPACE)
public class Appeal {
      @XmlElement(name = "AppealComments", namespace = Representations.RESTBUCKS_NAMESPACE)
    private StringBuilder AppealComments = new StringBuilder();
    
    @XmlElement(name = "status", namespace = Representations.RESTBUCKS_NAMESPACE)
    private AppealStatus appStatus = AppealStatus.PENDING;
    
         
     public StringBuilder getComments()
     {
         return AppealComments;
     }
     Appeal()
     { }
      public void setComments(StringBuilder com)
     {
         AppealComments.append(com);
     }
      public Appeal(StringBuilder app)
      {
          this.AppealComments.append(app);
           this.appStatus = AppealStatus.PENDING;
      }
      public Appeal(AppealStatus status)
    {
          //this.AppealComments.append(app);
       
           this.appStatus = status;
      }
      public void setStatus(AppealStatus s)
      {
          this.appStatus= s;
      }
      public AppealStatus getStatus()
      {
          return appStatus;
      }
}
