package utils;
import java.util.List;
import java.util.Date;



public class DoctorUtils {


    public Integer strToInteger(String field){
        try{
            return Integer.parseInt(field);
        }catch (NumberFormatException ex){
            return null;
        }
    }

}
