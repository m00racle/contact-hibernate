package com.teamtreehouse.contactmgr.model;

import javax.persistence.*;

//ENTRY 4
/**Entry 4: Creating model of Contact
 * 1. Since this is a model we need to group it into new package called model
 * 2. prepare for some annotations usage here and some confusing notes
 * 3. Since Java Persistence API (JPA) will call the constructor when instantiate
 * Contact object we need to ensure one is present. Well actually since no
 * constructor available inside a class and superclass then Java compiled will
 * choose default constructor for us. But here we want to show how a default
 * constructor looks like
 *
 * ---> after this Entry 5 will be in Application.java: Building Design
 * Pattern*/

//-> map this Contact object to a single row in the table of database
@Entity
public class Contact {
    //Set this field as Id in nature (auto increment field):
    @Id
    //Set this field Generation strategy as Identity which is auto numbering:
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Each @Column will make each field as column name in the database table:
    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private Long phone;

    //Default constructor for JPA:
    public Contact(){}

    //ENTRY 7
    /** Entry 7: Builder Design Pattern
     * We continue our step by buiding the ConatctBuilder method to be used in
     * instantiating Contact Object inside Application.java
     * 1. Making the method declaration. Please NOTE: the argument passed in the
     *    declaration is only ContactBuilder this is like method overloading but
     *    passing a ContactBuilder object.
     * 2. At first all field declared inside the public Contact (ContactBuilder)
     *    method all highlighted with red since it was not yet declared. This is our
     *    next step.
     * GOTO --> Entry 8: in Contact.java below public Contact(ContactBuilder) method!
     * */

    //7-1 ContactBuilder method declaration:
    public Contact(ContactBuilder builder){
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phone = builder.phone;
    }

    //ENTRY 8
    /**ENTRY 8: Builder Design Pattern:
     *  here we will declare a class inside a class. Specifically public static class
     *  ConatctBuilder inside Contact.java class:
     *  1. Declaring the class name inside this class
     *  2. Insert all fields that will be taken as argument remember only firstName,
     *     lastName, email, phone. Since id is auto generated
     *     NOTE: all fields inside inner class ContactBuilder is different scope
     *           with the Contact.java class. Thus it is not auto recognized when
     *           declared
     * 3. Declaring constructor method for the ContactBuilder. NOTE: remember the
     *    constructor only has two arguments passed firstName and lastName
     * 4. Next is coding for the builder for email and phone but using with key
     *    amd both must return this variable since it wan method to build
     * NOTE: please check if the public Contact (ContactBuilder) method is still
     *       highlighted red. It should be fine now since we already specified
     *       what ContactBuilder object is through ContactBuilder inner class
     * 5. Lastly, we must define and return a new Contact object we build using
     *    build method to return this
     * NEXT ENTRY 9 GOTO --> Application.java to import some stuff to enable Contact*/

    //8-1: Declaring class
    public static class ContactBuilder{

        //8-2 : declaring fields
        private String firstName;
        private String lastName;
        private String email;
        private Long phone;

        //8-3: Constructor:
        public ContactBuilder(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
        }

        //8-4: ContactBuilder with:
        public ContactBuilder withEmail(String email){
            this.email = email;
            return this;
        }

        public ContactBuilder withPhone(Long phone){
            this.phone = phone;
            return this;
        }

        //8-5: build method:
        public Contact build(){
            return new Contact(this);
        }
    }

    //Insert toString for all entries:
    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }

    //Insert getter and seter for all declared fields:

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
