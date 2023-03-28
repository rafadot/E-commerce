ALTER TABLE account ADD COLUMN password_recovery_id UUID;

ALTER TABLE account ADD CONSTRAINT fk_password_recovery_id FOREIGN KEY (password_recovery_id) REFERENCES password_recovery(id);