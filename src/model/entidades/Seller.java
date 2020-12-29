package model.entidades;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String email;
    private Date birthDate;
    private Double baseSalary;
    private Department department;

    public Seller(){}

    //Completo
    public Seller(Integer id, String name, String email, String birthDate, Double baseSalary, Department department) throws ParseException {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
        this.baseSalary = baseSalary;
        this.department = department;
    }

    //Sem ID
    public Seller(String name, String email, String birthDate, Double baseSalary, Department department) throws ParseException {
        this.name = name;
        this.email = email;
        this.birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthDate);
        this.baseSalary = baseSalary;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seller seller = (Seller) o;
        return id.equals(seller.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder printSeller = new StringBuilder()
                .append("ID: ")
                .append(id)
                .append(" | Nome: ")
                .append(name)
                .append(" | E-mail: ")
                .append(email)
                .append(" | Data de nascimento: ")
                .append(new SimpleDateFormat("dd/MM/yyyy").format(birthDate))
                .append(" | Salário: ")
                .append(baseSalary)
                .append(" | Departamento -> ")
                .append(department);
        return printSeller.toString();
    }
}
