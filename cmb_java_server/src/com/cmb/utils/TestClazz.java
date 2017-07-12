package com.cmb.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Test;

public class TestClazz {

	@Test
	public void TestString() {
		String path = "/testlock/lockName0000000014";
		String basePath = "/testlock/";
		String tmp = path.substring(basePath.length(), path.length());
		CBLogUtils.Log(tmp);
		
		List<String> listStrings = new ArrayList<String>();
		Set<String> setStrings = new HashSet<>();
		Queue<String> queueString ;
		listStrings.add(null);
		Map<String, Object> hashmap = new HashMap<>();
		hashmap.put(null, null);
		Map<String, String> treemap = new TreeMap<>();
		treemap.put("44", null);
		treemap.put("44", null);
		
		
		

	}
	
}
