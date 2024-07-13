-- Rarity
SELECT
    rarity.rarity_id AS "rarity.rarity_id",
    rarity.name AS "rarity.name",
    rarity.img_path AS "rarity.img_path"
FROM
    rarity;

-- Type
SELECT
    type.type_id AS "type.type_id",
    type.name AS "type.name",
    type.img_path AS "type.img_path"
FROM
    type;

-- Player
SELECT
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
    player;

-- Transaction
SELECT
    transaction.transaction_id AS "transaction.transaction_id",
    transaction.date AS "transaction.date",
    transaction.transaction_type_id AS "transaction.transaction_type_id",
    transaction.item_id AS "transaction.item_id",
    transaction.player_id AS "transaction.player_id",
    transaction.staff_id AS "transaction.staff_id",
    transaction.note AS "transaction.note"
FROM
    transaction;

-- Transaction type
SELECT
    transaction_type.transaction_type_id AS "transaction_type.transaction_type_id",
    transaction_type.transaction_type_code AS "transaction_type.transaction_type_code",
    transaction_type.name AS "transaction_type.name"
FROM
    transaction_type;