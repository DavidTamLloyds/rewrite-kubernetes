/*
 * Copyright 2021 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openrewrite.kubernetes.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.openrewrite.kubernetes.resource.ResourceValue
import java.util.stream.Stream

class ResourceValueTest {

    @ParameterizedTest
    @MethodSource("resourceUnits")
    fun `given a base unit, must parse into ResourceValue`(unit: ResourceValue.Unit) =
        setOf(10L, 100L, 1000L).forEach { i ->
            val value = ResourceValue.parseResourceString("$i$unit")
            assertThat(value.unit).isEqualTo(unit)
            assertThat(unit.fromAbsoluteValue(value.absoluteValue)).isEqualTo(i)
        }

    private companion object {
        @JvmStatic
        fun resourceUnits() = Stream.of(
            Arguments.of(ResourceValue.Unit.K),
            Arguments.of(ResourceValue.Unit.M),
            Arguments.of(ResourceValue.Unit.G),
            Arguments.of(ResourceValue.Unit.T),
            Arguments.of(ResourceValue.Unit.P),
            Arguments.of(ResourceValue.Unit.Ki),
            Arguments.of(ResourceValue.Unit.Mi),
            Arguments.of(ResourceValue.Unit.Gi),
            Arguments.of(ResourceValue.Unit.Ti),
            Arguments.of(ResourceValue.Unit.Pi),
        )
    }

}