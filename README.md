Dental hygiene shop:
 
The shop is meant to sell products to medical professionals and regular people alike.
The User class is abstracticized. It is extended by UnregisteredUser and RegisteredUser.
We store characteristic attributes for each class. E.g. each User has an email address, phone number and ID.
Users can add Products to their Shopping Carts and place Orders.
The only extra feature of Unregistered Users is to create an account.
Registered Users have the following added attributes: password, address, order history. Registered User methods: they can modify their info or delete their account.
The Products that the Users can buy all have an ID, name, base price, stock, type and use. Type can for example be "Toothbrush", "Dental Floss" etc. Use refers to home 
use or medical use.
Every Order has at least one product and is described by an order ID, date and time at which it was issued, User ID and delivery Address.
The many to many relationship between an order and a product is resolved by the ProductOrder class.

For this project we use the MVC pattern. Clients and Admins have different Views, Views have a Controller, which has a Repository. We will use several repository 
interfaces, such as Repository, ClientRepo, ProductRepo, OrderRepo, in order to reduce dependencies between objects.
