CREATE TABLE IF NOT EXISTS sale(
    id UUID PRIMARY KEY,
    status BOOLEAN,
    percentage INTEGER
);

ALTER TABLE public.product
ADD COLUMN sale_id UUID;

ALTER TABLE public.product
ADD CONSTRAINT fk_sale_id
FOREIGN KEY(sale_id) REFERENCES sale(id);

ALTER TABLE public.product
ADD CONSTRAINT uk_sale_id UNIQUE (sale_id);