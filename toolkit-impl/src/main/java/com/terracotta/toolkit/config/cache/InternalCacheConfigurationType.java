/*
 * All content copyright Terracotta, Inc., unless otherwise indicated. All rights reserved.
 */
package com.terracotta.toolkit.config.cache;

import org.terracotta.toolkit.config.Configuration;
import org.terracotta.toolkit.config.SupportedConfigurationType;
import org.terracotta.toolkit.store.ToolkitConfigFields.Consistency;
import org.terracotta.toolkit.store.ToolkitConfigFields.PinningStore;

import com.terracotta.toolkit.config.UnclusteredConfiguration;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.terracotta.toolkit.config.SupportedConfigurationType.BOOLEAN;
import static org.terracotta.toolkit.config.SupportedConfigurationType.INTEGER;
import static org.terracotta.toolkit.config.SupportedConfigurationType.LONG;
import static org.terracotta.toolkit.config.SupportedConfigurationType.STRING;
import static org.terracotta.toolkit.config.SupportedConfigurationType.getTypeForObject;
import static org.terracotta.toolkit.internal.store.ConfigFieldsInternal.DEFAULT_LOCAL_STORE_MANAGER_NAME;
import static org.terracotta.toolkit.internal.store.ConfigFieldsInternal.LOCAL_STORE_MANAGER_NAME_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.COMPRESSION_ENABLED_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.CONCURRENCY_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.CONSISTENCY_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.COPY_ON_READ_ENABLED_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_COMPRESSION_ENABLED;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_CONCURRENCY;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_CONSISTENCY;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_COPY_ON_READ_ENABLED;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_EVICTION_ENABLED;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_LOCAL_CACHE_ENABLED;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_MAX_BYTES_LOCAL_HEAP;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_MAX_BYTES_LOCAL_OFFHEAP;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_MAX_COUNT_LOCAL_HEAP;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_MAX_TOTAL_COUNT;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_MAX_TTI_SECONDS;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_MAX_TTL_SECONDS;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_OFFHEAP_ENABLED;
import static org.terracotta.toolkit.store.ToolkitConfigFields.DEFAULT_PINNING_STORE;
import static org.terracotta.toolkit.store.ToolkitConfigFields.EVICTION_ENABLED_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.LOCAL_CACHE_ENABLED_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.MAX_BYTES_LOCAL_HEAP_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.MAX_BYTES_LOCAL_OFFHEAP_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.MAX_COUNT_LOCAL_HEAP_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.MAX_TOTAL_COUNT_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.MAX_TTI_SECONDS_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.MAX_TTL_SECONDS_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.OFFHEAP_ENABLED_FIELD_NAME;
import static org.terracotta.toolkit.store.ToolkitConfigFields.PINNING_STORE_FIELD_NAME;

