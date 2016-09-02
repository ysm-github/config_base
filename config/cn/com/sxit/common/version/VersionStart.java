package cn.com.sxit.common.version;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.com.sxit.common.config.ConfigException;
import cn.com.sxit.common.system.ServerInfo;

/**
 * the servlet to show version & patch info at startup
 * 
 * @author yushiming
 * @company sxit
 * @version 1.0.0
 * @since 1.0.0
 * @date 2013-12-6
 * @CheckItem
 */
@SuppressWarnings("serial")
public class VersionStart {
	private final int charLength = 37;

	public VersionStart() {
	}

	public void init() throws ConfigException {
		ProductInfo.load();
		// 展示版本信息
		String info = getVersionString();
		try {
			System.out.write(info.getBytes("gb2312"));
		} catch (Exception e) {
			throw new ConfigException(e);
		}
	}

	private String getVersionString() {
		StringBuffer str = new StringBuffer();
		// print logo
		str.append(makeLogo());
		str.append(ServerInfo.LS);
		// print owner info
		str.append(repeatLetter('#', 2));
		str.append(repeatLetter(' ', 3));
		str.append(ProductInfo.getOwnerInfo());
		str.append(ServerInfo.LS);
		// print warn info
		String warnInfo = ProductInfo.getWarnInfo();
		String[] warns = parseLine(warnInfo);
		for (int i = 0; i < warns.length; i++) {
			str.append(repeatLetter('#', 2));
			str.append(repeatLetter(' ', 3));
			str.append(warns[i]);
			str.append(ServerInfo.LS);
		}
		// print seperator
		str.append(repeatLetter('#', 2));
		str.append(repeatLetter(' ', 3));
		str.append(repeatLetter('=', 72));
		str.append(repeatLetter('#', 2));
		str.append(ServerInfo.LS);
		// print version ID
		str.append(repeatLetter('#', 2));
		str.append(repeatLetter(' ', 3));
		str.append("程序名称: ");
		str.append(ServerInfo.getAppName());
		str.append(ServerInfo.LS);
		str.append(repeatLetter('#', 2));
		str.append(repeatLetter(' ', 3));
		str.append("发布时间: ");
		str
				.append(ProductInfo.getUpdateTime().equals("@UpdateTime@") ? new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(new Date())
						: ProductInfo.getUpdateTime());
		str.append(ServerInfo.LS);
		return str.toString();
	}

	private String makeLogo() {
		StringBuffer str = new StringBuffer();
		// line 1
		str.append(repeatLetter('#', 2));
		str.append(repeatLetter(' ', 3));
		str.append(repeatLetter('=', 72));
		str.append(repeatLetter('#', 2));
		str.append(ServerInfo.LS);
		// line 2
		str.append(repeatLetter('#', 2));
		str.append(repeatLetter(' ', 5));
		str.append(repeatLetter('S', 12));
		str.append(repeatLetter(' ', 5));
		str.append(repeatLetter('X', 4));
		str.append(repeatLetter(' ', 8));
		str.append(repeatLetter('X', 4));
		str.append(repeatLetter(' ', 4));

		str.append(repeatLetter('I', 12));
		str.append(repeatLetter(' ', 3));
		str.append(repeatLetter('T', 12));
		str.append(ServerInfo.LS);
		// line 3
		str.append(repeatLetter('#', 2));
		str.append(repeatLetter(' ', 3));
		str.append(repeatLetter('S', 7));
		str.append(repeatLetter(' ', 15));
		str.append(repeatLetter('X', 4));
		str.append(repeatLetter(' ', 2));
		str.append(repeatLetter('X', 4));
		str.append(repeatLetter(' ', 11));
		str.append(repeatLetter('I', 4));
		str.append(repeatLetter(' ', 11));
		str.append(repeatLetter('T', 4));
		str.append(ServerInfo.LS);
		// line 4
		str.append(repeatLetter('#', 2));
		str.append(repeatLetter(' ', 5));
		str.append(repeatLetter('S', 12));
		str.append(repeatLetter(' ', 11));
		str.append(repeatLetter('X', 4));
		str.append(repeatLetter(' ', 14));
		str.append(repeatLetter('I', 4));
		str.append(repeatLetter(' ', 11));
		str.append(repeatLetter('T', 4));
		str.append(ServerInfo.LS);
		// line 5
		str.append(repeatLetter('#', 2));
		str.append(repeatLetter(' ', 12));
		str.append(repeatLetter('S', 7));
		str.append(repeatLetter(' ', 6));
		str.append(repeatLetter('X', 4));
		str.append(repeatLetter(' ', 2));
		str.append(repeatLetter('X', 4));
		str.append(repeatLetter(' ', 11));
		str.append(repeatLetter('I', 4));
		str.append(repeatLetter(' ', 11));
		str.append(repeatLetter('T', 4));
		str.append(ServerInfo.LS);
		// line 6
		str.append(repeatLetter('#', 2));
		str.append(repeatLetter(' ', 5));
		str.append(repeatLetter('S', 12));
		str.append(repeatLetter(' ', 5));
		str.append(repeatLetter('X', 4));
		str.append(repeatLetter(' ', 8));
		str.append(repeatLetter('X', 4));
		str.append(repeatLetter(' ', 4));
		str.append(repeatLetter('I', 12));
		str.append(repeatLetter(' ', 7));
		str.append(repeatLetter('T', 4));
		str.append(ServerInfo.LS);
		// line 7
		str.append(repeatLetter('#', 2));
		return str.toString();

	}

	private String repeatLetter(char letter, int len) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < len; i++) {
			str.append(letter);
		}
		return str.toString();
	}

	private String[] parseLine(String info) {
		ArrayList<String> arr = new ArrayList<String>();
		String tmp = info;
		while (tmp.length() > charLength) {
			String line = tmp.substring(0, charLength);
			arr.add(line);
			tmp = tmp.substring(charLength);
		}
		arr.add(tmp);
		String[] lines = new String[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			lines[i] = (String) arr.get(i);
		}
		return lines;

	}

	public static void show() throws ConfigException {
		new VersionStart().init();
	}

	public static void main(String[] args) {
		try {
			new VersionStart().init();
		} catch (ConfigException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
