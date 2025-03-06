# Base User service

## preliminary requirements

- `docker --version` >= 25.0.0 (maybe earlier)

## how to start
For better development I recommend not to build the application in Docker
(it's too slow, I really don't like this)

so what should you do to start an app:

- download docker (`idk`)
- start docker engine (~~good for new developers to remember~~)
- download jdk >= 17
- `git clone https://github.com/KPfromSainP/idfc`
- `cd idfc`
- `mv .env.example .env` (and type your own environment variables)
- `docker compose up`
- check `docker ps` and find `postgres` container (name `idfc-postgres-1`)
- `./mvnw spring-boot:run`
- check [localhost:8080](http://localhost:8080) (and see the endpoints in doc)

To try Email service you should give som information about your email sender.
Type 

