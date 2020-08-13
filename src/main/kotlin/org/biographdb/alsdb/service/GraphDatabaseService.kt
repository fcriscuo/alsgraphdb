package org.biographdb.alsdb.service

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import java.util.*

object GraphDatabaseService : AutoCloseable {
    private val neo4jUser = System.getenv("NEO4J_USER") ?: "neo4j"
    private val neo4jPassword = System.getenv("NEO4J_PASSWORD") ?: "neo4j"
    val neo4jUri = "bolt://localhost:7687"
    val driver: Driver = GraphDatabase.driver(neo4jUri, AuthTokens.basic(neo4jUser, neo4jPassword))

    override fun close() {
        driver.close()
        println("Neo4j driver closed")
    }

    fun generateUUID(): String = UUID.randomUUID().toString()


    fun generateNeo4jUUID(): String {
        val uuidQuery = "RETURN apoc.create.uuid() AS uuid;"
        var uuid = "0"
        driver.session().use { session ->
            val result = session.run(uuidQuery)
            while (result.hasNext()) {
                uuid = result.next().get("uuid").toString()
            }
        }
        return uuid
    }

}

fun main() {
    // test getting UUID
    println("UUID = ${GraphDatabaseService.generateUUID()}")
    println("UUID = ${GraphDatabaseService.generateUUID()}")
    println("UUID = ${GraphDatabaseService.generateUUID()}")
    GraphDatabaseService.close()
}