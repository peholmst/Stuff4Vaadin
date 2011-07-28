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

import org.junit.Test;

/**
 * Test case for {@link StringUtil}.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public class StringUtilTest {

	@Test
	public void toHex_EmptyArray() {
		String hex = StringUtil.toHex(new byte[0]);
		assertTrue(hex.isEmpty());
	}

	@Test
	public void toHex_SingleByte_Lower() {
		String hex = StringUtil.toHex(new byte[] { 1 });
		assertEquals("01", hex);
	}

	@Test
	public void toHex_SingleByte_Upper() {
		String hex = StringUtil.toHex(new byte[] { (byte) 255 });
		assertEquals("ff", hex);
	}

}
