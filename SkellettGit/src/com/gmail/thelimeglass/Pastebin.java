package com.gmail.thelimeglass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class Pastebin {
	
	private final String POST_URL = "http://pastebin.com/api/api_post.php";
	private String API_KEY;
	
	public static class Paste {
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private List<String> TEXT = new ArrayList();
		
		public Paste() {}
		
		public void addLine(String text) {
			TEXT.add(text);
		}
		
		public void addFile(File file) {
			if (file == null) return;
			try {
				@SuppressWarnings("resource")
				BufferedReader localBufferedReader = new BufferedReader(new FileReader(file));
				Object localObject;
				while ((localObject = localBufferedReader.readLine()) != null) {
					TEXT.add((String) localObject);
				}
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
	
		public String toString() {
			StringBuilder localStringBuilder = new StringBuilder();
			for (String str : TEXT) {
				localStringBuilder.append(str + '\n');
			}
			return localStringBuilder.toString();
		}
	}
	
	public Pastebin(String key) {
		API_KEY = key;
	}
	
	public String post(String paramString, Paste paramPaste) {
		if (paramString == null) {
			paramString = "";
		}
		String str1 = "";
		try {
			URL localURL = new URL(POST_URL);
			HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
			localHttpURLConnection.setConnectTimeout(20000);
			localHttpURLConnection.setReadTimeout(20000);
			localHttpURLConnection.setRequestMethod("POST");
			localHttpURLConnection.addRequestProperty("Content-type", "application/x-www-form-urlencoded");
			localHttpURLConnection.setInstanceFollowRedirects(false);
			localHttpURLConnection.setDoOutput(true);
			OutputStream localOutputStream = localHttpURLConnection.getOutputStream();
			byte[] arrayOfByte = ("api_option=paste&api_dev_key=" + URLEncoder.encode(API_KEY, "utf-8") + "&api_paste_code=" + URLEncoder.encode(paramPaste.toString(), "utf-8") + "&api_paste_private=" + URLEncoder.encode("1", "utf-8") + "&api_paste_name=" + URLEncoder.encode(paramString, "utf-8") + "&api_paste_expire_date=" + URLEncoder.encode("N", "utf-8") + "&api_paste_format=" + URLEncoder.encode("yaml", "utf-8") + "&api_user_key=" + URLEncoder.encode("", "utf-8")).getBytes();
			localOutputStream.write(arrayOfByte);
			localOutputStream.flush();
			localOutputStream.close();
			if (localHttpURLConnection.getResponseCode() == 200) {
				InputStream localInputStream = localHttpURLConnection.getInputStream();
				BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream));
				StringBuffer localStringBuffer = new StringBuffer();
				String str2; while ((str2 = localBufferedReader.readLine()) != null) {
					localStringBuffer.append(str2);
					localStringBuffer.append("\r\n");
				}
				localBufferedReader.close();
				String str3 = localStringBuffer.toString().trim();
				str1 = str3.trim();
			} else {
				str1 = "Failed to post!";
			}
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return str1;
	}
}
