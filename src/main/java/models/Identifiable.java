package models;

/**
 * User and Course both inherits this to let UserOrCourseUpdater
 * update connected users/courses in a generic way.
 *
 * See UserOrCourseUpdater for more information.
 */

public interface Identifiable {
    long getId();
}
