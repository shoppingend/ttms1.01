package cn.tedu.ttms.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	/**
	 *  线程安全?
	 * 1)假如SimpleDateFormat是多线程共享,可考虑加锁.
	 * 2)或借助ThreadLocal实现每个线程一个SimpleDateFormat对象
	 */
	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
	
	public static synchronized String format(Date date) {
		return sdf.format(date);
	}
	
	public static synchronized Date parse(String str) throws ParseException {
		return sdf.parse(str);
	}
}
