# unisystemproject

## Installera

Skapa först databasen "universityportal" i pgadmin och kör sedan dessa kommandon i Glassfishs asadmin-program:

```
create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGSimpleDataSource --restype javax.sql.XADataSource --property portNumber=5432:password=LexiconJava:user=postgres:serverName=localhost:databaseName=universityportal jpa_universityportal_postgresql_pool

create-jdbc-resource --connectionpoolid jpa_universityportal_postgresql_pool jdbc/universityportal
```

Användaren ska heta postgres och lösenordet är LexikonJava.

För att lägga till testdata, gå till `/install.xhtml`
