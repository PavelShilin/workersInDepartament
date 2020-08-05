package org.one;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Department {
    String nameDepartment;
    private List<Worker> workers = new ArrayList<>();

    public Department(String depName) {
        this.nameDepartment = depName;
    }

    public String getName() {
        return nameDepartment;
    }

    public void addWorker(Worker workersInDepartment) {
        this.workers.add(workersInDepartment);
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public int getCountWorkerInDepartment() {
        return this.workers.size();
    }

    /*Получить среднюю зп отдела */
    public BigDecimal getAvgSalary() {
        BigDecimal summaZP = BigDecimal.valueOf(0);
        for (Worker workerInfo : this.workers) {
            summaZP = summaZP.add(workerInfo.getSalary());
        }
        if (getCountWorkerInDepartment() == 0) {
            throw new ArithmeticException("В отделе " + this.nameDepartment + " нет сотрудников");
        } else {
            return summaZP.divide(BigDecimal.valueOf(getCountWorkerInDepartment()), 2, RoundingMode.HALF_UP);
        }

    }

    /*Получить среднюю зп отдела с приплюсованным списком сотрудников*/
    public BigDecimal getAvgSalary(List<Worker> workerAdded) {
        BigDecimal sumSalary = BigDecimal.valueOf(0);
        ArrayList<Worker> result = new ArrayList<Worker>(workerAdded.size() + this.workers.size());
        result.addAll(this.workers);
        result.addAll(workerAdded);
        for (Worker workerInfo : result) {
            sumSalary = sumSalary.add(workerInfo.getSalary());
        }
        if (getCountWorkerInDepartment() == 0) {
            throw new ArithmeticException("В отделе " + this.nameDepartment + " нет сотрудников");
        } else {
            return sumSalary.divide(BigDecimal.valueOf(getCountWorkerInDepartment()), 2, RoundingMode.HALF_UP);
        }
    }

    /* Получить среднюю зарплату отдела department без списка сотрудников  */
    public BigDecimal getAvgSalaryWithoutList(List<Worker> customWorker) {
        BigDecimal sumSalary = BigDecimal.valueOf(0);
        if (!customWorker.isEmpty()) {
            ArrayList<Worker> result = new ArrayList<Worker>(this.workers.size() - customWorker.size());
            result.addAll(this.workers);
            for (Worker work : customWorker)
                result.remove(work);
            for (Worker workerInfo : result) {
                if (workerInfo.equals(customWorker)) {
                    continue;
                }
                sumSalary = sumSalary.add(workerInfo.getSalary());
            }
        } else {
            System.out.println("Ошибка подсчётаса средней ЗП отдела");
        }
        if (getCountWorkerInDepartment() == 0) {
            throw new ArithmeticException("В отделе " + this.nameDepartment + " нет сотрудников");
        } else {
            return sumSalary.divide(BigDecimal.valueOf(getCountWorkerInDepartment() - 1), 2, RoundingMode.HALF_UP);
        }
    }

    /* Получить среднюю зарплату отдела department без определёного сотрудника */
    public BigDecimal getAvgSalaryWithout(Worker customWorker) {
        BigDecimal summaZP = BigDecimal.valueOf(0);

        for (Worker workerInfo : this.workers) {
            if (workerInfo.equals(customWorker)) {
                continue;
            }
            summaZP = summaZP.add(workerInfo.getSalary());
        }
        if (getCountWorkerInDepartment() == 0) {
            throw new ArithmeticException("В отделе " + this.nameDepartment + " нет сотрудников");
        } else {
            return summaZP.divide(BigDecimal.valueOf(getCountWorkerInDepartment() - 1), 2, RoundingMode.HALF_UP);
        }
    }

    /* Получить среднюю зарплату отдела department c сотрудником worker*/
    public BigDecimal getAvgSalaryWith(Worker customWorker) {
        BigDecimal summaZP = BigDecimal.valueOf(0).add(customWorker.getSalary());
        for (Worker workerInfo : this.workers) {
            summaZP = summaZP.add(workerInfo.getSalary());
        }
        return summaZP.divide(BigDecimal.valueOf(getCountWorkerInDepartment() + 1), 2, RoundingMode.HALF_UP);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return Objects.equals(nameDepartment, that.nameDepartment) &&
                Objects.equals(workers, that.workers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameDepartment, workers);
    }
}
