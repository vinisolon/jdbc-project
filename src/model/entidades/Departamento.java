package model.entidades;

import java.io.Serializable;

public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String nome;

    public Departamento(){}

    public Departamento(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Departamento(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        StringBuilder printaDepartamento = new StringBuilder()
                .append("ID: ")
                .append(id)
                .append(" | Nome: ")
                .append(nome);
        return printaDepartamento.toString();
    }
}
