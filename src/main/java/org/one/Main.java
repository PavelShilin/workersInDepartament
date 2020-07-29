package org.one;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        if (args[0].isEmpty()) {
            System.out.println("Введен неверный путь/имя к файлу с входными данными");
        } else if (args[1].isEmpty()) {
            System.out.println("Введен неверный путь/имя к файлу с выходными данными");
        } else {
            Map<String, Department> departments = getMapFromFile(args[0]);
            printInConsole(departments);
            saveInFile(checkWorkerOnTransfer(departments), args[1]);
        }
    }


    private static Map<String, Department> getMapFromFile(String pathAndNameFile) {
        Map<String, Department> departments = new HashMap<String, Department>();
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(pathAndNameFile))) {
            String currentLine;
            int i = 0;
            while (((currentLine = bufferedReader.readLine()) != null)) {
                i++;
                String[] lineFromFile = currentLine.trim().split("/");
                String nameSortr = lineFromFile[0].trim();
                String secondNameSortr = lineFromFile[1].trim();
                String departmentSotr = lineFromFile[3].trim();
                try {BigDecimal sallarySotr = new BigDecimal(lineFromFile[2].trim());}
                    catch (NumberFormatException e){
                    System.out.println("dss");
                }

                if (lineFromFile.length != 4 || lineFromFile[0].equals(" ") || lineFromFile[1].equals(" ") || lineFromFile[2].equals(" ") || lineFromFile[3].equals(" ")) {
                    System.out.println("Ошибка: в строке - " + i);
                } else {
                    if (!departments.containsKey(lineFromFile[3].trim())) {
                        departments.put(lineFromFile[3].trim(), new Department(lineFromFile[3].trim()));
                    }
                  //  departments.get(lineFromFile[3].trim()).addWorker(new Worker(lineFromFile[0].trim(), lineFromFile[1].trim()));
                }
            }
        } catch (IOException  e) {
            System.out.println("Ошибка считывания файла или зарплата введена не верно " + e.getMessage());
        }
        return departments;
    }

    private static void printInConsole(Map<String, Department> departamentMap) {
        for (Map.Entry<String, Department> entry : departamentMap.entrySet()) {
            System.out.println("Департамент: " + entry.getValue().nameDepartment + " // Средняя ЗП в департаменте: " + entry.getValue().getAvgSallary());
            for (Worker work : entry.getValue().getWorkers()) {
                System.out.println(work.getSecondname()+" "+work.getFirstname());
            }
            System.out.println();
        }
    }

    private static List checkWorkerOnTransfer(Map<String, Department> departamentsMap) {
        List<String> resultReshuffle = new ArrayList<>();
        for (Map.Entry<String, Department> entry : departamentsMap.entrySet()) {
            for (Map.Entry<String, Department> entry2 : departamentsMap.entrySet()) {
                if (entry.getValue().getName().equals(entry2.getValue().getName())) {
                    continue;
                }
                for (Worker work : entry.getValue().getWorkers()) {
                    if (work.getSalary().compareTo(entry.getValue().getAvgSallary()) < 0 && (work.getSalary().compareTo(entry2.getValue().getAvgSallary()) > 0)) {
                        resultReshuffle.add("Сотрудника: " + work.getSecondname() + " можно перевести в отдел: " + entry2.getValue().getName());
                    }
                }
            }
        }
        if (resultReshuffle.isEmpty()) {
            resultReshuffle.add("Подходящих под условие переводов нет");
        }
        return resultReshuffle;
    }

    private static void saveInFile(List<String> result, String pathAndNameFile) {
        try (OutputStream f = new FileOutputStream(pathAndNameFile, true);
             OutputStreamWriter writer = new OutputStreamWriter(f)) {
            for (String line : result) {
                writer.write(line);
                writer.write(System.getProperty("line.separator"));
            }
        } catch (IOException ex) {
            System.out.println("Ошибка записи в файл  " + ex.getMessage());
        }
    }
}



