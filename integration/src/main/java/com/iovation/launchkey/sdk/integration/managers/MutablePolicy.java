package com.iovation.launchkey.sdk.integration.managers;

import com.iovation.launchkey.sdk.domain.policy.*;

import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;

/**
 * Simplifies modification of immutable policy objects
 */
public class MutablePolicy {

    private Policy sourceImmutablePolicy;

    public MutablePolicy(Policy immutablePolicy) {
        sourceImmutablePolicy = immutablePolicy;
    }

    public Policy toImmutablePolicy() {
        return sourceImmutablePolicy;
    }

    public void setAmount(int amount) throws Throwable {
        if ((sourceImmutablePolicy == null) || !(sourceImmutablePolicy instanceof MethodAmountPolicy)) {
            throw new Throwable("Source Policy is not of type method amount policy cannot add amount");
        }
        sourceImmutablePolicy = new MethodAmountPolicy(sourceImmutablePolicy.getDenyRootedJailbroken(), sourceImmutablePolicy.getDenyEmulatorSimulator(), sourceImmutablePolicy.getFences(), amount);
    }

    public void setFactors(List<String> factors) throws Throwable {
        if ((sourceImmutablePolicy == null) || !(sourceImmutablePolicy instanceof FactorsPolicy)) {
            throw new Throwable("Source Policy is not of type factors policy cannot add factors");
        }
        boolean inherence = false;
        boolean possession = false;
        boolean knowledge = false;
        for (String factor : factors) {
            if (factor.equals("KNOWLEDGE")) {
                knowledge = true;
            }
            if (factor.equals("INHERENCE")) {
                inherence = true;
            }
            if (factor.equals("POSSESSION")) {
                possession = true;
            }
        }
        sourceImmutablePolicy = new FactorsPolicy(sourceImmutablePolicy.getDenyRootedJailbroken(), sourceImmutablePolicy.getDenyEmulatorSimulator(), sourceImmutablePolicy.getFences(), inherence, knowledge, possession);
    }

    public void setDenyRootedJailBroken(Boolean newValue) throws Throwable {
        if (sourceImmutablePolicy == null) {
            throw new Throwable("Source Policy is null cannot set deny rooted jailbroken");
        }
        if (sourceImmutablePolicy instanceof MethodAmountPolicy) {
            sourceImmutablePolicy = new MethodAmountPolicy(newValue, sourceImmutablePolicy.getDenyEmulatorSimulator(), sourceImmutablePolicy.getFences(), ((MethodAmountPolicy) sourceImmutablePolicy).getAmount());
        } else if (sourceImmutablePolicy instanceof FactorsPolicy) {
            FactorsPolicy factorsPolicy = (FactorsPolicy) sourceImmutablePolicy;
            sourceImmutablePolicy = new FactorsPolicy(newValue, sourceImmutablePolicy.getDenyEmulatorSimulator(), sourceImmutablePolicy.getFences(), factorsPolicy.isInherenceRequired(), factorsPolicy.isKnowledgeRequired(), factorsPolicy.isPossessionRequired());
        } else if (sourceImmutablePolicy instanceof ConditionalGeoFencePolicy) {
            sourceImmutablePolicy = new ConditionalGeoFencePolicy(newValue, sourceImmutablePolicy.getDenyEmulatorSimulator(), sourceImmutablePolicy.getFences(), ((ConditionalGeoFencePolicy) sourceImmutablePolicy).getInPolicy(), ((ConditionalGeoFencePolicy) sourceImmutablePolicy).getOutPolicy());
        } else {
            throw new Throwable("Source Policy is of unknown type");
        }
    }

    public void setDenyEmulatorSimulator(Boolean newValue) throws Throwable {
        if (sourceImmutablePolicy == null) {
            throw new Throwable("Source Policy is null cannot set deny emulator simulator");
        }
        if (sourceImmutablePolicy instanceof MethodAmountPolicy) {
            sourceImmutablePolicy = new MethodAmountPolicy(sourceImmutablePolicy.getDenyRootedJailbroken(), newValue, sourceImmutablePolicy.getFences(), ((MethodAmountPolicy) sourceImmutablePolicy).getAmount());
        } else if (sourceImmutablePolicy instanceof FactorsPolicy) {
            FactorsPolicy factorsPolicy = (FactorsPolicy) sourceImmutablePolicy;
            sourceImmutablePolicy = new FactorsPolicy(sourceImmutablePolicy.getDenyRootedJailbroken(), newValue, sourceImmutablePolicy.getFences(), factorsPolicy.isInherenceRequired(), factorsPolicy.isKnowledgeRequired(), factorsPolicy.isPossessionRequired());
        } else if (sourceImmutablePolicy instanceof ConditionalGeoFencePolicy) {
            sourceImmutablePolicy = new ConditionalGeoFencePolicy(sourceImmutablePolicy.getDenyRootedJailbroken(), newValue, sourceImmutablePolicy.getFences(), ((ConditionalGeoFencePolicy) sourceImmutablePolicy).getInPolicy(), ((ConditionalGeoFencePolicy) sourceImmutablePolicy).getOutPolicy());
        } else {
            throw new Throwable("Source Policy is of unknown type");
        }
    }

