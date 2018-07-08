package com.thkmon.jpa.mapper;

import java.lang.reflect.Field;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.thkmon.exception.MsgException;

public class JPAMapper {
	
	public static JPAMapper jpaMapper = null;
	public static EntityManagerFactory entityManagerFactory = null;
	
	
	public static JPAMapper getInstance() {
		if (jpaMapper == null) {
			jpaMapper = new JPAMapper();
		}
		
		return jpaMapper;
	}
	
	
	public static EntityManagerFactory getEntityManagerFactory() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("jpabook");
		}
		
		return entityManagerFactory;
	}
	
	
	public void close(EntityManagerFactory obj) {
//		try {
//			if (obj != null) {
//				obj.close();
//			}
//			
//		} catch (Exception e) {
//			obj = null;
//			
//		} finally {
//			obj = null;
//		}
	}
	
	
	public void close(EntityManager obj) {
		try {
			if (obj != null) {
				obj.close();
			}
			
		} catch (Exception e) {
			obj = null;
			
		} finally {
			obj = null;
		}
	}
	
	
	public boolean insert(Class entityClass, Object newObject) throws Exception {
		
		boolean bResult = false;
		
		if (newObject == null) {
			throw new Exception("JPAMapper insert : newObject is null");
		}
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		
		try {
			// 엔티티 매니저 팩토리 생성
			emf = getEntityManagerFactory();
			
			// 엔티티 매니저 생성
			em = emf.createEntityManager();
			
			// 트랜잭션 획득
			tx = em.getTransaction();
			
			// 트랜잭션 시작
			tx.begin();
			
			// INSERT
			insert(em, entityClass, newObject);
			
			// 트랜잭션 커밋
			tx.commit();
			
			bResult = true;
			
		} catch (Exception e) {
			rollback(tx);
			throw e;
			
		} finally {
			close(em);
			close(emf);
		}
		
		return bResult;
	}
	
	
	public void insert(EntityManager em, Class entityClass, Object newObject) throws Exception {
		
		Object primaryKey = getPrimaryKey(newObject);
		
		// SELECT SINGLE ROW
		Object dbSingleRow = em.find(entityClass, primaryKey);
		if (dbSingleRow != null) {
			throw new MsgException("삽입 실패. 이미 존재하는 데이터입니다. primaryKey == [" + primaryKey + "]");
		}
		
		// INSERT
		em.persist(newObject);
	}
	
	
	public Object selectOne(Class entityClass, String primaryKey) throws Exception {
		
		if (primaryKey == null || primaryKey.length() == 0) {
			throw new Exception("JPAMapper selectOne : primaryKey == null || primaryKey.length() == 0");
		}
		
		Object dbSingleRow = null;
		EntityManagerFactory emf = null;
		EntityManager em = null;
		
		try {
			// 엔티티 매니저 팩토리 생성
			emf = getEntityManagerFactory();
			
			// 엔티티 매니저 생성
 			em = emf.createEntityManager();
			
			// SELECT SINGLE ROW
			dbSingleRow = selectOne(em, entityClass, primaryKey);
				
		} catch (Exception e) {
			throw e;
			
		} finally {
			close(em);
			close(emf);
		}
		
		return dbSingleRow;
	}
	
	
	public Object selectOne(EntityManager em, Class entityClass, String primaryKey) throws Exception {
		// SELECT SINGLE ROW
		Object dbSingleRow = em.find(entityClass, primaryKey);
		return dbSingleRow;
	}
	
	
	public boolean update(Class entityClass, Object newObject) throws Exception {
		
		boolean bResult = false;
		
		if (newObject == null) {
			throw new Exception("JPAMapper update : newObject is null");
		}
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		
		try {
			// 엔티티 매니저 팩토리 생성
			emf = getEntityManagerFactory();
			
			// 엔티티 매니저 생성
			em = emf.createEntityManager();
			
			// 트랜잭션 획득
			tx = em.getTransaction();
			
			// 트랜잭션 시작
			tx.begin();
			
			// UPDATE
			update(em, entityClass, newObject);
			
			// 트랜잭션 커밋
			tx.commit();
			
			bResult = true;
			
		} catch (Exception e) {
			rollback(tx);
			throw e;
			
		} finally {
			close(em);
			close(emf);
		}
		
		return bResult;
	}
	
	
	public void update(EntityManager em, Class entityClass, Object newObject) throws Exception {
		
		Object primaryKey = getPrimaryKey(newObject);
		
		// SELECT SINGLE ROW
		Object dbSingleRow = em.find(entityClass, primaryKey);
		if (dbSingleRow == null) {
			throw new MsgException("수정 실패. 존재하지 않는 데이터입니다. primaryKey == [" + primaryKey + "]");
		}
		
		// 첫번째 인덱스를 primaryKey라고 가정한다.
		Field[] fields = newObject.getClass().getDeclaredFields();
		if (fields != null && fields.length > 1) {
			Field[] dbfields = dbSingleRow.getClass().getDeclaredFields();
			
			if (dbfields != null && dbfields.length == fields.length) {
				int fieldCount = fields.length;
				for (int i=1; i<fieldCount; i++) {
					dbfields[i].setAccessible(true);
					fields[i].setAccessible(true);
					
					Object newValue = fields[i].get(newObject);
					dbfields[i].set(dbSingleRow, newValue);
					
					fields[i].setAccessible(false);
					dbfields[i].setAccessible(false);
				}
			}
		}
	}
	
	
	public Object getPrimaryKey(Object newObject) throws Exception {
		Object primaryKey = null;
		
		Field[] fields = newObject.getClass().getDeclaredFields();
		if (fields == null || fields.length < 1) {
			throw new Exception("JPAMapper insert : fields == null || fields.length < 1");
		}
		
		// 첫번째 인덱스를 primaryKey라고 가정한다.
		fields[0].setAccessible(true);
		primaryKey = fields[0].get(newObject);
		fields[0].setAccessible(false);
		
		if (primaryKey == null) {
			throw new Exception("JPAMapper insert : primaryKey == null");
		}
		
		if (primaryKey instanceof String && ((String)primaryKey).length() == 0) {
			throw new Exception("JPAMapper insert : primaryKey.length() == 0");
		}
		
		return primaryKey;
	}


	public boolean delete(Class entityClass, String primaryKey) throws Exception {
		
		boolean bResult = false;
		
		if (primaryKey == null || primaryKey.length() == 0) {
			throw new Exception("JPAMapper delete : primaryKey == null || primaryKey.length() == 0");
		}
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		
		try {
			// 엔티티 매니저 팩토리 생성
			emf = getEntityManagerFactory();
			
			// 엔티티 매니저 생성
			em = emf.createEntityManager();
			
			// 트랜잭션 획득
			tx = em.getTransaction();
		
			// 트랜잭션 시작
			tx.begin();
			
			// DELETE
			delete(em, entityClass, primaryKey);
			
			// 트랜잭션 커밋
			tx.commit();
			bResult = true;
			
		} catch (Exception e) {
			rollback(tx);
			throw e;
			
		} finally {
			close(em);
			close(emf);
		}
		
		return bResult;
	}
	
	
	public void delete(EntityManager em, Class entityClass, String primaryKey) throws Exception {
		// DELETE
		Object dbSingleRow = em.find(entityClass, primaryKey);
		if (dbSingleRow != null) {
			em.remove(dbSingleRow);
		} else {
			throw new MsgException("삭제 실패. 이미 삭제되었거나 존재하지 않는 데이터입니다. primaryKey == [" + primaryKey + "]");
		}
	}
	
	
	public void commit(EntityTransaction transanction) throws Exception {
		if (transanction != null) {
			transanction.commit();
		}
	}
	
	
	public void rollback(EntityTransaction transanction) throws Exception {
		if (transanction != null) {
			transanction.rollback();
		}
	}
	
	
	public void closeCommit(EntityManager entityManager) throws Exception {
		
		try {
			if (entityManager != null) {
				commit(entityManager.getTransaction());
			}
		
		} catch (Exception e) {
			throw e;
			
		} finally {
			close(entityManager);
		}
	}
	
	
	public void closeRollback(EntityManager entityManager) throws Exception {
		
		try {
			if (entityManager != null) {
				rollback(entityManager.getTransaction());
			}
		
		} catch (Exception e) {
			throw e;
			
		} finally {
			close(entityManager);
		}
	}
}
