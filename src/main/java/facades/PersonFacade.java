package facades;

import entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private PersonFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade PersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    //TODO Remove/Change this before use
    public long getRenameMeCount(){
        EntityManager em = emf.createEntityManager();
        try{
            long renameMeCount = (long)em.createQuery("SELECT COUNT(r) FROM RenameMe r").getSingleResult();
            return renameMeCount;
        }finally{  
            em.close();
        }
        
    }

    // TODO : Addresse feltet skal laves om så det et addresse object istedet for en streng
    @Override
    public Person addPerson(String fName, String lName, String phone, String address, String hobby) {
        EntityManager em = getEntityManager();
        Person person = new Person(fName, lName, phone, address, hobby);
        
        try{
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        }finally{
            em.close();
        }
            return person;
    }

    @Override
    public Person deletePerson(long id) {
      EntityManager em = getEntityManager();
       
       Person person = em.find(Person.class, id);
        try{
            em.getTransaction().begin();
            em.remove(person);
            em.getTransaction().commit();
            
        }finally{
            em.close();
        }
        return person;
    }

    @Override
    public Person editPerson(Person p) {
        EntityManager em = getEntityManager();
            Person person = em.find(Person.class, p.getId());
            person.setFirstName(p.getFirstName());
            person.setLastName(p.getLastName());
            person.setPhone(p.getPhone());
            person.setAddress(p.getAddress());
            person.setHobby(p.getHobby());
            person.setLastEdited();
            
        try{
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
            return person;
            
        }finally{  
            em.close();
        }
    }

    @Override
    public Person getPersonByPhone(String phone) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person getPersonsByHobby(String hobby) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person getPersonsByCity(String address) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person countPersonsByHobby(String hobby) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> allZipCodes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
