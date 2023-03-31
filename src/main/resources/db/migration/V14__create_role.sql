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
);

INSERT INTO role (id, name)
VALUES ('111e4567-e89b-12d3-a456-426655440000', 'ADMIN');

INSERT INTO role (id, name)
VALUES ('222e4567-e89b-12d3-a456-426655440000', 'USER');

INSERT INTO role (id, name)
VALUES ('333e4567-e89b-12d3-a456-426655440000', 'BRAND');