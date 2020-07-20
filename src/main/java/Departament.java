import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Departament{
    String name;
    float avgSallary;
   // private ArrayList<Worker> workers = new ArrayList();
   private ArrayList<String> workers = new ArrayList<>();

    public Departament(String line){
                String[] workerInfo = line.split(" ");
        //this.addWorker(line);
        this.name= workerInfo[3];
        addWorker();

    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getWorkers() {
        return workers;
    }

    public void addWorker(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/Base.txt"));
            String currentLine;
            while (((currentLine = bufferedReader.readLine()) != null)) {
                String[] workerInfo = currentLine.split(" ");
                    if (workerInfo[3].equals(this.name)){
                        workers.add(workerInfo[0]+" "+workerInfo[1]+" ");
                    }
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public float getAvgSallary() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/Base.txt"));
            String currentLine;
            int count=0;
            float sum=0;
            while (((currentLine = bufferedReader.readLine()) != null)) {
                String[] workerInfo = currentLine.split(" ");
                if (workerInfo[3].equals(this.name)){
                    count++;
                    sum=sum+Float.parseFloat(workerInfo[2]);
                    }
            }
            avgSallary = sum/count;
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return avgSallary;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departament)) return false;
        Departament that = (Departament) o;
        return Float.compare(that.avgSallary, avgSallary) == 0 &&
                Objects.equals(name, that.name);

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, avgSallary);
    }
}
