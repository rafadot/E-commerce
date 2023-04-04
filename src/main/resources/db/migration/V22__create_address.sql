CREATE TABLE IF NOT EXISTS address(
    id UUID PRIMARY KEY,
    account_id UUID,
    country VARCHAR,
    first_name VARCHAR,
    last_name VARCHAR,
    street VARCHAR,
    street_optional VARCHAR,
    city VARCHAR,
    state VARCHAR,
    zip_code VARCHAR,
    phone VARCHAR,
    FOREIGN KEY (account_id) REFERENCES account(id)
)