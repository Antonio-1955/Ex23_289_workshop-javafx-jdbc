/*
 */
package model.services;

import java.util.ArrayList;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
    
 private DepartmentDao dao = DaoFactory.createDepartmentDao();
//==============================================================================    
    //Método para buscar o dados do banco de dados.
    public List <Department> findAll() {
        
        return dao.findAll();
        
//        //Declarar os departamentos aqui significa 'MOCKAR' os dados, ou seja é apenas uma simulação.
//        List <Department> list = new ArrayList<>();
//        list.add(new Department(1,"Books"));
//        list.add(new Department(2, "Computers"));
//        list.add(new Department(3, "Electronics"));
//        return list;
    }
//==============================================================================  
    
    //Método p/ verificar se é preciso inserir ou atualizar um department no BD. 
    public void saveOrUpdate(Department obj){
        
        if (obj.getId() == null){
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }
//==============================================================================    
    
}
