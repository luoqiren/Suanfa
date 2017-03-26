package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FileDeleteService {

	public static void main(String[] args) {

		String path = "H:\\zipfile\\WOw-txt";
		int days = 2;
		fileDelete(path, days);

	}

	public static int fileDelete(String tPath, int cDays) {

		int number = 0;
		String s1 = null;
		Date dNow = new Date();// 获取当前系统时间--NOW
		Date dBefore = new Date();// 获取当前系统时间--BEFORE
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.DAY_OF_YEAR, -cDays);
		dBefore = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String date = sdf.format(dBefore);// 取得目标天数之前的时间
		System.out.println("dBefore:"+dBefore);
		File file = new File(tPath);// 读取目标路径
		List<String> filelist = new ArrayList<String>();
		List<String> tempList = tree(file, filelist);
		// 遍历取得目标路径下所有文件的绝对路径
		for (Iterator<String> result = tempList.iterator(); result.hasNext();) {
			s1 = result.next();
			System.out.println("filepath:"+s1);
			String strTime = null;
			file = new File(s1);
			if(file.exists()){
				Date fileDate = new Date(file.lastModified());
				if(dBefore.compareTo(fileDate)>0){
					System.out.println("Delete file:"+file.getAbsolutePath());
					file.delete();
				}
			} 

		}
		return number;

	}

	// 获取文件的绝对路径
	static List<String> tree(File f, List<String> filelist) {
		// TODO Auto-generated method stub
		if (!f.isDirectory()) {
			System.out.println("此路径无效~");
		} else {
			File[] t = f.listFiles();
			for (int i = 0; i < t.length; i++) {
				//
				if (t[i].isDirectory()) {
					tree(t[i], filelist);
				} else {
					filelist.add(t[i].getAbsolutePath());
				}
			}
		}
		return filelist;

	}

}