package com.company;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import java.sql.*;

public class Main{
    public static void main(String[] args) {
	    // write your code here
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome");

        String username, password, phno, gasProvider, deliveryAddress, AgencyName;
        try{
            while(true){
                Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/gas","root","2000");
                Statement stmt=con.createStatement();

                System.out.println("Select an option : ");
                System.out.println("1. New User ");
                System.out.println("2. Existing User ");
                System.out.println("3. Exit ");

                int choice = sc.nextInt();

                if(choice==1){

                    System.out.print("Enter username : ");
                    username = sc.next();
                    System.out.print("Enter password : ");
                    password = sc.next();

                    System.out.println("Enter phone number : ");
                    phno = sc.next();
                    System.out.println("Enter delivery address : ");
                    sc.next();
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
                            userfound = true;
                            break;
                        }
                    }
                    if(userfound==false){
                        System.out.println("User not found");
                    }
                    else{
                        while(true){
                            System.out.println("Hello "+ username + "Please select an option");
                            System.out.println("1. Book cylinder");
                            System.out.println("2. Show Details");
                            System.out.println("3. Report Issue");
                            System.out.println("4. Change password");
                            System.out.println("5. Exit");

                            int userchoice = sc.nextInt();
                            if(userchoice==1){
                                String query = "update users set qty = (qty+1) where username = ?";
                                PreparedStatement preparedStmt = con.prepareStatement(query);
                                preparedStmt.setString(1, username);
                                preparedStmt.executeUpdate();
                            }
                            else if(userchoice==2){
                                System.out.println("User Name : " + rs.getString(2));
                                System.out.println("Gas provider : " + rs.getString(6));
                                System.out.println("Consumer number : " + rs.getString(1));
                                System.out.println("Delivery Address : " + rs.getString(5));
                                System.out.println("Agency Name : " + rs.getString(7));
                            }
                            else if(userchoice==3){
                                String query = " insert into issues (consumerNumber,issueType,issueDetail,issueDate)"
                                        + " values (?, ?, ?, ?)";
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date date = new Date();
                                String dt = formatter.format(date);

                                System.out.println("Enter Issue Category : ");
                                sc.next();
                                String icategory = sc.nextLine();
                                System.out.println("Enter Issue Description in 100 characters : ");
                                String idesc = sc.nextLine();
//                                System.out.println(formatter.format(date));

                                PreparedStatement preparedStmt = con.prepareStatement(query);
                                preparedStmt.setString (1, rs.getString(1));
                                preparedStmt.setString (2, icategory);
                                preparedStmt.setString (3, idesc);
                                preparedStmt.setString (4, dt);

                                preparedStmt.execute();

                            }
                            else if(userchoice==4){
                                System.out.print("Enter old password : ");
                                if(password.equals(sc.next())){
                                    System.out.println("Enter new password : ");
                                    String newPassword = sc.next();
                                    String query = "update users set passwrd = ? where username = ?";
                                    PreparedStatement preparedStmt = con.prepareStatement(query);
                                    preparedStmt.setString(1, newPassword);
                                    preparedStmt.setString(2, username);
                                    preparedStmt.executeUpdate();
                                }
                                else{
                                    System.out.println("Incorrect password!!! Try again");
                                }
                            }
                            else if(userchoice==5){
                                break;
                            }
                            else{
                                System.out.println("Invalid choice!!! Try again");
                            }
                        }
                    }
                }
                else if(choice==3){
                    System.out.println("Thank You!!!");
                    break;
                }
                else{
                    System.out.println("Invalid option!!! Try again");
                }
                con.close();
            }
        }catch(Exception e){
                System.out.println(e);
            }
    }
}
