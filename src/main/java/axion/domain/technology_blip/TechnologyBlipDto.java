package axion.domain.technology_blip;

import jakarta.validation.constraints.NotNull;

import axion.domain.radar.Radar;
import axion.domain.ring.RingDto;
import axion.domain.segment.SegmentDto;
import axion.domain.technology.TechnologyDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TechnologyBlipDto {

  private Long id;

  @NotNull
  private Radar radar;

  private TechnologyDto technology;

  private SegmentDto segment;

  private RingDto ring;
}
