import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;

/**
 * Created by Elev1 on 2017-04-12.
 */
@Named
@SessionScoped
public class TeacherViewAttendance {

    public ArrayList<String> getMyCourses(){
        return new ArrayList<String>();
    }

    public void changeSelectedCourse(){}

}
