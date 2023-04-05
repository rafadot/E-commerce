CREATE TABLE IF NOT EXISTS voucher(
    id UUID PRIMARY KEY,
    name VARCHAR,
    date_expiration TIMESTAMP,
    discount_percentage INTEGER,
    creator_id UUID
);