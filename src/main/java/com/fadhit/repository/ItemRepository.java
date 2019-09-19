package com.fadhit.repository;

import org.springframework.data.repository.CrudRepository;

import com.fadhit.model.Item;

public interface ItemRepository extends CrudRepository <Item, Integer>{

}
