public class Worker {
    private String firstname;
    private String secondname;
   // private String departament;
    private Float salary;
    //private Departament departament;

    public Worker(String line)  {

        String[] workerInfo = line.split(" ");
        this.firstname= workerInfo[0];

        this.secondname=workerInfo[1];
        this.salary=Float.parseFloat(workerInfo[2]);


    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }


    public Float getSalary() {
        return salary;
    }
}
