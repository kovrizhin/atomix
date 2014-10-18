/*
 * Copyright 2014 the original author or authors.
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
package net.kuujo.copycat.protocol;

import net.kuujo.copycat.cluster.EventBusMember;
import net.kuujo.copycat.internal.util.Assert;
import net.kuujo.copycat.spi.protocol.AsyncProtocol;
import net.kuujo.copycat.spi.protocol.AsyncProtocolClient;
import net.kuujo.copycat.spi.protocol.AsyncProtocolServer;
import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.impl.DefaultVertx;

import java.util.concurrent.CountDownLatch;

/**
 * Vert.x event bus protocol implementation.
 *
 * @author <a href="http://github.com/kuujo">Jordan Halterman</a>
 */
public class VertxEventBusProtocol implements AsyncProtocol<EventBusMember> {
  private String host;
  private int port;
  private Vertx vertx;

  public VertxEventBusProtocol() {
  }

  public VertxEventBusProtocol(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public VertxEventBusProtocol(Vertx vertx) {
    this.vertx = vertx;
  }

  /**
   * Sets the Vert.x instance.
   *
   * @param vertx The Vert.x instance.
   */
  public void setVertx(Vertx vertx) {
    this.vertx = vertx;
  }

  /**
   * Returns the Vert.x instance.
   *
   * @return The Vert.x instance.
   */
  public Vertx getVertx() {
    return vertx;
  }

  /**
   * Sets the Vert.x instance, returning the protocol for method chaining.
   *
   * @param vertx The Vert.x instance.
   * @return The event bus protocol.
   */
  public VertxEventBusProtocol withVertx(Vertx vertx) {
    this.vertx = vertx;
    return this;
  }

  /**
   * Sets the Vert.x host.
   *
   * @param host The Vert.x host.
   */
  public void setHost(String host) {
    this.host = host;
  }

  /**
   * Returns the Vert.x host.
   *
   * @return The Vert.x host.
   */
  public String getHost() {
    return host;
  }

  /**
   * Sets the Vert.x host, returning the event bus protocol for method chaining.
   *
   * @param host The Vert.x host.
   * @return The event bus protocol.
   */
  public VertxEventBusProtocol withHost(String host) {
    this.host = host;
    return this;
  }

  /**
   * Sets the Vert.x port.
   *
   * @param port The Vert.x port.
   */
  public void setPort(int port) {
    this.port = port;
  }

  /**
   * Returns the Vert.x port.
   *
   * @return The Vert.x port.
   */
  public int getPort() {
    return port;
  }

  /**
   * Sets the Vert.x port, returning the protocol for method chaining.
   *
   * @param port The Vert.x port.
   * @return The event bus protocol.
   */
  public VertxEventBusProtocol withPort(int port) {
    this.port = port;
    return this;
  }

  /**
   * Creates a Vert.x instance.
   */
  private Vertx createVertx() {
    final CountDownLatch latch = new CountDownLatch(1);
    new DefaultVertx(port > 0 ? port : 0, Assert.isNotNull(host, "Vert.x host cannot be null"), new Handler<AsyncResult<Vertx>>() {
      @Override
      public void handle(AsyncResult<Vertx> result) {
        VertxEventBusProtocol.this.vertx = result.result();
        latch.countDown();
      }
    });
    try {
      latch.await();
    } catch (InterruptedException e) {
      throw new ProtocolException(e);
    }
    return vertx;
  }

  @Override
  public synchronized AsyncProtocolServer createServer(EventBusMember member) {
    if (vertx != null) {
      return new VertxEventBusProtocolServer(member.address(), vertx);
    } else {
      return new VertxEventBusProtocolServer(member.address(), createVertx());
    }
  }

  @Override
  public synchronized AsyncProtocolClient createClient(EventBusMember member) {
    if (vertx != null) {
      return new VertxEventBusProtocolClient(member.address(), vertx);
    } else {
      return new VertxEventBusProtocolClient(member.address(), createVertx());
    }
  }

}