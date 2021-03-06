/*
 *
 *  The contents of this file are subject to the Terracotta Public License Version
 *  2.0 (the "License"); You may not use this file except in compliance with the
 *  License. You may obtain a copy of the License at
 *
 *  http://terracotta.org/legal/terracotta-public-license.
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 *  the specific language governing rights and limitations under the License.
 *
 *  The Covered Software is Terracotta Core.
 *
 *  The Initial Developer of the Covered Software is
 *  Terracotta, Inc., a Software AG company
 *
 */
package com.tc.net.protocol.tcm;

import com.tc.exception.TCRuntimeException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Define all the various TC Message type numbers.
 */

public final class TCMessageType {
  // //////////////////////////////////////////////
  // NOTE: Never recycle these numbers, always add a new constant
  // //////////////////////////////////////////////
  public static final int           TYPE_PING_MESSAGE                                 = 1;
  // public static final int TYPE_PONG_MESSAGE = 2;
  // public static final int           TYPE_REQUEST_ROOT_MESSAGE                         = 8;
  public static final int           TYPE_LOCK_REQUEST_MESSAGE                         = 9;
//  public static final int           TYPE_COMMIT_TRANSACTION_MESSAGE                   = 10;
  // public static final int           TYPE_REQUEST_ROOT_RESPONSE_MESSAGE                = 11;
//  public static final int           TYPE_REQUEST_MANAGED_OBJECT_MESSAGE               = 12;
//  public static final int           TYPE_REQUEST_MANAGED_OBJECT_RESPONSE_MESSAGE      = 13;
//  public static final int           TYPE_BROADCAST_TRANSACTION_MESSAGE                = 14;
//  public static final int           TYPE_OBJECT_ID_BATCH_REQUEST_MESSAGE              = 18;
//  public static final int           TYPE_OBJECT_ID_BATCH_REQUEST_RESPONSE_MESSAGE     = 19;
//  public static final int           TYPE_ACKNOWLEDGE_TRANSACTION_MESSAGE              = 24;
  public static final int           TYPE_LOCK_RESPONSE_MESSAGE                        = 26;
  public static final int           TYPE_CLIENT_HANDSHAKE_MESSAGE                     = 28;
//  public static final int           TYPE_BATCH_TRANSACTION_ACK_MESSAGE                = 29;
  public static final int           TYPE_CLIENT_HANDSHAKE_ACK_MESSAGE                 = 30;
  // public static final int TYPE_CONFIG_PUSH_MESSAGE = 31;
  // public static final int TYPE_OVERRIDE_APPLICATION_CONFIG_MESSAGE = 32;
  public static final int           TYPE_LOCK_RECALL_MESSAGE                          = 33;
  // public static final int TYPE_JMX_MESSAGE = 34;
  public static final int           TYPE_LOCK_QUERY_RESPONSE_MESSAGE                  = 35;
  // public static final int TYPE_MEMORY_DATA_STORE_REQUEST_MESSAGE = 37;
  // public static final int TYPE_MEMORY_DATA_STORE_RESPONSE_MESSAGE = 38;
  public static final int           TYPE_CLUSTER_MEMBERSHIP_EVENT_MESSAGE             = 39;
//  public static final int           TYPE_CLIENT_JMX_READY_MESSAGE                     = 40;
//  public static final int           TYPE_OBJECTS_NOT_FOUND_RESPONSE_MESSAGE           = 41;
  // public static final int TYPE_BENCH_MESSAGE = 42;
  // public static final int TYPE_LOCK_STAT_MESSAGE = 43;
  // public static final int TYPE_LOCK_STATISTICS_RESPONSE_MESSAGE = 44;
//  public static final int           TYPE_COMPLETED_TRANSACTION_LOWWATERMARK_MESSAGE   = 45;
  public static final int           TYPE_GROUP_WRAPPER_MESSAGE                        = 46;
  public static final int           TYPE_GROUP_HANDSHAKE_MESSAGE                      = 47;
  // public static final int           TYPE_NODES_WITH_OBJECTS_MESSAGE                   = 48;
  // public static final int           TYPE_NODES_WITH_OBJECTS_RESPONSE_MESSAGE          = 49;
  // public static final int           TYPE_KEYS_FOR_ORPHANED_VALUES_MESSAGE             = 50;
  // public static final int           TYPE_KEYS_FOR_ORPHANED_VALUES_RESPONSE_MESSAGE    = 51;
  // public static final int           TYPE_NODE_META_DATA_MESSAGE                       = 52;
  // public static final int           TYPE_NODE_META_DATA_RESPONSE_MESSAGE              = 53;
  // public static final int TYPE_STRIPE_ID_MAP_MESSAGE = 54;
//  public static final int           TYPE_SYNC_WRITE_TRANSACTION_RECEIVED_MESSAGE      = 55;
  // public static final int           TYPE_NODES_WITH_KEYS_MESSAGE                      = 67;
  // public static final int           TYPE_NODES_WITH_KEYS_RESPONSE_MESSAGE             = 68;
//  public static final int           TYPE_INVALIDATE_OBJECTS_MESSAGE                   = 69;
  public static final int           TYPE_CLIENT_HANDSHAKE_REFUSED_MESSAGE             = 70;
//  public static final int           TYPE_RESOURCE_MANAGER_THROTTLE_STATE_MESSAGE      = 71;
  public static final int           TYPE_LIST_REGISTERED_SERVICES_MESSAGE             = 80;
  public static final int           TYPE_LIST_REGISTERED_SERVICES_RESPONSE_MESSAGE    = 81;
  public static final int           TYPE_INVOKE_REGISTERED_SERVICE_MESSAGE            = 82;
  public static final int           TYPE_INVOKE_REGISTERED_SERVICE_RESPONSE_MESSAGE   = 83;
//  public static final int           TYPE_REQUEST_BATCH_MESSAGE                        = 84;
//  public static final int           TYPE_REQUEST_RESPONSE_MESSAGE                     = 85;
  public static final int           TYPE_VOLTRON_ENTITY_RECEIVED_RESPONSE                          = 86;
//  public static final int           TYPE_GET_ENTITY_RESPONSE_MESSAGE                  = 87;
  public static final int           TYPE_SERVER_ENTITY_MESSAGE                        = 88;
  public static final int           TYPE_SERVER_ENTITY_RESPONSE_MESSAGE               = 89;
  public static final int           TYPE_VOLTRON_ENTITY_MESSAGE                       = 90;
  public static final int           TYPE_VOLTRON_ENTITY_APPLIED_RESPONSE              = 91;
  public static final int           TYPE_VOLTRON_ENTITY_RETIRED_RESPONSE              = 92;

