package com.thkmon.database.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Clob;
import java.util.ArrayList;

import com.thkmon.database.annotation.BBColumn;
import com.thkmon.database.annotation.BBPrimaryKey;
import com.thkmon.database.annotation.BBTable;
import com.thkmon.database.prototype.BBEntity;

public class BBMapperUtil {
	
	public static void loadClass() {
		
	}
	
	
	public static BBMapper getInstance() {
		return new BBMapper();
	}
	
	
	public static String getTableName(BBEntity inputData) {
		if (inputData == null) {
			return null;
		}
		
		Annotation[] annotations = inputData.getClass().getDeclaredAnnotations();
		if (annotations == null || annotations.length == 0) {
			return null;
		}
		
		int annotationCount = annotations.length;
		
		for (int i=0; i<annotationCount; i++) {
			if (annotations[i] == null) {
				continue;
			}
			
			try {
				return ((BBTable)annotations[i]).name();
			} catch (Exception e) {
				// 무시
			}
		}
		
		return null;
	}
	
	
	public static String getPrimaryKeyName(BBEntity inputData) {
		if (inputData == null) {
			return null;
		}
		
		Field[] fields = inputData.getClass().getDeclaredFields();
		if (fields == null || fields.length == 0) {
			return null;
		}
			
		int fieldCount = fields.length;
		for (int k=0; k<fieldCount; k++) {
			Field field = fields[k];
			if (field == null) {
				continue;
			}
			
			Annotation[] annotations = field.getDeclaredAnnotations();
			if (annotations == null || annotations.length == 0) {
				return null;
			}
			
			int annotationCount = annotations.length;
			
			for (int i=0; i<annotationCount; i++) {
				if (annotations[i] == null) {
					continue;
				}
				
				try {
					return ((BBPrimaryKey)annotations[i]).name();
				} catch (Exception e) {
					// 무시
				}
			}
		}
		
		return null;
	}
	
	
	public static Object getPrimaryKeyValue(BBEntity inputData) {
		if (inputData == null) {
			return null;
		}
		
		Field[] fields = inputData.getClass().getDeclaredFields();
		if (fields == null || fields.length == 0) {
			return null;
		}
			
		int fieldCount = fields.length;
		for (int k=0; k<fieldCount; k++) {
			Field field = fields[k];
			if (field == null) {
				continue;
			}
			
			Annotation[] annotations = field.getDeclaredAnnotations();
			if (annotations == null || annotations.length == 0) {
				return null;
			}
			
			int annotationCount = annotations.length;
			
			for (int i=0; i<annotationCount; i++) {
				if (annotations[i] == null) {
					continue;
				}
				
				try {
					BBPrimaryKey primaryKey = (BBPrimaryKey)annotations[i];
					if (primaryKey != null) {
						
						Class fieldClass = BBMapperUtil.getColumnType(field);
						if (fieldClass == null) {
							continue;
						}
						
						String fieldClassName = fieldClass.getName();
						if (fieldClassName != null) {
							if (fieldClassName.indexOf("java.lang.String") > -1) {
								field.setAccessible(true);
								String result = (String) field.get(inputData);
								field.setAccessible(false);
								return result;
							
							} else if (fieldClassName.indexOf("java.sql.Blob") > -1) {
								field.setAccessible(true);
								Blob result = (Blob) field.get(inputData);
								field.setAccessible(false);
								return result;
								
							} else if (fieldClassName.indexOf("java.sql.Clob") > -1) {
								field.setAccessible(true);
								Clob result = (Clob) field.get(inputData);
								field.setAccessible(false);
								return result;
								
							} else {
								field.setAccessible(true);
								int result = Integer.parseInt(String.valueOf(field.get(inputData)));
								field.setAccessible(false);
								return result;
							}
						}
					}
				} catch (Exception e) {
					// 무시
				}
			}
		}
		
		return null;
	}

	
	public static String getColumnName(Field field) {
		if (field == null) {
			return null;
		}
		
		Annotation[] annotations = field.getDeclaredAnnotations();
		if (annotations == null || annotations.length == 0) {
			return null;
		}
		
		int annotationCount = annotations.length;
		
		for (int i=0; i<annotationCount; i++) {
			if (annotations[i] == null) {
				continue;
			}
			
			try {
				return ((BBColumn)annotations[i]).name();
			} catch (Exception e) {
				// 무시
			}
		}
		
		return null;
	}
	
	
	public static Class getColumnType(Field field) {
		if (field == null) {
			return null;
		}

		return field.getType();
	}
	
	
	public static ArrayList<String> getColumnNameList(BBEntity inputData) {
		
		ArrayList<String> columnNameList = null;
		
		Field[] fields = inputData.getClass().getDeclaredFields();
		if (fields == null || fields.length == 0) {
			return null;
		}
			
		int fieldCount = fields.length;
		for (int k=0; k<fieldCount; k++) {
			Field field = fields[k];
			if (field == null) {
				continue;
			}
		
			Annotation[] annotations = field.getDeclaredAnnotations();
			if (annotations == null || annotations.length == 0) {
				return null;
			}
			
			int annotationCount = annotations.length;
			
			for (int i=0; i<annotationCount; i++) {
				if (annotations[i] == null) {
					continue;
				}
				
				try {
					String dataColumn = ((BBColumn)annotations[i]).name();
					
					if (dataColumn != null && dataColumn.length() > 0) {
						if (columnNameList == null) {
							columnNameList = new ArrayList<String>();
						}
						
						columnNameList.add(dataColumn);
					}
					
				} catch (Exception e) {
					// 무시
				}
			}
		}
		
		return columnNameList;
	}
	
	
	public static Object getColumnValue(BBEntity inputData, String columnName) throws Exception {
		if (columnName == null || columnName.length() == 0) {
			throw new Exception("DataAnnotationUtil getColumnValue : getColumnValue is null or empty.");
		}
		
		Field[] fields = inputData.getClass().getDeclaredFields();
		if (fields == null || fields.length == 0) {
			return null;
		}
			
		int fieldCount = fields.length;
		for (int k=0; k<fieldCount; k++) {
			Field field = fields[k];
			if (field == null) {
				continue;
			}
		
			Annotation[] annotations = field.getDeclaredAnnotations();
			if (annotations == null || annotations.length == 0) {
				return null;
			}
			
			int annotationCount = annotations.length;
			
			for (int i=0; i<annotationCount; i++) {
				if (annotations[i] == null) {
					continue;
				}
				
				try {
					String dataColumn = ((BBColumn)annotations[i]).name();
					
					if (dataColumn != null && dataColumn.equals(columnName)) {

						Class fieldClass = BBMapperUtil.getColumnType(field);
						if (fieldClass == null) {
							continue;
						}
						
						String fieldClassName = fieldClass.getName();
						if (fieldClassName != null) {
							if (fieldClassName.indexOf("java.lang.String") > -1) {
								field.setAccessible(true);
								String result = (String) field.get(inputData);
								field.setAccessible(false);
								return result;
								
							} else if (fieldClassName.indexOf("java.sql.Blob") > -1) {
								field.setAccessible(true);
								Blob result = (Blob) field.get(inputData);
								field.setAccessible(false);
								return result;
								
							} else if (fieldClassName.indexOf("java.sql.Clob") > -1) {
								field.setAccessible(true);
								Clob result = (Clob) field.get(inputData);
								field.setAccessible(false);
								return result;
								
							} else {
								field.setAccessible(true);
								int result = Integer.parseInt(String.valueOf(field.get(inputData)));
								field.setAccessible(false);
								return result;
							}
						}
					}
					
				} catch (Exception e) {
					// 무시
				}
			}
		}
		
		return null;
	}
	
	
//	public static void main(String[] sd) {
//		System.out.println("Dfdf");
//		
//		DdocBlobInfo blobInfo = new DdocBlobInfo();
//		
//		System.out.println(getTableName(blobInfo));
//		System.out.println(getPrimaryKey(blobInfo));
//		
//		
//		
//		
//	}
}
