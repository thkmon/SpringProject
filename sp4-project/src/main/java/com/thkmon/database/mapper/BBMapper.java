package com.thkmon.database.mapper;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import com.thkmon.database.prototype.BBEntity;
import com.thkmon.util.string.StringUtil;

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
	
	
	public BBEntity select(BBEntity bbEntity) throws Exception {
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
		Vector bindVector = new Vector();
		bindVector.add(primaryKeyValue);
				
		return select(bbEntity, sql, bindVector, false);
	}
	
	
	public BBEntity select(BBEntity bbEntity, String sql) throws Exception {
		return select(bbEntity, sql, null, false);
	}
	
	
	public BBEntity select(BBEntity bbEntity, String sql, Vector bindList) throws Exception {
		return select(bbEntity, sql, bindList, false);
	}
	
	
	public BBEntity select(BBEntity bbEntity, String sql, Vector bindList, boolean bTransaction) throws Exception {
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			if (!bTransaction || privateConn == null) {
				setConnection();
			}
			
			pstmt = privateConn.prepareStatement(sql);
			if (bindList != null && bindList.size() > 0) {
				Object bindObj = null;
				int bindCount = bindList.size();
				for (int i=0; i<bindCount; i++) {
					bindObj = bindList.get(i);
					
					if (bindObj == null) {
						pstmt.setNull(i+1, 0);
						
					} else if (bindObj instanceof String) {
						pstmt.setString(i+1, String.valueOf(bindObj));
						
					} else if (bindObj instanceof Blob) {
						pstmt.setBlob(i+1, (Blob)bindObj);
						
					} else if (bindObj instanceof Clob) {
						pstmt.setClob(i+1, (Clob)bindObj);
						
					} else {
						pstmt.setInt(i+1, Integer.parseInt(String.valueOf(bindObj)));
					}
				}
			}
			
			resultSet = pstmt.executeQuery();
			
			if (resultSet != null) {
				while (resultSet.next()) {
					
					Field[] fields = bbEntity.getClass().getDeclaredFields();
					int fieldCount = fields.length;
					for (int i=0; i<fieldCount; i++) {
						Field field = fields[i];
						if (field == null) {
							continue;
						}
						
						String columnName = BBMapperUtil.getColumnName(field);
						if (columnName == null || columnName.length() == 0) {
							continue;
						}
						
						Class fieldClass = BBMapperUtil.getColumnType(field);
						if (fieldClass == null) {
							continue;
						}
						
						String fieldClassName = fieldClass.getName();
						
						if (fieldClassName.indexOf("java.lang.String") > -1) {
							String value = resultSet.getString(columnName);
							field.setAccessible(true);
							field.set(bbEntity, value);
							field.setAccessible(false);
							
						} else if (fieldClassName.indexOf("java.sql.Blob") > -1) {
							Blob value = resultSet.getBlob(columnName);
							field.setAccessible(true);
							field.set(bbEntity, value);
							field.setAccessible(false);
							
						} else if (fieldClassName.indexOf("java.sql.Clob") > -1) {
							Clob value = resultSet.getClob(columnName);
							field.setAccessible(true);
							field.set(bbEntity, value);
							field.setAccessible(false);
							
						} else {
							int value = resultSet.getInt(columnName);
							field.setAccessible(true);
							field.setInt(bbEntity, value);
							field.setAccessible(false);
						}
					}
				}
				
				return bbEntity;
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
		
		return null;
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
			
			ArrayList<String> columnNameList = BBMapperUtil.getColumnNameList(bbEntity);
			if (columnNameList == null || columnNameList.size() == 0) {
				throw new Exception("DataMapper insert : columnNameList is null or empty");
			}
			
			int columnNameCount = columnNameList.size();
			
			StringBuffer sqlBuff = new StringBuffer();
			sqlBuff.append(" INSERT INTO ");
			sqlBuff.append(tableName);
			sqlBuff.append(" ( ");
			sqlBuff.append(StringUtil.join(columnNameList, ","));
			sqlBuff.append(" ) ");
			sqlBuff.append(" values ");
			sqlBuff.append(" ( ");
			sqlBuff.append(StringUtil.join("?", ",", columnNameCount));
			sqlBuff.append(" ) ");
			
			if (!bTransaction || privateConn == null) {
				setConnection();
			}
			
			String sql = sqlBuff.toString();
			pstmt = privateConn.prepareStatement(sql);
			Object bindObj = null;
			for (int i=0; i<columnNameCount; i++) {
				String columnName = columnNameList.get(i);
				
				bindObj = BBMapperUtil.getColumnValue(bbEntity, columnName);
				
				if (bindObj == null) {
					pstmt.setNull(i+1, 0);
					
				} else if (bindObj instanceof String) {
					pstmt.setString(i+1, String.valueOf(bindObj));
					
				} else if (bindObj instanceof Blob) {
					pstmt.setBlob(i+1, (Blob)bindObj);
					
				} else if (bindObj instanceof Clob) {
					pstmt.setClob(i+1, (Clob)bindObj);
					
				} else {
					pstmt.setInt(i+1, Integer.parseInt(String.valueOf(bindObj)));
				}
			}
			
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
			
			ArrayList<String> columnNameList = BBMapperUtil.getColumnNameList(bbEntity);
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
			
			int bindIndex = 1;
			Object bindObj = null;
			for (int i=0; i<columnNameCount; i++) {
				String columnName = columnNameList.get(i);
				
				bindObj = BBMapperUtil.getColumnValue(bbEntity, columnName);
				
				if (bindObj == null) {
					pstmt.setNull(bindIndex++, 0);
					
				} else if (bindObj instanceof String) {
					pstmt.setString(bindIndex++, String.valueOf(bindObj));
					
				} else if (bindObj instanceof Blob) {
					pstmt.setBlob(bindIndex++, (Blob)bindObj);
					
				} else if (bindObj instanceof Clob) {
					pstmt.setClob(bindIndex++, (Clob)bindObj);
					
				} else {
					pstmt.setInt(bindIndex++, Integer.parseInt(String.valueOf(bindObj)));
				}
			}
			
			Object primaryKeyValue = BBMapperUtil.getPrimaryKeyValue(bbEntity);
			
			if (primaryKeyValue instanceof String) {
				pstmt.setString(bindIndex++, String.valueOf(primaryKeyValue));
				
			} else {
				pstmt.setInt(bindIndex++, Integer.parseInt(String.valueOf(primaryKeyValue)));
			}
			
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
			
			ArrayList<String> columnNameList = BBMapperUtil.getColumnNameList(bbEntity);
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
			
			int bindIndex = 1;
			Object primaryKeyValue = BBMapperUtil.getPrimaryKeyValue(bbEntity);
			
			if (primaryKeyValue instanceof String) {
				pstmt.setString(bindIndex++, String.valueOf(primaryKeyValue));
				
			} else {
				pstmt.setInt(bindIndex++, Integer.parseInt(String.valueOf(primaryKeyValue)));
			}
			
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