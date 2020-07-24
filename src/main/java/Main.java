import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println(args.length);

        Map<String, Departament> departaments = new HashMap<String, Departament>();

        getMapFromFile(args,departaments);
        printInConsole(departaments);
        checkWorkerOnTransfer(departaments);

    }



    public static void getMapFromFile(String[] args,Map<String, Departament> customDepartaments){
            try (BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(args[0]))) {
                String currentLine;
                int i = 0;
                while (((currentLine = bufferedReader.readLine()) != null)) {
                    i++;
                    String[] lineFromFile = currentLine.trim().split("/");
                    if (lineFromFile.length != 4 || lineFromFile[0].equals(" ") || lineFromFile[1].equals(" ") || lineFromFile[2].equals(" ") || lineFromFile[3].equals(" ")) {
                        System.out.println("Ошибка: в строке - " + i);
                    } else {
                        if (!customDepartaments.containsKey(lineFromFile[3].trim())) {
                            customDepartaments.put(lineFromFile[3].trim(), new Departament(lineFromFile[3].trim()));
                        }
                        customDepartaments.get(lineFromFile[3].trim()).addWorker(new Worker(lineFromFile[0].trim(), lineFromFile[1].trim(), new BigDecimal(lineFromFile[2].trim())));
                    }
                }
            } catch (IOException e) {
                System.out.println("Ошибка считывания файла  " + e.getMessage());
            }
        }


    public static void printInConsole(Map<String, Departament> departamentMap) {
        for (Map.Entry<String, Departament> entry : departamentMap.entrySet()) {
            System.out.println("Департамент: " + entry.getValue().nameDepartament + " // Средняя ЗП в департаменте: " + entry.getValue().getAvgSallary());
            for (Worker Work : entry.getValue().getWorkers()) {
                System.out.println(Work.getSecondname());
            }
            System.out.println();
        }
    }

/*    private static void checkWorkerOnTransfer(Map<String,Departament> departamentsMap) {
        for (Map.Entry<String, Departament> entry : departamentsMap.entrySet()) {
            for (Map.Entry<String, Departament> entry2 : departamentsMap.entrySet())
            entry.getValue().getWorkers()

        }
    }*/
}


        //===============Возможные переводы сотрудников===========================
/*        int countDepartaments = departaments.size();//количество департаментов
        ArrayList<String> result = new ArrayList<>();//список с возмозными переводами сотрудников и отдела в отдел
        for (int i=0 ; i<countDepartaments;i++) {
            for (int j = 0; j < countDepartaments; j++) {
                if (i==j) {continue;}
                for (int k = 0 ; k<departaments.get(i).getCountWorkerInDepartament();k++){
                    BigDecimal oldSalaryInDepartament1 = departaments.get(i).getAvgSallary();
                    BigDecimal oldSalaryInDepartament2 = departaments.get(j).getAvgSallary();
                    departaments.get(j).addWorker(departaments.get(i).getWorkers().get(0));
                    departaments.get(i).removeWorker(departaments.get(i).getWorkers().get(0));
                    if (oldSalaryInDepartament1.compareTo(departaments.get(i).getAvgSallary()) < 0 && oldSalaryInDepartament2.compareTo(departaments.get(j).getAvgSallary()) < 0 ) {
                     //   System.out.println("Перевод сотрудника: "+departaments.get(i).getWorkers().get(0).getSecondname()+" "+departaments.get(i).getWorkers().get(0).getFirstname()+" в "+departaments.get(j).getName()+" повышает среднюю заработную плату в 2-х отделах");
                        result.add("Перевод сотрудника: "+departaments.get(i).getWorkers().get(0).getSecondname()+" "+departaments.get(i).getWorkers().get(0).getFirstname()+" в "+departaments.get(j).getName()+" повышает среднюю заработную плату в 2-х отделах");
                    }
                    departaments.get(i).addWorker(departaments.get(j).getWorkers().get(departaments.get(j).getCountWorkerInDepartament()-1));
                    departaments.get(j).removeWorker(departaments.get(j).getWorkers().get(departaments.get(j).getCountWorkerInDepartament()-1));
                    //System.out.println("I= "+i+"  j=  "+j + "  k=  "+k+"           работников в "+departaments.get(i).getName()+   departaments.get(i).getCountWorkerInDepartament() +  "   в"+ departaments.get(j).getName()+" j = "+departaments.get(j).getCountWorkerInDepartament());
                }
            }
        }

            //=========Запись в ArrayList с результатом в файл=======================================================

        try(OutputStream f = new FileOutputStream(args[1], true);
            OutputStreamWriter writer = new OutputStreamWriter(f);
            BufferedWriter out = new BufferedWriter(writer);)
        {
            for(String line : result)
            {
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
            }
        }
        catch(IOException ex)
        {
            System.out.println("Ошибка записи в файл  "+ ex.getMessage());
        }*/



/*
        for (int i=0 ; i<countDepartaments;i++) {
            if (i==countDepartaments-1) {break;}
            for (int j=i+1 ; j<countDepartaments; j++) {
                for (int k = 1; k <= ((departaments.get(i).getCountWorkerInDepartament() * departaments.get(j).getCountWorkerInDepartament()) ); k++) {
                    BigDecimal oldSalaryInDepartament1 = departaments.get(i).getAvgSallary();
                    BigDecimal oldSalaryInDepartament2 = departaments.get(j).getAvgSallary();

                    departaments.get(i).addWorker(departaments.get(j).getWorkers().get(0));
                    departaments.get(j).addWorker(departaments.get(i).getWorkers().get(0));
                    departaments.get(i).removeWorker(departaments.get(i).getWorkers().get(0));
                    departaments.get(j).removeWorker(departaments.get(j).getWorkers().get(0));

                    if (oldSalaryInDepartament1.compareTo(departaments.get(i).getAvgSallary()) < 0 && oldSalaryInDepartament2.compareTo(departaments.get(j).getAvgSallary()) < 0 ) {
                        System.out.println("Перевод повышает avg salary");
                    }

                    //System.out.println("средняя зп в отделе снабжения= "+departaments.get(0).getAvgSallary()+ "  средняяя зп в ИТ отделе = "+ departaments.get(1).getAvgSallary() );

                    departaments.get(i).addWorker(departaments.get(j).getWorkers().get(departaments.get(j).getCountWorkerInDepartament()-1));
                    departaments.get(j).addWorker(departaments.get(i).getWorkers().get(departaments.get(i).getCountWorkerInDepartament()-2));
                    departaments.get(i).removeWorker(departaments.get(i).getWorkers().get(departaments.get(i).getCountWorkerInDepartament()-2));
                    departaments.get(j).removeWorker(departaments.get(j).getWorkers().get(departaments.get(j).getCountWorkerInDepartament()-2));
                    //System.out.println("I= "+i+"  j=  "+j + "  k=  "+k+"           работников в  I департаменте=" + departaments.get(i).getCountWorkerInDepartament() +  "   в j ="+departaments.get(j).getCountWorkerInDepartament());
                }
            }
        }*/


