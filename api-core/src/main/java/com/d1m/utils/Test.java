package com.d1m.utils;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.*;

public class Test {

    static int lport;
    static String rhost;
    static int rport;
    public static void go(){
        String user = "d1m";
        String password = "85y:IoS%!t3E";
        String host = "182.254.208.25";
        int port = 22;
        try
        {
            JSch jsch = new JSch();
            Session session = jsch.getSession(user, host, port);
            lport = 3307;
            rhost = "192.168.1.17";
            rport = 3306;
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            System.out.println("Establishing Connection...");
            session.connect();
            int assinged_port=session.setPortForwardingL(lport, rhost, rport);
            System.out.println("localhost:"+assinged_port+" -> "+rhost+":"+rport);
        }
        catch(Exception e){System.err.print(e);}
    }
    public static void main(String[] args) {
        try{
            go();
        } catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("An example for updating a Row from Mysql Database!");
        Connection con = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:" + lport + "/";//" + rhost +";
        String db = "lv-vvv-php";
        String dbUser = "admin_readonly";
        String dbPasswd = "eqghtqFgnfxX160I";
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(url+db, dbUser, dbPasswd);
            try{
                Statement st = con.createStatement();
                String sql = "SELECT name FROM admin_users WHERE id = 1" ;

                ResultSet result = st.executeQuery(sql);
                while(result.next()){
                    System.out.println(result.getString("name") + " ");
                }
            }
            catch (SQLException s){
                System.out.println("SQL statement is not executed!");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
