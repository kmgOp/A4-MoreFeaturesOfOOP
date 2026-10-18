import java.io.*;
import java.util.*;

// simple enum for activity type
enum ActivityType {
    ACADEMIC, NON_ACADEMIC
}

// custom exception
class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}

// abstract class with different access modifiers
abstract class UniversityDepartment implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;
    private int id;
    public String location;

    public UniversityDepartment(String name, int id, String location) {
        this.name = name;
        this.id = id;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    protected void showProtectedInfo() {
        System.out.println("Department name: " + name);
    }

    public abstract void showDepartmentInfo();
}

// interfaces for activities
interface AcademicEvents {
    void displayAcademicActivities();
}

interface NonAcademicEvents {
    void displayNonAcademicActivities();
}

// one interface extending two interfaces
interface NIRFDetails extends AcademicEvents, NonAcademicEvents {
    void submitToNIRF();
}

// generic class for storing activities
class ActivityTracker<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<T> activities = new ArrayList<>();

    public void addActivity(T activity) {
        activities.add(activity);
    }

    public List<T> getActivities() {
        return activities;
    }
}

// single inheritance: DCST extends UniversityDepartment
// multiple interface inheritance: DCST implements three interfaces
class DCST extends UniversityDepartment
        implements NIRFDetails, Cloneable {

    private static final long serialVersionUID = 1L;
    private static int instanceCount = 0;

    public List<String> programs = new ArrayList<>();
    public Map<String, List<String>> courses = new LinkedHashMap<>();

    public ActivityTracker<String> academicTracker = new ActivityTracker<>();
    public ActivityTracker<String> nonAcademicTracker = new ActivityTracker<>();

    private Set<String> staff = new LinkedHashSet<>();
    private Queue<String> upcomingActivities = new LinkedList<>();

    public DCST(String name, int id, String location) {
        super(name, id, location);
        instanceCount++;
    }

    public static void displayInstanceCount() {
        System.out.println("DCST objects created: " + instanceCount);
    }

    // method overloading
    public void addProgram(String program) {
        programs.add(program);
    }

    public void addProgram(String program, List<String> subjects) {
        programs.add(program);
        courses.put(program, new ArrayList<>(subjects));
    }

    // method overriding
    @Override
    public void showDepartmentInfo() {
        System.out.println("Department: " + name);
        System.out.println("Department ID: " + getId());
        System.out.println("Location: " + location);
    }

    @Override
    public void displayAcademicActivities() {
        System.out.println("\nAcademic Activities:");
        for (String activity : academicTracker.getActivities()) {
            System.out.println("- " + activity);
        }
    }

    @Override
    public void displayNonAcademicActivities() {
        System.out.println("\nNon-Academic Activities:");
        for (String activity : nonAcademicTracker.getActivities()) {
            System.out.println("- " + activity);
        }
    }

    @Override
    public void submitToNIRF() {
        System.out.println("\nSubmitting data to NIRF...");
        System.out.println("Programs: " + programs);
        System.out.println("Courses and subjects: " + courses);
        System.out.println("Staff: " + staff);
    }

    public void addStaff(String member) {
        staff.add(member);
    }

    public void addUpcomingActivity(String activity) {
        upcomingActivities.offer(activity);
    }

    public void displayCollections() {
        System.out.println("List - Programs: " + programs);
        System.out.println("Map - Courses: " + courses);
        System.out.println("Set - Staff: " + staff);
        System.out.println("Queue - Upcoming activities: " + upcomingActivities);
    }

    // generic method
    public static <T> void displayItem(T item) {
        System.out.println("Generic item: " + item);
    }

    // shallow clone with independent activity trackers
    @Override
    public Object clone() throws CloneNotSupportedException {
        DCST copy = (DCST) super.clone();

        copy.academicTracker = new ActivityTracker<>();
        for (String activity : academicTracker.getActivities()) {
            copy.academicTracker.addActivity(activity);
        }

        copy.nonAcademicTracker = new ActivityTracker<>();
        for (String activity : nonAcademicTracker.getActivities()) {
            copy.nonAcademicTracker.addActivity(activity);
        }

        // programs and courses are still shared
        return copy;
    }
}

// multilevel inheritance example
class ResearchDepartment extends UniversityDepartment {
    private static final long serialVersionUID = 1L;

    public ResearchDepartment(String name, int id, String location) {
        super(name, id, location);
    }

    @Override
    public void showDepartmentInfo() {
        System.out.println("Research Department: " + name);
    }
}

class AIResearchDepartment extends ResearchDepartment {
    private static final long serialVersionUID = 1L;

    public AIResearchDepartment(String name, int id, String location) {
        super(name, id, location);
    }

    @Override
    public void showDepartmentInfo() {
        System.out.println("AI Research Department: " + name);
    }
}

// hierarchical inheritance: two classes extend the same parent
class TourismDepartment extends UniversityDepartment {
    private static final long serialVersionUID = 1L;

    public TourismDepartment(String name, int id, String location) {
        super(name, id, location);
    }

    @Override
    public void showDepartmentInfo() {
        System.out.println("Tourism Department: " + name);
    }
}

// hybrid inheritance: class inheritance combined with interfaces
class AgricultureDepartment extends UniversityDepartment
        implements AcademicEvents, NonAcademicEvents {

    private static final long serialVersionUID = 1L;

    public AgricultureDepartment(String name, int id, String location) {
        super(name, id, location);
    }

    @Override
    public void showDepartmentInfo() {
        System.out.println("Agriculture Department: " + name);
    }

    @Override
    public void displayAcademicActivities() {
        System.out.println("Agriculture seminar and field training");
    }

    @Override
    public void displayNonAcademicActivities() {
        System.out.println("Agriculture exhibition");
    }
}

