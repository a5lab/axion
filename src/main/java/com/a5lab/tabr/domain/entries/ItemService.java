package com.a5lab.tabr.domain.entries;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {

  Collection<Item> findAll();

  Page<ItemDto> findAll(Pageable pageable);

  Optional<ItemDto> findById(Long id);

  ItemDto save(ItemDto itemDto);

  void deleteById(Long id);
}
