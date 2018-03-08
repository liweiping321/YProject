package com.game.http;

import com.alibaba.fastjson.JSON;
import com.game.http.entity.HttpRequestMsg;
import com.game.http.entity.HttpResponseMsg;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class HttpClient {

	private static final String CHARSET = "UTF-8";

	private static final RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(15000)
			.setConnectTimeout(15000).setSocketTimeout(15000).build();

	public static void requestAsync(String url) throws IOException, InterruptedException, ExecutionException {
		try (CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault()) {
			httpclient.start();
			final CountDownLatch latch = new CountDownLatch(1);
			final HttpGet request = new HttpGet(url);
			HttpAsyncRequestProducer producer = HttpAsyncMethods.create(request);
			AsyncCharConsumer<HttpResponse> consumer = new AsyncCharConsumer<HttpResponse>() {
				HttpResponse response;

				@Override
				protected void onResponseReceived(final HttpResponse response) {
					this.response = response;
					System.out.println("onResponseReceived");
				}

				@Override
				protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException {
					// Do something useful
					System.out.println("onCharReceived" + buf.toString());
				}

				@Override
				protected void releaseResources() {
					System.out.println("releaseResources");
				}

				@Override
				protected HttpResponse buildResult(final HttpContext context) {
					System.out.println("buildResult");
					return this.response;
				}
			};
			httpclient.execute(producer, consumer, new FutureCallback<HttpResponse>() {
				public void completed(final HttpResponse response) {
					latch.countDown();
					System.out.println("completed " + request.getRequestLine() + "->" + response.getStatusLine());
				}

				public void failed(final Exception ex) {
					latch.countDown();
					System.out.println("failed " + request.getRequestLine() + "->" + ex);
				}

				public void cancelled() {
					latch.countDown();
					System.out.println("cancelled " + request.getRequestLine() + " cancelled");
				}
			});
			latch.await();
		}
	}

	/**
	 * 异步GET请求
	 * 
	 * @param callback
	 *            回调对象
	 */
	public static void getAsync(String url, FutureCallback<HttpResponse> callback) {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			httpclient.start();
			final CountDownLatch latch = new CountDownLatch(1);
			final HttpGet request = new HttpGet(url);
			request.setConfig(requestConfig);
			httpclient.execute(request, callback);
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 同步GET请求
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String get(String url) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpGet httpget = new HttpGet(url);
			httpget.setConfig(requestConfig);
			try (CloseableHttpResponse response = httpclient.execute(httpget)) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity);
				}
				return null;
			}
		}
	}

	/**
	 * 同步POST请求
	 *
	 * @param url
	 */
	@SuppressWarnings("unchecked")
	public static <V> HttpResponseMsg<V> post(String url, HttpRequestMsg<V> requestMsg) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("param", JSON.toJSONString(requestMsg)));

			httpPost.setEntity(new UrlEncodedFormEntity(params, CHARSET));
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity);
					return JSON.parseObject(result, HttpResponseMsg.class);
				}
				return null;
			} finally {
				response.close();
			}
		} finally {
			httpclient.close();
		}
	}

	/**
	 * 同步POST请求
	 * 
	 * @param url
	 */
	public static String post(String url, List<NameValuePair> params) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			httpPost.setEntity(new UrlEncodedFormEntity(params, CHARSET));
			try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity);
				}
				return null;
			}
		}
	}

	/**
	 * 异步POST请求
	 * 
	 * @param callback
	 *            回调对象
	 * @throws Exception
	 */
	public static void postAsync(String url, List<NameValuePair> params, FutureCallback<HttpResponse> callback)
			throws Exception {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			httpclient.start();
			final CountDownLatch latch = new CountDownLatch(1);
			final HttpPost request = new HttpPost(url);
			request.setConfig(requestConfig);
			request.setEntity(new UrlEncodedFormEntity(params, CHARSET));
			httpclient.execute(request, callback);
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
