DO
$do$
BEGIN
   IF EXISTS (SELECT * FROM pg_catalog.pg_tables WHERE schemaname='public'
                and tablename='users') THEN
        ALTER TABLE users
        ADD CONSTRAINT "FK_user_address" FOREIGN KEY (id_address) REFERENCES addresses(id)
        ON DELETE SET NULL;
   END IF;
END
$do$