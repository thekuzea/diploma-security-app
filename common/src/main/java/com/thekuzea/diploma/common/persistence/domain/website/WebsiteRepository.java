package com.thekuzea.diploma.common.persistence.domain.website;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteRepository extends MongoRepository<Website, String> {

}
