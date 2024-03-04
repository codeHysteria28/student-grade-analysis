// Simple grade analysis program
// Created by: Branislav Buna / B00158771

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.Scanner;

// Main class
// - purpose of this class is to combine the rest of the functionality from Display and Student Classes

public class Main {
    // declare global scope variables
    static Scanner sc = new Scanner(System.in);
    static Student[] students;

    // Student class - blueprint
    private static class Student{
        // declared variables for name and grade
        int grade;
        String name;

        // constructor
        public Student(String studentName, int studentGrade) {
            this.name = studentName;
            this.grade = studentGrade;
        }
    }

    public static void main(String[] args){
        // initialize program
        initializeProgram();

        // initialize menu component
        consoleMenu();
    }

    // method to initialize program
    public static void initializeProgram(){
        int numberOfStudents;

        // prompt a lecturer to add number of results
        System.out.println("");
        System.out.println("|--------------------------------------------------|");
        System.out.println("|   WELCOME to TUDublin grading analysis program   |");
        System.out.println("|--------------------------------------------------|");
        System.out.println("");

        // save the input to variable and define students array length
        do {
            System.out.println("Enter the number of students, value must be between 3 - 26: ");
            while(!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 3 and 26");
                sc.next();
            }

            numberOfStudents = sc.nextInt();
        }while(numberOfStudents < 3 || numberOfStudents > 26);

        sc.nextLine(); // consume newline
        students = new Student[numberOfStudents];

        // create a loop to fill the students array
        for(int i = 0; i < numberOfStudents; i++){
            // prompt a user to enter a student name
            System.out.println("Enter the name of student no.: " + (i + 1));
            String studentName = sc.nextLine();

            // check if the input string contains any digits
            while(studentName.matches(".*\\d.*")){
                System.out.println("Invalid input. Names should not contain numbers. Please enter a name.");
                studentName = sc.nextLine();
            }

            // prompt user to enter a grade
            System.out.println("Enter the grade of student no.: " + (i + 1));
            while(!sc.hasNextInt()){
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
            }

            // save the output from above step to variable
            int studentGrade = sc.nextInt();
            sc.nextLine(); // consume newline

            // store a Student object into students array
            students[i] = new Student(studentName, studentGrade);

            System.out.println("");
            System.out.println("Student record saved ... ");
            System.out.println("");
        }


    }

    // method to check if user wants to continue or exit the program
    public static boolean continueProgram(){
        System.out.println("");
        System.out.println("Do you want to continue? (yes/no)");
        System.out.println("");
        String response = sc.nextLine();

        return response.equalsIgnoreCase("yes");
    }

    // method to initialize menu
    public static void consoleMenu(){
        int choice;
        boolean continueProgram;

        System.out.println("*** Menu ***");
        System.out.println("Choose an option from below to continue: ");
        System.out.println("");

        do{
            System.out.println("1. Display average class grade");
            System.out.println("2. Display lowest class grade");
            System.out.println("3. Display highest class grade");
            System.out.println("4. Sort & display the grades");
            System.out.println("5. Search for a student");
            System.out.println("6. Exit");
            System.out.println("");

            while(!sc.hasNextInt()){
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice){
                case 1:
                    averageGrade(students);
                    continueProgram = continueProgram();
                    break;
                case 2:
                    lowestGrade(students);
                    continueProgram = continueProgram();
                    break;
                case 3:
                    highestGrade(students);
                    continueProgram = continueProgram();
                    break;
                case 4:
                    sortStudents(students);

                    // print an empty line
                    System.out.println("");

                    // display the students in table
                    displayStudentsTable(students);

                    continueProgram = continueProgram();
                    break;
                case 5:
                    // display searched student
                    displaySearchedStudent();

                    continueProgram = continueProgram();
                    break;
                case 6:
                    System.out.println("Exiting the program ...");
                    continueProgram = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                    continueProgram = true;
            }
        }while(continueProgram);
    }

    // method to calculate and display average class grade
    public static void averageGrade(Student[] students){
        // declare a variables total to hold sum of grades
        int total = 0;

        // loop over students array and add each student grade to total
        for(Student student : students){
            total += student.grade;
        }

        // calculate an average grade
        double average = (double) total / students.length;
        System.out.println("The average grade in class is: " + average);
    }

