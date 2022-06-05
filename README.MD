Run online: gradle run --args="online"
Run offline: gradle run --args="offline"

The user can see whether the server is currently active
    Check Server Status Button Top Right Corner

A new user can obtain and store credentials (these can just be stored in RAM, no need for saving to disk, but the user should be able to copy their access token to the clipboard)
    Opening Screen - Type a username and press register
    Token is copiable

An existing user can enter and store their existing credentials
    Opening Screen - Login with a token (without username is fine)

The user can see their info
    After logging in, the user info is displayed in the top left corner
    It is refreshed whenever a user does something that changes their user info

The user can list available loans and their details
    Press 'List Available Loans' in the menu on the LHS

The user can obtain a loan
    Press 'Obtain a Loan' in the menu on the LHS
    Select a loan from the table above and press confirm

The user can list active loans
    Press 'List Active Loans' in the menu on the LHS

The user can list available ships and their details
    Press 'List Available Ships' in the menu on the LHS

The user can purchase a ship
    Press 'Purchase a Ship' in the menu on the LHS
    Select a ship from the table above and select a location and press confirm

The user can list their ships and their details
    Press 'List My Ships' in the menu on the LHS

The user can purchase ship fuel
    Press 'Purchase Ship Fuel' in the menu on the LHS
    Select a ship and type a quantity of fuel and press confirm

The user can view the marketplace details for a given location
    Press 'View Local Marketplace' in the menu on the LHS
    Select a location

The user can purchase goods from the marketplace
    Press 'Purchase Goods' in the menu on the LHS
    Select a location, ship and a good from the table and type in a quantity and press confirm

The user can list nearby locations and their details
    Press 'Nearby Locations' in the menu on the LHS

The user can create a flight plan and journey to a nearby location
    Press 'Create a Flight Plan' in the menu on the LHS
    Select a ship from the table and select a destination and press confirm

The user can view their current flight plan (this does not need to automatically refresh, manual is ok)
    Press 'View Current Flight Plans' in the menu on the LHS
    Refresh by pressing refresh flight table

The user can sell goods to the marketplace
    Press 'Sell Goods' in the menu on the LHS
    Select a Ship from the table, a good and a quantity and press confirm

These features should be available ‘statelessly’ – that is, if a user closes the application and re-opens it, they should be able to pick up where they left off. This must be based on the server state, not any locally saved data.

Image References:
    Alfanz. (n.d.). Ecommerce free icon [Illustration]. Flaticon. https://www.flaticon.com/free-icons/marketplace
    Dan, D. (n.d.). space ship launch icons [Illustration]. Flaticon. https://www.flaticon.com/premium-icon/spaceship_3155295
    Freepik. (n.d.). location icons [Illustrations]. Flaticon. https://www.flaticon.com/free-icons/location
    Ware, G. (n.d.). transfer money icons [Illustration]. Flaticon. https://www.flaticon.com/free-icons/transfer-money

# Space-Traders-Marketplace