  public static final TCMessageType PING_MESSAGE                                      = new TCMessageType();
  public static final TCMessageType LOCK_REQUEST_MESSAGE                              = new TCMessageType();
  public static final TCMessageType LOCK_RECALL_MESSAGE                               = new TCMessageType();
  public static final TCMessageType LOCK_RESPONSE_MESSAGE                             = new TCMessageType();
  public static final TCMessageType LOCK_QUERY_RESPONSE_MESSAGE                       = new TCMessageType();
  public static final TCMessageType CLIENT_HANDSHAKE_MESSAGE                          = new TCMessageType();
  public static final TCMessageType CLIENT_HANDSHAKE_ACK_MESSAGE                      = new TCMessageType();
  public static final TCMessageType CLIENT_HANDSHAKE_REFUSED_MESSAGE                  = new TCMessageType();
  public static final TCMessageType CLUSTER_MEMBERSHIP_EVENT_MESSAGE                  = new TCMessageType();
  public static final TCMessageType GROUP_WRAPPER_MESSAGE                             = new TCMessageType();
  public static final TCMessageType GROUP_HANDSHAKE_MESSAGE                           = new TCMessageType();
  public static final TCMessageType LIST_REGISTERED_SERVICES_MESSAGE                  = new TCMessageType();
  public static final TCMessageType LIST_REGISTERED_SERVICES_RESPONSE_MESSAGE         = new TCMessageType();
  public static final TCMessageType INVOKE_REGISTERED_SERVICE_MESSAGE                 = new TCMessageType();
  public static final TCMessageType INVOKE_REGISTERED_SERVICE_RESPONSE_MESSAGE        = new TCMessageType();
  public static final TCMessageType VOLTRON_ENTITY_RECEIVED_RESPONSE                  = new TCMessageType();
  public static final TCMessageType SERVER_ENTITY_MESSAGE                             = new TCMessageType();
  public static final TCMessageType SERVER_ENTITY_RESPONSE_MESSAGE                    = new TCMessageType();
  public static final TCMessageType VOLTRON_ENTITY_MESSAGE                            = new TCMessageType();
  public static final TCMessageType VOLTRON_ENTITY_APPLIED_RESPONSE                   = new TCMessageType();
  public static final TCMessageType VOLTRON_ENTITY_RETIRED_RESPONSE                   = new TCMessageType();

  public static TCMessageType getInstance(int i) {
    return typeMap.get(i);
  }

  public int getType() {
    return this.type;
  }

  public String getTypeName() {
    return this.typeName;
  }

  @Override
  public String toString() {
    return this.typeName + " (" + this.type + ")";
  }

