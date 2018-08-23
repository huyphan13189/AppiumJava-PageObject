package utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArrayListHashMap {
	
	public static boolean textContainValueHsmStSt(HashMap<String, String> hsm, String text)  {
		for(String value : hsm.values()) {
			if(text.toUpperCase().contains(value.toUpperCase())) {
				return true;
			}
		}
		return false;
	}
	
	public static List<HashMap<String, String>> getListOfHasMapKeyAndValue(List<HashMap<String, String>> lhm, String keyFilter,String value){
		List<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
		for(HashMap<String, String> item:lhm) {
			if(item.get(keyFilter).equals(value))
				result.add(item);
		}
		return result;
	}
}
