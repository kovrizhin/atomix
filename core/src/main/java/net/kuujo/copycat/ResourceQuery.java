/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.kuujo.copycat;

import net.kuujo.copycat.raft.ConsistencyLevel;
import net.kuujo.copycat.raft.Operation;
import net.kuujo.copycat.raft.Query;

/**
 * Resource query.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public class ResourceQuery<T extends Query<U>, U> extends ResourceOperation<T, U> implements Query<U> {

  /**
   * Returns a new resource query builder.
   *
   * @return A new resource query builder.
   */
  @SuppressWarnings("unchecked")
  public static <T extends Query<U>, U> Builder<T, U> builder() {
    return Operation.builder(Builder.class);
  }

  @Override
  public ConsistencyLevel consistency() {
    return operation.consistency();
  }

  /**
   * Resource command builder.
   */
  public static class Builder<T extends Query<U>, U> extends Query.Builder<Builder<T, U>, ResourceQuery<T, U>> {
    @Override
    protected ResourceQuery<T, U> create() {
      return new ResourceQuery<>();
    }

    /**
     * Sets the resource ID.
     *
     * @param resource The resource ID.
     * @return The resource query builder.
     */
    public Builder withResource(long resource) {
      query.resource = resource;
      return this;
    }

    /**
     * Sets the resource command.
     *
     * @param query The resource command.
     * @return The resource command builder.
     */
    public Builder withQuery(T query) {
      this.query.operation = query;
      return this;
    }
  }

}