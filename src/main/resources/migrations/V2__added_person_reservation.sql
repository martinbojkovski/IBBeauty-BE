-- Add the column
ALTER TABLE reservation
    ADD COLUMN person VARCHAR(255);

-- Update all existing rows to use 'Ivana'
UPDATE reservation
SET person = 'IVANA';

-- (Optional) Add NOT NULL constraint if it should never be null
ALTER TABLE reservation
    ALTER COLUMN person SET NOT NULL;
