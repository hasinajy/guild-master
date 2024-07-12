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
        durability INT,
        quantity INT,
        FOREIGN KEY (item_id) REFERENCES item (item_id),
        FOREIGN KEY (player_id) REFERENCES player (player_id)
        -- TODO: Add durability constraint
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

-- Item view
CREATE
OR REPLACE VIEW v_item AS
SELECT
    item.item_id AS "item.item_id",
    item.name AS "item.name",
    item.type_id AS "item.type_id",
    item.rarity_id AS "item.rarity_id",
    item.img_path AS "item.img_path",
    item.is_deleted AS "item.is_deleted",
    type.type_id AS "type.type_id",
    type.name AS "type.name",
    type.img_path AS "type.img_path",
    rarity.rarity_id AS "rarity.rarity_id",
    rarity.name AS "rarity.name",
    rarity.img_path AS "rarity.img_path"
FROM
    item
    LEFT JOIN type ON item.type_id = type.type_id
    LEFT JOIN rarity ON item.rarity_id = rarity.rarity_id
WHERE
    item.is_deleted = false
ORDER BY
    item.name;

-- Active inventory view
CREATE
OR REPLACE VIEW v_inventory_active AS
SELECT
    inventory.inventory_id AS "inventory.inventory_id",
    inventory.item_id AS "inventory.item_id",
    inventory.player_id AS "inventory.player_id",
    inventory.durability AS "inventory.durability",
    inventory.quantity AS "inventory.quantity",
    item.item_id AS "item.item_id",
    item.name AS "item.name",
    item.type_id AS "item.type_id",
    item.rarity_id AS "item.rarity_id",
    item.img_path AS "item.img_path",
    item.is_deleted AS "item.is_deleted",
    player.player_id AS "player.player_id",
    player.username AS "player.username",
    player.character_name AS "player.character_name",
    player.gender_id AS "player.gender_id",
    player.level AS "player.level",
    player.faction_id AS "player.faction_id",
    player.description AS "player.description",
    player.img_path AS "player.img_path",
    player.is_deleted AS "player.is_deleted"
FROM
    inventory
    JOIN item ON inventory.item_id = item.item_id
    JOIN player ON inventory.player_id = player.player_id
WHERE
    item.is_deleted = false
ORDER BY
    inventory.inventory_id ASC;

-- Inventory view
CREATE
OR REPLACE VIEW v_inventory AS
SELECT
    inventory.inventory_id AS "inventory.inventory_id",
    inventory.item_id AS "inventory.item_id",
    inventory.player_id AS "inventory.player_id",
    inventory.durability AS "inventory.durability",
    inventory.quantity AS "inventory.quantity",
    item.item_id AS "item.item_id",
    item.name AS "item.name",
    item.type_id AS "item.type_id",
    item.rarity_id AS "item.rarity_id",
    item.img_path AS "item.img_path",
    item.is_deleted AS "item.is_deleted",
    player.player_id AS "player.player_id",
    player.username AS "player.username",
    player.character_name AS "player.character_name",
    player.gender_id AS "player.gender_id",
    player.level AS "player.level",
    player.faction_id AS "player.faction_id",
    player.description AS "player.description",
    player.img_path AS "player.img_path",
    player.is_deleted AS "player.is_deleted",
    type.type_id AS "type.type_id",
    type.name AS "type.name",
    type.img_path AS "type.img_path",
    rarity.rarity_id AS "rarity.rarity_id",
    rarity.name AS "rarity.name",
    rarity.img_path AS "rarity.img_path"
FROM
    inventory
    JOIN item ON inventory.item_id = item.item_id
    JOIN player ON inventory.player_id = player.player_id
    LEFT JOIN type ON item.type_id = type.type_id
    LEFT JOIN rarity ON item.rarity_id = rarity.rarity_id
WHERE
    item.is_deleted = false
ORDER BY
    inventory.inventory_id ASC;

-- Transaction full view
CREATE
OR REPLACE VIEW v_transaction AS
SELECT
    transaction.transaction_id AS "transaction.transaction_id",
    transaction.date AS "transaction.date",
    transaction.transaction_type_id AS "transaction.transaction_type_id",
    transaction.item_id AS "transaction.item_id",
    transaction.player_id AS "transaction.player_id",
    transaction.staff_id AS "transaction.staff_id",
    transaction.note AS "transaction.note",
    transaction_type.transaction_type_id AS "transaction_type.transaction_type_id",
    transaction_type.transaction_type_code AS "transaction_type.transaction_type_code",
    transaction_type.name AS "transaction_type.name",
    item.item_id AS "item.item_id",
    item.name AS "item.name",
    item.type_id AS "item.type_id",
    item.rarity_id AS "item.rarity_id",
    item.img_path AS "item.img_path",
    item.is_deleted AS "item.is_deleted",
    player.player_id AS "player.player_id",
    player.username AS "player.username",
    player.character_name AS "player.character_name",
    player.gender_id AS "player.gender_id",
    player.level AS "player.level",
    player.faction_id AS "player.faction_id",
    player.description AS "player.description",
    player.img_path AS "player.img_path",
    player.is_deleted AS "player.is_deleted",
    staff.staff_id AS "staff.staff_id",
    staff.username AS "staff.username",
    staff.character_name AS "staff.character_name"
FROM
    transaction
    JOIN transaction_type ON transaction.transaction_type_id = transaction_type.transaction_type_id
    JOIN item ON transaction.item_id = item.item_id
    JOIN player ON transaction.player_id = player.player_id
    JOIN staff ON transaction.staff_id = staff.staff_id
ORDER BY
    transaction.date DESC;

-- Player full view
CREATE
OR REPLACE VIEW v_player AS
SELECT
    player.player_id AS "player.player_id",
    player.username AS "player.username",
    player.character_name AS "player.character_name",
    player.gender_id AS "player.gender_id",
    player.level AS "player.level",
    player.faction_id AS "player.faction_id",
    player.description AS "player.description",
    player.img_path AS "player.img_path",
    player.is_deleted AS "player.is_deleted",
    gender.gender_id AS "gender.gender_id",
    gender.gender_code AS "gender.gender_code",
    gender.name AS "gender.name",
    faction.faction_id AS "faction.faction_id",
    faction.name AS "faction.name",
    faction.img_path AS "faction.img_path"
FROM
    player
    JOIN gender ON player.gender_id = gender.gender_id
    LEFT JOIN faction ON player.faction_id = faction.faction_id
WHERE
    player.is_deleted = false
ORDER BY
    player.character_name ASC;