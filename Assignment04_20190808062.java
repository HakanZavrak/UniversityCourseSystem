import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Hakan Zavrak
 * @since 02.06.2021
 */
public class Assignment04_20190808062 {
    public static void main(String[] args) {
        Department cse = new Department("CSE","Computer Engineering");
        Teacher teacher = new Teacher("Joseph LEDET","josephledet@akdeniz.edu.tr",123L,cse,3);
        Student student = new Student("Assignment 4 STUDENT","me@somewhere.com",456L,cse);
        Semester s2 = new Semester(2,2020);
        Semester s4 = new Semester(2,2021);
        Course cp1l = new Course(cse,101,"Computer Programming 1",2,teacher);
        Course neq = new Course(cse,181,"Natural Science",5,teacher);
        Course ccs = new Course(cse,105,"Introduction to Computer Science",2,
                teacher);
        student.addCourse(ccs,s2,79);
        student.addCourse(neq,s2,75);
        student.addCourse(cp1l, s2, 20);
        student.addCourse(cp1l, s4, 90);

        System.out.println("List Grades for CSE 101:\n"+ student.listGrades(cp1l));
        System.out.println("List Grades for Spring 2020:\n"+student.listGrades(s2));
        System.out.println("Student Transcript:\n"+student.transcript());
    }
}
class Department{
    private String id;
    private String name;
    private Teacher chair;

