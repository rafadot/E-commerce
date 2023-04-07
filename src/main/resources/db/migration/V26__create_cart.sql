CREATE TABLE IF NOT EXISTS products_cart(
    id UUID PRIMARY KEY,
    account_id UUID,
    product_id UUID,
    amount INT
);