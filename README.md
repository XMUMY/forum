# XMUX Forum

## API

gRPC API specifications can be found in `src/main/proto`.

## Features Roadmap

- [ ] Authentication (JWT)
- [ ] Moderation System
- [ ] Thread Image Support
- [ ] Per-course Forum

## Environment variables

- `PROFILE`: Default is `prod`, which connects to the real auth service. Use `dev` for development, which uses a dummy
  authentication filter.
- `AUTH_ENDPOINT`: The address of Auth service.
- `PORT`: Default to 9000.
- `DB_USER`
- `DB_PASS`
- `DB_ADDR`
- `DB_PORT`: Default to 5432.
- `DB_NAME`: Database name. 
- `SENTRY_DSN`