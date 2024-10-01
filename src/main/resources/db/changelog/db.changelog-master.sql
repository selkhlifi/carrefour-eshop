CREATE TABLE product (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          code VARCHAR(50) NOT NULL,
                          name VARCHAR(100),
                          description VARCHAR(255)
);

INSERT INTO product (id, code, name, description) VALUES
                                                       (1, 'P001', 'Product 1', 'Description of Product 1'),
                                                       (2, 'P002', 'Product 2', 'Description of Product 2');


CREATE TABLE customer (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           first_name VARCHAR(50) NOT NULL,
                           last_name VARCHAR(50) NOT NULL,
                           username VARCHAR(50) NOT NULL UNIQUE,
                           password VARCHAR(100) NOT NULL
);

INSERT INTO customer (first_name, last_name, username, password) VALUES
                                                                      ('John', 'Doe', 'johndoe', 'password123'),
                                                                      ('Jane', 'Smith', 'janesmith', 'securepassword'),
                                                                      ('Alice', 'Johnson', 'alicej', 'mypassword'),
                                                                      ('Bob', 'Williams', 'bobw', 'password456');


CREATE TABLE cart (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       customer_id BIGINT,                        -- Foreign key to reference the customer
                       total DECIMAL(19, 2),                      -- BigDecimal with scale 2 for currency values
                       CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id)  -- Foreign key constraint to the customers table
);


CREATE TABLE cart_entry (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,                  -- ID as the primary key
                              cart_id BIGINT,                         -- Foreign key reference to the carts table
                              product_id BIGINT,                      -- ID of the associated product
                              quantity INT,                           -- Quantity of the product in the cart
                              price DECIMAL(19, 2),                   -- Price captured at the time of purchase
                              CONSTRAINT fk_cart FOREIGN KEY (cart_id) REFERENCES cart(id)  -- Foreign key constraint to the carts table
);
