/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.service

import org.neo4j.driver.*
import java.util.concurrent.TimeUnit
import java.util.logging.Level

/**
 * Responsible for establishing a connection to the local Neo4j database
 * and managing database sessions
 * Neo4j user and password are extected to be supplied from environment variables
 * Created by fcriscuo on 7/11/20.
 */
object AlsDatabaseService {

    private val neo4j_user = Preferences.neo4j_user
    private val neo4j_password = Preferences.neo4j_password
    val neo4jUri = "bolt://localhost:7687"
    private val config = Config.builder()
        .withMaxConnectionLifetime(30, TimeUnit.MINUTES)
        .withMaxConnectionPoolSize(50)
        .withConnectionAcquisitionTimeout(2, TimeUnit.MINUTES)
        .withConnectionLivenessCheckTimeout(5, TimeUnit.MINUTES)
        .withLogging(Logging.console(Level.INFO))
        .build()
    private val driver = GraphDatabase.driver(neo4jUri, AuthTokens.basic(neo4j_user, neo4j_password), config)

    fun getSession() = driver.session()

    fun closeDriver() = driver.close()

    fun registerShutdownHook(driver: Driver): Unit {
        Runtime.getRuntime().addShutdownHook(Thread() {
            fun run() {
                driver.close()
            }
        })
    }

    init {
        registerShutdownHook(this.driver)
    }
}

fun main() {
    val session = AlsDatabaseService.getSession()
    session.use { session -> }

}
