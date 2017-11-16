/*
 * Copyright 2017-present Open Networking Foundation
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
package io.atomix.protocols.raft.protocol;

import com.google.common.collect.Maps;
import io.atomix.cluster.NodeId;
import io.atomix.serializer.Serializer;

import java.util.Map;

/**
 * Test Raft protocol factory.
 */
public class LocalRaftProtocolFactory {
  private final Serializer serializer;
  private final Map<NodeId, LocalRaftServerProtocol> servers = Maps.newConcurrentMap();
  private final Map<NodeId, LocalRaftClientProtocol> clients = Maps.newConcurrentMap();

  public LocalRaftProtocolFactory(Serializer serializer) {
    this.serializer = serializer;
  }

  /**
   * Returns a new test client protocol.
   *
   * @param nodeId the client member identifier
   * @return a new test client protocol
   */
  public RaftClientProtocol newClientProtocol(NodeId nodeId) {
    return new LocalRaftClientProtocol(nodeId, serializer, servers, clients);
  }

  /**
   * Returns a new test server protocol.
   *
   * @param nodeId the server member identifier
   * @return a new test server protocol
   */
  public RaftServerProtocol newServerProtocol(NodeId nodeId) {
    return new LocalRaftServerProtocol(nodeId, serializer, servers, clients);
  }
}
