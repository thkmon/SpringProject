package com.thkmon.database.mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.thkmon.database.annotation.DataColumn;
import com.thkmon.database.annotation.DataPrimaryKey;
import com.thkmon.database.annotation.DataTable;
import com.thkmon.database.data.DdocBlobInfo;
import com.thkmon.database.prototype.Data;

public class DataAnnotationUtil {
	
	public static void loadClass() {
		
	}
	
	
	public static String getTableName(Data inputData) {
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
				return ((DataTable)annotations[i]).name();
			} catch (Exception e) {
				// 무시
			}
		}
		
		return null;
	}
	
	
	public static String getPrimaryKeyName(Data inputData) {
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
					return ((DataPrimaryKey)annotations[i]).name();
				} catch (Exception e) {
					// 무시
				}
			}
		}
		
		return null;
	}
	
	
	public static Object getPrimaryKeyValue(Data inputData) {
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
					DataPrimaryKey primaryKey = (DataPrimaryKey)annotations[i];
					if (primaryKey != null) {
						
						Class fieldClass = DataAnnotationUtil.getColumnType(field);
						if (fieldClass == null) {
							continue;
						}
						
						String fieldClassName = fieldClass.getName();
						if (fieldClassName != null) {
							if (fieldClassName.indexOf("String") > -1) {
								field.setAccessible(true);
								String result = (String) field.get(inputData);
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
				return ((DataColumn)annotations[i]).name();
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
