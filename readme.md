# Client Application

## Before running
### TLS security
> Ensure to get the certificate file generated by the server and store it into the root of the project.
> The certificate needs to be registered into the keystore file.
> ```shell
> keytool -import -alias [CERTIFICATE_ALIAS] -file [PATH_TO_CERTIFICATE.CER] -keystore [PATH_TO_KEYSTORE]
> ```
> The keystore must be moved to the resources' folder.

### Properties
> There must be a .properties file in resources were are stored the following content:
```shell
SOCKET_PORT=
SERVER_HOST=
KEYSTORE_PATH=
KEYSTORE_PASS=
```
