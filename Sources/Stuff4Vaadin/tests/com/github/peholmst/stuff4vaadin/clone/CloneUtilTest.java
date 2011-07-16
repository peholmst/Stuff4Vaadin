/*
 * Copyright (c) 2011 Petter Holmström
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
package com.github.peholmst.stuff4vaadin.clone;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for {@link CloneUtil}.
 * 
 * @author Petter Holmström
 */
public class CloneUtilTest {

    static class CloneTestClass implements Cloneable {

        String immutableThatWillBeCopied;

        CloneTestClass mutableThatWillBeCopied;

        @CloneThis
        CloneTestClass mutableThatWillBeCloned;

        @CloneThis
        Set<CloneTestClass> collectionThatWillBeCloned;

        @CloneThis(deepClone = true)
        Set<CloneTestClass> collectionThatWillBeClonedDeeply;

        @CloneThis
        CloneTestClass[] arrayThatWillBeCloned;

        @CloneThis(deepClone = true)
        CloneTestClass[] arrayThatWillBeClonedDeeply;

        @CloneThis
        Map<String, CloneTestClass> mapThatWillBeCloned;

        @CloneThis(deepClone = true)
        Map<String, CloneTestClass> mapThatWillBeClonedDeeply;

        @Override
        public CloneTestClass clone() throws CloneNotSupportedException {
            return CloneUtil.deepClone((CloneTestClass) super.clone());
        }
    }

    CloneTestClass original;
    CloneTestClass clone;

    @Before
    public void setUp() {
        original = new CloneTestClass();
    }

    @After
    public void tearDown() {
        assertNotSame(original, clone);
    }

    @Test
    public void immutableThatWillBeCopied() throws Exception {
        original.immutableThatWillBeCopied = "Hello World";
        clone = original.clone();
        assertSame(original.immutableThatWillBeCopied,
                clone.immutableThatWillBeCopied);
    }

    @Test
    public void mutableThatWillBeCopied() throws Exception {
        original.mutableThatWillBeCopied = new CloneTestClass();
        clone = original.clone();
        assertSame(original.mutableThatWillBeCopied,
                clone.mutableThatWillBeCopied);
    }

    @Test
    public void mutableThatWillBeCloned() throws Exception {
        original.mutableThatWillBeCloned = new CloneTestClass();
        clone = original.clone();
        assertNotSame(original.mutableThatWillBeCloned,
                clone.mutableThatWillBeCloned);
    }

    @Test
    public void collectionThatWillBeCloned() throws Exception {
        original.collectionThatWillBeCloned = new HashSet<CloneTestClass>();
        original.collectionThatWillBeCloned.add(new CloneTestClass());
        clone = original.clone();
        assertNotSame(original.collectionThatWillBeCloned,
                clone.collectionThatWillBeCloned);
        assertSame(original.collectionThatWillBeCloned.iterator().next(),
                clone.collectionThatWillBeCloned.iterator().next());
    }

    @Test
    public void collectionThatWillBeClonedDeeply() throws Exception {
        original.collectionThatWillBeClonedDeeply = new HashSet<CloneTestClass>();
        original.collectionThatWillBeClonedDeeply.add(new CloneTestClass());
        clone = original.clone();
        assertNotSame(original.collectionThatWillBeClonedDeeply.iterator()
                .next(), clone.collectionThatWillBeClonedDeeply.iterator()
                .next());
    }

    @Test
    public void arrayThatWillBeCloned() throws Exception {
        original.arrayThatWillBeCloned = new CloneTestClass[] { new CloneTestClass() };
        clone = original.clone();
        assertNotSame(original.arrayThatWillBeCloned,
                clone.arrayThatWillBeCloned);
        assertSame(original.arrayThatWillBeCloned[0],
                clone.arrayThatWillBeCloned[0]);
    }

    @Test
    public void arrayThatWillBeClonedDeeply() throws Exception {
        original.arrayThatWillBeClonedDeeply = new CloneTestClass[] { new CloneTestClass() };
        clone = original.clone();
        assertNotSame(original.arrayThatWillBeClonedDeeply[0],
                clone.arrayThatWillBeClonedDeeply[0]);
    }

    @Test
    public void mapThatWillBeCloned() throws Exception {
        original.mapThatWillBeCloned = new HashMap<String, CloneTestClass>();
        original.mapThatWillBeCloned.put("key", new CloneTestClass());
        clone = original.clone();
        assertNotSame(original.mapThatWillBeCloned, clone.mapThatWillBeCloned);
        assertSame(original.mapThatWillBeCloned.get("key"),
                clone.mapThatWillBeCloned.get("key"));
    }

    @Test
    public void mapThatWillBeClonedDeeply() throws Exception {
        original.mapThatWillBeClonedDeeply = new HashMap<String, CloneTestClass>();
        original.mapThatWillBeClonedDeeply.put("key", new CloneTestClass());
        clone = original.clone();
        assertNotSame(original.mapThatWillBeClonedDeeply.get("key"),
                clone.mapThatWillBeClonedDeeply.get("key"));
    }
}
