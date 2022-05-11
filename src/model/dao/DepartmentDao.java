/*Projeto: Ex21_261_demo-dao-JDBC7
 */
package model.dao;

import java.util.List;
import model.entities.Department;

public interface DepartmentDao {
    
    /*Operações resp. por inserir/atualizar/deletar/encontrar no banco de dados 
     *o que será enviado como parâmetro de entrada.*/
    void insert(Department obj);
    void update(Department obj);
    void deleteById(Integer id);
    Department findById(Integer id);
    List<Department> findAll();
}
