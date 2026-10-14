-- TripWise Initial Sample Data Seed Script
-- Safe idempotent insertion for Indian destinations and realistic travel services

-- 1. DESTINATIONS (Goa, Jaipur, Munnar, Manali, Hyderabad)
INSERT INTO destinations (id, name, state, description, image_url, estimated_starting_cost, travel_category)
VALUES
(1, 'Goa (North & South)', 'Goa', 'Golden sun-kissed beaches, Portuguese heritage architecture, vibrant water sports, and tranquil coastal spice farms.', 'https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?auto=format&fit=crop&w=1000&q=80', 16000.00, 'Beaches'),
(2, 'Jaipur, Rajasthan', 'Rajasthan', 'The Pink City steeped in royal Rajput history, majestic hillside fortresses, colorful bazaars, and opulent palaces.', 'https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=1000&q=80', 12500.00, 'Heritage'),
(3, 'Munnar & Alleppey, Kerala', 'Kerala', 'Rolling emerald tea plantations, misty Western Ghat peaks, and serene overnight backwater houseboat cruising.', 'https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?auto=format&fit=crop&w=1000&q=80', 15500.00, 'Nature'),
(4, 'Manali, Himachal Pradesh', 'Himachal Pradesh', 'Breathtaking Himalayan valleys, snow-dusted pine forests, Solang adventures, and tranquil Old Manali cafes.', 'https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?auto=format&fit=crop&w=1000&q=80', 14500.00, 'Mountains'),
(5, 'Hyderabad, Telangana', 'Telangana', 'The City of Pearls blending historic Charminar and Golconda Fort with royal Nizami culinary traditions and modern tech hubs.', 'https://images.unsplash.com/photo-1605649487212-47bdab064df8?auto=format&fit=crop&w=1000&q=80', 11500.00, 'Heritage')
ON CONFLICT (id) DO UPDATE SET name=EXCLUDED.name;

-- 2. TRAVEL SERVICES
-- Categories: accommodation, transportation, local transport, activities, sightseeing, guides, food

-- Goa Services
INSERT INTO travel_services (id, name, category, destination_id, description, provider_name, price, rating, contact_details, availability_status)
VALUES
(1, 'Direct Indigo Return Flight to Dabolim', 'transportation', 1, 'Economy round trip direct flight ticket with 15kg checked luggage.', 'IndiGo Airlines', 5800.00, 4.6, '+91 124 6173838', 'AVAILABLE'),
(2, 'Goa Express AC 3-Tier Train', 'transportation', 1, 'Comfortable sleeper berths from major metros with bedding.', 'Indian Railways', 1850.00, 4.3, '139', 'AVAILABLE'),
(3, 'Santana Beach Resort Candolim', 'accommodation', 1, 'Beachside resort with 2 swimming pools, garden patios, and complimentary breakfast.', 'Santana Beach Resort', 3800.00, 4.7, '+91 832 2494000', 'AVAILABLE'),
(4, 'Zostel Morjim Boutique Hostel', 'accommodation', 1, 'Trendy backpacker hostel near Morjim beach with private AC rooms and common lounge.', 'Zostel Hospitality', 1400.00, 4.8, '+91 22 48962262', 'AVAILABLE'),
(5, 'Honda Activa Scooter Rental (Per Day)', 'local transport', 1, 'Well-maintained 110cc scooter with helmet. Fuel on actuals.', 'Goa Coastal Rentals', 450.00, 4.7, '+91 98221 23456', 'AVAILABLE'),
(6, 'Full Day Private AC Sedan with Driver', 'local transport', 1, '8 hours / 80 km North & South Goa sightseeing tour cab.', 'Goa Miles Cabs', 2200.00, 4.5, '+91 832 2435678', 'AVAILABLE'),
(7, 'Scuba Diving & Grand Island Boat Cruise', 'activities', 1, 'PADI certified instructor diving with underwater video, equipment, and lunch.', 'Goa Aqua Adventures', 2600.00, 4.9, '+91 98230 45678', 'AVAILABLE'),
(8, 'Dudhsagar Waterfalls Jeep Safari', 'activities', 1, 'Forest department registered 4x4 open jeep safari through Bhagwan Mahaveer Sanctuary.', 'Mollem Safari Union', 1950.00, 4.7, '+91 98234 56789', 'AVAILABLE'),
(9, 'Fort Aguada & Reis Magos Heritage Entry Pass', 'sightseeing', 1, 'Official entry passes for Portuguese sea-facing fortifications.', 'Goa Tourism Dept', 250.00, 4.5, 'info@goatourism.gov.in', 'AVAILABLE'),
(10, 'Fontainhas Latin Quarter Walking Guide', 'guides', 1, '2-hour certified heritage walk through colorful Indo-Portuguese homes and bakeries.', 'Soul Travelling Goa', 650.00, 4.9, '+91 95279 38383', 'AVAILABLE'),
(11, 'Coastal Goan Seafood Tasting Thali', 'food', 1, 'Authentic Fish Curry, Prawn Balchao, Sol Kadhi, and Bebinca set menu at Ritz Classic.', 'Ritz Classic Panaji', 600.00, 4.8, '+91 832 2426417', 'AVAILABLE')
ON CONFLICT (id) DO UPDATE SET name=EXCLUDED.name;

