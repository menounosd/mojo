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
package mojo.view.rest;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mojo.core.DataPage;
import mojo.core.DataService;
import mojo.core.spec.Batch;
import mojo.core.spec.Select;

public class DataController<E> {

	private DataService<E> service;

	public DataService<E> getService() {
		return service;
	}

	public void setService(DataService<E> service) {
		this.service = service;
	}

	@ResponseBody
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public E doFind(@PathVariable Integer id) {
		return service.findById(id);
	}

	@ResponseBody
	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public DataPage<E> doFetch(@RequestBody Select<E> spec) {
		return service.select(spec);
	}

	@ResponseBody
	@RequestMapping(value = "/select-view", method = RequestMethod.GET)
	public DataPage<?> doFetchView(@RequestBody Select<E> spec, @RequestParam String view) {
		try {
			DataPage<E> page = service.select(spec);
			List<Object> views = new ArrayList<Object>(page.getData().size());
			Class<?> viewClass = Class.forName(view);

			for (E entity : page.getData()) {
				Class<?> entityClass = entity.getClass();
				Constructor<?> viewConstructor = viewClass.getConstructor(entityClass);
				Object obj = viewConstructor.newInstance(entity);
				views.add(obj);
			}

			DataPage<Object> result = new DataPage<Object>();
			result.setTotal(page.getTotal());
			result.setData(views);
			return result;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/persist", method = RequestMethod.POST)
	public int doPersist(@RequestBody Batch<E> batch) {
		service.persist(batch);
		return 0;
	}
}
