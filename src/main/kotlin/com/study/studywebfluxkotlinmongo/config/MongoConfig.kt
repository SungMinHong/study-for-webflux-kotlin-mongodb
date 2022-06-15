package com.study.studywebfluxkotlinmongo.config

import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.connection.ClusterSettings
import com.mongodb.reactivestreams.client.MongoClients
import com.study.studywebfluxkotlinmongo.domain.EventRepository
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories
import java.util.*


@Configuration
@EnableReactiveMongoRepositories(
    basePackageClasses = [EventRepository::class]
)
@EnableConfigurationProperties(MongoProperties::class)
class MongoConfig(mongoProperties: MongoProperties) : AbstractReactiveMongoConfiguration() {

    private val credential: MongoCredential =
        MongoCredential.createCredential(mongoProperties.username, mongoProperties.database, mongoProperties.password)

    override fun getDatabaseName() = "study"

    override fun reactiveMongoClient() = mongoClient()


    @Bean
    fun mongoClient() = MongoClients.create(MongoClientSettings.builder()
        .applyToClusterSettings { builder: ClusterSettings.Builder ->
            builder.hosts(
                Arrays.asList(
                    ServerAddress("localhost", 27017)
                )
            )
        }
        .credential(credential)
        .build()
    )

    @Bean
    override fun reactiveMongoTemplate(
        databaseFactory: ReactiveMongoDatabaseFactory,
        mongoConverter: MappingMongoConverter
    ): ReactiveMongoTemplate = ReactiveMongoTemplate(mongoClient(), databaseName)
}