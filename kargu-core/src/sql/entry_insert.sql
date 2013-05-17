-- Kargu
-- SQL data insertion scheme
-- For the Entries in a Page

BEGIN TRANSACTION;

INSERT INTO PageName(
  ID,
  Nama,
  Jenis,
  Jumlah,
  Watt,
  Jam
) VALUES(
  'IDX',
  'Elektronik X',
  'Jenis Elektronik',
  1,
  100,
  24
);

COMMIT;