    public void addFences(List<Fence> newFences) throws Throwable {
        if (sourceImmutablePolicy == null) {
            throw new Throwable("Source Policy is null cannot add fences");
        }
        ArrayList<Fence> fences = new ArrayList<>();
        if (sourceImmutablePolicy.getFences() != null) {
            fences.addAll(sourceImmutablePolicy.getFences());
        }
        fences.addAll(newFences);
        if (sourceImmutablePolicy instanceof MethodAmountPolicy) {
            sourceImmutablePolicy = new MethodAmountPolicy(sourceImmutablePolicy.getDenyRootedJailbroken(), sourceImmutablePolicy.getDenyEmulatorSimulator(), fences, ((MethodAmountPolicy) sourceImmutablePolicy).getAmount());
        } else if (sourceImmutablePolicy instanceof FactorsPolicy) {
            FactorsPolicy factorsPolicy = (FactorsPolicy) sourceImmutablePolicy;
            sourceImmutablePolicy = new FactorsPolicy(sourceImmutablePolicy.getDenyRootedJailbroken(), sourceImmutablePolicy.getDenyEmulatorSimulator(), fences, factorsPolicy.isInherenceRequired(), factorsPolicy.isKnowledgeRequired(), factorsPolicy.isPossessionRequired());
        } else if (sourceImmutablePolicy instanceof ConditionalGeoFencePolicy) {
            sourceImmutablePolicy = new ConditionalGeoFencePolicy(sourceImmutablePolicy.getDenyRootedJailbroken(), sourceImmutablePolicy.getDenyEmulatorSimulator(), fences, ((ConditionalGeoFencePolicy) sourceImmutablePolicy).getInPolicy(), ((ConditionalGeoFencePolicy) sourceImmutablePolicy).getOutPolicy());
        } else {
            throw new Throwable("Source Policy is of unknown type");
        }
    }

    public void addInsidePolicy(Policy insidePolicy) throws Throwable {
        if ((sourceImmutablePolicy == null) || !(sourceImmutablePolicy instanceof ConditionalGeoFencePolicy)) {
            throw new Throwable("Source Policy is not ConditionalGeoFence type");
        }
        ConditionalGeoFencePolicy castedCachedPolicy = (ConditionalGeoFencePolicy) sourceImmutablePolicy;
        sourceImmutablePolicy = new ConditionalGeoFencePolicy(castedCachedPolicy.getDenyRootedJailbroken(),
                castedCachedPolicy.getDenyEmulatorSimulator(), castedCachedPolicy.getFences(), insidePolicy,
                castedCachedPolicy.getOutPolicy());
    }

    public void addOutsidePolicy(Policy outsidePolicy) throws Throwable {
        if ((sourceImmutablePolicy == null) || !(sourceImmutablePolicy instanceof ConditionalGeoFencePolicy)) {
            throw new Throwable("Source Policy is not ConditionalGeoFence type");
        }
        ConditionalGeoFencePolicy castedCachedPolicy = (ConditionalGeoFencePolicy) sourceImmutablePolicy;
        sourceImmutablePolicy = new ConditionalGeoFencePolicy(castedCachedPolicy.getDenyRootedJailbroken(),
                castedCachedPolicy.getDenyEmulatorSimulator(), castedCachedPolicy.getFences(),
                castedCachedPolicy.getInPolicy(), outsidePolicy);
    }

    public void addFactorToInsidePolicy(String factor) throws Throwable {
        if ((sourceImmutablePolicy == null) || !(sourceImmutablePolicy instanceof ConditionalGeoFencePolicy)) {
            throw new Throwable("Source Policy is not ConditionalGeoFence type cannot hold inside policies");
        }
        if (((ConditionalGeoFencePolicy) sourceImmutablePolicy).getInPolicy() == null) {
            throw new Throwable("Source Policy has no inside policy");
        }
        if (!(((ConditionalGeoFencePolicy) sourceImmutablePolicy).getInPolicy() instanceof FactorsPolicy)) {
            throw new Throwable("Source Policy inside policy is not Factors policy");
        }
        ConditionalGeoFencePolicy castedCachedPolicy = (ConditionalGeoFencePolicy) sourceImmutablePolicy;
        FactorsPolicy existingFactorsPolicy = (FactorsPolicy) castedCachedPolicy.getInPolicy();
        boolean inherence = false;
        boolean possession = false;
        boolean knowledge = false;
        if (factor.equals("KNOWLEDGE")) {
            knowledge = true;
        }
        if (factor.equals("INHERENCE")) {
            inherence = true;
        }
        if (factor.equals("POSSESSION")) {
            possession = true;
        }
        FactorsPolicy newFactorsPolicy = new FactorsPolicy(existingFactorsPolicy.getDenyRootedJailbroken(), existingFactorsPolicy.getDenyRootedJailbroken(), existingFactorsPolicy.getFences(), inherence, knowledge, possession);
        sourceImmutablePolicy = new ConditionalGeoFencePolicy(castedCachedPolicy.getDenyRootedJailbroken(), castedCachedPolicy.getDenyEmulatorSimulator(), castedCachedPolicy.getFences(), newFactorsPolicy, castedCachedPolicy.getOutPolicy());
    }

