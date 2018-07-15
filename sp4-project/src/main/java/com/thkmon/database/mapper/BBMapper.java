package com.thkmon.database.mapper;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.thkmon.database.prototype.BBColumnNameList;
import com.thkmon.database.prototype.BBColumnValueList;
import com.thkmon.database.prototype.BBEntity;
import com.thkmon.database.prototype.BBEntityList;

public class BBMapper {

	
	private Connection privateConn = null;

	
	private void setConnection() {

		try {
			if (privateConn != null) {
				if (!privateConn.isClosed()) {
					privateConn.rollback();
					privateConn.close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			privateConn = null;
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String dbUrl = "jdbc:mysql://localhost:3306/ddoc?useUnicode=true&characterEncoding=utf8";
			String user = "ddoc";
			String password = "ddoc";
			
			privateConn = DriverManager.getConnection(dbUrl, user, password);
			privateConn.setAutoCommit(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void rollbackAndClose(Connection conn) {
		try {
			if (conn != null) {
				conn.rollback();
				conn.close();
			}
			
		} catch (Exception e) {
			conn = null;
			
		} finally {
			conn = null;
		}
	}
	
	
	private void close(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
			
		} catch (Exception e) {
			conn = null;
			
		} finally {
			conn = null;
		}
	}
	
	
	private void close(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			
		} catch (Exception e) {
			resultSet = null;
			
		} finally {
			resultSet = null;
		}
	}
	
	
	private void close(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			
		} catch (Exception e) {
			pstmt = null;
			
		} finally {
			pstmt = null;
		}
	}
	
	
	public BBEntity selectSingleRow(BBEntity bbEntity) throws Exception {
		String sql = "";
		
		String tableName = BBMapperUtil.getTableName(bbEntity);
		if (tableName == null || tableName.length() == 0) {
			throw new Exception("DataMapper select : tableName is null or empty");
		}
		
		String primaryKeyName = BBMapperUtil.getPrimaryKeyName(bbEntity);
		if (tableName == null || tableName.length() == 0) {
			throw new Exception("DataMapper select : primaryKeyName is null or empty");
		}
		
		Object primaryKeyValue = BBMapperUtil.getPrimaryKeyValue(bbEntity);
		if (tableName == null || tableName.length() == 0) {
			throw new Exception("DataMapper select : primaryKeyValue is null or empty");
		}
		
		sql = " SELECT * FROM " + tableName + " WHERE " + primaryKeyName + " = ? ";
		BBColumnValueList bindVector = new BBColumnValueList();
		bindVector.add(primaryKeyValue);
				
		BBEntityList list = select(bbEntity, sql, bindVector, false);
		if (list == null || list.size() == 0) {
			return null;
		}
		
		if (list.size() > 1) {
			throw new Exception("BBMapper selectSingleRow : count == [" + list.size() + "]");
		}
		
		return list.get(0);
	}
	

	public BBEntityList select(BBEntity bbEntity, String sql) throws Exception {
		return select(bbEntity, sql, null, false);
	}
	
	
	public BBEntityList select(BBEntity bbEntity, String sql, BBColumnValueList bindList) throws Exception {
		return select(bbEntity, sql, bindList, false);
	}
	
	
	private BBEntityList select(BBEntity bbEntity, String sql, BBColumnValueList bindList, boolean bTransaction) throws Exception {
		return selectList(bbEntity, sql, bindList, false);
	}
	
	
	private BBEntityList selectList(BBEntity inputEntity, String sql, BBColumnValueList bindList, boolean bTransaction) throws Exception {
		BBEntityList entityList = null;
		
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			if (!bTransaction || privateConn == null) {
				setConnection();
			}
			
			pstmt = privateConn.prepareStatement(sql);
			BBMapperUtil.bind(pstmt, bindList);
			
			resultSet = pstmt.executeQuery();
			
			if (resultSet != null) {
				while (resultSet.next()) {
					
					BBEntity oneEntity = (BBEntity) (Class.forName(inputEntity.getClass().getName()).newInstance());
					
					Field[] fields = oneEntity.getClass().getDeclaredFields();
					int fieldCount = fields.length;
					for (int i=0; i<fieldCount; i++) {
						Field field = fields[i];
						if (field == null) {
							continue;
						}
						
						BBMapperUtil.setFieldValue(oneEntity, field, resultSet);
					}
					
					if (entityList == null) {
						entityList = new BBEntityList();
					}
					
					entityList.add(oneEntity);
				}
			}
			
		} catch (Exception e) {
			rollbackAndClose(privateConn);
			throw e;
			
		} finally {
			close(resultSet);
			close(pstmt);
			
			if (!bTransaction) {
				close(privateConn);
			}			
		}
		
		return entityList;
	}
	
	
	public int insert(BBEntity bbEntity) throws Exception {
		return insert(bbEntity, false);
	}
	
	
	public int insert(BBEntity bbEntity, boolean bTransaction) throws Exception {
		
		int bResult = -1;
		
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			
			String tableName = BBMapperUtil.getTableName(bbEntity);
			if (tableName == null || tableName.length() == 0) {
				throw new Exception("DataMapper insert : tableName is null or empty");
			}
			
			BBColumnNameList columnNameList = BBMapperUtil.getColumnNameList(bbEntity);
			if (columnNameList == null || columnNameList.size() == 0) {
				throw new Exception("DataMapper insert : columnNameList is null or empty");
			}
			
			int columnNameCount = columnNameList.size();
			
			StringBuffer sqlBuff = new StringBuffer();
			sqlBuff.append(" INSERT INTO ");
			sqlBuff.append(tableName);
			sqlBuff.append(" ( ");
			sqlBuff.append(BBMapperUtil.join(columnNameList, ","));
			sqlBuff.append(" ) ");
			sqlBuff.append(" values ");
			sqlBuff.append(" ( ");
			sqlBuff.append(BBMapperUtil.join("?", ",", columnNameCount));
			sqlBuff.append(" ) ");
			
			if (!bTransaction || privateConn == null) {
				setConnection();
			}
			
			String sql = sqlBuff.toString();
			pstmt = privateConn.prepareStatement(sql);
			
			BBColumnValueList columnValueList = columnNameList.convertToColumnValueList(bbEntity);
			BBMapperUtil.bind(pstmt, columnValueList);
			
			bResult = pstmt.executeUpdate();
			if (!bTransaction) {
				privateConn.commit();
			}
			
		} catch (Exception e) {
			rollbackAndClose(privateConn);
			throw e;
			
		} finally {
			close(resultSet);
			close(pstmt);
			
			if (!bTransaction) {
				close(privateConn);
			}			
		}
		
		return bResult;
	}
	
	
	public int update(BBEntity bbEntity) throws Exception {
		return update(bbEntity, false);
	}
	
	
	public int update(BBEntity bbEntity, boolean bTransaction) throws Exception {
		
		int bResult = -1;
		
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			
			String tableName = BBMapperUtil.getTableName(bbEntity);
			if (tableName == null || tableName.length() == 0) {
				throw new Exception("DataMapper update : tableName is null or empty");
			}
			
			BBColumnNameList columnNameList = BBMapperUtil.getColumnNameList(bbEntity);
			if (columnNameList == null || columnNameList.size() == 0) {
				throw new Exception("DataMapper update : columnNameList is null or empty");
			}
			
			String primaryKeyName = BBMapperUtil.getPrimaryKeyName(bbEntity);
			
			StringBuffer sqlBuff = new StringBuffer();
			sqlBuff.append(" UPDATE ");
			sqlBuff.append(tableName);
			sqlBuff.append(" SET ");
			
			int columnNameCount = columnNameList.size();
			int lastIndex = columnNameCount - 1;
			for (int i=0; i<columnNameCount; i++) {
				String columnName = columnNameList.get(i);
				sqlBuff.append(columnName);
				if (i < lastIndex) {
					sqlBuff.append(" = ?, ");
				} else {
					sqlBuff.append(" = ? ");
				}
			}
			
			sqlBuff.append(" WHERE ");
			sqlBuff.append(primaryKeyName);
			sqlBuff.append(" = ? ");
			
			if (!bTransaction || privateConn == null) {
				setConnection();
			}
			
			String sql = sqlBuff.toString();
			pstmt = privateConn.prepareStatement(sql);
			
			BBColumnValueList columnValueList = columnNameList.convertToColumnValueList(bbEntity);
			
			Object primaryKeyValue = BBMapperUtil.getPrimaryKeyValue(bbEntity);
			columnValueList.add(primaryKeyValue);
			
			BBMapperUtil.bind(pstmt, columnValueList);
			
			bResult = pstmt.executeUpdate();
			if (!bTransaction) {
				privateConn.commit();
			}
			
		} catch (Exception e) {
			rollbackAndClose(privateConn);
			throw e;
			
		} finally {
			close(resultSet);
			close(pstmt);
			
			if (!bTransaction) {
				close(privateConn);
			}			
		}
		
