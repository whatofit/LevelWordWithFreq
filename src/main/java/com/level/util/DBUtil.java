package com.level.util;
//package db;
//
////sqlite判断数据表存在用到的Sql语句
////SELECT COUNT(*)  as CNT FROM sqlite_master where type='table' and name='DBInfo' //其中DBInfo为需要判断的表名。注意大小写敏感！
//
////sqlite不支持像mysql这样一条语句插入多条记录。
////而sqlite是以文件的形式存在磁盘中，每次访问时都要打开一次文件，如果对数据进行大量操作时，会很慢~
////解决办法是用事务的形式提交：因为我们开始事务后，进行大量操作的语句都保存在内存中，当提交时才全部写入数据库，此时，数据库文件也就只用打开一次。
////conn.setAutoCommit(false);
////prep.executeBatch();
////conn.setAutoCommit(true);
//
////分页查询显示:SELECT * FROM 表名称 LIMIT M,N
//
////1、批量插入。
////很多人发现使用SQLite插入大量数据的时候非常慢。我有印象刚使用SQLite的时候发现插入2000条数据（10列左右）居然要好几分钟，速度慢得令人发指。但后来发现如果是正确使用SQLite，一般这是在小几百毫秒就能完成的工作。要点：
////A）务必要使用事务。这点可以数量级地提高批量插入的速度。
////B）使用Statement并用好，具体地说是： 
////->prepare（此步奏只运行一次） 
////-> (开始处理第一条记录) 
////-> bind（填充第一列）
////-> bind（填充第二列……） 
////-> execute（执行插入） 
////-> clearBind（别忘了清除填充的数据） 
////->  (开始处理第二条记录)  
////-> bind（填充第一列） 
////-> bind （填充第二列……）
////-> execute 
////-> clearBind（清除填充的数据） 
////-> （处理第三条记录……）。
////Statement的prepare也是一个比较耗时的操作，在批量插入的时候没必要每次都去调用。这点对性能的提升没有使用事务来得明显，但一般也能提升几倍的性能。
////2、数据库升级机制。产品周期较长的软件开发过程中，难免要对数据库的表结构进行修改，比如增加表、字段、索引。一开始我是通过表名来进行区分，比如用户表是t_user，如果表结构需要修改就建立一张新表t_user_v2。在数据库打开的时候，检查各表是否存在，如果不存在则创建。后面开发安卓应用，发现Google的解决方法优雅多了：其可以为数据库定义一个版本号（举个栗子——19），之后如果数据库表结构需要修改，则增加这个版本号（举个栗子——增加到20）。然后在打开数据库的时候会自动触发 void onUpgrade(SQLiteDatabase db, int oldVersion[=19], int newVersion[=20] ) 函数，该函数有之前的版本号和目前的版本号，然后就可以执行相应的数据库升级脚本了。
////这套机制即使SDK没有自带，实现起来也很简单：就是在数据库初始化或者升级后找个地方保存版本记录（建议参考安卓用SQLite的user_version设置，详见：http://sqlite.org/pragma.html#pragma_schema_version，或者用SharedPreference、文本文件也是可以的。）。下次打开数据库的时候，对比保存的版本和当前的数据库版本。如果当前的数据库版本更高，则调用数据库升级函数进行升级（记得之后更新版本记录为新的版本号）。我在IOS上就是使用这种方式来进行数据库升级的。
////3、数据库划分。建议：如果没有事务、外键等需要，尽量把不同用途的表划分在多个数据库文件中，而不是使用单一的数据库文件。这么做最主要优点是有利于模块划分，避免其他所有模块都要访问同一个巨大务必的数据库模块。另外性能也会更好，比如SQLite并不支持多线程读写，所以访问同一个数据库的时候是要加同步锁的。使用多个数据库可以降低锁等待的可能。
////4、理解rowid。
//// rowid是SQLite比较有特色的功能。rowid是SQLite为每条记录保存的一个隐藏列（select * from table 查看不了，需要的 select rowid,* from table），rowid从1开始递增。如果拿rowid作为外键来使用的时候，需要注意的一点是：“insert or replace into" 是会改变rowid的值。所以如果想更新记录，应该使用update语句，如果想忽略新值，应该用"insert or ignore into"语句。
////最后但很重要的是：掌握SQL的基本知识。推荐一本书：《SQL反模式》。SQL是很简单，但用久了发现里面还是有不少门道。磨刀不误砍柴工，多看一本书，也许就可以少犯很多错。
//
/////** 通过属性文件获取数据库连接的参数值 **/
////Properties properties = new Properties();
////// FileInputStream fileInputStream = new
////// FileInputStream("src/config/jdbc-mysql.properties");;
////FileInputStream fileInputStream = new FileInputStream("src/config/jdbc-oracle.properties");
////properties.load(fileInputStream);
/////** 获取属性文件中的值 **/
////String driverClassName = properties
////		.getProperty("jdbc.driverClassName");
////String url = properties.getProperty("jdbc.url");
////String username = properties.getProperty("jdbc.username");
////String password = properties.getProperty("jdbc.password");
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.util.Vector;
//
//public class DBUtil {
//	// SqlServer桥连方式
//	// private static final String dbDriver = "sun.jdbc.odbc.JdbcOdbcDriver";
//	// private static final String dbUrl = "jdbc:odbc:restrant";
//	// private static final String dbUser = "sa";
//	// private static final String dbPwd = "";
//
//	// private static final String dbDriver =
//	// "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//	// private static final String dbUrl =
//	// "jdbc:sqlserver://localhost:1433;databaseName=restrant";
//	// private static final String dbUser = "sa";
//	// private static final String dbPwd = "";
//
//	// MYSQL
//	// private static final String dbDriver = "com.mysql.jdbc.Driver";
//	// private static final String dbUrl = "jdbc:mysql://localhost:3306/jxc";
//	// private static final String dbUser = "root";
//	// private static final String dbPwd = "sa";
//	// private static final Connection conn = null;
//
//	// 1.删除旧数据库 File.Delete("test1.db3");
//	// 2.创建数据库 文件 SQLiteConnection.CreateFile("test1.db3");
//	// 2.
//
//	// sqlite
//	private static final String dbDriver = "org.sqlite.JDBC";
//	private static final String dbUrl = "jdbc:sqlite:";
//	private static final String DB_NAME = "./LevelDict.db3"; // "D:/my/testDB.db3";
//															// 数据库名称,可以写绝对路径
//	private static final String dbUser = "";
//	private static final String dbPwd = "";
//
//	private static final String TABLE_NAME = "treeinfo";// 数据表名称
//
//	private Connection mConn = null;
//
//	// //表的字段名
//	// private static String KEY_ID = "id";
//	// private static String KEY_NAME = "name";
//	// private static String KEY_AGE = "age";
//	// private static String KEY_PRICE = "price";
//	//
//	// private Connection mDatabase;
//	/*
//	 * 连接字符串
//	 */
//	public DBUtil() {
//		try {
//			// 1.加载驱动程序
//			Class.forName(dbDriver);
//			// 2.建立数据库连接
//			mConn = DriverManager.getConnection(dbUrl + DBUtil.DB_NAME, dbUser,
//					dbPwd);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * 连接字符串
//	 */
//	public DBUtil(String dbName) {
//		try {
//			// 1.加载驱动程序
//			Class.forName(dbDriver);
//			// 2.建立数据库连接
//			if (dbName == null) {
//				dbName = DBUtil.DB_NAME;
//			}
//			mConn = DriverManager.getConnection(dbUrl + dbName, dbUser, dbPwd);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * 关闭连接
//	 */
//	public void closeRS(ResultSet rs) {
//		try {
//			// 5.关闭连接
//			if (rs != null) {
//				rs.close();
//				rs = null;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * 关闭连接
//	 */
//	public void closeStmt(PreparedStatement pStmt) {
//		try {
//			if (pStmt != null) {
//				pStmt.close();
//				pStmt = null;
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * 关闭连接
//	 */
//	public void closeConn() {
//		try {
//			// 5.关闭连接
//			if (mConn != null && !mConn.isClosed()) {
//				mConn.close();
//				mConn = null;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/*
//	 * 
//	 * 执行带参数的查询的方法
//	 */
//	public ResultSet executeQuery(String sql, Object[] param) {
//		PreparedStatement pStmt = null;
//		ResultSet rs = null;
//		try {
//			// 3.建立Statement语句执行对象
//			pStmt = mConn.prepareStatement(sql);
//			// pStmt = conn.prepareStatement(sql,
//			// ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			if (param != null && param.length > 0) {
//				for (int i = 0; i < param.length; i++) {
//					pStmt.setObject(i + 1, param[i]);
//				}
//			}
//			// 4.建立ResultSet结果集,执行SQL命令
//			rs = pStmt.executeQuery();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			// closeStmt(pStmt);查询后,不能立即关闭pStmt,因为本函数外要使用rs
//			// CloseConn();
//		}
//		return rs;
//	}
//
//	/*
//	 * 
//	 * 执行无参的查询的方法
//	 */
//	public ResultSet executeQuery(String sql) {
//		return executeQuery(sql, null);
//	}
//
//	/** 把结果集转成Object[][] */
//	public static Object[][] resultSetToObjectArray(ResultSet rs) {
//		Object[][] d2Array = null;
//		if (rs == null) {
//			return d2Array;
//		}
//		try {
//			rs.last();
//			int rowCount = rs.getRow();
//			d2Array = new Object[rowCount][];
//			ResultSetMetaData md = rs.getMetaData();// 获取记录集的元数据
//			int columnCount = md.getColumnCount();// 列数
//			rs.first();
//			int k = 0;
//			while (rs.next()) {
//				// System.out.println("i" + k);
//				Object[] row = new Object[columnCount];
//				for (int i = 0; i < columnCount; i++) {
//					row[i] = rs.getObject(i + 1).toString();
//				}
//				d2Array[k] = row;
//				k++;
//			}
//		} catch (Exception e) {
//		}
//		return d2Array;
//	}
//
//	/** 把结果集转成Vector<Object> */
//	public static Vector resultSetToObjectVector(ResultSet result) {
//		Vector retVector = new Vector();
//		Vector<Object> vector = new Vector();
//		try {
//			while (result.next()) {
//				vector.clear();
//				vector.add(result.getObject(1));
//				vector.add(result.getObject(2));
//				vector.add(result.getObject(3));
//				vector.add(result.getObject(4));
//				retVector.add(vector.clone()); // 注意此处不能用 retV.add(v);
//			}
//		} catch (Exception e) {
//		}
//
//		return retVector;
//	}
//
//	/*
//	 * execSQL 执行带参数的增、删、改的方法
//	 */
//	public int executeUpdate(String sql, Object[] param) {
//		int count = 0;
//		PreparedStatement pStmt = null;
//		try {
//			pStmt = mConn.prepareStatement(sql);
//			if (param != null && param.length > 0) {
//				for (int i = 0; i < param.length; i++) {
//					pStmt.setObject(i + 1, param);
//				}
//			}
//			count = pStmt.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			closeStmt(pStmt); // 可以关闭
//			// CloseConn();
//		}
//		return count;
//	}
//
//	/*
//	 * execSQL 执行无参的增、删、改的方法
//	 */
//	public int executeUpdate(String sql) {
//		return executeUpdate(sql, null);
//	}
//
//	/*
//	 * execSQL 执行带参数的批量insert
//	 */
//	public int executeBatchInsert(String sql, Vector paramList) {
//		int affectRowCount = -1;// 影响的行数
//		PreparedStatement pStmt = null;
//		try {
//			/** 执行SQL预编译 **/
//			pStmt = mConn.prepareStatement(sql);
//			/** 设置不自动提交，以便于在出现异常的时候数据库回滚 **/
//			mConn.setAutoCommit(false);
//			// System.out.println(sql);
//			for (int i = 0; i < paramList.size(); i++) {
//				Vector param = (Vector) paramList.get(i);
//				for (int j = 0; j < param.size(); j++) {
//					pStmt.setObject(j + 1, param.get(j));
//				}
//				pStmt.addBatch();
//			}
//			int[] arr = pStmt.executeBatch();
//			mConn.commit();
//			affectRowCount = arr.length;
//			// System.out.println("成功了插入了" + affectRowCount + "行");
//			// System.out.println();
//		} catch (Exception e) {
//			if (mConn != null) {
//				try {
//					mConn.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//			}
//			e.printStackTrace();
//		} finally {
//			closeStmt(pStmt); // 可以关闭
//			// CloseConn();
//		}
//		return affectRowCount;
//	}
//
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//	}
// }
