DROP DATABASE IF EXISTS busticketbooking;

CREATE DATABASE busticketbooking;

-- Agencies
CREATE TABLE agencies (
    agency_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_person_name VARCHAR(30) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL
);

-- Addresses
CREATE TABLE addresses (
    address_id SERIAL PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zip_code VARCHAR(10) NOT NULL
);

-- Agency Offices
CREATE TABLE agency_offices(
    office_id SERIAL PRIMARY KEY,
    agency_id INT,
    office_mail VARCHAR(100),
    office_contact_person_name VARCHAR(50),
    office_contact_number CHAR(10),
    office_address_id INT,
    FOREIGN KEY (agency_id) REFERENCES agencies(agency_id),
    FOREIGN KEY (office_address_id) REFERENCES addresses(address_id)
);

-- Customers
CREATE TABLE customers (
    customer_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    address_id INT,
    FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);

-- Buses
CREATE TABLE buses (
    bus_id SERIAL PRIMARY KEY,
    office_id INT NOT NULL,
    registration_number VARCHAR(20) NOT NULL,
    capacity INT NOT NULL,
    type VARCHAR(30) NOT NULL,
    FOREIGN KEY (office_id) REFERENCES agency_offices(office_id)
);

-- Drivers
CREATE TABLE drivers (
    driver_id SERIAL PRIMARY KEY,
    license_number VARCHAR(20) NOT NULL,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    office_id INT,
    address_id INT,
    FOREIGN KEY (office_id) REFERENCES agency_offices(office_id),
    FOREIGN KEY (address_id) REFERENCES addresses(address_id)
);

-- Routes
CREATE TABLE routes (
    route_id SERIAL PRIMARY KEY,
    from_city VARCHAR(255) NOT NULL,
    to_city VARCHAR(255) NOT NULL,
    break_points INT,
    duration INT
);

-- Trips
CREATE TABLE trips(
    trip_id SERIAL PRIMARY KEY,
    route_id INT NOT NULL,
    bus_id INT NOT NULL,
    boarding_address_id INT NOT NULL,
    dropping_address_id INT NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    driver1_driver_id INT NOT NULL,
    driver2_driver_id INT NOT NULL,
    available_seats INT NOT NULL,
    fare DECIMAL(10, 2) NOT NULL,
    trip_date TIMESTAMP NOT NULL,
    FOREIGN KEY (bus_id) REFERENCES buses(bus_id),
    FOREIGN KEY (driver1_driver_id) REFERENCES drivers(driver_id),
    FOREIGN KEY (driver2_driver_id) REFERENCES drivers(driver_id),
    FOREIGN KEY (route_id) REFERENCES routes(route_id),
    FOREIGN KEY (boarding_address_id) REFERENCES addresses(address_id),
    FOREIGN KEY (dropping_address_id) REFERENCES addresses(address_id)
);

CREATE TYPE booking_status AS ENUM ('Available', 'Booked');
CREATE TYPE payment_status_type AS ENUM ('Success', 'Failed');

-- Bookings Table
CREATE TABLE bookings (
    booking_id SERIAL PRIMARY KEY,
    trip_id INT,
    seat_number INT NOT NULL,
    status booking_status DEFAULT 'Available',
    FOREIGN KEY (trip_id) REFERENCES trips(trip_id)
);

-- Payments table
CREATE TABLE payments (
    payment_id SERIAL PRIMARY KEY,
    booking_id INT NOT NULL,
    customer_id INT,
    amount DECIMAL(10, 2),
    payment_date TIMESTAMP,
    payment_status payment_status_type,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);

