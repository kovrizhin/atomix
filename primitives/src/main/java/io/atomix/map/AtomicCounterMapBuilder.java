/*
 * Copyright 2015-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.atomix.map;

import io.atomix.primitive.DistributedPrimitiveBuilder;
import io.atomix.primitive.PrimitiveTypes;

/**
 * Builder for AtomicCounterMap.
 */
public abstract class AtomicCounterMapBuilder<K>
    extends DistributedPrimitiveBuilder<AtomicCounterMapBuilder<K>, AtomicCounterMap<K>, AsyncAtomicCounterMap<K>> {
  public AtomicCounterMapBuilder() {
    super(PrimitiveTypes.COUNTER_MAP);
  }

  @Override
  public AtomicCounterMap<K> build() {
    return buildAsync().asAtomicCounterMap();
  }
}
