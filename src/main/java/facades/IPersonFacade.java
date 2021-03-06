/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Person;
import java.util.List;

/**
 *
 * @author jelle
 */
public interface IPersonFacade {
  public Person addPerson(String fName, String lName, String address, String hobby, String phone);
  public Person deletePerson(long id);  
  public Person editPerson(Person p); 
  
  public Person getPersonByPhone(String phone);
  public Person getPersonsByHobby(String hobby);
  public Person getPersonsByCity(String address);
  public Person countPersonsByHobby(String hobby);
  public List<String> allZipCodes();

    
}