-- Reviews table
CREATE TABLE reviews (
    review_id SERIAL PRIMARY KEY,
    customer_id INT NOT NULL,
    trip_id INT NOT NULL,
    rating INT NOT NULL,
    comment TEXT,
    review_date TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (trip_id) REFERENCES trips(trip_id)
);
INSERT INTO addresses (address, city, state, zip_code) VALUES
    ('Sapphire Towers, Malleswaram', 'Bangalore', 'Karnataka', '560003'),
    ('Ocean View Apartments, Bandra West', 'Mumbai', 'Maharashtra', '400050'),
    ('Green View Residency, Lajpat Nagar', 'Delhi', 'Delhi', '110024'),
    ('Eco Heights, New Town', 'Kolkata', 'West Bengal', '700156'),
    ('Golden Orchid Apartments, Velachery', 'Chennai', 'Tamil Nadu', '600042'),
    ('Harmony Residency, Kukatpally', 'Hyderabad', 'Telangana', '500072'),
    ('Sunrise Enclave, Kharadi', 'Pune', 'Maharashtra', '411014'),
    ('Sapphire Heights, Maninagar', 'Ahmedabad', 'Gujarat', '380008'),
    ('Emerald Towers, Katargam', 'Surat', 'Gujarat', '395004'),
    ('Royal Paradise, Tonk Road', 'Jaipur', 'Rajasthan', '302018'),
    ('Elite Greens, Gomti Nagar', 'Lucknow', 'Uttar Pradesh', '226010'),
    ('Imperial Towers, Civil Lines', 'Kanpur', 'Uttar Pradesh', '208001'),
    ('Grand View Apartments, South Tukoganj', 'Indore', 'Madhya Pradesh', '452001'),
    ('Pearl Residency, Kharghar', 'Navi Mumbai', 'Maharashtra', '410210'),
    ('Riverside Homes, Arera Hills', 'Bhopal', 'Madhya Pradesh', '462011'),
    ('Serene Residences, Sharanpur Road', 'Nashik', 'Maharashtra', '422005'),
    ('Tranquil Towers, Kalawad Road', 'Rajkot', 'Gujarat', '360005'),
    ('Regal Heights, Ranjit Avenue', 'Amritsar', 'Punjab', '143001'),
    ('Mountain View Homes, Hazratbal', 'Srinagar', 'Jammu and Kashmir', '190006'),
    ('Majestic Towers, Sector 16', 'Faridabad', 'Haryana', '121002'),
    ('Royal Plaza, Begum Bridge', 'Meerut', 'Uttar Pradesh', '250001'),
    ('Eternal Enclave, Khadakpada', 'Kalyan', 'Maharashtra', '421301'),
    ('Silver Springs, Vasai East', 'Vasai', 'Maharashtra', '401208'),
    ('Sunset Residency, Kothrud', 'Pune', 'Maharashtra', '411038'),
    ('Golden Meadows, Aliganj', 'Lucknow', 'Uttar Pradesh', '226024'),
    ('Tranquil Heights, Malleswaram', 'Bangalore', 'Karnataka', '560003'),
    ('Sunset Residences, Bandra West', 'Mumbai', 'Maharashtra', '400050'),
    ('Golden Plaza, Lajpat Nagar', 'Delhi', 'Delhi', '110024'),
    ('Skyline Towers, New Town', 'Kolkata', 'West Bengal', '700156'),
    ('Silk Meadows, Velachery', 'Chennai', 'Tamil Nadu', '600042'),
    ('Pristine Gardens, Kukatpally', 'Hyderabad', 'Telangana', '500072'),
    ('Emerald Heights, Kharadi', 'Pune', 'Maharashtra', '411014'),
    ('Blissful Homes, Maninagar', 'Ahmedabad', 'Gujarat', '380008'),
    ('Grandeur Residency, Katargam', 'Surat', 'Gujarat', '395004'),
    ('Majestic Towers, Tonk Road', 'Jaipur', 'Rajasthan', '302018'),
    ('Crown Court, Gomti Nagar', 'Lucknow', 'Uttar Pradesh', '226010'),
    ('Avenue Gardens, Civil Lines', 'Kanpur', 'Uttar Pradesh', '208001'),
    ('Royal Orchards, South Tukoganj', 'Indore', 'Madhya Pradesh', '452001'),
    ('Sapphire Springs, Kharghar', 'Navi Mumbai', 'Maharashtra', '410210'),
    ('Elegant Enclave, Arera Hills', 'Bhopal', 'Madhya Pradesh', '462011'),
    ('Tranquil Towers, Sharanpur Road', 'Nashik', 'Maharashtra', '422005'),
    ('Summit Residences, Kalawad Road', 'Rajkot', 'Gujarat', '360005'),
    ('Pearl Gardens, Ranjit Avenue', 'Amritsar', 'Punjab', '143001'),
    ('Vista Homes, Hazratbal', 'Srinagar', 'Jammu and Kashmir', '190006'),
    ('Grand View Apartments, Sector 16', 'Faridabad', 'Haryana', '121002'),
    ('Regency Residency, Begum Bridge', 'Meerut', 'Uttar Pradesh', '250001'),
    ('Silver Oaks, Khadakpada', 'Kalyan', 'Maharashtra', '421301'),
    ('Sunflower Heights, Vasai East', 'Vasai', 'Maharashtra', '401208'),
    ('Harmony Homes, Kothrud', 'Pune', 'Maharashtra', '411038'),
    ('Lavish Gardens, Aliganj', 'Lucknow', 'Uttar Pradesh', '226024'),
    ('Sunrise Towers, Malleswaram', 'Bangalore', 'Karnataka', '560003'),
    ('Marigold Residency, Bandra West', 'Mumbai', 'Maharashtra', '400050'),
    ('Elite Apartments, Lajpat Nagar', 'Delhi', 'Delhi', '110024'),
    ('Grand View Heights, New Town', 'Kolkata', 'West Bengal', '700156'),
    ('Ivory Towers, Velachery', 'Chennai', 'Tamil Nadu', '600042'),
    ('Emerald Springs, Kukatpally', 'Hyderabad', 'Telangana', '500072'),
    ('Golden Meadows, Kharadi', 'Pune', 'Maharashtra', '411014'),
    ('Skyline Residences, Maninagar', 'Ahmedabad', 'Gujarat', '380008'),
    ('Sapphire Plaza, Katargam', 'Surat', 'Gujarat', '395004'),
    ('Green Haven, Tonk Road', 'Jaipur', 'Rajasthan', '302018'),
    ('Paradise Residency, Gomti Nagar', 'Lucknow', 'Uttar Pradesh', '226010'),
    ('Royal Gardens, Civil Lines', 'Kanpur', 'Uttar Pradesh', '208001'),
    ('Moonlight Towers, South Tukoganj', 'Indore', 'Madhya Pradesh', '452001'),
    ('Coral Springs, Kharghar', 'Navi Mumbai', 'Maharashtra', '410210'),
    ('Golden Woods, Arera Hills', 'Bhopal', 'Madhya Pradesh', '462011'),
    ('Serenity Homes, Sharanpur Road', 'Nashik', 'Maharashtra', '422005'),
    ('Sunset Residency, Kalawad Road', 'Rajkot', 'Gujarat', '360005'),
    ('Silver Bells, Ranjit Avenue', 'Amritsar', 'Punjab', '143001'),
    ('Majestic Heights, Hazratbal', 'Srinagar', 'Jammu and Kashmir', '190006'),
    ('Regal Towers, Sector 16', 'Faridabad', 'Haryana', '121002'),
    ('Blossom Residency, Begum Bridge', 'Meerut', 'Uttar Pradesh', '250001'),
    ('Green Valley Homes, Khadakpada', 'Kalyan', 'Maharashtra', '421301'),
    ('Sunset Enclave, Vasai East', 'Vasai', 'Maharashtra', '401208'),
    ('Harmony Residences, Kothrud', 'Pune', 'Maharashtra', '411038'),
    ('Platinum Gardens, Aliganj', 'Lucknow', 'Uttar Pradesh', '226024'),
    ('Palm Grove Towers, Malleswaram', 'Bangalore', 'Karnataka', '560003'),
    ('Ocean View Residency, Bandra West', 'Mumbai', 'Maharashtra', '400050'),
    ('Evergreen Apartments, Lajpat Nagar', 'Delhi', 'Delhi', '110024'),
    ('Regency Heights, New Town', 'Kolkata', 'West Bengal', '700156'),
    ('Ivory Residences, Velachery', 'Chennai', 'Tamil Nadu', '600042'),
    ('Silver Springs, Kukatpally', 'Hyderabad', 'Telangana', '500072'),
    ('Golden Orchid Residency, Kharadi', 'Pune', 'Maharashtra', '411014'),
    ('Crystal Heights, Maninagar', 'Ahmedabad', 'Gujarat', '380008'),
    ('Sapphire Gardens, Katargam', 'Surat', 'Gujarat', '395004'),
    ('Emerald Enclave, Tonk Road', 'Jaipur', 'Rajasthan', '302018'),
    ('Crown Plaza Residences, Gomti Nagar', 'Lucknow', 'Uttar Pradesh', '226010'),
    ('Paradise Towers, Civil Lines', 'Kanpur', 'Uttar Pradesh', '208001'),
    ('Moonlight Residency, South Tukoganj', 'Indore', 'Madhya Pradesh', '452001'),
    ('Coral Gardens, Kharghar', 'Navi Mumbai', 'Maharashtra', '410210'),
    ('Silk Meadows, Arera Hills', 'Bhopal', 'Madhya Pradesh', '462011'),
    ('Serenity Homes, Sharanpur Road', 'Nashik', 'Maharashtra', '422005'),
    ('Sunset Residences, Kalawad Road', 'Rajkot', 'Gujarat', '360005'),
    ('Silver Bells, Ranjit Avenue', 'Amritsar', 'Punjab', '143001'),
    ('Majestic Heights, Hazratbal', 'Srinagar', 'Jammu and Kashmir', '190006'),
    ('Regal Towers, Sector 16', 'Faridabad', 'Haryana', '121002'),
    ('Blossom Residency, Begum Bridge', 'Meerut', 'Uttar Pradesh', '250001'),
    ('Green Valley Homes, Khadakpada', 'Kalyan', 'Maharashtra', '421301'),
    ('Sunset Enclave, Vasai East', 'Vasai', 'Maharashtra', '401208'),
    ('Harmony Residences, Kothrud', 'Pune', 'Maharashtra', '411038'),
    ('Platinum Gardens, Aliganj', 'Lucknow', 'Uttar Pradesh', '226024'),
    ('Sunset Towers, Malleswaram', 'Bangalore', 'Karnataka', '560003'),
    ('Green Valley Residency, Bandra West', 'Mumbai', 'Maharashtra', '400050'),
    ('Elite Heights, Lajpat Nagar', 'Delhi', 'Delhi', '110024'),
    ('Sapphire Residences, New Town', 'Kolkata', 'West Bengal', '700156'),
    ('Pearl Springs, Velachery', 'Chennai', 'Tamil Nadu', '600042'),
    ('Royal Plaza, Kukatpally', 'Hyderabad', 'Telangana', '500072'),
    ('Diamond Meadows, Kharadi', 'Pune', 'Maharashtra', '411014'),
    ('Crystal Gardens, Maninagar', 'Ahmedabad', 'Gujarat', '380008'),
    ('Golden Heights, Katargam', 'Surat', 'Gujarat', '395004'),
    ('Ivory Towers, Tonk Road', 'Jaipur', 'Rajasthan', '302018'),
    ('Pristine Residency, Gomti Nagar', 'Lucknow', 'Uttar Pradesh', '226010'),
    ('Regency Residences, Civil Lines', 'Kanpur', 'Uttar Pradesh', '208001'),
    ('Sunshine Towers, South Tukoganj', 'Indore', 'Madhya Pradesh', '452001'),
    ('Diamond Residency, Kharghar', 'Navi Mumbai', 'Maharashtra', '410210'),
    ('Emerald Springs, Arera Hills', 'Bhopal', 'Madhya Pradesh', '462011'),
    ('Palm Grove Homes, Sharanpur Road', 'Nashik', 'Maharashtra', '422005'),
    ('Majestic Residences, Kalawad Road', 'Rajkot', 'Gujarat', '360005'),
    ('Silver Bells, Ranjit Avenue', 'Amritsar', 'Punjab', '143001'),
    ('Vista Homes, Hazratbal', 'Srinagar', 'Jammu and Kashmir', '190006'),
    ('Royal Heights, Sector 16', 'Faridabad', 'Haryana', '121002'),
    ('Crimson Residency, Begum Bridge', 'Meerut', 'Uttar Pradesh', '250001'),
    ('Meadow View Apartments, Khadakpada', 'Kalyan', 'Maharashtra', '421301'),
    ('Sunflower Enclave, Vasai East', 'Vasai', 'Maharashtra', '401208'),
    ('Harmony Towers, Kothrud', 'Pune', 'Maharashtra', '411038'),
    ('Platinum Residency, Aliganj', 'Lucknow', 'Uttar Pradesh', '226024'),
    ('Elegance Residency, Malleswaram', 'Bangalore', 'Karnataka', '560003'),
    ('Sapphire Gardens, Bandra West', 'Mumbai', 'Maharashtra', '400050'),
    ('Royal Residences, Lajpat Nagar', 'Delhi', 'Delhi', '110024'),
    ('Crystal Springs, New Town', 'Kolkata', 'West Bengal', '700156'),
    ('Palm Grove Apartments, Velachery', 'Chennai', 'Tamil Nadu', '600042'),
    ('Emerald Enclave, Kukatpally', 'Hyderabad', 'Telangana', '500072'),
    ('Golden Gate Towers, Kharadi', 'Pune', 'Maharashtra', '411014'),
    ('Serene Homes, Maninagar', 'Ahmedabad', 'Gujarat', '380008'),
    ('Sapphire Plaza Residency, Katargam', 'Surat', 'Gujarat', '395004'),
    ('Crimson Heights, Tonk Road', 'Jaipur', 'Rajasthan', '302018'),
    ('Royal View Residency, Gomti Nagar', 'Lucknow', 'Uttar Pradesh', '226010'),
    ('Diamond Plaza Apartments, Civil Lines', 'Kanpur', 'Uttar Pradesh', '208001'),
    ('Silver Meadows, South Tukoganj', 'Indore', 'Madhya Pradesh', '452001'),
    ('Sunflower Residency, Kharghar', 'Navi Mumbai', 'Maharashtra', '410210'),
    ('Platinum Springs, Arera Hills', 'Bhopal', 'Madhya Pradesh', '462011'),
    ('Coral Gardens, Sharanpur Road', 'Nashik', 'Maharashtra', '422005'),
    ('Sapphire Towers, Kalawad Road', 'Rajkot', 'Gujarat', '360005'),
    ('Emerald Bells, Ranjit Avenue', 'Amritsar', 'Punjab', '143001'),
    ('Pristine Heights, Hazratbal', 'Srinagar', 'Jammu and Kashmir', '190006'),
    ('Pearl Residences, Sector 16', 'Faridabad', 'Haryana', '121002'),
    ('Golden Grove Towers, Begum Bridge', 'Meerut', 'Uttar Pradesh', '250001'),
    ('Majestic Gardens, Khadakpada', 'Kalyan', 'Maharashtra', '421301'),
    ('Skyline Residency, Vasai East', 'Vasai', 'Maharashtra', '401208'),
    ('Ivory Homes, Kothrud', 'Pune', 'Maharashtra', '411038'),
    ('Harmony Heights, Aliganj', 'Lucknow', 'Uttar Pradesh', '226024'),
    ('Regency Residences, Malleswaram', 'Bangalore', 'Karnataka', '560003'),
    ('Green Park Towers, Bandra West', 'Mumbai', 'Maharashtra', '400050'),
    ('Crimson Residency, Lajpat Nagar', 'Delhi', 'Delhi', '110024'),
    ('Silver Springs, New Town', 'Kolkata', 'West Bengal', '700156'),
    ('Emerald Gardens, Velachery', 'Chennai', 'Tamil Nadu', '600042'),
    ('Golden View Residency, Kukatpally', 'Hyderabad', 'Telangana', '500072'),
    ('Majestic Residences, Kharadi', 'Pune', 'Maharashtra', '411014'),
    ('Diamond Plaza, Maninagar', 'Ahmedabad', 'Gujarat', '380008'),
    ('Palm Grove Heights, Katargam', 'Surat', 'Gujarat', '395004'),
    ('Sapphire Meadows, Tonk Road', 'Jaipur', 'Rajasthan', '302018'),
    ('Royal Springs Residency, Gomti Nagar', 'Lucknow', 'Uttar Pradesh', '226010'),
    ('Crystal Gardens, Civil Lines', 'Kanpur', 'Uttar Pradesh', '208001'),
    ('Sunset Plaza Residences, South Tukoganj', 'Indore', 'Madhya Pradesh', '452001'),
    ('Diamond Enclave, Kharghar', 'Navi Mumbai', 'Maharashtra', '410210'),
    ('Platinum Heights, Arera Hills', 'Bhopal', 'Madhya Pradesh', '462011'),
    ('Serenity Homes, Sharanpur Road', 'Nashik', 'Maharashtra', '422005'),
    ('Sunrise Residency, Kalawad Road', 'Rajkot', 'Gujarat', '360005'),
    ('Ivory Springs, Ranjit Avenue', 'Amritsar', 'Punjab', '143001'),
    ('Golden Heights, Hazratbal', 'Srinagar', 'Jammu and Kashmir', '190006'),
    ('Majestic Towers, Sector 16', 'Faridabad', 'Haryana', '121002'),
    ('Coral Residences, Begum Bridge', 'Meerut', 'Uttar Pradesh', '250001'),
    ('Royal Plaza Apartments, Khadakpada', 'Kalyan', 'Maharashtra', '421301'),
    ('Skyline Gardens, Vasai East', 'Vasai', 'Maharashtra', '401208'),
    ('Harmony Residency, Kothrud', 'Pune', 'Maharashtra', '411038'),
    ('Platinum Towers, Aliganj', 'Lucknow', 'Uttar Pradesh', '226024');


