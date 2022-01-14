# XMUX Forum

## API

gRPC API specifications can be found in `src/main/proto`.

## Environment variables

- `PROFILE`: Default is `dev`, which uses a dummy authentication filter. Use `prod` to connect to the real auth service.
- `SERVICE_ADDR`: The address of other gRPC services. 
- `PORT`
- `DB_USER`
- `DB_PASSWD`
- `DB_URL`
- `DB_DATABASE`