/* 
 * Copyright 2015 Terracotta, Inc., a Software AG company.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terracotta.offheapstore;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.terracotta.offheapstore.paging.PageSource;
import org.terracotta.offheapstore.storage.StorageEngine;

/**
 * An exclusive-read/write off-heap clock cache.
 * <p>
 * This cache uses one of the unused bits in the off-heap entry's status value to
 * store the clock data.  This clock data is safe to update during read
 * operations since the cache provides exclusive-read/write characteristics.
 * Since clock eviction data resides in the hash-map's table, it is correctly
 * copied across during table resize operations.
 * <p>
 * The cache uses a regular {@code ReentrantLock} to provide exclusive read and
 * write operations.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author Chris Dennis
 */
public class WriteLockedOffHeapClockCache<K, V> extends AbstractOffHeapClockCache<K, V> {

  private final Lock lock = new ReentrantLock();

  public WriteLockedOffHeapClockCache(PageSource source, StorageEngine<? super K, ? super V> storageEngine) {
    super(source, storageEngine);
  }

  public WriteLockedOffHeapClockCache(PageSource source, boolean tableAllocationsSteal, StorageEngine<? super K, ? super V> storageEngine) {
    super(source, tableAllocationsSteal, storageEngine);
  }

  public WriteLockedOffHeapClockCache(PageSource source, StorageEngine<? super K, ? super V> storageEngine, int tableSize) {
    super(source, storageEngine, tableSize);
  }
  
  public WriteLockedOffHeapClockCache(PageSource source, boolean tableAllocationsSteal, StorageEngine<? super K, ? super V> storageEngine, int tableSize) {
    super(source, tableAllocationsSteal, storageEngine, tableSize);
  }
  
  @Override
  public Lock readLock() {
    return lock;
  }

  @Override
  public Lock writeLock() {
    return lock;
  }

  @Override
  public ReentrantReadWriteLock getLock() {
    throw new UnsupportedOperationException();
  }
}
