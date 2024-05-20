CREATE DATABASE guild_master;

USE guild_master;

CREATE TABLE
    gender (
        gender_id SERIAL PRIMARY KEY,
        gender_code VARCHAR(1) UNIQUE,
        name VARCHAR(8)
    );

CREATE TABLE
    faction (
        faction_id SERIAL PRIMARY KEY,
        name VARCHAR(32),
        img_path VARCHAR(32) DEFAULT 'faction/'
    );

CREATE TABLE
    player (
        player_id SERIAL PRIMARY KEY,
        username VARCHAR(32),
        character_name VARCHAR(32),
        gender_id INT,
        level INT,
        faction_id INT,
        description VARCHAR(256),
        img_path VARCHAR(32) DEFAULT 'player/',
        is_deleted BOOLEAN DEFAULT false,
        FOREIGN KEY (gender_id) REFERENCES gender (gender_id),
        FOREIGN KEY (faction_id) REFERENCES faction (faction_id) ON DELETE SET NULL
    );

CREATE TABLE
    staff (
        staff_id SERIAL PRIMARY KEY,
        username VARCHAR(32),
        character_name VARCHAR(32)
    );

CREATE TABLE
    type (
        type_id SERIAL PRIMARY KEY,
        name VARCHAR(32),
        img_path VARCHAR(32) DEFAULT 'type/'
    );

CREATE TABLE
    rarity (
        rarity_id SERIAL PRIMARY KEY,
        name VARCHAR(32),
        img_path VARCHAR(32) DEFAULT 'rarity/'
    );

CREATE TABLE
    item (
        item_id SERIAL PRIMARY KEY,
        name VARCHAR(32),
        type_id INT,
        rarity_id INT,
        img_path VARCHAR(32) DEFAULT 'item/',
        is_deleted BOOLEAN DEFAULT false,
        FOREIGN KEY (type_id) REFERENCES type (type_id) ON DELETE SET NULL,
        FOREIGN KEY (rarity_id) REFERENCES rarity (rarity_id) ON DELETE SET NULL
    );

CREATE TABLE
    inventory (
        inventory_id SERIAL PRIMARY KEY,
        item_id INT,
        player_id INT,
        durability FLOAT,
        quantity INT,
        FOREIGN KEY (item_id) REFERENCES item (item_id),
        FOREIGN KEY (player_id) REFERENCES player (player_id)
    );

CREATE TABLE
    transaction_type (
        transaction_type_id SERIAL PRIMARY KEY,
        transaction_type_code VARCHAR(1) UNIQUE,
        name VARCHAR(16)
    );

CREATE TABLE
    transaction (
        transaction_id SERIAL PRIMARY KEY,
        date DATE DEFAULT CURRENT_DATE,
        transaction_type_id INT,
        item_id INT,
        player_id INT,
        staff_id INT,
        note VARCHAR(128),
        FOREIGN KEY (transaction_type_id) REFERENCES transaction_type (transaction_type_id),
        FOREIGN KEY (item_id) REFERENCES item (item_id),
        FOREIGN KEY (player_id) REFERENCES player (player_id),
        FOREIGN KEY (staff_id) REFERENCES staff (staff_id)
    );