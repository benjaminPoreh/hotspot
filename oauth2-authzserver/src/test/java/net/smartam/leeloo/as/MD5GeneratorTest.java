/**
 *       Copyright 2010 Newcastle University
 *           Maciej Machulak, Lukasz Moren
 *
 *          http://research.ncl.ac.uk/smart/
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package net.smartam.leeloo.as;

import junit.framework.Assert;

import org.junit.Test;

import net.smartam.leeloo.as.issuer.MD5Generator;
import net.smartam.leeloo.as.issuer.ValueGenerator;
import net.smartam.leeloo.common.exception.OAuthSystemException;


/**
 * @author Maciej Machulak
 * @author Lukasz Moren
 */
public class MD5GeneratorTest extends Assert {
    @Test
    public void testGenerateValue() throws Exception {
        ValueGenerator g = new MD5Generator();
        Assert.assertNotNull(g.generateValue());

        Assert.assertNotNull(g.generateValue("test"));

        try {
            g.generateValue(null);
            fail("Exception not thrown");
        } catch (OAuthSystemException e) {
            //ok
        }
    }
}
