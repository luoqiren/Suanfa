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
		Date dNow = new Date();// ��ȡ��ǰϵͳʱ��--NOW
		Date dBefore = new Date();// ��ȡ��ǰϵͳʱ��--BEFORE
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.DAY_OF_YEAR, -cDays);
		dBefore = cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String date = sdf.format(dBefore);// ȡ��Ŀ������֮ǰ��ʱ��
		System.out.println("dBefore:"+dBefore);
		File file = new File(tPath);// ��ȡĿ��·��
		List<String> filelist = new ArrayList<String>();
		List<String> tempList = tree(file, filelist);
		// ����ȡ��Ŀ��·���������ļ��ľ���·��
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

	// ��ȡ�ļ��ľ���·��
	static List<String> tree(File f, List<String> filelist) {
		// TODO Auto-generated method stub
		if (!f.isDirectory()) {
			System.out.println("��·����Ч~");
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