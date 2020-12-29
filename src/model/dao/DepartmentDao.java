package model.dao;

import model.entidades.Department;

import java.util.List;

public interface DepartmentDao {
    void insert(Department department);
    void update(Department department);
    void deleteById(Integer id);
    List<Department> findAll();
}
