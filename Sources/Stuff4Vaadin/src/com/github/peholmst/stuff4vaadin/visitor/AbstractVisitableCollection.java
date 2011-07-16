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

import java.io.Serializable;
import java.util.Collection;

/**
 * Abstract base class for {@link VisitableCollection} implementations.
 * 
 * @author Petter Holmström
 * @since 1.0
 * @param <T>
 *            the type of the visitable items.
 */
public abstract class AbstractVisitableCollection<T> implements
		VisitableCollection<T>, Serializable {

	private static final long serialVersionUID = 6324289617046585334L;

	@Override
	public void add(T item) {
		getItems().add(item);
	}

	@Override
	public void remove(T item) {
		getItems().remove(item);
	}

	/**
	 * Returns a clone of the item collection.
	 */
	protected abstract Collection<T> cloneItemCollection();

	@Override
	public void visitItems(Visitor<T> visitor) {
		final Collection<T> items = cloneItemCollection();
		for (T visitableItem : items) {
			visitor.visit(visitableItem);
		}
	}
}