		return bResult;
	}
	
	
	public int delete(BBEntity bbEntity) throws Exception {
		return delete(bbEntity, false);
	}
	
	
	public int delete(BBEntity bbEntity, boolean bTransaction) throws Exception {
		
		int bResult = -1;
		
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			
			String tableName = BBMapperUtil.getTableName(bbEntity);
			if (tableName == null || tableName.length() == 0) {
				throw new Exception("DataMapper delete : tableName is null or empty");
			}
			
			BBColumnNameList columnNameList = BBMapperUtil.getColumnNameList(bbEntity);
			if (columnNameList == null || columnNameList.size() == 0) {
				throw new Exception("DataMapper delete : columnNameList is null or empty");
			}
			
			String primaryKeyName = BBMapperUtil.getPrimaryKeyName(bbEntity);
			
			StringBuffer sqlBuff = new StringBuffer();
			sqlBuff.append(" DELETE FROM ");
			sqlBuff.append(tableName);
			sqlBuff.append(" WHERE ");
			sqlBuff.append(primaryKeyName);
			sqlBuff.append(" = ? ");
			
			if (!bTransaction || privateConn == null) {
				setConnection();
			}
			
			String sql = sqlBuff.toString();
			pstmt = privateConn.prepareStatement(sql);
			
			Object primaryKeyValue = BBMapperUtil.getPrimaryKeyValue(bbEntity);
			BBColumnValueList columnValueList = new BBColumnValueList();
			columnValueList.add(primaryKeyValue);
			
			BBMapperUtil.bind(pstmt, columnValueList);
			
			bResult = pstmt.executeUpdate();
			if (!bTransaction) {
				privateConn.commit();
			}
			
		} catch (Exception e) {
			rollbackAndClose(privateConn);
			throw e;
			
		} finally {
			close(resultSet);
			close(pstmt);
			
			if (!bTransaction) {
				close(privateConn);
			}			
		}
		
		return bResult;
	}
}