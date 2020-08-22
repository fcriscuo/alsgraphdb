/*
 * Copyright (c) 2020. BioGraphDb
 * All rights reserved
 */

package org.biographdb.alsdb.service

import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.session.Session
import org.neo4j.ogm.session.SessionFactory

object OgmDatabaseService {
    private val neo4jUser = System.getenv("NEO4J_USER") ?: "neo4j"
    private val neo4jPassword = System.getenv("NEO4J_PASSWORD") ?: "neo4j"
    val neo4jUri = "bolt://localhost:7687"
    var configuration: Configuration = Configuration.Builder()
        .uri("bolt://neo4j:fjc92116@localhost")
        .connectionPoolSize(50)
        .build()
    val packageName: String = "org.biographdb.alsdb.domain"

    val sessionFactory: SessionFactory = SessionFactory(configuration, packageName)

    fun closeSessionFactory() {
        sessionFactory.close()
        println("SessionFactory is closed...")
    }

    fun getSession(): Session = sessionFactory.openSession()


}