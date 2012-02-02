package org.gsoft.openserv.rulesengine;

import java.lang.reflect.Constructor;

import org.gsoft.openserv.domain.PhoenixDomainObject;

public class FactUtil {

    /**
     * Using reflection, determines the context object types the given fact type needs for instantiation.
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends PhoenixDomainObject>[] determineConstructorTypesForFactType(Class<? extends Fact> factType) {
        final Constructor<?>[] constructors = factType.getConstructors();
        if (constructors.length != 1) {
            throw new RulesEngineException("Facts must declare a single constructor.");
        }
        else {
            try {
                return (Class<? extends PhoenixDomainObject>[]) constructors[0].getParameterTypes();
            }
            catch (final ClassCastException castException) {
                throw new RulesEngineException(
                        "Fact constructors must only take parameters that are PhoenixDomainObject subclasses.");
            }
        }
    }
    
}
