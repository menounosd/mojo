/*
 * Copyright (C) 2010 Dimitrios Menounos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package mojo.web.core;

/**
 * Thread Local web context storage.<br />
 * Works in conjunction with either WebContextFilter or WebContextInterceptor.
 */
public class WebContextHolder {

	private static final ThreadLocal<WebContextObject> context = new ThreadLocal<WebContextObject>();

	public static WebContextObject getCurrentContext() {
		return context.get();
	}

	public static void setCurrentContext(WebContextObject value) {
		context.set(value);
	}

	public static void removeCurrentContext() {
		context.remove();
	}
}