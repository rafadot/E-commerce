CREATE TABLE IF NOT EXISTS password_recovery(
    id UUID PRIMARY KEY,
    code INTEGER,
    verified BOOLEAN
)
