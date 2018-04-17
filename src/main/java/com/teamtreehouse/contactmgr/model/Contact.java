package com.teamtreehouse.contactmgr.model;

import javax.persistence.*;

/**Step 4: Creating model of Contact
 * 1. Since this is a model we need to group it into new package called model
 * 2. prepare for some annotations usage here and some confusing notes
 * 3. Since Java Persistence API (JPA) will call the constructor when instantiate
 * Contact object we need to ensure one is present. Well actually since no
 * constructor available inside a class and superclass then Java compiled will
 * choose default constructor for us. But here we want to show how a default
 * constructor looks like*/

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
