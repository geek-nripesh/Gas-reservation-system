package com.company;

import java.util.Scanner;
import java.sql.*;

public class Main extends login{

    public static void main(String[] args) {
	    // write your code here
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome");
        System.out.println("Select an option : ");
        System.out.println("1. New User ");
        System.out.println("2. Existing User ");
        System.out.println("3. Exit ");

        String username, password;
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/student","root","2000");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from stu");

            int choice = sc.nextInt();
            if(choice==1){
                System.out.print("Enter username : ");
                username = sc.next();
                System.out.print("Enter password : ");
                password = sc.next();

            }
            else if(choice==2){
                System.out.print("Enter username : ");
                username = sc.next();
                System.out.print("Enter password : ");
                password = sc.next();
                boolean userfound = false;
                while(rs.next()){
                    if(rs.getString(1).equals(username) && rs.getString(2).equals(password)){
                        System.out.println("Gas provider : ****");
                        System.out.println("Consumer number : ****");
                        System.out.println("Delivery Address : ****");
                        System.out.println("Agency Name : ****");
                        userfound = true;
                    }
                }
                if(userfound==false){
                    System.out.println("User not found");
                }
            }
            else if(choice==3){

            }
            else{
                System.out.println("Invalid option");
            }

            con.close();
            }catch(Exception e){
                System.out.println(e);
            }
    }
}
