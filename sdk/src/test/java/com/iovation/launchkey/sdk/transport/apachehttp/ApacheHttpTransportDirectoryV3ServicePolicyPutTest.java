package com.iovation.launchkey.sdk.transport.apachehttp; /**
 * Copyright 2017 iovation, Inc.
 * <p>
 * Licensed under the MIT License.
 * You may not use this file except in compliance with the License.
 * A copy of the License is located in the "LICENSE.txt" file accompanying
 * this file. This file is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.iovation.launchkey.sdk.transport.domain.EntityIdentifier;
import com.iovation.launchkey.sdk.transport.domain.ServicePolicyPutRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.net.URI;
import java.security.PublicKey;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(org.mockito.junit.MockitoJUnitRunner.Silent.class)
public class ApacheHttpTransportDirectoryV3ServicePolicyPutTest extends ApacheHttpTransportTestBase {
    @Mock
    private ServicePolicyPutRequest request;

    @Mock
    private EntityIdentifier entityIdentifier;


    @Test
    public void sendsRequestWithProperMethodAndPath() throws Exception {
        transport.directoryV3ServicePolicyPut(request, entityIdentifier);
        verifyCall("PUT", URI.create(baseUrl.concat("/directory/v3/service/policy")));
    }

    @Test
    public void marshalsExpectedData() throws Exception {
        transport.directoryV3ServicePolicyPut(request, entityIdentifier);
        verify(objectMapper).writeValueAsString(request);
    }

    @Test
    public void encryptsDataWithMarshaledValue() throws Exception {
        when(objectMapper.writeValueAsString(any(Object.class))).thenReturn("Expected");
        transport.directoryV3ServicePolicyPut(request, entityIdentifier);
        verify(jweService).encrypt(eq("Expected"), any(PublicKey.class), anyString(), anyString());
    }

}