// message passing through an interface
class DataProcessor {
    public void process(NIRFDetails details) {
        details.displayAcademicActivities();
        details.displayNonAcademicActivities();
        details.submitToNIRF();
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("===== DCST NIRF INFORMATION SYSTEM =====");

        try {
            // constructors and shared reference
            DCST dcst = new DCST(
                    "Computer Science", 269, "Goa Business School");

            DCST reference = dcst;
            reference.location = "GBS, Taleigao";

            System.out.println("\n1. OBJECT REFERENCES");
            dcst.showDepartmentInfo();
            System.out.println("Shared reference location: " + dcst.location);

            // static and enum
            System.out.println("\n2. STATIC MEMBERS AND ENUM");
            DCST.displayInstanceCount();
            System.out.println("Activity type: " + ActivityType.ACADEMIC);

            // programs and subjects
            dcst.addProgram("M.C.A.", Arrays.asList(
                    "Mathematics", "Internet Technologies", "Data Structures"));
            dcst.addProgram("M.Sc. Data Science", Arrays.asList(
                    "Python", "Machine Learning", "Data Analytics"));
            dcst.addProgram("M.Sc. Artificial Intelligence", Arrays.asList(
                    "Artificial Intelligence", "Machine Learning"));

            // academic activities
            dcst.academicTracker.addActivity("ISA");
            dcst.academicTracker.addActivity("Midpoint");
            dcst.academicTracker.addActivity("SEA");
            dcst.academicTracker.addActivity("Study Tour");
            dcst.academicTracker.addActivity("Placements");

            // non-academic activities
            dcst.nonAcademicTracker.addActivity("InfoFest");
            dcst.nonAcademicTracker.addActivity("Plateaunica");
            dcst.nonAcademicTracker.addActivity("Play-on");

            dcst.addStaff("Teaching Staff");
            dcst.addStaff("Non-Teaching Staff");
            dcst.addUpcomingActivity("Guest Lecture");
            dcst.addUpcomingActivity("Department Seminar");

            // abstract class and access modifiers
            System.out.println("\n3. ACCESS MODIFIERS");
            System.out.println("Public location: " + dcst.location);
            System.out.println("Private ID through getter: " + dcst.getId());

            // interface and message passing
            System.out.println("\n4. MESSAGE PASSING");
            DataProcessor processor = new DataProcessor();
            processor.process(dcst);

            // overloading
            System.out.println("\n5. METHOD OVERLOADING");
            dcst.addProgram("Additional Program");
            System.out.println("One-argument addProgram called");

            // collections and generics
            System.out.println("\n6. COLLECTIONS AND GENERICS");
            dcst.displayCollections();
            DCST.displayItem("Computer Science");
            DCST.displayItem(100);

            // inheritance demonstrations
            System.out.println("\n7. INHERITANCE DEMONSTRATIONS");

            dcst.showDepartmentInfo();

            ResearchDepartment research = new ResearchDepartment(
                    "Research", 101, "Goa");
            AIResearchDepartment ai = new AIResearchDepartment(
                    "AI Research", 102, "Goa");
            research.showDepartmentInfo();
            ai.showDepartmentInfo();

            TourismDepartment tourism = new TourismDepartment(
                    "Tourism", 103, "Goa");
            AgricultureDepartment agriculture = new AgricultureDepartment(
                    "Agriculture", 104, "Goa");
            tourism.showDepartmentInfo();
            agriculture.showDepartmentInfo();

            System.out.println("Java does not support extending two classes.");
            System.out.println("Multiple inheritance is demonstrated using interfaces.");
            agriculture.displayAcademicActivities();
            agriculture.displayNonAcademicActivities();

            // reflection
            System.out.println("\n8. REFLECTION");
            Class<?> cls = dcst.getClass();
            System.out.println("Class: " + cls.getName());
            System.out.println("Superclass: "
                    + cls.getSuperclass().getSimpleName());

            for (Class<?> item : cls.getInterfaces()) {
                System.out.println("Interface: " + item.getSimpleName());
            }

            // shallow and deep cloning
            System.out.println("\n9. CLONING");
            DCST cloned = (DCST) dcst.clone();

            cloned.programs.add("Cloned Program");
            cloned.academicTracker.addActivity("Extra Guest Lecture");

            System.out.println("Original programs: " + dcst.programs);
            System.out.println("Cloned programs: " + cloned.programs);
            System.out.println("Original activities: "
                    + dcst.academicTracker.getActivities());
            System.out.println("Cloned activities: "
                    + cloned.academicTracker.getActivities());

            // persistence and file I/O
            System.out.println("\n10. PERSISTENCE");
            try (ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("dcst_data.ser"))) {
                out.writeObject(dcst);
                System.out.println("Department saved successfully");
            }

            try (ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("dcst_data.ser"))) {
                DCST restored = (DCST) in.readObject();
                System.out.println("Restored department: " + restored.getName());
            }

            // custom exception
            System.out.println("\n11. EXCEPTION HANDLING");
            try {
                int students = -5;
                if (students < 0) {
                    throw new InvalidDataException(
                            "Student count cannot be negative");
                }
            } catch (InvalidDataException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("\nProgram finished.");
        }
    }
}