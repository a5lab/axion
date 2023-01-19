package com.a5lab.tabr.domain.entries;

import java.util.Collection;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ItemServiceImpl implements ItemService {
  private final ItemRepository itemRepository;
  private final ItemMapper itemMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<Item> findAll() {
    return itemRepository.findAll();
  }


  @Override
  @Transactional(readOnly = true)
  public Page<ItemDto> findAll(Pageable pageable) {
    return itemRepository.findAll(pageable).map(itemMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<ItemDto> findById(Long id) {
    return itemRepository.findById(id).map(itemMapper::toDto);
  }

  @Override
  @Transactional
  public ItemDto save(ItemDto itemDto) {
    return itemMapper.toDto(itemRepository.save(itemMapper.toEntity(itemDto)));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    itemRepository.deleteById(id);
  }
}
