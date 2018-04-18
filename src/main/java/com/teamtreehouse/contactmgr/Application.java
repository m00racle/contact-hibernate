package com.teamtreehouse.contactmgr;

import com.teamtreehouse.contactmgr.model.Contact;
import com.teamtreehouse.contactmgr.model.Contact.ContactBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

//ENTRY 3; ENTRY 5; ENTRY 9
/**Entry 3: Making main method on the Application.java
 * 1. Just make new package: com.teamtreehouse.contactmger
 * 2. make ne Java class named it Application
 * 3. inside application class declaration type psvm enter to create main meth
 *
 * Entry 5: Builder Design Pattern
 * 1. We will make a Builder design to avoid using Contact.ContactBuilder in the
 *    instantiation.
 * 2. First we build Hibernate SessionFactory object to hold re-usable reference to
 *    SessionFactory
 *    NOTE: static members are always loaded as soon as the class is needed by the JVM
 *    so before main method is instantiated the static members including SessionFactory
 *    will be created first!
 * 3. We build the buildSessionFactory that create StandardServiceRegistry which gives:
 *    - jdbc connectivity
 *    - hibernate configuration
 *    - imports initial database from a SQL file if we wanted to
 *    - building a SessionFactory
 * 4. Creates builder object that is used to create the standardServiceRegistry. Then
 *    calling the configure method one the builder will loads the hibernate config
 *    file from its default location (hibernate.cfg.xml)
 *    NOTE: if you want to specify different config file put String parameter inside
 *    the configure method!
 *    then the build() method finally will construct the standardServiceRegistry object
 *
 * 5. Building Hibernate SessionFactory(3:10): This is a build design pattern which
 *    we make metadata (data that provides info about other data) on the buildSession
 *    Factory. The build design pattern will be explained next entry 6 in
 *    BuilderDesign Pattern
 * NOTE: If you confused why does this metadata builder can build specifically a
 * Sessionfactory: IT IS BECAUSE IT'S BY DEFAULT REFERENCING hibernate.cfg,xml (in
 * configure()) AND CREATING METADATA FROM IT
 *6. The buildSessionFactory() in the return MetadataSources(registry) is not related to
 *   method name buildSessionFactory since it was a method name in the MetadataSources
 *   class after reading registry collected from hibernate.cfg.xml and recognized it want
 *   to buildSessionFactory. I knew this for a fact since the method name has a typo
 *   which proven it was not related to MetadataSources.
 *7. You cannot directly pass in MetadataSources to the field sessionFactory because we
 *   need to build the registry first!
 *
 *Entry 9: Builder Design Pattern:
 * 1. Modify the import list: import com.teamtreehouse.contactmgr.model.Contact
 *    add .ContactBuilder to eanble ContactBuilder object instantiation
 *    Oh BTW it seems we still need the
 *    import com.teamtreehouse.contactmgr.model.Contact after all please keep one
 *    while creating the other for ContactBuilder.
 * 2. This build() method pattern is similar in the Step 5-4. Meaning the step inside
 *    the inner class is also happening in the ServiceRegistry class
 * NEXT: ENTRY 10: Saving Data with Hibernate GOTO ---> build.gradle dependency{}*/

public class Application {
    //step 5-2:NOTE: at first buildSessionFactory is not present:
    private static final SessionFactory sessionFactory = builSessionFactory();

