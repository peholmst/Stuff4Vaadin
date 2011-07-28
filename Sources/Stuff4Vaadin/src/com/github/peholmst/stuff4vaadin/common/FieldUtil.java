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

import java.lang.reflect.Field;

/**
 * This is a utility class for working with {@link Field}s.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public final class FieldUtil {

	// TODO Add support for using nested fields (e.g. aa.bb.cc)

	private FieldUtil() {
	}

	/**
	 * Visitor interface for
	 * {@link FieldUtil#visitAllDeclaredFields(Class, FieldVisitor)}.
	 * 
	 * @author Petter Holmström
	 */
	public static interface FieldVisitor {

		void visitField(Field field) throws Exception;

	}

	/**
	 * Visitor class that makes sure that the field is accessible when the
	 * visitor operates on it.
	 * 
	 * @see Field#isAccessible()
	 * 
	 * @author Petter Holmström
	 */
	public static abstract class AccessibleFieldVisitor implements FieldVisitor {

		@Override
		public final void visitField(Field field) throws Exception {
			final boolean oldAccessible = field.isAccessible();
			field.setAccessible(true);
			try {
				visitAccessibleField(field);
			} finally {
				field.setAccessible(oldAccessible);
			}
		}

		/**
		 * Visits the specified field. The field is guaranteed to be accessible.
		 * 
		 * @param field
		 *            the field to visit.
		 * @throws Exception
		 *             if something goes wrong.
		 */
		public abstract void visitAccessibleField(Field field) throws Exception;
	}

	/**
	 * Visits all the fields that have been declared by the specified class or
	 * any of its superclasses.
	 * 
	 * @param startFrom
	 *            the class from which visiting should start.
	 * @param visitor
	 *            the visitor.
	 */
	public static void visitAllDeclaredFields(Class<?> startFrom,
			FieldVisitor visitor) throws Exception {
		Class<?> currentClass = startFrom;
		while (currentClass != Object.class) {
			for (Field field : currentClass.getDeclaredFields()) {
				visitor.visitField(field);
			}
			currentClass = currentClass.getSuperclass();
		}
	}

	/**
	 * Tries to find a field with the specified name that is declared by the
	 * specified class or any of its superclasses.
	 * 
	 * @param startFrom
	 *            the class to start from.
	 * @param fieldName
	 *            the name of the field to find.
	 * @return the field.
	 * @throws NoSuchFieldException
	 *             if the field could not be found.
	 */
	public static Field findDeclaredField(Class<?> startFrom, String fieldName)
			throws NoSuchFieldException {
		Class<?> currentClass = startFrom;
		while (currentClass != Object.class) {
			try {
				return currentClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
				currentClass = currentClass.getSuperclass();
			}
		}
		throw new NoSuchFieldException(fieldName);
	}

	/**
	 * Sets the value of the specified field on the specified object.
	 * 
	 * @param owner
	 *            the object on which the field will be set.
	 * @param fieldName
	 *            the name of the field.
	 * @param value
	 *            the new field value.
	 * @throws NoSuchFieldException
	 *             if the field could not be found.
	 */
	public static void setFieldValue(final Object owner,
			final String fieldName, final Object value)
			throws NoSuchFieldException {
		Field f = findDeclaredField(owner.getClass(), fieldName);
		try {
			new AccessibleFieldVisitor() {

				@Override
				public void visitAccessibleField(Field field) throws Exception {
					field.set(owner, value);
				}

			}.visitField(f);
		} catch (Exception e) {
			throw new RuntimeException("Could not set field value", e);
		}
	}

	/**
	 * Gets the value of the specified field on the specified object.
	 * 
	 * @param owner
	 *            the object from which the field value will be retrieved.
	 * @param fieldName
	 *            the name of the field.
	 * @return the field value.
	 * @throws NoSuchFieldException
	 *             if the field could not be found.
	 */
	public static Object getFieldValue(final Object owner,
			final String fieldName) throws NoSuchFieldException {
		Field f = findDeclaredField(owner.getClass(), fieldName);
		try {
			final Object[] fieldValue = new Object[1];
			new AccessibleFieldVisitor() {

				@Override
				public void visitAccessibleField(Field field) throws Exception {
					fieldValue[0] = field.get(owner);
				}

			}.visitField(f);
			return fieldValue[0];
		} catch (Exception e) {
			throw new RuntimeException("Could not get field value", e);
		}
	}
}
