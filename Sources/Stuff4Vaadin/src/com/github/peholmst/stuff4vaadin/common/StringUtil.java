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

/**
 * This is a utility class for working with strings.
 * 
 * @author Petter Holmström
 * @since 1.0
 */
public final class StringUtil {

	private StringUtil() {
	}

	private static final char[] HEX_DIGITS = new char[] { '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * Converts the byte array into a hexadecimal string.
	 * 
	 * @param bytes
	 *            the byte array.
	 * @return the hexadecimal string
	 */
	public static final String toHex(byte[] bytes) {
		// Algorithm is copied from
		// http://forums.xkcd.com/viewtopic.php?f=11&t=16666&p=553936.
		final char[] out = new char[bytes.length * 2];
		for (int i = 0; i < bytes.length; i++) {
			out[2 * i] = HEX_DIGITS[bytes[i] < 0 ? 8 + (bytes[i] + 128) / 16
					: bytes[i] / 16]; // append sign bit for negative bytes
			out[2 * i + 1] = HEX_DIGITS[bytes[i] < 0 ? (bytes[i] + 128) % 16
					: bytes[i] % 16];
		}
		return new String(out); // char sequence to string
	}
}
