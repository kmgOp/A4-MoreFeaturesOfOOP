import java.io.*;
import java.util.*;
import java.lang.reflect.*;

//Enums
enum ActivityType { ACADEMIC, NON_ACADEMIC }

//Exception Handling (Custom Exception)
class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}

//Abstract Classes & Interfaces
abstract class UniversityDepartment implements Serializable {
    //Access Modifiers: protected, private, public
    protected String name;
    private int id;
    public String location;

    //Constructors
    public UniversityDepartment(String name, int id, String location) {
        this.name = name;
        this.id = id;
        this.location = location;
    }

    public int getId() { return id; }
    
    //Abstract method
    public abstract void showDepartmentInfo();
}

//Multiple Inheritance via Interfaces
interface AcademicEvents {
    void displayAcademicActivities();
}

interface NonAcademicEvents {
    void displayNonAcademicActivities();
}

// Interface inheriting multiple interfaces
interface NIRFDetails extends AcademicEvents, NonAcademicEvents {
    void submitToNIRF();
}

//Generic Class 
class ActivityTracker<T> implements Serializable {
    private List<T> activities = new ArrayList<>();
    
    public void addActivity(T activity) {
        activities.add(activity);
    }
    
    public List<T> getActivities() {
        return activities;
    }
}

// Main class representing DCST
class DCST extends UniversityDepartment implements NIRFDetails, Cloneable {
    //Static Attributes & Methods
    private static int instanceCount = 0;
    
    //Collection Frameworks (List & Map)
    public List<String> programs = new ArrayList<>();
    public Map<String, List<String>> courses = new LinkedHashMap<>();
    
    public ActivityTracker<String> academicTracker = new ActivityTracker<>();
    public ActivityTracker<String> nonAcademicTracker = new ActivityTracker<>();

    public DCST(String name, int id, String location) {
        super(name, id, location);
        instanceCount++; // Tracks object creation
    }

    public static void displayInstanceCount() {
        System.out.println("Total DCST Instances Created: " + instanceCount);
    }

    //Polymorphism (Overloading)
    public void addProgram(String program) {
        programs.add(program);
    }

    public void addProgram(String program, List<String> subjects) {
        programs.add(program);
        courses.put(program, subjects);
    }

    //Polymorphism (Overriding)
    @Override
    public void showDepartmentInfo() {
        System.out.println("Department: " + name + " | Location: " + location);
    }

    @Override
    public void displayAcademicActivities() {
        System.out.println("\n Academic Activities ");
        for(String act : academicTracker.getActivities()) {
            System.out.println("- " + act);
        }
    }

    @Override
    public void displayNonAcademicActivities() {
        System.out.println("\n Non-Academic Activities ");
        for(String act : nonAcademicTracker.getActivities()) {
            System.out.println("- " + act);
        }
    }

    @Override
    public void submitToNIRF() {
        System.out.println("\nSubmitting Data to NIRF Portal...");
        System.out.println("Programs Offered: " + programs);
    }

    //Cloning (Shallow vs Deep)
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // Shallow clone
        DCST cloned = (DCST) super.clone();
        
        // Deep clone implementation for the tracking arrays
        cloned.academicTracker = new ActivityTracker<>();
        for(String a : this.academicTracker.getActivities()) {
            cloned.academicTracker.addActivity(a);
        }
        return cloned;
    }
}
class DataProcessor {
    //implementing NIRFDetails
    public void processNIRFSubmission(NIRFDetails entity) {
        System.out.println("\n[Initiating Message Passing to System...]");
        entity.displayAcademicActivities();
        entity.displayNonAcademicActivities();
        entity.submitToNIRF();
    }
}
public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("DCST Goa University NIRF System\n");
            
            // 1. Object Referencing & Constructors
            DCST dcst1 = new DCST("Computer Science", 269, "Goa Business School");
            DCST referenceDcst = dcst1; // Multiple objects sharing same reference
            
            // Demonstrate side effect of referencing
            referenceDcst.location = "GBS, Taleigao"; 
            System.out.println("Location updated via shared reference: " + dcst1.location);
            
            // 2. Static Method Call
            DCST.displayInstanceCount();
            dcst1.addProgram("M.C.A.", Arrays.asList("Maths", "Internet Tech", "Data Structure"));
            dcst1.addProgram("M.Sc. Data Science", Arrays.asList("Machine Learning", "Python"));
            dcst1.addProgram("M.Sc. Artificial Intelligence");
            
            
            // Activities
            dcst1.academicTracker.addActivity("ISA");
            dcst1.academicTracker.addActivity("Midpoint");
            dcst1.academicTracker.addActivity("SEA");
            dcst1.academicTracker.addActivity("Study Tour");
            dcst1.academicTracker.addActivity("Placements");

            dcst1.nonAcademicTracker.addActivity("InfoFest (Tech Event)");
            dcst1.nonAcademicTracker.addActivity("Plateaunica (Inter-departmental fiesta)");
            dcst1.nonAcademicTracker.addActivity("Play-on (Sports)");
            
            // 6. Message Passing
            DataProcessor processor = new DataProcessor();
            processor.processNIRFSubmission(dcst1);

            // 10. Reflection (RTTI)
            System.out.println("\n Reflection ");
            Class<?> cls = dcst1.getClass();
            System.out.println("Class Name: " + cls.getName());
            System.out.println("Implemented Interfaces: ");
            for (Class<?> i : cls.getInterfaces()) {
                System.out.println("- " + i.getSimpleName());
            }

            // 10. Deep/Shallow Cloning
            System.out.println("\n Cloning Deep/Shallow ");
            DCST clonedDcst = (DCST) dcst1.clone();
            clonedDcst.academicTracker.addActivity("Extra Guest Lecture"); 
            System.out.println("Original Programs (affected by shallow clone): " + dcst1.programs);
            System.out.println("Original Academic Activities (unaffected by deep clone): " + dcst1.academicTracker.getActivities());
            System.out.println("Cloned Academic Activities: " + clonedDcst.academicTracker.getActivities());

            // 10. Persistence & I/O
            System.out.println("\n Persistence File I/O ");
            String filename = "dcst_data.ser";
            
            // Serialize
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(dcst1);
                System.out.println("Object successfully serialized to " + filename);
            }
            
            // Deserialize
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
                DCST deserialized = (DCST) in.readObject();
                System.out.println("Object successfully deserialized.");
                System.out.println("Recovered Dept Name: " + deserialized.name);
            }

            // 9. Exception Handling
            System.out.println("\n Exception Handling ");
            throwCustomExceptionTest();

        } catch (Exception e) {
            System.out.println("General Exception caught: " + e.getMessage());
        } finally {
            System.out.println("\nExecution Completed. Resources cleaned up in finally block.");
        }
    }
    
    // Custom Exception Test using 'throws' and 'throw'
    public static void throwCustomExceptionTest() throws InvalidDataException {
        try {
            int students = -5;
            if (students < 0) {
                throw new InvalidDataException("Student count cannot be negative.");
            }
        } catch (InvalidDataException e) {
            System.out.println("Caught Custom Exception: " + e.getMessage());
        }
    }
}