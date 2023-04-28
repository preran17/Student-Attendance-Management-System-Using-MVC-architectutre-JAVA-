import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {
    // Implement Singleton Pattern
    private static AttendanceController instance;

    private Connection connection;

    private AttendanceController() {
        // Load MySQL JDBC driver and connect to the database
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_attendance", "root", "Rida@2504");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static AttendanceController getInstance() {
        if (instance == null) {
            instance = new AttendanceController();
        }
        return instance;
    }

    // Implement methods for admin, faculty, and student operations
    // These methods should execute SQL queries to interact with the database

    public void addFaculty(Faculty faculty) {
        // Add faculty to the database
        String sql = "INSERT INTO faculty (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, faculty.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFaculty(int facultyId) {
        // Remove faculty from the database
        String sql = "DELETE FROM faculty WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, facultyId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addStudent(Student student) {
        // Add student to the database
        String sql = "INSERT INTO student (name) VALUES (?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, student.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeStudent(int studentId) {
        // Remove student from the database
        String sql = "DELETE FROM student WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCourse(Course course) {
        // Add course to the database
        String sql = "INSERT INTO course (name, faculty_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, course.getName());
            pstmt.setInt(2, course.getFaculty().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCourse(int courseId) {
        // Remove course from the database
        String sql = "DELETE FROM course WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAttendance(int attendanceId, int attendancePercentage) {
        // Update attendance in the database
        String sql = "UPDATE attendance SET attendance_percentage = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, attendancePercentage);
            pstmt.setInt(2, attendanceId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getCoursesForFaculty(int facultyId) {
        // Retrieve faculty's courses from the database
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course WHERE faculty_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, facultyId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                courses.add(new Course(rs.getInt("id"), rs.getString("name"), new Faculty(rs.getInt("faculty_id"), null)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public List<Attendance> getAttendanceForStudent(int studentId) {
        // Retrieve student's attendance from the database
        List<Attendance> attendanceList = new ArrayList<>();
        String sql = "SELECT a.id, a.attendance_percentage, c.id AS course_id, c.name AS course_name " +
                     "FROM attendance a " +
                     "JOIN course c ON a.course_id = c.id " +
                     "WHERE a.student_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                attendanceList.add(new Attendance(
                    rs.getInt("id"),
                    new Student(studentId, null),
                    new Course(rs.getInt("course_id"), rs.getString("course_name"), null),
                    rs.getInt("attendance_percentage")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attendanceList;
    }
}
