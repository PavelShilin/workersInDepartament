import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        ArrayList<Worker> workers = new ArrayList<>();
        Map<Integer, Departament> departaments = new HashMap<Integer, Departament>();
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(args[0]))) {
            String currentLine;
            int i = 0;
            int j = 0;
            while (((currentLine = bufferedReader.readLine()) != null)) {
                i++;
                String[] lineFromFile = currentLine.split("/");
                if (lineFromFile.length != 4) {
                    //System.out.println(lineFromFile[0]);
                    throw new IndexOutOfBoundsException("Ошибка, в строке - " + i);

                } else {
                    workers.add(new Worker(lineFromFile[0], lineFromFile[1], BigDecimal.valueOf(Long.parseLong(lineFromFile[2])),lineFromFile[3]));
                    if (!(departaments.containsValue(new Departament(lineFromFile[3])))) {
                        departaments.put(j, new Departament(lineFromFile[3]));
                        j++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        //Связка Депатрамент с работниками
        for (Map.Entry<Integer, Departament> entry : departaments.entrySet()) {
            for (Worker workerInfo : workers){
                if (workerInfo.getDepartament().equals(entry.getValue().getName())){
                    entry.getValue().addWorker(workerInfo);
                }
            }
        }
        //==================================================
        int countDepartaments = departaments.size();

      //  System.out.println(countDepartaments);
        //==================================================


      departaments.get(0).addWorker(departaments.get(1).getWorkers().get(0));
        departaments.get(1).addWorker(departaments.get(0).getWorkers().get(0));
        departaments.get(0).removeWorker(departaments.get(0).getWorkers().get(0));
        departaments.get(1).removeWorker(departaments.get(1).getWorkers().get(0));

        departaments.get(0).addWorker(departaments.get(1).getWorkers().get(0));
        departaments.get(1).addWorker(departaments.get(0).getWorkers().get(0));
        departaments.get(0).removeWorker(departaments.get(0).getWorkers().get(0));
        departaments.get(1).removeWorker(departaments.get(1).getWorkers().get(0));





        //==================================================
       /* departaments.get(0).addWorker(departaments.get(1).getWorkers().get(0));
        departaments.get(1).addWorker(departaments.get(0).getWorkers().get(1));
        departaments.get(0).removeWorker(departaments.get(0).getWorkers().get(0));
        departaments.get(1).removeWorker(departaments.get(1).getWorkers().get(1));*/
        //==================================================
      /*  departaments.get(0).addWorker(departaments.get(1).getWorkers().get(0));
        departaments.get(1).addWorker(departaments.get(0).getWorkers().get(1));
        departaments.get(0).removeWorker(departaments.get(0).getWorkers().get(0));
        departaments.get(1).removeWorker(departaments.get(1).getWorkers().get(1));
        */


        for (int i=0 ; i<countDepartaments-1;i++){
            for (int j=0; j<countDepartaments-2 ; j++){


            }

        }

    // departaments.get(0).addWorker(departaments.get(1).getWorkers().get(0));
        //==================================================


        for (Map.Entry<Integer, Departament> entry : departaments.entrySet()) {
            System.out.println("Департамент: "+ entry.getValue().nameDepartament + " // Средняя ЗП в департаменте: "+  entry.getValue().getAvgSallary());
            for (Worker Work : entry.getValue().getWorkers()){
                System.out.println(Work.getFirstname());
            }
            System.out.println();
        }


    }
}

