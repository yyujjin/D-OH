package com.DOH.DOH.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.DOH.DOH.mapper")  // MyBatis Mapper 패키지 스캔
public class configure {

    private static final String MAPPER_LOCATIONS_PATH = "classpath:/mapper/**/*Mapper.xml";

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")  // HikariCP 설정을 가져옴
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource() {
        try {
            HikariConfig config = hikariConfig();
            return new HikariDataSource(config);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to configure DataSource: " + e.getMessage(), e);
        }
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATIONS_PATH)
        );
        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
