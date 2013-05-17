-- Kargu
-- SQL database scheme model table
-- For the Page in a Document

BEGIN TRANSACTION;

CREATE TABLE PageName(
  ID text PRIMARY KEY NOT NULL,
  Nama text NOT NULL,
  Jenis text,
  Jumlah integer,
  Watt integer,
  Jam integer
);

COMMIT;

