--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: t_adresse_client; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE t_adresse_client (
    id_adresse integer NOT NULL,
    nume_rue integer,
    nom_rue character varying(50),
    nom_ville character varying(50),
    code_postal character varying(25)
);


--
-- Name: t_adresse_client_id_adresse_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE t_adresse_client_id_adresse_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: t_adresse_client_id_adresse_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE t_adresse_client_id_adresse_seq OWNED BY t_adresse_client.id_adresse;


--
-- Name: t_adresse_agence; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE t_adresse_agence (
    id_adresse integer NOT NULL,
    nume_rue integer,
    nom_rue character varying(50),
    nom_ville character varying(50),
    code_postal character varying(25),
    tel_acceuil character varying(25),
    fax_agence character varying(25)
);


--
-- Name: t_adresse_agence_id_adresse_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE t_adresse_agence_id_adresse_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: t_adresse_agence_id_adresse_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE t_adresse_agence_id_adresse_seq OWNED BY t_adresse_agence.id_adresse;


--
-- Name: t_agence; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE t_agence (
    id_agence integer NOT NULL,
    nom_agence character varying(25),
    id_adresse integer NOT NULL
);


--
-- Name: t_agence_id_agence_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE t_agence_id_agence_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: t_agence_id_agence_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE t_agence_id_agence_seq OWNED BY t_agence.id_agence;


--
-- Name: t_client; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE t_client (
    id_client integer NOT NULL,
    nom_client character varying(25),
    prenom_client character varying(25),
    mail_client character varying(50),
    salaire_client double precision,
    mdp_client character varying(25),
    id_agence integer,
    id_adresse integer NOT NULL
);


--
-- Name: t_client_id_client_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE t_client_id_client_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: t_client_id_client_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE t_client_id_client_seq OWNED BY t_client.id_client;


--
-- Name: t_conseiller; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE t_conseiller (
    id_conseiller integer NOT NULL,
    nom_conseiller character varying(25),
    prenom_conseiller character varying(25),
    directeur_agence boolean,
    id_agence integer
);


--
-- Name: t_conseiller_id_conseiller_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE t_conseiller_id_conseiller_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: t_conseiller_id_conseiller_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE t_conseiller_id_conseiller_seq OWNED BY t_conseiller.id_conseiller;


--
-- Name: t_detail_pret; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE t_detail_pret (
    id_detail integer NOT NULL,
    num_annee_detail integer NOT NULL,
    taux_detail double precision,
    mensualite_detail double precision,
    id_pret integer
);


--
-- Name: t_detail_pret_id_detail_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE t_detail_pret_id_detail_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: t_detail_pret_id_detail_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE t_detail_pret_id_detail_seq OWNED BY t_detail_pret.id_detail;


--
-- Name: t_simu_pret; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE t_simu_pret (
    id_pret integer NOT NULL,
    libelle_pret character varying(25),
    montant_pret double precision,
    duree_pret integer,
    type_duree_pret character varying(1),
    type_taux_pret character varying(1),
    id_type_pret integer NOT NULL,
    id_client integer
);


--
-- Name: t_simu_pret_id_pret_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE t_simu_pret_id_pret_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: t_simu_pret_id_pret_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE t_simu_pret_id_pret_seq OWNED BY t_simu_pret.id_pret;


--
-- Name: t_type_pret; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE t_type_pret (
    id_type_pret integer NOT NULL,
    libelle_type_pret character varying(25),
    taux_type_pret double precision
);


--
-- Name: t_type_pret_id_type_pret_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE t_type_pret_id_type_pret_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: t_type_pret_id_type_pret_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE t_type_pret_id_type_pret_seq OWNED BY t_type_pret.id_type_pret;


--
-- Name: id_adresse; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_adresse_agence ALTER COLUMN id_adresse SET DEFAULT nextval('t_adresse_agence_id_adresse_seq'::regclass);


--
-- Name: id_adresse; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_adresse_client ALTER COLUMN id_adresse SET DEFAULT nextval('t_adresse_client_id_adresse_seq'::regclass);


--
-- Name: id_agence; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_agence ALTER COLUMN id_agence SET DEFAULT nextval('t_agence_id_agence_seq'::regclass);


--
-- Name: id_client; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_client ALTER COLUMN id_client SET DEFAULT nextval('t_client_id_client_seq'::regclass);


