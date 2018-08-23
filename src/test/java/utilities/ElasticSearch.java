package utilities;

import java.util.Collections;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;


public class ElasticSearch {
	public static String getQuery(String jsonQuery) {
		try {
			RestClient restClient = RestClient.builder(new HttpHost("search-propq-dev-2coia735jvvr47ob3sxsjbu534.ap-southeast-1.es.amazonaws.com", 443, "https"))
					.build();
			HttpEntity entity = new NStringEntity(jsonQuery, ContentType.APPLICATION_JSON);
			Response indexResponse = restClient.performRequest("GET", "/20180320_01_developments/_search",
					Collections.<String, String>emptyMap(), entity);
			String retSrc = EntityUtils.toString(indexResponse.getEntity());
			restClient.close();

			return retSrc;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
