--liquibase formatted sql
--changeset hoai:AUTH-SERVER-1
CREATE TABLE if not exists oauth2_registered_client
(
    id                            varchar(100)                        NOT NULL,
    client_id                     varchar(100)                        NOT NULL,
    client_id_issued_at           timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL,
    client_secret                 varchar(200)                        NULL,
    client_secret_expires_at      timestamp                           NULL,
    client_name                   varchar(200)                        NOT NULL,
    client_authentication_methods varchar(1000)                       NOT NULL,
    authorization_grant_types     varchar(1000)                       NOT NULL,
    redirect_uris                 varchar(1000)                       NULL,
    scopes                        varchar(1000)                       NOT NULL,
    client_settings               varchar(2000)                       NOT NULL,
    token_settings                varchar(2000)                       NOT NULL,
    post_logout_redirect_uris     varchar(200)                        NULL,
    PRIMARY KEY (id)
);

INSERT INTO public.oauth2_registered_client (id,client_id,client_id_issued_at,client_secret,client_secret_expires_at,client_name,client_authentication_methods,authorization_grant_types,redirect_uris,scopes,client_settings,token_settings) VALUES
	 ('add17e76-548f-4b59-a575-93875eeba478','clientA','2023-02-07 21:04:31.106429','$2a$12$lfzKdGF/vCM8Fyin6J/6nu1AiHBwINQ8fIbm6n5yZ61mngEquOwwG',NULL,'add17e76-548f-4b59-a575-93875eeba478','client_secret_basic,client_secret_post','refresh_token,client_credentials,authorization_code','http://127.0.0.1:9000/authorized','openid,profile,read,write','{"@class":"java.util.Collections$UnmodifiableMap","settings.client.require-proof-key":false,"settings.client.require-authorization-consent":true}','{"@class":"java.util.Collections$UnmodifiableMap","settings.token.reuse-refresh-tokens":true,"settings.token.id-token-signature-algorithm":["org.springframework.security.oauth2.jose.jws.SignatureAlgorithm","RS256"],"settings.token.authorization-code-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-time-to-live":["java.time.Duration",300.000000000],"settings.token.access-token-format":{"@class":"org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat","value":"self-contained"},"settings.token.refresh-token-time-to-live":["java.time.Duration",3600.000000000]}');

CREATE TABLE if not exists oauth2_authorization (
    id varchar(100) NOT NULL,
    registered_client_id varchar(100) NOT NULL,
    principal_name varchar(200) NOT NULL,
    authorization_grant_type varchar(100) NOT NULL,
    authorized_scopes varchar(1000) DEFAULT NULL,
    attributes TEXT DEFAULT NULL,
    state varchar(500) DEFAULT NULL,
    authorization_code_value TEXT DEFAULT NULL,
    authorization_code_issued_at timestamp DEFAULT NULL,
    authorization_code_expires_at timestamp DEFAULT NULL,
    authorization_code_metadata TEXT DEFAULT NULL,
    access_token_value TEXT DEFAULT NULL,
    access_token_issued_at timestamp DEFAULT NULL,
    access_token_expires_at timestamp DEFAULT NULL,
    access_token_metadata TEXT DEFAULT NULL,
    access_token_type varchar(100) DEFAULT NULL,
    access_token_scopes varchar(1000) DEFAULT NULL,
    oidc_id_token_value TEXT DEFAULT NULL,
    oidc_id_token_issued_at timestamp DEFAULT NULL,
    oidc_id_token_expires_at timestamp DEFAULT NULL,
    oidc_id_token_metadata TEXT DEFAULT NULL,
    refresh_token_value TEXT DEFAULT NULL,
    refresh_token_issued_at timestamp DEFAULT NULL,
    refresh_token_expires_at timestamp DEFAULT NULL,
    refresh_token_metadata TEXT DEFAULT NULL,
    user_code_value TEXT DEFAULT NULL,
    user_code_issued_at timestamp DEFAULT NULL,
    user_code_expires_at timestamp DEFAULT NULL,
    user_code_metadata TEXT DEFAULT NULL,
    device_code_value TEXT DEFAULT NULL,
    device_code_issued_at timestamp DEFAULT NULL,
    device_code_expires_at timestamp DEFAULT NULL,
    device_code_metadata TEXT DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE if not exists public.t_user (
    id bigint NOT NULL,
    created_by character varying(50) NOT NULL,
    created_date timestamp without time zone,
    last_modified_by character varying(50),
    last_modified_date timestamp without time zone,
    version bigint,
    email character varying(255),
    is_enabled boolean,
    first_name character varying(255),
    last_name character varying(255),
    pwd_reset_date timestamp without time zone,
    password_hash character varying(255),
    username character varying(255),
    avatar bytea,
    date_of_birth timestamp without time zone,
    auth_provider character varying(255)
);

CREATE SEQUENCE public.t_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.t_user_id_seq OWNED BY public.t_user.id;

ALTER TABLE ONLY public.t_user ALTER COLUMN id SET DEFAULT nextval('public.t_user_id_seq'::regclass);

SELECT pg_catalog.setval('public.t_user_id_seq', 1, false);

ALTER TABLE ONLY public.t_user
    ADD CONSTRAINT t_user_pkey PRIMARY KEY (id);

INSERT INTO public.t_user(USERNAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD_HASH, IS_ENABLED, PWD_RESET_DATE, CREATED_BY, VERSION, AUTH_PROVIDER, avatar, date_of_birth)
	VALUES('hoai', 'Hoai', 'le', 'hoai@test.com', '$2a$12$lfzKdGF/vCM8Fyin6J/6nu1AiHBwINQ8fIbm6n5yZ61mngEquOwwG', true, '2010-01-01', 'me', 0, 'LOCAL', '', '2010-01-01');

CREATE TABLE public.role (
    role_id integer NOT NULL,
    name character varying(255),
    description character varying(512)
);

CREATE SEQUENCE public.role_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.role_role_id_seq OWNED BY public.role.role_id;
ALTER TABLE ONLY public.role ALTER COLUMN role_id SET DEFAULT nextval('public.role_role_id_seq'::regclass);
SELECT pg_catalog.setval('public.role_role_id_seq', 1, false);
ALTER TABLE ONLY public.role
    ADD CONSTRAINT role_id_pkey PRIMARY KEY (role_id);

INSERT INTO public.role(name, description)
	VALUES('Administrator', 'Administrator');
INSERT INTO public.role(name, description)
	VALUES('User', 'User');

CREATE TABLE public.user_role (
    role_id integer,
    user_id integer
);

ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fk_user__reference_user FOREIGN KEY (user_id) REFERENCES public.t_user(id) ON UPDATE RESTRICT ON DELETE RESTRICT;
ALTER TABLE ONLY public.user_role
    ADD CONSTRAINT fk_user__reference_role FOREIGN KEY (role_id) REFERENCES public.role(role_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

INSERT INTO public.user_role(role_id, user_id)
	VALUES(1, 1);

create table if not exists oauth2_authorization_consent
(
    registered_client_id varchar(100)  not null,
    principal_name       varchar(200)  not null,
    authorities          varchar(1000) not null,
    primary key (registered_client_id, principal_name)
);

