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
package com.github.peholmst.stuff4vaadin.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.HashSet;

import org.junit.Test;

import com.github.peholmst.stuff4vaadin.common.FieldUtil.FieldVisitor;

/**
 * Test case for {@link FieldUtil}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class FieldUtilTest {

	static class Superclass {
		@SuppressWarnings("unused")
		private String superclassField;
	}

	static class Subclass extends Superclass {
		private String subclassField;
	}

	@Test
	public void findDeclaredFieldInSameClass() throws Exception {
		FieldUtil.findDeclaredField(Subclass.class, "subclassField");
		// If no exception is thrown, the method succeeded
	}

	@Test
	public void findDeclaredFieldInSuperclass() throws Exception {
		FieldUtil.findDeclaredField(Subclass.class, "superclassField");
		// If no exception is thrown, the method succeeded
	}

	@Test(expected = NoSuchFieldException.class)
	public void findDeclaredFieldThatDoesNotExist() throws Exception {
		FieldUtil.findDeclaredField(Subclass.class, "nonExistent");
	}

	@Test
	public void setFieldValue() throws Exception {
		Subclass owner = new Subclass();
		FieldUtil.setFieldValue(owner, "subclassField", "hello");
		assertEquals("hello", owner.subclassField);
	}

	@Test
	public void getFieldValue() throws Exception {
		Subclass owner = new Subclass();
		owner.subclassField = "hello";
		assertEquals("hello", FieldUtil.getFieldValue(owner, "subclassField"));
	}

	@Test
	public void visitAllDeclaredFields() throws Exception {
		final HashSet<String> visitedFields = new HashSet<String>();
		FieldUtil.visitAllDeclaredFields(Subclass.class, new FieldVisitor() {

			@Override
			public void visitField(Field field) throws Exception {
				visitedFields.add(field.getName());
			}
		});
		assertTrue(visitedFields.contains("subclassField"));
		assertTrue(visitedFields.contains("superclassField"));
	}
}
