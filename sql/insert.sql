-- Gender
INSERT INTO
    gender (gender_code, name)
VALUES
    ('m', 'Male'),
    ('f', 'Female');

-- Faction
INSERT INTO
    faction (name)
VALUES
    ('Crimson Knights'),
    ('Azure Order'),
    ('Emerald Legion'),
    ('Griffon Alliance'),
    ('Aegis Aeternum'),
    ('Misfit Brigade'),
    ('Iron Guardians'),
    ('Shadow Dragon'),
    ('Cosmic Culinarians'),
    ('Silver Legionnaires');

-- Player
INSERT INTO
    player (
        username,
        character_name,
        gender_id,
        level,
        faction_id,
        description
    )
VALUES
    -- Male
    (
        'William',
        'IronClad Will',
        1,
        87,
        1,
        'A stoic warrior known for his unwavering resolve.'
    ),
    (
        'Alexander',
        'Nightshade',
        1,
        97,
        2,
        'A cunning rogue who operates under the cloak of darkness.'
    ),
    (
        'Matthew',
        'Emberheart',
        1,
        77,
        3,
        'A passionate mage who wields fire magic with fiery spirit.'
    ),
    (
        'Joseph',
        'Stoneshield',
        1,
        12,
        4,
        'A dependable tank, strong and unwavering as the earth itself.'
    ),
    (
        'Daniel',
        'Whisperwind',
        1,
        62,
        5,
        'A nimble archer known for his swiftness and silent strikes.'
    ),
    (
        'Christopher',
        'Skyforge',
        1,
        87,
        6,
        'A skilled blacksmith known for crafting legendary weapons and armor.'
    ),
    (
        'Jacob',
        'Moonwalker',
        1,
        87,
        7,
        'A mysterious druid who communes with nature and wields its power.'
    ),
    (
        'Ethan',
        'Stormbreaker',
        1,
        100,
        8,
        'A powerful berserker who channels the fury of the storm in battle.'
    ),
    (
        'Michael',
        'Dawnbringer',
        1,
        99,
        9,
        'A charismatic paladin who fights for justice and inspires hope.'
    ),
    (
        'David',
        'Silverwing',
        1,
        56,
        10,
        'A skilled monster hunter known for his swiftness and aerial prowess.'
    ),
    -- Female
    (
        'Emily',
        'Whisperblade',
        2,
        88,
        5,
        'A deadly assassin known for her stealth and precise strikes.'
    ),
    (
        'Olivia',
        'Sunweaver',
        2,
        98,
        7,
        '	A radiant priestess who heals her allies and channels the power of the sun.'
    ),
    (
        'Sophia',
        'Moonwhisperer',
        2,
        47,
        7,
        'A kind enchantress who uses illusion magic and diplomacy to solve problems.'
    ),
    (
        'Isabella',
        'Nightsong',
        2,
        92,
        2,
        'A talented bard who inspires her comrades with music and magic.'
    ),
    (
        'Charlotte',
        'Wildfire',
        2,
        98,
        3,
        'A fierce huntress who uses her wit and agility to track and take down prey.'
    ),
    (
        'Ava',
        'Earthshaker',
        2,
        73,
        1,
        'A powerful geomancer who commands the power of the earth to control the battlefield.'
    ),
    (
        'Mia',
        'Starlight',
        2,
        96,
        9,
        'A skilled astrologer who studies the stars and uses their power for guidance and magic.'
    ),
    (
        'Evelyn',
        'Stormbringer',
        2,
        100,
        8,
        'A courageous warrior who channels the power of lightning in battle.'
    ),
    (
        'Abigail',
        'Nightspark',
        2,
        99,
        9,
        'A mischievous inventor who creates fantastical gadgets and traps.'
    ),
    (
        'Hannah',
        'Silvermist',
        2,
        24,
        10,
        'A graceful dancer and swordswoman who moves with deadly precision.'
    );

-- Staff
INSERT INTO
    staff (username, character_name)
VALUES
    ('Silara Feather', 'Silara'),
    ('Kharn Darkstone', 'Kharn'),
    ('Eryn Petalbloom', 'Eryn'),
    ('Fae Whisperwind', 'Fae'),
    ('Zahir Moonshadow', 'Zahir');

-- Type
INSERT INTO
    type (name)
VALUES
    ('Weapon'),
    ('Armor'),
    ('Consumable'),
    ('Crafting materials'),
    ('Quest Item');

-- Rarity
INSERT INTO
    rarity (name)
VALUES
    ('Common'),
    ('Uncommon'),
    ('Rare'),
    ('Epic'),
    ('Legendary'),
    ('Mythic'),
    ('Divine');

-- Item
INSERT INTO
    item (name, type_id, rarity_id)
VALUES
    -- Weapon
    ('Training Sword', 1, 1),
    ('Hunter''s Axe', 1, 2),
    ('Dwarven War Hammer', 1, 3),
    ('Dragon Slayer Longsword', 1, 4),
    ('Staff of the Ancients', 1, 5),
    -- Armor
    ('Leather Jerkin', 2, 1),
    ('Traveler''s Cloak', 2, 2),
    ('Knight''s plate armor', 2, 3),
    ('Dragonscale armor', 2, 4),
    ('Robe of the Archmage', 2, 5),
    -- Consumables
    ('Healing Potion', 3, 1),
    ('Antidote Potion', 3, 2),
    ('Phoenix Feather', 3, 3),
    ('Elixir of Life', 3, 4),
    ('Potion of Invisibility', 3, 5),
    -- Crafting materials
    ('Iron Ore', 4, 1),
    ('Mithril Ore', 4, 2),
    ('Moonwood', 4, 3),
    ('Phoenix Down', 4, 4),
    ('Essence of a God', 4, 5),
    -- Quest item
    ('Rusty Key', 5, 1),
    ('Ancient Tablet', 5, 2),
    ('King''s Royal Decree', 5, 3),
    ('Demon''s Eye', 5, 4),
    ('Orb of creation', 5, 5);

-- Inventory
INSERT INTO
    inventory (item_id, player_id, durability, quantity)
VALUES
    (1, 1, 50, 1),
    (2, 17, 100, 1),
    (3, 2, 70, 1),
    (4, 3, 80, 1),
    (5, 4, 30, 1),
    (6, 5, 20, 1),
    (7, 6, 10, 1),
    (8, 7, 100, 1),
    (9, 8, 90, 1),
    (10, 9, 90, 4),
    (11, 10, 100, 1),
    (12, 11, 100, 1),
    (13, 12, 100, 1),
    (14, 13, 100, 1),
    (15, 14, 100, 1),
    (16, 15, 100, 1),
    (17, 16, 100, 1),
    (18, 18, 100, 1),
    (19, 19, 100, 1),
    (20, 20, 100, 1);

-- Transaction type
INSERT INTO
    transaction_type (transaction_type_code, name)
VALUES
    ('w', 'Withdraw'),
    ('d', 'Deposit');

-- Transaction
INSERT INTO
    transaction (
        transaction_type_id,
        item_id,
        player_id,
        staff_id,
        note,
        date
    )
VALUES
    (2, 5, 1, 1, '', '2023-01-14'),
    (2, 1, 2, 1, '', '2023-10-21'),
    (2, 12, 3, 1, '', '2023-05-02'),
    (2, 7, 4, 1, '', '2024-01-25'),
    (2, 4, 5, 1, '', '2024-02-10');