-- Jaipur Services
INSERT INTO travel_services (id, name, category, destination_id, description, provider_name, price, rating, contact_details, availability_status)
VALUES
(12, 'Ajmer Shatabdi Express AC Chair Car', 'transportation', 2, 'Fast morning express train with breakfast included.', 'Indian Railways', 1250.00, 4.6, '139', 'AVAILABLE'),
(13, 'Umaid Bhawan Heritage Haveli Hotel', 'accommodation', 2, 'Classic Rajput-style carved balconies, pool, and rooftop dining in Bani Park.', 'Umaid Haveli Group', 3200.00, 4.7, '+91 141 2316184', 'AVAILABLE'),
(14, 'Full Day Private AC Cab for Amber & Nahargarh', 'local transport', 2, 'Dedicated sedan covering Amber Fort, Jaigarh, and sunset at Nahargarh.', 'Pink City Cabs', 1800.00, 4.6, '+91 141 2200111', 'AVAILABLE'),
(15, 'Hot Air Balloon Safari over Aravali & Palaces', 'activities', 2, '60-minute sunrise flight over royal forts and rural villages.', 'SkyWaltz Balloon Safaris', 8500.00, 4.9, '+91 98290 88888', 'AVAILABLE'),
(16, 'Composite Monument Entry Pass (5 Monuments)', 'sightseeing', 2, 'Official department ticket for Amber Fort, Hawa Mahal, Jantar Mantar, Nahargarh, Albert Hall.', 'Dept of Archaeology Rajasthan', 500.00, 4.8, 'info@rajasthantourism.gov.in', 'AVAILABLE'),
(17, 'Certified Amber Fort & City Palace Historian Guide', 'guides', 2, 'Government-approved heritage guide explaining Mughal & Rajput architecture.', 'Jaipur Guide Guild', 1000.00, 4.8, '+91 94140 12345', 'AVAILABLE'),
(18, 'Royal Rajasthani Dal Baati Churma Thali', 'food', 2, 'Traditional ghee-rich Rajasthani feast at LMB Johari Bazaar.', 'Laxmi Misthan Bhandar', 550.00, 4.7, '+91 141 2565844', 'AVAILABLE')
ON CONFLICT (id) DO UPDATE SET name=EXCLUDED.name;

-- Munnar Services
INSERT INTO travel_services (id, name, category, destination_id, description, provider_name, price, rating, contact_details, availability_status)
VALUES
(19, 'Cochin Airport to Munnar Hill Station Private Cab', 'transportation', 3, 'Comfortable scenic Ghat road drive via Cheeyappara waterfalls.', 'Kerala State Cabs', 2800.00, 4.6, '+91 484 2610115', 'AVAILABLE'),
(20, 'Tea Valley Plantation Resort Munnar', 'accommodation', 3, 'Serene cottages overlooking misty emerald tea slopes with campfire area.', 'Tea Valley Resorts', 3500.00, 4.8, '+91 4865 230500', 'AVAILABLE'),
(21, 'Alleppey Deluxe Private Houseboat with Meals', 'accommodation', 3, 'Air-conditioned backwater houseboat cruise including Kerala lunch, snacks, and dinner.', 'Spice Coast Houseboats', 8500.00, 4.9, '+91 477 2244455', 'AVAILABLE'),
(22, 'Local Sightseeing 4x4 Jeep to Kolukkumalai', 'local transport', 3, 'Off-road drive to the worlds highest organic tea estate for sunrise.', 'Munnar Offroad Drivers', 2500.00, 4.8, '+91 94470 12345', 'AVAILABLE'),
(23, 'Eravikulam National Park Safari & Nilgiri Tahr', 'sightseeing', 3, 'Forest department bus ride and walking trail amidst rolling shoals.', 'Kerala Forest Dept', 200.00, 4.7, 'eravikulam@forest.kerala.gov.in', 'AVAILABLE'),
(24, 'Tea Tasting & Orthodox Leaf Manufacturing Tour', 'activities', 3, 'Guided tea museum experience with curated single-estate tea sampling.', 'Tata Tea Museum', 350.00, 4.6, '+91 4865 230561', 'AVAILABLE'),
(25, 'Traditional Kerala Sadhya Feast on Banana Leaf', 'food', 3, 'Grand vegetarian banquet featuring 18 dishes, sambar, avial, and payasam.', 'Saravana Bhavan Hill Top', 350.00, 4.6, '+91 4865 230489', 'AVAILABLE')
ON CONFLICT (id) DO UPDATE SET name=EXCLUDED.name;

