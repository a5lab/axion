package com.a5lab.axion.domain;

import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.a5lab.axion.config.ApplicationTestBaseConfig;
import com.a5lab.axion.config.JpaAuditingConfiguration;

@ApplicationTestBaseConfig
public abstract class AbstractMapperTests  extends AbstractAnyTests{
}