    public Department(String id, String name) {
        if (id.length()!=3 && id.length()!=4)
            throw new InvalidIDException(id);
        this.id = id;
        this.name = name;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        if (id.length()!=3 && id.length()!=4)
            throw new InvalidIDException(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getChair() {
        return chair;
    }

    public void setChair(Teacher chair) {
        if (this.chair==null){
            if (this==chair.getDepartment())
                this.chair=chair;
            else
                throw new DepartmentMismatchException(this,chair);
        }
        else{
            if(chair == null){
                this.chair = null;
            }
            else if (this ==chair.getDepartment())
                this.chair=chair;
            else
                throw new DepartmentMismatchException(this,chair);
        }
    }
}
class Course{
    private Department department;
    private int number;
    private String title;
    private int akts;
    private Teacher teacher;

    public Course(Department department, int number, String title,
                  int akts,Teacher teacher) {
        this.department =department;
        if (number<100||number>499) {
            if (number < 5000 || number > 5999){
                if (number<7000||number>7999)
                    throw new InvalidNumberException(number);
            }
        }
        this.number = number;
        if (akts<0)
            throw new InvalidAKTSException(akts);
        if (teacher.getDepartment()!=department)
            throw new DepartmentMismatchException(this,teacher);
        this.title = title;
        this.akts = akts;
        this.teacher = teacher;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        if (teacher.getDepartment()!=department)
            throw new DepartmentMismatchException(this,teacher);
        this.teacher = teacher;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if (number<100||number>499) {
            if (number < 5000 || number > 5999){
                if (number<7000||number>7999)
                    throw new InvalidNumberException(number);
            }
        }
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAKTS() {
        return akts;
    }

    public void setAKTS(int akts) {
        if (akts <0){
            throw new InvalidAKTSException(akts);
        }
        this.akts = akts;
    }
    public String courseCode(){
        return this.department.getID()+" "+this.number;

    }
    public String toString(){
        return this.department.getID()+" "+this.number+" - "+this.title+" ("
                +this.akts +")";
    }
}
abstract class Person{
    private String name;
    private String email;
    private long id;
    private Department department;

    public Person(String name, String email, long id, Department department) {

        if (email.matches("(.*)@(.*).com") || email.matches
                ("(.*)@(.*)" +
                        ".edu.tr")){
            this.name = name;
            this.email = email;
            this.id = id;
            this.department = department;
        }else
            throw new InvalidEmailException(email);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.matches("(.*)@(.*).com") || email.matches
                ("(.*)@(.*)" +
                        ".edu.tr"))
            this.email = email;
        else
            throw new InvalidEmailException(email);

    }

    public long getID() {
        return id;
    }

    public void setID(long id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String toString(){
        return getName()+" ("+getID()+") - "+getEmail();
    }
}
class Teacher extends Person{
    private int rank;
    public Teacher(String name, String email, long id, Department department,
                   int rank) {
        super(name, email, id, department);
        if (rank!=1&&rank!=2&&rank!=3&&rank!=4&&rank!=5)
            throw new InvalidRankException(rank);
        this.rank=rank;
    }

    @Override
    public void setDepartment(Department department) {
        if (this==this.getDepartment().getChair()) {
            this.getDepartment().setChair(null);
        }
        super.setDepartment(department);
    }

    public int getRank() {
        return rank;
    }

    public String getTitle(){
        if (rank==1)
            return "Adjunct Instructor";
        else if (rank==2)
            return "Lecturer";
        else if (rank==3)
            return "Assistant Professor";
        else if (rank==4)
            return "Associate Professo";
        else
            return "Professor";
    }
    public void promote(){
        if (rank==1||rank==2||rank==3||rank==4)
            rank=getRank()+1;
        else
            throw new InvalidRankException(rank+1);
    }
    public void demote(){
        if (rank==4||rank==3||rank==2||rank==5)
            rank=getRank()-1;
        else
            throw new InvalidRankException(rank-1);
    }
    public String toString(){
        return getTitle()+" "+super.toString();
    }

}
class Student extends Person{
    private ArrayList<Course> Courses = new ArrayList<>();
    private ArrayList<Double> Grades = new ArrayList<>();
    private ArrayList<Semester> Semesters = new ArrayList<>();

    public Student(String name, String email, long id, Department department) {
        super(name, email, id, department);
    }
    public ArrayList<Semester> quesem(){
        ArrayList<Semester> quesem=new ArrayList<>();
        for (int i = 0; i < Semesters.size(); i++) {
            quesem.add(Semesters.get(i));
        }
        Collections.sort(quesem,Comparator.comparing(Semester::getSeason));
        return quesem;
    }

    public int getAKTS(){
        int gradakts=0;
        ArrayList<Course> result =courseaktsIndex();
        for (int i = 0; i < result.size(); i++) {
            gradakts+=result.get(i).getAKTS();
        }
        return gradakts;
    }
    public ArrayList<Course> courseaktsIndex(){
        ArrayList<Course> result =new ArrayList<>();
        for (int i = 0; i < Courses.size(); i++) {
            if (soncourse(Courses.get(i))>=60){
                if (!result.contains(Courses.get(i)))
                    result.add(Courses.get(i));
            }
        }
        return result;
    }
    public ArrayList<Course> courseattempaktsIndex(){
        ArrayList<Course> result =new ArrayList<>();
        for (int i = 0; i < Courses.size(); i++) {
            if (soncourse(Courses.get(i))>=0){
                if (!result.contains(Courses.get(i)))
                    result.add(Courses.get(i));
            }
        }
        return result;
    }

    public int getAttemptedAKTS(){
        int gradakts=0;
        ArrayList<Course> result =courseattempaktsIndex();
        for (int i = 0; i < result.size(); i++) {
            gradakts+=result.get(i).getAKTS();
        }
        return gradakts;
    }

    public void addCourse(Course course,Semester semester,double grade){
        if (grade<0||grade>100)
            throw new InvalidGradeException(grade);
        for (int i = 0; i < Semesters.size(); i++) {
            if (Semesters.get(i)==semester&&Courses.get(i)==course){
                Grades.set(i,grade);
                return;
            }
        }
        Courses.add(course);
        Grades.add(grade);
        Semesters.add(semester);
    }

    public double courseGPAPoints(Course course){
        if (!Courses.contains(course))
            throw new CourseNotFoundException(this,course);
        else {
            double gradgrade=soncourse(course);
            if (gradgrade >= 88 && gradgrade <= 100)
                return 4.0;
            else if (gradgrade >= 81 && gradgrade <= 87)
                return 3.5;
            else if (gradgrade >= 74 && gradgrade <= 80)
                return 3.0;
            else if (gradgrade >= 67 && gradgrade <= 73)
                return 2.5;
            else if (gradgrade >= 60 && gradgrade <= 66)
                return 2.0;
            else if (gradgrade >= 53 && gradgrade <= 59)
                return 1.5;
            else if (gradgrade >= 46 && gradgrade <= 52)
                return 1.0;
            else if (gradgrade >= 35 && gradgrade <= 45)
                return 0.5;
            else
                return 0.0;
        }
    }
    public double soncourse(Course course){
        double result=0;
        for (int i = 0; i < Courses.size(); i++) {
            if (Courses.get(i)==course){
                if (Grades.get(i)>result){
                    result=Grades.get(i);
                }
            }
        }
        return result;
    }
    public String courseGradeLetter(Course course){
        if (!Courses.contains(course))
            throw new CourseNotFoundException(this,course);
        else{
            double gradgrade=soncourse(course);
            if (gradgrade >= 88 && gradgrade <= 100)
                return "AA";
            else if (gradgrade >= 81 && gradgrade <= 87)
                return "BA";
            else if (gradgrade >= 74 && gradgrade <= 80)
                return "BB";
            else if (gradgrade >= 67 && gradgrade <= 73)
                return "CB";
            else if (gradgrade >= 60 && gradgrade <= 66)
                return "CC";
            else if (gradgrade >= 53 && gradgrade <= 59)
                return "DC";
            else if (gradgrade >= 46 && gradgrade <= 52)
                return "DD";
            else if (gradgrade >= 35 && gradgrade <= 45)
                return "FD";
            else
                return "FF";
        }
    }

    public String courseGradeLetterSeason(int index){
        if (!Courses.contains(Courses.get(index)))
            throw new CourseNotFoundException(this,Courses.get(index));
        else{
            double gradgrade=Grades.get(index);
            if (gradgrade >= 88 && gradgrade <= 100)
                return "AA";
            else if (gradgrade >= 81 && gradgrade <= 87)
                return "BA";
            else if (gradgrade >= 74 && gradgrade <= 80)
                return "BB";
            else if (gradgrade >= 67 && gradgrade <= 73)
                return "CB";
            else if (gradgrade >= 60 && gradgrade <= 66)
                return "CC";
            else if (gradgrade >= 53 && gradgrade <= 59)
                return "DC";
            else if (gradgrade >= 46 && gradgrade <= 52)
                return "DD";
            else if (gradgrade >= 35 && gradgrade <= 45)
                return "FD";
            else
                return "FF";
        }
    }

    public String courseResult(Course course){
        if (!Courses.contains(course))
            throw new CourseNotFoundException(this,course);
        else{
            double gradgrade=Grades.get(Courses.indexOf(course));
            if (gradgrade >= 60 && gradgrade <= 100)
                return "Passed";
            else if (gradgrade < 60 && gradgrade >= 46)
                return "Conditionally Passed";
            else
                return "Failed";
        }
    }
    public double getGPA(){
        double gradGPA=0;
        ArrayList<Course> corse=new ArrayList<>();
        for (int i = 0; i < Courses.size(); i++) {
            if (!corse.contains(Courses.get(i)))
                corse.add(Courses.get(i));
        }
        for (int i = 0; i < corse.size(); i++) {
            gradGPA+=courseGPAPoints(corse.get(i))*corse.get(i).getAKTS();
        }
        return gradGPA/getAttemptedAKTS();
    }
    public String listGrades(Semester semester){
        ArrayList<Integer> index=semesterindex(semester);
        String res="";
        if (!Semesters.contains(semester))
            throw new SemesterNotFoundException(this,semester);
        for (int i = 0; i < index.size(); i++) {
            res+=Courses.get(index.get(i)).courseCode()+" - " +
                    ""+courseGradeLetterSeason(index.get(i));
            res+="\n";
        }
        return res;
    }
    public ArrayList<Integer> semesterindex(Semester semester){
        ArrayList<Integer> result =new ArrayList<>();
        for (int i = 0; i < Semesters.size(); i++) {
            if (Semesters.get(i)==semester)
                result.add(i);
        }
        return result;
    }
    public String listGrades(Course course){
        String res="";
        ArrayList<Integer> index= courseindex(course);
        if (!Courses.contains(course))
            throw new CourseNotFoundException(this,course);
        else{
            for (int i = 0; i < index.size() ; i++) {
                res += Semesters.get(index.get(i)) + " -" +
                        " " + courseGradeLetterSeason(index.get(i));
                res += "\n";
            }
            return res;
        }
    }
    public ArrayList<Integer> courseindex(Course course){
        ArrayList<Integer> result =new ArrayList<>();
        for (int i = 0; i < Courses.size(); i++) {
            if (Courses.get(i)==course)
                result.add(i);
        }
        return result;
    }
    public ArrayList<Semester> getsetseason(){
        ArrayList<Semester> resSeason =new ArrayList<>();
        for (int i = 0; i < Semesters.size(); i++) {
            if (!resSeason.contains(Semesters.get(i)))
                resSeason.add(Semesters.get(i));
        }
        return resSeason;
    }
    public String transcript(){
        String res="";
        ArrayList<Semester> result = getsetseason();
        result.sort(new SeasonSorter().thenComparing(Semester::getSeason));
        for (int i = 0; i < result.size(); i++) {
            res+=result.get(i)+"\n";
            double resultgpa=0;
            double totalakts=0;
            for (int j = 0; j < Courses.size(); j++) {
                if (result.get(i)==Semesters.get(j)){
                    res+="\t"+Courses.get(j).courseCode()+" -" +
                            " "+courseGradeLetterSeason(j)+"\n";
                    resultgpa+=courseGPAPoints(Grades.get(j))*
                            Courses.get(j).getAKTS();
                    totalakts+=Courses.get(j).getAKTS();
                }
            }
            res+="GPA: - "+resultgpa/totalakts+"\n\n";
        }
        return res+"Overall GPA: "+getGPA();
    }

    public double courseGPAPoints(Double grade){
        if (grade >= 88 && grade <= 100)
            return 4.0;
        else if (grade >= 81 && grade <= 87)
            return 3.5;
        else if (grade >= 74 && grade <= 80)
            return 3.0;
        else if (grade >= 67 && grade <= 73)
            return 2.5;
        else if (grade >= 60 && grade <= 66)
            return 2.0;
        else if (grade >= 53 && grade <= 59)
            return 1.5;
        else if (grade >= 46 && grade <= 52)
            return 1.0;
        else if (grade >= 35 && grade <= 45)
            return 0.5;
        else
            return 0.0;
    }



    public String toString(){
        return super.toString()+" - GPA: "+ getGPA();
    }

    public ArrayList<Course> getCourses() {
        return Courses;
    }

    public ArrayList<Double> getGrades() {
        return Grades;
    }

    public ArrayList<Semester> getSemesters() {
        return Semesters;
    }
}
class GradStudent extends Student{
    private String thesis;
    private Course teachingAssistant;

    public GradStudent(String name, String email, long id,
                       Department department,
                       String thesis) {
        super(name, email, id, department);
        this.thesis=thesis;
    }

    @Override public double courseGPAPoints(Course course) {
        if (!getCourses().contains(course))
            throw new CourseNotFoundException(this,course);
        else{
            double gradgrade=soncourse(course);
            if (gradgrade >= 90 && gradgrade <= 100)
                return 4.0;
            else if (gradgrade >= 85 && gradgrade <= 89)
                return 3.5;
            else if (gradgrade >= 80 && gradgrade <= 84)
                return 3.0;
            else if (gradgrade >= 75 && gradgrade <= 79)
                return 2.5;
            else if (gradgrade >= 70 && gradgrade <= 74)
                return 2.0;
            else
                return 1.5;
        }
    }

    @Override public String courseGradeLetter(Course course) {
        if (!getCourses().contains(course))
            throw new CourseNotFoundException(this,course);
        else{
            double gradgrade=soncourse(course);
            if (gradgrade >= 90 && gradgrade <= 100)
                return "AA";
            else if (gradgrade >= 85 && gradgrade <= 89)
                return "BA";
            else if (gradgrade >= 80 && gradgrade <= 84)
                return "BB";
            else if (gradgrade >= 75 && gradgrade <= 79)
                return "CB";
            else if (gradgrade >= 70 && gradgrade <= 74)
                return "CC";
            else
                return "FF";
        }
    }

    @Override public String courseResult(Course course) {
        if (!getCourses().contains(course))
            throw new CourseNotFoundException(this,course);
        else{
            double gradgrade=soncourse(course);
            if (gradgrade >= 70 && gradgrade <= 100)
                return "Passed";
            else
                return "Failed";
        }
    }
    public String getThesis() {
        return thesis;
    }

    public void setThesis(String thesis) {
        this.thesis = thesis;
    }
    public String toString(){
        return super.toString();
    }
    public void setTeachingAssistant(Course course){
        if (this.getTeachingAssistant()==null&&this.getCourses().contains
                (course)&&this.getGrades().get(this.getCourses().indexOf
                (course))>=80)
            this.teachingAssistant=course;
        else
            throw new CourseNotFoundException(this,course);
    }
    public Course getTeachingAssistant(){
        return teachingAssistant;
    }
}
final class Semester{
    private final int season;
    private final int year;

    public Semester(int season, int year) {
        this.season = season;
        this.year = year;
    }

    public String getSeason() {
        if (season==1)
            return "Fall";
        else if (season==2)
            return "Spring";
        else
            return "Summer";
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return getSeason()+" - "+getYear();
    }
}
class CourseNotFoundException extends RuntimeException{
    private Student student;
    private Course course;

    public CourseNotFoundException(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public String toString() {
        return "CourseNotFoundException: "+this.student.getID()+" has not yet" +
                " taken "+this.course.courseCode();
    }
}
class DepartmentMismatchException extends RuntimeException{
    private Department department;
    private Teacher person;
    private Course course;

    public DepartmentMismatchException(Course course,Teacher person) {
        this.person = person;
        this.course = course;
        this.department=null;
    }

    public DepartmentMismatchException(Department department, Teacher person) {
        this.department = department;
        this.person = person;
        this.course=null;
    }

    public String toString() {
        if (this.course==null)
            return "DepartmentMismatchException: "+this.person.getName()+"" +
                    "("+this.person.getID()+") cannot be chair of "+
                    this.department.getID()+" because he/she is currently " +
                    "assigned to "+ this.person.getDepartment().getID();
        else
            return "DepartmentMismatchException: "+this.person.getName()+"" +
                    "("+this.person.getID()+") cannot teach "+
                    this.course.courseCode()+" because he/she is " +
                    "currently " +
                    "assigned to "+ this.person.getDepartment().getID();
    }
}
class InvalidGradeException extends RuntimeException{
    private double grade;

    public InvalidGradeException(double grade) {
        this.grade = grade;
    }

    public String toString() {
        return "InvalidGradeException: "+this.grade;
    }
}
class InvalidRankException extends RuntimeException{
    private int rank;

    public InvalidRankException(int rank) {
        this.rank = rank;
    }

    public String toString() {
        return "InvalidRankException: "+this.rank;
    }

}
class InvalidIDException extends RuntimeException{
    private String id;

    public InvalidIDException(String id) {
        this.id = id;
    }

    public String toString() {
        return "InvalidIDException:Department ID invalid value entered "+
                this.id+" ID must be 3 or 4 characters";
    }
}
class InvalidNumberException extends RuntimeException{
    private int number;

    public InvalidNumberException(int number) {
        this.number = number;
    }

    public String toString() {
        return "InvalidNumberException:Teacher Number invalid value " +
                "entered "+this.number+" Valid values are (100-500,5000-6000," +
                "7000-8000)";
    }
}
class InvalidAKTSException extends RuntimeException{
    private int akts;
    public InvalidAKTSException(int akts) {
        this.akts = akts;
    }
    public String toString() {
        return "InvalidAKTSException:Student AKTS invalid value " +
                "entered "+this.akts+" AKTS must be non negative";
    }
}
class InvalidEmailException extends RuntimeException{
    private String email;

    public InvalidEmailException(String email) {
        this.email = email;
    }

    public String toString() {
        return "InvalidEmailException:Persons Email invalid value " +
                "entered "+this.email+" Email must be (...)@(...).com or (.." +
                ".)@"+
                "(...).edu.tr ";
    }
}
class SemesterNotFoundException extends RuntimeException{
    private Student student;
    private Semester semester;

    public SemesterNotFoundException(Student student, Semester semester) {
        this.student = student;
        this.semester = semester;
    }

    @Override
    public String toString() {
        return "SemesterNotFoundException: "+student.getID()+" has not taken " +
                "any courses in "+semester;
    }
}
class SeasonSorter implements Comparator<Semester>{

    @Override
    public int compare(Semester o1, Semester o2) {
        if (o1.getYear()<o2.getYear())
            return -1;
        else if (o1.getYear()>o2.getYear())
            return 1;
        else
            return 0;
    }
}