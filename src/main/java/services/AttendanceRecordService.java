package services;
import models.AttendanceRecord;

/**
 * Created by Elev1 on 2017-04-24.
 */
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Local
@Stateless
public class AttendanceRecordService {
    @PersistenceContext
    private EntityManager em;

    public void addAttendanceRecord(AttendanceRecord a){
        em.persist(a);
    }

    public void updateAttendanceRecord(AttendanceRecord attendanceRecord) {
        List<AttendanceRecord> attendanceRecords = getAttendance(attendanceRecord);
        if (attendanceRecords.size() > 0) {
            AttendanceRecord oldRecord = attendanceRecords.get(0);
            oldRecord.setPresent(attendanceRecord.isPresent());
            em.merge(oldRecord);
        }
        else {
            em.persist(attendanceRecord);
        }
    }

    public List<AttendanceRecord> getAttendance(AttendanceRecord record){
        Query query = em.createQuery("select a from AttendanceRecord a where a.date = :date and a.courseId = :courseId and a.userId = :userId", AttendanceRecord.class);
        query.setParameter("date", record.getDate());
        query.setParameter("courseId", record.getCourseId());
        query.setParameter("userId", record.getUserId());

        return query.getResultList();
    }

    public List<AttendanceRecord> getAttendanceByCourseAndUser(long courseId, long userId){
        Query query = em.createQuery("select a from AttendanceRecord a where a.courseId = :courseId and a.userId = :userId", AttendanceRecord.class);
        query.setParameter("courseId", courseId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    public List<AttendanceRecord> getSortedAttendanceByCourseAndUser(long courseId, long userId){
        Query query = em.createQuery("select a from AttendanceRecord a where a.courseId = :courseId order by a.date", AttendanceRecord.class);
        query.setParameter("courseId", courseId);
        return query.getResultList();
    }
}
