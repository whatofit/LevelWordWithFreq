/**
 * 
 */
package com.myblog.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * @author FanMingyou
 *
 */
//PropertiesUtil
public class CfgUtil {

	/**
	 * 
	 */
	public CfgUtil() {
	}

	// 获取配置文件选项
	public static String getPropCfg(String cfgFile, String key) {
		Properties prop = new OrderedProperties();
		try {
			// 读取属性文件config.properties
			InputStreamReader in = new InputStreamReader(new FileInputStream(cfgFile), "UTF-8");
			prop.load(in); /// 加载属性列表
			return prop.getProperty(key);
		} catch (Exception e) {
			System.out.println(e);
		}
		return "";
	}

	  private static HashMap<String, String> simpleProperty2HashMap(InputStream in) throws IOException {
	        HashMap<String, String> hashMap = new HashMap<String, String>();
	        Properties properties = new Properties();
	        properties.load(in);
	        in.close();
	        Set keyValue = properties.keySet();
	        for (Iterator it = keyValue.iterator(); it.hasNext(); ) {
	            String key = (String) it.next();
	            hashMap.put(key, (String) properties.get(key));
	        }

	        return hashMap;
	    }
	  
	public static void getSysProp() {
		Properties props = System.getProperties(); // 系统属性
		System.out.println("Java的运行环境版本：" + props.getProperty("java.version"));
		System.out.println("Java的运行环境供应商：" + props.getProperty("java.vendor"));
		System.out.println("Java供应商的URL：" + props.getProperty("java.vendor.url"));
		System.out.println("Java的安装路径：" + props.getProperty("java.home"));
		System.out.println("Java的虚拟机规范版本：" + props.getProperty("java.vm.specification.version"));
		System.out.println("Java的虚拟机规范供应商：" + props.getProperty("java.vm.specification.vendor"));
		System.out.println("Java的虚拟机规范名称：" + props.getProperty("java.vm.specification.name"));
		System.out.println("Java的虚拟机实现版本：" + props.getProperty("java.vm.version"));
		System.out.println("Java的虚拟机实现供应商：" + props.getProperty("java.vm.vendor"));
		System.out.println("Java的虚拟机实现名称：" + props.getProperty("java.vm.name"));
		System.out.println("Java运行时环境规范版本：" + props.getProperty("java.specification.version"));
		System.out.println("Java运行时环境规范供应商：" + props.getProperty("java.specification.vender"));
		System.out.println("Java运行时环境规范名称：" + props.getProperty("java.specification.name"));
		System.out.println("Java的类格式版本号：" + props.getProperty("java.class.version"));
		System.out.println("Java的类路径：" + props.getProperty("java.class.path"));
		System.out.println("加载库时搜索的路径列表：" + props.getProperty("java.library.path"));
		System.out.println("默认的临时文件路径：" + props.getProperty("java.io.tmpdir"));
		System.out.println("一个或多个扩展目录的路径：" + props.getProperty("java.ext.dirs"));
		System.out.println("操作系统的名称：" + props.getProperty("os.name"));
		System.out.println("操作系统的构架：" + props.getProperty("os.arch"));
		System.out.println("操作系统的版本：" + props.getProperty("os.version"));
		System.out.println("文件分隔符：" + props.getProperty("file.separator")); // 在 unix 系统中是＂／＂
		System.out.println("路径分隔符：" + props.getProperty("path.separator")); // 在 unix 系统中是＂:＂
		System.out.println("行分隔符：" + props.getProperty("line.separator")); // 在 unix 系统中是＂/n＂
		System.out.println("用户的账户名称：" + props.getProperty("user.name"));
		System.out.println("用户的主目录：" + props.getProperty("user.home"));
		System.out.println("用户的当前工作目录：" + props.getProperty("user.dir"));
		return;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// getSysProp();
		// Path path = FileSystems.getDefault().getPath("/Home/projects/node.txt");
		// System.out.println();
		// System.out.println("toString: " + path.toString());
		// System.out.printf("getFileName: %s\n", path.getFileName());
		// System.out.printf("getRoot: %s\n", path.getRoot());
		// System.out.printf("getNameCount: %d\n", path.getNameCount());
		//
		// for (int index = 0; index < path.getNameCount(); index++) {
		// System.out.printf("getName(%d): %s\n", index, path.getName(index));
		// }
		//
		// System.out.printf("subpath(0,2): %s\n", path.subpath(0, 2));
		// System.out.printf("getParent: %s\n", path.getParent());
		// System.out.println(path.isAbsolute());
		//
		// try {
		// path = Paths.get("Home", "projects", "users.txt");
		// System.out.printf("Absolute path: %s", path.toAbsolutePath());
		// } catch (InvalidPathException ex) {
		// System.out.printf("Bad path: [%s] at position %s", ex.getInput(),
		// ex.getIndex());
		// }

		try {
			Path path2 = Paths.get(new URI("file:///C:/home/docs/users.txt"));
			System.out.println(path2.toAbsolutePath());
			File file = new File("C:\\home\\docs\\users.txt");
			Path toPath = file.toPath();
			System.out.println(toPath.equals(path2));
		} catch (URISyntaxException e) {
			System.out.println("Bad URI");
		}

		// String separator = FileSystems.getDefault().getSeparator();
		// System.out.println("The separator is " + separator);
		//
		// try {
		// Path path = Paths.get(new URI("file:///D:/Home/projects/node.txt"));
		// System.out.println("subpath: " + path.subpath(0, 3));
		// path = Paths.get("/home", "docs", "users.txt");
		// System.out.println("Absolute path: " + path.toAbsolutePath());
		// System.out.println("URI: " + path.toUri());
		//
		// Path path3 = Paths.get(new URI("file:///C:/home/docs/bogusfile.txt"));
		// System.out.println("File exists: " + Files.exists(path3));
		// } catch (URISyntaxException ex) {
		// System.out.println("Bad URI");
		// } catch (InvalidPathException ex) {
		// System.out.println("Bad path: [" + ex.getInput() + "] at position " +
		// ex.getIndex());
		// }

		// Path path = Paths.get("D:/home/docs/../music/Space Machine A.mp3");
		//
		// System.out.println("Absolute path: " + path.toAbsolutePath());
		// System.out.println("URI: " + path.toUri());
		// System.out.println("Normalized Path: " + path.normalize());
		// System.out.println("Normalized URI: " + path.normalize().toUri());
		// System.out.println();
		//
		// path = Paths.get("D:/home/./music/ Robot Brain A.mp3");
		//
		// System.out.println("Absolute path: " + path.toAbsolutePath());
		// System.out.println("URI: " + path.toUri());
		// System.out.println("Normalized Path: " + path.normalize());
		// System.out.println("Normalized URI: " + path.normalize().toUri());

		// Path path1 = null;
		// Path path2 = null;
		//
		// path1 = Paths.get("/home/docs/users.txt");
		// path2 = Paths.get("/home/music/users.txt");
		//
		// System.out.println(Files.isSymbolicLink(path1));
		// System.out.println(Files.isSymbolicLink(path2));
		//
		// try {
		//
		// Path path = Paths.get("C:/home/./music/users.txt");
		//
		// System.out.println("Normalized: " + path.normalize());
		// System.out.println("Absolute path: " + path.toAbsolutePath());
		// System.out.println("URI: " + path.toUri());
		// System.out.println("toRealPath (Do not follow links): " +
		// path.toRealPath(LinkOption.NOFOLLOW_LINKS));
		// System.out.println("toRealPath: " + path.toRealPath());
		//
		// Path firstPath = Paths.get("/home/music/users.txt");
		// Path secondPath = Paths.get("/docs/status.txt");
		//
		// System.out.println("From firstPath to secondPath: " +
		// firstPath.relativize(secondPath));
		// System.out.println("From secondPath to firstPath: " +
		// secondPath.relativize(firstPath));
		// System.out.println("exists (Do not follow links): " + Files.exists(firstPath,
		// LinkOption.NOFOLLOW_LINKS));
		// System.out.println("exists: " + Files.exists(firstPath));
		// System.out.println("notExists (Do not follow links): " +
		// Files.notExists(firstPath, LinkOption.NOFOLLOW_LINKS));
		// System.out.println("notExists: " + Files.notExists(firstPath));
		// } catch (Exception ex) {
		// Logger.getLogger(CfgUtil.class.getName()).log(Level.SEVERE, null, ex);
		// }

		// Path pathRoot = Paths.get("/").toAbsolutePath();
		// System.out.println("path Root: " + pathRoot);
		//
		// Path path0 = Paths.get("").toAbsolutePath();
		// System.out.println("path0: " + path0);
		//
		// Path path1 = Paths.get("bin").toAbsolutePath();
		// System.out.println("path1: " + path1);
		//
		// Path path2 = Paths.get("..").toAbsolutePath();
		// System.out.println("path2: " + path2);
		//
		// Path path3 = Paths.get("../../product").toAbsolutePath();
		// System.out.println("path3: " + path3);

		// String path4 = Class.class.getClass().getResource("/").getPath();
		// //String path4 = this.getClass().getResource("/").getPath();
		// //CfgUtil
		// System.out.println("path4: " + path4);

		// getResourceAsStream ()返回的是inputstream
		// getResource()返回:URL
		// Class.getResource("") 返回的是当前Class这个类所在包开始的为置
		// Class.getResource("/") 返回的是classpath的位置
		// getClassLoader().getResource("") 返回的是classpath的位置
		// getClassLoader().getResource("/") 错误的!!
	}

}
