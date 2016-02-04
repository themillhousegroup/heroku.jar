package com.heroku.api;

import org.testng.annotations.Test;
import com.heroku.api.http.Http;

import com.heroku.api.parser.*;
import java.util.*;
import com.heroku.api.Release;
import com.google.common.collect.Lists;

import com.heroku.api.request.releases.ListReleases;

import org.testng.annotations.DataProvider;

import static org.testng.Assert.assertEquals;

/**
 * Tests for parsing of the releases list, which changed in 2015 and is not a Map<String, String> any more 
 *
 * @author John Marshall 
 */
public class ListReleasesTest {

    @DataProvider
    Object[][] getParsers() {
        return new Object[][]{{new GsonParser()}, {new JacksonParser()}};
    }


		ListReleases testInstance = new ListReleases("test");

    @Test
    public void parsingEmptyJSONArrayShouldResultInEmptyList() {
        assertEquals(testInstance.getResponse("[]".getBytes(), Http.Status.OK.statusCode), new ArrayList<Release>());
    }

		private Release buildRelease(String name, String creationDate, String desc) {
			Release r = new Release();
			r.name = name;
			r.created_at = creationDate;
			r.descr = desc;
			r.user = "user@gmail.com";
			return r;
		}

    @Test
    public void parsingLegacyJSONArrayShouldNotCauseParseException() {
			String realJson = "[" + 
" {\"addons\":[],\"commit\":null,\"created_at\":\"2016/01/26 21:05:28 -0800\",\"descr\":\"Initial release\",\"env\":{},\"name\":\"v1\",\"pstable\":{},\"user\":\"user@gmail.com\"}," +
"{\"addons\":[],\"commit\":null,\"created_at\":\"2016/01/26 21:05:28 -0800\",\"descr\":\"Enable Logplex\",\"env\":{},\"name\":\"v2\",\"pstable\":{},\"user\":\"user@gmail.com\"}," +
"{\"addons\":[],\"commit\":null,\"created_at\":\"2016/02/01 12:20:04 -0800\",\"descr\":\"Set JAVA_OPTS config vars\",\"env\":{\"JAVA_OPTS\":\"-Xss512k\"},\"name\":\"v3\",\"pstable\":{},\"user\":\"user@gmail.com\"}," +
"{\"addons\":[\"heroku-postgresql:hobby-dev\"],\"commit\":null,\"created_at\":\"2016/02/01 12:20:04 -0800\",\"descr\":\"Attach DATABASE resource\",\"env\":{\"JAVA_OPTS\":\"-Xss512k\",\"DATABASE_URL\":\"postgres://foo:bar@ec2\"},\"name\":\"v4\",\"pstable\":{},\"user\":\"user@gmail.com\"}" +
"]";


				List<Release> expected = Lists.newArrayList(
					buildRelease("v1", "2016/01/26 21:05:28 -0800", "Initial release"),
					buildRelease("v2", "2016/01/26 21:05:28 -0800", "Enable Logplex"),
					buildRelease("v3", "2016/02/01 12:20:04 -0800", "Set JAVA_OPTS config vars"),
					buildRelease("v4", "2016/02/01 12:20:04 -0800", "Attach DATABASE resource")
				);
        assertEquals(testInstance.getResponse(realJson.getBytes(), Http.Status.OK.statusCode), expected);
    }

    @Test
    public void parsingActual2015JSONArrayShouldNotCauseParseException() {
			String realJson = "[" + 
"{\"addons\":[\"heroku-postgresql:hobby-dev\"],\"commit\":\"245d556\",\"created_at\":\"2016/02/01 19:47:06 -0800\",\"descr\":\"Deploy 245d556\",\"env\":{\"JAVA_OPTS\":\"-Xss512k\",\"DATABASE_URL\":\"postgres://foo:bar@ec2\"},\"name\":\"v10\",\"pstable\":{\"web\":{\"command\":\"runme.sh -Dhttp.port=$PORT -Dconfig.resource=$HEROKU_ENV.conf\",\"slug\":{\"id\":\"0596d1f0-0c3e-4548-96d9-8f41cd95596a\"}}},\"user\":\"user@gmail.com\"}," +
"{\"addons\":[\"heroku-postgresql:hobby-dev\"],\"commit\":\"b6e509e\",\"created_at\":\"2016/02/02 04:19:24 -0800\",\"descr\":\"Deploy b6e509e\",\"env\":{\"JAVA_OPTS\":\"-Xss512k\",\"DATABASE_URL\":\"postgres://foo:bar@ec2\"},\"name\":\"v11\",\"pstable\":{\"web\":{\"command\":\"runme.sh -Dhttp.port=$PORT -Dconfig.resource=$HEROKU_ENV.conf\",\"slug\":{\"id\":\"fd046fcd-dbef-4c25-8f4a-fa6290e7084b\"}}},\"user\":\"user@gmail.com\"}," +
"{\"addons\":[\"heroku-postgresql:hobby-dev\"],\"commit\":\"b6e509e\",\"created_at\":\"2016/02/02 15:07:50 -0800\",\"descr\":\"Set EXTRA_INFO, BUILD_ID, HEROKU_ENV, BUILD_NUMBER, GIT_COMMIT config vars\",\"env\":{\"JAVA_OPTS\":\"-Xss512k\",\"DATABASE_URL\":\"postgres://foo:bar@ec2\",\"EXTRA_INFO\":\"set by Heroku Jenkins plugin\",\"BUILD_ID\":\"13\",\"HEROKU_ENV\":\"prod\",\"BUILD_NUMBER\":\"13\",\"GIT_COMMIT\":\"d5a25e0a82f0f03dbc65ffe2bba9435e36869173\"},\"name\":\"v12\",\"pstable\":{\"web\":{\"command\":\"runme.sh -Dhttp.port=$PORT -Dconfig.resource=$HEROKU_ENV.conf\",\"slug\":{\"id\":\"43711f30-de83-43dc-9d87-16e07b4a72f3\"}}},\"user\":\"user@gmail.com\"}" +
"]";

				List<Release> expected = Lists.newArrayList(
					buildRelease("v10", "2016/02/01 19:47:06 -0800", "Deploy 245d556"),
					buildRelease("v11", "2016/02/02 04:19:24 -0800", "Deploy b6e509e"),
					buildRelease("v12", "2016/02/02 15:07:50 -0800", "Set EXTRA_INFO, BUILD_ID, HEROKU_ENV, BUILD_NUMBER, GIT_COMMIT config vars")
				);
        assertEquals(testInstance.getResponse(realJson.getBytes(), Http.Status.OK.statusCode), expected);
    }

}
