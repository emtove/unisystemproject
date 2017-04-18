package services;

import models.Identifiable;

import javax.persistence.EntityManager;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * A kitchen sink class containing various things common to both users and courses.
 */

class UserCourseCommon {

    private static Set<Long> getIdList(Set<? extends Identifiable> entitySet) {
        return entitySet.stream().map(Identifiable::getId).collect(Collectors.toSet());
    }

    /**
     * Updates the other end of the course/user many-to-many connection.
     *
     * The source is the course/user that was changed and the target is the other one.
     * The source should then be removed from all targets that were removed from the source,
     * and added to all targets that was added to the source.
     *
     * Example:
     * User U was changed: course C was removed from them since U stopped taking said course.
     * U is the source and C is the target. C is present in the old version of U (oldSource,
     * from the database) but no in the new version (newSource).
     * U must now be manually removed from C as well. First, find C in either the studentCourses
     * list or the teacherCourses list (provided in getTargetList) in U. Then, in C, find
     * U in either the students list or the teachers list (provided in getSourceList) and
     * remove U from the correct list.
     * Do this for all removed targets and the opposite for all added targets.
     *
     * (Yes, it's convoluted and horrible but at least it isn't duplicated anymore.)
     *
     * @param oldSource         the source object from the database
     * @param newSource         the source object with the new changes
     * @param getSourceList     the student- or teacher-related method for the source
     * @param getTargetList     the student- or teacher-related method for the target
     * @param targetClass       Target.class since generic types don't work like that
     * @param em                entityManager
     * @param <S>               source class (User or Course)
     * @param <T>               target class (User or Course)
     */
    static <S extends Identifiable, T extends Identifiable>
    void updateUsersOrCourses(S oldSource, S newSource,
                              Function<T, Set<S>> getSourceList,
                              Function<S, Set<T>> getTargetList,
                              Class<T> targetClass, EntityManager em) {
        // Get the correct kind of courses/users (for teachers or students) and then their ids
        Set<Long> oldTargetIds = getIdList(getTargetList.apply(oldSource));
        Set<Long> newTargetIds = getIdList(getTargetList.apply(newSource));
        // Add the source to all added targets
        newTargetIds.stream()
                .filter(item -> !oldTargetIds.contains(item))
                .map(id -> em.find(targetClass, id))
                .map(getSourceList)
                .forEach(set -> set.add(oldSource));
        // Remove the source from all removed targets
        oldTargetIds.stream()
                .filter(item -> !newTargetIds.contains(item))
                .map(id -> em.find(targetClass, id))
                .map(getSourceList)
                .forEach(set -> set.remove(oldSource));
    }
}
