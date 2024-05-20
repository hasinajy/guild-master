-- Player
SELECT
    player.player_id AS player_id,
    player.username AS username,
    player.character_name AS character_name,
    player.level AS level,
    player.description AS description,
    player.img_path AS img_path,
    gender.gender_id AS gender_id,
    gender.name AS gender_name,
    faction.faction_id AS faction_id,
    faction.name AS faction_name
FROM
    player
    JOIN gender ON player.gender_id = gender.gender_id
    LEFT JOIN faction ON player.faction_id = faction.faction_id
WHERE
    player.is_deleted = false;

UPDATE
    player
SET
    is_deleted = true
WHERE
    player_id = ?;

-- Inventory
SELECT
    inventory_id,
    item.item_id AS item_id,
    inventory.player_id AS player_id,
    durability,
    quantity,
    item.type_id AS type_id,
    item.rarity_id AS rarity_id,
    player.is_deleted AS is_deleted
FROM
    inventory
    JOIN item ON inventory.item_id = item.item_id
    JOIN player ON inventory.player_id = player.player_id
WHERE
    inventory_id = ?;

SELECT
    inventory.inventory_id AS inventory_id,
    item.item_id AS item_id,
    item.name AS item_name,
    player.character_name AS character_name,
    durability,
    quantity,
    type.type_id AS type_id,
    type.name AS type_name,
    rarity.rarity_id AS rarity_id,
    rarity.name AS rarity_name,
    item.img_path AS img_path,
    player.is_deleted AS player_deleted
FROM
    inventory
    JOIN item ON inventory.item_id = item.item_id
    LEFT JOIN type ON item.type_id = type.type_id
    LEFT JOIN rarity ON item.rarity_id = rarity.rarity_id
    JOIN player ON inventory.player_id = player.player_id
WHERE
    item.is_deleted = false
ORDER BY inventory_id ASC;

1 = 1
AND character_name LIKE '%a%';

AND character_name LIKE '%a%'
AND item_name LIKE '%e'
AND durability BETWEEN 35
AND 80
AND type_id = 2
AND rarity_id = 5
AND quantity BETWEEN 1
AND 10;

-- Item
SELECT
    item.item_id AS item_id,
    item.name AS item_name,
    item.type_id AS type_id,
    type.name AS type_name,
    item.rarity_id AS rarity_id,
    rarity.name AS rarity_name,
    item.img_path AS img_path,
    item.is_deleted AS is_deleted
FROM
    item
    LEFT JOIN type ON item.type_id = type.type_id
    LEFT JOIN rarity ON item.rarity_id = rarity.rarity_id
WHERE
    item.name LIKE '%ie%'
    OR item.type_id = 1
    OR item.rarity_id = 1;

-- Transaction
SELECT
    transaction_id,
    date,
    transaction_type.name AS transaction_type,
    item.name AS item_name,
    player.character_name AS player_character_name,
    staff.username AS staff_character_name,
    note
FROM
    transaction
    JOIN transaction_type ON transaction.transaction_type_id = transaction_type.transaction_type_id
    JOIN item ON transaction.item_id = item.item_id
    JOIN player ON transaction.player_id = player.player_id
    JOIN staff ON transaction.staff_id = staff.staff_id
WHERE
    1 = 1;