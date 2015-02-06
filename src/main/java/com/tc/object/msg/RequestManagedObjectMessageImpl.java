/*
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.
 */
package com.tc.object.msg;

import com.tc.bytes.TCByteBuffer;
import com.tc.io.TCByteBufferOutputStream;
import com.tc.net.ClientID;
import com.tc.net.protocol.tcm.MessageChannel;
import com.tc.net.protocol.tcm.MessageMonitor;
import com.tc.net.protocol.tcm.TCMessageHeader;
import com.tc.net.protocol.tcm.TCMessageType;
import com.tc.object.EntityID;
import com.tc.object.ObjectRequestID;
import com.tc.object.session.SessionID;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RequestManagedObjectMessageImpl extends DSOMessageBase implements RequestManagedObjectMessage {
  private final static byte ENTITIES_REQUESTED = 1;
  private final static byte ENTITIES_REMOVED = 2;
  private final static byte REQUEST_ID                 = 4;
  private final static byte REQUESTING_THREAD_NAME     = 6;

  private Set<EntityID> requestedEntities;
  private Set<EntityID> removedEntities;
  private ObjectRequestID   requestID;
  private String            threadName;

  public RequestManagedObjectMessageImpl(SessionID sessionID, MessageMonitor monitor, TCByteBufferOutputStream out,
                                         MessageChannel channel, TCMessageType type) {
    super(sessionID, monitor, out, channel, type);
  }

  public RequestManagedObjectMessageImpl(SessionID sessionID, MessageMonitor monitor, MessageChannel channel,
                                         TCMessageHeader header, TCByteBuffer[] data) {
    super(sessionID, monitor, channel, header, data);
  }

  @Override
  protected void dehydrateValues() {
    putNVPair(ENTITIES_REQUESTED, requestedEntities.size());
    for (EntityID entityID : requestedEntities) {
      entityID.serializeTo(getOutputStream());
    }
    putNVPair(ENTITIES_REMOVED, removedEntities.size());
    for (EntityID removedEntity : removedEntities) {
      removedEntity.serializeTo(getOutputStream());
    }
    putNVPair(REQUEST_ID, this.requestID.toLong());
    putNVPair(REQUESTING_THREAD_NAME, this.threadName);
  }

  @Override
  protected boolean hydrateValue(byte name) throws IOException {
    switch (name) {
      case ENTITIES_REQUESTED:
        this.requestedEntities = new HashSet<>();
        for (int i = getIntValue(); i > 0; i--) {
          requestedEntities.add(EntityID.readFrom(getInputStream()));
        }
        return true;
      case ENTITIES_REMOVED:
        this.removedEntities = new HashSet<>();
        for (int i = getIntValue(); i > 0; i--) {
          removedEntities.add(EntityID.readFrom(getInputStream()));
        }
        return true;
      case REQUEST_ID:
        this.requestID = new ObjectRequestID(getLongValue());
        return true;
      case REQUESTING_THREAD_NAME:
        this.threadName = getStringValue();
        return true;
      default:
        return false;
    }
  }

  @Override
  public ObjectRequestID getRequestID() {
    return this.requestID;
  }

  @Override
  public Set<EntityID> getRequestedEntities() {
    return this.requestedEntities;
  }

  @Override
  public Set<EntityID> getRemoved() {
    return this.removedEntities;
  }

  @Override
  public void initialize(ObjectRequestID rid, Set<EntityID> requestedEntities, Set<EntityID> removedEntities) {
    this.requestID = rid;
    this.requestedEntities = requestedEntities;
    this.removedEntities = removedEntities;
    this.threadName = Thread.currentThread().getName();
  }

  @Override
  public String getRequestingThreadName() {
    return this.threadName;
  }

  @Override
  public ClientID getClientID() {
    return (ClientID) getSourceNodeID();
  }

  @Override
  public Object getKey() {
    return getSourceNodeID();
  }

  @Override
  public LOOKUP_STATE getLookupState() {
    return LOOKUP_STATE.CLIENT;
  }
}
