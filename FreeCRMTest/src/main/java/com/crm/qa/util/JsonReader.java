package com.crm.qa.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

import com.crm.qa.base.TestBase;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonReader extends TestBase{

	public static Object[][] getData(String JSON_path, String typeData, int totalRows, int totalColumn) 
			throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = jsonParser.parse(new FileReader(JSON_path)).getAsJsonObject();
		JsonArray jsonArray = (JsonArray) jsonObject.get(typeData);
		return searchJsonElement(jsonArray, totalRows, totalColumn);
	}
	
	public static Object[][] searchJsonElement(JsonArray jsonArray, int rowsAll, int columnsAll){
		
		Object[][] object = new Object[rowsAll][columnsAll];
		int i = 0;
		int j = 0;
		for (JsonElement jsonElement : jsonArray){
			for(Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()){
				object[i][j] = entry.getValue().toString().replace("\"", "");
				j++;
			}
			i++;
			j=0;
		}
		return object;
	}
}
