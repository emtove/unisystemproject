import java.util.Date;

/**
 * Created by Elev1 on 2017-04-11.
 */
public class AttendanceDate {
    private Date date;
    private boolean here;

    public Date getDate() {
        return date;
    }

    //Skall returnera typ en string eller en bock för utskrift i JSF
    public boolean isHere() {
        return here;
    }
}

