package tds.common.entity.utils;

/**
 * An interface for listening for and responding to changes on an entity
 */
public interface ChangeListener<T> {
    /**
     * Compare an original instance of an object's state to an updated instance of an object's state and execute any
     * business rules/logic that are a consequence of the change in state.
     *
     * @param oldInstance The original instance of the object/entity
     * @param newInstance The updated instance of the object/entity
     */
    void accept(T oldInstance, T newInstance);
}
