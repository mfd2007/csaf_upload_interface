# CSAF Import Modul Test

## Purpose

The purpose of this project is to provide an interface for uploading CSAF documents that were collected by `csaf_downloader`.

## Specication of REST-API-Endpoint

Details on the REST-API-Endpoint for uploading CSAF documents are availabe in the folder `api` as [OpenAPI YAML](./api/api.yaml) and as [HTML](./api/api.html).

## Running the application with Docker

To start the application with `docker` & `docker-compose`execute the following command:

```BASH
docker-compose up

```

To update and rebuild the image use:

```BASH
docker-compose up --build

```

The suggested way to ensure all is cleaned up properly is by this command:

```BASH
docker-compose rm
```

It clears stopped containers correctly. Might consider removing clutter of images too, especially the ones fiddled with:

```BASH
docker images

docker image rm <image-id>
```

##

To start the application directly execute the following commands:

```BASH
./gradlew generateKey
./gradlew bootRun
```

## Update OpenAPI

HTML documation of the API can be generated from ```api/api.yaml```. This file is written manually.

```BASH
./gradlew openApiGenerate
```

## OpenAPI YAML of the implementation

A OpenAPI YAML from the implementation can be generated with

```BASH
./gradlew generateOpenApiDocs
```

At the moment the documentation in the sourcecode doesn't match completly the manually generated file.

## Security consideration

This is only a test application to support the development. The generated key for TLS is just for testing purposes! Do not use this project in any kind of production environment.
