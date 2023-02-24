/*
 * Copyright © 2023 KorurgChat author or authors. All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package korurg.twitch.impl.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration("twitchDataSourceConfiguration")
@EnableJpaRepositories(
        basePackages = {"korurg.twitch"},
        entityManagerFactoryRef = "twitchEntityFactoryManager",
        transactionManagerRef = "twitchTransactionManager"
)
public class DatasourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.twitch")
    public DataSourceProperties twitchDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean("twitchDataSource")
    public DataSource twitchDataSource() {
        return twitchDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean("twitchEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean twitchEntityFactoryManager(@Qualifier("twitchDataSource") DataSource dataSource,
                                                                             EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        return entityManagerFactoryBuilder.dataSource(dataSource)
                .packages("korurg.twitch")
                .build();
    }

    @Bean
    public PlatformTransactionManager twitchTransactionManager(
            @Qualifier("twitchEntityManagerFactory") LocalContainerEntityManagerFactoryBean todosEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(todosEntityManagerFactory.getObject()));
    }

    @Bean("twitchLiquibaseProperties")
    @ConfigurationProperties(prefix = "spring.datasource.twitch.liquibase")
    public LiquibaseProperties twitchLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean
    public SpringLiquibase twitchLiquibase() {
        return springLiquibase(twitchDataSource(), twitchLiquibaseProperties());
    }

    private static SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabels(properties.getLabels());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }
}