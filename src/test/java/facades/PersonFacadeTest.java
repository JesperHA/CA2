package facades;

import entities.Person;
import utils.EMF_Creator;
import entities.Person;
import entities.Phone;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.Settings;
import utils.EMF_Creator.DbSelector;
import utils.EMF_Creator.Strategy;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    private static Person p1, p2, p3;
    

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/startcode_test",
                "dev",
                "ax2",
                EMF_Creator.Strategy.DROP_AND_CREATE);
        facade = PersonFacade.getPersonFacade(emf);
    }

    /*   **** HINT **** 
        A better way to handle configuration values, compared to the UNUSED example above, is to store those values
        ONE COMMON place accessible from anywhere.
        The file config.properties and the corresponding helper class utils.Settings is added just to do that. 
        See below for how to use these files. This is our RECOMENDED strategy
     */
    //@BeforeAll
    public static void setUpClassV2() {
       emf = EMF_Creator.createEntityManagerFactory(DbSelector.TEST,Strategy.DROP_AND_CREATE);
       facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        p1 = new Person("Jønke", "Jensen", "Vestergade 11", "Skydning");
        p2 = new Person("Lamseben", "Lammesen", "Gulerodsgade 1", "Bueskydning");
        p3 = new Person("Makrellen", "Hønningsen", "Østergade 6", "Skydning");
        
        Phone phone = new Phone("45874125");
        p1.addPhone(phone);
        
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Person.deleteAllRows").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testAllFacadeMethod() {
        assertEquals(3, facade.getPersonCount(), "Expects three rows in the database");
    }
    
   /* @Test
    public void testGetPerson(){
        Person person = facade.getPerson(p1.getId());
        assertEquals("Jønke", person.getFirstName(), "Expects to find Jønke");
    }*/

    @Test
    public void testAddPerson(){
<<<<<<< HEAD
        Person p = facade.addPerson("Jon", "Snow", "Storegade 10", "Øl","23124232");
=======
        Person p = facade.addPerson("Jon", "Snow", "Storegade 10", "Øl", "23541278");
>>>>>>> 3fa92d0429fe152dd7cb26f1152fb20fb0a22796
        assertNotNull(p.getId());
        EntityManager em = emf.createEntityManager();
        try{
        List<Person> persons = em.createQuery("select p from Person p").getResultList();
        assertEquals(4, persons.size(), "Expects 4 persons in the DB");
        } finally{
            em.close();
        }
    }
    
    @Test
    public void testDeletePerson(){
        long p1Id = p1.getId();
        long p2Id = p2.getId();
        facade.deletePerson(p1.getId());        
        EntityManager em = emf.createEntityManager();
        try{
        List<Person> persons = em.createQuery("select p from Person p").getResultList();
        assertEquals(2, persons.size(), "Expects 2 persons in the DB");
        
        persons = em.createQuery("select p from Person p WHERE p.id = " + p1Id).getResultList();
        assertEquals(0, persons.size(), "Expects 0 persons in the DB");
        Person p = em.find(Person.class, p1Id);
        assertNull(p, "Expects that person is removed and p is null");
        
        p = em.find(Person.class, p2Id);
        assertNotNull(p, "Expects that person is not removed and p not null");
        } finally{
            em.close();
        }
    }
    
    @Test
    public void testEditPerson(){
        p3.setLastName("Henningsen");
        Person p1New = facade.editPerson(p3);
        assertEquals(p1New.getLastName(), p3.getLastName(), "Expects Lastname: Henningsen");
        assertNotEquals(p3.getLastName(), "Hønningsen", "Expects Lastname to NOT be Hønningsen");
    }
    
   /* @Test
    public void testGetPersonByPhone(){
        Person person = facade.getPersonByPhone(p1.getPhone());
        assertEquals("25478963", person.getPhone(), "Expects to find 25478963");
    }*/
}