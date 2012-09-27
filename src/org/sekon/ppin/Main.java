package org.sekon.ppin;

import java.net.MalformedURLException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;


public class Main {
	public static void main(String[] args) {
		String url = "http://kitap.tatar.ru/solr/";
		HttpSolrServer solr = new HttpSolrServer( url );
//		solr.setDefaultMaxConnectionsPerHost(32);
//		solr.setMaxTotalConnections(32);
//		solr.setFollowRedirects(false);
		ModifiableSolrParams params = new ModifiableSolrParams();
	    params.set("q", "*:*");
//	    params.set("start", "0");
	    QueryResponse response = null;
		try {
			response = solr.query(params);
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    SolrDocumentList results = response.getResults();
	    for (SolrDocument doc: results){
	    	System.out.println(doc.getFieldValues("author_t"));
	    	System.out.println(doc.getFieldValues("author_t"));
	    }
	}
}
