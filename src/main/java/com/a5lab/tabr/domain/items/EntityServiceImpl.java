package com.a5lab.tabr.domain.entities;

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
public class EntityServiceImpl implements EntityService {
  private final EntityRepository entityRepository;
  private final EntityMapper entityMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<Entity> findAll() {
    return entityRepository.findAll();
  }


  @Override
  @Transactional(readOnly = true)
  public Page<EntityDto> findAll(Pageable pageable) {
    return entityRepository.findAll(pageable).map(entityMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<EntityDto> findById(Long id) {
    return entityRepository.findById(id).map(entityMapper::toDto);
  }

  @Override
  @Transactional
  public EntityDto save(EntityDto entityDto) {
    return entityMapper.toDto(entityRepository.save(entityMapper.toEntity(entityDto)));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    entityRepository.deleteById(id);
  }
}
