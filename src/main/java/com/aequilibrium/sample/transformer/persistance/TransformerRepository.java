package com.aequilibrium.sample.transformer.persistance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.aequilibrium.sample.transformer.TransformerImpl;

@Repository
public interface TransformerRepository extends JpaRepository<TransformerImpl, Long> {

}
