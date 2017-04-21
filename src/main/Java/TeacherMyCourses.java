import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.util.ArrayList;

/**
 * Created by Elev1 on 2017-04-12.
 */
@SessionScoped
@Named
public class TeacherMyCourses {

    public ArrayList<String> getMyCourses(){
        return new ArrayList<String>();
    }

    public void changeSelectedCourse(){}

    public ArrayList<String> getStudemts(){return new ArrayList<String>();}

}
