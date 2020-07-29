package org.one;

import java.math.BigDecimal;
import java.util.Objects;

public class Worker {
    private String firstname;
    private String secondname;
    private BigDecimal salary;


    public Worker(String firstname, String secondname, BigDecimal salary) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.salary = salary;

    }

    public String getFirstname() {
        return firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public BigDecimal getSalary() {
        return salary;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Worker)) return false;
        Worker worker = (Worker) o;
        return Objects.equals(firstname, worker.firstname) &&
                Objects.equals(secondname, worker.secondname) &&

                Objects.equals(salary, worker.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, secondname, salary);
    }
}
