package com.polyman.family.repository;



import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.polyman.family.entity.neo4j.Person;


/**
 * The repository to perform CRUD operations on book entities
 */
public interface PersonRepository extends Neo4jRepository<Person, Long> {
	
	Person findByNativeId(String nativeId);
	
	@Query("match(p:Person) where p.nativeId={nativeId} set p={map} return p")
	Person updatePerson(@Param("nativeId") String nativeId,@Param("map") Map<String,Object> map);
	
	@Query("match (p:Person)-[r:p2f]->(f:Family) where f.nativeId={nativeId} and  any (x in r.name where x='创建' or x='管理') return p")
	List<Person> findCreatorPerson(@Param("nativeId") String nativeId);
	
	@Query("match (p:Person)-[r:p2f]->(f:Family) where p.nativeId={nativeId} and f.nativeId={familyNativeId} and any (x in r.name where x='创建' or x='管理') return p")
	Person isFamilyCreatorPerson(@Param("nativeId") String nativeId,@Param("familyNativeId") String familyNativeId);
	
	@Query("match (p1:Person)-[r:p2p]->(p2:Person) where p1.nativeId={nativeId} and r.name={p2PRelation} return p2")
    List<Person> findP2PRelationPersonList(@Param("nativeId") String nativeId,@Param("p2PRelation") String p2PRelation);
	
	
	
	@Query("match(p1:Person)-[r:p2p]-(p2:Person) where p1.nativeId={nativeIdOne} and p2.nativeId={nativeIdTwo} delete r")
	void deletePersonRelation(@Param("nativeIdOne") String nativeIdOne,@Param("nativeIdTwo") String nativeIdTwo);
	
	@Query("match (p1:Person) where p1.nativeId={nativeId} detach delete p1")
	void deletePersonAllRelation(@Param("nativeId") String nativeId);
	
	@Query("match(p1:Person)-[r]-(f1:Family) where p1.nativeId={nativeId} and f1.nativeId={familyNativeId} delete r")
	void deletePersonByFamily(@Param("nativeId") String nativeId,@Param("familyNativeId") String familyNativeId);
	
	@Query("match(p1:Person)-[r1:p2f]->(f1:Family)<--(p2:Person) where p1.nativeId={opNativeId} and p2.nativeId={nativeId} and any (x in r1.name where x='创建' or x='管理') return p1 ")
	Person queryOpInSameFamily(@Param("opNativeId") String opNativeId,@Param("nativeId") String nativeId);
}