--
-- Name: id_conseiller; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_conseiller ALTER COLUMN id_conseiller SET DEFAULT nextval('t_conseiller_id_conseiller_seq'::regclass);


--
-- Name: id_detail; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_detail_pret ALTER COLUMN id_detail SET DEFAULT nextval('t_detail_pret_id_detail_seq'::regclass);


--
-- Name: id_pret; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_simu_pret ALTER COLUMN id_pret SET DEFAULT nextval('t_simu_pret_id_pret_seq'::regclass);


--
-- Name: id_type_pret; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_type_pret ALTER COLUMN id_type_pret SET DEFAULT nextval('t_type_pret_id_type_pret_seq'::regclass);


--
-- Name: t_adresse_client_id_adresse_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('t_adresse_client_id_adresse_seq', 19, true);


--
-- Data for Name: t_adresse_agence; Type: TABLE DATA; Schema: public; Owner: -
--

COPY t_adresse_agence (id_adresse, nume_rue, nom_rue, nom_ville, code_postal, tel_acceuil, fax_agence) FROM stdin;
1	1	LabanqueRue	BOULOGNE	78180	\N	\N
2	15	rue Raymond Lapchin	GOUSSAINVILLE	95190	07 81 28 91 73	07 81 28 91 73
3	2	allée Jean de Joinville	ACHERES	78260	06 47 69 00 91	06 47 69 00 91
4	51	rue du Coq	VERNON	27200	06 87 95 38 00	06 87 95 38 00
5	54	rue de Juvisy	ATHIS MONS	91200	07 60 48 25 66	07 60 48 25 66
6	26	rue chateaubriand	Montigny le bretonneux	78180	06 29 51 68 98	06 29 51 68 98
7	77	avenue Yves Cariou	Le Blanc Mesnil	93150	06 69 97 96 90	06 69 97 96 90
8	33	rue des pièces de lugny	Moissy Cramayel	77550	06 06 87 90 23	06 06 87 90 23
9	55	ue Georges Médéric	Maisons-Alfort	94700	06 09 86 66 72	06 09 86 66 72
10	22	place Jules Vallès	EVRY	91000	06 79 48 16 22	06 79 48 16 22
11	6	rue de Nice	ALFORTVILLE	94140	06 49 32 87 07	06 49 32 87 07
12	7	chemin de laubépine	NOISY LE GRAND	93160	06 79 09 53 59	06 79 09 53 59
13	6	allee jean poncelet	Creteil	94000	07 81 05 60 67	07 81 05 60 67
14	20	rue des Colnottes	YERRES	91330	06 85 85 09 79	06 85 85 09 79
15	1	allée du murier	Orly	94310	06 63 59 50 44	06 63 59 50 44
16	85	rue du Professeur Einstein	Fresnes	94260	06 95 88 05 44	06 95 88 05 44
17	33	route de corbeil	Montgeron	91230	06 59 61 10 68	06 59 61 10 68
18	13	rue des frères reclus	CHOISY LE ROI	94600	06 03 18 11 08	06 03 18 11 08
19	35	rue Michelet	Valenton	94460	06 77 87 18 86	06 77 87 18 86
\.


--
-- Name: t_adresse_agence_id_adresse_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('t_adresse_agence_id_adresse_seq', 19, true);


--
-- Data for Name: t_adresse_client; Type: TABLE DATA; Schema: public; Owner: -
--

COPY t_adresse_client (id_adresse, nume_rue, nom_rue, nom_ville, code_postal) FROM stdin;
1	26	CHateaubriand	Montigny 	78180
2	15	rue Raymond Lapchin	GOUSSAINVILLE	95190
3	2	allée Jean de Joinville	ACHERES	78260
4	51	rue du Coq	VERNON	27200
5	54	rue de Juvisy	ATHIS MONS	91200
6	26	rue chateaubriand	Montigny le bretonneux	78180
7	77	avenue Yves Cariou	Le Blanc Mesnil	93150
8	33	rue des pièces de lugny	Moissy Cramayel	77550
9	55	ue Georges Médéric	Maisons-Alfort	94700
10	22	place Jules Vallès	EVRY	91000
11	6	rue de Nice	ALFORTVILLE	94140
12	7	chemin de laubépine	NOISY LE GRAND	93160
13	6	allee jean poncelet	Creteil	94000
14	20	rue des Colnottes	YERRES	91330
15	1	allée du murier	Orly	94310
16	33	route de corbeil	Montgeron	91230
17	13	rue des frères reclus	CHOISY LE ROI	94600
18	35	rue Michelet	Valenton	94460
19	45	rue machin	VERSAILLES	78000
\.