INSERT INTO agencies (name, contact_person_name, email, phone) VALUES
    ('Indian Travels', 'Rahul Sharma', 'rahul@indiantravels.com', '9876543210'),
    ('Royal Tours', 'Priya Singh', 'priya@royaltours.in', '8765432109'),
    ('Sai Holidays', 'Vikram Patel', 'vikram@saiholidays.co.in', '7654321098'),
    ('Mumbai Explorer', 'Neha Shah', 'neha@mumbaiexplorer.com', '6543210987'),
    ('Golden Chariots', 'Rajiv Verma', 'rajiv@goldenchariots.in', '5432109876'),
    ('Southern Wings', 'Ananya Reddy', 'ananya@southernwings.co.in', '4321098765'),
    ('Green Routes', 'Arjun Kumar', 'arjun@greenroutes.com', '3210987654'),
    ('Unity Travels', 'Preeti Gupta', 'preeti@unitytravels.in', '2109876543'),
    ('Eternal Journeys', 'Vishal Jain', 'vishal@eternaljourneys.co.in', '1098765432'),
    ('Sunrise Tours', 'Aisha Khan', 'aisha@sunrisetours.com', '9876543210');


-- Offices for Indian Travels

INSERT INTO agency_offices (agency_id, office_mail, office_contact_person_name, office_contact_number, office_address_id) VALUES
    (1, 'info-mumbai@indiantravels.com', 'Rakesh Kumar', '9876543211', 2),
    (1, 'info-pune@indiantravels.com', 'Sneha Singh', '9876543212', 7),
    (1, 'info-delhi@indiantravels.com', 'Amit Gupta', '9876543213', 3),
    (1, 'info-nashik@indiantravels.com', 'Neha Patel', '9876543214', 16),
    (1, 'info-banglore@indiantravels.com', 'Rajat Sharma', '9876543215', 1);


    
-- Offices for Royal Tours
INSERT INTO agency_offices (agency_id, office_mail, office_contact_person_name, office_contact_number, office_address_id) VALUES
    (2, 'info-mumbai@royaltours.in', 'Amit Joshi', '8765432101', 27),
    (2, 'info-pune@royaltours.in', 'Riya Malhotra', '8765432102', 24),
    (2, 'info-delhi@royaltours.in', 'Prakash Singh', '8765432103', 28),
    (2, 'info-surat@royaltours.in', 'Aishwarya Verma', '8765432104', 9),
    (2, 'info-hyderabad@royaltours.in', 'Rahul Khanna', '8765432105', 6);


-- Office records for Sai Holidays
INSERT INTO agency_offices (agency_id, office_mail, office_contact_person_name, office_contact_number, office_address_id) VALUES
    (3, 'info-kolkatta@saiholidays.com', 'Vikram Patel', '7654321098', 4),
    (3, 'info-chennai@saiholidays.com', 'Anita Desai', '7654321099', 5),
    (3, 'info-ahmedabad@saiholidays.com', 'Rahul Verma', '7654321100', 8),
    (3, 'info-rajkot@saiholidays.com', 'Preeti Shah', '7654321101', 17),
    (3, 'info-bangalore@saiholidays.com', 'Arun Kumar', '7654321102', 26);




-- buses for Indian Trvels
-- office 1
INSERT INTO buses (office_id, registration_number, capacity, type) VALUES
    -- Office 1  location - Mumbai
    (1, 'MH01AB1234', 50, 'Seater'),
    (1, 'MH01AC5678', 40, 'AC Seater'),
    (1, 'MH01AD9101', 30, 'Sleeper'),
    (1, 'MH01AE1122', 25, 'AC Sleeper'),
    (1, 'MH01AF3344', 35, 'Semi-Sleeper'),

 -- Office 2 location - Pune
    (2, 'MH03AB1234', 50, 'Seater'),
    (2, 'MH03AC5678', 40, 'AC Seater'),
    (2, 'MH03AD9101', 30, 'Sleeper'),
    (2, 'MH03AE1122', 25, 'AC Sleeper'),
    (2, 'MH03AF3344', 35, 'Semi-Sleeper'),

-- Office 3  location - Delhi
    (3, 'DL02AB1234', 50, 'Seater'),
    (3, 'DL02AC5678', 40, 'AC Seater'),
    (3, 'DL02AD9101', 30, 'Sleeper'),
    (3, 'DL02AE1122', 25, 'AC Sleeper'),
    (3, 'DL02AF3344', 35, 'Semi-Sleeper'),
    
-- Office 4 location - Nashik
    (4, 'MH04AB1234', 50, 'Seater'),
    (4, 'MH04AC5678', 40, 'AC Seater'),
    (4, 'MH04AD9101', 30, 'Sleeper'),
    (4, 'MH04AE1122', 25, 'AC Sleeper'),
    (4, 'MH04AF3344', 35, 'Semi-Sleeper'),

-- Office 5 location - 
    (5, 'KA05AB1234', 50, 'Seater'),
    (5, 'KA05AC5678', 40, 'AC Seater'),
    (5, 'KA05AD9101', 30, 'Sleeper'),
    (5, 'KA05AE1122', 25, 'AC Sleeper'),
    (5, 'KA05AF3344', 35, 'Semi-Sleeper');



-- buses for Royal Tours
-- office 1 location Mumbai
INSERT INTO buses (office_id, registration_number, capacity, type) VALUES
    -- Office 1  location - Mumbai
    (6, 'MH06AB1234', 50, 'Seater'),
    (6, 'MH06AC5678', 40, 'AC Seater'),
    (6, 'MH06AD9101', 30, 'Sleeper'),
    (6, 'MH06AE1122', 25, 'AC Sleeper'),
    (6, 'MH06AF3344', 35, 'Semi-Sleeper'),

 -- Office 2 location - Pune
    (7, 'MH07AB1234', 50, 'Seater'),
    (7, 'MH07AC5678', 40, 'AC Seater'),
    (7, 'MH07AD9101', 30, 'Sleeper'),
    (7, 'MH07AE1122', 25, 'AC Sleeper'),
    (7, 'MH07AF3344', 35, 'Semi-Sleeper'),

-- Office 8  location - Delhi
    (8, 'DL08AB1234', 50, 'Seater'),
    (8, 'DL08AC5678', 40, 'AC Seater'),
    (8, 'DL08AD9101', 30, 'Sleeper'),
    (8, 'DL08AE1122', 25, 'AC Sleeper'),
    (8, 'DL08AF3344', 35, 'Semi-Sleeper'),
    
-- Office 4 location - Surat
    (9, 'GJ09AB1234', 50, 'Seater'),
    (9, 'GJ09AC5678', 40, 'AC Seater'),
    (9, 'GJ09AD9101', 30, 'Sleeper'),
    (9, 'GJ09AE1122', 25, 'AC Sleeper'),
    (9, 'GJ09AF3344', 35, 'Semi-Sleeper'),

