package org.one;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Не введены входные данные (путь к исходному файлу или выходному)");
        } else if (args[0].isEmpty()) {
            System.out.println("Ошибка ввода пути к входному файлу");
        } else if (args[1].isEmpty()) {
            System.out.println("Оишбка ввода пути к выходному файлу");
        } else {
            Map<String, Department> departments = getMapFromFile(args[0]);
            // printInConsole(departments);
            recursiveCheckOnTransfer(departments);
            if (checkWorkerOnTransfer(departments).isEmpty() || recursiveCheckOnTransfer(departments).isEmpty()) {
                saveInFile(new ArrayList(Collections.singleton("Подходящих переводов нет")), args[1]);
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
                String nameWorker = lineFromFile[0].trim();
                String secondNameWorker = lineFromFile[1].trim();
                String departmentWorker = lineFromFile[3].trim();
                BigDecimal salaryWorker = new BigDecimal(lineFromFile[2].trim());
                boolean key = true;
                if (salaryWorker.compareTo(new BigDecimal("0")) < 0) {
                    System.out.println("Некорректно введена зарплата в строке - " + i);
                    key = false;
                }
                if (nameWorker.length() < 1) {
                    System.out.println("Пропуск имени сотрудника в строке - " + i);
                    key = false;
                }
                if (secondNameWorker.length() < 1) {
                    System.out.println("Пропуск фамилии сотрудника в строке - " + i);
                    key = false;
                }
                if (departmentWorker.length() < 1) {
                    System.out.println("Пропуск департамента сотрудника в строке - " + i);
                    key = false;
                }

                if (!departments.containsKey(departmentWorker) && key) {
                    departments.put(departmentWorker, new Department(departmentWorker));
                }
                if (key) {
                    departments.get(lineFromFile[3].trim()).addWorker(new Worker(nameWorker, secondNameWorker, salaryWorker));
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
            System.out.println("Департамент: " + entry.getValue().nameDepartment + " // Средняя ЗП в департаменте: " + entry.getValue().getAvgSalary());
            for (Worker work : entry.getValue().getWorkers()) {
                System.out.println(work.getSecondname()+" "+work.getFirstname());
            }
            System.out.println();
        }
    }

    private static List recursiveCheckOnTransfer(Map<String, Department> departamentMap) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Department> deprt1 : departamentMap.entrySet()) {
            for (Map.Entry<String, Department> deprt2 : departamentMap.entrySet()) {
                if (deprt1.getValue().getName().equals(deprt2.getValue().getName())) {
                    continue;
                }
                int countWorkersInDepartment = deprt1.getValue().getWorkers().size();
                for (int i = 1; i < countWorkersInDepartment + 1; i++) {
                    List<List<Worker>> tempList = new ArrayList<>();
                    tempList = getCombinationFrom(deprt1.getValue().getWorkers(), 0, i);
                    for (List<Worker> work : tempList) {
                        BigDecimal avgSalaryInDeaOld1 = deprt1.getValue().getAvgSalary();
                        BigDecimal avgSalaryInDeaOld2 = deprt2.getValue().getAvgSalary();
                        BigDecimal avgSalaryInDeaNew1 = deprt1.getValue().getAvgSalaryWithoutList(work);
                        BigDecimal avgSalaryInDeaNew2 = deprt2.getValue().getAvgSalary(work);
                        String nameDepartment1 = deprt1.getValue().getName();
                        String nameDepartment2 = deprt2.getValue().getName();
                        if (avgSalaryInDeaNew1.compareTo(avgSalaryInDeaOld1) > 0 && avgSalaryInDeaNew2.compareTo(avgSalaryInDeaOld2) > 0) {
                            result.add("Сотрудников отдела " + nameDepartment1 + " : ");
                            for (Worker workerInfo : work) {
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

    private static List checkWorkerOnTransfer(Map<String, Department> departamentsMap) {
        List<String> resultReshuffle = new ArrayList<>();
        for (Map.Entry<String, Department> entry : departamentsMap.entrySet()) {
            for (Map.Entry<String, Department> entry2 : departamentsMap.entrySet()) {
                if (entry.getValue().getName().equals(entry2.getValue().getName())) {
                    continue;
                }
                for (Worker work : entry.getValue().getWorkers()) {
                    if (work.getSalary().compareTo(entry.getValue().getAvgSalary()) < 0 && (work.getSalary().compareTo(entry2.getValue().getAvgSalary()) > 0)) {
                        resultReshuffle.add("Сотрудника: " + work.getSecondname() + " из отдела "
                                + entry.getValue().getName() + "со средней ЗП отдела=" + entry.getValue().getAvgSalary()
                                + " , можно перевести в отдел: " + entry2.getValue().getName() + " со средней ЗП отдела="
                                + entry2.getValue().getAvgSalary() + ". После данного перевода средняя ЗП в отделе: "
                                + entry.getValue().getName() + " станет=" + entry.getValue().getAvgSalaryWithout(work)
                                + " , а в отделе " + entry2.getValue().getName() + " станет =" + entry2.getValue().getAvgSalaryWith(work));
                    }
                }
            }
        }
        return resultReshuffle;
    }

    private static List<List<org.one.Worker>> getCombinationFrom(List<Worker> objects, int index, int r){
        int n = objects.size();
        List<List<org.one.Worker>> result = new ArrayList<>();
        if (r==1){
            for (int i = index; i <= n-r; i++) {
                result.add(Collections.singletonList(objects.get(i)));
            }
        }else{
            for (int i = index; i <= n-r; i++) {
                Worker object = objects.get(i);
                List<List<org.one.Worker>> tmp = getCombinationFrom(objects, i+1, r-1);
                tmp.forEach(x -> result.add(join(object, x)));
            }
        }
        return result;
    }

    private static List<Worker> join(Worker first, List<Worker> other){
        List<Worker> joined = new ArrayList();
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
}



