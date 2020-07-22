import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Objects;

public class Departament  {
    String nameDepartament;
    BigDecimal avgSallary;
   private ArrayList<Worker> workers = new ArrayList<>();

    public Departament(String depName){
        this.nameDepartament =  depName;
    }

    public String getName() {
        return nameDepartament;
    }

    public void addWorker (Worker workersInDepartament) {
        this.workers.add(workersInDepartament);
    }
    public void removeWorker(Worker workerOnRemove) {
        this.workers.remove(workerOnRemove);
    }


    public ArrayList<Worker> getWorkers() {
        return workers;
    }
    public int getCountWorkerInDepartament() {
        return this.workers.size();
    }

    public BigDecimal getAvgSallary (){
        BigDecimal summaZP = BigDecimal.valueOf(0);
                for (Worker workerInfo : this.workers) {
            summaZP = summaZP.add(workerInfo.getSalary());

        }
        if (getCountWorkerInDepartament() == 0){
            throw new ArithmeticException("В отделе "+ this.nameDepartament  +" нет сотрудников");
        } else{
            this.avgSallary = summaZP.divide(BigDecimal.valueOf(getCountWorkerInDepartament()),2,RoundingMode.HALF_UP);
            return this.avgSallary;
        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departament)) return false;
        Departament that = (Departament) o;
        return Objects.equals(nameDepartament, that.nameDepartament) &&
                Objects.equals(avgSallary, that.avgSallary) &&
                Objects.equals(workers, that.workers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameDepartament, avgSallary, workers);
    }
}
