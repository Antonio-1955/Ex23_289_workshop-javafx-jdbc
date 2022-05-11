/*Projeto: Ex21_261_demo-dao-JDBC7
 */
package model.dao;

import java.util.List;
import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
    
    /* Operações responsáveis por inserir/atualizar/deletar/encontrar 
     * no banco de dados o que será enviado como parâmetro de entrada.
     */
    void insert(Seller obj);
    void update(Seller obj);
    void deleteById(Integer id);
    Seller findById(Integer id);
    List<Seller> findAll();
    List<Seller> findByDepartment(Department department);
}
