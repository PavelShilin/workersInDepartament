package org.one;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            Map<String, Department> departments = getMapFromFile(args[0]);
            printInConsole(departments);
            if (checkWorkerOnTransfer(departments).isEmpty()) {
                saveInFile(new ArrayList(Collections.singleton("Подходящих переводов нет")), args[1]);
            }
            saveInFile(checkWorkerOnTransfer(departments), args[1]);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Не введён путь к входному/выходному файлу ");
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
                BigDecimal sallarySotr = new BigDecimal(lineFromFile[2].trim());
                boolean key = true;
                if (sallarySotr.compareTo(new BigDecimal("0")) < 0) {
                    System.out.println("Некорректно введена зарплата в строке - " + i);
                    key = false;
                }
                if (nameSortr.length() < 1) {
                    System.out.println("Пропуск имени сотрудника в строке - " + i);
                    key = false;
                }
                if (secondNameSortr.length() < 1) {
                    System.out.println("Пропуск фамилии сотрудника в строке - " + i);
                    key = false;
                }
                if (departmentSotr.length() < 1) {
                    System.out.println("Пропуск департамента сотрудника в строке - " + i);
                    key = false;
                }

                if (!departments.containsKey(departmentSotr) && key) {
                    departments.put(departmentSotr, new Department(departmentSotr));
                }
                if (key) {
                    departments.get(lineFromFile[3].trim()).addWorker(new Worker(nameSortr, secondNameSortr, sallarySotr));
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка считывания файла " + e.getMessage());
        }catch (NumberFormatException ex) {
            System.out.println("Зарплата введена не верно " + ex.getMessage());
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
                        resultReshuffle.add("Сотрудника: " + work.getSecondname() + " из отдела "
                                + entry.getValue().getName() + "со средней ЗП отдела=" + entry.getValue().getAvgSallary()
                                + " , можно перевести в отдел: " + entry2.getValue().getName() + " со средней ЗП отдела="
                                + entry2.getValue().getAvgSallary() + ". После данного перевода средняя ЗП в отделе: "
                                + entry.getValue().getName() + " станет=" + entry.getValue().getAvgSallaryWithout(work)
                                + " , а в отделе " + entry2.getValue().getName() + " станет =" + entry2.getValue().getAvgSallaryWith(work));
                    }
                }
            }
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



