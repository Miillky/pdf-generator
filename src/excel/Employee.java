package excel;

import lombok.Getter;

@Getter
class Employee {

    private final Integer id;
    private final String name;
    private final String surname;
    private final Integer base;
    private final Double coefficient;

    public Employee(Integer id, String name, String surname, Integer base, Double coefficient) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.base = base;
        this.coefficient = coefficient;
    }
}