package com.a5lab.axion.domain.technology_blip;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.a5lab.axion.domain.ModelApprover;
import com.a5lab.axion.domain.ModelError;

@RequiredArgsConstructor
public class TechnologyBlipUniqueFieldsSaveApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final TechnologyBlipDto technologyBlipDto;

  private final TechnologyBlip technologyBlip;


  @Override
  public List<ModelError> approve() {
    if (!Objects.equals(technologyBlipDto.getId(), technologyBlip.getId())) {
      return List.of(new ModelError("radar_id_and_technology_id_is_taken",
          messageSource.getMessage("technology_blip.error.radar_id_and_technology_id_is_taken", null,
              LocaleContextHolder.getLocale()), null));
    }
    return new LinkedList<>();
  }
}
