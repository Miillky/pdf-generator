package excel;

import lombok.Getter;

@Getter
public class Employee {


    private final Integer id;
    private final String name;
    private final String surname;
    private final Integer base;
    private final Double coefficient;
    private static boolean selected;

    public Employee(Integer id, String name, String surname, Integer base, Double coefficient) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.base = base;
        this.coefficient = coefficient;
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