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
package org.terracotta.offheapstore.storage.listener;

import org.terracotta.offheapstore.storage.listener.ListenableStorageEngine;
import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;

import org.terracotta.offheapstore.disk.paging.MappedPageSource;
import org.terracotta.offheapstore.disk.storage.FileBackedStorageEngine;
import org.terracotta.offheapstore.storage.portability.StringPortability;

public class FileBackedStorageEngineListenerIT extends AbstractListenerIT {

  protected File dataFile;

  @Before
  public void createDataFile() throws IOException {
    dataFile = File.createTempFile(getClass().getSimpleName(), ".data");
    dataFile.deleteOnExit();
  }

  @After
  public void destroyDataFile() {
    dataFile.delete();
  }
  
  @Override
  protected ListenableStorageEngine<String, String> createStorageEngine() {
    try {
      return new FileBackedStorageEngine<String, String>(new MappedPageSource(dataFile), StringPortability.INSTANCE, StringPortability.INSTANCE, 1024);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

}
