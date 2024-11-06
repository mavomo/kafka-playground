package com.playground.kafkaplayground.infra;

import com.playground.kafkaplayground.domain.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Service
public class ProductService {

    public static final int DEFAULT_PRODUCT_QUANTITY = 10;

    public List<Product> products() {
        return new ArrayList<>() {
            {
                add(new Product(1L, "Tennis Racket", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(2L, "Basketball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(3L, "Soccer Ball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(4L, "Golf Clubs Set", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(5L, "Volleyball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(6L, "Baseball Bat", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(7L, "Badminton Racket",
                        new Product.Price(BigDecimal.valueOf(100),
                                Currency.getInstance("USD"))));
                add(new Product(8L, "Table Tennis Paddle",
                        new Product.Price(BigDecimal.valueOf(100),
                                Currency.getInstance("USD"))));
                add(new Product(9L, "Hockey Stick", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(10L, "Cricket Bat", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(11L, "Frisbee", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(12L, "Skateboard", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(13L, "Roller Skates", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(14L, "Ice Skates", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(15L, "Snowboard", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(16L, "Ski Set", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(17L, "Swimming Goggles",
                        new Product.Price(BigDecimal.valueOf(100),
                                Currency.getInstance("USD"))));
                add(new Product(18L, "Diving Mask", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(19L, "Snorkel", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(20L, "Surfboard", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(21L, "Kayak", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(22L, "Canoe", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(23L, "Rowing Oars", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(24L, "Fishing Rod", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(25L, "Climbing Harness",
                        new Product.Price(BigDecimal.valueOf(100),
                                Currency.getInstance("USD"))));
                add(new Product(26L, "Climbing Shoes",
                        new Product.Price(BigDecimal.valueOf(100),
                                Currency.getInstance("USD"))));
                add(new Product(27L, "Climbing Rope", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(28L, "Boxing Gloves", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(29L, "Punching Bag", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(30L, "Yoga Mat", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(31L, "Dumbbell Set", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(32L, "Kettlebell", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(33L, "Jump Rope", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(34L, "Resistance Bands",
                        new Product.Price(BigDecimal.valueOf(100),
                                Currency.getInstance("USD"))));
                add(new Product(35L, "Foam Roller", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(36L, "Exercise Ball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(37L, "Treadmill", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(38L, "Stationary Bike",
                        new Product.Price(BigDecimal.valueOf(100),
                                Currency.getInstance("USD"))));
                add(new Product(39L, "Elliptical Machine", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(40L, "Rowing Machine",
                        new Product.Price(BigDecimal.valueOf(100),
                                Currency.getInstance("USD"))));
                add(new Product(41L, "Weight Bench", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(42L, "Barbell", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(43L, "Weight Plates", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(44L, "Pull-up Bar", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(45L, "Gymnastics Rings",
                        new Product.Price(BigDecimal.valueOf(100),
                                Currency.getInstance("USD"))));
                add(new Product(46L, "Balance Beam", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(47L, "Parallel Bars", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(48L, "Pommel Horse", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(49L, "Trampoline", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(50L, "Archery Bow", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(51L, "Archery Arrows", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(52L, "Fencing Sword", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(53L, "Fencing Mask", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(54L, "Javelin", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(55L, "Discus", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(56L, "Shot Put", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(57L, "Pole Vault Pole", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(58L, "High Jump Bar", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(59L, "Starting Blocks", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(60L, "Hurdles", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(61L, "Stopwatch", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(62L, "Referee Whistle", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(63L, "Scoreboard", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(64L, "Team Jersey", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(65L, "Sports Shorts", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(66L, "Running Shoes", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(67L, "Cleats", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(68L, "Swim Cap", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(69L, "Swim Fins", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(70L, "Wetsuit", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(71L, "Lifejacket", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(72L, "Bicycle Helmet", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(73L, "Knee Pads", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(74L, "Elbow Pads", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(75L, "Wrist Guards", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(76L, "Mouth Guard", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(77L, "Shin Guards", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(78L, "Golf Balls", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(79L, "Tennis Balls", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(80L, "Shuttlecocks", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(81L, "Table Tennis Balls", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(82L, "Baseball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(83L, "Softball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(84L, "Cricket Ball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(85L, "Squash Ball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(86L, "Racquetball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(87L, "Lacrosse Stick", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(88L, "Lacrosse Ball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(89L, "Rugby Ball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(90L, "American Football", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(91L, "Handball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(92L, "Water Polo Ball", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(93L, "Ping Pong Table", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(94L, "Billiard Cue", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(95L, "Billiard Balls", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(96L, "Dartboard", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(97L, "Darts", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(98L, "Chess Set", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(99L, "Poker Chips", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
                add(new Product(100L, "Playing Cards", new Product.Price(BigDecimal.valueOf(100),
                        Currency.getInstance("USD"))));
            }
        };
    }

    public Product getProductById(Long productId) {
        return this.products().stream()
                .filter(p -> p.id().equals(productId))
                .findFirst()
                .orElse(null);
    }
}
