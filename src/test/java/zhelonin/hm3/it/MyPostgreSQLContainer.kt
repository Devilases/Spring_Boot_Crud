package zhelonin.hm3.it

import org.testcontainers.containers.PostgreSQLContainer

class MyPostgreSQLContainer(imageName: String) : PostgreSQLContainer<MyPostgreSQLContainer>(imageName)