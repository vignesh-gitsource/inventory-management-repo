-- Products (Fruit names)
INSERT INTO product_entity (id, name, sku, price, version, stock) VALUES ('d290f1ee-6c54-4b01-90e6-d701748f0851', 'Apple', 'SKU-APPLE', 1.50, 0, 100);
INSERT INTO product_entity (id, name, sku, price, version, stock) VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'Banana', 'SKU-BANANA', 0.50, 0, 150);
INSERT INTO product_entity (id, name, sku, price, version, stock) VALUES ('7c9e6679-7425-40de-944b-e07fc1f90ae7', 'Cherry', 'SKU-CHERRY', 3.00, 0, 200);
INSERT INTO product_entity (id, name, sku, price, version, stock) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Date', 'SKU-DATE', 2.00, 0, 120);
INSERT INTO product_entity (id, name, sku, price, version, stock) VALUES ('5a4d7f9a-6b58-41d6-9174-22936d5ecaa4', 'Elderberry', 'SKU-ELDERBERRY', 4.00, 0, 80);
INSERT INTO product_entity (id, name, sku, price, version, stock) VALUES ('3b241101-e2bb-4255-8caf-4136c566a962', 'Fig', 'SKU-FIG', 2.50, 0, 90);
INSERT INTO product_entity (id, name, sku, price, version, stock) VALUES ('fbdc6a0c-05a1-4d21-a79c-4a9e86f07f01', 'Grape', 'SKU-GRAPE', 2.20, 0, 110);
INSERT INTO product_entity (id, name, sku, price, version, stock) VALUES ('e58c4c5a-93d3-4b14-ae8c-faa21db0f2e2', 'Honeydew Melon', 'SKU-HONEYDEW', 3.50, 0, 60);
INSERT INTO product_entity (id, name, sku, price, version, stock) VALUES ('a1d8c9f9-6a49-4a23-bac0-74f1e6d20792', 'Indian Fig (Prickly Pear)', 'SKU-INDIANFIG', 3.80, 0, 40);
INSERT INTO product_entity (id, name, sku, price, version, stock) VALUES ('45d3c691-8a54-4f0c-85c2-1e3882c8a7b6', 'Jackfruit', 'SKU-JACKFRUIT', 1.80, 0, 70);

-- Orders
INSERT INTO order_entity (id, status) VALUES ('56e8b2e3-984c-4c0e-9d48-2d3c63d8e05f', 'PENDING');
INSERT INTO order_entity (id, status) VALUES ('abd7e381-9f7a-4b2f-8c44-24a4ea4eb772', 'PENDING');
INSERT INTO order_entity (id, status) VALUES ('62f71b1f-bf72-4846-9db0-2cc5cb13b615', 'PENDING');
INSERT INTO order_entity (id, status) VALUES ('94e1c684-7012-4d19-9c5b-8d5e1f9a5ca9', 'PENDING');
INSERT INTO order_entity (id, status) VALUES ('2b7d1e89-83e2-44f6-a3b0-8f69d16b7a62', 'PENDING');

-- Order Items
INSERT INTO order_item_entity (id, order_id, product_id, quantity) VALUES ('aa66aabe-cc34-4e16-870a-3e0a45d87ed9', '56e8b2e3-984c-4c0e-9d48-2d3c63d8e05f', 'd290f1ee-6c54-4b01-90e6-d701748f0851', 10);
INSERT INTO order_item_entity (id, order_id, product_id, quantity) VALUES ('df02b46c-9b55-4f42-94e6-224e7aee4c2e', '56e8b2e3-984c-4c0e-9d48-2d3c63d8e05f', 'f47ac10b-58cc-4372-a567-0e02b2c3d479', 15);
INSERT INTO order_item_entity (id, order_id, product_id, quantity) VALUES ('c437e7b2-77fa-4f2a-b0bc-12836a9cbf44', 'abd7e381-9f7a-4b2f-8c44-24a4ea4eb772', '7c9e6679-7425-40de-944b-e07fc1f90ae7', 20);
INSERT INTO order_item_entity (id, order_id, product_id, quantity) VALUES ('88a5bbf7-f2e2-4667-a83c-21ddbb5eae99', 'abd7e381-9f7a-4b2f-8c44-24a4ea4eb772', '123e4567-e89b-12d3-a456-426614174000', 12);
INSERT INTO order_item_entity (id, order_id, product_id, quantity) VALUES ('d3c7a244-f73c-4f1f-8d82-fde49a462db1', '62f71b1f-bf72-4846-9db0-2cc5cb13b615', '5a4d7f9a-6b58-41d6-9174-22936d5ecaa4', 8);
INSERT INTO order_item_entity (id, order_id, product_id, quantity) VALUES ('ef2c38db-5942-48de-874a-c7c2090d7a62', '94e1c684-7012-4d19-9c5b-8d5e1f9a5ca9', '3b241101-e2bb-4255-8caf-4136c566a962', 10);
INSERT INTO order_item_entity (id, order_id, product_id, quantity) VALUES ('67b8e9ad-9f10-4216-8f8e-52725b76f938', '94e1c684-7012-4d19-9c5b-8d5e1f9a5ca9', 'fbdc6a0c-05a1-4d21-a79c-4a9e86f07f01', 9);
INSERT INTO order_item_entity (id, order_id, product_id, quantity) VALUES ('c9b41e97-b06e-4ac9-aeb2-89bb75c3a77b', '2b7d1e89-83e2-44f6-a3b0-8f69d16b7a62', 'e58c4c5a-93d3-4b14-ae8c-faa21db0f2e2', 6);
INSERT INTO order_item_entity (id, order_id, product_id, quantity) VALUES ('37c12d6f-1f0c-42a7-9c6c-e8e1a678a8b4', '2b7d1e89-83e2-44f6-a3b0-8f69d16b7a62', 'a1d8c9f9-6a49-4a23-bac0-74f1e6d20792', 4);
INSERT INTO order_item_entity (id, order_id, product_id, quantity) VALUES ('2e4aeb72-45df-4c1a-8a70-55986e29516f', '2b7d1e89-83e2-44f6-a3b0-8f69d16b7a62', '45d3c691-8a54-4f0c-85c2-1e3882c8a7b6', 5);
