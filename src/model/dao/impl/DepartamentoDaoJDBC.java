package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDao;
import model.entidades.Departamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDaoJDBC implements DepartamentoDao {

    private Connection connection;

    public DepartamentoDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Departamento departamento) {
        PreparedStatement stm = null;
        try {
            String sql = "INSERT INTO departamento (nome) VALUES (?)";
            stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, departamento.getNome());
            int linhasAfetadas = stm.executeUpdate();
            if (linhasAfetadas > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    int idGeradoAoInserir = rs.getInt(1);
                    departamento.setId(idGeradoAoInserir);
                    DB.closeResultSet(rs);
                }
                System.out.println("Sucesso ao inserir departamento! ID: " + departamento.getId());
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha foi afetada!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stm);
        }
    }

    @Override
    public void update(Departamento departamento) {
        PreparedStatement stm = null;
        try {
            String sql = "UPDATE departamento SET nome = ? WHERE id = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, departamento.getNome());
            stm.setInt(2, departamento.getId());
            int linhasAfetadas = stm.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Sucesso ao atualizar dados do departamento!");
            } else {
                System.out.println("Nenhuma linha foi afetada!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stm);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement stm = null;
        try {
            String sql = "DELETE FROM departamento WHERE id = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, id);

            int linhasAfetadas = stm.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Departamento deletado com sucesso!");
            } else {
                System.out.println("Departamento não encontrado!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stm);
        }
    }

    @Override
    public List<Departamento> findAll() {
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM departamento";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();
            List<Departamento> departamentos = new ArrayList<>();
            while (rs.next()) {
                departamentos.add(instanciaDepartamento(rs));
            }
            return departamentos;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(stm);
            DB.closeResultSet(rs);
        }
    }

    private Departamento instanciaDepartamento(ResultSet rs) throws SQLException {
        Departamento departamento = new Departamento();
        departamento.setId(rs.getInt(1));
        departamento.setNome(rs.getString(2));
        return departamento;
    }
}
