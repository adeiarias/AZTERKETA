package ehu.isad.controllers.db;

import ehu.isad.model.PhpMyAdminModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhpMyAdminDB {

    private static PhpMyAdminDB instantzia = new PhpMyAdminDB();

    public static PhpMyAdminDB getInstance(){ return instantzia; }

    private PhpMyAdminDB(){}

    DBController dbController = DBController.getInstance();

    public boolean dagoHasha(String hash){
        String query = "select idCMS from checksums where md5 = '"+hash+"'";
        ResultSet rs = dbController.execSQL(query);
        try{
            rs.next();
            if(rs.getInt("idCMS")==1) {
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public PhpMyAdminModel modelaHartu(String hash) {
        String query = "select * from checksums where md5 = '"+hash+"'";
        ResultSet rs = dbController.execSQL(query);
        try{
            String md5,version;
            while(rs.next()){
                md5 = rs.getString("md5");
                version = rs.getString("version");
                return new PhpMyAdminModel("",md5,version);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void addToDB(PhpMyAdminModel model) {
        String query = "insert into checksums values(1,'"+model.getVersion()+"','"+model.getMd5()+"','README')";
        dbController.execSQL(query);
    }
}
