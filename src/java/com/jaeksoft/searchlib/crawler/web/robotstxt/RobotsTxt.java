/**   
 * License Agreement for Jaeksoft WebSearch
 *
 * Copyright (C) 2008 Emmanuel Keller / Jaeksoft
 * 
 * http://www.jaeksoft.com
 * 
 * This file is part of Jaeksoft WebSearch.
 *
 * Jaeksoft WebSearch is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * Jaeksoft WebSearch is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jaeksoft WebSearch. 
 *  If not, see <http://www.gnu.org/licenses/>.
 **/

package com.jaeksoft.searchlib.crawler.web.robotstxt;

import java.net.MalformedURLException;
import java.net.URL;

import com.jaeksoft.searchlib.crawler.web.database.RobotsTxtStatus;
import com.jaeksoft.searchlib.crawler.web.database.UrlItem;
import com.jaeksoft.searchlib.crawler.web.spider.Crawl;

public class RobotsTxt {

	private long expiredTime;

	private DisallowList disallowList;

	private Crawl crawl;

	protected RobotsTxt(Crawl crawl) {
		this.expiredTime = System.currentTimeMillis() + 1000 * 60 * 60 * 24;
		this.disallowList = (DisallowList) crawl.getParser();
		this.crawl = crawl;
	}

	/**
	 * Construit l'URL d'acc�s au fichier robots.txt � partir d'une URL donn�e
	 * 
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	protected static URL getRobotsUrl(URL url) throws MalformedURLException {
		String sUrl = url.getProtocol() + "://" + url.getHost() + ":"
				+ (url.getPort() == -1 ? url.getDefaultPort() : url.getPort())
				+ "/robots.txt";
		return new URL(sUrl);
	}

	/**
	 * Renvoie false si le robots.txt ne permet pas le crawl de l'url pass�e en
	 * param�tre pour le userAgent indiqu�
	 * 
	 * @param url
	 * @param userAgent
	 * @return
	 * @throws MalformedURLException
	 */
	public RobotsTxtStatus status(String userAgent)
			throws MalformedURLException {
		UrlItem urlItem = crawl.getUrlItem();
		Integer code = urlItem.getResponseCode();
		if (code == null)
			return RobotsTxtStatus.ERROR;
		switch (code) {
		case 404:
			return RobotsTxtStatus.NO_ROBOTSTXT;
		case 200:
			break;
		default:
			return RobotsTxtStatus.ERROR;
		}
		if (disallowList == null)
			return RobotsTxtStatus.ALLOW;
		DisallowSet disallowSet = disallowList.get(userAgent.toLowerCase());
		if (disallowSet == null)
			disallowSet = disallowList.get("*");
		if (disallowSet == null)
			return RobotsTxtStatus.ALLOW;
		if (disallowSet.isAllowed(urlItem.getURL()))
			return RobotsTxtStatus.ALLOW;
		return RobotsTxtStatus.DISALLOW;
	}

	/**
	 * Retourne la date d'expiration. Lorsque la date est expir�e, le robots.txt
	 * est � nouveau t�l�charg�.
	 * 
	 * @return
	 */
	protected long getExpiredTime() {
		return this.expiredTime;
	}

	protected Crawl getCrawl() {
		return crawl;
	}

}
