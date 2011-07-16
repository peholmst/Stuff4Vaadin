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
package com.github.peholmst.stuff4vaadin.visitor;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * Base class for {@link VisitableListTest} and {@link VisitableSetTest}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public abstract class AbstractVisitableCollectionTest {

	protected abstract VisitableCollection<String> createCollection();

	protected VisitableCollection<String> collection;

	protected static class StringVisitor implements Visitor<String> {

		public final LinkedList<String> visitedItems = new LinkedList<String>();
		
		@Override
		public void visit(String visitable) {
			visitedItems.add(visitable);
		}
		
	}
	
	@Before
	public void setUp() {
		collection = createCollection();
	}
	
	@Test
	public void add() {
		collection.add("A string");
		assertTrue(collection.getItems().contains("A string"));
		assertEquals(1, collection.getItems().size());
	}

	@Test
	public void remove() {
		collection.add("A string");
		collection.remove("A string");
		assertEquals(0, collection.getItems().size());		
	}
	
	@Test
	public void addAndVisit() {
		final StringVisitor visitor = new StringVisitor();
		collection.add("A string");
		collection.visitItems(visitor);
		assertTrue(visitor.visitedItems.contains("A string"));
	}

	@Test
	public void removeItemWhileVisiting() {
		final StringVisitor visitor = new StringVisitor() {
			@Override
			public void visit(String visitable) {
				super.visit(visitable);
				collection.remove(visitable);
			}
		};
		collection.add("A string");
		collection.add("Another string");
		collection.visitItems(visitor);
		assertEquals(0, collection.getItems().size());		
		assertTrue(visitor.visitedItems.contains("A string"));
		assertTrue(visitor.visitedItems.contains("Another string"));		
	}
	
}
