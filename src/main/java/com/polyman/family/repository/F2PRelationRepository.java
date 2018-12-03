package com.polyman.family.repository;


import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.polyman.family.entity.neo4j.relationships.F2PRelation;


/**
 * The repository to perform CRUD operations on book entities
 */
public interface F2PRelationRepository extends Neo4jRepository<F2PRelation, Long> {
	
}