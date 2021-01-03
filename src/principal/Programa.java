package principal;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entidades.Departamento;
import model.entidades.Vendedor;

import java.text.ParseException;
import java.util.List;

public class Programa {
    public static void main(String[] args) throws ParseException {

        VendedorDao vendedorDao = DaoFactory.createVendedorDao();

        System.out.println("INSERT VENDEDOR");
        Vendedor vendedorParaInsert = new Vendedor(
                null,"Vinicius Solon", "vini@email.com", "13/12/1997", 3000.0, new Departamento(1)
        );
        vendedorDao.insert(vendedorParaInsert);
/*
        System.out.println("\nDELETE BY ID");
        vendedorDao.deleteById(6);

        System.out.println("\nUPDATE VENDEDOR");
        vendedorDao.update(vendedorParaInsert);

        System.out.println("\nLISTA VENDEDORES BY ID");
        Vendedor vendedorById = vendedorDao.findById(6);
        System.out.println(vendedorById);

        System.out.println("\nLISTA VENDEDORES BY DEPARTAMENTO");
        List<Vendedor> vendedoresByDepartamentoId = vendedorDao.findByDepartmentId(1);
        vendedoresByDepartamentoId.forEach(System.out::println);

        System.out.println("\nLISTA TODOS OS VENDEDORES");
        List<Vendedor> vendedoresRegistradosNoBanco = vendedorDao.findAll();
        vendedoresRegistradosNoBanco.forEach(System.out::println);
*/
    }
}
