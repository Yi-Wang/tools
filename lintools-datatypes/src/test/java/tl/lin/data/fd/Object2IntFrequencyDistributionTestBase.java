/*
 * Lintools: tools by @lintool
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package tl.lin.data.fd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import tl.lin.data.fd.SortableEntries.Order;
import tl.lin.data.pair.PairOfObjectInt;

public class Object2IntFrequencyDistributionTestBase {
  protected void test1Common(Object2IntFrequencyDistribution<String> fd) {
    assertEquals(0, fd.get("a"));

    fd.increment("a");
    fd.increment("b");
    fd.increment("c");
    fd.increment("b");
    fd.increment("c");
    fd.increment("c");

    assertEquals(3, fd.getNumberOfEvents());
    assertEquals(6, fd.getSumOfCounts());

    assertEquals(1, fd.get("a"));
    assertEquals(2, fd.get("b"));
    assertEquals(3, fd.get("c"));

    assertEquals((double) 1 / 6, fd.computeRelativeFrequency("a"), 10e-6);
    assertEquals((double) 2 / 6, fd.computeRelativeFrequency("b"), 10e-6);
    assertEquals((double) 3 / 6, fd.computeRelativeFrequency("c"), 10e-6);

    assertEquals(Math.log((double) 1 / 6), fd.computeLogRelativeFrequency("a"), 10e-6);
    assertEquals(Math.log((double) 2 / 6), fd.computeLogRelativeFrequency("b"), 10e-6);
    assertEquals(Math.log((double) 3 / 6), fd.computeLogRelativeFrequency("c"), 10e-6);

    fd.decrement("c");

    assertEquals(3, fd.getNumberOfEvents());
    assertEquals(5, fd.getSumOfCounts());

    assertEquals(1, fd.get("a"));
    assertEquals(2, fd.get("b"));
    assertEquals(2, fd.get("c"));

    assertEquals((double) 1 / 5, fd.computeRelativeFrequency("a"), 10e-6);
    assertEquals((double) 2 / 5, fd.computeRelativeFrequency("b"), 10e-6);
    assertEquals((double) 2 / 5, fd.computeRelativeFrequency("c"), 10e-6);

    assertEquals(Math.log((double) 1 / 5), fd.computeLogRelativeFrequency("a"), 10e-6);
    assertEquals(Math.log((double) 2 / 5), fd.computeLogRelativeFrequency("b"), 10e-6);
    assertEquals(Math.log((double) 2 / 5), fd.computeLogRelativeFrequency("c"), 10e-6);

    fd.decrement("a");

    assertEquals(2, fd.getNumberOfEvents());
    assertEquals(4, fd.getSumOfCounts());

    assertEquals(0, fd.get("a"));
    assertEquals(2, fd.get("b"));
    assertEquals(2, fd.get("c"));

    assertEquals((double) 2 / 4, fd.computeRelativeFrequency("b"), 10e-6);
    assertEquals((double) 2 / 4, fd.computeRelativeFrequency("c"), 10e-6);

    assertEquals(Math.log((double) 2 / 4), fd.computeLogRelativeFrequency("b"), 10e-6);
    assertEquals(Math.log((double) 2 / 4), fd.computeLogRelativeFrequency("c"), 10e-6);

    Set<String> set = fd.keySet();
    assertEquals(2, set.size());
    assertFalse(set.contains("a"));
    assertTrue(set.contains("b"));
    assertTrue(set.contains("c"));
  }

  protected void test2Common(Object2IntFrequencyDistribution<String> fd) {
    fd.increment("a");
    fd.increment("a");
    fd.increment("b");
    fd.increment("c");

    assertEquals(3, fd.getNumberOfEvents());
    assertEquals(4, fd.getSumOfCounts());

    assertEquals(2, fd.get("a"));
    assertEquals(1, fd.get("b"));
    assertEquals(1, fd.get("c"));

    fd.set("d", 5);

    assertEquals(4, fd.getNumberOfEvents());
    assertEquals(9, fd.getSumOfCounts());

    assertEquals(2, fd.get("a"));
    assertEquals(1, fd.get("b"));
    assertEquals(1, fd.get("c"));
    assertEquals(5, fd.get("d"));

    fd.set("a", 5);

    assertEquals(4, fd.getNumberOfEvents());
    assertEquals(12, fd.getSumOfCounts());

    assertEquals(5, fd.get("a"));
    assertEquals(1, fd.get("b"));
    assertEquals(1, fd.get("c"));
    assertEquals(5, fd.get("d"));

    fd.increment("c");
    fd.increment("c");
    fd.increment("c");

    assertEquals(4, fd.getNumberOfEvents());
    assertEquals(15, fd.getSumOfCounts());

    assertEquals(5, fd.get("a"));
    assertEquals(1, fd.get("b"));
    assertEquals(4, fd.get("c"));
    assertEquals(5, fd.get("d"));

    fd.set("c", 1);

    assertEquals(4, fd.getNumberOfEvents());
    assertEquals(12, fd.getSumOfCounts());

    assertEquals(5, fd.get("a"));
    assertEquals(1, fd.get("b"));
    assertEquals(1, fd.get("c"));
    assertEquals(5, fd.get("d"));
  }

  protected void test3Common(Object2IntFrequencyDistribution<String> fd) {
    fd.increment("a");
    fd.increment("a");
    fd.increment("b");
    fd.increment("c");

    assertEquals(3, fd.getNumberOfEvents());
    assertEquals(4, fd.getSumOfCounts());

    assertEquals(2, fd.get("a"));
    assertEquals(1, fd.get("b"));
    assertEquals(1, fd.get("c"));

    fd.clear();
    assertEquals(0, fd.getNumberOfEvents());
    assertEquals(0, fd.getSumOfCounts());
  }

  protected void testFailedDecrement1Common(Object2IntFrequencyDistribution<String> fd) {
    fd.increment("a");

    assertEquals(1, fd.getNumberOfEvents());
    assertEquals(1, fd.getSumOfCounts());
    assertEquals(1, fd.get("a"));

    fd.decrement("a");

    assertEquals(0, fd.getNumberOfEvents());
    assertEquals(0, fd.getSumOfCounts());
    assertEquals(0, fd.get("a"));

    fd.decrement("a");
  }

  protected void testFailedDecrement2Common(Object2IntFrequencyDistribution<String> fd) {
    fd.increment("a", 1000);

    assertEquals(1, fd.getNumberOfEvents());
    assertEquals(1000, fd.getSumOfCounts());
    assertEquals(1000, fd.get("a"));

    fd.decrement("a", 997);

    assertEquals(1, fd.getNumberOfEvents());
    assertEquals(3, fd.getSumOfCounts());
    assertEquals(3, fd.get("a"));

    fd.decrement("a", 3);

    assertEquals(0, fd.getNumberOfEvents());
    assertEquals(0, fd.getSumOfCounts());
    assertEquals(0, fd.get("a"));

    fd.increment("a", 3);
    fd.decrement("a", 4);
  }

  protected void testMultiIncrementDecrementCommon(Object2IntFrequencyDistribution<String> fd) {
    fd.increment("a", 2);
    fd.increment("b", 3);
    fd.increment("c", 4);

    assertEquals(3, fd.getNumberOfEvents());
    assertEquals(9, fd.getSumOfCounts());

    assertEquals(2, fd.get("a"));
    assertEquals(3, fd.get("b"));
    assertEquals(4, fd.get("c"));

    fd.decrement("b", 2);

    assertEquals(3, fd.getNumberOfEvents());
    assertEquals(7, fd.getSumOfCounts());

    assertEquals(2, fd.get("a"));
    assertEquals(1, fd.get("b"));
    assertEquals(4, fd.get("c"));
  }

  protected void testGetFrequencySortedCommon(Object2IntFrequencyDistribution<String> fd) {
    fd.set("a", 5);
    fd.set("d", 2);
    fd.set("b", 5);
    fd.set("e", 2);
    fd.set("f", 1);
    fd.set("c", 5);

    assertEquals(6, fd.getNumberOfEvents());
    assertEquals(20, fd.getSumOfCounts());

    List<PairOfObjectInt<String>> list = fd.getEntries(Order.ByRightElementDescending);

    assertEquals(6, list.size());

    assertEquals("a", list.get(0).getLeftElement());
    assertEquals(5, list.get(0).getRightElement());
    assertEquals("b", list.get(1).getLeftElement());
    assertEquals(5, list.get(1).getRightElement());
    assertEquals("c", list.get(2).getLeftElement());
    assertEquals(5, list.get(2).getRightElement());
    assertEquals("d", list.get(3).getLeftElement());
    assertEquals(2, list.get(3).getRightElement());
    assertEquals("e", list.get(4).getLeftElement());
    assertEquals(2, list.get(4).getRightElement());
    assertEquals("f", list.get(5).getLeftElement());
    assertEquals(1, list.get(5).getRightElement());

    list = fd.getEntries(Order.ByRightElementAscending);

    assertEquals(6, list.size());

    assertEquals("f", list.get(0).getLeftElement());
    assertEquals(1, list.get(0).getRightElement());
    assertEquals("d", list.get(1).getLeftElement());
    assertEquals(2, list.get(1).getRightElement());
    assertEquals("e", list.get(2).getLeftElement());
    assertEquals(2, list.get(2).getRightElement());
    assertEquals("a", list.get(3).getLeftElement());
    assertEquals(5, list.get(3).getRightElement());
    assertEquals("b", list.get(4).getLeftElement());
    assertEquals(5, list.get(4).getRightElement());
    assertEquals("c", list.get(5).getLeftElement());
    assertEquals(5, list.get(5).getRightElement());

    list = fd.getEntries(Order.ByRightElementDescending, 4);

    assertEquals(4, list.size());

    assertEquals("a", list.get(0).getLeftElement());
    assertEquals(5, list.get(0).getRightElement());
    assertEquals("b", list.get(1).getLeftElement());
    assertEquals(5, list.get(1).getRightElement());
    assertEquals("c", list.get(2).getLeftElement());
    assertEquals(5, list.get(2).getRightElement());
    assertEquals("d", list.get(3).getLeftElement());
    assertEquals(2, list.get(3).getRightElement());

    list = fd.getEntries(Order.ByRightElementAscending, 4);

    assertEquals(4, list.size());

    assertEquals("f", list.get(0).getLeftElement());
    assertEquals(1, list.get(0).getRightElement());
    assertEquals("d", list.get(1).getLeftElement());
    assertEquals(2, list.get(1).getRightElement());
    assertEquals("e", list.get(2).getLeftElement());
    assertEquals(2, list.get(2).getRightElement());
    assertEquals("a", list.get(3).getLeftElement());
    assertEquals(5, list.get(3).getRightElement());
  }

  protected void testGetSortedEventsCommon(Object2IntFrequencyDistribution<String> fd) {
    fd.set("a", 1);
    fd.set("d", 3);
    fd.set("b", 4);
    fd.set("e", 7);
    fd.set("f", 9);
    fd.set("c", 2);

    assertEquals(6, fd.getNumberOfEvents());
    assertEquals(26, fd.getSumOfCounts());

    List<PairOfObjectInt<String>> list = fd.getEntries(Order.ByLeftElementAscending);

    assertEquals(6, list.size());

    assertEquals("a", list.get(0).getLeftElement());
    assertEquals(1, list.get(0).getRightElement());
    assertEquals("b", list.get(1).getLeftElement());
    assertEquals(4, list.get(1).getRightElement());
    assertEquals("c", list.get(2).getLeftElement());
    assertEquals(2, list.get(2).getRightElement());
    assertEquals("d", list.get(3).getLeftElement());
    assertEquals(3, list.get(3).getRightElement());
    assertEquals("e", list.get(4).getLeftElement());
    assertEquals(7, list.get(4).getRightElement());
    assertEquals("f", list.get(5).getLeftElement());
    assertEquals(9, list.get(5).getRightElement());

    list = fd.getEntries(Order.ByLeftElementDescending);

    assertEquals(6, list.size());

    assertEquals("f", list.get(0).getLeftElement());
    assertEquals(9, list.get(0).getRightElement());
    assertEquals("e", list.get(1).getLeftElement());
    assertEquals(7, list.get(1).getRightElement());
    assertEquals("d", list.get(2).getLeftElement());
    assertEquals(3, list.get(2).getRightElement());
    assertEquals("c", list.get(3).getLeftElement());
    assertEquals(2, list.get(3).getRightElement());
    assertEquals("b", list.get(4).getLeftElement());
    assertEquals(4, list.get(4).getRightElement());
    assertEquals("a", list.get(5).getLeftElement());
    assertEquals(1, list.get(5).getRightElement());

    list = fd.getEntries(Order.ByLeftElementAscending, 4);

    assertEquals(4, list.size());

    assertEquals("a", list.get(0).getLeftElement());
    assertEquals(1, list.get(0).getRightElement());
    assertEquals("b", list.get(1).getLeftElement());
    assertEquals(4, list.get(1).getRightElement());
    assertEquals("c", list.get(2).getLeftElement());
    assertEquals(2, list.get(2).getRightElement());
    assertEquals("d", list.get(3).getLeftElement());
    assertEquals(3, list.get(3).getRightElement());

    list = fd.getEntries(Order.ByLeftElementDescending, 4);

    assertEquals(4, list.size());

    assertEquals("f", list.get(0).getLeftElement());
    assertEquals(9, list.get(0).getRightElement());
    assertEquals("e", list.get(1).getLeftElement());
    assertEquals(7, list.get(1).getRightElement());
    assertEquals("d", list.get(2).getLeftElement());
    assertEquals(3, list.get(2).getRightElement());
    assertEquals("c", list.get(3).getLeftElement());
    assertEquals(2, list.get(3).getRightElement());
  }

  protected void testIterableCommon(Object2IntFrequencyDistribution<String> fd) {
    fd.set("a", 1);
    fd.set("d", 3);
    fd.set("b", 4);
    fd.set("e", 7);
    fd.set("f", 9);
    fd.set("c", 2);

    assertEquals(6, fd.getNumberOfEvents());
    assertEquals(26, fd.getSumOfCounts());

    SortedSet<PairOfObjectInt<String>> list = new TreeSet<PairOfObjectInt<String>>();

    for (PairOfObjectInt<String> pair : fd) {
      list.add(pair.clone());
    }

    assertEquals(6, list.size());

    Iterator<PairOfObjectInt<String>> iter = list.iterator();
    PairOfObjectInt<String> e = iter.next();
    assertEquals("a", e.getLeftElement());
    assertEquals(1, e.getRightElement());
    e = iter.next();
    assertEquals("b", e.getLeftElement());
    assertEquals(4, e.getRightElement());
    e = iter.next();
    assertEquals("c", e.getLeftElement());
    assertEquals(2, e.getRightElement());
    e = iter.next();
    assertEquals("d", e.getLeftElement());
    assertEquals(3, e.getRightElement());
    e = iter.next();
    assertEquals("e", e.getLeftElement());
    assertEquals(7, e.getRightElement());
    e = iter.next();
    assertEquals("f", e.getLeftElement());
    assertEquals(9, e.getRightElement());
  }
}