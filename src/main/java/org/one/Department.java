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

    public void addWorker(Worker workersInDepartament) {
        this.workers.add(workersInDepartament);
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public int getCountWorkerInDepartament() {
        return this.workers.size();
    }

    public BigDecimal getAvgSallary() {
        BigDecimal summaZP = BigDecimal.valueOf(0);
        for (Worker workerInfo : this.workers) {
            summaZP = summaZP.add(workerInfo.getSalary());
        }
        if (getCountWorkerInDepartament() == 0) {
            throw new ArithmeticException("В отделе " + this.nameDepartment + " нет сотрудников");
        } else {
            return summaZP.divide(BigDecimal.valueOf(getCountWorkerInDepartament()), 2, RoundingMode.HALF_UP);
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
