/**
 * This class is an implementation of the ISerializer<> interface and handles
 * of serialization and deserialization of instances based on a generic.
 * <p>
 * Use the GSon library for this purpose.
 */
package com.maker.filemanagement.in;

import com.google.gson.Gson;
import com.maker.filemanagement.out.ISerializer;
import com.maker.utils.Crypter;

public class Serializer<E> implements ISerializer<E> {

    /**
     * Serializes an object using a GSon instance.
     * @param entity It's the entity to be serialized, it can only be of the generic type
     *               indicated in the instantiation or derivatives.
     * @return Returns a string with the content of the object serialized in JSon.
     */
    @Override
    public String serialize(E entity) {
        return new Gson().toJson(entity);
    }

    /**
     * Deserializes an object using a GSon instance.
     * @param json Is the JSon string to deserialize.
     * @param entityClass Is the class to which the object to be deserialized belongs,
     *                    can only be of the generic type indicated in the
     *                    instantiation or derivatives.
     * @return Returns an instance of the generic type indicated in the instantiation
     * with the deserialized data.
     */
    @Override
    public E deserialize(String json, Class<? extends E> entityClass) {
        return new Gson().fromJson(json, entityClass);
    }
}
