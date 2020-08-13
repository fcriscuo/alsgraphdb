package org.biographdb.alsdb.service

import org.neo4j.driver.*
import java.util.concurrent.TimeUnit
import java.util.logging.Level

/**
 * Created by fcriscuo on 7/11/20.
 */
object AlsDatabaseService {


    private val neo4jUser = System.getenv("NEO4J_USER") ?: "neo4j"
    private val neo4jPassword = System.getenv("NEO4J_PASSWORD") ?: "neo4j"
    val neo4jUri = "bolt://localhost:7687"
    private val config = Config.builder()
        .withMaxConnectionLifetime(30, TimeUnit.MINUTES)
        .withMaxConnectionPoolSize(50)
        .withConnectionAcquisitionTimeout(2, TimeUnit.MINUTES)
        .withConnectionLivenessCheckTimeout(5, TimeUnit.MINUTES)
        .withLogging(Logging.console(Level.INFO))
        .build()
    private val driver = GraphDatabase.driver(neo4jUri, AuthTokens.basic(neo4jUser, neo4jPassword), config)

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
