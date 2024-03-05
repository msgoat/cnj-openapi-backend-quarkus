# cnj-openapi-backend-quarkus

Cloud native Java backend based on Quarkus using MP OpenAPI to document REST APIs.

The application is packaged as a multi-architecture docker image which supports the following platforms:
* linux/amd64
* linux/arm64/v8

## Synopsis

### Static vs. generated OpenAPI files
``
In this showcase, [MP OpenApi feature](https://github.com/eclipse/microprofile-open-api) is used to demonstrate the `Code First` approach
of documenting REST API according to the OpenAPI standard: the actual OpenAPI specification file is generated from
annotated application code. The generated OpenAPI specification file is served by MP OpenApi via `/q/openapi` in YAML format.

To demonstrate the `API First` or `Contract First` approach as well, a static OpenAPI specification file has been
added (see [openapi.yml](src/main/resources/META-INF/openapi.yml)) to the application code. The MP OpenAPI automatically
serves the static file as well.

MP OpenAPI supports a combination of both approaches: The `openapi.yml` file may be a fragment containing only the
static parts the OpenAPI specification. Fragments will be merged with the OpenAPI data generated from annotated code.

A Swagger UI for the exposed REST API is available as well via `/q/swagger-ui`.

> __Attention__: By default, the Swagger UI is switched off when Quarkus is not running in developer mode. If you want
> to have the Swagger UI even in production mode, you have to add the property `quarkus.swagger-ui.always-include=true`
> to your `application.properties` file.

## Status

![Build status](https://codebuild.eu-west-1.amazonaws.com/badges?uuid=eyJlbmNyeXB0ZWREYXRhIjoiZmoyTzBGYnBJUUhaU2V0bGwzZmg1NXdEL0dJejVFN2xVd2ZRNzUxM1c3SWxPYVBLemNBNU5UZm1XTE96MUNCS3BlS203OERIM0piVmpUbFYwbCs3dllzPSIsIml2UGFyYW1ldGVyU3BlYyI6Iko0aUFLNFAvYTRXeHhFM2wiLCJtYXRlcmlhbFNldFNlcmlhbCI6MX0%3D&branch=main)

## Release Information

A changelog can be found in [changelog.md](changelog.md).

## Docker Pull Command

`docker pull docker.cloudtrain.aws.msgoat.eu/cloudtrain/cnj-openapi-backend-quarkus`

## Helm Pull Command

`helm pull oci://docker.cloudtrain.aws.msgoat.eu/cloudtrain-charts/cnj-openapi-backend-quarkus`

## HOW-TO build this application locally

If all prerequisites are met, just run the following Maven command in the project folder:

```shell 
mvn clean verify -P pre-commit-stage
```

Build results: a Docker image containing the showcase application.

## HOW-TO start and stop this showcase locally

In order to run the whole showcase locally, just run the following docker commands in the project folder:

```shell 
docker compose up -d
docker compose logs -f 
```

Press `Ctlr+c` to stop tailing the container logs and run the following docker command to stop the showcase:

```shell 
docker compose down
```

## HOW-TO demo this showcase

The showcase application will be accessible:
* locally via `http://localhost:38080`
* remotely via `https://train2023-dev.k8s.cloudtrain.aws.msgoat.eu/cloudtrain/cnj-openapi-backend-micro` (if the training cluster is up and running)

The OpenAPI specification of the exposed REST API is available at URI `/q/openapi`.
A Swagger UI to check out the exposed REST API is available at URI `/q/swagger-ui`.
