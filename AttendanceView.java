import java.util.List;
import java.util.Scanner;

public class AttendanceView {
    private AttendanceController attendanceController;

    public AttendanceView() {
        attendanceController = AttendanceController.getInstance();
    }

    public void startProcess() {
        // Implement user interface and interaction
        // Use attendanceController methods to perform operations
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Attendance Management System");
            System.out.println("1. Admin");
            System.out.println("2. Faculty");
            System.out.println("3. Student");
            System.out.println("4. Exit");
            System.out.print("Enter your role: ");
            int role = scanner.nextInt();

            switch (role) {
                case 1:
                    handleAdminOperations();
                    break;
                case 2:
                    handleFacultyOperations();
                    break;
                case 3:
                    handleStudentOperations();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void handleAdminOperations() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Admin Operations");
            System.out.println("1. Add faculty");
            System.out.println("2. Remove faculty");
            System.out.println("3. Add student");
            System.out.println("4. Remove student");
            System.out.println("5. Add course");
            System.out.println("6. Remove course");
            System.out.println("7. Back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                    System.out.print("Enter faculty name: ");
                    String facultyName = scanner.nextLine();
                    attendanceController.addFaculty(new Faculty(facultyName));
                    System.out.println("Faculty added.");
                    break;
                case 2:
                    System.out.print("Enter faculty ID: ");
                    int facultyId = scanner.nextInt();
                    attendanceController.removeFaculty(facultyId);
                    System.out.println("Faculty removed.");
                    break;
                case 3:
                    System.out.print("Enter student name: ");
                    String studentName = scanner.nextLine();
                    attendanceController.addStudent(new Student(studentName));
                    System.out.println("Student added.");
                    break;
                case 4:
                    System.out.print("Enter student ID: ");
                    int studentId = scanner.nextInt();
                    attendanceController.removeStudent(studentId);
                    System.out.println("Student removed.");
                    break;
                case 5:
                    System.out.print("Enter course name: ");
                    String courseName = scanner.nextLine();
                    System.out.print("Enter course faculty ID: ");
                    int courseFacultyId = scanner.nextInt();
                    attendanceController.addCourse(new Course(courseName, new Faculty(courseFacultyId, null)));
                    System.out.println("Course added.");
                    break;
                case 6:
                    System.out.print("Enter course ID: ");
                    int courseId = scanner.nextInt();
                    attendanceController.removeCourse(courseId);
                    System.out.println("Course removed.");
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    
    }

    private void handleFacultyOperations() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your faculty ID: ");
        int facultyId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        while (true) {
            System.out.println("Faculty Operations");
            System.out.println("1. View courses");
            System.out.println("2. Update attendance");
            System.out.println("3. Back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                    List<Course> courses = attendanceController.getCoursesForFaculty(facultyId);
                    System.out.println("Courses:");
                    for (Course course : courses) {
                        System.out.println(course.getId() + ". " + course.getName());
                    }
                    break;
                case 2:
                    System.out.print("Enter attendance ID: ");
                    int attendanceId = scanner.nextInt();
                    System.out.print("Enter updated attendance percentage: ");
                    int attendancePercentage = scanner.nextInt();
                    attendanceController.updateAttendance(attendanceId, attendancePercentage);
                    System.out.println("Attendance updated.");
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
    

    private void handleStudentOperations() {
        
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your student ID: ");
            int studentId = scanner.nextInt();
            scanner.nextLine(); // Consume newline
        
            while (true) {
                System.out.println("Student Operations");
                System.out.println("1. View attendance");
                System.out.println("2. Back to main menu");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
        
                switch (choice) {
                    case 1:
                        List<Attendance> attendanceList = attendanceController.getAttendanceForStudent(studentId);
                        System.out.println("Attendance:");
                        for (Attendance attendance : attendanceList) {
                            System.out.println("Course: " + attendance.getCourse().getName() + ", Attendance: " + attendance.getAttendancePercentage() + "%");
                        }
                        break;
                    case 2:
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        
        

    }

    public static void main(String[] args) {
        AttendanceView view = new AttendanceView();
        view.startProcess();
    }
}
