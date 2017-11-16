/*
 * Copyright 2016-present Open Networking Foundation
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
package io.atomix.primitive;

import io.atomix.counter.AsyncAtomicCounter;
import io.atomix.generator.AsyncAtomicIdGenerator;
import io.atomix.leadership.AsyncLeaderElector;
import io.atomix.lock.AsyncDistributedLock;
import io.atomix.map.AsyncAtomicCounterMap;
import io.atomix.map.AsyncConsistentMap;
import io.atomix.map.AsyncConsistentTreeMap;
import io.atomix.multimap.AsyncConsistentMultimap;
import io.atomix.queue.AsyncWorkQueue;
import io.atomix.serializer.Serializer;
import io.atomix.set.AsyncDistributedSet;
import io.atomix.tree.AsyncDocumentTree;
import io.atomix.value.AsyncAtomicValue;

import java.time.Duration;
import java.util.Set;

import static io.atomix.primitive.DistributedPrimitive.DEFAULT_OPERATION_TIMEOUT_MILLIS;

/**
 * Interface for entity that can create instances of different distributed primitives.
 */
public interface DistributedPrimitiveCreator {

  /**
   * Creates a new {@code AsyncConsistentMap}.
   *
   * @param name       map name
   * @param serializer serializer to use for serializing/deserializing map entries
   * @param <K>        key type
   * @param <V>        value type
   * @return map
   */
  <K, V> AsyncConsistentMap<K, V> newAsyncConsistentMap(String name, Serializer serializer);

  /**
   * Creates a new {@code AsyncConsistentTreeMap}.
   *
   * @param name       tree name
   * @param serializer serializer to use for serializing/deserializing map entries
   * @param <K>        key type
   * @param <V>        value type
   * @return distributedTreeMap
   */
  <K, V> AsyncConsistentTreeMap<K, V> newAsyncConsistentTreeMap(String name, Serializer serializer);

  /**
   * Creates a new set backed {@code AsyncConsistentMultimap}.
   *
   * @param name       multimap name
   * @param serializer serializer to use for serializing/deserializing
   * @param <K>        key type
   * @param <V>        value type
   * @return set backed distributedMultimap
   */
  <K, V> AsyncConsistentMultimap<K, V> newAsyncConsistentSetMultimap(String name, Serializer serializer);

  /**
   * Creates a new {@code AsyncAtomicCounterMap}.
   *
   * @param name       counter map name
   * @param serializer serializer to use for serializing/deserializing keys
   * @param <K>        key type
   * @return atomic counter map
   */
  <K> AsyncAtomicCounterMap<K> newAsyncAtomicCounterMap(String name, Serializer serializer);

  /**
   * Creates a new {@code AsyncAtomicCounter}.
   *
   * @param name counter name
   * @return counter
   */
  AsyncAtomicCounter newAsyncCounter(String name);

  /**
   * Creates a new {@code AsyncAtomixIdGenerator}.
   *
   * @param name ID generator name
   * @return ID generator
   */
  AsyncAtomicIdGenerator newAsyncIdGenerator(String name);

  /**
   * Creates a new {@code AsyncAtomicValue}.
   *
   * @param name       value name
   * @param serializer serializer to use for serializing/deserializing value type
   * @param <V>        value type
   * @return value
   */
  <V> AsyncAtomicValue<V> newAsyncAtomicValue(String name, Serializer serializer);

  /**
   * Creates a new {@code AsyncDistributedSet}.
   *
   * @param name       set name
   * @param serializer serializer to use for serializing/deserializing set entries
   * @param <E>        set entry type
   * @return set
   */
  <E> AsyncDistributedSet<E> newAsyncDistributedSet(String name, Serializer serializer);

  /**
   * Creates a new {@code AsyncLeaderElector}.
   *
   * @param name leader elector name
   * @param serializer leader elector serializer
   * @return leader elector
   */
  default <T> AsyncLeaderElector<T> newAsyncLeaderElector(String name, Serializer serializer) {
    return newAsyncLeaderElector(name, serializer, Duration.ofMillis(DEFAULT_OPERATION_TIMEOUT_MILLIS));
  }

  /**
   * Creates a new {@code AsyncLeaderElector}.
   *
   * @param name            leader elector name
   * @param serializer leader elector serializer
   * @param electionTimeout leader election timeout
   * @return leader elector
   */
  <T> AsyncLeaderElector<T> newAsyncLeaderElector(String name, Serializer serializer, Duration electionTimeout);

  /**
   * Creates a new {@code AsyncDistributedLock}.
   *
   * @param name lock name
   * @return distributed lock
   */
  default AsyncDistributedLock newAsyncDistributedLock(String name) {
    return newAsyncDistributedLock(name, Duration.ofMillis(DEFAULT_OPERATION_TIMEOUT_MILLIS));
  }

  /**
   * Creates a new {@code AsyncDistributedLock}.
   *
   * @param name lock name
   * @param timeout lock timeout
   * @return distributed lock
   */
  AsyncDistributedLock newAsyncDistributedLock(String name, Duration timeout);

  /**
   * Creates a new {@code WorkQueue}.
   *
   * @param <E>        work element type
   * @param name       work queue name
   * @param serializer serializer
   * @return work queue
   */
  <E> AsyncWorkQueue<E> newAsyncWorkQueue(String name, Serializer serializer);

  /**
   * Creates a new {@code AsyncDocumentTree}.
   *
   * @param <V>        document tree node value type
   * @param name       tree name
   * @param serializer serializer
   * @return document tree
   */
  default <V> AsyncDocumentTree<V> newAsyncDocumentTree(String name, Serializer serializer) {
    return newAsyncDocumentTree(name, serializer, Ordering.NATURAL);
  }

  /**
   * Creates a new {@code AsyncDocumentTree}.
   *
   * @param <V>        document tree node value type
   * @param name       tree name
   * @param serializer serializer
   * @param ordering   tree node ordering
   * @return document tree
   */
  <V> AsyncDocumentTree<V> newAsyncDocumentTree(String name, Serializer serializer, Ordering ordering);

  /**
   * Returns a set of primitive names for the given primitive type.
   *
   * @param primitiveType the primitive type for which to return names
   * @return a set of names of the given primitive type
   */
  Set<String> getPrimitiveNames(PrimitiveType primitiveType);
}
