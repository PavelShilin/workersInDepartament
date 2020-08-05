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

    public BigDecimal getAvgSallary(List<Worker> workerAdded) {
        BigDecimal summaZP = BigDecimal.valueOf(0);
        ArrayList<Worker> result = new ArrayList<Worker>(workerAdded.size() + this.workers.size());
        result.addAll(this.workers);
        result.addAll(workerAdded);
        for (Worker workerInfo : result) {
            summaZP = summaZP.add(workerInfo.getSalary());
        }
        if (getCountWorkerInDepartament() == 0) {
            throw new ArithmeticException("В отделе " + this.nameDepartment + " нет сотрудников");
        } else {
            return summaZP.divide(BigDecimal.valueOf(getCountWorkerInDepartament()), 2, RoundingMode.HALF_UP);
        }
    }

    /* Получить среднюю зарплату отдела department без сотрудника worker*/
    public BigDecimal getAvgSallaryWithoutList(List<Worker> customWorker) {
        BigDecimal summaZP = BigDecimal.valueOf(0);
        ArrayList<Worker> result = new ArrayList<Worker>(this.workers.size() - customWorker.size() );
        result.addAll(this.workers);
        if (!customWorker.isEmpty()) {
            for (Worker work : customWorker)
                result.remove(work);
        } else {
            System.out.println("Ошибка подсчётаса средней ЗП отдела");
        }
        for (Worker workerInfo : result) {
            if (workerInfo.equals(customWorker)) {
                continue;
            }
            summaZP = summaZP.add(workerInfo.getSalary());
        }
        if (getCountWorkerInDepartament() == 0) {
            throw new ArithmeticException("В отделе " + this.nameDepartment + " нет сотрудников");
        } else {
            return summaZP.divide(BigDecimal.valueOf(getCountWorkerInDepartament() - 1), 2, RoundingMode.HALF_UP);
        }
    }

    /* Получить среднюю зарплату отдела department без сотрудников List worker*/
    public BigDecimal getAvgSallaryWithout(Worker customWorker) {
        BigDecimal summaZP = BigDecimal.valueOf(0);

        for (Worker workerInfo : this.workers) {
            if (workerInfo.equals(customWorker)) {
                continue;
            }
            summaZP = summaZP.add(workerInfo.getSalary());
        }
        if (getCountWorkerInDepartament() == 0) {
            throw new ArithmeticException("В отделе " + this.nameDepartment + " нет сотрудников");
        } else {
            return summaZP.divide(BigDecimal.valueOf(getCountWorkerInDepartament() - 1), 2, RoundingMode.HALF_UP);
        }
    }

    /* Получить среднюю зарплату отдела department c сотрудником worker*/
    public BigDecimal getAvgSallaryWith(Worker customWorker) {
        BigDecimal summaZP = BigDecimal.valueOf(0).add(customWorker.getSalary());
        for (Worker workerInfo : this.workers) {
            summaZP = summaZP.add(workerInfo.getSalary());
        }
        return summaZP.divide(BigDecimal.valueOf(getCountWorkerInDepartament() + 1), 2, RoundingMode.HALF_UP);
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
