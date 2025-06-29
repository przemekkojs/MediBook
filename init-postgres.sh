#!/bin/bash
set -e

# Copy and fix permissions
cp /ssl/domain.crt /var/lib/postgresql/postgresql.crt
cp /ssl/domain.key /var/lib/postgresql/postgresql.key
cp /ssl/rootCA.crt /var/lib/postgresql/root.crt

# Set ownership to postgres user
chown postgres:postgres /var/lib/postgresql/postgresql.key /var/lib/postgresql/postgresql.crt /var/lib/postgresql/root.crt

# Set correct permissions
chmod 600 /var/lib/postgresql/postgresql.key
chmod 644 /var/lib/postgresql/postgresql.crt /var/lib/postgresql/root.crt

echo "Hello from init-postgres.sh"

# Start Postgres with SSL enabled
exec docker-entrypoint.sh postgres \
  -c ssl=on \
  -c ssl_cert_file=/var/lib/postgresql/postgresql.crt \
  -c ssl_key_file=/var/lib/postgresql/postgresql.key \
  -c ssl_ca_file=/var/lib/postgresql/root.crt