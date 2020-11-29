
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Farhan
 */
public class DbUtility {
    
    private static DbUtility instance;
    
    private DbUtility(){
        
    }
    
    public static DbUtility getInstance(){
        if(instance ==null){
            instance = new DbUtility();
        }
        return instance;
    }
    
    public  Connection getConnection(){
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students","root", "farhan");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DbUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
        
    }
    
    public Student valStudent(Connection con,Student s){
        Student s1 =null;
        try {
            String id = s.getRegNo();
            String pass = s.getPass();
            Statement st = con.createStatement();
            String q = "Select * from student where RegNo = '"+id+"'";
            ResultSet rs = st.executeQuery(q);
            if(rs.next()){
                if(id.equals(rs.getString(2))&& pass.equals(rs.getString(3))){
                    s1 = new Student();
                    s1.setName(rs.getString(1));
                    s1.setRegNo(rs.getString(2));
                    s1.setPass(rs.getString(3));
                    s1.setDept(rs.getString(4));
                    s1.setSec(rs.getString(5));
                                    
            }
           }              
        } catch (SQLException ex) {
            Logger.getLogger(DbUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
        return s1;
    }
    
    public boolean registerStudent(Connection con, Student s){
    boolean bol = false;
        try {
            Statement stmt = con.createStatement();
            String q = "insert into student (Name,RegNo,Password,Department,Section)"+ "values('"+s.getName()+"','"+s.getRegNo()+"','"+s.getPass()+"','"+s.getDept()+"','"+s.getSec()+"')";
            int  status = stmt.executeUpdate(q);
            bol = true;
        } catch (SQLException ex) {
            Logger.getLogger(DbUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    return bol;
    }
    
    public boolean submitAttendance(Connection con, Student s){
        boolean bol = false;
        try {
            Statement st = con.createStatement();
            String q = "Insert into Attendance (regNo,date)"+"values('"+s.getRegNo()+"',"+"(Select curDate())"+")";
            int status = st.executeUpdate(q);
            bol = true;
        } catch (SQLException ex) {
            Logger.getLogger(DbUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bol;
    }
    
    public String checkAttendance(Connection con, Student s){
        String string = null;
        try {
            int x=100;
            Statement st = con.createStatement();
            String q = "Select count(date) from Attendance where regNo = "+"'"+s.getRegNo()+"'";
            ResultSet rs = st.executeQuery(q);
            while(rs.next()){
                string = "Your Attendance is: "+rs.getString(1) +"/ 100";
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DbUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return string;
    }
}
   /*
#Table Creation

create table student (
Name varchar(40) not null,
RegNo varchar(13) primary key,
Password varchar(15) not null,
Department varchar(5),
Section varchar(1)
)

create table Attendance(
regNo varchar(13) not null,
date Date,
Primary key(regNo,date)

*/
        
  
