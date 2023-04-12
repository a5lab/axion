package com.a5lab.axion.domain;

import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.a5lab.axion.config.ApplicationTestBaseConfig;
import com.a5lab.axion.config.JpaAuditingConfiguration;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
    JpaAuditingConfiguration.class
})
@ApplicationTestBaseConfig
public abstract class AbstractRepositoryTests  extends AbstractAnyTests{

}