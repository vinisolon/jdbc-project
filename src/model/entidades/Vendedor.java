package model.entidades;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Vendedor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;
    private String email;
    private Date dataNascimento;
    private Double salario;
    private Departamento departamento;

    public Vendedor(){}

    //Completo
    public Vendedor(Integer id, String nome, String email, String dataNascimento, Double salario, Departamento departamento) throws ParseException {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimento);
        this.salario = salario;
        this.departamento = departamento;
    }

    //Sem ID
    public Vendedor(String nome, String email, String dataNascimento, Double salario, Departamento departamento) throws ParseException {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimento);
        this.salario = salario;
        this.departamento = departamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Departamento getDepartment() {
        return departamento;
    }

    public void setDepartment(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vendedor vendedor = (Vendedor) o;
        return id.equals(vendedor.id);
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
                .append(nome)
                .append(" | E-mail: ")
                .append(email)
                .append(" | Data de nascimento: ")
                .append(new SimpleDateFormat("dd/MM/yyyy").format(dataNascimento))
                .append(" | Salário: ")
                .append(salario)
                .append(" | Departamento -> ")
                .append(departamento);
        return printSeller.toString();
    }
}
