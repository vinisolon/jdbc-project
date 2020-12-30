package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendedorDaoJDBC implements VendedorDao {

    private Connection connection;

    public VendedorDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Vendedor vendedor) {
        PreparedStatement stm = null;

        try {
            StringBuilder sql = new StringBuilder()
                    .append("INSERT INTO vendedor (nome, email, dataNascimento, salario, idDepartamento)")
                    .append(" VALUES (?, ?, ?, ?, ?)");

            stm = connection.prepareStatement(sql.toString());
            stm.setString(1, vendedor.getNome());
            stm.setString(2, vendedor.getEmail());
            stm.setDate(3, new java.sql.Date(vendedor.getDataNascimento().getTime()));
            stm.setDouble(4, vendedor.getSalario());
            stm.setInt(5, vendedor.getDepartment().getId());

            int rowsAffected = stm.executeUpdate();

            if(rowsAffected > 0) {
                System.out.println("Sucesso ao inserir vendedor!");
            } else {
                System.out.println("Falha ao inserir vendedor!");
            }
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stm);
        }
    }

    @Override
    public void update(Vendedor vendedor) {
        PreparedStatement stm = null;

        try {
            String sql = "UPDATE vendedor SET nome = ?, email = ?, dataNascimento = ?, salario = ? WHERE id = ?";

            stm = connection.prepareStatement(sql);
            stm.setString(1, vendedor.getNome());
            stm.setString(2, vendedor.getEmail());
            stm.setDate(3, new java.sql.Date(vendedor.getDataNascimento().getTime()));
            stm.setDouble(4, vendedor.getSalario());
            stm.setInt(5, vendedor.getId());

            int rowsAffected = stm.executeUpdate();

            if(rowsAffected > 0){
                System.out.println("Sucesso ao atualizar dados do vendedor!");
            } else {
                System.out.println("Falha ao atualizar dados do vendedor!");
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
                String sql = "DELETE FROM vendedor WHERE id = ?";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, id);

                int rowsAffected = stm.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Sucesso ao deletar vendedor!");
                } else {
                    System.out.println("Falha ao deletar vendedor!");
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
    public Vendedor findById(Integer id) {
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder()
                    .append("SELECT v.*, d.nome FROM vendedor v")
                    .append(" JOIN departamento d ON v.idDepartamento = d.id")
                    .append(" WHERE v.id = ?");

            stm = connection.prepareStatement(sql.toString());
            stm.setInt(1, id);
            rs = stm.executeQuery();

            if(rs.next()) {
                Departamento departamento = instanciaDepartment(rs);
                Vendedor vendedor = instanciaSeller(rs, departamento);
                return vendedor;
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
    public List<Vendedor> findAll() {
        List<Vendedor> list = new ArrayList<>();
        Statement stm = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT v.*, d.nome FROM vendedor v JOIN departamento d ON v.idDepartamento = d.id ORDER BY v.id";
            stm = connection.createStatement();
            rs = stm.executeQuery(sql);

            while(rs.next()) {
                Departamento departamento = instanciaDepartment(rs);
                Vendedor vendedor = instanciaSeller(rs, departamento);
                list.add(vendedor);
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
    public List<Vendedor> findByDepartmentId(Integer departmentId) {
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            StringBuilder sql = new StringBuilder("SELECT v.*, d.nome FROM vendedor v")
                    .append(" JOIN departamento d ON v.idDepartamento = d.id")
                    .append(" WHERE v.idDepartamento = ? ORDER BY v.name");

            stm = connection.prepareStatement(sql.toString());
            stm.setInt(1, departmentId);
            rs = stm.executeQuery();
            List<Vendedor> vendedors = new ArrayList<>();
            Departamento departamento = null;

            while(rs.next()) {
                if(departamento == null){
                    departamento = instanciaDepartment(rs);
                }
                vendedors.add(instanciaSeller(rs, departamento));
            }

            if(vendedors.isEmpty() == false){
                return vendedors;
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

    private Departamento instanciaDepartment(ResultSet rs) throws SQLException {
        Departamento departamento = new Departamento();
        departamento.setId(rs.getInt("v.idDepartamento"));
        departamento.setNome(rs.getString("d.nome"));
        return departamento;
    }

    private Vendedor instanciaSeller(ResultSet rs, Departamento departamento) throws SQLException {
        Vendedor vendedor = new Vendedor();
        vendedor.setId(rs.getInt("v.id"));
        vendedor.setNome(rs.getString("v.nome"));
        vendedor.setEmail(rs.getString("v.email"));
        vendedor.setDataNascimento(rs.getDate("v.dataNascimento"));
        vendedor.setSalario(rs.getDouble("v.salario"));
        vendedor.setDepartment(departamento);
        return vendedor;
    }
}
