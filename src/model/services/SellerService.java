/*
 */
package model.services;

import java.util.ArrayList;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
    
 private SellerDao dao = DaoFactory.createSellerDao();
//==============================================================================    
    //Método para buscar o dados do banco de dados.
    public List <Seller> findAll() {
        
        return dao.findAll();
        
//        //Declarar os departamentos aqui significa 'MOCKAR' os dados, ou seja é apenas uma simulação.
//        List <Seller> list = new ArrayList<>();
//        list.add(new Seller(1,"Books"));
//        list.add(new Seller(2, "Computers"));
//        list.add(new Seller(3, "Electronics"));
//        return list;
    }
//==============================================================================  
    
    //Método p/ verificar se é preciso inserir ou atualizar um department no BD. 
    public void saveOrUpdate(Seller obj){
        
        if (obj.getId() == null){
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }
//==============================================================================    
    
    //Método para remover um 'Seller'.
    public void remove(Seller obj){
        
        dao.deleteById(obj.getId());
    }
}
