package com.maker.filemanagement.out;

/**
 * This interface defines methods for serializing and deserializing objects.
 */
public interface ISerializer<E> {
    /**
     * Serializes an object to a JSON string.
     * @param entity The object to be serialized.
     * @return A JSON string representing the serialized object.
     */
    String serialize(E entity);

    /**
     * Deserializes a JSON string to an object.
     * @param json The JSON string to be deserialized.
     * @param entityClass The class of the object to be deserialized.
     * @return An instance of the specified class with deserialized data.
     */
    E deserialize(String json, Class<? extends E> entityClass);
}

