ALTER TABLE public.voucher
ADD COLUMN discount_value_dollar DECIMAL(20,2);

ALTER TABLE public.voucher
ADD COLUMN discount_value_real DECIMAL(20,2);

ALTER TABLE public.voucher
ADD COLUMN type VARCHAR;