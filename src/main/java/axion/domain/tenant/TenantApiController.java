package axion.domain.tenant;

import jakarta.validation.Valid;
import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantApiController {

  private final TenantService tenantService;

  @GetMapping("")
  public Collection<TenantDto> index(@Valid TenantFilter tenantFilter,
                               @RequestParam(defaultValue = "${application.paging.page}") int page,
                               @RequestParam(defaultValue = "${application.paging.size}") int size,
                               @RequestParam(defaultValue = "title,asc") String[] sort) {
    Sort.Direction direction = sort[1].equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort.Order order = new Sort.Order(direction, sort[0]);
    Page<TenantDto> tenantDtoPage = tenantService.findAll(tenantFilter, PageRequest.of(page - 1, size, Sort.by(order)));
    return tenantDtoPage.getContent();

  }
}