public enum InternalCacheConfigurationType {
  MAX_BYTES_LOCAL_HEAP(LONG, MAX_BYTES_LOCAL_HEAP_FIELD_NAME, DEFAULT_MAX_BYTES_LOCAL_HEAP) {
    @Override
    public boolean isClusterWideConfig() {
      return false;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return false;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return true;
    }

    @Override
    public void validateLegalValue(Object value) {
      greaterThanOrEqualTo(integerOrLong(notNull(value)), 0L);
    }

  },
  MAX_BYTES_LOCAL_OFFHEAP(LONG, MAX_BYTES_LOCAL_OFFHEAP_FIELD_NAME, DEFAULT_MAX_BYTES_LOCAL_OFFHEAP) {
    @Override
    public boolean isClusterWideConfig() {
      return false;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return false;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return false;
    }

    @Override
    public void validateLegalValue(Object value) {
      greaterThanOrEqualTo(integerOrLong(notNull(value)), 0L);
    }
  },
  MAX_COUNT_LOCAL_HEAP(INTEGER, MAX_COUNT_LOCAL_HEAP_FIELD_NAME, DEFAULT_MAX_COUNT_LOCAL_HEAP) {

    @Override
    public boolean isClusterWideConfig() {
      return false;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return false;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return true;
    }

    @Override
    public void validateLegalValue(Object value) {
      greaterThanOrEqualTo(integer(notNull(value)), 0);
    }
  },
  LOCAL_CACHE_ENABLED(BOOLEAN, LOCAL_CACHE_ENABLED_FIELD_NAME, DEFAULT_LOCAL_CACHE_ENABLED) {
    @Override
    public boolean isClusterWideConfig() {
      return false;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return false;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return true;
    }

    @Override
    public void validateLegalValue(Object value) {
      bool(value);
    }
  },
  OFFHEAP_ENABLED(BOOLEAN, OFFHEAP_ENABLED_FIELD_NAME, DEFAULT_OFFHEAP_ENABLED) {
    @Override
    public boolean isClusterWideConfig() {
      return false;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return false;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return false;
    }

    @Override
    public void validateLegalValue(Object value) {
      bool(value);
    }
  },
  LOCAL_STORE_MANAGER_NAME(STRING, LOCAL_STORE_MANAGER_NAME_NAME, DEFAULT_LOCAL_STORE_MANAGER_NAME) {
    @Override
    public boolean isClusterWideConfig() {
      return true;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return false;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return false;
    }

    @Override
    public void validateLegalValue(Object value) {
      notNull(value);
    }
  },
  MAX_TOTAL_COUNT(INTEGER, MAX_TOTAL_COUNT_FIELD_NAME, DEFAULT_MAX_TOTAL_COUNT) {
    @Override
    public boolean isClusterWideConfig() {
      return true;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return true;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return true;
    }

    @Override
    public void validateLegalValue(Object value) {
      greaterThanOrEqualTo(integer(notNull(value)), -1);
    }
  },
  EVICTION_ENABLED(BOOLEAN, EVICTION_ENABLED_FIELD_NAME, DEFAULT_EVICTION_ENABLED) {
    @Override
    public boolean isClusterWideConfig() {
      return true;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return true;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return true;
    }

    @Override
    public void validateLegalValue(Object value) {
      bool(value);
    }
  },
  MAX_TTI_SECONDS(INTEGER, MAX_TTI_SECONDS_FIELD_NAME, DEFAULT_MAX_TTI_SECONDS) {
    @Override
    public boolean isClusterWideConfig() {
      return true;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return true;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return true;
    }

    @Override
    public void validateLegalValue(Object value) {
      greaterThanOrEqualTo(integer(notNull(value)), 0);
    }
  },
  MAX_TTL_SECONDS(INTEGER, MAX_TTL_SECONDS_FIELD_NAME, DEFAULT_MAX_TTL_SECONDS) {
    @Override
    public boolean isClusterWideConfig() {
      return true;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return true;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return true;
    }

    @Override
    public void validateLegalValue(Object value) {
      greaterThanOrEqualTo(integer(notNull(value)), 0);
    }
  },
  CONCURRENCY(INTEGER, CONCURRENCY_FIELD_NAME, DEFAULT_CONCURRENCY) {
    @Override
    public boolean isClusterWideConfig() {
      return true;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return false;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return false;
    }

    @Override
    public void validateLegalValue(Object value) {
      greaterThan(integer(notNull(value)), 0);
    }
  },
  CONSISTENCY(STRING, CONSISTENCY_FIELD_NAME, DEFAULT_CONSISTENCY) {
    @Override
    public boolean isClusterWideConfig() {
      return true;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return false;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return false;
    }

    @Override
    public void validateLegalValue(Object value) {
      enumInstanceIn(notBlank(string(notNull(value))), Consistency.class);
    }
  },
  PINNING_STORE(STRING, PINNING_STORE_FIELD_NAME, DEFAULT_PINNING_STORE) {
    @Override
    public boolean isClusterWideConfig() {
      return true;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return false;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return false;
    }

    @Override
    public void validateLegalValue(Object value) {
      enumInstanceIn(notBlank(string(notNull(value))), PinningStore.class);
    }
  },
  COMPRESSION_ENABLED(BOOLEAN, COMPRESSION_ENABLED_FIELD_NAME, DEFAULT_COMPRESSION_ENABLED) {
    @Override
    public boolean isClusterWideConfig() {
      return true;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return false;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return false;
    }

    @Override
    public void validateLegalValue(Object value) {
      bool(value);
    }
  },
  COPY_ON_READ_ENABLED(BOOLEAN, COPY_ON_READ_ENABLED_FIELD_NAME, DEFAULT_COPY_ON_READ_ENABLED) {
    @Override
    public boolean isClusterWideConfig() {
      return true;
    }

    @Override
    public boolean isDynamicClusterWideChangeAllowed() {
      return false;
    }

    @Override
    public boolean isDynamicLocalChangeAllowed() {
      return false;
    }

    @Override
    public void validateLegalValue(Object value) {
      bool(value);
    }
  };

  private final SupportedConfigurationType                         typeSupported;
  private final String                                             configString;
  private final Object                                             defaultValue;
  private final static Map<String, InternalCacheConfigurationType> NAME_TO_TYPE_MAP;
  private final static Set<InternalCacheConfigurationType>         CLUSTER_WIDE_CONFIGS;
  private final static Set<InternalCacheConfigurationType>         LOCAL_CONFIGS;

  static {
    InternalCacheConfigurationType[] types = InternalCacheConfigurationType.values();
    // EnumSet would be more efficient here
    Set<InternalCacheConfigurationType> clusterWideConfigs = new HashSet<InternalCacheConfigurationType>();
    Set<InternalCacheConfigurationType> localConfigs = new HashSet<InternalCacheConfigurationType>();
    Map<String, InternalCacheConfigurationType> map = new HashMap<String, InternalCacheConfigurationType>();
    for (InternalCacheConfigurationType type : types) {
      map.put(type.getConfigString(), type);
      if (type.isClusterWideConfig()) {
        clusterWideConfigs.add(type);
      } else {
        localConfigs.add(type);
      }
    }
    CLUSTER_WIDE_CONFIGS = Collections.unmodifiableSet(clusterWideConfigs);
    LOCAL_CONFIGS = Collections.unmodifiableSet(localConfigs);
    NAME_TO_TYPE_MAP = Collections.unmodifiableMap(map);
  }