    //Step 5-3: building the buildSessionFactory method:
    private static SessionFactory builSessionFactory(){
        //step 5-4: configure() by default refer to hibernate.cfg.xml:
        final ServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        //step 5-5: building metadata based on hibernate.cfg.xml:
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    //ENTRY 6; ENTRY 16
    /**Entry 6: Builder Design Pattern
     * Here we will use a build() method as shortcut to instantiate object
     * in this case contact
     * 1. First we will show you how we normally create a contact object
     *    NOTE: we ommit the id argument as it was already set as auto increment
     *    in the setting in Contact.java class
     *    Since the constructor in Contact.java is still default it is not require
     *    any argument inputs, and if we input it the Long number will create error
     * 2. This is will be bothersome if the argument is in a large number. Thus
     *    it will be best to make builder design that will able to make arguments
     *    inputted in different lines of the instantiation code
     * 3. Now we want to make a builder design pattern so we will create first the
     *    builder instantiation code for Contact object
     *    NOTE: at first ContactBuilder will be highlighted red since this method is
     *          still unavailable in real life
     *          to solve this we need to go to --> Contact.java to Entry 7
     *
     *  ENTRY 16: Fetching Data with Hibernate:
     *  1.  we need a way to stream out (print) the query of POJO contact. Thus we need to call
     *      the private methods FetchAllContacts after we run save method
     *  2.  We call save method to save our entry as specified in the ContactBuilder
     *  3.  We print the Query or in this case we use stream to substitute for loop and println
     *  NEXT: ENTRY 17: Updating adn Deleting Data GOTO: below the fetchAllContacts()*/

    //step 3-3
    public static void main(String[] args) {

        //6-3: instantiating Contact Object using Builder pattern:
        Contact contact = new ContactBuilder("Moo", "Mee")
                .withEmail("moo@something.com")
                .withPhone(888776543L)
                .build();

        //16-2: save the built contact object:
        int id = save(contact);

        //16-3: stream the query fetched the POJO:
        fetchAllContacts().stream().forEach(System.out::println);
    }

    //ENTRY 11; ENTRY 13
    /** ENTRY 11: Saving Data with Hibernate
     * 1.   Now we will start enabling saving data with coding a method save(Contact)
     * 2.   It is static because this entity will be created in the initialized
     *      Application.java Class and does not wait to be initialized by new
     * 3.   We start by opening a session which we simply instantiate Session object and
     *      then called the sessionFactory object created earlier to open a session
     *      NOTE: we use Session object class from Hibernate package NOT H2 package!
     * 4.   We need to stated that we will begin the transaction using the session object
     *      we just instantiated earlier
     * 5.   The main activity here, we will save the contact POJO. We use session object
     *      method save(Contact) and pass in the POJO as argument
     * 6.   Next we need to commit the transaction as we see no problem here. Please NOTE:
     *      the transaction is already begun so we just need to get the active transaction
     *      to perform the commit method()
     * 7.   After all of the process commited we need to close the session
     * NEXT: ENTRY 12 ---> GOTO hibernate.cfg.xml bottom inned part of hibernate tags
     *
     * ENTRY 13: Fetching Data with Hibernate
     *  1.  Since we are using the Hibernate 5.2.0 or later we need to make some modifications
     *      on how to fetch multiple contacts at once.
     *  2.  form her on this is how to make a mtehod to Fetch all Contacts. Before that we need
     *      to understand one thing. That is we need to be able to designate Conatct POJO that
     *      we want to call. The best way to do this is by designating id as identification num
     *      is one variable that unique for one object
     *  3.  We generate ID when we save the POJO into a database. Thus we need to retrieve the ID
     *      which is automatically set by hibernate. We can do this by modifying the save(Contact)
     *      method to return int which is an id object
     *  4.  Then after that we generate the id by casting the save process inside the save(Contact)
     *      method and have that process produced int id object
     *  5.  Finally add return id in the last part of save(Contact) method to close it
     *  6.  Before we make the method to Fetch all Contacts we need to modify the
     *      hibernate.cfg.xml since we need to change its behavior that drop (delete) the
     *      database table each time we run the program. We want it to just update every time
     *      we run the program although since we only input the same POJO over and over again
     *      the content (for now) will be the same
     *  NEXT: ENTRY 14: Fetching Data with Hibernate -> GOTO: hibernate.cfg.xml hbm2ddl.auto*/

    //11-1: coding save(Contact) method; 13-3: modify the save(Contact) to return int id:

    public static int save(Contact contact){
        //11-3: Opening a session
        Session session = sessionFactory.openSession();

        //11-4: Begin the transaction:
        session.beginTransaction();

        //11-5: save the POJO; 13-4: casting the session.save to produce int id:
        int id = (int)session.save(contact);

        //11-6: commit the transaction
        session.getTransaction().commit();

        //11-7: close the session
        session.close();

        //13-5: return the int id:
        return id;
    }

    //ENTRY 15
    /** ENTRY 15: Fetching Data with Hibernate(2:00)
     *  1.  Now we will make the method that specifically made to Fetch Data (all data that is)
     *      from the database. The method declaration required it to return a List of Contacts POJO.
     *      NOTE: it is provate because we will call it from inside Application.java
     *  2.  Since we are using Hibernate 5.2.x we will using the latest code to Fetch data into a List
     *      many of the deprecated code still used in the course so maybe the explanation around this
     *      latest code is somewhat thin
     *  3.  we start with opening the session just like in previous save(Contact) method
     *  4.  Next we need to determine the criteria in which POJO we want to fetch thus we need to
     *      have CriteriaBuilder specified in the session object
     *  5.  Next step is to make a query, since the objective here is to Read (R part of CRUD) we need
     *      group them into a query that can be shown.
     *  6.  From that newly created query we will specify the criteria we initialized earlier
     *  7.  Then after specifying that criteria we can fetch all POJO that were in that criteria to
     *      form a List of that POJO
     *      NOTE: we will receive a warning about this since this method will return unparameterized List
     *          while in the method declaration it has a parameter Contact
     *  8.  Finally we close the session
     *  NEXT: ENTRY 16: Fetching Data with Hibernate GOTO : main() method to call save and stream the
     *      query.
     *  */

    //15-1: Fetching Data with Hibernate:
    private static List<Contact> fetchAllContacts(){
        //15-3: Opening a session
        Session session = sessionFactory.openSession();

        //15-4: determine the criteria:
        CriteriaBuilder builder = session.getCriteriaBuilder();

        //15-5: make query preparation for inserting POJO:
        CriteriaQuery<Contact> criteria = builder.createQuery(Contact.class);

        //15-6: populate the criteria query with specied POJO:
        criteria.from(Contact.class);

        //15-7: fetch all POJO from query to form a List:
        List<Contact> contacts = session.createQuery(criteria).getResultList();

        //15-8: close the session and return the List:
        session.close();
        return contacts;
    }
}