  // //////////////////////////////////////////////////////
  //
  // ******** You need not modify anything below *********
  //
  // //////////////////////////////////////////////////////
  private static final Map<Integer, TCMessageType> typeMap    = new HashMap<Integer, TCMessageType>();
  private static final String            typePrefix = "TYPE_";

  private int                            type;
  private String                         typeName;

  private TCMessageType() {
    // type and name are set automagically in init()
  }

  private void setType(int type) {
    this.type = type;
  }

  private void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  @Override
  public int hashCode() {
    return this.typeName.hashCode() + this.type;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof TCMessageType) {
      final TCMessageType other = (TCMessageType) obj;
      return this.typeName.equals(other.typeName) && (this.type == other.type);
    }
    return false;
  }

  /**
   * do some sanity checking on all the defined message types. Hopefully we'll catch duplicate message numbers early at
   * development time. If somehow a compiler introduces funny public static final fields in this class, then one can add
   * additional checks for naming conventions for the message types. Or maybe we shouldn't try to be smart about
   * validating the message type numbers dynamically. Maybe I need more some coffee
   */
  private static TCMessageType[] init() throws IllegalArgumentException, IllegalAccessException {
    final Field[] fields = TCMessageType.class.getDeclaredFields();

    final Map<String, Field> mtFields = new HashMap<String, Field>();
    final Map<String, Integer> intFields = new HashMap<String, Integer>();

    for (final Field field : fields) {
      final String fName = field.getName();

      final int modifiers = field.getModifiers();

      // disallow public non-final fields
      if (Modifier.isPublic(modifiers) && !Modifier.isFinal(modifiers)) { throw new RuntimeException(
                                                                                                     "TCMessageType: "
                                                                                                         + fName
                                                                                                         + " must be final if public"); }

      final boolean shouldInspect = Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)
                                    && Modifier.isFinal(modifiers);

      if (!shouldInspect) {
        continue;
      }

      if (field.getType().equals(TCMessageType.class)) {
        if (!fName.toUpperCase().equals(fName)) {
          // make formatter sane
          throw new RuntimeException("TCMessageType: Message type names must be all UPPER CASE: " + fName);
        }

        if (fName.startsWith("_")) {
          // make formatter sane
          throw new RuntimeException("TCMessageType: Message type cannot start with underscore: " + fName);
        }

        mtFields.put(fName, field);
      } else if (field.getType().equals(Integer.TYPE)) {
        if (!fName.startsWith(typePrefix)) {
          // <Hi there> If this is being thrown, you probably added an oddly
          // named static final integer to this class
          throw new RuntimeException("TCMessageType: Illegal integer field name: " + fName);
        }

        final Integer value = (Integer) field.get(TCMessageType.class);

        intFields.put(fName, value);
      }
    }

    for (final Iterator<Field> iter = mtFields.values().iterator(); iter.hasNext();) {
      final Field field = iter.next();
      final String name = field.getName();
      final TCMessageType type = (TCMessageType) field.get(TCMessageType.class);

      final String intName = typePrefix + name;
      if (!intFields.containsKey(intName)) {
        // make formatter sane
        throw new RuntimeException("TCMessageType: Missing " + intName + " integer constant");
      }

      final int val = intFields.remove(intName).intValue();

      type.setType(val);
      type.setTypeName(name);

      final Object prev = typeMap.put(type.getType(), type);
      if (prev != null) {
        // make formatter sane
        throw new RuntimeException("TCMessageType: Duplicate message types defined for message number: "
                                   + type.getType());
      }

      iter.remove();
    }

    if (!mtFields.isEmpty()) {
      // make formatter sane
      throw new RuntimeException("TCMessageType: internal error - not all message types filled in");
    }

    if (!intFields.isEmpty()) {
      final String unused = intFields.keySet().toString();
      throw new RuntimeException("TCMessageType: Unused integer constants (please remove): " + unused);
    }

    final TCMessageType[] rv = typeMap.values().toArray(new TCMessageType[0]);
    
    Arrays.sort(rv, new Comparator<TCMessageType>() {
      @Override
      public int compare(TCMessageType o1, TCMessageType o2) {
        final int i1 = o1.getType();
        final int i2 = o2.getType();

        if (i1 < i2) {
          return -1;
        } else if (i1 == i2) {
          return 0;
        } else if (i1 > i2) {
          return 1;
        } else {
          throw new RuntimeException("internal error");
        }
      }
    });

    return rv;
  }
  
  static {
    try {
      init();
    } catch (final Exception e) {
      e.printStackTrace();
      throw new TCRuntimeException(e);
    }
  }

}
