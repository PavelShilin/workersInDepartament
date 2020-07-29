package org.one;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Не найден путь к файлу");
        } else {
            Map<String, Departament> departaments = new HashMap<String, Departament>();
            getMapFromFile(args, departaments);
            printInConsole(departaments);
            saveInFile(checkWorkerOnTransfer(departaments), args);
        }
    }


    private static void getMapFromFile(String[] args, Map<String, Departament> customDepartaments) {
        try (BufferedReader bufferedReader = new BufferedReader(
                new FileReader(args[0]))) {
            String currentLine;
            int i = 0;
            while (((currentLine = bufferedReader.readLine()) != null)) {
                i++;
                String[] lineFromFile = currentLine.trim().split("/");
                if (lineFromFile.length != 4 || lineFromFile[0].equals(" ") || lineFromFile[1].equals(" ") || lineFromFile[2].equals(" ") || lineFromFile[3].equals(" ") || Float.parseFloat(lineFromFile[2].trim()) <= 0) {
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

    private static void printInConsole(Map<String, Departament> departamentMap) {
        for (Map.Entry<String, Departament> entry : departamentMap.entrySet()) {
            System.out.println("Департамент: " + entry.getValue().nameDepartament + " // Средняя ЗП в департаменте: " + entry.getValue().getAvgSallary());
            for (Worker work : entry.getValue().getWorkers()) {
                System.out.println(work.getSecondname()+" "+work.getFirstname());
            }
            System.out.println();
        }
    }

    private static ArrayList checkWorkerOnTransfer(Map<String, Departament> departamentsMap) {
        ArrayList<String> resultReshuffle = new ArrayList<>();
        for (Map.Entry<String, Departament> entry : departamentsMap.entrySet()) {
            for (Map.Entry<String, Departament> entry2 : departamentsMap.entrySet()) {
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

    private static void saveInFile(ArrayList<String> result, String[] args) {
        try (OutputStream f = new FileOutputStream(args[1], true);
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



