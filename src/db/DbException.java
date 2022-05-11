/*Projeto: Ex21_261_demo-dao-JDBC7
 *Exceção personalizada DbException*/
package db;

public class DbException extends RuntimeException {
    
    //Esta declaração não foi solicitada pelo RuntimeException
    //private static final long serialVersionUID = 1L; 
    
    public DbException(String msg){
        super(msg);
    }
    
}