-- Office 5 location - Hyderabad
    (10, 'TL10AB1234', 50, 'Seater'),
    (10, 'TL10AC5678', 40, 'AC Seater'),
    (10, 'TL10AD9101', 30, 'Sleeper'),
    (10, 'TL10AE1122', 25, 'AC Sleeper'),
    (10, 'TL10AF3344', 35, 'Semi-Sleeper');


-- buses for Royal Tours
-- office 1 location Kolkatta
INSERT INTO buses (office_id, registration_number, capacity, type) VALUES
    -- Office 1  location - Kolkatta
    (11, 'WB11AB1234', 50, 'Seater'),
    (11, 'WB11AC5678', 40, 'AC Seater'),
    (11, 'WB11AD9101', 30, 'Sleeper'),
    (11, 'WB11AE1122', 25, 'AC Sleeper'),
    (11, 'WB11AF3344', 35, 'Semi-Sleeper'),

 -- Office 2 location - Chennai
    (12, 'TN01AB1234', 50, 'Seater'),
    (12, 'TN01AC5678', 40, 'AC Seater'),
    (12, 'TN01AD9101', 30, 'Sleeper'),
    (12, 'TN01AE1122', 25, 'AC Sleeper'),
    (12, 'TN01AF3344', 35, 'Semi-Sleeper'),

-- Office 3  location - Ahmedabad
    (13, 'GJ13AB1234', 50, 'Seater'),
    (13, 'GJ13AC5678', 40, 'AC Seater'),
    (13, 'GJ13AD9101', 30, 'Sleeper'),
    (13, 'GJ13AE1122', 25, 'AC Sleeper'),
    (13, 'GJ13AF3344', 35, 'Semi-Sleeper'),
    
-- Office 4 location - Rajkot
    (14, 'RJ149AB1234', 50, 'Seater'),
    (14, 'RJ14AC5678', 40, 'AC Seater'),
    (14, 'RJ14AD9101', 30, 'Sleeper'),
    (14, 'RJ14AE1122', 25, 'AC Sleeper'),
    (14, 'RJ14AF3344', 35, 'Semi-Sleeper'),

-- Office 5 location - Banglore
    (15, 'KA15AB1234', 50, 'Seater'),
    (15, 'KA15AC5678', 40, 'AC Seater'),
    (15, 'KA15AD9101', 30, 'Sleeper'),
    (15, 'KA15AE1122', 25, 'AC Sleeper'),
    (15, 'KA15AF3344', 35, 'Semi-Sleeper');
    
---------------------------

-- Drivers data
-- Drivers for Indian Travels 
-- office 1 location - Mumbai
INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL123456', 'Raj Kumar', '9876543210', 1, 2),
	('DL654321', 'Amit Sharma', '8765432109', 1, 7),
	('DL987654', 'Priya Singh', '7654321098', 1, 14),
	('DL567890', 'Suresh Verma', '6543210987', 1, 16),
	('DL234567', 'Anita Yadav', '5432109876', 1, 22),
	('DL876543', 'Vikram Patel', '4321098765', 1, 23),
	('DL345678', 'Deepak Gupta', '3210987654', 1, 24),
	('DL901234', 'Neha Kapoor', '2109876543', 1, 27),
	('DL432109', 'Rakesh Kumar', '1098765432', 1, 32);
    

INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL109876', 'Aishwarya Mishra', '9876543210', 2, 39),
	('DL678901', 'Sanjay Jain', '8765432109', 2, 41),
	('DL345678', 'Meera Sharma', '7654321098', 2, 47),
	('DL987654', 'Rahul Verma', '6543210987', 2, 48),
	('DL567890', 'Pooja Yadav', '5432109876', 2, 49),
	('DL234567', 'Amit Kumar', '4321098765', 2, 52);
    
INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL987654', 'Rohit Kumar', '8765432109', 3, 3),
	('DL567890', 'Neha Gupta', '7654321098', 3, 28),
	('DL234567', 'Amit Singh', '6543210987', 3, 53),
	('DL876543', 'Pooja Verma', '5432109876', 3, 78),
	('DL901234', 'Vikas Sharma', '4321098765', 3, 103),
	('DL432109', 'Suman Yadav', '3210987654', 3, 128),
	('DL109876', 'Amita Jain', '2109876543', 3, 153);

INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL901234', 'Rohit Verma', '2109876543', 4, 47),
	('DL432109', 'Sneha Yadav', '1098765432', 4, 48),
	('DL109876', 'Alok Kumar', '9876543210', 4, 49),
	('DL678901', 'Anita Singh', '8765432109', 4, 52),
	('DL345678', 'Vinay Jain', '7654321098', 4, 57),
	('DL987654', 'Sapna Patel', '6543210987', 4, 64),
	('DL567890', 'Kunal Verma', '5432109876', 4, 66),
	('DL234567', 'Pooja Yadav', '4321098765', 4, 72);

INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL901234', 'Rahul Sharma', '2109876543', 5, 1),
	('DL432109', 'Aisha Khan', '1098765432', 5, 26),
	('DL109876', 'Rajeev Singh', '9876543210', 5, 51),
	('DL678901', 'Neha Gupta', '8765432109', 5, 76),
	('DL345678', 'Amit Patel', '7654321098', 5, 101),
	('DL987654', 'Sara Verma', '6543210987', 5, 126),
	('DL567890', 'Vikram Yadav', '5432109876', 5, 151);

-- Drivers data
-- Drivers for Royal Tours
INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL123456', 'Priya Sharma', '2109876543', 6, 2),
	('DL654321', 'Vishal Yadav', '1098765432', 6, 7),
	('DL210987', 'Nisha Verma', '9876543210', 6, 14),
	('DL876543', 'Rajat Singh', '8765432109', 6, 16),
	('DL543210', 'Deepa Patel', '7654321098', 6, 22),
	('DL210987', 'Arun Kumar', '6543210987', 6, 23),
	('DL876543', 'Sonam Gupta', '5432109876', 6, 24),
	('DL543210', 'Alok Yadav', '4321098765', 6, 27);

INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL123456', 'Amit Kumar', '9876543210', 7, 2),
	('DL654321', 'Kavita Yadav', '8765432109', 7, 7),
	('DL987123', 'Vivek Singh', '7654321098', 7, 14),
	('DL321987', 'Neha Patel', '6543210987', 7, 16),
	('DL456789', 'Raj Verma', '5432109876', 7, 22),
	('DL789012', 'Anjali Yadav', '4321098765', 7, 23),
	('DL210987', 'Rahul Kumar', '3210987654', 7, 24),
	('DL543210', 'Poonam Singh', '2109876543', 7, 27);
INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL111111', 'Vikram Sharma', '9876543210', 8, 3),
	('DL222222', 'Priya Gupta', '8765432109', 8, 28),
	('DL333333', 'Rahul Singh', '7654321098', 8, 53),
	('DL444444', 'Sanya Verma', '6543210987', 8, 78),
	('DL555555', 'Rohan Patel', '5432109876', 8, 103),
	('DL666666', 'Aishwarya Yadav', '4321098765', 8, 128),
	('DL777777', 'Arjun Kumar', '3210987654', 8, 153);
    
INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL888888', 'Manoj Kumar', '9876543210', 9, 8),
	('DL999999', 'Amita Verma', '8765432109', 9, 9),
	('DL101010', 'Rahul Sharma', '7654321098', 9, 17),
	('DL111111', 'Ananya Gupta', '6543210987', 9, 33),
	('DL121212', 'Kunal Singh', '5432109876', 9, 34),
	('DL131313', 'Sonia Verma', '4321098765', 9, 42),
	('DL141414', 'Ravi Patel', '3210987654', 9, 58),
	('DL151515', 'Neha Yadav', '2109876543', 9, 59);

INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL303030', 'Ankit Yadav', '9876543210', 10, 6),
	('DL313131', 'Sakshi Sharma', '8765432109', 10, 31),
	('DL323232', 'Amit Verma', '7654321098', 10, 56),
	('DL333333', 'Poonam Patel', '6543210987', 10, 81),
	('DL343434', 'Rohit Kumar', '5432109876', 10, 106),
	('DL353535', 'Neha Singh', '4321098765', 10, 131),
	('DL363636', 'Arjun Yadav', '3210987654', 10, 156);
    
-- Drivers data
-- Drivers for Sai Holidays    
INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL404040', 'Vikas Kumar', '9876543210', 11, 4),
	('DL414141', 'Riya Verma', '8765432109', 11, 29),
	('DL424242', 'Sandeep Yadav', '7654321098', 11, 54),
	('DL434343', 'Preeti Sharma', '6543210987', 11, 79),
	('DL444444', 'Rahul Patel', '5432109876', 11, 104),
	('DL454545', 'Megha Singh', '4321098765', 11, 129),
	('DL464646', 'Sumit Yadav', '3210987654', 11, 154);

INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL404040', 'Rahul Singh', '9876543210', 12, 5),
	('DL414141', 'Swati Verma', '8765432109', 12, 30),
	('DL424242', 'Alok Kumar', '7654321098', 12, 55),
	('DL434343', 'Kavita Patel', '6543210987', 12, 80),
	('DL444444', 'Amit Verma', '5432109876', 12, 105),
	('DL454545', 'Neha Yadav', '4321098765', 12, 130),
	('DL464646', 'Rohit Patel', '3210987654', 12, 155);
    
INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL161616', 'Raj Kumar', '1098765432', 13, 67),
	('DL171717', 'Pooja Sharma', '9876543210', 13, 83),
	('DL181818', 'Rohit Yadav', '8765432109', 13, 84),
	('DL191919', 'Suman Verma', '7654321098', 13, 92),
	('DL202020', 'Akash Kumar', '6543210987', 13, 108),
	('DL212121', 'Kavita Singh', '5432109876', 13, 109),
	('DL222222', 'Vivek Patel', '4321098765', 13, 117);
    
INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL232323', 'Shreya Gupta', '3210987654', 14, 133),
	('DL242424', 'Aryan Verma', '2109876543', 14, 134),
	('DL252525', 'Ishita Yadav', '1098765432', 14, 142),
	('DL262626', 'Sudhir Kumar', '9876543210', 14, 158),
	('DL272727', 'Sanya Sharma', '8765432109', 14, 159),
	('DL282828', 'Arnav Yadav', '7654321098', 14, 167);
    
INSERT INTO drivers (license_number, name, phone, office_id, address_id) VALUES
	('DL261616', 'Amit Singh', '1098765432', 15, 1),
	('DL271717', 'Neha Gupta', '9876543210', 15, 26),
	('DL381818', 'Rahul Yadav', '8765432109', 15, 51),
	('DL491919', 'Preeti Verma', '7654321098', 15, 76),
	('DL502020', 'Deepak Kumar', '6543210987', 15, 101),
	('DL112121', 'Sonia Singh', '5432109876', 15, 126),
	('DL122222', 'Vikas Patel', '4321098765', 15, 151);


-- Routes for all cities
INSERT INTO routes (from_city, to_city, break_points, duration)
VALUES
    ('Mumbai', 'Pune', 1, 3),   -- Mumbai to Pune
    ('Pune', 'Mumbai', 1, 3),   -- Pune to Mumbai
    ('Mumbai', 'Delhi', 2, 24),  -- Mumbai to Delhi
    ('Delhi', 'Mumbai', 2, 24),  -- Delhi to Mumbai
    ('Mumbai', 'Nashik', 3, 3),  -- Mumbai to Nashik
    ('Nashik', 'Mumbai', 3, 3),  -- Nashik to Mumbai
    ('Mumbai', 'Bangalore', 4, 20),  -- Mumbai to Bangalore
    ('Bangalore', 'Mumbai', 4, 20),  -- Bangalore to Mumbai
    ('Mumbai', 'Chennai', 1, 21),  -- Mumbai to Chennai
    ('Chennai', 'Mumbai', 1, 21),  -- Chennai to Mumbai
    ('Mumbai', 'Hyderabad', 2, 14),  -- Mumbai to Hyderabad
    ('Hyderabad', 'Mumbai', 2, 14),  -- Hyderabad to Mumbai
    ('Mumbai', 'Ahmedabad', 3, 9),  -- Mumbai to Ahmedabad
    ('Ahmedabad', 'Mumbai', 3, 9),  -- Ahmedabad to Mumbai
    ('Mumbai', 'Rajkot', 4, 12),  -- Mumbai to Rajkot
    ('Rajkot', 'Mumbai', 4, 12),  -- Rajkot to Mumbai
    ('Mumbai', 'Surat', 1, 7),  -- Mumbai to Surat
    ('Surat', 'Mumbai', 1, 7),  -- Surat to Mumbai
    ('Mumbai', 'Bangalore', 2, 20),  -- Mumbai to Bangalore
    ('Bangalore', 'Mumbai', 2, 20),  -- Bangalore to Mumbai

    ('Pune', 'Delhi', 1, 24), -- Pune to Delhi
    ('Delhi', 'Pune', 1, 24), -- Delhi to Pune
    ('Pune', 'Surat', 2, 5),  -- Pune to Surat
    ('Surat', 'Pune', 2, 5),  -- Surat to Pune
    ('Pune', 'Hyderabad', 3, 10), -- Pune to Hyderabad
    ('Hyderabad', 'Pune', 3, 10), -- Hyderabad to Pune
    ('Pune', 'Nashik', 2, 3),   -- Pune to Nashik
    ('Nashik', 'Pune', 2, 3),   -- Nashik to Pune
    ('Pune', 'Bangalore', 3, 18),  -- Pune to Bangalore
    ('Bangalore', 'Pune', 3, 18),  -- Bangalore to Pune
    ('Pune', 'Ahmedabad', 4, 9),  -- Pune to Ahmedabad
    ('Ahmedabad', 'Pune', 4, 9),  -- Ahmedabad to Pune
    ('Pune', 'Rajkot', 1, 11),  -- Pune to Rajkot
    ('Rajkot', 'Pune', 1, 11),  -- Rajkot to Pune
    ('Pune', 'Surat', 2, 5),  -- Pune to Surat
    ('Surat', 'Pune', 2, 5),  -- Surat to Pune
    ('Pune', 'Bangalore', 3, 18),  -- Pune to Bangalore
    ('Bangalore', 'Pune', 3, 18),  -- Bangalore to Pune

    ('Chennai', 'Ahmedabad', 1, 18),  -- Chennai to Ahmedabad
    ('Ahmedabad', 'Chennai', 1, 18),  -- Ahmedabad to Chennai
    ('Chennai', 'Rajkot', 2, 18), -- Chennai to Rajkot
    ('Rajkot', 'Chennai', 2, 18), -- Rajkot to Chennai
    ('Chennai', 'Bangalore', 3, 6),  -- Chennai to Bangalore
    ('Bangalore', 'Chennai', 3, 6),  -- Bangalore to Chennai
    ('Chennai', 'Hyderabad', 1, 12),  -- Chennai to Hyderabad
    ('Hyderabad', 'Chennai', 1, 12),  -- Hyderabad to Chennai
    ('Chennai', 'Delhi', 2, 24),  -- Chennai to Delhi
    ('Delhi', 'Chennai', 2, 24),  -- Delhi to Chennai
    ('Chennai', 'Surat', 3, 16),  -- Chennai to Surat
    ('Surat', 'Chennai', 3, 16);  -- Surat to Chennai



