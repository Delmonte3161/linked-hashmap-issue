package com.example.demo.complex;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface IngestionEventModelRepository
		extends PagingAndSortingRepository<IngestionEventModel, String> {

}