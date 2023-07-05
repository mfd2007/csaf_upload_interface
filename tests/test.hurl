POST http://localhost:8080/api/v1/import
[MultipartFormData]
advisory: file,invalid.json; application/json
HTTP 400
[Asserts]
body contains "Missing parameter: validation_status"

POST http://localhost:8080/api/v1/import
[MultipartFormData]
validation_status: test
HTTP 400
[Asserts]
body contains "Missing parameter: advisory"

POST http://localhost:8080/api/v1/import
[MultipartFormData]
advisory: file,test.hurl; application/json
validation_status: valid
HTTP 422
[Asserts]
body contains "Unrecognized token"

POST http://localhost:8080/api/v1/import
[MultipartFormData]
advisory: file,invalid.json; application/json
validation_status: valid
HTTP 422
[Asserts]
body contains "Schema error"

POST http://localhost:8080/api/v1/import
[MultipartFormData]
advisory: file,valid.json; application/json
validation_status: valid
hash-256: file,invalid.sha256;
HTTP 400
[Asserts]
body contains "Hash validation failed"

POST http://localhost:8080/api/v1/import
[MultipartFormData]
advisory: file,valid.json; application/json
validation_status: valid
hash-512: file,invalid.sha256;
HTTP 400
[Asserts]
body contains "Hash validation failed"

POST http://localhost:8080/api/v1/import
[MultipartFormData]
advisory: file,valid.json; application/json
validation_status: invalid
HTTP 201

POST http://localhost:8080/api/v1/import
[MultipartFormData]
advisory: file,valid.json; application/json
validation_status: not_validated
HTTP 201

POST http://localhost:8080/api/v1/import
[MultipartFormData]
advisory: file,valid.json; application/json
validation_status: wrong_status
HTTP 422
[Asserts]
body contains "Invalid validation_status"

POST http://localhost:8080/api/v1/import
api_key: wrong-api-key
[MultipartFormData]
advisory: file,valid.json; application/json
validation_status: valid
HTTP 401
[Asserts]
body contains "Unauthorized"

POST http://localhost:8080/api/v1/import
api_key: secret-api-key
[MultipartFormData]
advisory: file,valid.json; application/json
validation_status: valid
HTTP 201

POST http://localhost:8080/api/v1/import
[MultipartFormData]
advisory: file,valid.json; application/json
validation_status: valid
hash-256: file,valid.sha256;
HTTP 201

POST http://localhost:8080/api/v1/import
[MultipartFormData]
advisory: file,valid.json; application/json
validation_status: valid
hash-512: file,valid.sha512;
HTTP 201

POST http://localhost:8080/api/v1/import
[MultipartFormData]
advisory: file,valid.json; application/json
validation_status: valid
hash-256: file,valid.sha256;
hash-512: file,valid.sha512;
HTTP 201