  public static Set<InternalCacheConfigurationType> getClusterWideConfigs() {
    return CLUSTER_WIDE_CONFIGS;
  }

  public static Set<InternalCacheConfigurationType> getLocalConfigs() {
    return LOCAL_CONFIGS;
  }

  public static InternalCacheConfigurationType getTypeFromConfigString(String configString) {
    return NAME_TO_TYPE_MAP.get(configString);
  }

  InternalCacheConfigurationType(SupportedConfigurationType typeSupported, String configStringName,
                                 Object defaultValue) {
    this.typeSupported = typeSupported;
    this.configString = configStringName;
    checkValidType(defaultValue);
    validateLegalValue(defaultValue);
    this.defaultValue = defaultValue;
  }

  private void checkValidType(Object value) {
    SupportedConfigurationType valueType = getTypeForObject(value);
    if (!typeSupported.isSupported(valueType)) {
      //
      throw new IllegalArgumentException("Illegal value for '" + name() + "' - only supports " + typeSupported
                                         + ", got (" + getTypeForObject(value) + "): " + value);
    }
  }

  public String getConfigString() {
    return this.configString;
  }

  public void setValue(UnclusteredConfiguration config, Serializable value) {
    checkValidType(value);
    config.setObject(configString, value);
  }

  /**
   * Returns the value from the config if it is present in the config. Otherwise returns null
   */
  public Serializable getValueIfExists(Configuration config) {
    return isPresentInConfig(config) ? typeSupported.getFromConfig(config, configString) : null;
  }

  /**
   * Returns the value from the config if it is present in the config. Otherwise returns default value of the config
   */
  public Object getValueIfExistsOrDefault(Configuration config) {
    return isPresentInConfig(config) ? typeSupported.getFromConfig(config, configString) : getDefaultValue();
  }

  /**
   * Returns value for this config field from the passed configuration.
   * 
   * @throws IllegalArgumentException if this config field doesn't exist in the configuration
   */
  public Object getExistingValueOrException(Configuration config) {
    Object rv = getValueIfExists(config);
    if (rv == null) {
      throw new IllegalArgumentException("Config field '" + configString + "' does not exist in the configuration - "
                                         + config);
    } else {
      validateLegalValue(rv);
      return rv;
    }
  }

  /**
   * Returns the default value
   */
  public Object getDefaultValue() {
    return defaultValue;
  }

  public boolean isDynamicChangeAllowed() {
    return isDynamicClusterWideChangeAllowed() || isDynamicLocalChangeAllowed();
  }

  private boolean isPresentInConfig(Configuration config) {
    return config.hasField(configString);
  }

  /**
   * @throws IllegalArgumentException if existingValue is different from the value for this config type (if present in
   *         the {@code newConfig})
   */
  public void validateExistingMatchesValueFromConfig(Object existingValue, Configuration newConfig) {
    Object value = getValueIfExistsOrDefault(newConfig);
    if (!existingValue.equals(value)) {
      //
      throw new IllegalArgumentException('\'' + configString + "' should be same but does not match. Existing value: "
                                         + existingValue + ", value passed in config: " + value);
    }
  }

  /**
   * This only means that this config attribute is going to remain the same across the cluster.
   */
  public abstract boolean isClusterWideConfig();

  /**
   * This means that a transaction is sent to the server to apply on the server. <br>
   * It doesn't imply it will get applied on all the clients.
   */
  public abstract boolean isDynamicClusterWideChangeAllowed();

  /**
   * When a dynamic change comes either from the server OR the client whether to apply it or not.
   */
  public abstract boolean isDynamicLocalChangeAllowed();

  /**
   * Throws {@link IllegalArgumentException} for illegal values for this config
   */
  public abstract void validateLegalValue(Object value);

  public Object notNull(Object value) {
    return Validator.notNull(name(), value);
  }

  public boolean bool(Object value) {
    return Validator.bool(name(), value);
  }

  public int integer(Object value) {
    return Validator.integer(name(), value);
  }

  public int integerOrLong(Object value) {
    return Validator.integerOrLong(name(), value);
  }

  public String string(Object value) {
    return Validator.string(name(), value);
  }

  public int greaterThan(int value, int compareWith) {
    return Validator.greaterThan(name(), value, compareWith);
  }

  public int greaterThanOrEqualTo(int value, int compareWith) {
    return Validator.greaterThanOrEqualTo(name(), value, compareWith);
  }

  public long greaterThanOrEqualTo(long value, long compareWith) {
    return Validator.greaterThanOrEqualTo(name(), value, compareWith);
  }

  public String notBlank(String value) {
    return Validator.notBlank(name(), value);
  }

  public <T extends Enum<T>> T enumInstanceIn(String value, Class<T> enumClazz) {
    return Validator.enumInstanceIn(name(), value, enumClazz);
  }
}
