package excel;

import javafx.scene.control.CheckBox;
import lombok.Getter;

@Getter
public class Employee {

    private final Integer id;
    private final String name;
    private final String surname;
    private final Integer base;
    private final Double coefficient;
    private final Double salary;
    private final CheckBox selected;

    public Employee(Integer id, String name, String surname, Integer base, Double coefficient) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.base = base;
        this.coefficient = coefficient;
        this.salary = calculateSalary();
        this.selected = new CheckBox();
    }

    private double calculateSalary(){
        return Math.round(base * coefficient);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        return sb.append(id)
                .append(" ")
                .append(name)
                .append(" ")
                .append(surname)
                .append(" ")
                .append(base)
                .append(" ")
                .append(coefficient)
                .toString();
    }
}