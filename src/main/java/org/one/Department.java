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


    /*Получить среднюю зп отдела */
    public BigDecimal getAvgSalary() {
        if (this.workers.size() == 0) {
            throw new ArithmeticException("В отделе " + this.nameDepartment + " нет сотрудников");
        } else {
            return getAverage(this.workers);
        }
    }

    /*Получить среднюю зп отдела с приплюсованным списком сотрудников*/
    public BigDecimal getAvgSalary(List<Worker> workerAdded) {
        ArrayList<Worker> result = new ArrayList<>(this.workers);
        result.addAll(workerAdded);
        return getAverage(result);
    }

    /* Получить среднюю зарплату отдела department без списка сотрудников  */
    public BigDecimal getAvgSalaryWithoutList(List<Worker> withoutWorkers) {
        ArrayList<Worker> result = new ArrayList<>(this.workers);
        result.removeAll(withoutWorkers);
        return getAverage(result);
    }

    private BigDecimal getAverage(List<Worker> workers) {
        if (!(workers.size() < 1)) {
            BigDecimal sum = BigDecimal.valueOf(0);
            for (Worker workerInfo : workers) {
                sum = sum.add(workerInfo.getSalary());
            }
            return sum.divide(BigDecimal.valueOf(workers.size()), 2, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
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