--
-- Data for Name: t_agence; Type: TABLE DATA; Schema: public; Owner: -
--

COPY t_agence (id_agence, nom_agence, id_adresse) FROM stdin;
2	AgenceBoulogne	1
3	GOUSSAINVILLE	1
4	ACHERES	2
5	VERNON	3
6	ATHIS MONS	4
7	Montigny le bretonneux	5
8	Le Blanc Mesnil	6
9	Moissy Cramayel	7
10	Maisons-Alfort	8
11	EVRY	9
12	ALFORTVILLE	10
13	NOISY LE GRAND	11
14	Creteil	12
15	YERRES	13
16	Orly	14
17	Fresnes	15
18	Montgeron	16
19	CHOISY LE ROI	17
20	Valenton	18
\.


--
-- Name: t_agence_id_agence_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('t_agence_id_agence_seq', 20, true);


--
-- Data for Name: t_client; Type: TABLE DATA; Schema: public; Owner: -
--

COPY t_client (id_client, nom_client, prenom_client, mail_client, salaire_client, mdp_client, id_agence, id_adresse) FROM stdin;
39	ABID BUTT	Usman	usman.abid.butt@hotmail.com	\N	\N	2	1
40	ALBERCA	Romain	romain@alberca.fr	\N	\N	2	1
41	ALLOUCHE	Sarah	allouchesarah27@hotmail.fr	\N	\N	2	1
42	BALDE BABA	Abdoulaye	vybzbalde@gmail.com	\N	\N	2	1
43	BARRY	Alpha Ibrahima	alpha.ibra.barry@outlook.com	\N	\N	2	1
45	BOUZID	Linda	lnd.bouzid@gmail.com	\N	\N	2	1
46	CLOCHARD	David	david.clochard77@gmail.com	\N	\N	2	1
47	EDERY	Ruben	ederyruben@yahoo.fr	\N	\N	2	1
48	ELIN	Gregory	gregory.elin@gmail.com	\N	\N	2	1
49	GENGOUL	Alexandre	alexandre.gengoul@gmail.com	\N	\N	2	1
50	GOUGAM	Christophe	chris.g-93@hotmail.fr	\N	\N	2	1
51	HAIDARA	Mariam	mariamhaidara4@gmail.com	\N	\N	2	1
52	JARNOUEN DE VILLARTAY	Ulysse	u.devillartay@gmail.com	\N	\N	2	1
53	KERRAD	Mamed	mamed.kerrad@gmail.com	\N	\N	2	1
54	KICH	Tarik	tarik.kich@hotmail.fr	\N	\N	2	1
55	KNAFO	Aurélie	knf.aurelie@gmail.com	\N	\N	2	1
56	LELLOUCHE	Aaron	aaronlellouche@hotmail.fr	\N	\N	2	1
57	PEYTHIEU	Jérémy	peythieu.jeremy@gmail.com	\N	\N	2	1
44	BOURGEOIS	Thibault	thibault.bourgeois@live.fr	\N	\N	5	1
\.


--
-- Name: t_client_id_client_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('t_client_id_client_seq', 57, true);


--
-- Data for Name: t_conseiller; Type: TABLE DATA; Schema: public; Owner: -
--

COPY t_conseiller (id_conseiller, nom_conseiller, prenom_conseiller, directeur_agence, id_agence) FROM stdin;
3	ALLOUCHE	Sarah	f	3
4	BALDE BABA	Abdoulaye	f	4
6	BOUZID	Linda	f	6
7	CLOCHARD	David	f	7
8	EDERY	Ruben	f	8
9	ELIN	Gregory	f	9
10	GENGOUL	Alexandre	f	10
11	GOUGAM	Christophe	f	11
12	HAIDARA	Mariam	f	12
13	JARNOUEN DE VILLARTAY	Ulysse	f	13
14	KERRAD	Mamed	f	14
15	KICH	Tarik	f	15
16	KNAFO	Aurélie	f	16
17	LELLOUCHE	Aaron	f	17
18	PEYTHIEU	Jérémy	f	18
5	BOURGEOIS	Thibault	t	5
2	ALBERCA	Romain	f	2
\.


