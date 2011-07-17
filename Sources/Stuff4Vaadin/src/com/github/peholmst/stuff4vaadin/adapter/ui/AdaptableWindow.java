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
package com.github.peholmst.stuff4vaadin.adapter.ui;

import com.github.peholmst.stuff4vaadin.adapter.Adaptable;
import com.github.peholmst.stuff4vaadin.adapter.AdaptableSupport;
import com.github.peholmst.stuff4vaadin.adapter.UnsupportedAdapterException;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window;

/**
 * An extension of the standard Vaadin {@link Window} class that implements the
 * {@link Adaptable} interface.
 * 
 * @see #getAdaptableSupport()
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class AdaptableWindow extends Window implements Adaptable {

	private static final long serialVersionUID = 5141292669272015114L;

	private final AdaptableSupport adaptableSupport = new AdaptableSupport();

	public AdaptableWindow() {
		super();
	}

	public AdaptableWindow(String caption, ComponentContainer content) {
		super(caption, content);
	}

	public AdaptableWindow(String caption) {
		super(caption);
	}

	/**
	 * Returns the {@link AdaptableSupport} instance used by this window. This
	 * can be used to register new adapters or unregister existing ones.
	 */
	protected AdaptableSupport getAdaptableSupport() {
		return adaptableSupport;
	}

	@Override
	public boolean supportsAdapter(Class<?> adapterClass) {
		return adaptableSupport.supportsAdapter(adapterClass);
	}

	@Override
	public <T> T adapt(Class<T> adapterClass)
			throws UnsupportedAdapterException {
		return adaptableSupport.adapt(adapterClass);
	}

}
