package com.company;

import java.util.Scanner;
import java.sql.*;

public class Main{
    public static void main(String[] args) {
	    // write your code here
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome");
        System.out.println("Select an option : ");
        System.out.println("1. New User ");
        System.out.println("2. Existing User ");
        System.out.println("3. Exit ");

        String username, password, phno, gasProvider, deliveryAddress, AgencyName;
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/gas","root","2000");
            Statement stmt=con.createStatement();

            int choice = sc.nextInt();
            if(choice==1){
                System.out.print("Enter username : ");
                username = sc.next();
                System.out.print("Enter password : ");
                password = sc.next();

                System.out.println("Enter phone number : ");
                phno = sc.next();
                System.out.println("Enter delivery address : ");
                deliveryAddress = sc.nextLine();

                String query = " insert into users (username,passwrd,phno,address,gasProvider,agencyName)"
                        + " values (?, ?, ?, ?, ?, ?)";

                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString (1, username);
                preparedStmt.setString (2, password);
                preparedStmt.setString (3, phno);
                preparedStmt.setString (4, deliveryAddress);
                preparedStmt.setString (5, "Indane");
                preparedStmt.setString (6, "Blue Flames");

                preparedStmt.execute();
//                String insertQuery = "INSERT INTO USERS(username,passwrd,phno,address,gasProvider,agencyName) VALUES('" +username
//                        +','+password+','+phno+','+deliveryAddress+"','Indane','Blue Flames');";
//                stmt.executeUpdate(insertQuery);

            }
            else if(choice==2){
                System.out.print("Enter username : ");
                username = sc.next();
                System.out.print("Enter password : ");
                password = sc.next();

                boolean userfound = false;

                ResultSet rs=stmt.executeQuery("select * from users");
                while(rs.next()){
                    if(rs.getString(2).equals(username) && rs.getString(3).equals(password)){
                        System.out.println("Gas provider : " + rs.getString(6));
                        System.out.println("Consumer number : " + rs.getString(1));
                        System.out.println("Delivery Address : " + rs.getString(5));
                        System.out.println("Agency Name : " + rs.getString(7));
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
