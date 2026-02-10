--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2026-02-10 11:11:52

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 73427)
-- Name: category; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.category (
    id bigint NOT NULL,
    description character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);


--
-- TOC entry 223 (class 1259 OID 73473)
-- Name: category_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.category_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 218 (class 1259 OID 73434)
-- Name: credentials; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.credentials (
    id bigint NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    username character varying(255) NOT NULL,
    user_id bigint
);


--
-- TOC entry 224 (class 1259 OID 73474)
-- Name: credentials_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.credentials_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 219 (class 1259 OID 73441)
-- Name: recipe; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.recipe (
    id bigint NOT NULL,
    creation_date date,
    difficulty character varying(255) NOT NULL,
    instructions character varying(10000) NOT NULL,
    is_active boolean,
    prep_time integer NOT NULL,
    short_description character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    url_image character varying(255),
    author_id bigint,
    category_id bigint
);


--
-- TOC entry 220 (class 1259 OID 73448)
-- Name: recipe_ingredients; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.recipe_ingredients (
    recipe_id bigint NOT NULL,
    name character varying(255),
    quantity real,
    unit character varying(255)
);


--
-- TOC entry 225 (class 1259 OID 73475)
-- Name: recipe_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.recipe_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 221 (class 1259 OID 73453)
-- Name: review; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.review (
    id bigint NOT NULL,
    creation_date date,
    rating integer NOT NULL,
    text character varying(5000) NOT NULL,
    author_id bigint,
    recipe_id bigint
);


--
-- TOC entry 226 (class 1259 OID 73476)
-- Name: review_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.review_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 222 (class 1259 OID 73458)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    is_banned boolean,
    last_name character varying(255) NOT NULL,
    url_image character varying(255)
);


--
-- TOC entry 227 (class 1259 OID 73477)
-- Name: users_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 4834 (class 0 OID 73427)
-- Dependencies: 217
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.category VALUES (2, 'Pasta fresca, risotti e sapori della tradizione.', 'Primi Piatti');
INSERT INTO public.category VALUES (3, 'Eccellenze di carne e pesce cotte con cura.', 'Secondi Piatti');
INSERT INTO public.category VALUES (4, 'Verdure di stagione e sfiziosità d''accompagnamento.', 'Contorni');
INSERT INTO public.category VALUES (5, 'Impasti a lunga maturazione e topping gourmet.', 'Pizze & Lievitati');
INSERT INTO public.category VALUES (6, 'Dolci tentazioni per concludere in bellezza.', 'Dessert');
INSERT INTO public.category VALUES (1, 'Piccoli morsi per stuzzicare l''appetito.', 'Antipasti');
INSERT INTO public.category VALUES (152, 'Categoria predefinita per ricette senza categoria specifica.', 'Altro');


--
-- TOC entry 4835 (class 0 OID 73434)
-- Dependencies: 218
-- Data for Name: credentials; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.credentials VALUES (1, '$2a$10$oIXi5pbscWxafS9s1dqthOQmmVItR1buuXaaJSSqP1WHQKSJokNai', 'DEFAULT', 'Davide Mandolesi', 1);
INSERT INTO public.credentials VALUES (3, '$2a$10$07uCuCambGWZOmvsGglbKOjncigNuvuE.hz/pDDNnCtrKrJ1UoJQ6', 'DEFAULT', 'a', 3);
INSERT INTO public.credentials VALUES (4, '$2a$10$CNnEgCbGZNx5E/i0ggIKh.nsqk7kq3R0vSE04mRbwPjTKk0XYQKka', 'DEFAULT', 'b', 4);
INSERT INTO public.credentials VALUES (2, '$2a$10$qg07xwTzL50dJ/MYwUYIbObb5u7YGc/M8Gwt9jzxEPQBoLHLxSBNu', 'ADMIN', 'Admin', 2);
INSERT INTO public.credentials VALUES (5, '$2a$10$D/InztkFKA9Ir40/Q34xg.713nVhiocoDXFNqkHar.Q40N7Ql5a4a', 'DEFAULT', 'Mario Rossi', 5);
INSERT INTO public.credentials VALUES (52, '$2a$10$YYbvkyW3ca4G5jFJM9qe0ey6c4Z703V5KnwDqKUT7DjuF/PKJrlK2', 'DEFAULT', 'Chef Antonio', 52);


