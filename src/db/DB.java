/*Projeto: Ex21_261_demo-dao-JDBC7
 *Classe DB com métodos personalizados DbException.
 *Obter e fechar uma conexãodom o banco de dados.
 */
package db;

import java.sql.Connection;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {
    
    //Atributo ou variável de instância da classe 'Connection'.
    private static Connection conn = null;
    
    //Método para ABRIR a conexão com o banco de dados.
    public static Connection getConnection(){
        
        if(conn == null){
             try {
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
                
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
        return conn;
    }
    
    //Método para FECHAR a conexão com o banco de dados.
    public static void closeConnection(){
        
        if(conn != null){
            try {
              conn.close();  
            }catch(SQLException e){
                throw new DbException(e.getMessage());
            }
            
        }
    }
    
    //Método auxiliar para obter no arquivo 'db.properties' as propriedades de conexão.
    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
            
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }
    
    public static void closeStatement(Statement st) {
        if(st != null){
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
    
     public static void closeResultSet(ResultSet rs) {
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
