/***************************************************************************************************
 * Copyright 2017 Regents of the University of California. Licensed under the Educational
 * Community License, Version 2.0 (the “license”); you may not use this file except in
 * compliance with the License. You may obtain a copy of the license at
 *
 * https://opensource.org/licenses/ECL-2.0
 *
 * Unless required under applicable law or agreed to in writing, software distributed under the
 * License is distributed in an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for specific language governing permissions
 * and limitations under the license.
 **************************************************************************************************/
package tds.common.data.legacy;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class LegacyComparerTest {
    @Test
    public void legacy_Comparer_Should_Match_DbComparator_Functionality() {
        UUID uuidNull1 = null;
        UUID uuidNull2 = null;
        UUID uuid1 = UUID.fromString("2B20031D-4BD8-42A8-9963-F6FFA44A9271");
        UUID uuid2 = UUID.fromString("2B20031D-4BD8-42A8-9963-F6FFA44A9271");

        Assert.assertEquals(true, LegacyComparer.isEqual(uuid1, uuid2));
        Assert.assertEquals(false, LegacyComparer.isEqual(uuidNull1, uuid2));

        // NOT INTUITIVE RESULTS with current codebase that we can't change right now
        Assert.assertEquals(false, LegacyComparer.isEqual(uuidNull1, uuidNull2));

        Assert.assertEquals(false, LegacyComparer.notEqual(uuidNull1, uuidNull2));
    }
}
