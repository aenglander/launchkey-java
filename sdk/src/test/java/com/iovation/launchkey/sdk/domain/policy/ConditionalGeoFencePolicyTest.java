package com.iovation.launchkey.sdk.domain.policy;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ConditionalGeoFencePolicyTest {

    @Test
    public void getDenyRootedJailbroken() throws Exception {
        assertTrue(new ConditionalGeoFencePolicy(true,false,null,null,null).getDenyRootedJailbroken());
    }

    @Test
    public void getDenyEmulatorSimulator() throws Exception {
        assertTrue(new ConditionalGeoFencePolicy(false,true,null,null,null).getDenyEmulatorSimulator());
    }

    @Test
    public void getInPolicy() throws Exception {
        Policy inPolicy = new MethodAmountPolicy(false,false,null,0);
        ConditionalGeoFencePolicy conGeoPolicy = new ConditionalGeoFencePolicy(false,false,null, inPolicy,null);
        assertEquals(conGeoPolicy.getInPolicy(),inPolicy);
        assertFalse(conGeoPolicy.getInPolicy().getDenyRootedJailbroken());
    }

    @Test
    public void getOutPolicy() throws Exception {
        Policy outPolicy = new MethodAmountPolicy(false,false,null,0);
        ConditionalGeoFencePolicy conGeoPolicy = new ConditionalGeoFencePolicy(false,false,null, null, outPolicy);
        assertEquals(conGeoPolicy.getOutPolicy(),outPolicy);
        assertFalse(conGeoPolicy.getOutPolicy().getDenyEmulatorSimulator());
    }

    @Test
    public void getFences() throws Exception {
        List<Fence> fences = new ArrayList<>();
        fences.add(new GeoCircleFence("aFence", 0,0,5));
        ConditionalGeoFencePolicy conGeoPolicy = new ConditionalGeoFencePolicy(false,false,fences, null, null);
        assertEquals(fences,conGeoPolicy.getFences());
    }

    @Test
    public void defaultConstructor() throws Exception {
        ConditionalGeoFencePolicy policy = new ConditionalGeoFencePolicy();
        assertNull(policy.getInPolicy());
        assertNull(policy.getOutPolicy());
        assertFalse(policy.getDenyEmulatorSimulator());
        assertFalse(policy.getDenyRootedJailbroken());
        assertNull(policy.getFences());
    }

}
