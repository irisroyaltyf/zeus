package top.yulegou.zeus.manager.db;/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.yulegou.zeus.constant.ErrorCode;
import top.yulegou.zeus.dao.domain.Result;
import top.yulegou.zeus.dao.domain.publish.DbConnectionConfig;
import top.yulegou.zeus.domain.DbColumnsDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author irisroyalty
 * @date 2020/6/28
 **/
@Component
public class MysqlManager {

    public List<DbColumnsDTO> getColumnNames(DbConnectionConfig dbConfig, String tableName) throws SQLException, Exception{
        if (StringUtils.isEmpty(tableName)) {
            throw new Exception("schema of table name is null");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://").append(dbConfig.getHost())
                .append(":").append(dbConfig.getPort()).
                append("/").append(dbConfig.getSchema())
                .append("?").append("characterEncoding=").append(dbConfig.getCharset())
                .append("&useSSL=false").append("&serverTimezone=UTC");
        List<DbColumnsDTO> tableNames = null;
        Connection con = null;
        Statement stmt = null;
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(sb.toString(), dbConfig.getUser(), dbConfig.getPwd());
//            String sql = "SELECT column_name, data_type,(case when data_type = 'int' or  data_type = 'float' or data_type = 'double' or data_type = 'decimal' then NUMERIC_PRECISION else CHARACTER_MAXIMUM_LENGTH end ) as data_length,\n" +
//                    "(case when IS_NULLABLE = 'NO' then 0 else 1 end)as data_Null,(case when COLUMN_KEY='PRI' then 1 else 0 end) as data_IsPK " +
//                    " FROM information_schema.COLUMNS WHERE table_schema = '"+dbConfig.getSchema()+"' and table_name = '"+tableName+"'";
        String sql = "show full columns from " + dbConfig.getSchema()+"." + tableName;
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        tableNames = new ArrayList<DbColumnsDTO>();
        while(rs.next()) {

            DbColumnsDTO dbColumnsDTO = new DbColumnsDTO();
            dbColumnsDTO.setAutoInc(StringUtils.equals(rs.getString("extra"), "auto_increment"));
            dbColumnsDTO.setColumnName(rs.getString("Field"));
            dbColumnsDTO.setType(rs.getString("type"));
            dbColumnsDTO.setDefaultString(rs.getString("default"));
            dbColumnsDTO.setNotNull(StringUtils.equalsIgnoreCase(rs.getString("null"), "YES"));
            dbColumnsDTO.setPrimary(StringUtils.equalsIgnoreCase(rs.getString("key"), "PRI"));
            tableNames.add(dbColumnsDTO);
        }
        return tableNames;
    }

    public List<String> getSchemas(String host, String port, String charset, String user, String pwd) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://").append(host)
                .append(":").append(port).
                append("/information_schema")
                .append("?").append("characterEncoding=").append(charset)
                .append("&useSSL=false").append("&serverTimezone=UTC");
        Connection c = DriverManager.getConnection(sb.toString(), user, pwd);
        try {
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String ss = new String("select SCHEMA_NAME from information_schema.schemata;");
            preparedStatement = c.prepareStatement(ss);
            resultSet = preparedStatement.executeQuery();
            List<String> schemas = new ArrayList<>();

            while (resultSet.next()) {
                schemas.add(resultSet.getString(1));
            }
            return schemas;
        } finally {
            try {
                if (c != null && !c.isClosed()) {
                    c.close();
                }
            } catch (Exception e) {
            }
        }
    }
    public void testConnection(String host, String port, String schema, String charset, String user, String pwd) throws SQLException {
        if (StringUtils.isEmpty(charset)) {
            charset="utf8";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://").append(host)
                .append(":").append(port).
                append("/").append(schema)
                .append("?").append("characterEncoding=").append(charset)
                .append("&useSSL=false").append("&serverTimezone=UTC");
        Connection c = DriverManager.getConnection(sb.toString(), user, pwd);
        try {
        } finally {
            try {
                if (c != null && !c.isClosed()) {
                    c.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public List<String> getTables(DbConnectionConfig connectionConfig) throws SQLException{

        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://").append(connectionConfig.getHost())
                .append(":").append(connectionConfig.getPort()).
                append("/").append(connectionConfig.getSchema())
                .append("?").append("characterEncoding=").append(connectionConfig.getCharset())
                .append("&useSSL=false").append("&serverTimezone=UTC");
        Connection c = DriverManager.getConnection(sb.toString(),
                connectionConfig.getUser(), connectionConfig.getPwd());
        try {
            c.getCatalog();
            DatabaseMetaData dmd = c.getMetaData();
            ResultSet resultSet = dmd.getTables(c.getCatalog(), connectionConfig.getSchema(), null, new String[]{"TABLE"});
            List<String> schemas = new ArrayList<>();
            while (resultSet.next()) {
                schemas.add(resultSet.getString("TABLE_NAME"));
            }
            return schemas;
        } finally {
            try {
                if (c != null && !c.isClosed()) {
                    c.close();
                }
            } catch (Exception e) {
            }
        }
    }
    public long insertRow(DbConnectionConfig connectionConfig, String tableName, Map<String, String> field) throws SQLException {
        return insertRow(connectionConfig, tableName, field, false);
    }

    public long insertRow(DbConnectionConfig connectionConfig, String tableName, Map<String, String> field, boolean hasGenerateKey) throws SQLException {
        if (field == null || field.isEmpty()) {
            return 0;
        }
        Connection c = buildConnection(connectionConfig);
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ").append(tableName).append("(`")
                    .append(StringUtils.join(field.keySet(), "`,`"))
                    .append("`) values ('")
                    .append(StringUtils.join(field.values(), "','"))
                    .append("')");

            String sql = sb.toString();
            System.out.println(sql);
            PreparedStatement stmt = c.prepareStatement(sql);
            if (hasGenerateKey) {
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                return rs.getLong(1);
            } else {
                return stmt.executeUpdate();
            }
        } finally {
            if (!c.isClosed()) {
                c.close();
            }
        }
    }

    public Connection buildConnection(DbConnectionConfig connectionConfig) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc:mysql://").append(connectionConfig.getHost())
                .append(":").append(connectionConfig.getPort()).
                append("/").append(connectionConfig.getSchema())
                .append("?").append("characterEncoding=").append(connectionConfig.getCharset())
                .append("&useSSL=false").append("&serverTimezone=UTC");
        Connection c = DriverManager.getConnection(sb.toString(),
                connectionConfig.getUser(), connectionConfig.getPwd());
        return c;
    }
}
