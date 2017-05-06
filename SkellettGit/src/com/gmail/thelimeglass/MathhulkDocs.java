package com.gmail.thelimeglass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringJoiner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MathhulkDocs {
	
	//syntaxType has to be condition, effect, expression, function, type or event
	private static String key = "XrbZAzBCA65sCP4xJsu0";
	
	public static JsonObject sendDoc(String name, String syntax, String description, String syntaxType, String plugins, String example) {
		HashMap<String, String> args = new HashMap<String, String>();
		args.put("sec ", "new");
		args.put("name", name);
		args.put("description", description);
		args.put("addon", "Skellett");
		if (plugins != null) {
			args.put("plugins", plugins);
		} else {
			args.put("plugins", "None");
		}
		args.put("type", syntaxType);
		args.put("pattern", syntax);
		args.put("example", example);
		args.put("key", key);
		JsonElement jelement = new JsonParser().parse(post("new", args));
	    JsonObject jobject = jelement.getAsJsonObject();
	    return jobject;
	}
	
	private static String post(String section, HashMap<String, String> arguments) {
		System.out.println("Posting to: " + section + "...");
		try {
			arguments.put("sec", section);
			arguments.put("APIkey", key);
			URL url = new URL("https://www.theartex.net/cloud/api/skript");
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection) con;
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			http.setRequestProperty("User-Agent", "Java Mathhulk API by AL_1 / 0.0.1");
			http.setRequestProperty("sec", section);
			StringJoiner sj = new StringJoiner("&");
			for (Entry<String, String> entry : arguments.entrySet())
				sj.add(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8"));
			byte[] out = sj.toString().getBytes(StandardCharsets.UTF_8);
			int length = out.length;
			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			http.connect();
			try (OutputStream os = http.getOutputStream()) {
				os.write(out);
			}
			InputStream in = http.getInputStream();
			StringBuilder textBuilder = new StringBuilder();
			try (Reader reader = new BufferedReader(
					new InputStreamReader(in, Charset.forName(StandardCharsets.UTF_8.name())))) {
				int c = 0;
				while ((c = reader.read()) != -1) {
					textBuilder.append((char) c);
				}
			}
			String rtn = textBuilder.toString();
			System.out.println("Returned: " + rtn);
			return rtn;
		} catch (IOException e) {
			e.printStackTrace();
			return "{\"status\":\"error\",\"code\":\"404\",\"message\":\"Post failed (" + e.getMessage() + ")\"}";
		}
	}
}
