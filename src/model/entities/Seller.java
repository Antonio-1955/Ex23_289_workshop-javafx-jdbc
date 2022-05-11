/*Projeto: Ex21_261_demo-dao-JDBC7
 */
package model.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/*Implements Serializables é para que os objetos sejam transformados 
 *em sequência de bytes. Na linguagem Java isso tem que ser feito para 
 *que os objetos sejam gravados em arquivo e trafeguem em rede*/
public class Seller implements Serializable {
    
    //A linha abaixo foi exigida no curso com o Eclipse, mas não com o NetBeans-12
    //private static final long serialVersionUID = 1L;
//==============================================================================    

    //Atributos
    private Integer id;
    private String name;
    private String email;
    private Date birthDate;
    private Double baseSalary;
//==============================================================================    

    //Associação
    private Department departent;
//==============================================================================

    //Construtor vazio
    public Seller() {

    }
    //Construtor com argumentos

    public Seller(Integer id, String name, String email, Date birthDate, Double baseSalary, Department departent) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.baseSalary = baseSalary;
        this.departent = departent;
    }
//==============================================================================

    //Métodos Getters/Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Department getDepartent() {
        return departent;
    }

    public void setDepartent(Department departent) {
        this.departent = departent;
    }
//==============================================================================

    //Métodos hascode/equals
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Seller other = (Seller) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
//==============================================================================

    //Método toString
    @Override
    public String toString() {
        return "Seller{" + "id=" + id + ", name=" + name + ", email=" + email + ", birthDate=" + birthDate + ", baseSalary=" + baseSalary + ", departent=" + departent + '}';
    }

}
