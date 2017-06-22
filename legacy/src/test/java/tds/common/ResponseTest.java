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

package tds.common;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ResponseTest {
    @Test
    public void shouldNotHaveErrors() {
        Response<String> response = new Response<>("test");
        assertThat(response.hasError()).isFalse();
        assertThat(response.getError().isPresent()).isFalse();
        assertThat(response.getData().get()).isEqualTo("test");
    }

    @Test
    public void shouldHaveErrors() {
        ValidationError error = new ValidationError("error", "testing error");
        Response<String> response = new Response<String>(error);
        assertThat(response.hasError()).isTrue();
        assertThat(response.getError().get()).isEqualTo(error);
        assertThat(response.getData().isPresent()).isFalse();
    }
}