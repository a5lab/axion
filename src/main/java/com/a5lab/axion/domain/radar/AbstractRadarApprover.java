package com.a5lab.axion.domain.radar;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.context.ApplicationContext;

import com.a5lab.axion.domain.AbstractAuditable;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.wizard.WizardDto;
import com.a5lab.axion.domain.wizard.processors.AbstractRadarProcessor;
import com.a5lab.axion.utils.JpaConstants;

public class AbstractRadarApprover extends Approver {

  protected final Radar radar;


  public AbstractRadarApprover(Radar radar) {
    this.radar = radar.
  }

}
