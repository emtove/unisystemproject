package backingBeans;

import models.Course;

import java.util.Comparator;

/**
 * Created by Elev1 on 2017-04-21.
 */
public class CourseComparator implements Comparator<Course> {
    @Override
    public int compare(Course c1, Course c2) {
        return c1.getName().compareTo(c2.getName());
    }
}