--------------------------------------------
-- Insert More Customer Records with Indian Names and Unique Address IDs from 1 to 175
INSERT INTO customers (name, email, phone, address_id)
VALUES
    ('Aarohi Sharma', 'aarohi.sharma@example.com', '8765432101', 35),
    ('Ayaan Singh', 'ayaan.singh@example.com', '6543210982', 88),
    ('Anvi Verma', 'anvi.verma@example.com', '2345678903', 124),
    ('Advik Patel', 'advik.patel@example.com', '7890123454', 67),
    ('Arisha Sharma', 'arisha.sharma@example.com', '9876543216', 134),
    ('Vihaan Gupta', 'vihaan.gupta@example.com', '3456789018', 169),
    ('Aisha Kapoor', 'aisha.kapoor@example.com', '8901234569', 76),
    ('Rian Joshi', 'rian.joshi@example.com', '5678901230', 54),
    ('Aarav Malhotra', 'aarav.malhotra@example.com', '1234509871', 109),
    ('Zara Khanna', 'zara.khanna@example.com', '4321098762', 155),
    ('Arnav Gupta', 'arnav.gupta@example.com', '8765432101', 22),
    ('Anika Sharma', 'anika.sharma@example.com', '6543210982', 120),
    ('Advay Verma', 'advay.verma@example.com', '2345678903', 73),
    ('Ananya Patel', 'ananya.patel@example.com', '7890123454', 162),
    ('Vihaan Sharma', 'vihaan.sharma@example.com', '9876543216', 41),
    ('Riya Gupta', 'riya.gupta@example.com', '3456789018', 95),
    ('Aryan Kapoor', 'aryan.kapoor@example.com', '8901234569', 10),
    ('Ishita Joshi', 'ishita.joshi@example.com', '5678901230', 139),
    ('Aadi Malhotra', 'aadi.malhotra@example.com', '1234509871', 58),
    ('Sia Khanna', 'sia.khanna@example.com', '4321098762', 145),
    ('Aanya Singh', 'aanya.singh@example.com', '8765432101', 80),
    ('Aarav Verma', 'aarav.verma@example.com', '6543210982', 150),
    ('Anvi Patel', 'anvi.patel@example.com', '2345678903', 112),
    ('Advik Sharma', 'advik.sharma@example.com', '7890123454', 20),
    ('Arisha Kapoor', 'arisha.kapoor@example.com', '9876543216', 158),
    ('Vihaan Gupta', 'vihaan.gupta@example.com', '3456789018', 70),
    ('Aisha Joshi', 'aisha.joshi@example.com', '8901234569', 135),
    ('Rian Malhotra', 'rian.malhotra@example.com', '5678901230', 46),
    ('Aarohi Khanna', 'aarohi.khanna@example.com', '1234509871', 104),
    ('Zara Sharma', 'zara.sharma@example.com', '4321098762', 165),
    ('Arjun Sharma', 'arjun.sharma@example.com', '8765432101', 96),
    ('Ananya Gupta', 'ananya.gupta@example.com', '6543210982', 132),
    ('Advait Verma', 'advait.verma@example.com', '2345678903', 43),
    ('Anika Patel', 'anika.patel@example.com', '7890123454', 157),
    ('Vihaan Sharma', 'vihaan.sharma@example.com', '9876543216', 64),
    ('Riya Gupta', 'riya.gupta@example.com', '3456789018', 114),
    ('Aryan Kapoor', 'aryan.kapoor@example.com', '8901234569', 27),
    ('Ishita Joshi', 'ishita.joshi@example.com', '5678901230', 143),
    ('Aadi Malhotra', 'aadi.malhotra@example.com', '1234509871', 75),
    ('Sia Khanna', 'sia.khanna@example.com', '4321098762', 160),
    ('Arjun Sharma', 'arjun.sharma@example.com', '8765432101', 96),
    ('Ananya Gupta', 'ananya.gupta@example.com', '6543210982', 132),
    ('Advait Verma', 'advait.verma@example.com', '2345678903', 43),
    ('Anika Patel', 'anika.patel@example.com', '7890123454', 157),
    ('Vihaan Sharma', 'vihaan.sharma@example.com', '9876543216', 64),
    ('Riya Gupta', 'riya.gupta@example.com', '3456789018', 114),
    ('Aryan Kapoor', 'aryan.kapoor@example.com', '8901234569', 27),
    ('Ishita Joshi', 'ishita.joshi@example.com', '5678901230', 143),
    ('Aadi Malhotra', 'aadi.malhotra@example.com', '1234509871', 75),
    ('Sia Khanna', 'sia.khanna@example.com', '4321098762', 160),
    ('Aarav Sharma', 'aarav.sharma@example.com', '8765432101', 82),
    ('Zara Singh', 'zara.singh@example.com', '6543210982', 149),
    ('Advait Verma', 'advait.verma@example.com', '2345678903', 117),
    ('Anika Patel', 'anika.patel@example.com', '7890123454', 46),
    ('Vihaan Sharma', 'vihaan.sharma@example.com', '9876543216', 158),
    ('Riya Gupta', 'riya.gupta@example.com', '3456789018', 72),
    ('Aryan Kapoor', 'aryan.kapoor@example.com', '8901234569', 145),
    ('Ishita Joshi', 'ishita.joshi@example.com', '5678901230', 29),
    ('Aadi Malhotra', 'aadi.malhotra@example.com', '1234509871', 102),
    ('Sia Khanna', 'sia.khanna@example.com', '4321098762', 166),
    ('Aarav Sharma', 'aarav.sharma@example.com', '8765432101', 82),
    ('Zara Singh', 'zara.singh@example.com', '6543210982', 149),
    ('Advait Verma', 'advait.verma@example.com', '2345678903', 117),
    ('Anika Patel', 'anika.patel@example.com', '7890123454', 46),
    ('Vihaan Sharma', 'vihaan.sharma@example.com', '9876543216', 158),
    ('Riya Gupta', 'riya.gupta@example.com', '3456789018', 72),
    ('Aryan Kapoor', 'aryan.kapoor@example.com', '8901234569', 145),
    ('Ishita Joshi', 'ishita.joshi@example.com', '5678901230', 29),
    ('Aadi Malhotra', 'aadi.malhotra@example.com', '1234509871', 102),
    ('Sia Khanna', 'sia.khanna@example.com', '4321098762', 166),
    ('Aarohi Sharma', 'aarohi.sharma@example.com', '8765432101', 72),
    ('Ayaan Singh', 'ayaan.singh@example.com', '6543210982', 149),
    ('Anvi Verma', 'anvi.verma@example.com', '2345678903', 117),
    ('Advik Patel', 'advik.patel@example.com', '7890123454', 46),
    ('Arisha Sharma', 'arisha.sharma@example.com', '9876543216', 158),
    ('Vihaan Gupta', 'vihaan.gupta@example.com', '3456789018', 82),
    ('Aisha Kapoor', 'aisha.kapoor@example.com', '8901234569', 29),
    ('Rian Joshi', 'rian.joshi@example.com', '5678901230', 145),
    ('Aarav Malhotra', 'aarav.malhotra@example.com', '1234509871', 102),
    ('Zara Khanna', 'zara.khanna@example.com', '4321098762', 166),
    ('Aanya Singh', 'aanya.singh@example.com', '8765432101', 92),
    ('Aarav Verma', 'aarav.verma@example.com', '6543210982', 124),
    ('Anvi Patel', 'anvi.patel@example.com', '2345678903', 58),
    ('Advik Sharma', 'advik.sharma@example.com', '7890123454', 139),
    ('Arisha Kapoor', 'arisha.kapoor@example.com', '9876543216', 35),
    ('Vihaan Gupta', 'vihaan.gupta@example.com', '3456789018', 104),
    ('Aisha Joshi', 'aisha.joshi@example.com', '8901234569', 76),
    ('Rian Malhotra', 'rian.malhotra@example.com', '5678901230', 154),
    ('Aarohi Khanna', 'aarohi.khanna@example.com', '1234509871', 19),
    ('Zara Sharma', 'zara.sharma@example.com', '4321098762', 115),
    ('Aarohi Singh', 'aarohi.singh@example.com', '8765432101', 93),
    ('Ayaan Verma', 'ayaan.verma@example.com', '6543210982', 128),
    ('Anvi Patel', 'anvi.patel@example.com', '2345678903', 55),
    ('Advik Sharma', 'advik.sharma@example.com', '7890123454', 150),
    ('Arisha Kapoor', 'arisha.kapoor@example.com', '9876543216', 84),
    ('Vihaan Gupta', 'vihaan.gupta@example.com', '3456789018', 111),
    ('Aisha Joshi', 'aisha.joshi@example.com', '8901234569', 35),
    ('Rian Malhotra', 'rian.malhotra@example.com', '5678901230', 121),
    ('Aarav Khanna', 'aarav.khanna@example.com', '1234509871', 68),
    ('Zara Sharma', 'zara.sharma@example.com', '4321098762', 156),
    ('Aanya Singh', 'aanya.singh@example.com', '8765432101', 104),
    ('Aarav Verma', 'aarav.verma@example.com', '6543210982', 170),
    ('Anvi Patel', 'anvi.patel@example.com', '2345678903', 58),
    ('Advik Sharma', 'advik.sharma@example.com', '7890123454', 120),
    ('Arisha Kapoor', 'arisha.kapoor@example.com', '9876543216', 84),
    ('Vihaan Gupta', 'vihaan.gupta@example.com', '3456789018', 142),
    ('Aisha Joshi', 'aisha.joshi@example.com', '8901234569', 63),
    ('Rian Malhotra', 'rian.malhotra@example.com', '5678901230', 35),
    ('Aarohi Khanna', 'aarohi.khanna@example.com', '1234509871', 128),
    ('Zara Sharma', 'zara.sharma@example.com', '4321098762', 175),
    ('Aanya Singh', 'aanya.singh@example.com', '8765432101', 62),
    ('Aarav Verma', 'aarav.verma@example.com', '6543210982', 130),
    ('Anvi Patel', 'anvi.patel@example.com', '2345678903', 38),
    ('Advik Sharma', 'advik.sharma@example.com', '7890123454', 148),
    ('Arisha Kapoor', 'arisha.kapoor@example.com', '9876543216', 89),
    ('Vihaan Gupta', 'vihaan.gupta@example.com', '3456789018', 113),
    ('Aisha Joshi', 'aisha.joshi@example.com', '8901234569', 51),
    ('Rian Malhotra', 'rian.malhotra@example.com', '5678901230', 171),
    ('Aarohi Khanna', 'aarohi.khanna@example.com', '1234509871', 22),
    ('Zara Sharma', 'zara.sharma@example.com', '4321098762', 154),
    ('Arnav Sharma', 'arnav.sharma@example.com', '8765432101', 95),
    ('Ananya Singh', 'ananya.singh@example.com', '6543210982', 131),
    ('Advait Verma', 'advait.verma@example.com', '2345678903', 42),
    ('Anika Patel', 'anika.patel@example.com', '7890123454', 156),
    ('Vihaan Sharma', 'vihaan.sharma@example.com', '9876543216', 63),
    ('Riya Gupta', 'riya.gupta@example.com', '3456789018', 113),
    ('Aryan Kapoor', 'aryan.kapoor@example.com', '8901234569', 26),
    ('Ishita Joshi', 'ishita.joshi@example.com', '5678901230', 142),
    ('Aadi Malhotra', 'aadi.malhotra@example.com', '1234509871', 74),
    ('Sia Khanna', 'sia.khanna@example.com', '4321098762', 159),
    ('Arjun Singh', 'arjun.singh@example.com', '8765432101', 83),
    ('Zoya Verma', 'zoya.verma@example.com', '6543210982', 150),
    ('Advay Patel', 'advay.patel@example.com', '2345678903', 118),
    ('Anushka Sharma', 'anushka.sharma@example.com', '7890123454', 47),
    ('Vivaan Kapoor', 'vivaan.kapoor@example.com', '9876543216', 159),
    ('Kiara Gupta', 'kiara.gupta@example.com', '3456789018', 73),
    ('Aarush Joshi', 'aarush.joshi@example.com', '8901234569', 146),
    ('Ishika Malhotra', 'ishika.malhotra@example.com', '5678901230', 30),
    ('Aaryan Khanna', 'aaryan.khanna@example.com', '1234509871', 103),
    ('Sanya Sharma', 'sanya.sharma@example.com', '4321098762', 167),
    ('Reyansh Kapoor', 'reyansh.kapoor@example.com', '9876543210', 65),
    ('Avni Verma', 'avni.verma@example.com', '8765432198', 124),
    ('Arhaan Patel', 'arhaan.patel@example.com', '7654321098', 36),
    ('Aisha Singh', 'aisha.singh@example.com', '6543210987', 97),
    ('Veer Gupta', 'veer.gupta@example.com', '1234567890', 111),
    ('Aanya Kapoor', 'aanya.kapoor@example.com', '8901234567', 31),
    ('Kabir Joshi', 'kabir.joshi@example.com', '3456789012', 134),
    ('Ritvi Malhotra', 'ritvi.malhotra@example.com', '6789012345', 53),
    ('Vihan Khanna', 'vihan.khanna@example.com', '2345678901', 140),
    ('Arya Sharma', 'arya.sharma@example.com', '4567890123', 77),
    ('Zain Verma', 'zain.verma@example.com', '8901234561', 156),
    ('Sara Patel', 'sara.patel@example.com', '7654321098', 112),
    ('Kabir Gupta', 'kabir.gupta@example.com', '6543210987', 67),
    ('Aadhya Kapoor', 'aadhya.kapoor@example.com', '1234567890', 144),
    ('Aariz Joshi', 'aariz.joshi@example.com', '8901234567', 82),
    ('Anvi Malhotra', 'anvi.malhotra@example.com', '3456789012', 26),
    ('Aaryan Khanna', 'aaryan.khanna@example.com', '6789012345', 155),
    ('Ananya Sharma', 'ananya.sharma@example.com', '2345678901', 41),
    ('Arnav Verma', 'arnav.verma@example.com', '4567890123', 148),
    ('Aanya Singh', 'aanya.singh@example.com', '8901234561', 29),
    ('Reyansh Kapoor', 'reyansh.kapoor@example.com', '9876543210', 137),
    ('Avni Verma', 'avni.verma@example.com', '8765432198', 94),
    ('Arhaan Patel', 'arhaan.patel@example.com', '7654321098', 119),
    ('Aisha Singh', 'aisha.singh@example.com', '6543210987', 44),
    ('Veer Gupta', 'veer.gupta@example.com', '1234567890', 158),
    ('Aanya Kapoor', 'aanya.kapoor@example.com', '8901234567', 71),
    ('Kabir Joshi', 'kabir.joshi@example.com', '3456789012', 144),
    ('Ritvi Malhotra', 'ritvi.malhotra@example.com', '6789012345', 62),
    ('Vihan Khanna', 'vihan.khanna@example.com', '2345678901', 133),
    ('Arya Sharma', 'arya.sharma@example.com', '4567890123', 56),
    ('Zain Verma', 'zain.verma@example.com', '8901234561', 169);

	INSERT INTO trips (route_id, bus_id, boarding_address_id, dropping_address_id, departure_time,
 arrival_time, driver1_driver_id, driver2_driver_id, available_seats, fare, trip_date)
