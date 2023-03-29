CREATE TABLE IF NOT EXISTS product(
    id UUID primary key,
    name VARCHAR,
    profile BYTEA,
    color varchar,
    price_real DECIMAL(20,2),
    price_dollar DECIMAL(20,2),
    description TEXT
)