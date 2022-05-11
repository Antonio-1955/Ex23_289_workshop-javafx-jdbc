/*Projeto: Ex21_261_demo-dao-JDBC7
 */
package model.dao.impl; //impl = implementação
//===============================================================================

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.util.List;
import model.dao.SellerDao;
import model.entities.Seller;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import model.entities.Department;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.sql.Statement;
import java.sql.Date;
//import com.mysql.jdbc.Statement;
//==============================================================================

//Esta é uma implementação JDBC da interface SellerDao.
public class SellerDaoJDBC implements SellerDao {

    //Atributo para fazer a conexão
    private Connection conn;
//==============================================================================

    //Construtor
    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }
//==============================================================================

    //Métodos inserir dados no o banco de dados
    @Override
    public void insert(Seller obj) {
        
        PreparedStatement st = null;
        try {
            
            st = conn.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) " 
                    + "VALUES " 
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartent().getId());
            
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            }
            else {
                throw new DbException("ERRO INESPERADO! Nenhuma linha foi afetada!");
            }
        } 
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }
//==============================================================================

    //Métodos atualizar dados no o banco de dados
    @Override
    public void update(Seller obj) {

        PreparedStatement st = null;
        try {
            
            st = conn.prepareStatement(
                    "UPDATE seller " 
                    + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " 
                    + "WHERE Id = ?");
            
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartent().getId());
            st.setInt(6, obj.getId());
            
            st.executeUpdate();
            
        } 
        catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }
    }
//==============================================================================

    //Métodos deletar dados no o banco de dados
    @Override
    public void deleteById(Integer id) {
        
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
            
            st.setInt(1, id);
            
            st.executeUpdate();
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            DB.closeStatement(st);
        }

    }
//==============================================================================

    //Métodos pesquisar dados no o banco de dados
    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ? ");

            st.setInt(1, id);
            rs = st.executeQuery();

            /* Como o 'rs' está na posição '0', que não contém objeto, o 'if'
             * testa se o 'executeQuery()' retornou algun registro*/
            if (rs.next()) {

                //A instanciação abaixo foi transformada no método instantiateDepartment().
                //Department dep = new Department();
                //dep.setId(rs.getInt("DepartmentId"));
                //dep.setName(rs.getString("DepName"));
                //Instancia o Departamento (Department)
                Department dep = instantiateDepartment(rs);

                //A instanciação abaixo foi transformada no método instantiateSeller().
                //Seller obj = new Seller();
                //obj.setId(rs.getInt("Id"));
                //obj.setName(rs.getString("Name"));
                //obj.setEmail(rs.getString("Email"));
                //obj.setBaseSalary(rs.getDouble("BaseSalary"));
                //obj.setBirthDate(rs.getDate("BirthDate"));
                //obj.setDepartent(dep);
                //return obj;

                //Instancia o Vendedor (Seller)
                Seller obj = instantiateSeller(rs, dep);
                return obj;

            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
//==============================================================================

    //Método para listar dados do banco de dados
    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " 
                    + "FROM seller INNER JOIN department " 
                    + " ON seller.DepartmentId = department.Id "
                    + " ORDER BY Name");

            rs = st.executeQuery();

            List<Seller> list = new ArrayList();
            Map<Integer, Department> map = new HashMap<>();
            
            while (rs.next()) {
                
                Department dep = map.get(rs.getInt("DepartmentId"));
                
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                //Instancia o Vendedor (Seller)
                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);

            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
//==============================================================================
    
    /* TRATAR O PROPAGAR EXCEÇÕES FOI DISCUTIDO NO CAPÍTULO DE EXECÕES.
     * NOTA: O compilador aqui indicava que o método poderia gerar uma 
     * SQLException que deveria ser tratada, mas como ela já está sendo
     * tratada acima no método 'findById()', aqui ela foi simplesmente
     * propagada (throws SQLException).
     */
    //Método para instanciar Departamento (Department)
    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
//==============================================================================    

    /* TRATAR O PROPAGAR EXCEÇÕES FOI DISCUTIDO NO CAPÍTULO DE EXECÕES.
     * NOTA: O compilador aqui indicava que o método poderia gerar uma 
     * SQLException que deveria ser tratada, mas como ela já está sendo
     * tratada acima no método 'findById()', aqui ela foi simplesmente
     * propagada (throws SQLException)
     */
    //Método para instanciar o Vendedor (Seller)
    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartent(dep);
        return obj;
    }
//==============================================================================    

    //Método para encontrar vendedores com o mesmo Department
    @Override
    public List<Seller> findByDepartment(Department department) {
        
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " 
                    + "FROM seller INNER JOIN department " 
                    + " ON seller.DepartmentId = department.Id "
                    + " WHERE DepartmentId = ? "
                    + " ORDER BY Name");

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> list = new ArrayList();
            Map<Integer, Department> map = new HashMap<>();
            
            while (rs.next()) {
                
                Department dep = map.get(rs.getInt("DepartmentId"));
                
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                //Instancia o Vendedor (Seller)
                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);

            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

}
