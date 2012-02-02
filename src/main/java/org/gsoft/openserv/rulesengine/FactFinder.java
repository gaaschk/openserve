package org.gsoft.openserv.rulesengine;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.gsoft.openserv.domain.OpenServDomainObject;
import org.gsoft.openserv.util.ReversibleMultiMap;

public class FactFinder {
    
    private final List<OpenServDomainObject> context;
    private final Set<Class<? extends Fact>> neededFactClasses;
    private final ReversibleMultiMap<OpenServDomainObject, Fact> factsByContext;
    private Set<Fact> existingFacts;
    
    public FactFinder(final List<OpenServDomainObject> context, final Set<Class<? extends Fact>> neededFactClasses,
            final ReversibleMultiMap<OpenServDomainObject, Fact> factsByContext) {
        this.context = context;
        this.neededFactClasses = neededFactClasses;
        this.factsByContext = factsByContext;
    }
    
    public Set<Fact> createNonExistingNeededFacts() {
        Set<Class<? extends Fact>> stillNeeded = this.determineStillNeededFactClasses();
        Set<Fact> createdFacts = new HashSet<Fact>();
        for (Class<? extends Fact> factClass : stillNeeded) {
            Fact newFact = this.createFactIfEnoughContext(factClass);
            if (newFact != null) {
                createdFacts.add(newFact);
            }
        }
        // Don't want to create them again.
        this.existingFacts.addAll(createdFacts);
        return createdFacts;
    }
    
    private Fact createFactIfEnoughContext(Class<? extends Fact> factClass) {
        try {
            Class<? extends OpenServDomainObject>[] constructorParamTypes = FactUtil.determineConstructorTypesForFactType(factClass);
            Constructor<? extends Fact> constructor = factClass.getConstructor(constructorParamTypes);
            OpenServDomainObject[] constructorParams = this.buildConstructorParamsArray(constructorParamTypes, this.context);
            return constructor.newInstance((Object[])constructorParams);
        }
        catch (Exception e) {
            throw new RulesEngineException("Could not create fact: " + factClass.getSimpleName(), e);
        }
    }

    private OpenServDomainObject[] buildConstructorParamsArray(Class<? extends OpenServDomainObject>[] constructorParamTypes,
            List<OpenServDomainObject> contextList) {
        OpenServDomainObject[] paramArray = new OpenServDomainObject[constructorParamTypes.length];
        int index = 0;
        for (Class<? extends OpenServDomainObject> type : constructorParamTypes) {
            for (OpenServDomainObject contextObject : contextList) {
                if (contextObject.getClass().equals(type)) {
                    paramArray[index] = contextObject;
                    break;
                }
            }
            index++;
        }
        return paramArray;
    }

    private Set<Class<? extends Fact>> determineStillNeededFactClasses() {
        Set<Fact> existingFacts = this.getExistingNeededFacts();
        Set<Class<? extends Fact>> stillNeeded = new HashSet<Class<? extends Fact>>(this.neededFactClasses);
        for (Fact fact : existingFacts) {
            stillNeeded.remove(fact.getClass());
        }
        return stillNeeded;
    }

    public Set<Fact> getExistingNeededFacts() {
        if (this.existingFacts == null) {
            this.existingFacts = new HashSet<Fact>();
            for (final OpenServDomainObject contextObject : this.context) {
                final Collection<Fact> facts = this.factsByContext.get(contextObject);
                if (facts != null) {
                    this.addExistingFactsThatMatchNeedAndContext(facts);
                }
            }
        }
        return this.existingFacts;
    }
    
    private void addExistingFactsThatMatchNeedAndContext(final Collection<Fact> facts) {
        for (final Fact fact : facts) {
            if (this.isNeeded(fact) && this.matchesContext(fact)) {
                this.existingFacts.add(fact);
            }
        }
    }

    private boolean matchesContext(final Fact fact) {
        boolean matchesContext = false;
        final Collection<OpenServDomainObject> factContext = this.factsByContext.getByValue(fact);
        if (this.context.containsAll(factContext)) {
            matchesContext = true;
        }
        return matchesContext;
    }

    private boolean isNeeded(final Fact fact) {
        return this.neededFactClasses.contains(fact.getClass());
    }
}
