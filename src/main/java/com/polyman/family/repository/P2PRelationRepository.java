package com.polyman.family.repository;


import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import com.polyman.family.entity.neo4j.relationships.P2PRelation;


/**
 * The repository to perform CRUD operations on book entities
 */
public interface P2PRelationRepository extends Neo4jRepository<P2PRelation, Long> {
	
	@Query("match(p1:Person)-[r:p2p]-(p2:Person) where p1.nativeId={nativeIdOne} and p2.nativeId={nativeIdTwo} return *")
	List<P2PRelation> findP2PRelation(@Param("nativeIdOne") String nativeIdOne,@Param("nativeIdTwo") String nativeIdTwo);


	@Query("match(p1:Person) where p1.realName={realNameOne} "
			+ "match(p2:Person) where p2.realName={realNameTwo} "
			+ "match p=shortestPath((p1)-[:p2p*]-(p2)) return *")
	List<P2PRelation> findP2PShortRelationByName(@Param("realNameOne") String realNameOne,@Param("realNameTwo") String realNameTwo);


	@Query("match(p1:Person)-[:p2f]->(f:Family)<-[:p2f]-(p2:Person) "
			+ "where p1.realName={realNameOne} and  p2.realName={realNameTwo} "
			+ "match (p3:Person)-[:p2f]->(f) where  p3.nativeId={opNativeId} "
			+ "match p=shortestPath((p1)-[:p2p*]-(p2)) return *")
	List<P2PRelation> findP2PShortRelationByNameInSameFamily(@Param("opNativeId") String opNativeId,@Param("realNameOne") String realNameOne,@Param("realNameTwo") String realNameTwo);
}