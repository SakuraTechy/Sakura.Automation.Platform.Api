package com.sakura.common.utils.db;

import com.alibaba.fastjson.JSONArray;
import com.google.gson.Gson;
import com.jcraft.jsch.UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.*;

/**
 * 数据库操作类
 */
@Slf4j
public class DataBaseUtil {


    private static ResultSet rs;
    public static Statement sm;
    private static Connection con;
    private static CallableStatement cs;

    /**
     * 数据库插入
     *
     */
    public static int insert(String Driver, String Url, String User, String PassWord, String Sql) {
        return executeUpdate(Driver, Url, User, PassWord, Sql, OpType.INSERT);
    }

    /**
     * 数据库删除
     *
     */
    public static int delete(String Driver, String Url, String User, String PassWord, String Sql) {
        return executeUpdate(Driver, Url, User, PassWord, Sql, OpType.DELETE);
    }

    /**
     * 数据库修改
     *
     */
    public static int update(String Driver, String Url, String User, String PassWord, String Sql) {
        return executeUpdate(Driver, Url, User, PassWord, Sql, OpType.UPDATE);
    }

    /**
     * 执行数据库操作
     *
     */
    private static int executeUpdate(String Driver, String Url, String User, String PassWord, String Sql, OpType Type) {
        checkConnection(Driver, Url, User, PassWord);
        PreparedStatement ps = null;
        int rs;
        log.info("Sql: " + Sql);
        try {
            // ps = con.prepareStatement(Sql);
            // int result = ps.executeUpdate();
            rs = sm.executeUpdate(Sql);
            log.info("Result: " + rs);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Type.desc() + "失败", e.fillInStackTrace());
//            RunUnitService.Step.put("picture", "数据库查询失败");
        } finally {
            close();
        }
        return -1;
    }

    /**
     * 执行数据库操作
     *
     */
    public static Object executeDatabaseOperations(String Driver, String Url, String User, String PassWord, String type, String sql, String params) {
        checkConnection(Driver, Url, User, PassWord);
        Object rs = null;
        try {
            log.info("开始执行: " + sql);
            long startTime = System.currentTimeMillis();
            switch (type) {
                case "executeUpdate":
                    rs = sm.executeUpdate(sql);
                    break;
                case "executeQuery":
                    rs = query(Driver, Url, User, PassWord, sql);
                    break;
                case "prepareCall":
                    log.info("执行参数: " + params);
                    rs = prepareCall(Driver, Url, User, PassWord, sql, params);
                    break;
            }
            log.info("影响数据："+ rs);
            long endTime = System.currentTimeMillis();
            log.info("执行耗时：" + (endTime - startTime) + "ms");
        } catch (SQLException e) {
            log.error("执行数据库操作失败", e.fillInStackTrace());
        } finally {
            close();
        }
        return rs;
    }

    /**
     * 数据库查询
     */
    public static List<Map<String, Object>> query1(String Driver, String Url, String User, String PassWord, String Sql) {
        checkConnection(Driver, Url, User, PassWord);
        ResultSet rs;
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        try {
            rs = sm.executeQuery(Sql);
            ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()) {
                int count = metaData.getColumnCount();
                Map<String, Object> rowMap = new HashMap<String, Object>();
                for (int i = 1; i <= count; i++) {
                    rowMap.put(metaData.getColumnLabel(i), rs.getObject(i));
                    // log.info(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        // log.info("\t");
                    }
                }
                // log.info("");
                results.add(rowMap);
            }
            log.info("Count: " + results.size());
        } catch (SQLException e) {
            log.error("查询失败", e.fillInStackTrace());
//            RunUnitService.Step.put("picture", "数据库查询失败");
        }
        return results;
    }
    public static List<LinkedHashMap<String, Object>> query2(String Driver, String Url, String User, String PassWord, String Sql) {
        checkConnection(Driver, Url, User, PassWord);
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<LinkedHashMap<String, Object>> results = new ArrayList<LinkedHashMap<String, Object>>();
        log.info("Sql: " + Sql);
        try {
            // ps = connection.prepareStatement(sql);
            // rs = ps.executeQuery();
            rs = sm.executeQuery(Sql);
            ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()) {
                int count = metaData.getColumnCount();
                LinkedHashMap<String, Object> rowMap = new LinkedHashMap<String, Object>();
                for (int i = 1; i <= count; i++) {
                    rowMap.put(metaData.getColumnLabel(i), rs.getObject(i));
                    // log.info(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        // log.info("\t");
                    }
                }
                // log.info("");
                results.add(rowMap);
            }
            log.info("Count: " + results.size());
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("查询失败", e.fillInStackTrace());
//            RunUnitService.Step.put("picture", "数据库查询失败");
        } finally {
            close(rs);
        }
        return results;
    }
    public static List<String> query(String Driver, String Url, String User, String PassWord, String Sql) {
        checkConnection(Driver, Url, User, PassWord);
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> results = new ArrayList<>();
        List<LinkedHashMap<String, Object>> results1 = new ArrayList<LinkedHashMap<String, Object>>();
        Gson gson = new Gson(); // 使用默认的 Gson 实例，不进行 pretty printing，输出非格式化的json
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();// 输出格式化的json

        log.info("Sql: " + Sql);
        try {
            // ps = connection.prepareStatement(sql);
            // rs = ps.executeQuery();
            rs = sm.executeQuery(Sql);
            ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()) {
                int count = metaData.getColumnCount();
                LinkedHashMap<String, Object> rowMap = new LinkedHashMap<String, Object>();
                for (int i = 1; i <= count; i++) {
                    rowMap.put(metaData.getColumnLabel(i), rs.getObject(i));
                    // log.info(rs.getString(i) + "\t");
//                    if ((i == 2) && (rs.getString(i).length() < 8)) {
//                        // log.info("\t");
//                    }
                }
                // log.info("");
//                results.add(rowMap);
                String json = gson.toJson(rowMap);
                results.add(json);
            }
            log.info("Count: " + results.size());
            log.info("Results: " + results);
//            for (String json : results) {
//                Type type = new TypeToken<LinkedHashMap<String, Object>>(){}.getType();
//                LinkedHashMap<String, Object> map = gson.fromJson(json, type);
//                results1.add(map);
//            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("查询失败", e.fillInStackTrace());
//            RunUnitService.Step.put("picture", "数据库查询失败");
        } finally {
            close(rs);
        }
        return results;
    }

    /**
     * 指定SQL语句,执行查询操作,并打印结果
     *
     */
    public static void Query(String Driver, String Url, String User, String PassWord, String Sql) {
        checkConnection(Driver, Url, User, PassWord);
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // ps = con.prepareStatement(Sql);
            // rs = ps.executeQuery();
            rs = sm.executeQuery(Sql);
            int col = rs.getMetaData().getColumnCount();
            log.info("============================");
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    log.info(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        log.info("\t");
                    }
                }
                log.info("");
            }
            log.info("============================");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("", e);
        } finally {
            close(rs);
        }
    }

    /** 执行一批SQL查询语句 */
    public static void getBatchQuery(Connection con, String[] sqls) throws Exception {
        boolean supportBatch = supportBatch(con); // 判断是否支持批处理
        if (supportBatch && sqls != null) {
            Statement sm = null;
            try {
                sm = con.createStatement();
                for (int a = 0; a < sqls.length; a++) {
                    ResultSet rs = sm.executeQuery(sqls[a]);
                    int col = rs.getMetaData().getColumnCount();
                    log.info("============================");
                    while (rs.next()) {
                        for (int i = 1; i <= col; i++) {
                            log.info(rs.getString(i) + "\t");
                            if ((i == 2) && (rs.getString(i).length() < 8)) {
                                log.info("\t");
                            }
                        }
                        log.info("");
                    }
                    log.info("============================");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                sm.close();
            }
        }
    }

    /** 执行一批SQL更新语句 */
    public static void goBatchUpdate(Connection con, String[] sqls) throws Exception {
        boolean supportBatch = supportBatch(con); // 判断是否支持批处理
        if (supportBatch && sqls != null) {
            Statement sm = null;
            try {
                sm = con.createStatement();
                for (int i = 0; i < sqls.length; i++) {
                    sm.addBatch(sqls[i]);// 将所有的SQL语句添加到Statement中
                }
                // 一次执行多条SQL语句
                int[] results = sm.executeBatch();// 执行一批SQL语句
                // 分析执行的结果
                for (int i = 0; i < sqls.length; i++) {
                    if (results[i] >= 0) {
                        log.info("语句: " + sqls[i] + " 执行成功，影响了" + results[i] + "行数据");
                    } else if (results[i] == Statement.SUCCESS_NO_INFO) {
                        log.info("语句: " + sqls[i] + " 执行成功，影响的行数未知");
                    } else if (results[i] == Statement.EXECUTE_FAILED) {
                        log.info("语句: " + sqls[i] + " 执行失败");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                sm.close();
            }
        }
    }

    /**
     * 执行存储过程,带参数
     *
     * @return
     */
    public static int procedure(String Driver, String Url, String User, String PassWord, String prc_name, Object... params) {
        checkConnection(Driver,Url, User,PassWord);
        CallableStatement cs = null;
        try {
            cs = con.prepareCall(prc_name);
            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    // TODO set类型对应数据库,包含输入和输出
                    cs.setString(i + 1, String.valueOf(params[i]));
                }
            }
            log.info("开始执行存储过程: " + prc_name);
            cs.execute();
            log.info("存储过程执行成功 ");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("存储过程[" + prc_name + "]执行失败", e.fillInStackTrace());
        } finally {
            close(cs);
        }
        return 0;
    }

    public static Object prepareCall(String className, String url, String user, String password, String sql, String params) {
        Object results = null;
        try {
            checkConnection(className, url, user, password);
            CallableStatement cs = con.prepareCall(sql);
            int index = 1;
            // 使用fastjson解析JSON字符串
            JSONArray jsonArray = JSONArray.parseArray(params);
            // 将JSONArray转换为Object数组
            Object[] params1 = jsonArray.toArray(new Object[0]);
            for (Object param : params1) {
                // 根据参数的实际类型设置不同的数据类型
                if (param instanceof String) {
                    cs.setString(index++, (String) param);
                } else if (param instanceof Integer) {
                    cs.setInt(index++, (Integer) param);
                } // 可以继续添加其他类型的参数处理
            }
            boolean hasResultSet = cs.execute();
            if (hasResultSet) {
                // 处理返回的结果集
                while (cs.getMoreResults()) {
                    ResultSet rs = cs.getResultSet();
                    if (rs != null) {
                        // 这里可以根据需要处理结果集，并将结果作为返回值
                        while (rs.next()) {
                            // 处理结果集数据打印第一列
                            log.info(rs.getString(1));
                        }
                        // 例如，你可以将查询结果转换为一个List<Map<String, Object>>或其他数据结构
                        results = convertResultSetToList(rs);
                    }
                }
            } else {
                int updateCount = cs.getUpdateCount();
//                if (updateCount == -1) {
//                    log.info("存储过程执行成功，但没有返回更新计数。");
//                    return "No update count"; // 返回一个状态消息
//                } else {
//                    log.info("存储过程执行成功，更新了 " + updateCount + " 行。");
//                    return updateCount; // 返回更新的行数
//                }
                results = "存储过程执行成功，更新了 " + updateCount + " 行。";
            }
        } catch (SQLException e) {
            log.error("存储过程执行失败", e.fillInStackTrace());
            return e; // 返回异常对象，调用者可以根据需要处理
        }
        return results;
    }

    // 将ResultSet转换为List<Map<String, Object>>
    private static List<Map<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            Map<String, Object> row = new HashMap<>(columnCount);
            for (int i = 1; i <= columnCount; i++) {
                Object value = getColumnValue(rs, i, metaData.getColumnType(i));
                row.put(metaData.getColumnName(i), value);
            }
            resultList.add(row);
        }
        return resultList;
    }

    // 根据列的数据类型获取值
    private static Object getColumnValue(ResultSet rs, int columnIndex, int sqlType) throws SQLException {
        switch (sqlType) {
            case Types.BOOLEAN:
            case Types.BIT:
                return rs.getBoolean(columnIndex);
            case Types.TINYINT:
                return rs.getByte(columnIndex);
            case Types.SMALLINT:
                return rs.getShort(columnIndex);
            case Types.INTEGER:
                return rs.getInt(columnIndex);
            case Types.BIGINT:
                return rs.getLong(columnIndex);
            case Types.FLOAT:
                return rs.getFloat(columnIndex);
            case Types.DOUBLE:
                return rs.getDouble(columnIndex);
            case Types.DECIMAL:
            case Types.NUMERIC:
                return rs.getBigDecimal(columnIndex);
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
                return rs.getString(columnIndex);
            case Types.DATE:
                return rs.getDate(columnIndex).toLocalDate();
            case Types.TIME:
                return rs.getTime(columnIndex).toLocalTime();
            case Types.TIMESTAMP:
                return rs.getTimestamp(columnIndex).toLocalDateTime();
            default:
                return rs.getObject(columnIndex);
        }
    }
    public static void checkConnection(String DataType, String Driver, String URL, String DataBase, String User, String PassWord) {
        try {
            if (con == null || con.isClosed()) {
                switch (DataType) {
                    case "Custom":
                        Connect_Custom(Driver, URL, User, PassWord);
                        break;
                    case "MongoDB":
                        new MongoDBUtil(URL);
                        break;
                }
            }
        } catch (Exception e) {
            log.error("连接异常", e);
        }
    }

    public static Boolean checkConnection(String Driver, String Url, String User, String PassWord) {
        try {
            if (con == null || con.isClosed()) {
                Connect_Custom(Driver, Url, User, PassWord);
            }
            return true;
        } catch (Exception e) {
            log.error("连接异常", e);
            return false;
        }
    }

    /**
     * 连接 Oracle 数据库
     */
    public static void Connect_Oracle(String Driver, String IP,String Port, String DataBase, String User, String PassWord) throws Exception {
        try {
            Class.forName(Driver);
            con = DriverManager.getConnection("jdbc:oracle:thin:@"+IP+":"+Port+":"+DataBase , User, PassWord);
            sm = con.createStatement();
            log.info("数据库连接成功");
        } catch (Exception e) {
            String message = "数据库连接失败";
            if (e instanceof ClassNotFoundException)
                message = "数据库驱动类未找到";
            throw new Exception(message, e.fillInStackTrace());
        }
    }

    /**
     * 连接 MySql 数据库
     *
     */
    public static void Connect_MySql(String Driver, String IP,String Port, String DataBase, String User, String PassWord) throws Exception {
        try {
            con = DriverManager.getConnection("jdbc:mysql://"+ IP + ":"+Port+"/" + DataBase + "?useUnicode=true&characterEncoding=utf-8&useSSL=false", User, PassWord);
            sm = con.createStatement();
            log.info("数据库连接成功");
        } catch (Exception e) {
            String message = "数据库连接失败";
            throw new Exception(message, e.fillInStackTrace());
        }
    }

    /**
     * 连接自定义数据库
     *
     */
    public static void Connect_Custom(String Driver, String Url, String User, String PassWord) throws Exception {
        try {
            Class.forName(Driver);
            con = DriverManager.getConnection(Url, User, PassWord);
            sm = con.createStatement();
            log.info("数据库连接成功");
        } catch (Exception e) {
            String message = "数据库连接失败";
            if (e instanceof ClassNotFoundException)
                message = "数据库驱动类未找到";
            throw new Exception(message, e.fillInStackTrace());
        }
    }

    public static boolean testConnection(String Driver, String Url, String User, String PassWord) {
        try {
            Class.forName(Driver);
            con = DriverManager.getConnection(Url, User, PassWord);
            sm = con.createStatement();
            log.info("数据库连接成功");
            return true;
        } catch (Exception e) {
            log.error("数据库连接失败", e);
            return false;
        }finally {
            close();
        }
    }

    public static boolean testConnection(String URL) {
        MongoDBUtil mongoDBUtil = null;
        try {
            mongoDBUtil = new MongoDBUtil(URL);
            log.info("数据库连接成功");
            return true;
        } catch (Exception e) {
            log.error("数据库连接失败", e);
            return false;
        }finally {
            if (mongoDBUtil != null) {
                mongoDBUtil.close();
            }
        }
    }

    /**
     * 连接 MongoDB 数据库
     *
     * @throws Exception
     */
    public static MongoDBUtil Connect_MongoDB(String IP,String Port, String User, String PassWord, String DataBase) throws Exception {
        MongoDBUtil mongoDBUtil;
        try {
            mongoDBUtil = new MongoDBUtil(IP, Port, User, PassWord, DataBase);
            log.info("数据库连接成功");
        } catch (Exception e) {
            String message = "数据库连接失败";
            throw new Exception(message, e.fillInStackTrace());
        }
        return mongoDBUtil;
    }



    /** 判断数据库是否支持批处理 */
    public static boolean supportBatch(Connection con) {
        try {
            // 得到数据库的元数据
            DatabaseMetaData md = con.getMetaData();
            return md.supportsBatchUpdates();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 释放资源并关闭数据库
     */
    public static void close(ResultSet rs) {
        try {
            if (sm != null) {
                sm.close();
                sm = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            log.info("数据库资源释放成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("数据库资源释放失败！", e);
        }
        close();
    }

    /**
     * 释放资源并关闭数据库
     */
    public static void close(CallableStatement cs) {
        try {
            if (cs != null) {
                cs.close();
                cs = null;
            }
            log.info("数据库资源释放成功");
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("数据库资源释放失败", e);
        }
        close();
    }

    /**
     * 关闭数据库
     */
    public static void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                con = null;
            }
            if (sm != null) {
                sm.close();
                sm = null;
            }
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (cs != null) {
                cs.close();
                cs = null;
            }
            log.info("数据库关闭成功");
        } catch (SQLException e) {
            log.error("数据库关闭失败", e);
        }
    }

    public static class MyUserInfo implements UserInfo {
        private String passphrase = null;

        public MyUserInfo(String passphrase) {
            this.passphrase = passphrase;
        }

        public String getPassphrase() {
            return passphrase;
        }

        public String getPassword() {
            return null;
        }

        public boolean promptPassphrase(String s) {
            return true;
        }

        public boolean promptPassword(String s) {
            return true;
        }

        public boolean promptYesNo(String s) {
            return true;
        }

        public void showMessage(String s) {
            log.info(s);
        }
    }

    /**
     * 数据库操作枚举
     */
    public enum OpType {

        INSERT("插入"), UPDATE("更新"), DELETE("删除"), QUERY("查询"), PROCEDURE("执行存储过程");

        final String desc;

        OpType(String desc) {
            this.desc = desc;
        }

        String desc() {
            return desc;
        }
    }

    public static void main(String[] args) throws Exception {
        String Oracle_sql = "SELECT RAWTOHEX(IP) AS IP, RAWTOHEX(地址) AS 地址 FROM \"ROOT\".\"SENSITIVE_TYPES_ALL\"";
        String MySql_sql = "SELECT * FROM `test`.`jdbc` LIMIT 0,1000";
        String SqlServer_sql = "SELECT * FROM testdb.dbo.sensitive_types_all";
        String PostgreSQL_sql = "SELECT * FROM \"public\".\"users\" LIMIT 1000 OFFSET 0";
        String Greenplum_sql = "SELECT * FROM \"public\".\"users\" LIMIT 1000 OFFSET 0";
        String Sybase_sql = "SELECT id, a_text FROM test.dbo.a_text WHERE id='1'";
        String Hive_sql = "SELECT id, name FROM default.test WHERE id = '1'";
        String TiDB_sql = "SELECT * FROM test.TEST WHERE CVV ='420'";
        String MariaDB_sql = "SELECT * FROM test.student";
        String OceanBase_sql = "SELECT * FROM oceanbase.`__all_user` WHERE tenant_id=0 AND user_id=200001";
        String Teradata_sql = "SELECT a_id, a_timestamp, a_date, a_time FROM test.all_date WHERE a_id ='3'";
        String KingBase_sql ="SELECT dummy FROM sys.dual";
        String IRIS_sql ="SELECT * FROM SYS.Process";
        String Informix_sql ="SELECT * FROM sysadmin:informix.ph_version";
        String DB2_sql ="SELECT * FROM TEST.\"zmy_A_DATE_TYPE\"";
        String Cache_sql ="SELECT * FROM \"%SYS\".Task WHERE ID=1";
        String GaussDB_sql ="SELECT * FROM public.\"name\"";
        String Gbase8s_sql ="SELECT * FROM test:gbasedbt.test1 WHERE id=1";
        String Gbase8a_sql ="SELECT * FROM test.student";
        String TDengine_sql ="SELECT * FROM information_schema.ins_dnodes WHERE id='1'";
        String Hbase_sql ="SELECT NAME, AGE FROM T_USER WHERE AGE=1";

        String DM7_sql = "SELECT \"numid\", \"name\", \"school\", \"addrid\" FROM SYSDBA.\"test3\" WHERE \"numid\"=1;";
        String DM8_sql = "SELECT POPEDOM_ID, IS_ALLOW_BROWSE, IS_ALLOW_DELETE, IS_ALLOW_EDIT, IS_ALLOW_INSERT, MODULE_ID, USER_ID FROM SYSDBA.USER_POPEDOM WHERE \"POPEDOM_ID\"=1;";

//        String[] sqls = new String[1];
//        sqls[0] = "SELECT * FROM `555` ORDER BY df DESC;";
//        sqls[0] = "SELECT * FROM \"TEST\".\"JDBC\"";
//        sqls[0] = "SELECT \"numid\", \"name\", \"school\", \"addrid\" FROM SYSDBA.\"test3\" WHERE \"numid\"=1;";

//        String[] sqls1 = new String[7];
//        sqls1[0] = "DROP TABLE \"TEST\".\"JDBC\"";
//        sqls1[1] = "CREATE TABLE \"TEST\".\"JDBC\" (\r\n"
//        		+ "  \"id\" NUMBER ,\r\n"
//        		+ "  \"name\" VARCHAR2(255 BYTE) \r\n"
//        		+ ")";
//        sqls1[2] = "ALTER TABLE \"TEST\".\"JDBC\" ADD CONSTRAINT \"tableName_PK\" PRIMARY KEY (\"id\")";
//        sqls1[3] = "INSERT INTO \"TEST\".\"JDBC\" VALUES (1, '小王')";
//        sqls1[4] = "INSERT INTO \"TEST\".\"JDBC\" VALUES (2, '小李')";
//        sqls1[5] = "DELETE FROM \"TEST\".\"JDBC\" WHERE \"id\"=2";
//        sqls1[6] = "UPDATE \"TEST\".\"JDBC\" SET \"name\"='小李' WHERE \"id\"=1";

        try {
//            query("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@172.19.5.234:1521:orcl","sys as sysdba","3edc$RFV", Oracle_sql);
//            query("com.mysql.jdbc.Driver","jdbc:mysql://172.19.5.234:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false","root","Ankki_mySQL123", MySql_sql);
//            query("com.microsoft.sqlserver.jdbc.SQLServerDriver","jdbc:sqlserver://172.19.5.234:1433;DatabaseName=master;encrypt=false;trustServerCertificate=false","sa","3edc$RFV", SqlServer_sql);
//            query("org.postgresql.Driver","jdbc:postgresql://172.19.1.12:5432/postgres?encrypt=false&trustServerCertificate=false","postgres","Ceshi123", PostgreSQL_sql);
//            query("org.postgresql.Driver","jdbc:postgresql://172.19.1.244:5432/postgres","gpadmin","gpadmin", Greenplum_sql);
//            query("com.sybase.jdbc4.jdbc.SybDriver","jdbc:sybase:Tds:172.19.1.180:5000/test","sa","Ceshi123", Sybase_sql);
//            query("org.apache.hive.jdbc.HiveDriver","jdbc:hive2://172.19.1.91:10000/default","root","Ankki@2009", Hive_sql);
//            query("com.mysql.cj.jdbc.Driver","jdbc:mysql://172.19.1.146:4000/test","root","Ceshi123", TiDB_sql);
//            query("com.mysql.jdbc.Driver","jdbc:mysql://172.19.3.170:2881/oceanbase","demo","12345678", OceanBase_sql);
//            query("com.teradata.jdbc.TeraDriver","jdbc:teradata://172.19.1.204/DATABASE=test,DBS_PORT=1025","dbc","dbc", Teradata_sql);
//            query("org.mariadb.jdbc.Driver","jdbc:mysql://172.19.1.98:3306/test","root","Ceshi123", MariaDB_sql);
//            query("com.kingbase8.Driver","jdbc:kingbase8://172.19.1.170:54321/test","system","Ceshi123", KingBase_sql);
//            query("com.intersystems.jdbc.IRISDriver","jdbc:IRIS://172.19.1.77:1972/%sys","_system","Ceshi123", IRIS_sql);
//            query("com.informix.jdbc.IfxDriver","jdbc:informix-sqli://172.19.5.224:9088/sysadmin:informixserver=informix","informix","3edc$RFV", Informix_sql);
//            query("com.ibm.db2.jcc.DB2Driver","jdbc:db2://172.19.1.203:50000/test","db2inst1","Ankki@2009", DB2_sql);
//            query("com.intersys.jdbc.CacheDriver","jdbc:Cache://172.19.1.222:1972/%sys","_SYSTEM","ankki", Cache_sql);
//            query("org.postgresql.Driver","jdbc:postgresql://172.19.1.92:25308/test","test","Ceshi123", GaussDB_sql);
//            query("com.gbase.jdbc.Driver","jdbc:gbase://172.19.1.165:5258/test","root","Ceshi123", Gbase8a_sql);
//            query("com.gbasedbt.jdbc.Driver","jdbc:gbasedbt-sqli://172.19.1.146:9088/test:GBASEDBTSERVER=gbaseserver;CLIENT_LOCALE=zh_cn.utf8;SQLMODE=GBase;NEWCODESET=UTF8,zh_cn.UTF8,57372;DB_LOCALE=zh_CN.57372;","gbasedbt","Ankki@2009", Gbase8s_sql);
//            query("com.taosdata.jdbc.rs.RestfulDriver","jdbc:TAOS-RS://172.19.1.250:6041/information_schema","root","Ceshi123", TDengine_sql);
            query("org.apache.phoenix.jdbc.PhoenixDriver","jdbc:phoenix:172.19.5.224:2181","","", Hbase_sql);

//            query("dm.jdbc.driver.DmDriver","jdbc:dm://172.19.1.238:5236/schema=TEST?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT","SYSDBA","SYSDBA", DM8_sql);
//            MongoDBUtil mongoDBUtil = new MongoDBUtil("mongodb://root:Ceshi123@172.19.1.250:27017/test?authSource=admin");
//            mongoDBUtil.executeOperation("SELECT", "test", null, null);

//            getBatchQuery(con, sqls);
//            goBatchUpdate(con, sqls1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            close();
        }

//        String className = "com.mysql.jdbc.Driver";
//        String url = "jdbc:mysql://172.19.5.229:3306/test?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT";
//        String user = "root";
//        String password = "Ankki_mySQL123";
//        String sql1 = "INSERT INTO `TEST`.`JDBC` VALUES (1, '小王', 5/3,5/3,SYSDATE());";
//        String sql2 = "UPDATE `TEST`.`JDBC` SET name='小王' WHERE id=1;";
//        String sql3 = "SELECT * FROM `TEST`.`JDBC`;";
//        String sql4 = "DELETE FROM `TEST`.`JDBC` WHERE id=1;";
//        String sql5 = "{call `TEST`.`JDBC_TEST`(?,?,?,?)};";
//        String callParams = "[1, '小王', 1, 1]";
//        prepareCall(className, url, user, password, sql5, callParams);
    }
}
