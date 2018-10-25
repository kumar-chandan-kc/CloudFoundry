//package com.trail;
//
//public class MyActiveProfilesResolver  implements ActiveProfilesResolver{
//	  @Override
//	  public String[] resolve(Class<?> testClass) {
//	      String os = System.getProperty("os.name");
//	      String profile = (os.toLowerCase().startsWith("windows")) ? "windows" : "other";
//	      return new String[]{profile};
//	  }
//	}