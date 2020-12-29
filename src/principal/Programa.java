package principal;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entidades.Seller;

import java.text.ParseException;
import java.util.List;

public class Programa {
    public static void main(String[] args) throws ParseException {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        //Seller teste =
          //      new Seller("Vinicius Solon", "vini@email.com", "13/12/1997", 3000.0, new Department(1));
        //System.out.println(teste);
        //sellerDao.insert(teste);

        List<Seller> sellers = sellerDao.findByDepartmentId(1);
        sellers.stream().forEach(System.out::println);

        //Seller seller = sellerDao.findById(3);
        //System.out.println(seller);

        //sellerDao.deleteById(5);

        //List<Seller> sellers = sellerDao.findAll();
        //sellers.forEach(System.out::println);
    }
}
