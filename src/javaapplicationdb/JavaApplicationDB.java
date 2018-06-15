package javaapplicationdb;

import java.util.ArrayList;

/*
------------------------------------------------------
    Schema code generator
------------------------------------------------------
create schema contactapp;

use contactapp;

create table contactapp.contact (
    `pk_id_contact` int auto_increment,
    `name` varchar (50),
    `lastname` varchar (50),
    `address` varchar(100),
    primary key (`pk_id_contact`)
);

create table contactapp.phone (
    `pk_id_phone` int auto_increment,
    `number` int (15),
    `fk_id_contact` int not null,
    primary key (`pk_id_phone`),
    foreign key (fk_id_contact) references contactapp.contact(pk_id_contact) on update cascade
);

------------------------------------------------------
*/

/**
 *
 * @author Avk
 */
public class JavaApplicationDB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Manager manager=Manager.getInstance();
        
        // Insert Contact
        Contact c=new Contact();
        c.setName("Juan");
        c.setLastName("Rodriguez");
        c.setAddress("España 22");
        manager.insert(c);
        
        // Add phone
        Phone p=new Phone();
        p.setNumber(123456);
        c.addPhone(p);
        
        // Update Contact
        c.setName("Pedro");
        c.setLastName("Peralta");
        manager.update(c);
        
        // Delete Contact
        manager.delete(c);

        // List Contact
        System.out.println();
        System.out.println("Contact list in DB: ");
        ArrayList<Contact> contacts=manager.select(new Contact());
        contacts.forEach((cont) -> {
            System.out.println(cont);
        });
    }    
}
