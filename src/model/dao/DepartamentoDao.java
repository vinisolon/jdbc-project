package model.dao;

import model.entidades.Departamento;

import java.util.List;

public interface DepartamentoDao {
    void insert(Departamento departamento);
    void update(Departamento departamento);
    void deleteById(Integer id);
    List<Departamento> findAll();
}
