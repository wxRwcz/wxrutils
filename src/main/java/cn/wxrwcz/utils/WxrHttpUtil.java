package cn.wxrwcz.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class WxrHttpUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(WxrHttpUtil.class);

	private static final String CHARSET = "UTF-8";

	public static String get(String url) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			return result(response,httpClient);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static String result(CloseableHttpResponse response,CloseableHttpClient httpClient) throws IOException {
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String str = EntityUtils.toString(entity, CHARSET);
				return str;
			}
		} finally {
			response.close();
			httpClient.close();
		}
		return null;
	}
	public static String get(String url, Map<String, Object> params) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			url = url + "?";
			for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				String temp = key + "=" + params.get(key) + "&";
				url = url + temp;
			}
			url = url.substring(0, url.length() - 1);
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			return result(response,httpClient);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String post(String url, Map<String, Object> params) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				parameters.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(parameters, CHARSET);
			httpPost.setEntity(uefEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			return result(response,httpClient);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String post(String url, String params) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			StringEntity sEntity = new StringEntity(params, CHARSET);
			httpPost.setEntity(sEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity, CHARSET);
				}
			} finally {
				response.close();
				httpClient.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
	public static String doHttpsPostJson(String url,String json){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = null;
		CloseableHttpResponse response = null;
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(json,"UTF-8");
		entity.setContentType("application/json;charset=UTF-8");
		httpPost.setEntity(entity);
		try{
			response = httpClient.execute(httpPost);
			if(response != null){
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 200) {
					httpPost.abort();
					return null;
				}
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null) {
					result = EntityUtils.toString(resEntity,CHARSET);
				}
			}
			if (entity != null) {
				EntityUtils.consume(entity);
			}
		}catch(Exception ex){
			httpPost.abort();
			return null;
		}finally {
			try {
				response.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String sendPostL(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			conn.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			conn.setDoOutput(true);
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			LOGGER.info("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public static JSONObject requestServer(String channel,String encryptData,String url){
		Map<String,Object> map = new HashMap<>();
		map.put("channel",channel);
		map.put("data",encryptData);
		return requestServer(url,map);
	}
    public static JSONObject requestServer(String url,Map<String,Object> map){
        String result = post(url,map);
        JSONObject retJson = JSON.parseObject(result);
        if(!retJson.containsKey("code")){
            retJson.put("code",0);
        }
        return retJson;
    }
}
