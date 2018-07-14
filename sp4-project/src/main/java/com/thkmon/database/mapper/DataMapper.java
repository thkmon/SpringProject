package com.thkmon.database.mapper;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import com.thkmon.database.prototype.Data;

public class DataMapper {

	
	private Connection privateConn = null;
	
	
	public static DataMapper getInstance() {
		return new DataMapper();
	}
	
	
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
	
	
	public Data select(Data inputData) throws Exception {
		String sql = "";
		
		String tableName = DataAnnotationUtil.getTableName(inputData);
		if (tableName == null || tableName.length() == 0) {
			throw new Exception("DataMapper select : tableName is null or empty");
		}
		
		String primaryKeyName = DataAnnotationUtil.getPrimaryKeyName(inputData);
		if (tableName == null || tableName.length() == 0) {
			throw new Exception("DataMapper select : primaryKeyName is null or empty");
		}
		
		Object primaryKeyValue = DataAnnotationUtil.getPrimaryKeyValue(inputData);
		if (tableName == null || tableName.length() == 0) {
			throw new Exception("DataMapper select : primaryKeyValue is null or empty");
		}
		
		sql = " SELECT * FROM " + tableName + " WHERE " + primaryKeyName + " = ? ";
		Vector bindVector = new Vector();
		bindVector.add(primaryKeyValue);
				
		return select(inputData, sql, bindVector, false);
	}
	
	
	public Data select(Data inputData, String sql) throws Exception {
		return select(inputData, sql, null, false);
	}
	
	
	public Data select(Data inputData, String sql, Vector bindList) throws Exception {
		return select(inputData, sql, bindList, false);
	}
	
	
	public Data select(Data inputData, String sql, Vector bindList, boolean bTransaction) throws Exception {
		// Data outputData = null;
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
						
					} else {
						pstmt.setInt(i+1, Integer.parseInt(String.valueOf(bindObj)));
					}
				}
			}
			
			resultSet = pstmt.executeQuery();
			
			if (resultSet != null) {
				while (resultSet.next()) {
					
					Field[] fields = inputData.getClass().getDeclaredFields();
					int fieldCount = fields.length;
					for (int i=0; i<fieldCount; i++) {
						Field field = fields[i];
						if (field == null) {
							continue;
						}
						
						String columnName = DataAnnotationUtil.getColumnName(field);
						if (columnName == null || columnName.length() == 0) {
							continue;
						}
						
						Class fieldClass = DataAnnotationUtil.getColumnType(field);
						if (fieldClass == null) {
							continue;
						}
						
						String fieldClassName = fieldClass.getName();
						
						if (fieldClassName.indexOf("java.lang.String") > -1) {
							String value = resultSet.getString(columnName);
							field.setAccessible(true);
							field.set(inputData, value);
							field.setAccessible(false);
							
						} else if (fieldClassName.indexOf("java.sql.Blob") > -1) {
							String value = resultSet.getString(columnName);
							field.setAccessible(true);
							field.set(inputData, value);
							field.setAccessible(false);
							
						} else {
							int value = resultSet.getInt(columnName);
							field.setAccessible(true);
							field.setInt(inputData, value);
							field.setAccessible(false);
						}
					}
				}
				
				return inputData;
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
}