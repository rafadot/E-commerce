CREATE TABLE IF NOT EXISTS brand(
    id UUID primary key,
    name VARCHAR
);

ALTER TABLE public.account
ADD COLUMN brand_id UUID;

ALTER TABLE public.account
ADD CONSTRAINT fk_brand_id
FOREIGN KEY (brand_id)
REFERENCES brand(id);

ALTER TABLE public.account
ADD CONSTRAINT uk_brand_id
UNIQUE (brand_id);