-- Hyderabad Services
INSERT INTO travel_services (id, name, category, destination_id, description, provider_name, price, rating, contact_details, availability_status)
VALUES
(26, 'Hyderabad Metro 3-Day Unlimited Tourist Card', 'local transport', 5, 'Fast, seamless travel connecting HITEC City, Secunderabad, and MGBS.', 'L&T Metro Rail', 350.00, 4.6, '1800 425 4567', 'AVAILABLE'),
(27, 'Taj Banjara Lakeside Hotel', 'accommodation', 5, 'Luxury rooms with serene lake views, award-winning restaurants, and gym.', 'IHCL Hotels', 4900.00, 4.7, '+91 40 66669999', 'AVAILABLE'),
(28, 'Golconda Fort Sound & Light Show Entry Ticket', 'sightseeing', 5, 'Evening illuminated history narration amidst 16th century ramparts.', 'Telangana Tourism', 150.00, 4.7, 'tourism@telangana.gov.in', 'AVAILABLE'),
(29, 'Ramoji Film City Studio Tour VIP Pass', 'activities', 5, 'Guided film set tour, stunt shows, gardens, and express line entry.', 'Ramoji Film City', 2500.00, 4.8, '+91 8415 246555', 'AVAILABLE'),
(30, 'Old City Charminar & Pearl Bazaar Walking Guide', 'guides', 5, 'Nizami historian explaining Laad Bazaar lacquer bangles, Chowmahalla Palace.', 'Hyderabad Heritage Trust', 750.00, 4.8, '+91 40 24567890', 'AVAILABLE'),
(31, 'Authentic Hyderabadi Dum Biryani & Haleem Dining', 'food', 5, 'Signature mutton dum biryani, mirchi ka salan, and double ka meetha at Shadab.', 'Hotel Shadab', 500.00, 4.9, '+91 40 24565949', 'AVAILABLE')
ON CONFLICT (id) DO UPDATE SET name=EXCLUDED.name;

-- Manali Services
INSERT INTO travel_services (id, name, category, destination_id, description, provider_name, price, rating, contact_details, availability_status)
VALUES
(32, 'Intercity Volvo AC Sleeper Bus Delhi-Manali', 'transportation', 4, 'Overnight semi-sleeper coach with blanket, charging ports, and water bottle.', 'Himachal Roadways HPTDC', 1450.00, 4.5, '+91 177 2800073', 'AVAILABLE'),
(33, 'Himalayan Pine Wood Chalet Hotel', 'accommodation', 4, 'Panoramic snow peak views, in-room heating, cedar wood interiors, and apple orchards.', 'The Himalayan Chalet', 3100.00, 4.8, '+91 1902 252333', 'AVAILABLE'),
(34, 'Solang Valley Paragliding & ATV Adventure', 'activities', 4, 'Tandem high-fly paragliding flight with certified pilot and GoPro recording.', 'Solang Adventure Association', 3200.00, 4.8, '+91 1902 256789', 'AVAILABLE'),
(35, 'Rohtang Pass Snow Permit & 4x4 Vehicle Pass', 'local transport', 4, 'National Green Tribunal verified snow taxi with driver and mountain permit.', 'Manali Taxi Operators Union', 3800.00, 4.6, '+91 1902 252450', 'AVAILABLE'),
(36, 'Hadimba Temple & Vashisht Hot Springs Guided Walk', 'guides', 4, 'Local cultural guide recounting cedar forest folklore and ancient wood architecture.', 'Kullu Valley Guides', 600.00, 4.7, '+91 98160 11223', 'AVAILABLE'),
(37, 'Himachali Dham & Trout Fish Special Tasting', 'food', 4, 'Traditional yogurt-based kadhi, madra, and fresh river trout.', 'Johnsons Cafe & Bar', 650.00, 4.8, '+91 1902 251523', 'AVAILABLE')
ON CONFLICT (id) DO UPDATE SET name=EXCLUDED.name;

-- 3. USERS SEED
INSERT INTO users (id, full_name, email, password, phone_number, created_at)
VALUES
(1, 'Aditya Sharma', 'aditya.sharma@example.com', 'Password@123', '+91 98765 43210', NOW()),
(2, 'Priya Patel', 'priya.patel@example.com', 'TripWise#2026', '+91 98234 56789', NOW())
ON CONFLICT (id) DO UPDATE SET full_name=EXCLUDED.full_name;

SELECT setval(pg_get_serial_sequence('destinations', 'id'), COALESCE(MAX(id), 1), MAX(id) IS NOT NULL)
FROM destinations;
SELECT setval(pg_get_serial_sequence('travel_services', 'id'), COALESCE(MAX(id), 1), MAX(id) IS NOT NULL)
FROM travel_services;
SELECT setval(pg_get_serial_sequence('users', 'id'), COALESCE(MAX(id), 1), MAX(id) IS NOT NULL)
FROM users;
