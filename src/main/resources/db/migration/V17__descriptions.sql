ALTER TABLE public.product
DROP COLUMN description;

ALTER TABLE public.product
ADD COLUMN description_br TEXT;

ALTER TABLE public.product
ADD COLUMN description_en TEXT;