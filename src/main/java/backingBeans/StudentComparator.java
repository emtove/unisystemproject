package backingBeans;

import models.User;

import java.util.Comparator;

/**
 * Created by Elev1 on 2017-04-24.
 */
public class StudentComparator implements Comparator<User> {
        @Override
        public int compare(User u1, User u2) {
            return (int)(u1.getId()-u2.getId());
        }
    }

