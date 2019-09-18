/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cbvmp.service.data.model;

/**
 *
 * @author rahat
 */
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;


//@SecondaryTable(name = "products")
@Entity
//@Table(name = "gen_menus")


@NamedNativeQueries({
    @NamedNativeQuery(
            name = "SP_show",
            query = "CALL showvalue(?)",
            callable = true,
            resultClass = UserModel.class
    ),
    @NamedNativeQuery(
            name = "SP_insert",
            query = "call insert_menu(:par2)",
            callable = false,
            resultClass = UserModel.class
    ),
        
    @NamedNativeQuery(
            name = "SP_join",
            query = "CALL GEN_MENUS_JOIN(?)",
            callable = true,
            resultClass = UserModel.class
    ),
    
     @NamedNativeQuery(
            name = "SP_find",
            query = "select * from gen_menus",
            //callable = true,
            resultClass = UserModel.class
            
    ) 
    
})
public class UserModel implements Serializable {

    @Id
    @GeneratedValue
    private int MENU_NO;    
    private String MENU_NAME;

  
    
    //@Column(table="products")
    //private String PNAME;
//    private int PID;
//    
//    private String PNAME;
//    private String SNAME;

//    public User() {
//    }

//    public User(int menuNo,String menuName) {
//        this.MENU_NO=menuNo;
//        this.MENU_NAME = menuName;
//    }

  
    //}
    
    public int getMENU_NO() {
        return MENU_NO;
    }

    public void setMENU_NO(int MENU_NO) {
        this.MENU_NO = MENU_NO;
    }

    public String getMenuName() {
        return MENU_NAME;
    }

    public void setMenuName(String menuName) {
        this.MENU_NAME = menuName;
    }
    
//    public int getPid()
//    {
//        return PID;
//    }
//    
//    public void setPid(int pid)
//    {
//        this.PID=pid;
//    }
//    
//    public String getSName() {
//        return SNAME;
//    }
//
//    public void setSName(String sName) {
//        this.SNAME = sName;
//    }
//    
//    public String getPName() {
//        return PNAME;
//    }
//
//    public void setPName(String pName) {
//        this.PNAME = pName;
//    }
//    

}
