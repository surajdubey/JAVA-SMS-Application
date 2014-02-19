
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author suraj
 */
public class DBConnect {
    
    private static Connection conn = null;
    private static Statement stmt= null;
    private static ResultSet rs=null;
    private static PreparedStatement prepare = null;
    
    private static String query = "";
    
    static String username = "root";
    static String password = "";
    
    static String admin_table_name = "admin";
    static String user_table_name = "users";
    
    WelcomePage w = new WelcomePage();
    
    public DBConnect()
    {
        try{
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/sms" , "root" , "");
         
        stmt = conn.createStatement();
        
           // System.out.println("Connection");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public boolean logIn() 
    {
        boolean proceed = false; 
        try
        {
            query = "SELECT * FROM admin WHERE username = '"+WelcomePage.getUsername()+"' AND PASSWORD = '"+WelcomePage.getPassword()+"'";
            
           prepare = conn.prepareStatement(query);
            rs = prepare.executeQuery(query);
            
            if(rs.next())
            {
                proceed = true;
            }

        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        
        return proceed;
    }
    
    public static void addUser(int id , String name , String g_name , int standard , int contact_number)
    {
        try {
            query = "INSERT INTO "+user_table_name+" VALUES(?,?,?,?,?)";
            prepare = conn.prepareStatement(query);
            
            prepare.setInt(1, id);
            prepare.setString(2, name);
            prepare.setString(3, g_name);
            prepare.setInt(4, standard);
            prepare.setInt(5, contact_number);
            
            prepare.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            //Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    static void removeUser(int id)
    {
        try {
            query = "DELETE FROM "+user_table_name+" WHERE id = ?";
            prepare = conn.prepareStatement(query);
            
            prepare.setInt(1, id);
            
            prepare.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
            
            //Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
    static void changePassword(String password)
    {
        try{
            query = "update  "+admin_table_name+" set password = '"+password+"' WHERE username = '"+WelcomePage.getUsername()+"'";
            prepare = conn.prepareStatement(query);
            
            prepare.executeUpdate();
        }
        
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
}