--
-- TOC entry 4836 (class 0 OID 73441)
-- Dependencies: 219
-- Data for Name: recipe; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.recipe VALUES (2, '2026-01-25', 'Media', 'Prepara il caffè e lascialo raffreddare. In una ciotola, monta i tuorli con lo zucchero fino a ottenere un composto chiaro, poi incorpora il mascarpone delicatamente. Inzuppa velocemente i savoiardi nel caffè e disponili in una pirofila. Copri con uno strato di crema. Ripeti l''operazione per un secondo strato. Spolvera la superficie con il cacao amaro. Lascia riposare in frigorifero per almeno 2 ore prima di servire.', true, 30, 'Dolce al cucchiaio con caffè e mascarpone.', 'Tiramisù Classico', 'https://plus.unsplash.com/premium_photo-1695028378225-97fbe39df62a?q=80&w=870&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 1, 6);
INSERT INTO public.recipe VALUES (302, '2026-02-10', 'Facile', 'nessuno', false, 1, 'prima ricetta inattiva', 'Inattiva 1', 'https://img.freepik.com/free-photo/delicious-food-wooden-table_23-2148708281.jpg?semt=ais_hybrid&w=740&q=80', 52, 2);
INSERT INTO public.recipe VALUES (103, '2026-01-26', 'Facile', 'Preriscalda il forno a 180°C. Disponi i filetti di salmone su una teglia rivestita di carta forno. Spennella il pesce con olio d''oliva e condisci con sale, pepe e prezzemolo tritato. Taglia il limone a fettine sottili e posizionale sopra ogni filetto. Cuoci in forno per circa 15-20 minuti, finché il salmone risulta rosato e si sfalda facilmente con la forchetta. Servi caldo accompagnato da verdure o riso.', true, 5, 'Secondo piatto leggero, succoso e aromatico.', 'Salmone al Forno con Limone', 'https://plus.unsplash.com/premium_photo-1726863211360-c7aca3a39d9d?q=80&w=796&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 5, 3);
INSERT INTO public.recipe VALUES (303, '2026-02-10', 'Difficile', 'nessuno', false, 1, 'seconda ricetta inattiva', 'Inattiva 2', 'https://img.freepik.com/free-photo/delicious-food-wooden-table_23-2148708281.jpg?semt=ais_hybrid&w=740&q=80', 3, 5);
INSERT INTO public.recipe VALUES (1, '2026-01-26', 'Media', 'Cuoci gli spaghetti in acqua bollente salata. Nel frattempo, taglia il guanciale a listarelle e rosolalo in padella senza olio finché diventa croccante. In una ciotola, sbatti i tuorli con il pecorino e abbondante pepe nero. Scola la pasta al dente (conservando un po'' di acqua di cottura) e versala nella padella col guanciale. Spegni il fuoco. Aggiungi la crema di uova e un goccio d''acqua di cottura. Mescola velocemente per creare un''emulsione cremosa. Servi subito.', true, 20, 'Classico primo romano, cremoso e saporito.', 'Spaghetti alla Carbonara', 'https://images.unsplash.com/photo-1633337474564-1d9478ca4e2e?q=80&w=871&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 1, 2);
INSERT INTO public.recipe VALUES (152, '2026-01-26', 'Facile', 'Sbuccia le patate e tagliale a tocchetti regolari. Mettile in ammollo in acqua fredda per 10 minuti per perdere l''amido, poi asciugale molto bene. In una teglia, condisci le patate con olio generoso, sale, pepe, aglio in camicia e rosmarino. Mescola con le mani per ungere tutto. Cuoci in forno ventilato a 200°C per circa 40 minuti, girandole a metà cottura, finché non risultano dorate fuori e morbide dentro.', true, 50, 'Contorno dorato e profumato al rosmarino.', 'Patate al Forno Croccanti', 'https://plus.unsplash.com/premium_photo-1667233385009-d7accf020761?q=80&w=870&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 5, 4);
INSERT INTO public.recipe VALUES (153, '2026-01-29', 'Difficile', 'Sciogli il lievito nell''acqua a temperatura ambiente. In una ciotola unisci farina e acqua col lievito. Inizia a impastare, poi aggiungi sale e olio. Lavora per 10 minuti fino a ottenere un panetto liscio. Copri e lascia lievitare 3-4 ore (fino al raddoppio). Stendi l''impasto su una teglia unta. Condisci con la passata, sale e olio. Inforna al massimo (250°C) per 10 min sul fondo, poi aggiungi la mozzarella e sposta a metà altezza per altri 5 min.', true, 25, 'La regina delle pizze, soffice e fragrante.', 'Pizza Margherita', 'https://images.unsplash.com/photo-1622880833523-7cf1c0bd4296?q=80&w=870&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 52, 5);
INSERT INTO public.recipe VALUES (102, '2026-01-29', 'Facile', 'Lava e asciuga bene il basilico. Metti nel mixer (o mortaio) l''aglio e i pinoli e frulla brevemente. Aggiungi le foglie di basilico e un pizzico di sale grosso. Frulla a scatti per non scaldare le foglie. Unisci il parmigiano e il pecorino grattugiati. Infine, versa l''olio a filo continuando a mescolare fino a ottenere una crema verde brillante e omogenea. Utilizzalo subito per condire la pasta o conservalo coperto d''olio.', true, 15, 'Salsa fresca con basilico, pinoli e aglio.', 'Pesto alla Genovese', 'https://plus.unsplash.com/premium_photo-1702323867828-61c68cbe970e?q=80&w=1032&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 5, 152);


--
-- TOC entry 4837 (class 0 OID 73448)
-- Dependencies: 220
-- Data for Name: recipe_ingredients; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.recipe_ingredients VALUES (102, 'Basilico fresco', 50, '(g)');
INSERT INTO public.recipe_ingredients VALUES (102, 'Olio extravergine', 100, '(ml)');
INSERT INTO public.recipe_ingredients VALUES (102, 'Parmiggiano Reggiano', 70, '(g)');
INSERT INTO public.recipe_ingredients VALUES (102, 'Pecorino Sardo', 30, '(g)');
INSERT INTO public.recipe_ingredients VALUES (102, 'Pinoli', 15, '(g)');
INSERT INTO public.recipe_ingredients VALUES (102, 'Aglio', 2, '(pz)');
INSERT INTO public.recipe_ingredients VALUES (2, 'Savoiardi', 300, '(g)');
INSERT INTO public.recipe_ingredients VALUES (2, 'Mascarpone', 500, '(g)');
INSERT INTO public.recipe_ingredients VALUES (2, 'Uova', 4, '(pz)');
INSERT INTO public.recipe_ingredients VALUES (2, 'Zucchero semolato', 100, '(g)');
INSERT INTO public.recipe_ingredients VALUES (2, 'Caffè espresso', 300, '(ml)');
INSERT INTO public.recipe_ingredients VALUES (2, 'Cacao amaro', 20, '(g)');
INSERT INTO public.recipe_ingredients VALUES (103, 'Filetti di Salmone', 4, '(pz)');
INSERT INTO public.recipe_ingredients VALUES (103, 'Limone', 2, '(pz)');
INSERT INTO public.recipe_ingredients VALUES (1, 'Spaghetti', 320, '(g)');
INSERT INTO public.recipe_ingredients VALUES (1, 'Guanciale', 150, '(g)');
INSERT INTO public.recipe_ingredients VALUES (1, 'Tuorli d''uovo', 4, '(pz)');
INSERT INTO public.recipe_ingredients VALUES (1, 'Pecorino Romano', 50, '(g)');
INSERT INTO public.recipe_ingredients VALUES (1, 'Pepe Nero', 1, 'Cucchiai');
INSERT INTO public.recipe_ingredients VALUES (1, NULL, 0, NULL);
INSERT INTO public.recipe_ingredients VALUES (103, 'Olio Extravergine', 2, 'Cucchiai');
INSERT INTO public.recipe_ingredients VALUES (103, 'Prezzemolo', 1, '(pz)');
INSERT INTO public.recipe_ingredients VALUES (103, 'Sale', 1, 'Pizzichi');
INSERT INTO public.recipe_ingredients VALUES (103, 'Pepe', 1, 'Pizzichi');
INSERT INTO public.recipe_ingredients VALUES (103, NULL, 0, NULL);
INSERT INTO public.recipe_ingredients VALUES (152, 'Patate Gialle', 1000, '(g)');
INSERT INTO public.recipe_ingredients VALUES (152, 'Rosmarino', 2, '(pz)');
INSERT INTO public.recipe_ingredients VALUES (152, 'Olio Extravergine', 4, 'Cucchiai');
INSERT INTO public.recipe_ingredients VALUES (152, 'Aglio', 2, '(pz)');
INSERT INTO public.recipe_ingredients VALUES (152, 'Sale', 1, 'Cucchiai');
INSERT INTO public.recipe_ingredients VALUES (153, 'Farina per Pizza', 500, '(g)');
INSERT INTO public.recipe_ingredients VALUES (153, 'Acqua', 300, '(ml)');
INSERT INTO public.recipe_ingredients VALUES (153, 'Lievito di birra fresco', 7, '(g)');
INSERT INTO public.recipe_ingredients VALUES (153, 'Passata di Pomodoro', 250, '(g)');
INSERT INTO public.recipe_ingredients VALUES (153, 'Mozzarella fior di latte', 300, '(g)');
INSERT INTO public.recipe_ingredients VALUES (153, 'Olio Extravergine', 3, 'Cucchiai');
INSERT INTO public.recipe_ingredients VALUES (153, 'Basilico', 10, '(pz)');


--
-- TOC entry 4838 (class 0 OID 73453)
-- Dependencies: 221
-- Data for Name: review; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.review VALUES (2, '2026-01-25', 5, 'Finalmente una carbonara come si deve!
Ho seguito alla lettera le dosi di Davide e il risultato è stato impeccabile. La proporzione tra pecorino e tuorli crea una cremina perfetta, senza l''ombra di panna (come sacrilegio comanda!). Il guanciale a listarelle è diventato croccante al punto giusto. Un classico romano eseguito magistralmente. Cinque stelle meritate!', 5, 1);
INSERT INTO public.review VALUES (152, '2026-01-27', 4, 'Croccanti e gustose!', 1, 152);


--
-- TOC entry 4839 (class 0 OID 73458)
-- Dependencies: 222
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.users VALUES (1, 'davide.mandolesi@gmail.com', 'Davide', false, ' Mandolesi', 'https://cdn.discordapp.com/avatars/458964703419564032/2bd385e44cbab61e8eb14f9394da64ad.png?size=160');
INSERT INTO public.users VALUES (2, 'admin@admin.com', 'Admin', false, 'Admin', 'https://plus.unsplash.com/premium_vector-1750416471007-353c65f00e6b?q=80&w=580&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');
INSERT INTO public.users VALUES (52, 'chef.antonio@gmail.com', 'Chef', false, 'Antonio', 'https://images.unsplash.com/photo-1659354217586-c5931b31e4c6?q=80&w=870&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');
INSERT INTO public.users VALUES (4, 'b@b.com', 'B', false, 'B', 'https://icons.iconarchive.com/icons/fa-team/fontawesome/256/FontAwesome-Circle-User-icon.png');
INSERT INTO public.users VALUES (5, 'mario.rossi@gmail.com', 'Mario', true, 'Rossi', 'https://plus.unsplash.com/premium_photo-1689977807477-a579eda91fa2?q=80&w=870&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');
INSERT INTO public.users VALUES (3, 'a@a.com', 'A', false, 'A', 'https://cdn-icons-png.flaticon.com/512/4552/4552937.png');


--
-- TOC entry 4850 (class 0 OID 0)
-- Dependencies: 223
-- Name: category_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.category_seq', 201, true);


--
-- TOC entry 4851 (class 0 OID 0)
-- Dependencies: 224
-- Name: credentials_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.credentials_seq', 101, true);


--
-- TOC entry 4852 (class 0 OID 0)
-- Dependencies: 225
-- Name: recipe_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.recipe_seq', 351, true);


--
-- TOC entry 4853 (class 0 OID 0)
-- Dependencies: 226
-- Name: review_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.review_seq', 251, true);


--
-- TOC entry 4854 (class 0 OID 0)
-- Dependencies: 227
-- Name: users_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.users_seq', 101, true);


--
-- TOC entry 4666 (class 2606 OID 73433)
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- TOC entry 4670 (class 2606 OID 73440)
-- Name: credentials credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT credentials_pkey PRIMARY KEY (id);


--
-- TOC entry 4676 (class 2606 OID 73447)
-- Name: recipe recipe_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.recipe
    ADD CONSTRAINT recipe_pkey PRIMARY KEY (id);


--
-- TOC entry 4678 (class 2606 OID 73457)
-- Name: review review_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.review
    ADD CONSTRAINT review_pkey PRIMARY KEY (id);


--
-- TOC entry 4668 (class 2606 OID 73466)
-- Name: category uk46ccwnsi9409t36lurvtyljak; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT uk46ccwnsi9409t36lurvtyljak UNIQUE (name);


--
-- TOC entry 4680 (class 2606 OID 73472)
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 4672 (class 2606 OID 73468)
-- Name: credentials ukl7xhygibdj6cgkpj2ih1jgx14; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT ukl7xhygibdj6cgkpj2ih1jgx14 UNIQUE (username);


--
-- TOC entry 4674 (class 2606 OID 73470)
-- Name: credentials ukry431gkw9ueu8xq0yfbys0d1d; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT ukry431gkw9ueu8xq0yfbys0d1d UNIQUE (user_id);


--
-- TOC entry 4682 (class 2606 OID 73464)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 4687 (class 2606 OID 73503)
-- Name: review fk9dqw7ep5rnww1yuimutvg4nny; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.review
    ADD CONSTRAINT fk9dqw7ep5rnww1yuimutvg4nny FOREIGN KEY (recipe_id) REFERENCES public.recipe(id);


--
-- TOC entry 4688 (class 2606 OID 73498)
-- Name: review fk9o91rotu09ywxerf1evksnxhd; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.review
    ADD CONSTRAINT fk9o91rotu09ywxerf1evksnxhd FOREIGN KEY (author_id) REFERENCES public.users(id);


--
-- TOC entry 4683 (class 2606 OID 73478)
-- Name: credentials fkcbcgksvnqvqxrrc4dwv3qys65; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT fkcbcgksvnqvqxrrc4dwv3qys65 FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 4686 (class 2606 OID 73493)
-- Name: recipe_ingredients fkhnsmvxdlwxqq6x2wbgnoef5gr; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.recipe_ingredients
    ADD CONSTRAINT fkhnsmvxdlwxqq6x2wbgnoef5gr FOREIGN KEY (recipe_id) REFERENCES public.recipe(id);


--
-- TOC entry 4684 (class 2606 OID 73483)
-- Name: recipe fklvmxb2tmwa9979nk3yexb805p; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.recipe
    ADD CONSTRAINT fklvmxb2tmwa9979nk3yexb805p FOREIGN KEY (author_id) REFERENCES public.users(id);


--
-- TOC entry 4685 (class 2606 OID 73488)
-- Name: recipe fkrufhnv33hpfxstx9x108553kj; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.recipe
    ADD CONSTRAINT fkrufhnv33hpfxstx9x108553kj FOREIGN KEY (category_id) REFERENCES public.category(id);


-- Completed on 2026-02-10 11:11:52

--
-- PostgreSQL database dump complete
--

