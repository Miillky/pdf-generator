package excel;

import lombok.Getter;

@Getter
class Employee {

    private Integer id;
    private String name;
    private String designation;

    public Employee(Integer id, String name, String designation) {
        this.id = id;
        this.name = name;
        this.designation = designation;
    }
}