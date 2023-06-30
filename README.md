# CSAF Import Modul Test

## Purpose

The purpose of this project is to provide an interface for uploading CSAF documents that were collected by csaf downloader.

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



## Security consideration

This is only an test application to support he development. The generated key for TLS is just for testing purposes! Do not use this project in any kind of production environment.