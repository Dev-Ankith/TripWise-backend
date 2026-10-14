package com.tripwise.config;

import com.tripwise.entity.Destination;
import com.tripwise.entity.TravelService;
import com.tripwise.entity.User;
import com.tripwise.repository.DestinationRepository;
import com.tripwise.repository.TravelServiceRepository;
import com.tripwise.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initDatabase(DestinationRepository destinationRepository,
                                         TravelServiceRepository travelServiceRepository,
                                         UserRepository userRepository) {
        return args -> {
            // Seed initial users if table is empty
            if (userRepository.count() == 0) {
                userRepository.save(new User(
                        "Aditya Sharma",
                        "aditya.sharma@example.com",
                        "Password@123",
                        "+91 98765 43210"
                ));
                userRepository.save(new User(
                        "Priya Patel",
                        "priya.patel@example.com",
                        "TripWise#2026",
                        "+91 98234 56789"
                ));
            }

            // Check if database is already seeded to avoid duplication
            if (destinationRepository.count() > 0) {
                return;
            }

            // 1. Goa
            Destination goa = destinationRepository.save(new Destination(
                    null,
                    "Goa (North & South)",
                    "Goa",
                    "Golden sun-kissed beaches, Portuguese heritage architecture, vibrant water sports, and tranquil coastal spice farms.",
                    "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?auto=format&fit=crop&w=1000&q=80",
                    new BigDecimal("16000.00"),
                    "Beaches"
            ));

            // 2. Jaipur
            Destination jaipur = destinationRepository.save(new Destination(
                    null,
                    "Jaipur, Rajasthan",
                    "Rajasthan",
                    "The Pink City steeped in royal Rajput history, majestic hillside fortresses, colorful bazaars, and opulent palaces.",
                    "https://images.unsplash.com/photo-1599661046289-e31897846e41?auto=format&fit=crop&w=1000&q=80",
                    new BigDecimal("12500.00"),
                    "Heritage"
            ));

            // 3. Munnar
            Destination munnar = destinationRepository.save(new Destination(
                    null,
                    "Munnar & Alleppey, Kerala",
                    "Kerala",
                    "Rolling emerald tea plantations, misty Western Ghat peaks, and serene overnight backwater houseboat cruising.",
                    "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?auto=format&fit=crop&w=1000&q=80",
                    new BigDecimal("15500.00"),
                    "Nature"
            ));

            // 4. Manali
            Destination manali = destinationRepository.save(new Destination(
                    null,
                    "Manali, Himachal Pradesh",
                    "Himachal Pradesh",
                    "Breathtaking Himalayan valleys, snow-dusted pine forests, Solang adventures, and tranquil Old Manali cafes.",
                    "https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?auto=format&fit=crop&w=1000&q=80",
                    new BigDecimal("14500.00"),
                    "Mountains"
            ));

            // 5. Hyderabad
            Destination hyderabad = destinationRepository.save(new Destination(
                    null,
                    "Hyderabad, Telangana",
                    "Telangana",
                    "The City of Pearls blending historic Charminar and Golconda Fort with royal Nizami culinary traditions and modern tech hubs.",
                    "https://images.unsplash.com/photo-1605649487212-47bdab064df8?auto=format&fit=crop&w=1000&q=80",
                    new BigDecimal("11500.00"),
                    "Heritage"
            ));

            // Seed Realistic Travel Services for each destination across required categories:
            // accommodation, transportation, local transport, activities, sightseeing, guides, food

            // --- GOA SERVICES ---
            travelServiceRepository.saveAll(List.of(
                    new TravelService(null, "Direct Indigo Return Flight to Dabolim", "transportation", goa,
                            "Economy round trip direct flight ticket with 15kg checked luggage.", "IndiGo Airlines",
                            new BigDecimal("5800.00"), 4.6, "+91 124 6173838", "AVAILABLE"),
                    new TravelService(null, "Goa Express AC 3-Tier Train", "transportation", goa,
                            "Comfortable sleeper berths from major metros with bedding.", "Indian Railways",
                            new BigDecimal("1850.00"), 4.3, "139", "AVAILABLE"),
                    new TravelService(null, "Santana Beach Resort Candolim", "accommodation", goa,
                            "Beachside resort with 2 swimming pools, garden patios, and complimentary breakfast.", "Santana Beach Resort",
                            new BigDecimal("3800.00"), 4.7, "+91 832 2494000", "AVAILABLE"),
                    new TravelService(null, "Zostel Morjim Boutique Hostel", "accommodation", goa,
                            "Trendy backpacker hostel near Morjim beach with private AC rooms and common lounge.", "Zostel Hospitality",
                            new BigDecimal("1400.00"), 4.8, "+91 22 48962262", "AVAILABLE"),
                    new TravelService(null, "Honda Activa Scooter Rental (Per Day)", "local transport", goa,
                            "Well-maintained 110cc scooter with helmet. Fuel on actuals.", "Goa Coastal Rentals",
                            new BigDecimal("450.00"), 4.7, "+91 98221 23456", "AVAILABLE"),
                    new TravelService(null, "Full Day Private AC Sedan with Driver", "local transport", goa,
                            "8 hours / 80 km North & South Goa sightseeing tour cab.", "Goa Miles Cabs",
                            new BigDecimal("2200.00"), 4.5, "+91 832 2435678", "AVAILABLE"),
                    new TravelService(null, "Scuba Diving & Grand Island Boat Cruise", "activities", goa,
                            "PADI certified instructor diving with underwater video, equipment, and lunch.", "Goa Aqua Adventures",
                            new BigDecimal("2600.00"), 4.9, "+91 98230 45678", "AVAILABLE"),
                    new TravelService(null, "Dudhsagar Waterfalls Jeep Safari", "activities", goa,
                            "Forest department registered 4x4 open jeep safari through Bhagwan Mahaveer Sanctuary.", "Mollem Safari Union",
                            new BigDecimal("1950.00"), 4.7, "+91 98234 56789", "AVAILABLE"),
                    new TravelService(null, "Fort Aguada & Reis Magos Heritage Entry Pass", "sightseeing", goa,
                            "Official entry passes for Portuguese sea-facing fortifications.", "Goa Tourism Dept",
                            new BigDecimal("250.00"), 4.5, "info@goatourism.gov.in", "AVAILABLE"),
                    new TravelService(null, "Fontainhas Latin Quarter Walking Guide", "guides", goa,
                            "2-hour certified heritage walk through colorful Indo-Portuguese homes and bakeries.", "Soul Travelling Goa",
                            new BigDecimal("650.00"), 4.9, "+91 95279 38383", "AVAILABLE"),
                    new TravelService(null, "Coastal Goan Seafood Tasting Thali", "food", goa,
                            "Authentic Fish Curry, Prawn Balchao, Sol Kadhi, and Bebinca set menu at Ritz Classic.", "Ritz Classic Panaji",
                            new BigDecimal("600.00"), 4.8, "+91 832 2426417", "AVAILABLE")
            ));

            // --- JAIPUR SERVICES ---
            travelServiceRepository.saveAll(List.of(
                    new TravelService(null, "Ajmer Shatabdi Express AC Chair Car", "transportation", jaipur,
                            "Fast morning express train with breakfast included.", "Indian Railways",
                            new BigDecimal("1250.00"), 4.6, "139", "AVAILABLE"),
                    new TravelService(null, "Umaid Bhawan Heritage Haveli Hotel", "accommodation", jaipur,
                            "Classic Rajput-style carved balconies, pool, and rooftop dining in Bani Park.", "Umaid Haveli Group",
                            new BigDecimal("3200.00"), 4.7, "+91 141 2316184", "AVAILABLE"),
                    new TravelService(null, "Full Day Private AC Cab for Amber & Nahargarh", "local transport", jaipur,
                            "Dedicated sedan covering Amber Fort, Jaigarh, and sunset at Nahargarh.", "Pink City Cabs",
                            new BigDecimal("1800.00"), 4.6, "+91 141 2200111", "AVAILABLE"),
                    new TravelService(null, "Hot Air Balloon Safari over Aravali & Palaces", "activities", jaipur,
                            "60-minute sunrise flight over royal forts and rural villages.", "SkyWaltz Balloon Safaris",
                            new BigDecimal("8500.00"), 4.9, "+91 98290 88888", "AVAILABLE"),
                    new TravelService(null, "Composite Monument Entry Pass (5 Monuments)", "sightseeing", jaipur,
                            "Official department ticket for Amber Fort, Hawa Mahal, Jantar Mantar, Nahargarh, Albert Hall.", "Dept of Archaeology Rajasthan",
                            new BigDecimal("500.00"), 4.8, "info@rajasthantourism.gov.in", "AVAILABLE"),
                    new TravelService(null, "Certified Amber Fort & City Palace Historian Guide", "guides", jaipur,
                            "Government-approved heritage guide explaining Mughal & Rajput architecture.", "Jaipur Guide Guild",
                            new BigDecimal("1000.00"), 4.8, "+91 94140 12345", "AVAILABLE"),
                    new TravelService(null, "Royal Rajasthani Dal Baati Churma Thali", "food", jaipur,
                            "Traditional ghee-rich Rajasthani feast at LMB Johari Bazaar.", "Laxmi Misthan Bhandar",
                            new BigDecimal("550.00"), 4.7, "+91 141 2565844", "AVAILABLE")
            ));

            // --- MUNNAR & ALLEPPEY SERVICES ---
            travelServiceRepository.saveAll(List.of(
                    new TravelService(null, "Cochin Airport to Munnar Hill Station Private Cab", "transportation", munnar,
                            "Comfortable scenic Ghat road drive via Cheeyappara waterfalls.", "Kerala State Cabs",
                            new BigDecimal("2800.00"), 4.6, "+91 484 2610115", "AVAILABLE"),
                    new TravelService(null, "Tea Valley Plantation Resort Munnar", "accommodation", munnar,
                            "Serene cottages overlooking misty emerald tea slopes with campfire area.", "Tea Valley Resorts",
                            new BigDecimal("3500.00"), 4.8, "+91 4865 230500", "AVAILABLE"),
                    new TravelService(null, "Alleppey Deluxe Private Houseboat with Meals", "accommodation", munnar,
                            "Air-conditioned backwater houseboat cruise including Kerala lunch, snacks, and dinner.", "Spice Coast Houseboats",
                            new BigDecimal("8500.00"), 4.9, "+91 477 2244455", "AVAILABLE"),
                    new TravelService(null, "Local Sightseeing 4x4 Jeep to Kolukkumalai", "local transport", munnar,
                            "Off-road drive to the world's highest organic tea estate for sunrise.", "Munnar Offroad Drivers",
                            new BigDecimal("2500.00"), 4.8, "+91 94470 12345", "AVAILABLE"),
                    new TravelService(null, "Eravikulam National Park Safari & Nilgiri Tahr", "sightseeing", munnar,
                            "Forest department bus ride and walking trail amidst rolling shoals.", "Kerala Forest Dept",
                            new BigDecimal("200.00"), 4.7, "eravikulam@forest.kerala.gov.in", "AVAILABLE"),
                    new TravelService(null, "Tea Tasting & Orthodox Leaf Manufacturing Tour", "activities", munnar,
                            "Guided tea museum experience with curated single-estate tea sampling.", "Tata Tea Museum",
                            new BigDecimal("350.00"), 4.6, "+91 4865 230561", "AVAILABLE"),
                    new TravelService(null, "Traditional Kerala Sadhya Feast on Banana Leaf", "food", munnar,
                            "Grand vegetarian banquet featuring 18 dishes, sambar, avial, and payasam.", "Saravana Bhavan Hill Top",
                            new BigDecimal("350.00"), 4.6, "+91 4865 230489", "AVAILABLE")
            ));

            // --- HYDERABAD SERVICES ---
            travelServiceRepository.saveAll(List.of(
                    new TravelService(null, "Hyderabad Metro 3-Day Unlimited Tourist Card", "local transport", hyderabad,
                            "Fast, seamless travel connecting HITEC City, Secunderabad, and MGBS.", "L&T Metro Rail",
                            new BigDecimal("350.00"), 4.6, "1800 425 4567", "AVAILABLE"),
                    new TravelService(null, "Taj Banjara Lakeside Hotel", "accommodation", hyderabad,
                            "Luxury rooms with serene lake views, award-winning restaurants, and gym.", "IHCL Hotels",
                            new BigDecimal("4900.00"), 4.7, "+91 40 66669999", "AVAILABLE"),
                    new TravelService(null, "Golconda Fort Sound & Light Show Entry Ticket", "sightseeing", hyderabad,
                            "Evening illuminated history narration amidst 16th century ramparts.", "Telangana Tourism",
                            new BigDecimal("150.00"), 4.7, "tourism@telangana.gov.in", "AVAILABLE"),
                    new TravelService(null, "Ramoji Film City Studio Tour VIP Pass", "activities", hyderabad,
                            "Guided film set tour, stunt shows, gardens, and express line entry.", "Ramoji Film City",
                            new BigDecimal("2500.00"), 4.8, "+91 8415 246555", "AVAILABLE"),
                    new TravelService(null, "Old City Charminar & Pearl Bazaar Walking Guide", "guides", hyderabad,
                            "Nizami historian explaining Laad Bazaar lacquer bangles, Chowmahalla Palace.", "Hyderabad Heritage Trust",
                            new BigDecimal("750.00"), 4.8, "+91 40 24567890", "AVAILABLE"),
                    new TravelService(null, "Authentic Hyderabadi Dum Biryani & Haleem Dining", "food", hyderabad,
                            "Signature mutton dum biryani, mirchi ka salan, and double ka meetha at Shadab.", "Hotel Shadab",
                            new BigDecimal("500.00"), 4.9, "+91 40 24565949", "AVAILABLE")
            ));

            // --- MANALI SERVICES ---
            travelServiceRepository.saveAll(List.of(
                    new TravelService(null, "Intercity Volvo AC Sleeper Bus Delhi-Manali", "transportation", manali,
                            "Overnight semi-sleeper coach with blanket, charging ports, and water bottle.", "Himachal Roadways HPTDC",
                            new BigDecimal("1450.00"), 4.5, "+91 177 2800073", "AVAILABLE"),
                    new TravelService(null, "Himalayan Pine Wood Chalet Hotel", "accommodation", manali,
                            "Panoramic snow peak views, in-room heating, cedar wood interiors, and apple orchards.", "The Himalayan Chalet",
                            new BigDecimal("3100.00"), 4.8, "+91 1902 252333", "AVAILABLE"),
                    new TravelService(null, "Solang Valley Paragliding & ATV Adventure", "activities", manali,
                            "Tandem high-fly paragliding flight with certified pilot and GoPro recording.", "Solang Adventure Association",
                            new BigDecimal("3200.00"), 4.8, "+91 1902 256789", "AVAILABLE"),
                    new TravelService(null, "Rohtang Pass Snow Permit & 4x4 Vehicle Pass", "local transport", manali,
                            "National Green Tribunal verified snow taxi with driver and mountain permit.", "Manali Taxi Operators Union",
                            new BigDecimal("3800.00"), 4.6, "+91 1902 252450", "AVAILABLE"),
                    new TravelService(null, "Hadimba Temple & Vashisht Hot Springs Guided Walk", "guides", manali,
                            "Local cultural guide recounting cedar forest folklore and ancient wood architecture.", "Kullu Valley Guides",
                            new BigDecimal("600.00"), 4.7, "+91 98160 11223", "AVAILABLE"),
                    new TravelService(null, "Himachali Dham & Trout Fish Special Tasting", "food", manali,
                            "Traditional yogurt-based kadhi, madra, and fresh river trout.", "Johnson's Cafe & Bar",
                            new BigDecimal("650.00"), 4.8, "+91 1902 251523", "AVAILABLE")
            ));
        };
    }
}
