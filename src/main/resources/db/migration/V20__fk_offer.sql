ALTER TABLE product
DROP COLUMN sale_id;

ALTER TABLE product
ADD COLUMN offer_id UUID;

ALTER TABLE product
ADD CONSTRAINT fk_offer_id
FOREIGN KEY (offer_id)
REFERENCES offer(id);

ALTER TABLE product
ADD CONSTRAINT uk_offer_id
UNIQUE (offer_id);