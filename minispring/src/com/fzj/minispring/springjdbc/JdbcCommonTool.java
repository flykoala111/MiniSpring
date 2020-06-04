package com.fzj.minispring.springjdbc;

import com.fzj.minispring.exception.MiniSpringException;
import com.fzj.minispring.log.MiniSpringLog;
import com.fzj.minispring.minienum.LogEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库通用操作（受保护）
 **/
class JdbcCommonTool {
    private JdbcCommonTool() {

    }

    private static Connection connection = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;

    private static void printSqlMessage(String sql, List<Object> objects) {
        StringBuffer paramStringBuffer = new StringBuffer();
        int i = 1;
        for (Object object : objects) {
            paramStringBuffer.append(object);
            if (i < objects.size()) {
                paramStringBuffer.append(",");
            }
            i++;
        }
        new MiniSpringLog().printConsoleBlank(JdbcCommonTool.class, LogEnum.springjdbc.getName("debug"), sql);
        new MiniSpringLog().printConsoleNontimeBlank("parameters => ", paramStringBuffer);
    }

    private static void printResMessage(int total) {
        new MiniSpringLog().printConsoleNontimeBlank("total => ", total);
    }

    private static void printResdetail(Object detail) {
        new MiniSpringLog().printConsoleNontimeBlank("result => ", detail);
    }

    /**
     * insert/delete/update
     *
     * @param sql
     * @return
     */
    @SuppressWarnings("finally")
    static int sqlUpdate(String sql, List<Object> objects)
            throws MiniSpringException {
        printSqlMessage(sql, objects);
        int a = -1;
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= objects.size(); i++) {
                preparedStatement.setObject(i, objects.get(i - 1));
            }
            a = preparedStatement.executeUpdate();
            printResMessage(1);
            printResdetail(a);
        } catch (SQLException e) {
            e.printStackTrace();
            new MiniSpringLog().printException("fzjupdate error", e.getMessage());
            throw new MiniSpringException(0, LogEnum.springjdbc.getName("exception"), "fzjupdate error", e.getMessage(), e.getStackTrace());
        } finally {
            ConnectionPool.close(connection, preparedStatement, resultSet);
        }
        return a;
    }

    /**
     * select
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("finally")
    static List<Map<String, Object>> sqlSelect(String sql, List<Object> objects) throws MiniSpringException {
        printSqlMessage(sql, objects);
        List<Map<String, Object>> listmap_res = new ArrayList<Map<String, Object>>();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 1; i <= objects.size(); i++) {
                preparedStatement.setObject(i, objects.get(i - 1));
            }
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int allcol = rsmd.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> map_res = new HashMap<String, Object>();
                for (int i = 1; i <= allcol; i++) {
                    map_res.put(rsmd.getColumnName(i), resultSet.getObject(i));
                }
                listmap_res.add(map_res);

            }
            printResMessage(listmap_res.size());
            printResdetail(net.sf.json.JSONArray.fromObject(listmap_res));
        } catch (SQLException e) {
            e.printStackTrace();
            new MiniSpringLog().printException("fzjselect error", e.getMessage());
            throw new MiniSpringException(0, LogEnum.springjdbc.getName("exception"), "fzjselect error", e.getMessage(), e.getStackTrace());
        } finally {
            ConnectionPool.close(connection, preparedStatement,
                    resultSet);
        }
        return listmap_res;
    }
}
