ALTER TABLE offer
ADD COLUMN offer_price_real DECIMAL(20,2);

ALTER TABLE offer
ADD COLUMN offer_price_dollar DECIMAL(20,2);

ALTER TABLE offer
ADD COLUMN expiration TIMESTAMP;