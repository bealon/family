package com.polyman.family.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.polyman.family.entity.neo4j.Family;
import com.polyman.family.entity.neo4j.Person;


/**
 * The repository to perform CRUD operations on book entities
 */
public interface FamilyRepository extends Neo4jRepository<Family, Long> {
	
	Family findByNativeId (String nativeId);
	
	@Query("match (p:Person)-[:p2f]-(f:Family) where p.nativeId={personNativeId}  return f")
	List<Family> findFamilyByPersonId (@Param("personNativeId") String personNativeId);
	
	@Query("match (p1:Person)-[:p2p]-(p2:Person)-[:p2f]-(f:Family)  where p1.nativeId={personNativeId1} and p2.nativeId={personNativeId2} and f.nativeId={familyNativeId}  return distinct f")
	Family queryInSameFamily (@Param("personNativeId1") String personNativeId1,@Param("personNativeId2") String personNativeId2,@Param("familyNativeId") String familyNativeId);
	
	@Query("match(f:Family) where f.nativeId={nativeId} set f={map} return f")
	Family updateFamily(@Param("nativeId") String nativeId,@Param("map") Map<String,Object> map);
	
	@Query("match(f:Family) where f.name contains {name} and f.birthland contains {birthland}  return f skip {skip} limit {limit}")
	List<Family> queryFamilyList (@Param("name") String name,@Param("birthland") String birthland,@Param("skip") int skip,@Param("limit") int limit);
	
	@Query("match (p:Person)-[r:p2f]-(f:Family) where f.nativeId={familyNativeId} and p.nativeId={opPersonNativeId}  and any (x in r.name where x='创建') detach delete f")
	void deleteFamily(@Param("opPersonNativeId") String opPersonNativeId,@Param("familyNativeId") String familyNativeId);
	

}