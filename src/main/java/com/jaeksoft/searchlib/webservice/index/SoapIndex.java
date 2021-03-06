/**   
 * License Agreement for OpenSearchServer
 *
 * Copyright (C) 2011-2015 Emmanuel Keller / Jaeksoft
 * 
 * http://www.open-search-server.com
 * 
 * This file is part of OpenSearchServer.
 *
 * OpenSearchServer is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * OpenSearchServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with OpenSearchServer. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/
package com.jaeksoft.searchlib.webservice.index;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.jaeksoft.searchlib.template.TemplateList;
import com.jaeksoft.searchlib.webservice.CommonResult;

@WebService(name = "Index")
public interface SoapIndex {

	public CommonResult createIndex(@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "name") String name,
			@WebParam(name = "template") TemplateList template,
			@WebParam(name = "remote_uri") String remoteURI);

	public CommonResult deleteIndex(@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "name") String name);

	public ResultIndexList indexList(@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "details") Boolean details);

	public CommonResult indexExists(@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "name") String name);

	public CommonResult closeIndex(@WebParam(name = "login") String login,
			@WebParam(name = "key") String key,
			@WebParam(name = "name") String name);

}
