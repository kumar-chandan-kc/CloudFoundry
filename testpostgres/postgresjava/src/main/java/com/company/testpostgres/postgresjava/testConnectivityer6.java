package com.company.testpostgres.postgresjava;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.cloudfoundry.identity.client.UaaContext;
import org.cloudfoundry.identity.client.UaaContextFactory;
import org.cloudfoundry.identity.client.token.GrantType;
import org.cloudfoundry.identity.client.token.TokenRequest;
import org.cloudfoundry.identity.uaa.oauth.token.CompositeAccessToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * Servlet implementation class testConnectivityer6
 */
@WebServlet(description = "testing abap connection to er6", urlPatterns = { "/testConnectivityer6" })
public class testConnectivityer6 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public testConnectivityer6() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			HttpURLConnection urlConnection = null;
			// fetching variable values env variables
			
			// 36-39 xsuua env vars
			JSONObject jsonObj = new JSONObject(System.getenv("VCAP_SERVICES"));
			JSONArray jsonArr = jsonObj.getJSONArray("xsuaa");
			JSONObject jsoncredentials = jsonArr.getJSONObject(0).getJSONObject("credentials");
			URI xsUaaUrl = new URI(jsoncredentials.getString("url"));
			response.getWriter().println(jsoncredentials + "\n");
			response.getWriter().println(xsUaaUrl+ "\n");
			
			// 42-47 connectivity env var
			JSONArray jsonArrConnect = jsonObj.getJSONArray("connectivity");
			JSONObject jsoncredentialsConn = jsonArrConnect.getJSONObject(0).getJSONObject("credentials");
			String onprem_proxy_host = jsoncredentialsConn.getString("onpremise_proxy_host");
			int onprem_proxy_port = Integer.parseInt(jsoncredentialsConn.getString("onpremise_proxy_port"));
			String clientId = jsoncredentialsConn.getString("clientid");
			String clientSecret = jsoncredentialsConn.getString("clientsecret");
			response.getWriter().println(onprem_proxy_host + "\t" + onprem_proxy_port + "\n");
		
			
			// fetching a token from xsuaa using connectivity client id, secret which is needed by connectivity service 57-64
			UaaContextFactory factory = UaaContextFactory.factory(xsUaaUrl).authorizePath("/oauth/authorize").tokenPath("/oauth/token");
			TokenRequest tokenRequest = factory.tokenRequest();
			response.getWriter().println("worked till factory");
			tokenRequest.setGrantType(GrantType.CLIENT_CREDENTIALS);
			tokenRequest.setClientId(clientId);
			tokenRequest.setClientSecret(clientSecret);
			response.getWriter().println("token request got created");
			UaaContext xsUaaContext = factory.authenticate(tokenRequest);
			response.getWriter().println("worked till here");
			CompositeAccessToken jwtToken1 = xsUaaContext.getToken();
			
			response.getWriter().println("jwtToken1 received" + "\n");
			// creating url object for calling er9 backend
			String s = "";
//			s = request.getParameter("test");
			URL url;
			if(s.equals(""))
			{ url = new URL(
					"http://ldai2er6.wdf.sap.corp:44300/sap/opu/odata/sap/API_PRODUCT_SRV/A_Product?sap-client=200");}
			else
			{
				 url = new URL(s);
			}
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(onprem_proxy_host, onprem_proxy_port));
			urlConnection = (HttpURLConnection) url.openConnection(proxy);
			
			response.getWriter().println(urlConnection + "\t proxy + connection worked");
			//adding the token fetched in er9 request
			urlConnection.setRequestProperty("Proxy-Authorization", "Bearer " + jwtToken1);
			
			// adding username and password Basic authentication to back end (er9)
//			String authtoken = request.getParameter("basic");
			urlConnection.setRequestProperty("Authorization", "Basic a2FrYW5pOkNoYW5kYW5AMjM=");
//			urlConnection.setRequestProperty("Authorization", authtoken);
	
			urlConnection.setRequestProperty("content-type", "application/json");
			urlConnection.setRequestMethod("GET");
		
			try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			response.getWriter().println("Authentication context received");
			response.getWriter().println(auth.getDetails().toString());
			OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
			response.getWriter().println("details fetched" + details.toString());
			String jwtToken2 = details.getTokenValue();
			response.getWriter().println("details gave token value");
			urlConnection.setRequestProperty("SAP-Connectivity-Authentication", "Bearer " + jwtToken2);
			response.getWriter().println(jwtToken2 + "\t jwt token2 received");
			}
			catch(Exception e)
			{response.getWriter().println(e.getMessage() + "\t jwttoken2 fetch failed");}
//			
//			
			
			
			
			// getting the output from er9 into input stream object and converting into string and returning it
//			urlConnection.setRequestProperty("SAP-Connectivity-Authentication", "Bearer " + "");
			InputStream instream = urlConnection.getInputStream();
			response.getWriter().println(instream.toString());
			String outString = "";
			StringWriter writer = new StringWriter();
			IOUtils.copy(instream, writer);
			outString = writer.toString();
			response.getWriter().println("reached till the last outstring" + outString);
		} 
		catch (Exception e) {
		//	e.printStackTrace();
		 response.getWriter().println(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
