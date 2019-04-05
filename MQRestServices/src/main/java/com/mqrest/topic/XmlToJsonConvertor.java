package com.mqrest.topic;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class XmlToJsonConvertor {
public int PRETTY_PRINT_INDENT_FACTOR = 4;
private List<String> convetedJsonObj=new ArrayList<String>();


public List<String> xmlToJson(List<String> messageBody) 
{
for(String stringBody:messageBody) {

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