    // method to find the lowest grade in a class
    public static void lowestGrade(Student[] students){
        // create a local variable to hold the lowest value
        int lowest = students[0].grade;

        // check what is the lowest grade
        for(int i = 1; i < students.length; i++){
            if(students[i].grade < lowest){
                lowest = students[i].grade;
            }
        }

        // printing out the lowest value
        System.out.println("");
        System.out.println("The lowest grade in class is: " + lowest);
    }

    // method to find the highest grade in a class
    public static void highestGrade(Student[] students){
        // local variable to hold the highest grade
        int highest = students[0].grade;

        // loop over grades to find the highest one
        for(int i = 1; i < students.length; i++){
            if(students[i].grade > highest){
                highest = students[i].grade;
            }
        }

        // print out the highest value
        System.out.println("");
        System.out.println("The highest grade in class is: " + highest);
    }

    // sort and display the student records in ascending order
    // for sorting the student grade was used as a key
    public static void sortStudents(Student[] students){
        for(int i = 1; i < students.length; i++){
            Student key = students[i];
            int j = i - 1;

            while(j >= 0 && students[j].grade > key.grade){
                students[j + 1] = students[j];
                j = j - 1;
            }

            students[j + 1] = key;
        }
    }

    // method to format the output of students in ASCII Table
    // using AsciiTable dependency / 3rd party library
    public static void displayStudentsTable(Student[] students){
        int index = 1;
        AsciiTable asciiTable = new AsciiTable();

        // creating a row
        asciiTable.addRule();
        asciiTable.addRow("ID", "Name", "Grade");
        asciiTable.addRule();

        // looping over students array and filling the table with individual student
        for(Student student : students){
            asciiTable.addRow(index, student.name, student.grade);
            asciiTable.addRule();
            index++;
        }

        // alignment of table text and rendering
        asciiTable.setTextAlignment(TextAlignment.CENTER);
        String rend = asciiTable.render();
        System.out.println(rend);
    }

    // method to search for a specific student
    public static int studentSearch(Student[] students, String key){
        // sort the students array
        sortStudents(students);

        // declare necessary variable for searching algorithm
        int low = 0;
        int high = students.length - 1;
        int middle;

        while(low <= high){
            middle = (low + high) / 2;

            if(students[middle].name.compareTo(key) == 0 ){
                return middle;
            }else if(students[middle].name.compareTo(key) > 0){
                high = middle - 1;
            }else {
                low = middle + 1;
            }
        }

        return -1;
    }

    // method to display the searched student
    public static void displaySearchedStudent(){
        // declare a variable to hold user input and result from search method
        String studentName;
        int searchedStudent;
        // print empty line
        System.out.println("");
        // prompt a user to enter the name of searched student
        System.out.println("Enter the name of student: ");

        // capture input by Scanner object
        studentName = sc.nextLine();

        // call search method
        searchedStudent = studentSearch(students, studentName);

        // check the response from searchedStudent method and display the student
        if(searchedStudent != -1){
            // student found
            System.out.println("Student with name: " + studentName + " was found !");
            System.out.println("");

            // check if user wants to display student data like grades
            System.out.println("Do you want to display " + studentName + "'s record ? (yes/no)");
            System.out.println("");
            String response = sc.nextLine();

            if(response.equals("yes")){
                AsciiTable asciiTable = new AsciiTable();

                // creating a row
                asciiTable.addRule();
                asciiTable.addRow("Name", "Grade");
                asciiTable.addRule();

                // filling the table with individual student
                asciiTable.addRow(studentName, students[searchedStudent].grade);
                asciiTable.addRule();

                // alignment of table text and rendering
                asciiTable.setTextAlignment(TextAlignment.CENTER);
                String rend = asciiTable.render();
                System.out.println(rend);
                System.out.println("");
            }else if(response.equals("no")){
                System.out.println("Loading main screen ...");
            }
        }else {
            // not found
            System.out.println("Student with name: " + studentName + " was not found !");
        }
    }
}