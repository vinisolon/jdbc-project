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

        Vendedor vendedorParaTestes = new Vendedor(
                "Vinicius Solon", "vini@email.com", "13/12/1997", 3000.0, new Departamento(1));
        vendedorDao.insert(vendedorParaTestes);

        List<Vendedor> vendedors = vendedorDao.findByDepartmentId(1);
        vendedors.stream().forEach(System.out::println);

        Vendedor seller = vendedorDao.findById(3);
        System.out.println(seller);

        vendedorDao.deleteById(5);

        List<Vendedor> sellers = vendedorDao.findAll();
        sellers.forEach(System.out::println);
    }
}
