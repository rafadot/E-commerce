ALTER TABLE product
ADD COLUMN brand_id UUID;

ALTER TABLE product
ADD CONSTRAINT fk_brand_id
FOREIGN KEY (brand_id)
REFERENCES brand(id);
