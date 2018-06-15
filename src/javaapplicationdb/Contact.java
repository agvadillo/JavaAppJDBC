package javaapplicationdb;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Avk
 */
public class Contact {
    private int id=-1;
    private String name="";
    private String lastName="";
    private String address="";
    private ArrayList<Phone> phones;

    public Contact() {
        phones=new ArrayList<Phone>();
    }
        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public void addPhone(Phone p){
        if (id>=0 && p.getId()<0) Manager.getInstance().insert(p, id);
        phones.add(p);
    }

    public void addPhones(ArrayList<Phone> phs){
        for (Phone p:phs) {
            addPhone(p);
        }
    }

    public void deletePhone(Phone p){
        int position=phones.indexOf(p);
        if (position>=0) {
            phones.remove(position);
            if (p.getId()>=0) Manager.getInstance().delete(p);
        }
    }

    public Iterator<Phone> getPhones(){
        return phones.iterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contact other = (Contact) obj;
        return this.id == other.id;
    }
    
    @Override
    public String toString() {
        String r="Contact: id:"+id+", name:"+name+", lastName:"+lastName+", address:"+address+", phones:[";
        int count=0;
        for (Phone ph:phones) {
            if (count>0) r+=",";
            count++;
            r+=ph.getId();
        }
        r+="]";
        return r;
    }
}
