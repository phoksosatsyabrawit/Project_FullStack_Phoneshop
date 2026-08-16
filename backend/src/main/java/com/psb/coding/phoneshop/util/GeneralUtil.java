package com.psb.coding.phoneshop.util;

import java.util.List;

public class GeneralUtil {

	// return list of String to list of Integer
	public static List<Integer> toInteger(List<String> list){
		return list.stream()
				.map(s -> s.length())
				.toList();
	}
	
	// return any number % 2 == 0
	public static List<Integer> listOfEvenNumber(List<Integer> list){
		return list.stream()
				.filter(x -> x%2 == 0)
				.toList();
	}
}
