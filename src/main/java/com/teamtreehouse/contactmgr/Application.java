package com.teamtreehouse.contactmgr;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

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
 * */

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
    //step 3-3
    public static void main(String[] args) {

    }
}
