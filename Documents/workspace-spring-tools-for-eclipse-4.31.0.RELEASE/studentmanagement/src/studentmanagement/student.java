package studentmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class student {
	// DB Configuration 
		 private static final String Driver = "com.mysql.cj.jdbc.Driver";
		 private static final String url = "jdbc:mysql://localhost:3306/studentmanagement";
		 private static final String username = "root";
		 private static final String password = "Vasavi@2005";
		 
		 // SINGLE GLOBAL SCANNER (IMPORTANT FIX)
		 static Scanner src = new Scanner(System.in);
		 
		 public static void main(String[] args) {
			 int choose;

		        while (true) {
		            studentMenu();

		            System.out.print("Enter your choice: ");
		            choose = src.nextInt();

		            switch (choose) {
		                case 1:
		                    insert();
		                    break;
		                case 2:
		                    update();
		                    break;
		                case 3:
		                    delete();
		                    break;
		                case 4:
		                    getAll();
		                    break;
		                case 5:
		                    findBy();
		                    break;
		                case 6:
		                    System.out.println("Exiting program...");
		                    System.exit(0);
		                    break;
		                default:
		                    System.out.println("Invalid selection!!");
		            }
		        }
		 }
		 
		 // DATABASE CONNECTION METHOD (Reusable)
		    private static Connection getConnection() throws Exception {
		        Class.forName(Driver);
		        return DriverManager.getConnection(url, username, password);
		    }

		 // INSERT STUDENT
		    private static void insert() {
		        try (Connection conn = getConnection()) {

		            String sql = "INSERT INTO student(roll_no,stu_name,stu_email,marks) VALUES (?,?,?,?)";
		            PreparedStatement pmst = conn.prepareStatement(sql);

		            System.out.print("Enter Roll Number: ");
		            pmst.setString(1, src.next());

		            System.out.print("Enter Student Name: ");
		            pmst.setString(2, src.next());

		            System.out.print("Enter Student Email: ");
		            pmst.setString(3, src.next());

		            System.out.print("Enter Marks: ");
		            pmst.setInt(4, src.nextInt());

		            int i = pmst.executeUpdate();

		            if (i > 0)
		                System.out.println("Student inserted successfully");
		            else
		                System.out.println("Insertion failed");

		        } catch (Exception e) {
		            System.out.println("Error inserting student");
		            e.printStackTrace();
		        }
		    }

		    // UPDATE STUDENT
		    private static void update() {
		        try (Connection conn = getConnection()) {

		            String sql = "UPDATE student SET stu_name=?, stu_email=?, marks=? WHERE roll_no=?";
		            PreparedStatement pmst = conn.prepareStatement(sql);

		            System.out.print("Enter New Name: ");
		            pmst.setString(1, src.next());

		            System.out.print("Enter New Email: ");
		            pmst.setString(2, src.next());

		            System.out.print("Enter New Marks: ");
		            pmst.setInt(3, src.nextInt());

		            System.out.print("Enter Roll Number to Update: ");
		            pmst.setString(4, src.next());

		            int i = pmst.executeUpdate();

		            if (i > 0)
		                System.out.println("Student updated successfully");
		            else
		                System.out.println("Update failed (check roll number)");

		        } catch (Exception e) {
		            System.out.println("Error updating student");
		            e.printStackTrace();
		        }
		    }

		    // DELETE STUDENT
		    private static void delete() {
		        try (Connection conn = getConnection()) {

		            String sql = "DELETE FROM student WHERE roll_no=?";
		            PreparedStatement pmst = conn.prepareStatement(sql);

		            System.out.print("Enter Roll Number to Delete: ");
		            pmst.setString(1, src.next());

		            int i = pmst.executeUpdate();

		            if (i > 0)
		                System.out.println("Student deleted successfully");
		            else
		                System.out.println("Deletion failed (check roll number)");

		        } catch (Exception e) {
		            System.out.println("Error deleting student");
		            e.printStackTrace();
		        }
		    }

		    // VIEW ALL STUDENTS
		    private static void getAll() {
		        try (Connection conn = getConnection()) {

		            String sql = "SELECT * FROM student";
		            PreparedStatement pmst = conn.prepareStatement(sql);
		            ResultSet rs = pmst.executeQuery();

		            System.out.println("\n===== STUDENT RECORDS =====");

		            boolean found = false;

		            while (rs.next()) {
		                found = true;
		                System.out.println("ID: " + rs.getInt("id"));
		                System.out.println("Roll No: " + rs.getString("roll_no"));
		                System.out.println("Name: " + rs.getString("stu_name"));
		                System.out.println("Email: " + rs.getString("stu_email"));
		                System.out.println("Marks: " + rs.getInt("marks"));
		                System.out.println("--------------------------");
		            }

		            if (!found)
		                System.out.println("No records found");

		        } catch (Exception e) {
		            System.out.println("Error retrieving students");
		            e.printStackTrace();
		        }
		    }

		    // FIND STUDENT BY ROLL NUMBER
		    private static void findBy() {
		        try (Connection conn = getConnection()) {

		            String sql = "SELECT * FROM student WHERE roll_no=?";
		            PreparedStatement pmst = conn.prepareStatement(sql);

		            System.out.print("Enter Roll Number: ");
		            pmst.setString(1, src.next());

		            ResultSet rs = pmst.executeQuery();

		            if (rs.next()) {
		                System.out.println("\n===== STUDENT DETAILS =====");
		                System.out.println("ID: " + rs.getInt("id"));
		                System.out.println("Roll No: " + rs.getString("roll_no"));
		                System.out.println("Name: " + rs.getString("stu_name"));
		                System.out.println("Email: " + rs.getString("stu_email"));
		                System.out.println("Marks: " + rs.getInt("marks"));
		            } else {
		                System.out.println("Student not found");
		            }

		        } catch (Exception e) {
		            System.out.println("Error finding student");
		            e.printStackTrace();
		        }
		    }

		    // MENU DISPLAY
		    private static void studentMenu() {
		        System.out.println("\n===== Student Management System =====");
		        System.out.println("1. Insert Student");
		        System.out.println("2. Update Student");
		        System.out.println("3. Delete Student");
		        System.out.println("4. View All Students");
		        System.out.println("5. Find Student by Roll No");
		        System.out.println("6. Exit");
		    }
}