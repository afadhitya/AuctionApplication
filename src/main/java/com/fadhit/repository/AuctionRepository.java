package com.fadhit.repository;

import org.springframework.data.repository.CrudRepository;

import com.fadhit.model.Auction;

public interface AuctionRepository extends CrudRepository<Auction, Integer> {
}
