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

import java.util.List;

import org.junit.Test;

/**
 * Test case for {@link VisitableList}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class VisitableListTest extends AbstractVisitableCollectionTest {

	@Override
	protected VisitableCollection<String> createCollection() {
		return new VisitableList<String>();
	}

	@Test
	public void collectionIsList() {
		assertTrue(collection.getItems() instanceof List);
	}
}
