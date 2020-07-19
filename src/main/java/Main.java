import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main (String[] args){
        ArrayList<Worker> workers = new ArrayList<>();
        try {
            BufferedReader  bufferedReader= new BufferedReader(new FileReader("src/main/resources/Base.txt"));
            String currentLine ;
            while (((currentLine = bufferedReader.readLine()) != null)){
                workers.add(new Worker(currentLine));
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
                workers.stream()
                    .map(x -> x.getFirstname() + " | " + x.getSecondname() +" | " + x.getSalary() +" | " + x.getDepartament())
                    .forEach(System.out::println);

    }

}

