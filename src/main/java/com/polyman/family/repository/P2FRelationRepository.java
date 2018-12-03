package com.polyman.family.repository;


import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.polyman.family.entity.neo4j.relationships.P2FRelation;


/**
 * The repository to perform CRUD operations on book entities
 */
public interface P2FRelationRepository extends Neo4jRepository<P2FRelation, Long> {
	
}