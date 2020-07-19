import javax.xml.soap.SOAPPart;
import java.util.Arrays;
import java.util.regex.Pattern;

public class Worker {
    private String firstname;
    private String secondname;
    private String departament;
    private Float salary;

//    Departament departament;

    public Worker(String line)  {
       // String pattern = "\\s+|,\\s*";
        String[] workerInfo = line.split(" ");
        this.firstname= workerInfo[0];
        System.out.println("----"+workerInfo.length);
       // System.out.println(Arrays.toString(workerInfo));
        this.secondname=workerInfo[1];
        this.salary=Float.parseFloat(workerInfo[2]);
        this.departament= workerInfo[3];
    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public String getDepartament() {
        return departament;
    }

    public Float getSalary() {
        return salary;
    }
}
