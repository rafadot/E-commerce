ALTER TABLE public.account
DROP COLUMN ROLE;

CREATE TABLE IF NOT EXISTS role(
    id UUID PRIMARY KEY,
    name VARCHAR
);

CREATE TABLE IF NOT EXISTS account_role(
    account_id UUID REFERENCES account(id),
    role_id UUID REFERENCES role(id),
    PRIMARY KEY(account_id, role_id)
)