VALUES
    (1, 1, 2, 7, '2024-01-15 08:00:00', '2024-01-15 08:30:00', 1, 2, 00, 500.00, '2024-01-15'), 
    (2, 1, 7, 2, '2024-01-15 15:30:00', '2024-01-15 16:00:00', 1, 2, 00, 500.00, '2024-01-15'),  
   
    (3, 2, 2, 7, '2024-01-15 12:00:00', '2024-01-15 12:30:00', 3, 4, 0, 800.00, '2024-01-15'),  
    (4, 2, 7, 2, '2024-01-15 19:00:00', '2024-01-18 19:30:00', 3, 4, 10, 800.00, '2024-01-15'), 
    
    (5, 3, 9, 10, '2024-01-17 18:00:00', '2024-01-17 18:30:00', 5, 6, 30, 900.00, '2024-01-19'),
    (6, 3, 10, 9, '2024-01-18 16:00:00', '2024-01-19 00:00:00', 5, 6, 30, 900.00, '2024-01-19');



--------------------------------------

INSERT INTO bookings (trip_id, seat_number, status)
VALUES
    (1, 1, 'Booked'),
    (1, 2, 'Booked'),
    (1, 3, 'Booked'),
    (1, 4, 'Booked'),
    (1, 5, 'Booked'),
    (1, 6, 'Booked'),
    (1, 7, 'Booked'),
    (1, 8, 'Booked'),
    (1, 9, 'Booked'),
    (1, 10, 'Booked'),
    (1, 11, 'Booked'),
    (1, 12, 'Booked'),
    (1, 13, 'Booked'),
    (1, 14, 'Booked'),
    (1, 15, 'Booked'),
    (1, 16, 'Booked'),
    (1, 17, 'Booked'),
    (1, 18, 'Booked'),
    (1, 19, 'Booked'),
    (1, 20, 'Booked'),
    (1, 21, 'Booked'),
    (1, 22, 'Booked'),
    (1, 23, 'Booked'),
    (1, 24, 'Booked'),
    (1, 25, 'Booked'),
    (1, 26, 'Booked'),
    (1, 27, 'Booked'),
    (1, 28, 'Booked'),
    (1, 29, 'Booked'),
    (1, 30, 'Booked'),
    (1, 31, 'Booked'),
    (1, 32, 'Booked'),
    (1, 33, 'Booked'),
    (1, 34, 'Booked'),
    (1, 35, 'Booked'),
    (1, 36, 'Booked'),
    (1, 37, 'Booked'),
    (1, 38, 'Booked'),
    (1, 39, 'Booked'),
    (1, 40, 'Booked'),
    (1, 41, 'Booked'),
    (1, 42, 'Booked'),
    (1, 43, 'Booked'),
    (1, 44, 'Booked'),
    (1, 45, 'Booked'),
    (1, 46, 'Booked'),
    (1, 47, 'Booked'),
    (1, 48, 'Booked'),
    (1, 49, 'Booked'),
    (1, 50, 'Booked'),
    (2, 1, 'Booked'),
    (2, 2, 'Booked'),
    (2, 3, 'Booked'),
    (2, 4, 'Booked'),
    (2, 5, 'Booked'),
    (2, 6, 'Booked'),
    (2, 7, 'Booked'),
    (2, 8, 'Booked'),
    (2, 9, 'Booked'),
    (2, 10, 'Booked'),
    (2, 11, 'Booked'),
    (2, 12, 'Booked'),
    (2, 13, 'Booked'),
    (2, 14, 'Booked'),
    (2, 15, 'Booked'),
    (2, 16, 'Booked'),
    (2, 17, 'Booked'),
    (2, 18, 'Booked'),
    (2, 19, 'Booked'),
    (2, 20, 'Booked'),
    (2, 21, 'Booked'),
    (2, 22, 'Booked'),
    (2, 23, 'Booked'),
    (2, 24, 'Booked'),
    (2, 25, 'Booked'),
    (2, 26, 'Booked'),
    (2, 27, 'Booked'),
    (2, 28, 'Booked'),
    (2, 29, 'Booked'),
    (2, 30, 'Booked'),
    (2, 31, 'Booked'),
    (2, 32, 'Booked'),
    (2, 33, 'Booked'),
    (2, 34, 'Booked'),
    (2, 35, 'Booked'),
    (2, 36, 'Booked'),
    (2, 37, 'Booked'),
    (2, 38, 'Booked'),
    (2, 39, 'Booked'),
    (2, 40, 'Booked'),
    (2, 41, 'Booked'),
    (2, 42, 'Booked'),
    (2, 43, 'Booked'),
    (2, 44, 'Booked'),
    (2, 45, 'Booked'),
    (2, 46, 'Booked'),
    (2, 47, 'Booked'),
    (2, 48, 'Booked'),
    (2, 49, 'Booked'),
    (2, 50, 'Booked'),
    (3, 1, 'Booked'),
    (3, 2, 'Booked'),
    (3, 3, 'Booked'),
    (3, 4, 'Booked'),
    (3, 5, 'Booked'),
    (3, 6, 'Booked'),
    (3, 7, 'Booked'),
    (3, 8, 'Booked'),
    (3, 9, 'Booked'),
    (3, 10, 'Booked'),
    (3, 11, 'Booked'),
    (3, 12, 'Booked'),
    (3, 13, 'Booked'),
    (3, 14, 'Booked'),
    (3, 15, 'Booked'),
    (3, 16, 'Booked'),
    (3, 17, 'Booked'),
    (3, 18, 'Booked'),
    (3, 19, 'Booked'),
    (3, 20, 'Booked'),
    (3, 21, 'Booked'),
    (3, 22, 'Booked'),
    (3, 23, 'Booked'),
    (3, 24, 'Booked'),
    (3, 25, 'Booked'),
    (3, 26, 'Booked'),
    (3, 27, 'Booked'),
    (3, 28, 'Booked'),
    (3, 29, 'Booked'),
    (3, 30, 'Booked'),
    (3, 1, 'Available'),
    (3, 2, 'Available'),
    (3, 3, 'Available'),
    (3, 4, 'Available'),
    (3, 5, 'Available'),
    (4, 1, 'Available'),
    (4, 2, 'Available'),
    (4, 3, 'Available'),
    (4, 4, 'Available'),
    (4, 5, 'Available'),
    (4, 6, 'Available'),
    (4, 7, 'Available'),
    (4, 8, 'Available'),
    (4, 9, 'Available'),
    (4, 10, 'Available'),
    (4, 11, 'Available'),
    (4, 12, 'Available'),
    (4, 13, 'Available'),
    (4, 14, 'Available'),
    (4, 15, 'Available'),
    (4, 16, 'Available'),
    (4, 17, 'Available'),
    (4, 18, 'Available'),
    (4, 19, 'Available'),
    (4, 20, 'Available'),
    (4, 21, 'Available'),
    (4, 22, 'Available'),
    (4, 23, 'Available'),
    (4, 24, 'Available'),
    (4, 25, 'Available'),
    (4, 26, 'Available'),
    (4, 27, 'Available'),
    (4, 28, 'Available'),
    (4, 29, 'Available'),
    (4, 30, 'Available');


