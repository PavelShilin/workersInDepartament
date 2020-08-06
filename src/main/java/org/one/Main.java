package org.one;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Не введены входные данные (путь к исходному файлу или выходному)");
        } else {
            Map<String, Department> departments = getMapFromFile(args[0]);
            printInConsole(departments);
            recursiveCheckOnTransfer(departments);
            if (recursiveCheckOnTransfer(departments).isEmpty()) {
                saveInFile(new ArrayList<>(Collections.singleton("Подходящих переводов нет")), args[1]);
            }
            saveInFile(recursiveCheckOnTransfer(departments), args[1]);
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
                if (checkLine(lineFromFile, i)) {
                    String nameWorker = lineFromFile[0].trim();
                    String secondNameWorker = lineFromFile[1].trim();
                    BigDecimal salaryWorker = new BigDecimal(lineFromFile[2].trim());
                    String departmentWorker = lineFromFile[3].trim();
                    if (!departments.containsKey(departmentWorker)) {
                        departments.put(departmentWorker, new Department(departmentWorker));
                    }
                    departments.get(lineFromFile[3].trim()).addWorker(new Worker(nameWorker, secondNameWorker, salaryWorker));
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка считывания файла " + e.getMessage());
        }
        return departments;
    }


    private static void printInConsole(Map<String, Department> departmentMap) {
        for (Department depart1 : departmentMap.values()) {
            System.out.println("Департамент: " + depart1.nameDepartment + " // Средняя ЗП в департаменте: " + depart1.getAvgSalary());
            for (Worker work : depart1.getWorkers()) {
                System.out.println(work.getSecondname() + " " + work.getFirstname());
            }
            System.out.println();
        }
    }


    private static List<String> recursiveCheckOnTransfer(Map<String, Department> departmentMap) {
        List<String> result = new ArrayList<>();
        for (Department depart1 : departmentMap.values()) {
            for (Department depart2 : departmentMap.values()) {
                if (depart1.getName().equals(depart2.getName())) {
                    continue;
                }
                int countWorkersInDepartment = depart1.getWorkers().size();
                for (int i = 1; i < countWorkersInDepartment + 1; i++) {
                    List<List<Worker>> tempList;
                    tempList = getCombinationFrom(depart1.getWorkers(), 0, i);
                    for (List<Worker> worker : tempList) {
                        BigDecimal avgSalaryInDeaOld1 = depart1.getAvgSalary();
                        BigDecimal avgSalaryInDeaOld2 = depart2.getAvgSalary();
                        BigDecimal avgSalaryInDeaNew1 = depart1.getAvgSalaryWithoutList(worker);
                        BigDecimal avgSalaryInDeaNew2 = depart2.getAvgSalary(worker);
                        String nameDepartment1 = depart1.getName();
                        String nameDepartment2 = depart2.getName();
                        if (avgSalaryInDeaNew1.compareTo(avgSalaryInDeaOld1) > 0 && avgSalaryInDeaNew2.compareTo(avgSalaryInDeaOld2) > 0) {
                            result.add("Сотрудников отдела " + nameDepartment1 + " : ");
                            for (Worker workerInfo : worker) {
                                result.add(workerInfo.getSecondname() + "/");
                            }
                            result.add("\n Можно перевести в отдел " + nameDepartment2 + " При этом переводе средняя зп в отделе: " + nameDepartment1 + "  увеличится на "
                                    + avgSalaryInDeaNew1.subtract(avgSalaryInDeaOld1) + " руб. а , в отделе " + nameDepartment2
                                    + "увеличится на " + avgSalaryInDeaNew2.subtract(avgSalaryInDeaOld2)
                                    + "\n средняя зп в отделе:" + nameDepartment1 + ", сейчас - " + avgSalaryInDeaOld1 + " руб. а станет - " + avgSalaryInDeaNew1 + " руб."
                                    + "\n средняя зп в отделе:" + nameDepartment2 + " сейчас - " + avgSalaryInDeaOld2 + " руб. а станет - " + avgSalaryInDeaNew2 + " руб.\n");
                        }
                    }
                }
            }
        }
        return result;
    }

    private static boolean checkLine(String[] line, int numberLine) {
        if (line.length < 4) {
            System.out.println("Нехватает входных данных в строке - " + numberLine);
            return false;
        }
        if (!isNumeric(line[2])) {
            System.out.println("Некорректно введена зарплата в строке - " + numberLine);
            return false;
        }
        if (line[2].compareTo(String.valueOf(new BigDecimal(BigInteger.ZERO))) < 0) {
            System.out.println("Некорректно введена зарплата в строке - " + numberLine);
            return false;
        }
        if (line[0].length() < 1) {
            System.out.println("Пропуск имени сотрудника в строке - " + numberLine);
            return false;
        }
        if (line[1].length() < 1) {
            System.out.println("Пропуск фамилии сотрудника в строке - " + numberLine);
            return false;
        }
        if (line[3].length() < 1) {
            System.out.println("Пропуск департамента  в строке - " + numberLine);
            return false;
        }
        return true;
    }


    private static List<List<org.one.Worker>> getCombinationFrom(List<Worker> objects, int index, int r) {
        int n = objects.size();
        List<List<org.one.Worker>> result = new ArrayList<>();
        if (r == 1) {
            for (int i = index; i <= n - r; i++) {
                result.add(Collections.singletonList(objects.get(i)));
            }
        } else {
            for (int i = index; i <= n - r; i++) {
                Worker object = objects.get(i);
                List<List<org.one.Worker>> tmp = getCombinationFrom(objects, i + 1, r - 1);
                tmp.forEach(x -> result.add(join(object, x)));
            }
        }
        return result;
    }

    private static List<Worker> join(Worker first, List<Worker> other) {
        List<Worker> joined = new ArrayList<>();
        joined.add(first);
        joined.addAll(other);
        return joined;
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

    public static boolean isNumeric(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}



