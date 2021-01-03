package principal;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Programa {
    public static void main(String[] args) throws ParseException {

        VendedorDao vendedorDao = DaoFactory.createVendedorDao();
        DepartamentoDao departamentoDao = DaoFactory.createDepartamentoDao();
        Scanner inputNumber = new Scanner(System.in);

        System.out.println("INSERT DEPARTAMENTO");
        Departamento departamento = new Departamento(null, "SAC");
        departamentoDao.insert(departamento);

        System.out.println("\nUPDATE DEPARTAMENTO");
        departamento.setId(5);
        departamento.setNome("SACO");
        departamentoDao.update(departamento);

        System.out.print("\nDELETE DEPARTAMENTO BY ID: ");
        int idDepartamento = inputNumber.nextInt();
        departamentoDao.deleteById(idDepartamento);

        System.out.println("\nLISTA DEPARTAMENTOS");
        List<Departamento> departamentos = departamentoDao.findAll();
        departamentos.forEach(System.out::println);

        System.out.println("\nINSERT VENDEDOR");
        Vendedor vendedorParaTestes = new Vendedor(
                null,"Vinicius Solon", "vini@email.com", "13/12/1997", 3000.0, new Departamento(1)
        );
        vendedorDao.insert(vendedorParaTestes);

        System.out.println("\nUPDATE VENDEDOR");
        vendedorParaTestes = vendedorDao.findById(7);
        vendedorParaTestes.setNome("Cleitinho");
        vendedorDao.update(vendedorParaTestes);

        System.out.print("\nDELETE BY ID: ");
        int idParaDelete = inputNumber.nextInt();
        vendedorDao.deleteById(idParaDelete);

        System.out.print("\nVENDEDOR BY ID: ");
        int idParaBusca = inputNumber.nextInt();
        Vendedor vendedorById = vendedorDao.findById(idParaBusca);
        System.out.println(vendedorById);

        System.out.println("\nLISTA TODOS OS VENDEDORES");
        List<Vendedor> vendedoresRegistradosNoBanco = vendedorDao.findAll();
        vendedoresRegistradosNoBanco.forEach(System.out::println);

        System.out.print("\nLISTA VENDEDORES BY DEPARTAMENTO: ");
        idDepartamento = inputNumber.nextInt();
        List<Vendedor> vendedoresByDepartamentoId = vendedorDao.findByDepartmentId(idDepartamento);
        vendedoresByDepartamentoId.forEach(System.out::println);
    }
}
