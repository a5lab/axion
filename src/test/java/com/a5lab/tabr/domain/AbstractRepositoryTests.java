package com.a5lab.tabr.domain;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.a5lab.tabr.config.ApplicationTestBaseConfig;
import com.a5lab.tabr.config.JpaAuditingConfiguration;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
    JpaAuditingConfiguration.class
})
@ApplicationTestBaseConfig
public abstract class AbstractRepositoryTests {
}