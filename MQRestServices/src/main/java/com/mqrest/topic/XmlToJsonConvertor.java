package com.mqrest.topic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XmlToJsonConvertor {
	public static int PRETTY_PRINT_INDENT_FACTOR = 4;

	public static List<String> xmlToJson(List<String> messageBody){
		List<String> convetedJsonObj = new ArrayList<String>();
		for (String stringBody : messageBody) {

			try {
				JSONObject xmlJSONObj = XML.toJSONObject(stringBody);

				convetedJsonObj.add(xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR));
			} catch (JSONException je) {
				System.out.println(je.toString());
			}
		}
		return convetedJsonObj;

	}
}