--
-- Name: t_conseiller_id_conseiller_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('t_conseiller_id_conseiller_seq', 18, true);


--
-- Data for Name: t_detail_pret; Type: TABLE DATA; Schema: public; Owner: -
--

COPY t_detail_pret (id_detail, num_annee_detail, taux_detail, mensualite_detail, id_pret) FROM stdin;
\.


--
-- Name: t_detail_pret_id_detail_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('t_detail_pret_id_detail_seq', 1, false);


--
-- Data for Name: t_simu_pret; Type: TABLE DATA; Schema: public; Owner: -
--

COPY t_simu_pret (id_pret, libelle_pret, montant_pret, duree_pret, type_duree_pret, type_taux_pret, id_type_pret, id_client) FROM stdin;
\.


--
-- Name: t_simu_pret_id_pret_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('t_simu_pret_id_pret_seq', 1, false);


--
-- Data for Name: t_type_pret; Type: TABLE DATA; Schema: public; Owner: -
--

COPY t_type_pret (id_type_pret, libelle_type_pret, taux_type_pret) FROM stdin;
\.


--
-- Name: t_type_pret_id_type_pret_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('t_type_pret_id_type_pret_seq', 1, false);


--
-- Name: prk_constraint_t_adresse_client; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY t_adresse_client
    ADD CONSTRAINT prk_constraint_t_adresse_client PRIMARY KEY (id_adresse);


--
-- Name: prk_constraint_t_adresse_agence; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY t_adresse_agence
    ADD CONSTRAINT prk_constraint_t_adresse_agence PRIMARY KEY (id_adresse);


--
-- Name: prk_constraint_t_agence; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY t_agence
    ADD CONSTRAINT prk_constraint_t_agence PRIMARY KEY (id_agence);


--
-- Name: prk_constraint_t_client; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY t_client
    ADD CONSTRAINT prk_constraint_t_client PRIMARY KEY (id_client);


--
-- Name: prk_constraint_t_conseiller; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY t_conseiller
    ADD CONSTRAINT prk_constraint_t_conseiller PRIMARY KEY (id_conseiller);


--
-- Name: prk_constraint_t_detail_pret; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY t_detail_pret
    ADD CONSTRAINT prk_constraint_t_detail_pret PRIMARY KEY (id_detail);


--
-- Name: prk_constraint_t_simu_pret; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY t_simu_pret
    ADD CONSTRAINT prk_constraint_t_simu_pret PRIMARY KEY (id_pret);


--
-- Name: prk_constraint_t_type_pret; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY t_type_pret
    ADD CONSTRAINT prk_constraint_t_type_pret PRIMARY KEY (id_type_pret);


--
-- Name: t_detail_pret_num_annee_detail_key; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY t_detail_pret
    ADD CONSTRAINT t_detail_pret_num_annee_detail_key UNIQUE (num_annee_detail);


--
-- Name: fk_t_agence_id_adresse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_agence
    ADD CONSTRAINT fk_t_agence_id_adresse FOREIGN KEY (id_adresse) REFERENCES t_adresse_agence(id_adresse);


--
-- Name: fk_t_client_id_adresse; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_client
    ADD CONSTRAINT fk_t_client_id_adresse FOREIGN KEY (id_adresse) REFERENCES t_adresse_client(id_adresse);


--
-- Name: fk_t_client_id_agence; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_client
    ADD CONSTRAINT fk_t_client_id_agence FOREIGN KEY (id_agence) REFERENCES t_agence(id_agence);


--
-- Name: fk_t_conseiller_id_agence; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_conseiller
    ADD CONSTRAINT fk_t_conseiller_id_agence FOREIGN KEY (id_agence) REFERENCES t_agence(id_agence);


--
-- Name: fk_t_detail_pret_id_pret; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_detail_pret
    ADD CONSTRAINT fk_t_detail_pret_id_pret FOREIGN KEY (id_pret) REFERENCES t_simu_pret(id_pret);


--
-- Name: fk_t_simu_pret_id_client; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_simu_pret
    ADD CONSTRAINT fk_t_simu_pret_id_client FOREIGN KEY (id_client) REFERENCES t_client(id_client);


--
-- Name: fk_t_simu_pret_id_type_pret; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY t_simu_pret
    ADD CONSTRAINT fk_t_simu_pret_id_type_pret FOREIGN KEY (id_type_pret) REFERENCES t_type_pret(id_type_pret);


--
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

