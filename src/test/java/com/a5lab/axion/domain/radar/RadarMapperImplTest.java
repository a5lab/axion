package com.a5lab.axion.domain.radar;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.AbstractMapperTests;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.ring.RingMapper;
import com.a5lab.axion.domain.ring.RingRepository;
import com.a5lab.axion.domain.ring.RingService;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.tenant.TenantService;
import com.a5lab.axion.domain.tenant.TenantServiceImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

public class RadarMapperImplTest  extends AbstractMapperTests{

  @MockBean
  private RadarTypeRepository radarTypeRepository;

  @MockBean
  private RingRepository ringRepository;

  @Autowired
  private RadarMapper radarMapper;

  @Autowired
  private RingMapper ringMapper;

  @Test
  public void testRingToRingDto() {
    final Radar radar = radarMapper.toEntity(null);
    final Ring ring = ringMapper.toEntity(null);

    Assertions.assertNull(radar);
    Assertions.assertNull(ring);

  }

}

