package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtomappers.PersonDTO;
import entities.Person;
import entities.Phone;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory(
                "pu",
                "jdbc:mysql://localhost:3307/CA2",
                "dev",
                "ax2",
                EMF_Creator.Strategy.CREATE);
    private static final PersonFacade FACADE =  PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
            
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @Path("/phone/{phone}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Person getPersonByPhone(@PathParam("phone") String phone) {
        Person p = FACADE.getPersonByPhone(phone);
        return p;  //Done manually so no need for a DTO
    }
    
    // ADD & DELETE & EDIT
    //-------------------------------------------------------------------
    
    //@Path("/addPerson/{fName}/{lName}/{adresse}/{hobby}/{phone}")
    @Path("addPerson")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Response addNewPerson(@FormParam("firstname") String firstName, @FormParam("lastname") String lastName, @FormParam("adresse") String adresse, @FormParam("hobby") String hobby, @FormParam("telefon") String telefon) {
        Person p = FACADE.addPerson(firstName, lastName, adresse, hobby, telefon);
        //VIS TILFØJET PERSON
        return Response.ok("Person created with name: " + firstName).build();
    }
   
    
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonCount() {
        long count = FACADE.getPersonCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    /*
    @Path("SearchByPhone{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByPhone(@PathParam("id") String id){
        Person person = FACADE.getPersonByPhone(id);
        return GSON.toJson(new PersonDTO(person));
    }
    */

    /*
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String addPerson(String person){
        PersonDTO p = GSON.fromJson(person, PersonDTO.class);
        Person pNew = FACADE.addPerson(p.getFirstName(), p.getLastName(), p.getAddress(), p.getHobby());
        return GSON.toJson(new PersonDTO(pNew));
    }
    */
 
}
