package tds.common;

/**
 * Information about an updated entity and its previous state
 *
 * @param <T> the type of entity
 */
public class EntityUpdate<T> {
    private final T existingEntity;
    private final T updatedEntity;

    public EntityUpdate(final T existingEntity, final T updatedEntity) {
        this.existingEntity = existingEntity;
        this.updatedEntity = updatedEntity;
    }

    /**
     * @return the existing entity prior to the update
     */
    public T getExistingEntity() {
        return existingEntity;
    }

    /**
     * @return the updated entity
     */
    public T getUpdatedEntity() {
        return updatedEntity;
    }
}
