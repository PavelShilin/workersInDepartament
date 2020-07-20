import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        getListWorkers();
    }

    public static void getListWorkers() {
        ArrayList<Worker> workers = new ArrayList<>();
        Set<Departament> Departaments = new HashSet<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/Base.txt"));
            String currentLine;
            while (((currentLine = bufferedReader.readLine()) != null)) {
                //workers.add(new Worker(currentLine));
                Departaments.add(new Departament(currentLine));
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (Departament Departament : Departaments) {
          System.out.println(Departament.getName()+"  "+Departament.getWorkers()+" Средняя ЗП ="+Departament.getAvgSallary());
        }
    }
}





