package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entidades.Department;
import model.entidades.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement stm = null;

        try {
            StringBuilder sql = new StringBuilder()
                    .append("insert into seller (name, email, birthdate, basesalary, fk_departmentid)")
                    .append(" values (?, ?, ?, ?, ?)");

            stm = connection.prepareStatement(sql.toString());
            stm.setString(1, seller.getName());
            stm.setString(2, seller.getEmail());
            stm.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            stm.setDouble(4, seller.getBaseSalary());
            stm.setInt(5, seller.getDepartment().getId());

            int rowsAffected = stm.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Sucesso insert seller!");
            } else {
                System.out.println("Falha insert seller!");
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stm);
        }
    }

    @Override
    public void update(Seller seller) {
        PreparedStatement stm = null;

        try {
            String sql = "update seller set name = ?, email = ?, birthdate = ?, basesalary = ? where id = ?";

            stm = connection.prepareStatement(sql);
            stm.setString(1, seller.getName());
            stm.setString(2, seller.getEmail());
            stm.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            stm.setDouble(4, seller.getBaseSalary());
            stm.setInt(5, seller.getId());

            int rowsAffected = stm.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Sucesso update seller!");
            } else {
                System.out.println("Falha update seller!");
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stm);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement stm = null;

        try {
            if(findById(id) != null) {
                String sql = "delete from seller where id = ?";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, id);

                int rowsAffected = stm.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Sucesso delete seller!");
                } else {
                    System.out.println("Falha delete seller!");
                }
            } else {
                System.out.println("ID não encontrado!");
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stm);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder()
                    .append("select s.*, d.name from seller s")
                    .append(" inner join department d on s.fk_departmentid = d.id")
                    .append(" where s.id = ?");

            stm = connection.prepareStatement(sql.toString());
            stm.setInt(1, id);
            rs = stm.executeQuery();

            if(rs.next()) {
                Department department = instanciaDepartment(rs);
                Seller seller = instanciaSeller(rs, department);
                return seller;
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stm);
            DB.closeResultSet(rs);
        }
        return null;
    }

    @Override
    public List<Seller> findAll() {
        List<Seller> list = new ArrayList<>();
        Statement stm = null;
        ResultSet rs = null;

        try {
            String sql = "select s.*, d.name from seller s inner join department d on s.fk_departmentid = d.id order by s.id";
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);

            while(rs.next()) {
                Department department = instanciaDepartment(rs);
                Seller seller = instanciaSeller(rs, department);
                list.add(seller);
            }
            if(!list.isEmpty()){
                return list;
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stm);
            DB.closeResultSet(rs);
        }
        return null;
    }

    @Override
    public List<Seller> findByDepartmentId(Integer departmentId) {
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            StringBuilder sql = new StringBuilder("select s.*, d.name from seller s")
                    .append(" join department d on s.fk_departmentid = d.id")
                    .append(" where s.fk_departmentid = ? order by s.name");

            stm = connection.prepareStatement(sql.toString());
            stm.setInt(1, departmentId);
            rs = stm.executeQuery();
            List<Seller> sellers = new ArrayList<>();
            Department department = null;

            while(rs.next()) {
                if(department == null){
                    department = instanciaDepartment(rs);
                }
                sellers.add(instanciaSeller(rs, department));
            }

            if(sellers.isEmpty() == false){
                return sellers;
            } else {
                System.out.println("Nenhum vendedor nesse departamento ou departamento não existe");
            }

        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stm);
            DB.closeResultSet(rs);
        }
        return null;
    }

    private Department instanciaDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("s.fk_departmentid"));
        department.setName(rs.getString("d.name"));
        return department;
    }

    private Seller instanciaSeller(ResultSet rs, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("s.id"));
        seller.setName(rs.getString("s.name"));
        seller.setEmail(rs.getString("s.email"));
        seller.setBirthDate(rs.getDate("s.birthdate"));
        seller.setBaseSalary(rs.getDouble("s.basesalary"));
        seller.setDepartment(department);
        return seller;
    }
}
