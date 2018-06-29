package com.prongbang.sjt.ddl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class DDL {

	
	/**
	 * Generate SQL SELECT STAR FROM WHERE
	 * 
	 * @param table
	 * @param whereClause
	 * @return
	 */
	public static String selectStarSQL(String table, String[] whereClause) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT * FROM ");
		sql.append(table);
		
		int i = 0;
        sql.append((whereClause.length > 0) ? " WHERE " : "");
        for (String where : whereClause) {
            sql.append((whereClause.length - 1 > (i++)) ? (where + " = ? AND ") : (where + " = ?"));
        }
		
		return sql.toString();
	}
	
	/**
     * Generate SQL SELECT CUSTOM ATTRIBUTES
     * 
     * @param table
     * @param attributes
     * @param whereClause
     * @return
     */
    public static String selectAttrSQL(String table, String[] attributes, String[] whereClause) {
    	
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        int i = 0;
        for (String atr : attributes) {
            sql.append((attributes.length - 1 > (i++)) ? (atr + ",") : atr);
        }
        sql.append(" FROM ");
        sql.append(table);

        i = 0;
        sql.append((whereClause.length > 0) ? " WHERE " : "");
        for (String where : whereClause) {
            sql.append((whereClause.length - 1 > (i++)) ? (where + " = ? AND ") : (where + " = ?"));
        }

        return sql.toString();
    }
	
    /**
     * Generate SQL INSERT
     * 
     * @param table
     * @param entity
     * @param pkAutoIncrement
     * @return
     */
    public static String insertSQL(String table, Object entity, String pkAutoIncrement) {
    	
        StringBuilder sql = new StringBuilder();
        
        if(pkAutoIncrement == null) pkAutoIncrement = "";
        
		try {

			Class<?> c = Class.forName(entity.getClass().getName());
			Method[] methods = c.getDeclaredMethods();
			
			int v = 0;
			String attribute = "";
			List<String> culumns = new ArrayList<String>();
			
			sql.append("INSERT INTO ");
			sql.append(table);
			sql.append(" (");
			
			for (int i = 0; i < methods.length; i++){
				if (methods[i].getName().startsWith("get")) {
					attribute = firstNameToLower(methods[i].getName().substring(3));
					if(!pkAutoIncrement.equals(attribute)){
						v++;
						culumns.add(attribute); 
					}
				}
			}
			
			String e = "";
			for(int j = 0; j < v; j++) {
				sql.append((v - 1 > j) ? (culumns.get(j) + ",") : (culumns.get(j) + ")"));
				e += ((v - 1 > j) ? ("?,") : ("?"));
			}
			
			sql.append(" VALUES ");
			sql.append("(");
			sql.append(e);
			sql.append(")");
			
		} catch (Throwable e) {

		}
        return sql.toString();
    }
    
	/**
	 * Generate SQL UPDATE
	 * 
	 * How to use
	 * Example : String sql = genSQLUpdate("Table Name", bean, new String[]{"id"});
	 * 
	 * @param entity the object
	 * @return
	 */
	public static String updateSQL(String table, Object entity, String[] whereClause) {
		
		StringBuilder sql = new StringBuilder();
		
		try {
			
			Class<?> c = Class.forName(entity.getClass().getName()); 
			Method[] methods = c.getDeclaredMethods();

			Object value;
			String attribute = "";
			String sqlUpdate = "UPDATE ";
			String sqlSet = " SET ";
			String sqlWhere = " WHERE ";
			Boolean found = false;
			
			sql.append(sqlUpdate); 
			sql.append(table); 
			
			for (int i = 0; i < methods.length; i++){
				if (methods[i].getName().startsWith("get")) {
					
					attribute = firstNameToLower(methods[i].getName().substring(3));
					value = methods[i].invoke(entity);
					
					if(value != null) { 
						for(int j = 0; j < whereClause.length; j++){ 
							if(whereClause[j].equals(attribute)) { 
								found = true;
								sqlWhere += ddlWhere(whereClause[j]);
								break;
							} else {
								found = false;
							}
						}
						
						if(!found) { 
							sqlSet += ddlSET(attribute);
						}
					}
				}
			}
			
			sql.append(deleteLastComma(sqlSet));
			sql.append(deleteLastAnd(sqlWhere));
			
		} catch (Throwable e) {
			
		}
		
		return sql.toString();
	}

	/**
	 * Generate SQL DELETE
	 * 
	 * @param table
	 * @param entity
	 * @param whereClause
	 * @return
	 */
	public static String deleteSQL(String table, Object entity, String[] whereClause) {
		
		StringBuilder sql = new StringBuilder();

		sql.append("DELETE FROM ");
		sql.append(table);
		
		int i = 0;
        sql.append((whereClause.length > 0) ? " WHERE " : "");
        for (String where : whereClause) {
            sql.append((whereClause.length - 1 > (i++)) ? (where + " = ? AND ") : (where + " = ?"));
        }
				
		return sql.toString();
	}
	
	
	/**
	 * Delete Last AND
	 * 
	 * @param ddlUpdate
	 * @return
	 */
	private static String deleteLastAnd(String ddlUpdate){
		
		return (ddlUpdate.substring(0, ddlUpdate.length()-4));
	}
	
	/**
	 * Delete Last comma (,)
	 * 
	 * @param ddlUpdate
	 * @return
	 */
	private static String deleteLastComma(String ddlUpdate){
		
		return (ddlUpdate.substring(0, ddlUpdate.length()-1));
	}
	
	/**
	 * Generate SET : attribute = ?,
	 * 
	 * @param attribute
	 * @return
	 */
	private static String ddlSET(String attribute){
		
		return attribute + "= ?,";
	}
	
	/**
	 * Generate WHERE : attribute = ? AND
	 * 
	 * @param attribute
	 * @return
	 */
	private static String ddlWhere(String attribute){
		
		return attribute + " = ? AND ";
	}
	
	/**
	 * Change first character to lower
	 * 
	 * @param name
	 * @return
	 */
	private static String firstNameToLower(String name){
		if(name == null) return name;
		String f = (name.substring(0,1)).toLowerCase();
		String e = name.substring(1);
		return f + e;
	}
	

	/**
	 * How to use 
	 * Example : Field[] fields = FieldsUtil.dump(String.class);
	 * 
	 * @param klazz
	 * @return
	 */
	public static <T> Field[] dump(Class<T> klazz) {
        Field[] fields = klazz.getDeclaredFields(); 
        
        return fields;
    }
	
	/**
	 * Print Fields in Class
	 * 
	 * @param fields
	 */
	public static void print(Field[] fields){
        System.out.printf("%d fields:%n", fields.length);
		for (Field field : fields) {
            System.out.printf("%s %s %s%n",
                Modifier.toString(field.getModifiers()),
                field.getType().getSimpleName(),
                field.getName()
            );
        }
	}
	
}
