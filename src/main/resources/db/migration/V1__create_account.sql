CREATE TABLE IF NOT EXISTS account(
    id UUID PRIMARY KEY,
    full_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255)
)