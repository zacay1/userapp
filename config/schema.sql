CREATE TABLE app_user (
	id uuid NOT NULL,
	active bool NULL,
	created timestamp NULL,
	email varchar(255) NULL,
	last_login timestamp NULL,
	modified timestamp NULL,
	"name" varchar(255) NULL,
	"password" varchar(255) NULL,
	"token" varchar(255) NULL,
	CONSTRAINT app_user_pkey PRIMARY KEY (id),
	CONSTRAINT user_email_unique_constraints UNIQUE (email)
);

CREATE TABLE app_phone (
	id int8 NOT NULL,
	citycode varchar(255) NULL,
	contrycode varchar(255) NULL,
	"number" varchar(255) NULL,
	user_id uuid NOT NULL,
	CONSTRAINT app_phone_pkey PRIMARY KEY (id),
	CONSTRAINT fk4eqmqwrlvs7oqeaqpb604upx5 FOREIGN KEY (user_id) REFERENCES app_user(id)
);