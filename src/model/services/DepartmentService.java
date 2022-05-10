/*
 */
package model.services;

import java.util.ArrayList;
import java.util.List;
import model.entities.Department;

public class DepartmentService {
//==============================================================================    
    //Método para buscar o dados do banco de dados.
    public List <Department> findAll() {
        
        //Declarar os departamentos aqui significa 'MOCKAR' os dados, ou seja é apenas uma simulação.
        List <Department> list = new ArrayList<>();
        list.add(new Department(1,"Books"));
        list.add(new Department(2, "Computers"));
        list.add(new Department(3, "Electronics"));
        return list;
    }
//==============================================================================    
    
}