    public void setInsidePolicyAmount(int amount) throws Throwable {
        if ((sourceImmutablePolicy == null) || !(sourceImmutablePolicy instanceof ConditionalGeoFencePolicy)) {
            throw new Throwable("Source Policy is not ConditionalGeoFence type cannot hold inside policies");
        }
        if (((ConditionalGeoFencePolicy) sourceImmutablePolicy).getInPolicy() == null) {
            throw new Throwable("Source Policy has no inside policy");
        }
        if (!(((ConditionalGeoFencePolicy) sourceImmutablePolicy).getInPolicy() instanceof MethodAmountPolicy)) {
            throw new Throwable("Source Policy inside policy is not MethodAmountPolicy");
        }
        ConditionalGeoFencePolicy castedCachedPolicy = (ConditionalGeoFencePolicy) sourceImmutablePolicy;
        MethodAmountPolicy existingInsidePolicy = (MethodAmountPolicy) castedCachedPolicy.getInPolicy();
        MethodAmountPolicy newInsidePolicy = new MethodAmountPolicy(existingInsidePolicy.getDenyRootedJailbroken(), existingInsidePolicy.getDenyEmulatorSimulator(), existingInsidePolicy.getFences(), amount);
        sourceImmutablePolicy = new ConditionalGeoFencePolicy(castedCachedPolicy.getDenyRootedJailbroken(), castedCachedPolicy.getDenyEmulatorSimulator(), castedCachedPolicy.getFences(), newInsidePolicy, castedCachedPolicy.getOutPolicy());
    }

    public void addFactorToOutsidePolicy(String factor) throws Throwable {
        if ((sourceImmutablePolicy == null) || !(sourceImmutablePolicy instanceof ConditionalGeoFencePolicy)) {
            throw new Throwable("Source Policy is not ConditionalGeoFence type cannot hold outside policies");
        }
        if (((ConditionalGeoFencePolicy) sourceImmutablePolicy).getOutPolicy() == null) {
            throw new Throwable("Source Policy has no outside policy");
        }
        if (!(((ConditionalGeoFencePolicy) sourceImmutablePolicy).getOutPolicy() instanceof FactorsPolicy)) {
            throw new Throwable("Source Policy outside policy is not Factors policy");
        }
        ConditionalGeoFencePolicy castedCachedPolicy = (ConditionalGeoFencePolicy) sourceImmutablePolicy;
        FactorsPolicy existingFactorsPolicy = (FactorsPolicy) castedCachedPolicy.getOutPolicy();
        boolean inherence = false;
        boolean possession = false;
        boolean knowledge = false;
        if (factor.equals("KNOWLEDGE")) {
            knowledge = true;
        }
        if (factor.equals("INHERENCE")) {
            inherence = true;
        }
        if (factor.equals("POSSESSION")) {
            possession = true;
        }
        FactorsPolicy newFactorsPolicy = new FactorsPolicy(existingFactorsPolicy.getDenyRootedJailbroken(), existingFactorsPolicy.getDenyRootedJailbroken(), existingFactorsPolicy.getFences(), inherence, knowledge, possession);
        sourceImmutablePolicy = new ConditionalGeoFencePolicy(castedCachedPolicy.getDenyRootedJailbroken(), castedCachedPolicy.getDenyEmulatorSimulator(), castedCachedPolicy.getFences(), castedCachedPolicy.getInPolicy(), newFactorsPolicy);
    }

    public void setOutsidePolicyAmount(int amount) throws Throwable {
        if ((sourceImmutablePolicy == null) || !(sourceImmutablePolicy instanceof ConditionalGeoFencePolicy)) {
            throw new Throwable("Source Policy is not ConditionalGeoFence type cannot hold outside policies");
        }
        if (((ConditionalGeoFencePolicy) sourceImmutablePolicy).getOutPolicy() == null) {
            throw new Throwable("Source Policy has no outside policy");
        }
        if (!(((ConditionalGeoFencePolicy) sourceImmutablePolicy).getOutPolicy() instanceof MethodAmountPolicy)) {
            throw new Throwable("Source Policy outside policy is not MethodAmountPolicy");
        }
        ConditionalGeoFencePolicy castedCachedPolicy = (ConditionalGeoFencePolicy) sourceImmutablePolicy;
        MethodAmountPolicy existingOutsidePolicy = (MethodAmountPolicy) castedCachedPolicy.getOutPolicy();
        MethodAmountPolicy newOutsidePolicy = new MethodAmountPolicy(existingOutsidePolicy.getDenyRootedJailbroken(), existingOutsidePolicy.getDenyEmulatorSimulator(), existingOutsidePolicy.getFences(), amount);
        sourceImmutablePolicy = new ConditionalGeoFencePolicy(castedCachedPolicy.getDenyRootedJailbroken(), castedCachedPolicy.getDenyEmulatorSimulator(), castedCachedPolicy.getFences(), castedCachedPolicy.getInPolicy(), newOutsidePolicy);
    }

}
