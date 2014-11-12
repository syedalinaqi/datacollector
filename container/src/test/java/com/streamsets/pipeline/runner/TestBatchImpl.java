/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.streamsets.pipeline.runner;

import com.google.common.collect.ImmutableList;
import com.streamsets.pipeline.api.Batch;
import com.streamsets.pipeline.api.Record;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestBatchImpl {

  @Test
  public void testBatch() {
    SourceOffsetTracker offsetTracker = Mockito.mock(SourceOffsetTracker.class);
    Mockito.when(offsetTracker.getOffset()).thenReturn("offset");
    List<Record> records = ImmutableList.of(Mockito.mock(Record.class), Mockito.mock(Record.class));
    Batch batch = new BatchImpl("i", offsetTracker, records);

    Assert.assertEquals("offset", batch.getSourceOffset());
    Iterator<Record> it = batch.getRecords();
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(records.get(0), it.next());
    Assert.assertTrue(it.hasNext());
    Assert.assertEquals(records.get(1), it.next());
    Assert.assertFalse(it.hasNext());
    try {
      it.remove();
      Assert.fail();
    } catch (UnsupportedOperationException ex) {
      //expected
    }
    try {
      batch.getRecords();
      Assert.fail();
    } catch (IllegalStateException ex) {
      //expected
    }
  }

}