INSERT INTO payments (booking_id, customer_id, amount, payment_date, payment_status)
VALUES
    (1, 1, 500.00, '2024-01-14', 'Success'),
    (2, 2, 500.00, '2024-01-14', 'Success'),
    (3, 3, 500.00, '2024-01-14', 'Success'),
    (4, 4, 500.00, '2024-01-14', 'Success'),
    (5, 5, 500.00, '2024-01-14', 'Success'),
    (6, 6, 500.00, '2024-01-14', 'Success'),
    (7, 7, 500.00, '2024-01-14', 'Success'),
    (8, 8, 500.00, '2024-01-14', 'Success'),
    (9, 9, 500.00, '2024-01-14', 'Success'),
    (10, 10, 500.00, '2024-01-14', 'Success'),
    (11, 11, 500.00, '2024-01-14', 'Success'),
    (12, 12, 500.00, '2024-01-14', 'Success'),
    (13, 13, 500.00, '2024-01-14', 'Success'),
    (14, 14, 500.00, '2024-01-14', 'Success'),
    (15, 15, 500.00, '2024-01-14', 'Success'),
    (16, 16, 500.00, '2024-01-14', 'Success'),
    (17, 17, 500.00, '2024-01-14', 'Success'),
    (18, 18, 500.00, '2024-01-14', 'Success'),
    (19, 19, 500.00, '2024-01-14', 'Success'),
    (20, 20, 500.00, '2024-01-14', 'Success'),
    (21, 21, 500.00, '2024-01-14', 'Success'),
    (22, 22, 500.00, '2024-01-14', 'Success'),
    (23, 23, 500.00, '2024-01-14', 'Success'),
    (24, 24, 500.00, '2024-01-14', 'Success'),
    (25, 25, 500.00, '2024-01-14', 'Success'),
    (26, 26, 500.00, '2024-01-14', 'Success'),
    (27, 27, 500.00, '2024-01-14', 'Success'),
    (28, 28, 500.00, '2024-01-14', 'Success'),
    (29, 29, 500.00, '2024-01-14', 'Success'),
    (30, 30, 500.00, '2024-01-14', 'Success'),
    (31, 31, 500.00, '2024-01-14', 'Success'),
    (32, 32, 500.00, '2024-01-14', 'Success'),
    (33, 33, 500.00, '2024-01-14', 'Success'),
    (34, 34, 500.00, '2024-01-14', 'Success'),
    (35, 35, 500.00, '2024-01-14', 'Success'),
    (36, 36, 500.00, '2024-01-14', 'Success'),
    (37, 37, 500.00, '2024-01-14', 'Success'),
    (38, 38, 500.00, '2024-01-14', 'Success'),
    (39, 39, 500.00, '2024-01-14', 'Success'),
    (40, 40, 500.00, '2024-01-14', 'Success'),
    (41, 41, 500.00, '2024-01-14', 'Success'),
    (42, 42, 500.00, '2024-01-14', 'Success'),
    (43, 43, 500.00, '2024-01-14', 'Success'),
    (44, 44, 500.00, '2024-01-14', 'Success'),
    (45, 45, 500.00, '2024-01-14', 'Success'),
    (46, 46, 500.00, '2024-01-14', 'Success'),
    (47, 47, 500.00, '2024-01-14', 'Success'),
    (48, 48, 500.00, '2024-01-14', 'Success'),
    (49, 49, 500.00, '2024-01-14', 'Success'),
    (50, 50, 500.00, '2024-01-14', 'Success'),
    (51, 51, 500.00, '2024-01-14', 'Success'),
    (52, 52, 500.00, '2024-01-14', 'Success'),
    (53, 53, 500.00, '2024-01-14', 'Success'),
    (54, 54, 500.00, '2024-01-14', 'Success'),
    (55, 55, 500.00, '2024-01-14', 'Success'),
    (56, 56, 500.00, '2024-01-14', 'Success'),
    (57, 57, 500.00, '2024-01-14', 'Success'),
    (58, 58, 500.00, '2024-01-14', 'Success'),
    (59, 59, 500.00, '2024-01-14', 'Success'),
    (60, 60, 500.00, '2024-01-14', 'Success'),
    (61, 61, 500.00, '2024-01-14', 'Success'),
    (62, 62, 500.00, '2024-01-14', 'Success'),
    (63, 63, 500.00, '2024-01-14', 'Success'),
    (64, 64, 500.00, '2024-01-14', 'Success'),
    (65, 65, 500.00, '2024-01-14', 'Success'),
    (66, 66, 500.00, '2024-01-14', 'Success'),
    (67, 67, 500.00, '2024-01-14', 'Success'),
    (68, 68, 500.00, '2024-01-14', 'Success'),
    (69, 69, 500.00, '2024-01-14', 'Success'),
    (70, 70, 500.00, '2024-01-14', 'Success'),
    (71, 71, 500.00, '2024-01-14', 'Success'),
    (72, 72, 500.00, '2024-01-14', 'Success'),
    (73, 73, 500.00, '2024-01-14', 'Success'),
    (74, 74, 500.00, '2024-01-14', 'Success'),
    (75, 75, 500.00, '2024-01-14', 'Success'),
    (76, 76, 500.00, '2024-01-14', 'Success'),
    (77, 77, 500.00, '2024-01-14', 'Success'),
    (78, 78, 500.00, '2024-01-14', 'Success'),
    (79, 79, 500.00, '2024-01-14', 'Success'),
    (80, 80, 500.00, '2024-01-14', 'Success'),
    (81, 81, 500.00, '2024-01-14', 'Success'),
    (82, 82, 500.00, '2024-01-14', 'Success'),
    (83, 83, 500.00, '2024-01-14', 'Success'),
    (84, 84, 500.00, '2024-01-14', 'Success'),
    (85, 85, 500.00, '2024-01-14', 'Success'),
    (86, 86, 500.00, '2024-01-14', 'Success'),
    (87, 87, 500.00, '2024-01-14', 'Success'),
    (88, 88, 500.00, '2024-01-14', 'Success'),
    (89, 89, 500.00, '2024-01-14', 'Success'),
    (90, 90, 500.00, '2024-01-14', 'Success'),
    (91, 91, 500.00, '2024-01-14', 'Success'),
    (92, 92, 500.00, '2024-01-14', 'Success'),
    (93, 93, 500.00, '2024-01-14', 'Success'),
    (94, 94, 500.00, '2024-01-14', 'Success'),
    (95, 95, 500.00, '2024-01-14', 'Success'),
    (96, 96, 500.00, '2024-01-14', 'Success'),
    (97, 97, 500.00, '2024-01-14', 'Success'),
    (98, 98, 500.00, '2024-01-14', 'Success'),
    (99, 99, 500.00, '2024-01-14', 'Success'),
    (100, 100, 500.00, '2024-01-14', 'Success'),
    (101, 101, 800.00, '2024-01-15', 'Success'),
    (102, 102, 800.00, '2024-01-15', 'Success'),
    (103, 103, 800.00, '2024-01-15', 'Success'),
    (104, 104, 800.00, '2024-01-15', 'Success'),
    (105, 105, 800.00, '2024-01-15', 'Success'),
    (106, 106, 800.00, '2024-01-15', 'Success'),
    (107, 107, 800.00, '2024-01-15', 'Success'),
    (108, 108, 800.00, '2024-01-15', 'Success'),
    (109, 109, 800.00, '2024-01-15', 'Success'),
    (110, 110, 800.00, '2024-01-15', 'Success'),
    (111, 111, 800.00, '2024-01-15', 'Success'),
    (112, 112, 800.00, '2024-01-15', 'Success'),
    (113, 113, 800.00, '2024-01-15', 'Success'),
    (114, 114, 800.00, '2024-01-15', 'Success'),
    (115, 115, 800.00, '2024-01-15', 'Success'),
    (116, 116, 800.00, '2024-01-15', 'Success'),
    (117, 117, 800.00, '2024-01-15', 'Success'),
    (118, 118, 800.00, '2024-01-15', 'Success'),
    (119, 119, 800.00, '2024-01-15', 'Success'),
    (120, 120, 800.00, '2024-01-15', 'Success'),
    (121, 121, 800.00, '2024-01-15', 'Success'),
    (122, 122, 800.00, '2024-01-15', 'Success'),
    (123, 123, 800.00, '2024-01-15', 'Success'),
    (124, 124, 800.00, '2024-01-15', 'Success'),
    (125, 125, 800.00, '2024-01-15', 'Success'),
    (126, 126, 800.00, '2024-01-15', 'Success'),
    (127, 127, 800.00, '2024-01-15', 'Success'),
    (128, 128, 800.00, '2024-01-15', 'Success'),
    (129, 129, 800.00, '2024-01-15', 'Success'),
    (130, 130, 800.00, '2024-01-15', 'Success'),
    (131, 131, 800.00, '2024-01-15', 'Success'),
    (132, 132, 800.00, '2024-01-15', 'Success'),
    (133, 133, 800.00, '2024-01-15', 'Success'),
    (134, 134, 800.00, '2024-01-15', 'Success'),
    (135, 135, 800.00, '2024-01-15', 'Success'),
    (136, 136, 800.00, '2024-01-15', 'Success'),
    (137, 137, 800.00, '2024-01-15', 'Success'),
    (138, 138, 800.00, '2024-01-15', 'Success'),
    (139, 139, 800.00, '2024-01-15', 'Success'),
    (140, 140, 800.00, '2024-01-15', 'Success');


