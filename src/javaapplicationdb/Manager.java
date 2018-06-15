package javaapplicationdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Avk
 */
public class Manager {
    private static final Manager managerInstance=new Manager();
    private Connection conn=null;
    
    public static Manager getInstance(){
        return managerInstance;
    }
    
    private Manager (){
        try {
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/contactapp?autoReconnect=true&useSSL=false&serverTimezone=UTC", "root", "");
            System.out.println("Connected");            
        } catch (SQLException e){
            conn=null;
            System.err.println(e);
        }
    }
    
    public void insert(Contact c){
        try {
            // add contact
            Statement stm=(Statement) conn.createStatement();
            stm.execute("INSERT INTO contact (name, lastname, address) VALUES ('"+c.getName()+"','"+c.getLastName()+"','"+c.getAddress()+"');",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stm.getGeneratedKeys();

            if (rs.next()) {
             c.setId(rs.getInt(1));
             System.out.println("insert: " + c); 
            }
            stm.close();
            
            // add phones of contact
            Iterator<Phone> phones=c.getPhones();
            while (phones.hasNext()) {
                insert(phones.next(), c.getId());
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
    public void update(Contact c) {
        try {
            Statement stm=(Statement) conn.createStatement();
            stm.execute("UPDATE contact SET name='"+c.getName()+"', lastname='"+c.getLastName()+"', address='"+c.getAddress()+"' WHERE pk_id_contact="+c.getId()+";");
            stm.close();
            System.out.println("update: "+c);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
    public Contact selectOne(int id_contact) {
        Contact c=new Contact();
        c.setId(id_contact);
        ArrayList<Contact> res=select(c);
        if (res.size()>0) return res.get(0);
        return null;
    }

    public ArrayList<Contact> select(Contact c) {
        ArrayList<Contact> res=new ArrayList<Contact>();
        
        String query="SELECT pk_id_contact, name, lastname, address FROM contact WHERE ";
        if (c.getId()>=0) {
            query+=" pk_id_contact="+c.getId()+" ";
        } else query+=" 1=1 ";
        
        if (c.getName().trim().length()>0) {
            query+=" and name LIKE '%"+c.getName()+"%' ";
        } else query+=" and 1=1 ";
        
        if (c.getLastName().trim().length()>0) {
            query+=" and lastname LIKE '%"+c.getLastName()+"%' ";
        } else query+=" and 1=1 ";
        
        if (c.getAddress().trim().length()>0) {
            query+=" and address LIKE '%"+c.getAddress()+"%' ";
        } else query+=" and 1=1 ";
        query+=" ; ";

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Contact aux=new Contact();
                aux.setId(rs.getInt("pk_id_contact"));
                aux.setAddress(rs.getString("name"));
                aux.setLastName(rs.getString("lastname"));
                aux.setAddress(rs.getString("address"));
                // add phones
                aux.addPhones(select(aux.getId()));
                res.add(aux);
            }        
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return res;
    }
    
    public void delete(Contact c) {
        try {
            // delete phones of contact
            Iterator<Phone> phones=c.getPhones();
            while (phones.hasNext()) {
                delete(phones.next());
            }
            
            // delete contact
            Statement stm=(Statement) conn.createStatement();
            stm.execute("DELETE FROM contact WHERE pk_id_contact="+c.getId()+";");
            stm.close();
            System.out.println("delete: "+c);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
    public void insert(Phone p, int id_contact){
        try {
            Statement stm=(Statement) conn.createStatement();
            stm.execute("INSERT INTO phone (number, fk_id_contact) VALUES ("+p.getNumber()+","+id_contact+");",Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stm.getGeneratedKeys();

            if (rs.next()) {
             p.setId(rs.getInt(1));
             System.out.println("insert: " + p + ", id_contact: "+id_contact); 
            }
            stm.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
    public void update(Phone p) {
        try {
            Statement stm=(Statement) conn.createStatement();
            stm.execute("UPDATE phone SET number="+p.getNumber()+" WHERE pk_id_phone="+p.getId()+";");
            stm.close();
            System.out.println("update: "+p);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
    
    public ArrayList<Phone> select(Phone p) {
        ArrayList<Phone> res=new ArrayList<Phone>();
        
        String query="SELECT pk_id_phone, number FROM phone WHERE ";
        if (p.getId()>=0) {
            query+=" pk_id_phone="+p.getId()+" ";
        } else query+=" 1=1 ";
        
        if (p.getNumber()>=0) {
            query+=" and number="+p.getNumber()+" ";
        } else query+=" and 1=1 ";
        
        query+=" ; ";
        
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Phone aux=new Phone();
                aux.setId(rs.getInt("pk_id_phone"));
                aux.setNumber(rs.getInt("number"));
                res.add(aux);
            }        
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return res;
    }
    
    public ArrayList<Phone> select(int id_contact) {
        ArrayList<Phone> res=new ArrayList<Phone>();
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT pk_id_phone, number FROM phone WHERE fk_id_contact="+id_contact+";");
            while (rs.next()) {
                Phone aux=new Phone();
                aux.setId(rs.getInt("pk_id_phone"));
                aux.setNumber(rs.getInt("number"));
                res.add(aux);
            }        
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return res;
    }
    
    public void delete(Phone p) {
        try {
            Statement stm=(Statement) conn.createStatement();
            stm.execute("DELETE FROM phone WHERE pk_id_phone="+p.getId()+";");
            stm.close();
            System.out.println("delete: "+p);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }    
}
