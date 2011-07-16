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

import java.util.Collection;
import java.util.HashSet;

/**
 * Implementation of {@link VisitableCollection} that uses a {@link HashSet}
 * to store the visitable items.
 * 
 * @author Petter Holmström
 * @since 1.0
 * @param <T>
 *            the type of the visitable items.
 */
public class VisitableSet<T> extends AbstractVisitableCollection<T> {

	private static final long serialVersionUID = -1797096374217880358L;
	
	private final HashSet<T> itemSet = new HashSet<T>();
	
	@Override
	public Collection<T> getItems() {
		return itemSet;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Collection<T> cloneItemCollection() {
		return (Collection<T>) itemSet.clone();
	}

}
