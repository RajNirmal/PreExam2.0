package com.spintum.preexam;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class Global {
	public static boolean[] answeredCorrect=new boolean[20];
	public static boolean[] attended=new boolean[20];
	public static boolean[] flag;
	public static String score;
	public static String maxScore;
	public static String date;
	public static String path = "file:///android_asset/";
	public static String js = "<html><head>" + "<link rel='stylesheet' href='"
			+ path + "jqmath-0.4.0.css'>" + "<script src='" + path
			+ "jquery-1.4.0.min.js'></script>" + "<script src='" + path
			+ "jqmath-etc-0.4.0.min.js'></script>" + "<script src='" + path
			+ "jquery-1.4.3.js'></script>" + "<script src='" + path
			+ "jqmath-0.4.3.js'></script>" + "</head><body>";
	//public static String[] qns = new String[40];
	public static int noOfQuestions=20;
	public static long totalTime;
	public static long answerTime;
	public static String exam = "Tamil Nadu State Board Grade 12";
	public static String subject = "";
	public static String email = "";
	public static String password = "";
	public static String userName = "";
	public static Drawable getGradient(int[] colors) {
		GradientDrawable gd = new GradientDrawable();
		gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
		gd.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
		gd.setColors(colors);
		return gd;
	}
	public static Drawable getSmoothGradient(int[] colors) {
		GradientDrawable gd = new GradientDrawable();
		gd.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
		gd.setColors(colors);
		return gd;
	}
	public static Drawable getSmoothButtonGradient(int[] colors,int radius) {
		GradientDrawable gd = new GradientDrawable();
		gd.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
		gd.setColors(colors);
		gd.setGradientRadius(radius);
		return gd;
	}

}
